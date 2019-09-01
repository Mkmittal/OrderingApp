package com.example.orderingapp.Model;

public class User {
    private String Name;
    private String Password;
    private String orderNo;

    public User(){
    }

    public User(String name,String password,String order)
    {
        Name = name;
        Password = password;
        orderNo = order;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
}
