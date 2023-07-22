package com.esm.appsearchsample.adapter;

import android.view.View;

import com.esm.appsearchsample.adapter.viewholders.PersonViewHolder;
import com.esm.appsearchsample.entities.AppSearchPerson;
import com.esm.appsearchsample.entities.AppSearchShortcut;
import com.esm.appsearchsample.adapter.exception.TypeNotSupportedException;
import com.esm.appsearchsample.adapter.viewholders.AbstractBetterViewHolder;
import com.esm.appsearchsample.adapter.viewholders.ShortcutViewHolder;
import com.esm.appsearchsample.adapter.viewholders.ShortcutViewHolderTemp;
import com.esm.appsearchsample.entities.AppSearchShortcutDataTemp;

public class TypeFactoryForList implements TypeFactory {
    @Override
    public int type(AppSearchShortcutDataTemp searchShortcutInfoData) {
        return ShortcutViewHolderTemp.LAYOUT;
    }

    @Override
    public int type(AppSearchShortcut appSearchShortcut) {
        return ShortcutViewHolder.LAYOUT;
    }

    @Override
    public int type(AppSearchPerson appSearchPerson) {
         return PersonViewHolder.LAYOUT;
    }

    @Override
    public AbstractBetterViewHolder createViewHolder(View parent, int type) {

        AbstractBetterViewHolder createdViewHolder;

        if (type == ShortcutViewHolderTemp.LAYOUT) {
            createdViewHolder = new ShortcutViewHolderTemp(parent);

        }
        else if (type == ShortcutViewHolder.LAYOUT) {
            createdViewHolder = new ShortcutViewHolder(parent);

        }else if (type == PersonViewHolder.LAYOUT){
            createdViewHolder=new PersonViewHolder(parent);
        }

        else {
            throw TypeNotSupportedException.create(String.format("LayoutType: %d", type));

        }
        return createdViewHolder;
    }
}
