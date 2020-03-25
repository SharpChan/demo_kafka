package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class PersonEntity {
    /**
     * 姓名
     */
    public String name;
    /**
     * 员工ID
     */
    public String id;

    /**
     * 部门
     */
    public String department;

    /**
     *记录该项目下的工作
     */
    public List<WorkHoursEntity> workHoursEntityList;


}
