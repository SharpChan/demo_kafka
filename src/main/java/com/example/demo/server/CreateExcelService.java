package com.example.demo.server;

import com.example.demo.entity.RowEntity;
import com.example.demo.intf.CreateExcelIntf;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateExcelService implements CreateExcelIntf {
    public void formatConverter(List<RowEntity> rowEntityList){
        Set<String> nameSet = new HashSet();
        //获取所有的项目名称
        rowEntityList.forEach(s-> nameSet.add(s.getProjectName()));
     for(String projectName : nameSet) {
         for (RowEntity row : rowEntityList) {
             if(projectName.equals(row.getProjectName())){

             }
         }
     }
    }
}
