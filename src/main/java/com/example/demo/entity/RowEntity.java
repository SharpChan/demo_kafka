package com.example.demo.entity;

import lombok.Data;

@Data
public class RowEntity {

    /**
     *ID
     */
    public String  id;
    /**
     *日期
     */
    public String  date;
    /**
     *登记人
     */
    public String  personName;
    /**
     *工作内容
     */
    public String  jobDescription;
    /**
     *耗时
     */
    public String  elapsedTime;
    /**
     *剩余
     */
    public String  remainingTime;
    /**
     *对象
     */
    public String  mission;
    /**
     *产品
     */
    public String  product;
    /**
     *项目
     */
    public String  projectName;
}
