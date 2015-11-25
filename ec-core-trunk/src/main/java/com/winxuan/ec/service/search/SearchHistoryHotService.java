package com.winxuan.ec.service.search;

import java.util.List;

import com.winxuan.ec.model.search.SearchHistoryHot;

/**
 * 执搜
 * @author juqkai(juqkai@gmail.com)
 */
public interface SearchHistoryHotService {
    /**
     * 用户添加的热搜
     * @return
     */
    List<SearchHistoryHot> listUserHot();
    /**
     * 搜索自生成的热搜
     * @return
     */
    List<SearchHistoryHot> listSearchHot();
    
    void save(SearchHistoryHot hot);
    /**
     * 根据index删除热搜
     * @param index
     */
    void delForIndex(int index);
}
