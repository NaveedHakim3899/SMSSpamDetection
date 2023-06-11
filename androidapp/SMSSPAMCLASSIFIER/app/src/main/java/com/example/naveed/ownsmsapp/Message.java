package com.example.naveed.ownsmsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class Message {

    /*private String message, type;


    private String from;

    public Message(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Message(String message, String type ) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public Message(){

    }*/
    private String text;
    private MemberData memberData;
    private boolean belongsToCurrentUser;
    public Message(String text, boolean belongsToCurrentUser) {
        this.text = text;
      //  this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }
    public String getText() {
        return text;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

}