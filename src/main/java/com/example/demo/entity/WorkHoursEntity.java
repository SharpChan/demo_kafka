package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class WorkHoursEntity {
    /**
     * 记录的日期
     */
    private String  workDate;

    /**
     * 这天工作的时长
     */

    private BigDecimal manHour;
}
