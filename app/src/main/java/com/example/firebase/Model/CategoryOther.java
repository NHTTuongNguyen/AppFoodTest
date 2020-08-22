package com.example.firebase.Model;

public class CategoryOther {
    private String Description;
    private String Price;
    private String Discount;
    private String Image;
    private String Star;
    private String Name;
    private String MenuId;

    public CategoryOther() {
    }

    public CategoryOther(String description, String price, String discount, String image, String star, String name, String menuId) {
        Description = description;
        Price = price;
        Discount = discount;
        Image = image;
        Star = star;
        Name = name;
        MenuId = menuId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
