package com.esm.appsearchsample.entities;

import android.graphics.drawable.Drawable;

import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.Visitable;

public class AppSearchShortcut implements Visitable {
    private Drawable appIcon;
    private String shortLabel;
    private String appName;
    private Drawable shortcutIcon;

    private String[] shortcutIntent;

    public AppSearchShortcut(Drawable appIcon, String shortLabel, Drawable shortcutIcon, String[] shortcutIntent, String appName) {
        this.appIcon = appIcon;
        this.shortLabel = shortLabel;
        this.shortcutIcon = shortcutIcon;
        this.shortcutIntent = shortcutIntent;
        this.appName = appName;
    }
    public String getShortLabel() {
        return shortLabel;
    }
    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }
    public Drawable getShortcutIcon() {
        return shortcutIcon;
    }
    public void setShortcutIcon(Drawable shortcutIcon) {
        this.shortcutIcon = shortcutIcon;
    }

    public String[] getShortcutIntent() {
        return shortcutIntent;
    }
    public void setShortcutIntent(String[] shortcutIntent) {
        this.shortcutIntent = shortcutIntent;
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