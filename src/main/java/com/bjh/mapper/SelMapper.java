package com.bjh.mapper;

import com.bjh.pojo.CropInfo;
import com.bjh.pojo.CropsSpeciesInfo;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface SelMapper {
    //查询种质种类 SpeciesInfo{name='粮食作物', database='GrainCrope', image='grain.jpg'}
    List<CropsSpeciesInfo> selSpecies();

    //查询某个种类信息
    List<CropInfo> selCrop(@Param("name") String name);

    //查询列名
    String selColumnName(Map<String,String> map );

    //查询作物数据
    String selCropData(Map<String,String> map);

    //查询总共多少页数据
    Integer selTotalPageCount(@Param("databaseName")String databaseName);

    //查询筛选条件
    List<String> selQueryCondition(@Param("crop")String crop);

    //根据条件查询总页数
    Integer selQueryTotalPageCount(Map<String,String> map);

    //根据条件查询数据
    String selQueryCropData(Map<String,String> map);
}
