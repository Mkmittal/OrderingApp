package com.example.orderingapp.Common;

import com.example.orderingapp.Model.Order;
import com.example.orderingapp.Model.User;

import java.util.ArrayList;

public class Common {
    public static User currentUser;
    public static ArrayList<Order> cart=new ArrayList<>();
    public static boolean firstTimeLogin = true;
    public static String customerId;
    public static String cartTotal;
}
