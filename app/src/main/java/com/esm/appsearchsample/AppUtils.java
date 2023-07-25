package com.esm.appsearchsample;

import android.app.appsearch.SearchResult;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

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

}
