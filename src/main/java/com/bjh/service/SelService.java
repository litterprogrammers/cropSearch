package com.bjh.service;

import java.util.List;

public interface SelService {

    //返回种类的list集合
    List<List> getAllCropList();

    //返回列明
    List getCropNameList(String cropName);

    //返回一条数据
    List<String> getCropList(String crop, int pageNumber);
}