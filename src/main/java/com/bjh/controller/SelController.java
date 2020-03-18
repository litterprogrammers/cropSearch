package com.bjh.controller;

import com.bjh.service.SelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class SelController {
    @Resource
    private SelService selServiceImpl;

    @ResponseBody
    @RequestMapping(value = "/allCropList")
    public List allCropList(){
        return selServiceImpl.getAllCropList();
    }
    @ResponseBody
    @RequestMapping(value = "/cropNameList")
    public List CropNameList(@RequestParam() String crop){
        return selServiceImpl.getCropNameList(crop);
    }
    @ResponseBody
    @RequestMapping(value = "/cropList")
    public List CropList(@RequestParam() String crop, @RequestParam() int pageNumber){
        return selServiceImpl.getCropList(crop,pageNumber);
    }


}
