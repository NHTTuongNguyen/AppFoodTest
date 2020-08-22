package com.example.firebase.Model;

public class Category2 {
    private String Image;
    private String Name;
    private String Sale;

    public Category2() {
    }

    public Category2(String image, String name, String sale) {
        Image = image;
        Name = name;
        Sale = sale;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSale() {
        return Sale;
    }

    public void setSale(String sale) {
        Sale = sale;
    }
}
