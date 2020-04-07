package com.example.demo.server;

import com.example.demo.entity.PersonEntity;
import com.example.demo.entity.ProjectEntity;
import com.example.demo.entity.RowEntity;
import com.example.demo.entity.WorkHoursEntity;
import com.example.demo.intf.CreateExcelIntf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateExcelService implements CreateExcelIntf {
    private static final Logger logger = LogManager.getLogger(CreateExcelService.class);

    /**
     * 获取结构化对象
     * @param rowEntityList
     * @return
     */
    public List<ProjectEntity> formatConverter(List<RowEntity> rowEntityList){
        if(CollectionUtils.isEmpty(rowEntityList)){
            logger.info("需要导出的表格为空");
        }
        List<ProjectEntity> projectEntityList = new ArrayList<ProjectEntity>();
        Set<String> projectNameSet = new HashSet();
        //获取所有的项目名称
        rowEntityList.forEach(s-> projectNameSet.add(s.getProjectName()));
     for(String projectName : projectNameSet) {
         ProjectEntity projectEntity = new ProjectEntity();
         projectEntityList.add(projectEntity);
         projectEntity.setProjectName(projectName);
         List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
         projectEntity.setPersonEntityList(personEntityList);
         Set<String> personNameSet = new HashSet();//该项目下的人员
         List<RowEntity> rowList = new ArrayList<RowEntity>();//该项目下的条目
         BigDecimal projectHour = new BigDecimal(0);
         for (RowEntity row : rowEntityList) {
             if(projectName.equals(row.getProjectName()) && !row.getJobDescription().contains("完成了任务")){
                 personNameSet.add(row.getPersonName());
                 rowList.add(row);
                 projectHour = projectHour.add(new BigDecimal(row.getElapsedTime()));
             }
         }
         projectEntity.setProjectTotalHour(projectHour);

         for(String personName : personNameSet ){
             PersonEntity personEntity = new PersonEntity();
             personEntity.setName(personName);
             List<WorkHoursEntity> workHoursEntityList = new ArrayList<WorkHoursEntity>();
             personEntity.setWorkHoursEntityList(workHoursEntityList);
             BigDecimal personHour = new BigDecimal(0);
            for(RowEntity row : rowList){
               if(personName.equals(row.getPersonName())){
                   WorkHoursEntity workHoursEntity = new WorkHoursEntity();
                   workHoursEntity.setManHour(new BigDecimal(row.getElapsedTime()) );
                   workHoursEntity.setWorkDate(row.getDate());
                   workHoursEntityList.add(workHoursEntity);
                   personHour = personHour.add(workHoursEntity.getManHour());
               }
            }
            personEntity.setPersonHour(personHour);
            personEntityList.add(personEntity);
         }
     }
      return projectEntityList;
    }
}


