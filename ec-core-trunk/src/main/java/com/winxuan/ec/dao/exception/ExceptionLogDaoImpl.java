package com.winxuan.ec.dao.exception;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.refund.RefundLog;
/**
 * 
 * @author youwen
 * 
 */
@Component("exceptionLogDao")
public class ExceptionLogDaoImpl implements ExceptionLogDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void save(RefundLog refundFailed) {
		hibernateTemplate.save(refundFailed);
	}
	
	@Override
	public List<RefundLog> get(String id) {
		String query = "from RefundLog where refund = ?";
		List<RefundLog> refunds = (List<RefundLog>)hibernateTemplate.find(query, id);
		return refunds;
	}
	
	
}
