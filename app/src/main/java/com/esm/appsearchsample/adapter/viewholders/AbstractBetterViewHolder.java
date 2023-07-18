package com.esm.appsearchsample.adapter.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractBetterViewHolder<T> extends RecyclerView.ViewHolder {

    public AbstractBetterViewHolder(@NonNull View view) {
        super(view);
    }
    public abstract void bind(T element);

}
