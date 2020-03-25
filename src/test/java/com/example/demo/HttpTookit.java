package com.example.demo;

import com.example.demo.entity.RowEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTookit {
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "text/html, */*; q=0.01");
            connection.setRequestProperty("Accept-Encoding", "deflate");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Cookie", "lang=zh-cn; device=desktop; theme=default; feedbackView=0; keepLogin=on; za=chengfeng; lastProduct=51; lastProject=52; from=doc; zp=56d219691d2280e73f2ce25e2c21f2d3ca368fc9; checkedItem=; downloading=1; downloading=null; windowHeight=752; windowWidth=359; zentaosid=drpmj7hmcbs2atrqe7u7abafk7");
            connection.setRequestProperty("Host", "10.10.10.151");
            connection.setRequestProperty("Referer", "http://10.10.10.151/zentao/company-calendar-0-20200317-20200324-0-0--1.html");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }



    public static void main(String[] args) throws Exception{
        System.out.println("开始时间为：" + System.currentTimeMillis());
        //String table = parseTable();
        String s = sendPost("http://10.10.10.151/zentao/company-effort-custom-date_desc.html");
        List<RowEntity> rowEntityList = parseTable(s);
        System.out.println("输出：" + rowEntityList);

    }

    private static List<RowEntity> parseTable(String table) {
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


    public static String sendPost(String url) throws Exception{
        URL urls = null;
        BufferedReader in = null;
        HashMap<String,String> parameter = new HashMap<String,String>();
        /*
        parameter.put("fileName","chengfeng - 日志");
        parameter.put("fileType","xlsx");
        parameter.put("exportType","all");
        parameter.put("template","0");
        parameter.put("exportFields_01","id");
        parameter.put("exportFields_02","date");
        parameter.put("exportFields_03","account");
        parameter.put("exportFields_04","work");
        parameter.put("exportFields_05","consumed");
        parameter.put("exportFields_06","left");
        parameter.put("exportFields_07","objectType");
        parameter.put("exportFields_08","product");
        parameter.put("exportFields_09","project");
        parameter.put("title","默认模板");
        */
        parameter.put("dept","0");
        parameter.put("begin","2020-01-24");
        parameter.put("end","2020-03-24");
        parameter.put("product","0");
        parameter.put("project","0");
        parameter.put("user","");



        try {
            urls = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        String rs = null;
        try {
            connection = (HttpURLConnection) urls.openConnection();

            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

            connection.setRequestProperty("Accept-Encoding", "deflate");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("Content-Length", "355");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=--testsssssss");
            connection.setRequestProperty("Cookie", "lang=zh-cn; device=desktop; theme=default; feedbackView=0; keepLogin=on; " +
                                                     "za=chengfeng; lastProduct=51; lastProject=52; from=doc; zp=56d219691d2280e73f2ce25e2c21f2d3ca368fc9; " +
                                                     "checkedItem=; downloading=1; pagerCompanyEffort=20; downloading=null; windowHeight=789; windowWidth=681; zentaosid=drpmj7hmcbs2atrqe7u7abafk7");
            connection.setRequestProperty("Host", "10.10.10.151");
            connection.setRequestProperty("Origin", "http://10.10.10.151");
            connection.setRequestProperty("Proxy-Connection", "keep-alive");
            connection.setRequestProperty("Referer", "http://10.10.10.151/zentao/company-effort-custom-date_desc.html");
            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestProperty("Range", "bytes="+"");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("POST");

            StringBuffer sb = new StringBuffer();
            String mimeBoundary = "testsssssss";
            for(String key:parameter.keySet()) {
                //在boundary关需添加两个横线
                sb = sb.append("--").append(mimeBoundary);
                sb.append("\r\n");
                String keyFin = "";
                if(key.contains("exportFields")){
                    keyFin = "exportFields[]";
                }else{
                    keyFin = key;
                }
                sb.append("Content-Disposition: form-data; "+keyFin+"=\""+parameter.get(key)+"\"");
                //提交的数据前要有两个回车换行
                sb.append("\r\n\r\n");
            }
            //body结束时 boundary前后各需添加两上横线，最添加添回车换行
            sb.append("--").append(mimeBoundary).append("--").append("\r\n");
            outputStream = connection.getOutputStream();
            System.out.println(sb.toString());
            outputStream.write(sb.toString().getBytes());
            try {
                connection.connect();
                if(connection.getResponseCode() == 200) {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        rs += line;
                    }
                }
            }
            catch (Exception e) {
                rs = null;
            }

            return rs;
        }
        finally {
            try {
                outputStream.close();
            }
            catch (Exception e) {
            }
            outputStream = null;

            if(connection != null)
                connection.disconnect();
            connection = null;
        }
    }

}