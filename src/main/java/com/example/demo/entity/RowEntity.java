package com.example.demo.entity;

import lombok.Data;

@Data
public class RowEntity {

    /**
     *ID
     */
    private String  id;
    /**
     *日期
     */
    private String  date;
    /**
     *登记人
     */
    private String  personName;
    /**
     *工作内容
     */
    private String  jobDescription;
    /**
     *耗时
     */
    private String  elapsedTime;
    /**
     *剩余
     */
    private String  remainingTime;
    /**
     *对象
     */
    private String  mission;
    /**
     *产品
     */
    private String  product;
    /**
     *项目
     */
    private String  projectName;
}
