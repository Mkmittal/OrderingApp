package com.example.orderingapp.Common;

import com.example.orderingapp.Model.Order;
import com.example.orderingapp.Model.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Common {
    public static User currentUser;
    public static DatabaseReference table_user;
    public static ArrayList<Order> cart=new ArrayList<>();
    public static boolean firstTimeLogin = true;
    public static String customerId="7777777777";
    public static String cartTotal;
}
