package com.esm.appsearchsample.adapter.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.esm.appsearchsample.GlobalSearchListAdapter;
import com.esm.appsearchsample.entities.AppSearchShortcut;
import com.esm.appsearchsample.R;

public class GlobalViewHolder extends AbstractBetterViewHolder<AppSearchShortcut> {

    @LayoutRes
    public static final int LAYOUT = R.layout.viewholder_shortcut;
    public ImageView imgShortcutIcon = (ImageView) itemView.findViewById(R.id.img_shortcut_icon);
    public ImageView imgAppIcon = itemView.findViewById(R.id.img_app_icon);
    public TextView tvShortcutName = (TextView) itemView.findViewById(R.id.tv_shortcut_name);
    public TextView tvAppName = (TextView) itemView.findViewById(R.id.txt_app_name);
    public CardView cvShortcutDescription = (CardView) itemView.findViewById(R.id.cv_desc);

    public LinearLayout layoutShortcutTittle = (LinearLayout) itemView.findViewById(R.id.layout_tittle);
    public LinearLayout layoutShortcutMain = (LinearLayout) itemView.findViewById(R.id.layout_main);


    GlobalSearchListAdapter.OnSearchListener onSearchListener;

    public GlobalViewHolder(@NonNull View view) {
        super(view);
    }


    @Override
    public void bind(AppSearchShortcut element) {


        cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_middle);
        if (element.getAppIcon() == null) {
            layoutShortcutTittle.setVisibility(View.GONE);
            setMargins(layoutShortcutMain,0,0,0,0);
        } else {
            layoutShortcutTittle.setVisibility(View.VISIBLE);
            setMargins(layoutShortcutMain,4,4,4,4);

        }

        tvShortcutName.setText(element.getShortLabel());
        tvAppName.setText(element.getAppName());
        imgAppIcon.setImageDrawable(element.getAppIcon());
        imgShortcutIcon.setImageDrawable(element.getShortcutIcon());


    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
