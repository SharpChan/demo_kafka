package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class WorkHoursEntity {
    /**
     * 记录的日期
     */
    public Date workDate;

    /**
     * 这天工作的时长
     */

    public BigDecimal manHour;
}
