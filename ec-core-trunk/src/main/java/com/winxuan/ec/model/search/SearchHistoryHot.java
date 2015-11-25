package com.winxuan.ec.model.search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 热搜
 * @author juqkai(juqkai@gmail.com)
 */
@Entity
@Table(name = "search_history_hot")
public class SearchHistoryHot {
    
    @Id
    private String keyword;
    
    @Column(name = "qty")
    private int qty;
    
    @Column(name = "mustshow")
    private boolean mustshow;
    
    @Column(name = "`index`")
    private Integer index;
    
    @Column(name="href")
    private String href;
    
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }
    public boolean isMustshow() {
        return mustshow;
    }
    public void setMustshow(boolean mustshow) {
        this.mustshow = mustshow;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
}
