package com.almissbha.barbera.model;

import java.io.Serializable;

/**
 * Created by mohamed on 7/14/2018.
 */

public class User implements Serializable{
    private int id=0;
    private String userName="";
    private String deviceName="";
    private String token;
    private boolean isLogged=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
