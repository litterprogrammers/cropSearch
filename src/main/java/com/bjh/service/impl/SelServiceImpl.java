
package com.bjh.service.impl;
import com.bjh.mapper.SelMapper;
import com.bjh.pojo.QueryCropData;
import com.bjh.service.SelService;
import com.bjh.pojo.CropInfo;
import com.bjh.pojo.CropsSpeciesInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class SelServiceImpl implements SelService {

    @Resource
    SelMapper selMapper;
    @Resource
    ComboPooledDataSource dataSource;


    /**
     *返回查询页面的查询条件
     * [PGB0001, 早金冠, ZAO JIN GUAN, 辽宁兴城, 中国农科院果树所, Rosaceae(蔷薇科), Malus(苹果属...
     * @param crop
     * @return
     */
    @Override
    public String[] getCropQuery(String crop) {
        //查询出来list
        List<String> strings = selMapper.selQueryCondition(crop+"ku");
        //封装成指定格式
        int dex = 0;
        String arr[] = new String[strings.size()];

        for (String o : strings) {
            arr[dex] = o;
            dex++;
        }

        return arr;
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public Map<String,Object> getQueryCountAndData(Map<String,Object> str) {
        /*输入
        {
           querySelect={属名=Malus(苹果属), 高程=<=240, 北纬=<3042, 生育期D=>=170, 生育期评价=[长, 中], 始果年龄=<>5},
           crop=APPLE,
           pageNumber=1
         }
         `属名`='Malus(苹果属)' and `高程`<=240 and `北纬`<3042 and `生育期D` >=170 and `生育期评价` in ('长','中') and `始果年龄`<>5
         加工
            {
             `属名` : ='Malus(苹果属)',
             `高程` : <=240,
             `北纬` : <3042,
             `生育期D` : >=170,
             `生育期评价` : in ('长','中'),
             `始果年龄` : <>5
             }
         */
            //
            Map<String,Object> querySelect = (Map)str.get("querySelect");






            //返回值是处理好的map集合
            Map<String,Object> map = new HashMap<>();
            //三大天王
            String tableName = (String)str.get("crop");

            String column = getColumnName(tableName);
            column = ChangFormat(column);
            Integer pageNumber =(Integer) str.get("pageNumber");





        for(String key : querySelect.keySet()){

                String value = querySelect.get(key).toString();
                if(value.contains(">") || value.contains("<") || value.contains("=")){
                    //是   "高程  <=240"    =>  " `高程`  <=240"

                    String innerKey = "`"+key+"`";
                    String innerValue = value;
                    map.put(innerKey,innerValue);
                }else if(value.contains(",")){
                    //是  "生育期评价  [长, 中]"  =>  "`生育期评价`  in ('长','中')"

                    String innerKey = "`"+key+"`";

                    String replace = value.replace("[", "");
                    String replace1 = replace.replace("]", "");


                    String[] split = replace1.split(",");


                    String innerValue = "in (" ;
                    int count = 0;
                    for (String s : split) {
                        s = s.trim();
                        if(count==0){
                            innerValue= innerValue+"'"+s+"'";
                        }else{
                            innerValue= innerValue+",'"+s+"'";
                        }
                        count++;
                    }

                    innerValue= innerValue+")";

                    map.put(innerKey,innerValue);
                }else{

                    Pattern reg = Pattern.compile("[0-9]*");

                    Matcher matcher = reg.matcher(value);
                    if(matcher.matches()){
                        //是数字的话  属名   123  =>   `属名`  =123
                        String innerKey = "`"+key+"`";
                        String innerValue = "="+value;
                        map.put(innerKey,innerValue);
                    }else{
                        //不是数字  属名   Malus(苹果属)  =>   `属名`  ='Malus(苹果属)'
                        String innerKey = "`"+key+"`";
                        value = value.trim();
                        String innerValue = "='"+value+"'";
                        map.put(innerKey,innerValue);
                    }
                }
            }

        QueryCropData queryCropData = new QueryCropData(map, tableName, column, pageNumber);
        System.out.println(queryCropData);
        //到数据库获取值列
        String s[] = selMapper.selQueryCropData(queryCropData).split(",");
        Integer count =  selMapper.selQueryTotalPageCount(queryCropData);
        HashMap<String, Object> returnHashMap = new HashMap<>();


        returnHashMap.put("data",s);
        returnHashMap.put("count",count);




        return returnHashMap;
    }



    /**
     * 返回种类列表
     * @param
     * @return List [
                     List[String种类名称,String图片地址,map[[String作物1,数据库名], [String作物1,数据库名]。。。。。。],
                     List[String种类名称,String图片地址,map[[String作物1,数据库名], [String作物1,数据库名]。。。。。。]，，
                 ]返回前端解析
     */
    @Override
    public List<List> getAllCropList() {

        //list[String 种类名称，String 图片地址，String 数据库名称]
        List<CropsSpeciesInfo> infos = selMapper.selSpecies();

        //构建最外层list
        List<List> outer = new ArrayList();
        //循环构建内部内容
        for (CropsSpeciesInfo info : infos) {

            //构造内层list
            List inner = new ArrayList();

            //加入String种类名称,String图片地址和map集合
            inner.add(info.getName());
            inner.add(info.getImage());
            inner.add(getMapper(info));
            //将封装的信息加到外部list
            outer.add(inner);
        }

        return outer;

    }

    /**
     * 返回对应数据库列名封装
     * @param cropName
     * @return ist[int pageCount, List[String 库编号 , String统一编号。。。]]
     */
    @Override
    public List getCropNameList(String cropName) {

        //获取总数量
        Integer pageCount = selMapper.selTotalPageCount(cropName);
        String columnName = getColumnName(cropName);


        List s = new ArrayList();
        s.add(pageCount);
        s.add(columnName);

        return s;
    }

    /**
     * 根据列名查询第pageNumber页的内容
     * @param crop
     * @param pageNumber
     * @return
     */
    public List<String> getCropList(String crop,int pageNumber){

        String columnName = getColumnName(crop);
        columnName = ChangFormat(columnName);
        Map<String,String> m2 = new HashMap();
        m2.put("tableName",crop);
        m2.put("colName",columnName);
        m2.put("pageNumber",String.valueOf(pageNumber-1));
        //根据列名查询第pageNumber页的内容
        String data = selMapper.selCropData(m2);
        String dataArr[] = data.split(",");
        List result = new ArrayList();

        for (String s : dataArr) {
            result.add(s);
        }


        return result;
    }

    /**
     * !工具类
     * 返回列名
     * @param cropName
     * @return
     * 统一编号,品种名称,译名,原产地,保存单位,科名,属名。。。。。
     */
    private String getColumnName(String cropName){
        //获取数据库名
        String databaseName = getDatabaseName(dataSource.getJdbcUrl());
        Map<String,String> m = new HashMap<>();
        m.put("tableName",cropName);
        m.put("databaseName",databaseName);
        //获取列明名
        String columnName = selMapper.selColumnName(m);

        return columnName;
    }
    /**
     * !!工具类
     *  输入jdbc:mysql://localhost:3306/crops?useSSL=false&serverTimezone=UTC
     * @param s
     * @return 数据库名 crops
     */
    private String getDatabaseName(String s){
        //jdbc:mysql://localhost:3306/crops?useSSL=false&serverTimezone=UTC
        int i = s.indexOf("?");

        s = s.substring(0,i);
        int i1 = s.lastIndexOf("/");
        s = s.substring(i1+1);
        return s;
    }
    /**
     *   ！！工具类
     *   将查询的作物类型数据库名找出并根据此数据库名查询对应信息，封装成如下格式
     *   @param
     *   @return
            List[map[[String作物1,数据库名], [String作物1,数据库名]......]
     */
    private Map<String,String> getMapper(CropsSpeciesInfo info) {
        //创建map集合
        Map<String,String> mapList = new LinkedHashMap<>();
        //到数据库查询数据
        List<CropInfo> cropInfos = selMapper.selCrop(info.getDatabase());
        //封装
        for (CropInfo cropInfo : cropInfos) {
            mapList.put(cropInfo.getName(),cropInfo.getDatabaseData());
        }

        return mapList;
    }
    /**
     * ！！工具类
     *  将需要查询的列名封装成如下格式
     * @param s
     * @return
         ifnull(统一编号,"null"),",",ifnull(叶色,"null")。。。。。。)形式
     */
    private String ChangFormat(String s){
        StringBuffer ss = new StringBuffer();
        String[] split = s.split(",");
        int count = 0;
        for (String s1 : split) {
            String s3=null;
            if(count==0){
                s3 = "ifnull("+s1+",\"null\")";
                count++;
            }else{
                s3 = " ,\",\",ifnull("+s1+",\"null\")";
            }
            ss.append(s3);

        }
        return ss.toString();
    }
}
