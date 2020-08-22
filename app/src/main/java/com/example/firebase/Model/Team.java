package com.example.firebase.Model;

import java.io.Serializable;

public class Team implements Serializable {
    private String Name;
    private String Image;
    private String Location;
    private String Avatar;
    private String Status;
    private String Student;
    private String Live;
    private String From;
    private String Jonined;
    private String ProfileId;
    private String Phone;
    private String Email;
    private String About;

    public Team() {
    }

    public Team(String name, String image, String location, String avatar, String status, String student, String live, String from, String jonined, String profileId, String phone, String email, String about) {
        Name = name;
        Image = image;
        Location = location;
        Avatar = avatar;
        Status = status;
        Student = student;
        Live = live;
        From = from;
        Jonined = jonined;
        ProfileId = profileId;
        Phone = phone;
        Email = email;
        About = about;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getLive() {
        return Live;
    }

    public void setLive(String live) {
        Live = live;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getJonined() {
        return Jonined;
    }

    public void setJonined(String jonined) {
        Jonined = jonined;
    }

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }
}
