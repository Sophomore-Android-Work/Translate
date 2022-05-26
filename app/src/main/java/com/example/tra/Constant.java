package com.example.tra;

/**
 * Created by lenovo on 2018/3/17.
 * 连接服务器工具类
 */

public class Constant {
    public static String URL = "http://192.168.3.30:8080/Translate_Service_war_exploded/";
    public static String URL_Register = URL + "LoginServlet/RegisterServlet";
    public static String URL_Login = URL + "LoginServlet/a";
    public static String URL_User=URL+"LoginServlet/selectUser";
    public static String URL_Word = URL+"LoginServlet/queryWord";
    public static String URL_Update = URL+"LoginServlet/update";
}
