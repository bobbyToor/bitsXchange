package com.example.deepak.bitsxchange;

/**
 * Created by Deepak on 14-02-2015.
 */
public class Product {

    public String name = null;
    public String price = null;
    public String pid = null;
    public String uid = null;
    public String description = null;
    public String filename = null;


    public Product(String name, String price, String pid, String uid, String description, String filename) {
        this.name = name;
        this.price = price;
        this.pid = pid;
        this.uid = uid;
        this.description = description;
        this.filename = filename;
    }
}