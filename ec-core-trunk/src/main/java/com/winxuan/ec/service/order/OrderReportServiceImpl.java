package com.winxuan.ec.service.order;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 订单报表定制
 * @author heyadong
 * @version 1.0, 2012-8-13 下午03:19:18
 */
@Service("orderReportService")
public class OrderReportServiceImpl implements OrderReportService , InitializingBean{

	@InjectDao
	private OrderDao orderDao;
	
	
	private Map<String, String> channelMap; 
	
	

	
	public String getCodeId(String username) {
		String code = channelMap.get(username);
		return code == null ? "" : code;
	}
	
	@Override
	public void generateOrderDeliveryReport(String path,
			Map<String, Object> parameters, Short sort) throws Exception {
		Pagination pagination = new Pagination();
		int pagesize = MagicNumber.PAGE_SIZE_200;
		pagination.setPageSize(pagesize);
		File file = new File(path);
		WritableWorkbook workbook = Workbook.createWorkbook(file);
		
		WritableFont blodFont = new WritableFont(WritableFont.createFont("宋体"), MagicNumber.ELEVEN,
				WritableFont.BOLD,false,
				UnderlineStyle.NO_UNDERLINE);
		
		
		//标题-对齐方式中
		WritableCellFormat titleCell = new WritableCellFormat();
		titleCell.setBorder(Border.ALL,BorderLineStyle.THIN);
		titleCell.setFont(blodFont);
		titleCell.setAlignment(Alignment.CENTRE);
		
		//标题-对齐方式左
		WritableCellFormat titleCellLeft = new WritableCellFormat();
		titleCellLeft.setBorder(Border.ALL,BorderLineStyle.THIN);
		titleCellLeft.setFont(blodFont);
		
		//普通
		WritableCellFormat normalCell = new WritableCellFormat();
		normalCell.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		//商品名 
		WritableCellFormat productNameCell = new WritableCellFormat();
		productNameCell.setBorder(Border.ALL, BorderLineStyle.THIN);
		productNameCell.setWrap(true);
		
		boolean hasOrder = false;
		
		
		do{
			List<Order> orders = new ArrayList<Order>();
			 if (parameters.get("meta") == null && parameters.get("nmeta")==null) {
				 orders = orderDao.findOrderWithMeta(parameters, sort, pagination);
			 } else {
				 orders = orderDao.findOrderWithMeta1(parameters, sort, pagination);
			 }
			int i = 0;
			for(Order order : orders) {
				WritableSheet sheet = workbook.createSheet(order.getId(), i++);
				sheet.getSettings().setScaleFactor(100);
				sheet.addCell(new Label(MagicNumber.ZERO, MagicNumber.ZERO,"供应商代码", titleCellLeft));

				sheet.addCell(new Label(MagicNumber.ONE, MagicNumber.ZERO,getCodeId(order.getCustomer().getName()), titleCell));
				
				sheet.addCell(new Label(MagicNumber.ZERO, MagicNumber.ONE,"供应商名称", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE , MagicNumber.ONE ,"四川文轩在线电子商务有限公司", titleCell));
				
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.TWO,"客户采购单号", titleCellLeft));
				/**
				 * 针对当当JIT渠道，客户采购单号在原有外部单号的基础上去掉-1、-2等后缀后加上"-EDI"
				 */
				if (order.getChannel().getId().equals(Channel.CHANNEL_ID_DANGDANG_JIT)){
					String outerId = order.getOuterId(); 
					int index = outerId.indexOf("-");
					outerId = outerId.substring(0, index + 1) + "EDI";
					sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.TWO, outerId, titleCell));
				}
				else{
					sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.TWO, order.getOuterId(), titleCell));
				}
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.THREE, "供应商发货单号", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.THREE, order.getId(), titleCell));
				
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.FOUR, "包件数", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.FOUR, order.getPackages(), titleCell));
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.FIVE, "总册数", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.FIVE, order.getDeliveryQuantity() + "", titleCell));
				
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.SIX, "品种数", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.SIX, order.getDeliveryKind() + "", titleCell));
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.SEVEN, "总码洋", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.SEVEN, order.getDeliveryListPrice().toString(), titleCell));
				
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.EIGHT, "总实洋", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.EIGHT, order.getDeliverySalePrice().toString(), titleCell));
				
				
				// cell, row, cell, row
				//merge
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.ZERO, MagicNumber.FIVE, MagicNumber.ZERO);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.ONE, MagicNumber.FIVE, MagicNumber.ONE);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.TWO, MagicNumber.FIVE, MagicNumber.TWO);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.THREE, MagicNumber.FIVE, MagicNumber.THREE);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.FOUR, MagicNumber.FIVE, MagicNumber.FOUR);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.FIVE, MagicNumber.FIVE, MagicNumber.FIVE);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.SIX, MagicNumber.FIVE, MagicNumber.SIX);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.SEVEN, MagicNumber.FIVE, MagicNumber.SEVEN);
				sheet.mergeCells(MagicNumber.ONE, MagicNumber.EIGHT, MagicNumber.FIVE, MagicNumber.EIGHT);
				
				
			
				sheet.addCell(new Label(MagicNumber.ZERO,MagicNumber.NINE, "商品编号", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE,MagicNumber.NINE, "商品名称", titleCell));
				sheet.addCell(new Label(MagicNumber.TWO,MagicNumber.NINE, "定价", titleCell));
				sheet.addCell(new Label(MagicNumber.THREE,MagicNumber.NINE, "数量", titleCell));
				sheet.addCell(new Label(MagicNumber.FOUR, MagicNumber.NINE, "码洋", titleCell));
				sheet.addCell(new Label(MagicNumber.FIVE, MagicNumber.NINE, "实洋", titleCell));
				int row = MagicNumber.TEN;
				for (OrderItem oi : order.getItemList()) {
					ProductSale ps = oi.getProductSale();
					Product p = ps.getProduct();
					
					sheet.addCell(new Label(MagicNumber.ZERO, row, p.getBarcode() + "", normalCell));
				
					sheet.addCell(new Label(MagicNumber.ONE, row, p.getName(), productNameCell));
				
					sheet.addCell(new Label(MagicNumber.TWO, row, p.getListPrice().toString(), normalCell));
			
					sheet.addCell(new Label(MagicNumber.THREE, row, oi.getDeliveryQuantity() + "", normalCell));
					
					sheet.addCell(new Label(MagicNumber.FOUR, row, p.getListPrice().multiply(new BigDecimal(oi.getDeliveryQuantity())).toString(),  normalCell));
				
					sheet.addCell(new Label(MagicNumber.FIVE, row, oi.getDeliveryBalancePrice().toString(), normalCell));
//					
					row++;
				}
				
				//结尾
				sheet.addCell(new Label(MagicNumber.ZERO, row, "合计", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.ONE, row, "", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.TWO, row, "" ,titleCellLeft));
				sheet.addCell(new Label(MagicNumber.THREE, row, order.getDeliveryQuantity() + "", titleCellLeft));
				sheet.addCell(new Label(MagicNumber.FOUR, row,order.getDeliveryListPrice().toString(),  titleCellLeft));
				sheet.addCell(new Label(MagicNumber.FIVE, row,order.getDeliverySalePrice().toString(), titleCellLeft));
				
				
				 
				sheet.setColumnView(MagicNumber.ZERO, MagicNumber.SIXTEEN + MagicNumber.ONE);
				sheet.setColumnView(MagicNumber.ONE, MagicNumber.THIRTY - MagicNumber.FOUR);
				sheet.setColumnView(MagicNumber.TWO, MagicNumber.NINE);
				sheet.setColumnView(MagicNumber.THREE, MagicNumber.SEVEN);
				sheet.setColumnView(MagicNumber.FOUR, MagicNumber.TEN + MagicNumber.ONE);
				sheet.setColumnView(MagicNumber.FIVE, MagicNumber.TEN + MagicNumber.ONE);
				
				hasOrder = true;
			}
			
			if(pagination.canGoNext()) {
				pagination.setCurrentPage(pagination.next());
			} else {
				break;
			}
		} while(true);
		
		if(!hasOrder) {
			WritableSheet sheet = workbook.createSheet("订单数量为空",MagicNumber.ZERO);
			sheet.getSettings().setScaleFactor(100);
			sheet.addCell(new Label(MagicNumber.ZERO, MagicNumber.ZERO,"请填写包件数", titleCell));
		}
		
		workbook.write();
		workbook.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		channelMap = new HashMap<String, String>();
		channelMap.put("卓越供货", "AAYMZ");
		channelMap.put("卓越供货_EDI", "EIOYS");
		channelMap.put("苏宁易购", "10032966");		
		/**
		 * 针对当当JIT渠道，供应商代码修改为：16101
		 */
		channelMap.put("xhbs-system", "16101");
		
	}

}
