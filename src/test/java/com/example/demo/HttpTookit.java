package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

    /**
     * 2003 excel后缀
     */
    private static final String EXCEL_XLS = "xls";

    /**
     * 2007 及以上版本excel后缀
     */
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {
        System.out.println("开始时间为：" + System.currentTimeMillis());
        //String s = HttpTookit.sendGet("http://10.10.10.151/zentao/company-calendar-0-1584374400-1584979200-0-0--1-yes.html");

        sendPost("http://10.10.10.151/zentao/effort-export--date_asc.html");

        //System.out.println("输出：" + s);

    }

    public static void sendPost(String url) {
        HttpClient httpclient = HttpClients.createDefault();
        BufferedReader in = null;
        try {
            String uri = url;
            HttpPost httppost = new HttpPost(uri);


            StringBody fileName = new StringBody("chengfeng - 日志", ContentType.TEXT_PLAIN);
            StringBody fileType = new StringBody("xlsx", ContentType.TEXT_PLAIN);
            StringBody exportType = new StringBody("exportType", ContentType.TEXT_PLAIN);
            StringBody template = new StringBody("template", ContentType.TEXT_PLAIN);
            StringBody exportField_01 = new StringBody("account", ContentType.TEXT_PLAIN);
            StringBody exportField_02 = new StringBody("consumed", ContentType.TEXT_PLAIN);
            StringBody exportField_03 = new StringBody("project", ContentType.TEXT_PLAIN);
            StringBody title = new StringBody("ggggg", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // .addPart("bin", bin)
                    //  .addPart("comment", comment)
                    .addPart("fileName",fileName)
                    .addPart("fileType",fileType)
                    .addPart("exportType",exportType)
                    .addPart("template",template)
                    .addPart("exportFields[]",exportField_01)
                    .addPart("exportFields[]",exportField_02)
                    .addPart("exportFields[]",exportField_03)
                    .addPart("title",title)
                    .build();


            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost.getRequestLine());



            HttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println("状态"+response.getStatusLine());
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    System.out.println("Response content LazyDecompressingInputStream: " + resEntity.getContent());

                    /*
                    String a= EntityUtils.toString(resEntity);
                    //打印获取到的返回值
                    System.out.println("Response content: " + a);
                    */

                    byte[] data = EntityUtils.toByteArray(resEntity);
                    //存入磁盘
                    FileOutputStream fos = new FileOutputStream("E:/text/sharecertificate.xlsx");
                    fos.write(data);
                    fos.close();

                    System.out.println("sharecertificate.xls文件下载成功!!!!");
                }
                EntityUtils.consume(resEntity);
            } finally {
                try {
                   // response.close();
                }catch(Exception e){
                    e.printStackTrace();

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
               // httpclient.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String downloadFile(HttpServletResponse response) {
        OutputStream out = null;
        try {
            // 获取输入流
            File file = new File("E:\\123.txt");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            // 保持文件名
            String fileName = file.getName();
            // 防止中文名乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 设置文件ContentType类型
            response.setContentType("multipart/form-data");
            out = new BufferedOutputStream(response.getOutputStream());
            String suffix = StringUtils.substringAfterLast(fileName, ".");
            if ((suffix.equals(EXCEL_XLS) || suffix.equals(EXCEL_XLSX))) {
                Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
                workbook.write(out);
                workbook.close();
            } else {
                byte[] buff = new byte[10 * 1024];
                while (in.read(buff) != -1) {
                    out.write(buff);
                    //清空缓存区中的数据流
                    out.flush();
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }
}