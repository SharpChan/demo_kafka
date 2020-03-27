package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目信息
 */
@Data
public class ProjectEntity {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目耗时
     */
    private BigDecimal projectTotalHour;

    /**
     * 做这个项目的人员列表
     */
    private List<PersonEntity> personEntityList;
}
