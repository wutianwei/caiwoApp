package com.caiwo.caiwoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences-本地缓存
 */
public class SpUtils {

    public static final String FILE_NAME = "save_datas";
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static SpUtils save;
    static final Object sInstanceSync = new Object();

    private String MEMBER_AVATAR = "member_avatar";
    private String MEMBER_PHONE = "member_phone";
    private String MEMBER_NAME = "member_name";
    private String IMUSERNAME = "imUsername";
    private String IMPASSWORD = "imPassword";
    private String USER_ID = "user_id";
    private String NICKNAME = "nickname";
    private String HEADIMG = "headimg";
    private String ISFRIST = "";
    private String IShaveTives3 = "";
    private String QunGongGao = "qungonggao";
    private String Search_RECODER = "no";

    public SpUtils(Context context) {
        try {
            sp = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            editor = sp.edit();
        } catch (Exception ex) {
        }
        ;
    }


    /**
     * 获取实例
     *
     * @return SpUtils
     */
    public static SpUtils getInstance(Context context) {
        synchronized (sInstanceSync) {
            if (null != save) {
                return save;
            }
            save = new SpUtils(context);
        }
        return save;
    }

    /**
     * 存储发现模块的搜索记录
     *
     * @param value
     */
    public void putFind_RECODER(String value) {
        saveString(Search_RECODER, value);
    }

    /**
     * 获取发现模块的搜索记录
     *
     * @return
     */
    public String getFind_RECODER() {
        return getString(Search_RECODER, "");
    }

    /**
     * 发现模块的搜索记录 清空
     */
    public void deleteFind_RECODER() {
        putFind_RECODER("");
    }

    /**
     * 保存第一次启动App 标志
     */
    public void putGroupNotify(String value) {
        saveString(QunGongGao, value);
    }

    /**
     * 获取第一次启动App 标志
     */
    public String getGroupNotify() {
        return getString(QunGongGao, "");
    }

    /**
     * 保存第一次启动App 标志
     */
    public void putIsfirst(String value) {
        saveString(ISFRIST, value);
    }

    /**
     * 获取第一次启动App 标志
     */
    public String getIsfirst() {
        return getString(ISFRIST, "");
    }

    /**
     * 保存第一次启动App导航 标志
     */
    public void putIsHaveTive(String value) {
        saveString(IShaveTives3, value);
    }

    /**
     * 获取第一次启动App导航 标志
     */
    public String getIShaveTive() {
        return getString(IShaveTives3, "");
    }

    /**
     * 保存用户头像
     */
    public void putHead(String value) {
        saveString(HEADIMG, value);
    }

    /**
     * 获取用户头像
     */
    public String getHead() {
        return getString(HEADIMG, "");
    }

    /**
     * 保存用户Id
     */
    public void putUserId(String value) {
        saveString(USER_ID, value);
    }

    /**
     * 获取用户Id
     */
    public String getUserId() {
        return getString(USER_ID, "");
    }

    /**
     * 保存IM账号
     */
    public void putImUsername(String value) {
        saveString(IMUSERNAME, value);
    }

    /**
     * 获取IM账号
     */
    public String getImUsername() {
        return getString(IMUSERNAME, "");
    }

    /**
     * 保存IM 密码
     */
    public void putImPassword(String value) {
        saveString(IMPASSWORD, value);
    }

    /**
     * 获取IM密码
     */
    public String getImPassword() {
        return getString(IMPASSWORD, "");
    }


    /**
     * 保存 NICKNAME
     *
     * @param value
     */
    public void putNickName(String value) {
        saveString(NICKNAME, value);
    }

    /**
     * 获取 NICKNAME
     *
     * @return
     */
    public String getNickName() {
        return getString(NICKNAME, "");
    }


    /**
     * 保存 MEMBER_AVATAR
     *
     * @param value
     */
    public void putMemberAvatar(String value) {
        saveString(MEMBER_AVATAR, value);
    }

    /**
     * 获取 MEMBER_AVATAR
     *
     * @return
     */
    public String getMemberAvatar() {
        return getString(MEMBER_AVATAR, "");
    }

    /**
     * 保存 MEMBER_PHONE
     *
     * @param value
     */
    public void putMemberPhone(String value) {
        saveString(MEMBER_PHONE, value);
    }

    /**
     * 获取 MEMBER_PHONE
     *
     * @return
     */
    public String getMemberPhone() {
        return getString(MEMBER_PHONE, "");
    }


    /**
     * 保存 MEMBER_NAME
     *
     * @param value
     */
    public void putMemberName(String value) {
        saveString(MEMBER_NAME, value);
    }

    /**
     * 获取 MEMBER_NAME
     *
     * @return
     */
    public String getMemberName() {
        return getString(MEMBER_NAME, "");
    }

    /**
     * 保存String型数据
     *
     * @param key
     * @param value
     */
    public void saveString(String key, String value) {

        try {
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
        }

    }

    /**
     * 从sp中得到数据 默认值为 ""
     *
     * @param key
     * @param dafaultValue
     * @return
     */
    public String getString(String key, String dafaultValue) {
        if (null != sp) {
            return sp.getString(key, dafaultValue);
        } else {
            return dafaultValue;
        }
    }


    /**
     * 保存int型数据
     *
     * @param key
     * @param value
     */
    private void saveInt(String key, int value) {
        try {
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
        }

    }

    /**
     * 从sp中得到数据 默认值为0
     *
     * @param key
     * @param dafaultValue
     * @return
     */
    private int getInt(String key, int dafaultValue) {
        if (null != sp) {
            return sp.getInt(key, dafaultValue);
        } else {
            return 0;
        }
    }

    /**
     * 保存long型数据
     *
     * @param key
     * @param value
     */
    private void saveLong(String key, long value) {
        try {
            editor.putLong(key, value);
            editor.commit();
        } catch (Exception e) {
        }

    }

    /**
     * 从sp中得到数据 默认值为0L
     *
     * @param key
     * @param dafaultValue
     * @return
     */
    private long getLong(String key, long dafaultValue) {
        if (null != sp) {
            return sp.getLong(key, dafaultValue);
        } else {
            return 0L;
        }
    }

    /**
     * 保存float型数据
     *
     * @param key
     * @param value
     */
    private void saveFloat(String key, float value) {
        try {
            editor.putFloat(key, value);
            editor.commit();
        } catch (Exception e) {
        }

    }

    /**
     * 从sp中得到数据 默认值为0.0f
     *
     * @param key
     * @param dafaultValue
     * @return
     */
    private float getFloat(String key, float dafaultValue) {
        if (null != sp) {
            return sp.getFloat(key, dafaultValue);
        } else {
            return 0.0f;
        }
    }

    /**
     * 保存Boolean型数据
     *
     * @param key
     * @param value
     */
    private void saveBoolean(String key, boolean value) {
        try {
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception e) {
        }

    }

    /**
     * 从sp中得到数据 默认值为 flase
     *
     * @param key
     * @param dafaultValue
     * @return
     */
    private boolean getBoolean(String key, boolean dafaultValue) {
        if (null != sp) {
            return sp.getBoolean(key, dafaultValue);
        } else {
            return false;
        }
    }

}
