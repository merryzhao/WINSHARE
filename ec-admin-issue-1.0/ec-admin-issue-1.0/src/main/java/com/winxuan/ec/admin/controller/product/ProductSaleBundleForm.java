package com.winxuan.ec.admin.controller.product;

/**
 * 
 * @author juqkai(juqkai@gmail.com)
 */
public class ProductSaleBundleForm {
    private int queryType;
    private String codingType;
    private String codingContent;
    public int getQueryType() {
        return queryType;
    }
    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }
    public String getCodingContent() {
        return codingContent;
    }
    public void setCodingContent(String codingContent) {
        if("".equals(codingContent.trim())){
            this.codingContent = null;
        }
        this.codingContent = codingContent;
    }
    public String getCodingType() {
        return codingType;
    }
    public void setCodingType(String codingType) {
        if("".equals(codingType.trim())){
            this.codingType = null;
        }
        this.codingType = codingType;
    }
}
