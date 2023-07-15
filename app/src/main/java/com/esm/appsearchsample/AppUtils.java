package com.esm.appsearchsample;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AppUtils {
    public static String getAppNameFromPkgName(Context context, String Packagename) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo info = packageManager.getApplicationInfo(Packagename, PackageManager.GET_META_DATA);
            String appName = (String) packageManager.getApplicationLabel(info);
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setBackgroundListItem(@NonNull GlobalSearchListAdapter.ViewHolder holder, int position, ArrayList<GlobalSearchListData> listdata) {
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
    }

}
