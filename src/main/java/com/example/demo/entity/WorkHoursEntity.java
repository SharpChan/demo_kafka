package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class WorkHoursEntity {
    /**
     * 记录的日期
     */
    public String  workDate;

    /**
     * 这天工作的时长
     */

    public BigDecimal manHour;
}
