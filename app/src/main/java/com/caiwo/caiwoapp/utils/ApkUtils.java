package com.caiwo.caiwoapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 */
public class ApkUtils {
    /**
     * This method provide the package's name.
     *
     * @param context
     * @return String name
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String packageName = "0";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
            packageName = info.packageName;
        } catch (Exception e) {
            Log.e("ApkInfo", e+"");
        }
        return packageName;
    }

    /**
     * This method provide the package's version name.
     *
     * @param context
     * @return String name
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String versionName = "0";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
            versionName = info.versionName;
        } catch (Exception e) {
            Log.e("ApkInfo", e+"");
        }
        return versionName;
    }

    /**
     * This method provide the package's version code
     *
     * @param context
     * @return int code
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            Log.e("ApkInfo", e+"");
        }
        return versionCode;
    }
}

