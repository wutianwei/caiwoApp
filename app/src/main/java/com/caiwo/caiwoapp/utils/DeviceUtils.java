package com.caiwo.caiwoapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: xulingzhi
 * Date: 14-8-6
 * Time: 上午8:22
 * To change this template use File | Settings | File Templates.
 */
public class DeviceUtils {
    /**
     * mSettings 对应的存储文件。
     */
    private static final String PREFS_NAME = "identity";

    /**
     * 获取当前的UID.
     *
     * @param context context
     * @return current uid
     */
//    public static String getUID(Context context) {
//
//        final String keyUid = "uid_v3"; //格式 deviceid|imei逆序，其中deviceid为：MD5(imei+androidid+UUID)
//        SharedPreferences mSettings = context.getSharedPreferences(PREFS_NAME, 0);
//        String uid = mSettings.getString(keyUid, ""); // 从本地获取
//
//        if (TextUtils.isEmpty(uid)) {
//            // 如果本地文件不存在，则新生成一个uid，然后存储到本地
//            uid = generateUID(context);
//            if (!TextUtils.isEmpty(uid)) {
//                uid = uid.replace("|", "_");
//            }
//            // write to local file
//            SharedPreferences.Editor editor = mSettings.edit();
//            editor.putString(keyUid, uid);
//            editor.commit();
//        }
//
//        return uid;
//    }

    /**
     * 生成新的UID.
     * 格式 deviceid|imei逆序
     *
     * @param context context
     * @return generated uid.
     */
//    private static String generateUID(Context context) {
//        return CommonParam.getCUID(context);
//    }

    /**
     * 获取imei
     *
     * @param context 上下文
     * @return imei号
     */
    public static String getIMEI(Context context) {
        if (context == null) {
            return null;
        }
        if (context.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE,
                context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
        } else {
            return "notavailable";
        }
    }

    /**
     * 获取Mac
     *
     * @param context 上下文
     * @return mac地址
     */
    public static String getMac(Context context) {
        if (context == null) {
            return null;
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            return TextUtils.isEmpty(info.getMacAddress()) ? "" : info.getMacAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取OS Version
     *
     * @return OS Version
     */
    public static String getOSVersion() {
        try {
            return URLEncoder.encode(TextUtils.isEmpty(Build.VERSION.RELEASE) ? ""
                    : Build.VERSION.RELEASE, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取品牌
     *
     * @return 品牌
     */
    public static String getBrandName() {
        try {
            return URLEncoder.encode(TextUtils.isEmpty(Build.BRAND) ? "" : Build.BRAND, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取机型
     *
     * @return 机型
     */
    public static String getBrandMode() {
        try {
            return URLEncoder.encode(TextUtils.isEmpty(Build.MODEL) ? "" : Build.MODEL, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成设备号信息，用于设备号登录
     *
     * @return
     */
    public static String createDeviceInfo() {
        StringBuilder deviceInfo = new StringBuilder();
        deviceInfo.append("os_version=").append(getOSVersion()).append("&brand_name=")
                .append(getBrandName()).append("&brand_model=").append(getBrandMode())
                .append("&os_type=").append("Android");
        return deviceInfo.toString();
    }
}
