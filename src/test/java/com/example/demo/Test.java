package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class Test {

    private static String doPost(String strUrl, String content) {
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

            Map map = urlConnection.getHeaderFields();
            System.out.println("Map:"+map);

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

    public static void main(String[] args) {
        String str = doPost("http://10.10.10.151/zentao/user-login.html", "account=chengfeng&password=12345678&passwordStrength=0&referer=%2Fzentao%2F&verifyRand=1841686753&keepLogin=0");
        System.out.println(str);
    }
}
