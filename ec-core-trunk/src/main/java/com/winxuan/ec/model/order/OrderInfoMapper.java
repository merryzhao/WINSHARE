package com.winxuan.ec.model.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @Description:
 * @Copyright:四川文轩在线电子商务有限公司
 * @Date:2015年1月13日
 * @Time:下午1:13:04
 * @author QiuWei
 * @version 1.0
 */
public class OrderInfoMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Order  order = new Order();
		order.setId(rs.getString("id"));
		order.setDeliveryCode(rs.getString("deliverycode"));
		return order;
	}

}
