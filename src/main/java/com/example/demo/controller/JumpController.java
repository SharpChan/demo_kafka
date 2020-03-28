package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JumpController {

    /*
    @GetMapping("/job.html")
    public ModelAndView test(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/job");
        return view;
    }
    */
    @GetMapping("/job")
    public ModelAndView test(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/job");
        return view;
    }

}
