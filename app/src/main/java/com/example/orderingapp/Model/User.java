package com.example.orderingapp.Model;

import com.example.orderingapp.Common.Common;

public class User {
    private String Name;
    private String Password;
    private String OrderNo;

    public User(){
    }

    public User(String name,String password,String order)
    {
        Name = name;
        Password = password;
        OrderNo = order;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        this.OrderNo = orderNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void updateOrderId()
    {
        setOrderNo(String.valueOf(Integer.parseInt(Common.currentUser.getOrderNo())+1));
    }
}
