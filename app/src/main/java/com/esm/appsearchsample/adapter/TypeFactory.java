package com.esm.appsearchsample.adapter;

import android.view.View;

import com.esm.appsearchsample.adapter.viewholders.AbstractViewHolder;
import com.esm.appsearchsample.entities.AppSearchPerson;
import com.esm.appsearchsample.entities.AppSearchShortcut;

public interface TypeFactory {
    int type (AppSearchShortcut appSearchShortcut);
    int type (AppSearchPerson appSearchPerson);

    AbstractViewHolder createViewHolder(View parent, int type);

}
