package com.bjh.pojo;

import java.util.Map;

public class QueryCropData {
    Map<String,Object> querySelector;
    String tableName  ;
    String colName ;
    Integer pageNumber;

    public QueryCropData(Map<String, Object> querySelector, String tableName, String colName, Integer pageNumber) {
        this.querySelector = querySelector;
        this.tableName = tableName;
        this.colName = colName;
        this.pageNumber = pageNumber;
    }

    public Map<String, Object> getQuerySelector() {
        return querySelector;
    }

    public void setQuerySelector(Map<String, Object> querySelector) {
        this.querySelector = querySelector;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getcolName() {
        return colName;
    }

    public void setcolName(String colName) {
        this.colName = colName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "QueryCropData{" +
                "querySelector=" + querySelector +
                ", tableName='" + tableName + '\'' +
                ", colName='" + colName + '\'' +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
