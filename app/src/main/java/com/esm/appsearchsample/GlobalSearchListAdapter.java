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

public class GlobalSearchListAdapter extends RecyclerView.Adapter<GlobalSearchListAdapter.ViewHolder> {
    private ArrayList<GlobalSearchListData> listdata;

    public GlobalSearchListAdapter(ArrayList<GlobalSearchListData> listdata) {
        this.listdata = listdata;

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

        if (listdata.get(position).getAppName().equals("")) {
            holder.textViewAppName.setVisibility(View.GONE);

        } else {
            holder.textViewAppName.setVisibility(View.VISIBLE);
        }

        try {
            if (((position + 1) < listdata.size()) && (listdata.get(position).getAppName() == "") && (listdata.get(position + 1).getAppName() != "")) {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
            } else {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_middle);
            }

            if (((position + 1) < listdata.size()) && (listdata.get(position).getAppName() != "") && (listdata.get(position + 1).getAppName() != "")) {
                holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (position == listdata.size() - 1) {
            holder.linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_bottom);

        }


        holder.textViewAppName.setText(listdata.get(position).getAppName());

        holder.textView.setText(listdata.get(position).getDescription());
        holder.imageView.setImageDrawable(listdata.get(position).getImgId());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + listdata.get(position).getDescription(), Toast.LENGTH_LONG).show();

                Intent intent;
                try {
                    intent = Intent.parseUri(listdata.get(position).getIntent()[0], 0);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                view.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textViewAppName;
        public CardView linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.linearLayout = (CardView) itemView.findViewById(R.id.card_view);
            this.textViewAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
        }
    }

}