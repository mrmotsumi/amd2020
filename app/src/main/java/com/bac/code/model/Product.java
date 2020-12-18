package com.bac.code.model;

public class Product {

    private int id;
    private String title;
    private double price;
    private String webPage;
    private String location;


    public Product(String title, double price, String webPage, String location) {
        this.title = title;
        this.price = price;
        this.webPage = webPage;
        this.location = location;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
