package com.example.firebase.Model;

import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String email;
    private String address;
    private String total;
    private String status;
    private String comments;
    private List<Order> foods;


    public Request() {
    }

    public Request(String phone, String name, String email, String address, String total, String status, String comments, List<Order> foods) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.address = address;
        this.total = total;
        this.status="0";
        this.comments = comments;
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
