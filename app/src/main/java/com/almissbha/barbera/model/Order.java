package com.almissbha.barbera.model;

import java.io.Serializable;

/**
 * Created by mohamed on 7/14/2018.
 */

public class Order implements Serializable {
    int id=0;
    int adminId=0;
    int userId=0;
    String costumerPhone="";
    int balanceTime=0;
    boolean isRequested=false;

    public boolean isRequested() {
        return isRequested;
    }

    public void setRequested(boolean requested) {
        isRequested = requested;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCostumerPhone() {
        return costumerPhone;
    }

    public void setCostumerPhone(String costumerPhone) {
        this.costumerPhone = costumerPhone;
    }

    public int getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(int balanceTime) {
        this.balanceTime = balanceTime;
    }
}
