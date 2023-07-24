package com.esm.appsearchsample;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class GlobalSearchAdapter extends RecyclerView.Adapter<GlobalSearchAdapter.ViewHolder> {
    private ArrayList<GlobalSearchData> globalSearchDataList;

    public GlobalSearchAdapter(ArrayList<GlobalSearchData> globalSearchDataList) {
        this.globalSearchDataList = globalSearchDataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (globalSearchDataList.get(position).getAppName().equals("")) {
            holder.textViewAppName.setVisibility(View.GONE);
        } else {
            holder.textViewAppName.setVisibility(View.VISIBLE);
        }

        try {
            if (((position + 1) < globalSearchDataList.size()) && (globalSearchDataList.get(position).getAppName() == "")
                    && (globalSearchDataList.get(position + 1).getAppName() != "")) {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
            } else {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_middle);
            }

            if (((position + 1) < globalSearchDataList.size()) && (globalSearchDataList.get(position).getAppName() != "") &&
                    (globalSearchDataList.get(position + 1).getAppName() != "")) {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (position == globalSearchDataList.size() - 1) {
            holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
        }

        holder.textViewAppName.setText(globalSearchDataList.get(position).getAppName());
        holder.textView.setText(globalSearchDataList.get(position).getLabel());
        holder.imageView.setImageDrawable(globalSearchDataList.get(position).getIcon());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + globalSearchDataList.get(position).getLabel(), Toast.LENGTH_LONG).show();
                Intent intent;
                try {
                    intent = Intent.parseUri(globalSearchDataList.get(position).getIntent()[0], 0);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return globalSearchDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textViewAppName;
        public CardView linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.img_icon);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.linearLayout = (CardView) itemView.findViewById(R.id.card_view);
            this.textViewAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
        }
    }

}