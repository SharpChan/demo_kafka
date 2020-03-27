package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PersonEntity {
    /**
     * 姓名
     */
    private String name;

    /**
     * 部门
     */
    private String department;

    /**
     * 员工某一项目耗时
     */
    private BigDecimal personHour;

    /**
     *记录该项目下的工作
     */
    private List<WorkHoursEntity> workHoursEntityList;


}
