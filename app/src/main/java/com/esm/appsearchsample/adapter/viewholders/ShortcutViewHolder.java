package com.esm.appsearchsample.adapter.viewholders;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.esm.appsearchsample.AppUtils;
import com.esm.appsearchsample.GlobalSearchListAdapter;
import com.esm.appsearchsample.entities.AppSearchShortcut;
import com.esm.appsearchsample.R;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ShortcutViewHolder extends AbstractBetterViewHolder<AppSearchShortcut> {

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

    public ShortcutViewHolder(@NonNull View view) {
        super(view);
    }


    @Override
    public void bind(AppSearchShortcut element) {

        if (element.getAppIcon() != null) {
            layoutShortcutTittle.setVisibility(View.VISIBLE);
            setMargins(layoutShortcutMain, 4, 8, 4, 4);
            cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_middle);
        } else {
            layoutShortcutTittle.setVisibility(View.GONE);
            setMargins(layoutShortcutMain, 0, 0, 0, 0);
            cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
        }

        Log.e("ESM", "bind: "+element.getAppName() );


        tvShortcutName.setText(element.getShortLabel());
        tvAppName.setText(element.getAppName());
        imgAppIcon.setImageDrawable(element.getAppIcon());
        imgShortcutIcon.setImageDrawable(element.getShortcutIcon());

        cvShortcutDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + element.getShortLabel(), Toast.LENGTH_LONG).show();
                Intent intent;
                try {
                    intent = Intent.parseUri(element.getShortcutIntent()[0], 0);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                view.getContext().startActivity(intent);


            }
        });


    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
