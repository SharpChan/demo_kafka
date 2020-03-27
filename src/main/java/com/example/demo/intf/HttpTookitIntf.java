package com.example.demo.intf;

import java.util.List;

public interface HttpTookitIntf {

    String sendGet(String url);

    String sendPost(String url,String cookie,String departmentNo,String startDate,String endDate);

    String logInGetCookie(String userName,String password,List<String> cookieList);
}
