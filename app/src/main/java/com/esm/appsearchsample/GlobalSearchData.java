package com.esm.appsearchsample;

import android.graphics.drawable.Drawable;

public class GlobalSearchData {
    private String label;
    private String appName;
    private Drawable icon;
    private String[] intent;

    public GlobalSearchData(String description, Drawable imgId, String[] intent, String appName) {
        this.label = description;
        this.icon = imgId;
        this.intent = intent;
        this.appName = appName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
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
}