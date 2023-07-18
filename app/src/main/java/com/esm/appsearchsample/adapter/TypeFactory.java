package com.esm.appsearchsample.adapter;

import android.view.View;

import com.esm.appsearchsample.GlobalSearchListData;
import com.esm.appsearchsample.adapter.viewholders.AbstractBetterViewHolder;
import com.esm.appsearchsample.entities.AppSearchShortcutData;

public interface TypeFactory {
    int type (AppSearchShortcutData searchShortcutInfoData);
    int type (GlobalSearchListData globalSearchListData );


    AbstractBetterViewHolder createViewHolder(View parent, int type);


}
