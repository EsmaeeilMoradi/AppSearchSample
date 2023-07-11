package com.esm.appsearchsample;

import android.graphics.drawable.Drawable;

public class GlobalSearchListData {
    private String description;
    private String appName;
    private Drawable imgId;

    private  String[]  intent;
    public GlobalSearchListData(String description, Drawable imgId, String[] intent, String appName) {
        this.description = description;
        this.imgId = imgId;
        this.intent=intent;
        this.appName=appName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Drawable getImgId() {
        return imgId;
    }
    public void setImgId(Drawable imgId) {
        this.imgId = imgId;
    }

    public  String[]  getIntent(){
        return intent;
    }
    public void setIntent( String[]  intent){
        this.intent = intent;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}