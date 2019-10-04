package com.almissbha.barbera.data.remote;

import com.almissbha.barbera.model.User;

public class AddOrderRequest {
    private String costumerPhone;
    private String balanceTime;
    private User user;

    public String getCostumerPhone() {
        return costumerPhone;
    }

    public void setCostumerPhone(String costumerPhone) {
        this.costumerPhone = costumerPhone;
    }

    public String getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(String balanceTime) {
        this.balanceTime = balanceTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
