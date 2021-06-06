package com.example.firebase.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private String Name;
    private String Image;
    private String Sale;
    public  Category(){

    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getSale() {
        return Sale;
    }

    public void setSale(String sale) {
        Sale = sale;
    }

    public Category(String name, String image, String sale) {
        Name = name;
        Image = image;
        Sale = sale;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
