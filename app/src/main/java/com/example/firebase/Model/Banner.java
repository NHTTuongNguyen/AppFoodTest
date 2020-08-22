package com.example.firebase.Model;

public class Banner {

    private  String Id;
    private String Name;
    private String Image;

    public Banner() {
    }

    public Banner(String id, String name, String image) {
        Id = id;
        Name = name;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
