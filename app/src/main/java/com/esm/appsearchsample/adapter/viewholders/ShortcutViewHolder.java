package com.esm.appsearchsample.adapter.viewholders;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.esm.appsearchsample.R;
import com.esm.appsearchsample.entities.AppSearchShortcutData;


public class ShortcutViewHolder extends AbstractBetterViewHolder<AppSearchShortcutData> {
    @LayoutRes
    public static final int LAYOUT = R.layout.viewholder_shortcut;
    CardView cvShortcut = itemView.findViewById(R.id.cv_shortcut);
    TextView tvAppName = itemView.findViewById(R.id.tv_app_name);
    ImageView ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
    TextView tvShortName = itemView.findViewById(R.id.tv_shortname);

    public ShortcutViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(AppSearchShortcutData element) {
        cvShortcut.setBackgroundResource(R.drawable.lmo_preference_background_middle);
        tvAppName.setText(element.getNamespace());
        ivAppIcon.setImageResource(element.getIconResId());
        tvShortName.setText(element.getShortLabel());
    }
}
