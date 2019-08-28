package com.example.orderingapp.Model;


import java.io.Serializable;

public class Order implements Serializable {
    private String Image;
    private String ProductName;
    private String Quantity;
    private String Price;

    public Order(){
    }

    public Order(String image, String productName, String quantity, String price) {
        Image = image;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public void increaseQuantity(){
        int quantity = Integer.parseInt(Quantity) + 1;
        Quantity=String.valueOf(quantity);
    }
    public void decreaseQuantity(){
        int quantity = Integer.parseInt(Quantity) - 1;
            Quantity = String.valueOf(quantity);
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

}
