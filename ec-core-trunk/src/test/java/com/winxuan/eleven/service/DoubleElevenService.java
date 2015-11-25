package com.winxuan.eleven.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.winxuan.eleven.model.DoubleEleven;
import com.winxuan.eleven.model.DoubleElvenGroup;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-6 下午1:25:46 --!
 * @description:
 ******************************** 
 */
@Component("doubleElevenService")
public class DoubleElevenService {

    private static final String SQL_FINDALL = "select '' vid,'' vname,deg.id as gid,deg.name as gname ,de.ecid ,de.productname,de.saleprice,"
            + "de.totalsales,de.introduction,de.producturl,de.favoritelink,"
            + "de.imgurl from double_eleven de,double_eleven_group deg where deg.id = de.`group;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DoubleEleven> findAll() {
        List<DoubleEleven> result = jdbcTemplate.query(SQL_FINDALL, new RowMapper<DoubleEleven>() {
            @Override
            public DoubleEleven mapRow(ResultSet rs, int rowNum) throws SQLException {
                DoubleEleven de = new DoubleEleven();
                de.setEcId(rs.getInt("ecid"));
                de.setFavoriteLink(rs.getString("favoritelink"));
                de.setgId(rs.getInt("gid"));
                de.setgName(rs.getString("gname"));
                de.setProductName(rs.getString("productname"));
                de.setProductUrl(rs.getString("producturl"));
                de.setSalePrice(rs.getBigDecimal("saleprice"));
                de.setvId(rs.getInt("vid"));
                de.setvName(rs.getString("vname"));
                de.setImagUrl(rs.getString("imgurl"));
                de.setTotalSales(rs.getInt("totalsales"));
                de.setIntroduction(rs.getString("introduction"));
                return de;
            }
        });
        return result;
    }

    public DoubleElvenGroup getGroupById(int id) {
        String sql = "select * from double_eleven_group  deg where deg.id = ?";
        DoubleElvenGroup result = this.jdbcTemplate.queryForObject(sql, new Object[] { id },
                new RowMapper<DoubleElvenGroup>() {
                    @Override
                    public DoubleElvenGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        DoubleElvenGroup de = new DoubleElvenGroup();
                        de.setId(rs.getInt(1));
                        de.setName(rs.getString(2));
                        return de;
                    }
                });
        return result;
    }

    public List<DoubleEleven> findAllByGid(int id) {
        List<DoubleEleven> allDoubleEleven = this.findAll();
        List<DoubleEleven> result = new ArrayList<DoubleEleven>();
        for (DoubleEleven doubleEleven : allDoubleEleven) {
            if (id == doubleEleven.getgId()) {
                result.add(doubleEleven);
            }
        }
        return result;
    }

    public List<DoubleEleven> findAllByVid(int id) {
        List<DoubleEleven> allDoubleEleven = this.findAll();
        List<DoubleEleven> result = new ArrayList<DoubleEleven>();
        for (DoubleEleven doubleEleven : allDoubleEleven) {
            if (id == doubleEleven.getvId()) {
                result.add(doubleEleven);
            }
        }
        return result;
    }

}
