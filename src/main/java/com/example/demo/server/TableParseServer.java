package com.example.demo.server;

import com.example.demo.entity.RowEntity;
import com.example.demo.intf.TableParseIntf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableParseServer implements TableParseIntf {
    private static final Logger logger = LogManager.getLogger(TableParseServer.class);
    public List<RowEntity> parseTable(String table) {
        if(!table.contains("table[class=table has-sort-head table-fixed ]")){
            logger.info("登录出现问题！");
        }
        Document doc = Jsoup.parse(table);
        Elements rows = doc.select("table[class=table has-sort-head table-fixed ]").get(0).select("tr");
        List<RowEntity> rowEntityList = new ArrayList<RowEntity>();
        if (rows.size() == 1) {
            System.out.println("没有结果");
        }else {
            for(int i=1;i<rows.size();i++)
            {
                Element row = rows.get(i);
                RowEntity rowBean =new RowEntity();
                rowBean.setId(row.select("td").get(0).text());
                rowBean.setDate(row.select("td").get(1).text());
                rowBean.setPersonName(row.select("td").get(2).text());
                rowBean.setJobDescription(row.select("td").get(3).text());
                rowBean.setElapsedTime(row.select("td").get(4).text());
                rowBean.setRemainingTime(row.select("td").get(5).text());
                rowBean.setMission(row.select("td").get(6).text());
                rowBean.setProduct(row.select("td").get(7).text());
                rowBean.setProjectName(row.select("td").get(8).text());
                rowEntityList.add(rowBean);
            }
        }
        return rowEntityList;
    }
}
