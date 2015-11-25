package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.replenishment.MrDeliveryRecord;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * @author yangxinyi
 *
 */
public interface MrDeliveryRecordDao {

	@Query("from MrDeliveryRecord mdr ")
	@Conditions({ 
			@Condition("mdr.productSale.id = :productSale"),
			@Condition("mdr.deliveryDate = :deliveryDate"),
			@Condition("mdr.channel.id = :channel"), 
			@Condition("mdr.dc.id = :dc")
	})
	List<MrDeliveryRecord> find(@ParameterMap Map<String, Object> parameters);
	
	@Save
	void save(MrDeliveryRecord mrDeliveryRecord);
	
	@Update
	void update(MrDeliveryRecord mrDeliveryRecord);
	
	@SaveOrUpdate
	void saveOrUpdate(MrDeliveryRecord mrDeliveryRecord);
	
	@Get
	MrDeliveryRecord get(Long id);
	
}
