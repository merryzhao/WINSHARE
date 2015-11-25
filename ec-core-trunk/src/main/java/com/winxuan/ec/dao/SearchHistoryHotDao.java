package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.search.SearchHistoryHot;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 热搜DAO
 * @author juqkai(juqkai@gmail.com)
 */
public interface SearchHistoryHotDao {
    /**
     * 所有用户添加的
     * @return
     */
    @Query("from SearchHistoryHot hot where hot.index is not null order by index")
    List<SearchHistoryHot> listUserHot();
    
    /**
     * 所有系统生成的
     * @return
     */
    @Query("from SearchHistoryHot hot where hot.index is null")
    List<SearchHistoryHot> listSearchHot();
    
    @Save
    void save(SearchHistoryHot searchHot);
    
    @Delete
    void del(SearchHistoryHot h);
    
    @Query("from SearchHistoryHot hot")
    @Conditions({
        @Condition("hot.index = :index")
        ,@Condition("hot.keyword = :keyword")
        ,@Condition("hot.mustshow = :mustshow")
    })
    SearchHistoryHot find(@ParameterMap Map<String, Object> parameters);
    
    @Query("from SearchHistoryHot hot where index = ?")
    List<SearchHistoryHot> findForIndex(int index);

    @Query("from SearchHistoryHot hot where keyword = ?")
    List<SearchHistoryHot> findForKeyword(String keyword);
}
