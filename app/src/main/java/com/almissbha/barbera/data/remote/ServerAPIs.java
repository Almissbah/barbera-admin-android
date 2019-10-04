package com.almissbha.barbera.data.remote;

/**
 * Created by mohamed on 7/7/2017.
 */

public class ServerAPIs {
    private static String AppVersion="0";
    private static String ServerDomin="http://hakeem-sd.com/barbera/admin";
   // private static String ServerDomin="http://192.168.137.1/";
    private static String Login_url=ServerDomin+"/login.php";
    private static String add_order_url=ServerDomin+"/add_order.php";

    public static String getAppVersion() {
        return AppVersion;
    }

    public static String getServerDomin() {
        return ServerDomin;
    }

    public static String getLogin_url() {
        return Login_url;
    }

    public static String getAdd_order_url() {
        return add_order_url;
    }
}
