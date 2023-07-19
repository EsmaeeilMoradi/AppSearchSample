package com.esm.appsearchsample;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.esm.appsearchsample.entities.AppSearchShortcut;

import java.util.ArrayList;

public class GlobalSearchListAdapter extends RecyclerView.Adapter<GlobalSearchListAdapter.ViewHolder> {
    private ArrayList<AppSearchShortcut> appSearchListData;
    private OnSearchListener mOnSearchListener;

    public GlobalSearchListAdapter(ArrayList<AppSearchShortcut> appSearchListData, OnSearchListener OnSearchListener) {
        this.appSearchListData = appSearchListData;
        this.mOnSearchListener = OnSearchListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.viewholder_shortcut, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem, mOnSearchListener);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AppUtils.setBackgroundListShortcutItem(holder, position, appSearchListData);


        holder.tvShortcutName.setText(appSearchListData.get(position).getShortLabel());
        holder.tvAppName.setText(appSearchListData.get(position).getAppName());
        holder.imgAppIcon.setImageDrawable(appSearchListData.get(position).getAppIcon());
        holder.imgShortcutIcon.setImageDrawable(appSearchListData.get(position).getShortcutIcon());
        if (appSearchListData.get(position).getAppIcon() == null){
         holder.layoutShortcutTittle.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    public int getItemCount() {
        return appSearchListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgShortcutIcon;
        public ImageView imgAppIcon;
        public TextView tvShortcutName;
        public TextView tvAppName;
        public CardView cvShortcutDescription;
        public LinearLayout layoutShortcutTittle;

        OnSearchListener onSearchListener;

        public ViewHolder(View itemView, OnSearchListener onSearchListener) {
            super(itemView);
            this.cvShortcutDescription = (CardView) itemView.findViewById(R.id.cv_desc);
            this.layoutShortcutTittle = (LinearLayout) itemView.findViewById(R.id.layout_tittle);
            this.tvAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
            this.imgShortcutIcon = (ImageView) itemView.findViewById(R.id.img_shortcut_icon);
            this.tvShortcutName = (TextView) itemView.findViewById(R.id.tv_shortcut_name);
            this.imgAppIcon = itemView.findViewById(R.id.img_app_icon);
            this.onSearchListener = onSearchListener;
            cvShortcutDescription.setOnClickListener(this);
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