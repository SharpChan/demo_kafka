package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.CookieEntity;
import com.example.demo.entity.ProjectEntity;
import com.example.demo.entity.RowEntity;
import com.example.demo.intf.CreateExcelIntf;
import com.example.demo.intf.HttpTookitIntf;
import com.example.demo.intf.TableParseIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWordController {

    @Autowired
    private TableParseIntf tableParseServer;

    @Autowired
    private HttpTookitIntf httpTookitService;

    @Autowired
    private CreateExcelIntf createExcelService;

    @RequestMapping("/hello")
    public String index() {
        return "Hello Spring Boot!";
    }



    @RequestMapping("/getExcel")
    public  List<ProjectEntity> getExcel(){
        List<String> list =new ArrayList<String>();
        String s = httpTookitService.logInGetCookie("chengfeng","12345678",list);
        String  aaa = list.get(list.size()-1);
        String[] arr = aaa.split(";");

        CookieEntity cookieEntity = new CookieEntity();
        cookieEntity.setZa("za=chengfeng;");
        cookieEntity.setZentaosid(arr[0]);
        String tableStr = httpTookitService.sendPost("http://10.10.10.151/zentao/company-effort-custom-date_desc.html",cookieEntity.toString());
        List<RowEntity> rowEntityList = tableParseServer.parseTable(tableStr);

        List<ProjectEntity> projectEntityList = createExcelService.formatConverter(rowEntityList);

        String jsonString = JSON.toJSONString(projectEntityList);
        System.out.println("jsonString"+jsonString);
        return projectEntityList;
    }


    @GetMapping("/test.html")
    public ModelAndView test(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/test");
        return view;
    }
}
