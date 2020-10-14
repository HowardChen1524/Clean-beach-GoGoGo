package com.example.a1061524_1061525_final;

public class item {

    String name;
    Integer price;

    public item(String name, int price)
    {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }
    public String getPrice() {
        return price.toString();
    }
}


