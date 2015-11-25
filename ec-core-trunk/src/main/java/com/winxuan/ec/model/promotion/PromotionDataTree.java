package com.winxuan.ec.model.promotion;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-18 下午1:40:35 --!
 * @description:促销数据,该数据从excel导入,故没有和相关实体做关联映射,用于支持业务做各渠道做促销活动的页面
 ******************************** 
 */

@Entity
@Table(name = "promotion_data_tree")
public class PromotionDataTree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(name = "index_")
    private int index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private PromotionDataTree parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = PromotionDataTree.class)
    @OrderBy("index")
    private Set<PromotionDataTree> children;

    //数据量不大.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venueTree", targetEntity = PromotionData.class)
    @OrderBy("index")
    private Set<PromotionData> promotionDataList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public PromotionDataTree getParent() {
        return parent;
    }

    public void setParent(PromotionDataTree parent) {
        this.parent = parent;
    }

    public Set<PromotionDataTree> getChildren() {
        return children;
    }

    public void setChildren(Set<PromotionDataTree> children) {
        this.children = children;
    }

    public Set<PromotionData> getPromotionDataList() {
        return promotionDataList;
    }

    public void setPromotionDataList(Set<PromotionData> promotionDataList) {
        this.promotionDataList = promotionDataList;
    }

    public void addChildren(PromotionDataTree promotionDataTree) {
        if (CollectionUtils.isEmpty(this.children)) {
            this.children = new HashSet<PromotionDataTree>();
        }
        this.children.add(promotionDataTree);
    }

    public PromotionDataTree findNode(PromotionDataTree promotionDataTree, String nodeName) {
        if (promotionDataTree.getName().equals(nodeName)) {
            return promotionDataTree;
        }
        Set<PromotionDataTree> childList = promotionDataTree.getChildren();
        if (CollectionUtils.isNotEmpty(childList)) {
            for (PromotionDataTree pdt : childList) {
                PromotionDataTree result = this.findNode(pdt, nodeName);
                if (result != null) {
                    return result;
                }

            }
        }
        return null;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PromotionDataTree [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
