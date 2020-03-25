package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * 项目信息
 */
@Data
public class ProjectEntity {
    /**
     * 项目名称
     */
    public String projectName;

    /**
     * 做这个项目的人员列表
     */
    public List<PersonEntity> personEntityList;
}
