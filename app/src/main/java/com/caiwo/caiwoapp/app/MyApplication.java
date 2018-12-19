package com.caiwo.caiwoapp.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import com.caiwo.caiwoapp.utils.HandlerExcetionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    public static String loginUrl = "";
    private static MyApplication instance;

    private static int screenWidth;//屏幕宽度
    private static int screenHeight;//屏幕高度
    public static boolean isWriteAndRedePermission;//是否获取到读写权限
    public static boolean isCamerPermission;//是否获取到相机权限
    private static TelephonyManager tm;
    private static NetworkInfo networkInfo;
    public static Map<String, String> map = new HashMap<>();
    public static List<String> historicalSearch_list = new ArrayList<String>();

    public static MyApplication getInstance() {
        return instance;
    }

    public static String appState = "online";
    public static int Code = 0x185;

    public void onCreate() {
        super.onCreate();
        instance = this;
//        Foreground.init(this);
        //防止自踢
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase("com.rongba.xindai")) {
            return;
        }
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                "669c30a9584623e70e8cd01b0381dcb4");
//        Config.DEBUG = false;
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.init(this, "576fe33fe0f55ad0c5001317", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        UMShareAPI.init(this, "576fe33fe0f55ad0c5001317");
//        UMShareAPI.get(this);
        //监听收集Excetion
//        HandlerExcetionUtils crashHandler = HandlerExcetionUtils.getInstance();
//        crashHandler.init(getApplicationContext());

        initSDK();
//        if (MsfSdkUtils.isMainProcess(this)) {
//            push();
////            registerPush();
//            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
//                @Override
//                public void handleNotification(TIMOfflinePushNotification notification) {
//                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
//                        //消息被设置为需要提醒
////                       notification.doNotify(getApplicationContext(), R.mipmap.logo);
////                       Intent intent = new Intent(BaseApplication.getInstance(),MainActivity.class);
////                        st artActivity(intent);
//                    }
//                }
//            });
//        }

        getWindowSize();
//        PlatformConfig.setWeixin("wx94e60e3acfa94052", "a9e17d4ac4e3009498c1d3638a1ea5f7");
//        PlatformConfig.setQQZone("1105415249", "qjMApk8KvvbqYpdB");
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public void quitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 初始化SDK，包括Bugly，IMSDK，RTMPSDK等
     */
    public void initSDK() {
        //启动bugly组件，bugly组件为腾讯提供的用于crash上报和分析的开放组件，如果您不需要该组件，可以自行移除
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
//        strategy.setAppVersion(TXLiveBase.getSDKVersionStr());
//        CrashReport.initCrashReport(getApplicationContext(), TCConstants.BUGLY_APPID, true, strategy);
        //设置rtmpsdk log回调，将log保存到文件
//        TXLiveBase.setListener(new TCLog(getApplicationContext()));
        //初始化APP
//        InitBusinessHelper.initApp(this);

    }


    /**
     * 获取全局屏幕宽度
     */
    public static int getScreenWidth() {
        return screenWidth;
    }


    /**
     * 获取全局屏幕高度
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * 获取屏幕的宽高
     */
    private void getWindowSize() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        @SuppressWarnings("deprecation") final int height = wm.getDefaultDisplay().getHeight();
        @SuppressWarnings("deprecation") final int width = wm.getDefaultDisplay().getWidth();
        screenHeight = height;
        screenWidth = width;

    }

    /**
     * 防止被自踢封装（IM）
     *
     * @param pID
     * @return
     */

    public String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }

    /**
     * 注册推送
     */
    public void push() {
//        String deviceMan = Build.MANUFACTURER;
//        //注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
//            MiPushClient.registerPush(BaseApplication.getInstance(), "2882303761517493962", "5141749322962");
//        } else if (deviceMan.equals("HUAWEI")) {
//            PushManager.requestToken(BaseApplication.getInstance());
//        }

    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = MyApplication.getInstance().getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 存储旧的搜索记录
     *
     * @param data
     */
    public static void addSearchRecord(String data) {
        if (!historicalSearch_list.contains(data)) {
            if (historicalSearch_list.size() < 5) {
                historicalSearch_list.add(data);
            } else {
                historicalSearch_list.remove(0);
                historicalSearch_list.add(data);
            }

        }
    }
}
