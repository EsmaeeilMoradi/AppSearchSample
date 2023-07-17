package com.esm.appsearchsample;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GlobalSearchListAdapter extends RecyclerView.Adapter<GlobalSearchListAdapter.ViewHolder> {
    private ArrayList<GlobalSearchListData> globalSearchListData;
    private OnSearchListener mOnSearchListener;

    public GlobalSearchListAdapter(ArrayList<GlobalSearchListData> globalSearchListData, OnSearchListener OnSearchListener) {
        this.globalSearchListData = globalSearchListData;
        this.mOnSearchListener = OnSearchListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem, mOnSearchListener);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AppUtils.setBackgroundListItem(holder, position, globalSearchListData);


        holder.textView.setText(globalSearchListData.get(position).getDescription());
        holder.textViewAppName.setText(globalSearchListData.get(position).getAppName());
        holder.appIcon.setImageDrawable(globalSearchListData.get(position).getAppIcon());

        holder.imageView.setImageDrawable(globalSearchListData.get(position).getImgId());


    }


    @Override
    public int getItemCount() {
        return globalSearchListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public ImageView appIcon;
        public TextView textView;
        public TextView textViewAppName;
        public CardView linearLayout;

        OnSearchListener onSearchListener;

        public ViewHolder(View itemView, OnSearchListener onSearchListener) {
            super(itemView);
            this.linearLayout = (CardView) itemView.findViewById(R.id.card_view);
            this.textViewAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.appIcon=  itemView.findViewById(R.id.img_app_icon);
            this.onSearchListener = onSearchListener;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSearchListener.onSearchClick(getAdapterPosition());
        }
    }
    public interface OnSearchListener {
        void onSearchClick(int position);
    }
}