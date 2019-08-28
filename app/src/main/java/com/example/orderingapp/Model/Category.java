package com.example.orderingapp.Model;

public class Category {
    private  String NAME;
    private String image;
    private long PRICE;
    private long QUANTITY;

    public Category(){

    }
    public Category(String name, String image, long price,long quantity)
    {
        this.NAME=name;
        this.image=image;
        this.PRICE= price;
        this.QUANTITY=quantity;
    }

    public String getPrice() {
        String price=String.valueOf(PRICE);
        return price;
    }

    public String getQUANTITY() {
        String quantity=String.valueOf(QUANTITY);
        return quantity;
    }

    public void setQUANTITY(String quantity) {
        this.QUANTITY = Long.parseLong(quantity);
    }

    public void setPrice(String price) {
        PRICE = Long.parseLong(price);
    }

    public String getName() {
        return NAME;
    }

    public void setName(String name) {
        NAME = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
