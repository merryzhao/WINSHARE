package com.winxuan.ec.task.dao.sap;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.task.model.sap.SapOrder;
import com.winxuan.ec.task.service.sap.impl.SapOrderRowMapper;

/**
 * 
 * @author yangxinyi
 *
 */
@Component("sapDao")
public class SapDaoImpl implements Serializable, SapDao {

	private static final long serialVersionUID = -3453886800116798656L;
	private static final String FINDCOMPLEX_SQL="SELECT i.item ,p.listprice ,i.childnum,i.quantiy,ps.id "+
			" from interface_sap_complex i,product p,product_sale ps where i.item =ps.outerid and p.id=ps.product and p.complex=0 and ps.storagetype = 6001 and  ps.shop=1 and i.complex=?";
	
	@Autowired
	JdbcTemplate jdbcTemplateEcCore; 

	@Override
	public void sendOrderItems(Object[] params) {
		jdbcTemplateEcCore.update(INSERT_SAP_ORDER, params);
	}
	
	@Override
	public void sendReplenishmentItems(Object[] params) {
		jdbcTemplateEcCore.update(INSERT_INTERFACE_REPLENISHMENT, params);
	}
	
	public int sendReplenishmentItemsNew(List<MrProductFreeze> mrProductFreezes) {
		MessageFormat transferForm = new MessageFormat("(''{0}'', ''{1}'', ''{2}'', ''{3}'', ''{4}'', ''{5}''),"); 
		
		StringBuffer transferStringBuffer = new StringBuffer();
		transferStringBuffer.append(SapDao.NEW_INSERT_INTERFACE_REPLENISHMENT);
		
		int num = 0;
		for (MrProductFreeze mrProductFreeze : mrProductFreezes){
			/**
			 * 如果商品的补货数量不大于0，则不下传到SAP
			 */
			if(mrProductFreeze.getQuantity() > 0){
				boolean isComplex= mrProductFreeze.getProductSale().getProduct().isPhysicalComplex();
				/*if(isComplex){
					  List<Map<String,Object>> complexitem= jdbcTemplateEcCore.queryForList(FINDCOMPLEX_SQL,mrProductFreeze.getProductSale().getOuterId());
					  if(complexitem!=null&&complexitem.size()>0&&
							  complexitem.size()==Integer.parseInt(complexitem.get(0).get("childnum").toString())){
						 for(Map<String,Object> vo:complexitem){
							 transferStringBuffer.append(transferForm.format(getTransferByComplexItem(mrProductFreeze,vo)));	 
							 num++;
						 }
					  }
				}else{
					transferStringBuffer.append(transferForm.format(getTransferArgs(mrProductFreeze)));	
					num++;
				}*/
				if(!isComplex){
					transferStringBuffer.append(transferForm.format(getTransferArgs(mrProductFreeze)));	
					num++;
				}
			}
		}
		jdbcTemplateEcCore.update(transferStringBuffer.substring(0, transferStringBuffer.length()-1));
		return num;
	}
	
	public void batchUpdateMrProductFreezeFlag(final List<MrProductFreeze> mrProductFreezes) {
		jdbcTemplateEcCore.batchUpdate(SapDao.UPDATE_MR_FREEZE_FLAG,
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, mrProductFreezes.get(i).getId().toString());
				}
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return mrProductFreezes.size();
				}  
			});
	}
	
	/**
	 * 接口表写入完成后，将标志位更新为C
	 */
	@Override
	public void updateReplenishmentItems(){
		jdbcTemplateEcCore.update(UPDATE_INTERFACE_REPLENISHMENT);
	}
	
	@Override
	public void sendSapBillItems(Object[] params) {
		jdbcTemplateEcCore.update(INSERT_BILL_SETTLE, params);
	}
	
	@Override
	public List<String> listSapOrderDelivery() {
		return jdbcTemplateEcCore.queryForList(FIND_SAP_DELIVERY, String.class);
	}
	
	@Override
	public List<SapOrder> listSapOrderItemDelivery(String orderId) {
		return jdbcTemplateEcCore.query(FIND_SAP_ORDER, new Object[]{orderId}, new SapOrderRowMapper());
	}
	
	@Override
	public boolean sapDoCancel(String orderId){
		return jdbcTemplateEcCore.queryForInt(FIND_SAP_STOCK, new Object[]{orderId}) > 0 ? false : true;
	}
	
	@Override
	public void updateOrderItem(Long orderItemId, int deliveryQuantity) {
		jdbcTemplateEcCore.update(UPDATE_ORDERITEM_DELIVERYQUANTITY, new Object[]{orderItemId,deliveryQuantity});
	}
	
	@Override
	public void updateSapOrderIflag(String orderId) {
		jdbcTemplateEcCore.update(UPDATE_SAP_DELIVERY_IFLAG, new Object[]{orderId});
	}

	private Object[] getTransferArgs(MrProductFreeze mrProductFreeze) {
		// TODO Auto-generated method stub
		/**
		 * 接口表interface_replenishment的outerorder字段是由日期和库位拼接而成
		 */
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Object[] transferArgs = new Object[6];
		transferArgs[0] = dateFormat.format(date)+ mrProductFreeze.getDc().getId();
		transferArgs[1] = mrProductFreeze.getProductSale().getId().toString();
		transferArgs[2] = mrProductFreeze.getDc().getName();
		transferArgs[3] = mrProductFreeze.getProductSale().getOuterId();
		transferArgs[4] = String.valueOf(mrProductFreeze.getQuantity());
		transferArgs[5] = "T";
		return transferArgs;
	}
	private Object[] getTransferByComplexItem(MrProductFreeze mrProductFreeze,Map<String,Object> vo){
		/**
		 * 接口表interface_replenishment的outerorder字段是由日期和库位拼接而成
		 */
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Object[] transferArgs = new Object[6];
		transferArgs[0] = dateFormat.format(date)+ mrProductFreeze.getDc().getId();
		transferArgs[1] = vo.get("id").toString();
		transferArgs[2] = mrProductFreeze.getDc().getName();
		transferArgs[3] = vo.get("item");
		transferArgs[4] = String.valueOf(mrProductFreeze.getQuantity()*(Integer)vo.get("quantiy"));
		transferArgs[5] = "T";
		return transferArgs;
	}
}