package com.winxuan.ec.service.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInfoMapper;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 快递
 * @author juqkai(juqkai@gmail.com)
 */
@Service("orderExpressService")
@Transactional(rollbackFor = Exception.class)
public class OrderExpressServiceImpl implements OrderExpressService{
	
	private  static final String FIND_ORDEREXPRESS_INFO = "SELECT oe.deliverycode, oe.deliveryquantity ,  oe.province AS province ,"
			+ " oe.city AS city , oe.deliverylistprice AS deliverylistprice , c.name AS processStatus , oe.weight AS weight "
			+ "FROM order_express oe, code c WHERE oe.processstatus = c.id and oe._order = ? AND oe.deliverycode = ? LIMIT 1";
	
	private  static final String FIND_ORDER_INFO = "SELECT o.id, o.deliverycode FROM	_order o WHERE o.deliverycompany = ? AND o.deliverytime BETWEEN ? AND ?";
    /**
     * 快递对账是否正在执行
     */
    private static boolean expressAccount = false;
    
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
    @Autowired
    private AttachService attachService; 
    
    @InjectDao
    private OrderDao orderDao;
    
    private JdbcTemplate jdbcTemplate;

   
    /**
     * 快递对账
     */
    @Async
    @Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
    public void expressToAccount(Employee employee, Map<String, List<String>> delivery, Map<String, Object> param, List<String> head, String name){
        try{
            expressAccount = true;
            
            List<List<String>> destData = findExpresses(param, delivery, head.size());
            List<String> destHead = new ArrayList<String>();
            destHead.addAll(head);
            destHead.addAll(makeHead());
            attachService.makeAttach(employee, "快递对账" + name, destHead, destData);
        }catch(Exception e){
        	e.getStackTrace();
        } 
        finally{
            expressAccount = false;
        }
    }
    
    /**
     * 是否能进行快递对账
     * @return
     */
    public boolean canExpressToAccount(){
        return expressAccount;
    }
    /**
     * 填充表头
     * @return 
     */
    private List<String> makeHead() {
        List<String> head = new ArrayList<String>();
        head.add("订单号");
        head.add("发货商品数量");
        head.add("区域");
        head.add("区域");
        head.add("发货码洋");
        head.add("订单状态");
        head.add("订单重量(克)");
        head.add("重复标记");
        return head;
    }
    
    /**
     * 填充数据
     * @param deliverycode 运单号
     * @param write
     * @param row
     * @param col
     * @return 
     */
    private List<String> writeProduct(List<Order> orders){
        Order order = orders.get(0);
        List<String> val = new ArrayList<String>();
        Map<String, Object> map = findOrderExpress(order.getId(), order.getDeliveryCode());
        val.add(order.getId().toString());
        val.add((String.valueOf(map.get("deliveryquantity"))));
        val.add(String.valueOf(map.get("province")));
        val.add(String.valueOf(map.get("city")));
        val.add(String.valueOf(map.get("deliverylistprice")));
        val.add(String.valueOf(map.get("processStatus")));
        val.add(String.valueOf(map.get("weight")));
        val.add(String.valueOf(orders.size()));
        return val;
    }
    
    /**
     * 根据条件读取出订单信息, 拆分时间条件
     * @param form
     * @return
     */
    private List<List<String>> findExpresses(Map<String, Object> param, Map<String, List<String>> delivery, int width){
        List<List<String>> destData = new ArrayList<List<String>>();
        Date start = (Date) param.get("startDeliveryTime");
        Date end = (Date) param.get("endDeliveryTime");
        Date partDate = DateUtils.addDays(start, MagicNumber.ONE);
        while (end.after(partDate)) {
        	 param.put("startDeliveryTime", start);
             param.put("endDeliveryTime", partDate);
             Map<String, List<Order>> orders = findOrderByExpress(param);
             mateItem(destData, delivery, orders, width);
             start = partDate;
             partDate = DateUtils.addDays(start,  MagicNumber.ONE);
		}
        if(!end.after(partDate)){
        	param.put("startDeliveryTime", start);
            param.put("endDeliveryTime", end);
            Map<String, List<Order>> orders = findOrderByExpress(param);
            mateItem(destData, delivery, orders, width);
        }
        for(List<String> item : delivery.values()){
            destData.add(item);
        }
        return destData;
    }
    
    /**
     * 匹配数据
     * @param destData
     * @param delivery
     * @param orders
     * @param width
     */
    private void mateItem(List<List<String>> destData, Map<String, List<String>> delivery, Map<String, List<Order>> orders, int width){
    	
        List<String> temp = new ArrayList<String>();
        for(String deliverycode : delivery.keySet()){
            if(orders.containsKey(deliverycode)){
                List<String> item = new ArrayList<String>();
                item.addAll(delivery.get(deliverycode));
                //填充空白
                for(int i = 0; i < width - item.size(); i ++){
                    item.add("");
                }
                item.addAll(writeProduct(orders.get(deliverycode)));
                destData.add(item);
                temp.add(deliverycode);
            }
        }
        for(String item : temp){
            delivery.remove(item);
        }
    }
    /**
     * 根据条件读取出订单信息
     * @param form
     * @return
     */
    @Deprecated
	private Map<String, List<Order>> findExpress(Map<String, Object> param){
        Map<String, List<Order>> os = new HashMap<String, List<Order>>();
        
        List<Object[]> orders = findForDeliveryCompany(param);
        if(orders.size() <= 0){
            return os;
        }
        for(Object[] objs : orders){
            if(!(objs[0] instanceof Order)){
                continue;
            }
            Order o = (Order) objs[0];
            if(os.get(o.getDeliveryCode()) == null){
                List<Order> od = new ArrayList<Order>();
                od.add(o);
                os.put(o.getDeliveryCode(), od);
                continue;
            }
            os.get(o.getDeliveryCode()).add(o);
        }
        return os;
    }
    
    @Deprecated
    public List<Object[]> findForDeliveryCompany(Map<String, Object> param) {
        return orderDao.findForDeliveryCompany(param);
    }
    
    private Map<String, List<Order>> findOrderByExpress(Map<String, Object> param){
    	Map<String, List<Order>> ordersmap = new HashMap<String, List<Order>>();
    	List<Order> orderList = findOrderInfo(param);
    	if(CollectionUtils.isEmpty(orderList)){
    		return ordersmap;
    	}
    	for (Order order : orderList) {
			if(ordersmap.get(order.getDeliveryCode()) == null){
				List<Order> newOrderList = new ArrayList<Order>();
				newOrderList.add(order);
				ordersmap.put(order.getDeliveryCode(), newOrderList);
				continue;
			}
			ordersmap.get(order.getDeliveryCode()).add(order);
		}
    	return ordersmap;
    }
    
	private List<Order> findOrderInfo(Map<String, Object> param){
		Long deliveryCompanyId = (Long)param.get("deliveryCompany");
    	String startDeliveryTime = dateFormat.format(param.get("startDeliveryTime"));
    	String endDeliveryTime = dateFormat.format(param.get("endDeliveryTime"));
    	Object[] paramObjs = {deliveryCompanyId , startDeliveryTime , endDeliveryTime};
    	return jdbcTemplate.query(FIND_ORDER_INFO, paramObjs, new OrderInfoMapper());
    }
    
    public Map<String, Object> findOrderExpress(String id, String deliveryCode){
		Map<String, Object> map = jdbcTemplate.queryForMap(FIND_ORDEREXPRESS_INFO, id , deliveryCode);
		if(map != null && !map.isEmpty()){
			return map;
		}
		return null;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
