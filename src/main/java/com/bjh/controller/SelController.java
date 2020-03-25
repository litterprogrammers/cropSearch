package com.bjh.controller;

import com.bjh.service.SelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class SelController {
    @Resource
    private SelService selServiceImpl;


    @RequestMapping(value = "/allCropList")
    public List allCropList(){

        return selServiceImpl.getAllCropList();
    }

    @RequestMapping(value = "/cropNameList")
    public List CropNameList(@RequestParam() String crop){
        return selServiceImpl.getCropNameList(crop);
    }

    @RequestMapping(value = "/cropList")
    public List CropList(@RequestParam() String crop, @RequestParam() int pageNumber){
        return selServiceImpl.getCropList(crop,pageNumber);
    }

    @RequestMapping(value = "/cropQueryCondition")
    public String[] CropQuery(@RequestParam() String crop){
        return selServiceImpl.getCropQuery(crop);
    }
    @RequestMapping(value = "/cropQuery",method = RequestMethod.POST)
    public Map<String,Object> CropList(@RequestBody(required = false) Map<String, Object> data){
        return selServiceImpl.getQueryCountAndData(data);

    }

}
