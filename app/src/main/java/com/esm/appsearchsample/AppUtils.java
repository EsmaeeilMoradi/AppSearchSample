package com.esm.appsearchsample;

import android.app.appsearch.SearchResult;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.esm.appsearchsample.entities.AppSearchShortcut;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    public static String getAppNameFromPkgName(Context context, String PackageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo info = packageManager.getApplicationInfo(PackageName, PackageManager.GET_META_DATA);
            String appName = (String) packageManager.getApplicationLabel(info);
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Drawable getAppIconFromPkgName(Context context, String PackageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Drawable d = packageManager.getApplicationIcon(PackageName);
            return d;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getShortcutIconFromIconResId(Context context, List<SearchResult> searchResults, int position) throws PackageManager.NameNotFoundException {
        Resources res = context.getPackageManager().getResourcesForApplication(searchResults.get(position).getGenericDocument().getNamespace());
        int iconResId = (int) searchResults.get(position).getGenericDocument().getPropertyLong("iconResId");
        Drawable appIconDrawable = (iconResId != 0) ? res.getDrawable(iconResId) : context.getDrawable(R.drawable.border);
        return appIconDrawable;
    }

    public static void setBackgroundListShortcutItem(@NonNull GlobalSearchListAdapter.ViewHolder holder, int position, ArrayList<AppSearchShortcut> listdata) {
        if (listdata.get(position).getAppName().equals("")) {
            holder.tvAppName.setVisibility(View.GONE);
        } else {
            holder.tvAppName.setVisibility(View.VISIBLE);
        }
        try {
            if (((position + 1) < listdata.size()) && (listdata.get(position).getAppName() == "") && (listdata.get(position + 1).getAppName() != "")) {
                holder.cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
            } else {
                holder.cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_middle);
            }
            if (((position + 1) < listdata.size()) && (listdata.get(position).getAppName() != "") && (listdata.get(position + 1).getAppName() != "")) {
                holder.cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_bottom);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (position == listdata.size() - 1) {
            holder.cvShortcutDescription.setBackgroundResource(R.drawable.lmo_preference_background_bottom);
        }

        if (listdata.get(position).getAppIcon() == null) {
            holder.imgAppIcon.setVisibility(View.GONE);
        } else {
            holder.imgAppIcon.setVisibility(View.VISIBLE);

        }
    }

}
