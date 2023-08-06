package com.esm.appsearchsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.esm.appsearchsample.adapter.viewholders.AbstractViewHolder;

import java.util.List;

public class Adapter_Visitor extends RecyclerView.Adapter<AbstractViewHolder> {

    private final List<Visitable> elements;
    private final TypeFactory typeFactory;

    public Adapter_Visitor(List<Visitable> elements, TypeFactory typeFactory) {
        this.elements = elements;
        this.typeFactory = typeFactory;
    }

    public void insertData(List<Visitable> insertList) {
        /*
        This function will add new data to RecyclerView
         */
        DiffUtilCallback diffUtilCallback = new DiffUtilCallback(elements, insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        elements.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }


    public void updateData(List<Visitable> newList) {
        DiffUtilCallback diffUtilCallback = new DiffUtilCallback(elements, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);

        elements.clear();
        elements.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }


    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        /**
         * attention: {@link viewType} as resource
         */
        View contactView = LayoutInflater.from(context).inflate(viewType, parent, false);
        return typeFactory.createViewHolder(contactView, viewType);
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        /**
         * attention: unchecked
         */
        holder.bind(elements.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return elements.get(position).type(typeFactory);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

}