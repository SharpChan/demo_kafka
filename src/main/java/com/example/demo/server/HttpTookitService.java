package com.example.demo.server;

import com.example.demo.intf.HttpTookitIntf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpTookitService implements HttpTookitIntf {

    private static final Logger logger = LogManager.getLogger(HttpTookitService.class);

    public String logInGetCookie(String userName,String password,List<String>  cookieList)  {
        String strUrl = "http://10.10.10.151/zentao/user-login.html";
        String content = "account=chengfeng&password=12345678&passwordStrength=0&referer=%2Fzentao%2F&verifyRand=1841686753&keepLogin=0";
        String result = "";

        try {
            URL url = new URL(strUrl);
            //通过调用url.openConnection()来获得一个新的URLConnection对象，并且将其结果强制转换为HttpURLConnection.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //设置连接的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setConnectTimeout(30000);
            //设置读取的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setReadTimeout(30000);
            //将url连接用于输出，这样才能使用getOutputStream()。getOutputStream()返回的输出流用于传输数据
            urlConnection.setDoOutput(true);
            //设置通用请求属性为默认浏览器编码类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //getOutputStream()返回的输出流，用于写入参数数据。
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            //此时将调用接口方法。getInputStream()返回的输入流可以读取返回的数据。
            InputStream inputStream = urlConnection.getInputStream();

            List cookieListTmp=  urlConnection.getHeaderFields().get("Set-Cookie");
            //System.out.println("getHeaderFields:"+urlConnection.getHeaderFields());
            if(CollectionUtils.isEmpty(cookieListTmp)){
                throw new RuntimeException("登录出错！");
            }
            cookieList.addAll(cookieListTmp);
            byte[] data = new byte[1024];
            StringBuilder sb = new StringBuilder();
            //inputStream每次就会将读取1024个byte到data中，当inputSteam中没有数据时，inputStream.read(data)值为-1
            while (inputStream.read(data) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb.append(s);
            }
            result = sb.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String sendPost(String url,String cookie) {
        URL urls = null;
        BufferedReader in = null;

        HashMap<String,String> parameter = new HashMap<String,String>();
        parameter.put("dept","0");
        parameter.put("begin","2020-01-24");
        parameter.put("end","2020-03-24");
        parameter.put("product","0");
        parameter.put("project","0");
        parameter.put("user","");

        try {
            urls = new URL(url);
        } catch (MalformedURLException e) {
            logger.info("{}:{}","访问外部URL时", e);
        }
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        String rs = null;
        try {
            connection = (HttpURLConnection) urls.openConnection();
            //设置请求头信息
            setConnectionParms(connection,cookie);
            StringBuffer sb = new StringBuffer();
            //获取请求的FormData
            sb = getFormDataStringBuffer(parameter, sb);
            outputStream = connection.getOutputStream();
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
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                outputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if(connection != null) {
                connection.disconnect();
            }
        }
        return rs;
    }

    private StringBuffer getFormDataStringBuffer(HashMap<String, String> parameter, StringBuffer sb) {
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
        return sb;
    }

    private void setConnectionParms(HttpURLConnection connection,String cookie) throws ProtocolException {
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Content-Length", "355");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=--testsssssss");
        connection.setRequestProperty("Cookie",cookie);
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
    }

    private void setConnectionParmsLogIn(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");

        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        connection.setRequestProperty("Content-Length", "131");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Cookie", "lang=zh-cn; device=desktop; theme=default; windowWidth=1284; windowHeight=233; zentaosid=jvti1qilde69cje6hnkajkif43");
        connection.setRequestProperty("Host", "10.10.10.151");
        connection.setRequestProperty("Origin", "http://10.10.10.151");
        connection.setRequestProperty("Referer", "http://10.10.10.151/zentao/user-login.html");
        connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestProperty("Range", "bytes="+"");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(20000);
        connection.setRequestMethod("POST");
    }

    public  String sendGet(String url) {
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

}
