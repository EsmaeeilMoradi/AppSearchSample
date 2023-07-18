package com.esm.appsearchsample.adapter;

import android.view.View;

import com.esm.appsearchsample.GlobalSearchListData;
import com.esm.appsearchsample.adapter.exception.TypeNotSupportedException;
import com.esm.appsearchsample.adapter.viewholders.AbstractBetterViewHolder;
import com.esm.appsearchsample.adapter.viewholders.GlobalViewHolder;
import com.esm.appsearchsample.adapter.viewholders.ShortcutViewHolder;
import com.esm.appsearchsample.entities.AppSearchShortcutData;

public class TypeFactoryForList implements TypeFactory {
    @Override
    public int type(AppSearchShortcutData searchShortcutInfoData) {
        return ShortcutViewHolder.LAYOUT;
    }

    @Override
    public int type(GlobalSearchListData globalSearchListData) {
        return GlobalViewHolder.LAYOUT;
    }

    @Override
    public AbstractBetterViewHolder createViewHolder(View parent, int type) {

        AbstractBetterViewHolder createdViewHolder;

        if (type == ShortcutViewHolder.LAYOUT) {
            createdViewHolder = new ShortcutViewHolder(parent);

        }
        else if (type == GlobalViewHolder.LAYOUT) {
            createdViewHolder = new GlobalViewHolder(parent);

        }

        else {
            throw TypeNotSupportedException.create(String.format("LayoutType: %d", type));

        }
        return createdViewHolder;
    }
}
