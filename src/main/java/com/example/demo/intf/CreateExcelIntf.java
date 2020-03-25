package com.example.demo.intf;

import com.example.demo.entity.RowEntity;

import java.util.List;

public interface CreateExcelIntf {
    void formatConverter(List<RowEntity> rowEntityList);
}
