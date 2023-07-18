package com.esm.appsearchsample;

import android.graphics.drawable.Drawable;

import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.Visitable;

public class GlobalSearchListData implements Visitable {
    private Drawable appIcon;
    private String description;
    private String appName;
    private Drawable imgId;

    private String[] intent;

    public GlobalSearchListData(Drawable appIcon, String description, Drawable imgId, String[] intent, String appName) {
        this.appIcon = appIcon;
        this.description = description;
        this.imgId = imgId;
        this.intent = intent;
        this.appName = appName;
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

    public String[] getIntent() {
        return intent;
    }
    public void setIntent(String[] intent) {
        this.intent = intent;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return  typeFactory.type(this);
    }
}