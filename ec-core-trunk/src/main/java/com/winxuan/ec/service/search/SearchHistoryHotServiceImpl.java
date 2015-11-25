package com.winxuan.ec.service.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SearchHistoryHotDao;
import com.winxuan.ec.model.search.SearchHistoryHot;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 热搜
 * @author juqkai(juqkai@gmail.com)
 */
@Service("searchHistoryHotServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class SearchHistoryHotServiceImpl implements SearchHistoryHotService{
    @InjectDao
    private SearchHistoryHotDao searchHistoryHotDao;
    
    /**
     * 用户添加的热搜
     * @return
     */
    public List<SearchHistoryHot> listUserHot(){
        return searchHistoryHotDao.listUserHot();
    }
    /**
     * 搜索自生成的热搜
     * @return
     */
    public List<SearchHistoryHot> listSearchHot(){
        return searchHistoryHotDao.listSearchHot();
    }
    
    public void save(SearchHistoryHot hot){
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("index", hot.getIndex());
        //清除相同index与keyword的数据, 然后保存, 嘿嘿
        delForIndex(hot.getIndex());
        delForKeyword(hot.getKeyword());
        
        searchHistoryHotDao.save(hot);
    }
    
    public void delForIndex(int index){
        del(searchHistoryHotDao.findForIndex(index));
    }
    public void delForKeyword(String keyword){
//        List<SearchHistoryHot>  a = searchHistoryHotDao.findForKeyword(keyword);
        del(searchHistoryHotDao.findForKeyword(keyword));
        
    }
    
    private void del(List<SearchHistoryHot> hots){
        for(SearchHistoryHot hot : hots){
            searchHistoryHotDao.del(hot);
        }
    }
}
