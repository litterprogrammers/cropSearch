package com.bjh.service;

import java.util.List;
import java.util.Map;

public interface SelService {

    //返回种类的list集合
    List<List> getAllCropList();

    //返回列明
    List getCropNameList(String cropName);

    //返回一条数据
    List<String> getCropList(String crop, int pageNumber);

    //返回查询结构
    String[] getCropQuery(String crop);

    //返回条件查询的页总数和数据
    Map<String,String> getQueryCountAndData(Map<String,Object> str);
}