package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoController {

    /**
     * 日期选择框
     * @return
     */
    @GetMapping("/wuiDate.html")
    public ModelAndView demo(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/demo/wuiDate");
        return view;
    }
}
