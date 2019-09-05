package com.example.orderingapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String orderNo;
    private String totalPrice;
    private ArrayList<Category> orderList= new ArrayList<>();

    public Order() {
    }

    public Order(String orderNo, String totalPrice, ArrayList<Category> orderList) {
        this.orderNo = orderNo;
        this.totalPrice = totalPrice;
        this.orderList = orderList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Category> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Category> orderList) {
        this.orderList = orderList;
    }

}
