package com.example.orderingapp.Common;

import com.example.orderingapp.Model.Items;
import com.example.orderingapp.Model.User;

import java.util.ArrayList;

public class Common {
    public static User currentUser;
    public static ArrayList<Items> cart=new ArrayList<>();
    public static boolean firstTimeLogin = true;
    public static String customerId;
    public static String quantity;
    public static String cartTotal;
}
