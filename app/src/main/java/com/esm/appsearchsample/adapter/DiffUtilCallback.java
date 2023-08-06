package com.esm.appsearchsample.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {

    private List<Visitable> oldList;
    private List<Visitable> newList;

    public DiffUtilCallback(List<Visitable> oldList, List<Visitable> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
