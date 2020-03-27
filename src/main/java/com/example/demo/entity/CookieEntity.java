package com.example.demo.entity;

import lombok.Data;

@Data
public class CookieEntity {
 private String cookie ;
 private String za;
 private String zentaosid;


 public CookieEntity(){
     this.cookie="lang=zh-cn; device=desktop; theme=default; feedbackView=0; keepLogin=on; " +
             "lastProduct=51; lastProject=52; from=doc; zp=56d219691d2280e73f2ce25e2c21f2d3ca368fc9; " +
             "checkedItem=; downloading=1; pagerCompanyEffort=6000; downloading=null; windowHeight=789; windowWidth=681; ";
 }

 @Override
    public String toString(){
     return this.cookie+this.za+this.zentaosid;
    }
}
