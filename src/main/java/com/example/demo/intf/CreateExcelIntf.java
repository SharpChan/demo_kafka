package com.example.demo.intf;

import com.example.demo.entity.ProjectEntity;
import com.example.demo.entity.RowEntity;

import java.util.List;

public interface CreateExcelIntf {
    List<ProjectEntity> formatConverter(List<RowEntity> rowEntityList);
}
