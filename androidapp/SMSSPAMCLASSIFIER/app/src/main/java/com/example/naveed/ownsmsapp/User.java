package com.example.naveed.ownsmsapp;

public class User {
    private String name;
    private String number;
    public User(String number) {
      //  this.name = text;
        //  this.memberData = data;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
