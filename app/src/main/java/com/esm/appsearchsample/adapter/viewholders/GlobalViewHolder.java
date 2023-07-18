package com.esm.appsearchsample.adapter.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.esm.appsearchsample.GlobalSearchListAdapter;
import com.esm.appsearchsample.GlobalSearchListData;
import com.esm.appsearchsample.R;
import com.esm.appsearchsample.entities.AppSearchShortcutData;

public class GlobalViewHolder extends AbstractBetterViewHolder<GlobalSearchListData> {

    @LayoutRes
    public static final int LAYOUT = R.layout.list_item;
    public ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
    public ImageView appIcon = itemView.findViewById(R.id.img_app_icon);
    public TextView textView = (TextView) itemView.findViewById(R.id.textView);
    public TextView textViewAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
    public CardView linearLayout = (CardView) itemView.findViewById(R.id.card_view);


    GlobalSearchListAdapter.OnSearchListener onSearchListener;

    public GlobalViewHolder(@NonNull View view) {
        super(view);
    }



    @Override
    public void bind(GlobalSearchListData element) {
        linearLayout.setBackgroundResource(R.drawable.lmo_preference_background_middle);

        textView.setText(element.getDescription());
        textViewAppName.setText(element.getAppName());
        appIcon.setImageDrawable(element.getAppIcon());
        imageView.setImageDrawable(element.getImgId());


    }
}
