/*
 * @(#)MrFlushEntityEventListener.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.hibernate.listener;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.engine.EntityEntry;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.Status;
import org.hibernate.event.FlushEntityEvent;
import org.hibernate.event.def.DefaultFlushEntityEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.pretty.MessageHelper;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.support.log.AutoLog;
import com.winxuan.ec.support.log.AutoLogger;
import com.winxuan.ec.support.principal.PrincipalHolder;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-4
 */
public class MrFlushEntityEventListener extends DefaultFlushEntityEventListener {

	private static final long serialVersionUID = 2692348206189821061L;
	
	private static final Log LOG = LogFactory.getLog(MrFlushEntityEventListener.class);
	
	protected void dirtyCheck(FlushEntityEvent event) throws HibernateException {

		final Object entity = event.getEntity();
		final Object[] values = event.getPropertyValues();
		final SessionImplementor session = event.getSession();
		final EntityEntry entry = event.getEntityEntry();
		final EntityPersister persister = entry.getPersister();
		final Serializable id = entry.getId();
		final Object[] loadedState = entry.getLoadedState();

		int[] dirtyProperties = session.getInterceptor().findDirty(entity, id,
				values, loadedState, persister.getPropertyNames(),
				persister.getPropertyTypes());

		event.setDatabaseSnapshot(null);

		final boolean interceptorHandledDirtyCheck;
		boolean cannotDirtyCheck;

		if (dirtyProperties == null) {
			// Interceptor returned null, so do the dirtycheck ourself, if
			// possible
			interceptorHandledDirtyCheck = false;

			cannotDirtyCheck = loadedState == null; // object loaded by update()
			if (!cannotDirtyCheck) {
				// dirty check against the usual snapshot of the entity
				dirtyProperties = persister.findDirty(values, loadedState,
						entity, session);
			} else if (entry.getStatus() == Status.DELETED
					&& !event.getEntityEntry().isModifiableEntity()) {
				// A non-modifiable (e.g., read-only or immutable) entity needs
				// to be have
				// references to transient entities set to null before being
				// deleted. No other
				// fields should be updated.
				if (values != entry.getDeletedState()) {
					throw new IllegalStateException(
							"Entity has status Status.DELETED but values != entry.getDeletedState");
				}
				// Even if loadedState == null, we can dirty-check by comparing
				// currentState and
				// entry.getDeletedState() because the only fields to be updated
				// are those that
				// refer to transient entities that are being set to null.
				// - currentState contains the entity's current property values.
				// - entry.getDeletedState() contains the entity's current
				// property values with
				// references to transient entities set to null.
				// - dirtyProperties will only contain properties that refer to
				// transient entities
				final Object[] currentState = persister.getPropertyValues(
						event.getEntity(), event.getSession().getEntityMode());
				dirtyProperties = persister.findDirty(entry.getDeletedState(),
						currentState, entity, session);
				cannotDirtyCheck = false;
			} else {
				// dirty check against the database snapshot, if
				// possible/necessary
				final Object[] databaseSnapshot = getDatabaseSnapshot(session,
						persister, id);
				if (databaseSnapshot != null) {
					dirtyProperties = persister.findModified(databaseSnapshot,
							values, entity, session);
					cannotDirtyCheck = false;
					event.setDatabaseSnapshot(databaseSnapshot);
				}
			}
		} else {
			// the Interceptor handled the dirty checking
			cannotDirtyCheck = false;
			interceptorHandledDirtyCheck = true;
		}
		logDirtyProperties(id, dirtyProperties, persister,values,loadedState,event);

		event.setDirtyProperties(dirtyProperties);
		event.setDirtyCheckHandledByInterceptor(interceptorHandledDirtyCheck);
		event.setDirtyCheckPossible(!cannotDirtyCheck);

	}
	
	
	private void logDirtyProperties(Serializable id, int[] dirtyProperties, EntityPersister persister, Object[] values, Object[] loadedState,FlushEntityEvent event) {
		if(dirtyProperties != null && dirtyProperties.length > 0){
			final String[] allPropertyNames = persister.getPropertyNames();
			final String[] dirtyPropertyNames = new String[ dirtyProperties.length ];
			for ( int i = 0; i < dirtyProperties.length; i++ ) {
				dirtyPropertyNames[i] = allPropertyNames[ dirtyProperties[i]];
				Class<?> clazz = persister.getEntityMetamodel().getEntityType().getReturnedClass();
				if(AutoLogger.class.isAssignableFrom(clazz)){
					try {
						AutoLogger autoLogger = (AutoLogger) clazz.newInstance();
						Map<String, Class<?>> map = autoLogger.getFieldNames();
						Class<?> logCls =  map.get(dirtyPropertyNames[i]);
						if(logCls != null && AutoLog.class.isAssignableFrom(logCls)){
							String fieldName = dirtyPropertyNames[i];
							AutoLog log = (AutoLog) logCls.newInstance();
							LOG.info(PrincipalHolder.get());
							log.setChangedValue(getValueString(values[dirtyProperties[i]], event));
							log.setOperator(PrincipalHolder.get());
							log.setFieldName(fieldName);
							log.setUpdateTime(new Date());
							log.setOriginalValue(getValueString(loadedState[dirtyProperties[i]], event));
							log.setTargetId(id.toString());
							event.getSession().save(log);
						}
					} catch (InstantiationException e) {
						LOG.error(e.getMessage(),e);
					} catch (IllegalAccessException e) {
						LOG.error(e.getMessage(),e);
					}
				}
				
				LOG.trace((MessageHelper.infoString( persister.getEntityName(), id )+","+dirtyPropertyNames[i] +": "+loadedState[dirtyProperties[i]] +"->"+values[dirtyProperties[i]]));
			}
		}
	
	}
	
	private Object[] getDatabaseSnapshot(SessionImplementor session, EntityPersister persister, Serializable id) {
		if ( persister.isSelectBeforeUpdateRequired() ) {
			Object[] snapshot = session.getPersistenceContext()
					.getDatabaseSnapshot(id, persister);
			if (snapshot==null) {
				//do we even really need this? the update will fail anyway....
				if ( session.getFactory().getStatistics().isStatisticsEnabled() ) {
					session.getFactory().getStatisticsImplementor()
							.optimisticFailure( persister.getEntityName() );
				}
				throw new StaleObjectStateException( persister.getEntityName(), id );
			}
			else {
				return snapshot;
			}
		}
		else {
			EntityKey entityKey = new EntityKey( id, persister, session.getEntityMode() );
			return session.getPersistenceContext()
					.getCachedDatabaseSnapshot( entityKey ); 
		}
	}
	
	private String getValueString(Object obj,FlushEntityEvent event){
		if(Code.class.isAssignableFrom(obj.getClass())){
			Code code = Code.class.cast(obj);
			if(code.getName() == null){
				code = (Code) event.getSession().get(Code.class,code.getId());
			}
			return code.getName();
		} else {
			return obj == null ? null : obj.toString();
		}
	}
	
}
