package com.winxuan.ec.task.dao.robot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.robot.RobotProduct;
import com.winxuan.ec.task.model.robot.RobotProductCategoryLog;

/**
 * 实现类
 * @author Heyadong
 * @version 1.0, 2012-3-28
 */
@Repository("robotProductDao")
public class RobotProductDaoImpl implements RobotProductDao {

	private static final long serialVersionUID = 7614073625273903340L;
	@Autowired
	private JdbcTemplate jdbcTemplateRobot;

	@Override
	public List<RobotProduct> findProductByBarcode(String[] barcodes) {
		if(barcodes != null && barcodes.length > 0) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT p.barcode,GROUP_CONCAT(pc.category) AS 'robots' FROM product p")
			.append(" INNER JOIN product_category pc ON (p.id = pc.product)")
			.append(" WHERE p.source = 2 AND p.barcode IN (");
			for (int i = 0; i < barcodes.length; i++){
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
			sql.append(" GROUP BY p.barcode");
			return jdbcTemplateRobot.query(sql.toString(), barcodes, new RowMapper<RobotProduct>(){
				@Override
				public RobotProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
					RobotProduct product = new RobotProduct();
					product.setBarcode(rs.getString("barcode"));

					String[] robots = rs.getString("robots").split(",");
					List<Long> robotsId = new ArrayList<Long>(robots.length);
					for (int i = 0 ; i < robots.length; i++){
						robotsId.add(Long.valueOf(robots[i]));
					}
					product.setRobots(robotsId);
					return product;
				}
			});
		}
		return null;
	}

    @Override
    public List<RobotProductCategoryLog> productCategoryUnprocessedLog(int limit) {
        String sql = String.format("SELECT id,product,barcode,category,status,createdate FROM product_category_increase WHERE status=%s LIMIT %s",RobotProductCategoryLog.STATUS_UNPROCESSED, limit);
        List<RobotProductCategoryLog> list = jdbcTemplateRobot.query(sql, new RowMapper<RobotProductCategoryLog>(){
            @Override
            public RobotProductCategoryLog mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                RobotProductCategoryLog log = new RobotProductCategoryLog();
                log.setId(rs.getLong("id"));
                log.setProductId(rs.getLong("product"));
                log.setBarcode(rs.getString("barcode"));
                log.setStatus(rs.getInt("status"));
                return log;
            }
            
        });
        return list;
    }

    @Override
    public void updateLogStatus(List<RobotProductCategoryLog> list, int status) {
        if (!list.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE product_category_increase SET status = ").append(status);
            sql.append(" WHERE id IN (");
                for (RobotProductCategoryLog log : list) {
                        sql.append(log.getId()).append(",");
                }
                sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
            jdbcTemplateRobot.update(sql.toString());
        }
    }
}
