package com.example.demo.intf;

import com.example.demo.entity.RowEntity;

import java.util.List;

public interface TableParseIntf {
    List<RowEntity> parseTable(String table);
}
