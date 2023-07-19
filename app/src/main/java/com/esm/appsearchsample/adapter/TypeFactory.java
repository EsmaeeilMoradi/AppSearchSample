package com.esm.appsearchsample.adapter;

import android.view.View;

import com.esm.appsearchsample.entities.AppSearchShortcut;
import com.esm.appsearchsample.adapter.viewholders.AbstractBetterViewHolder;
import com.esm.appsearchsample.entities.AppSearchShortcutDataTemp;

public interface TypeFactory {
    int type (AppSearchShortcutDataTemp searchShortcutInfoData);
    int type (AppSearchShortcut appSearchShortcut);


    AbstractBetterViewHolder createViewHolder(View parent, int type);


}
