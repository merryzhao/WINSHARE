/*
 * @(#)PresentCardIdGenerator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.presentcard;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.support.util.AbstrctSequenceIdentifierGenerator;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-9-6
 */
public class PresentCardIdGenerator extends AbstrctSequenceIdentifierGenerator {

	@Override
	public synchronized Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		int iPrimaryKey;
		try {
			iPrimaryKey = sequelize(session);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		String sPrimaryKey=converse(iPrimaryKey);
		if(obj instanceof PresentCard){
			return assemble(sPrimaryKey, ((PresentCard)obj).getType());
		}
		return assemble(sPrimaryKey);
	}


	public String assemble(String primaryKey, Code type) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yy");
		String t = "";
		if(type.getId().equals(Code.PRESENT_CARD_TYPE_ELECTRONIC)){
			t = "1";
		}else if(type.getId().equals(Code.PRESENT_CARD_TYPE_PHYSICAL)){
			t = "2";
		}
		final int length = 3;
		String primaryKeyTemp = t + dateFormat.format(date) + primaryKey + RandomCodeUtils.create(RandomCodeUtils.MODE_NUMBER, length);
		int sum = 0;
		for(int i=0; i<primaryKeyTemp.length(); i++) {
			char tempChar = primaryKeyTemp.charAt(i);
			int tempInt = tempChar - '0';
			if((i+1) % 2 == 0){
				sum = sum + tempInt * 1;
			} else {
				sum = sum + tempInt * 2;
			}
		}
		final int divisor = 10;
		primaryKey = primaryKeyTemp + (sum % divisor);
		return primaryKey;
	}


	@Override
	public String assemble(String primaryKey) {
		return primaryKey;
	}
	
}
