package com.caiwo.caiwoapp.guide;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.caiwo.caiwoapp.home.MainActivity;
import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.app.MyApplication;
import com.caiwo.caiwoapp.utils.SpUtils;


public class SplashActivity extends Activity {
    RelativeLayout defaultSplashView;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private static final String TAG = SplashActivity.class.getSimpleName();

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent .ACTION_MAIN)) {
                finish(); return;
            }
        }

        clearNotification();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_splash);
//        StatusBarUtil.setColor(this, Color.parseColor("#d60000"),0);

        this.defaultSplashView = ((RelativeLayout)findViewById(R.id.default_splash));
         intData();



    }

    public void intData(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent localIntent;
//                Log.e("aaaccc","getIsfirst"+ SpUtils.getInstance(BaseApplication.getInstance()).getIsfirst());
                if(null != SpUtils.getInstance(MyApplication.getInstance()).getIsfirst()&&
                        !SpUtils.getInstance(MyApplication.getInstance()).getIsfirst().equals("")){
                    localIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(localIntent);
                }else{
                    localIntent = new Intent(SplashActivity.this, GuideActivity.class);
                    SplashActivity.this.startActivity(localIntent);
                }
                SplashActivity.this.finish();
            }
        }, 2000L);
    }
    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification(){
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
//        MiPushClient.clearNotification(getApplicationContext());
    }

    /**
     * 初始化
     */

    private void init(){
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
//        //初始化IMSDK
//        InitBusiness.start(getApplicationContext(),loglvl);
//        //设置刷新监听
////        FriendshipEvent.getInstance().init();
//        GroupEvent.getInstance().init();
//        RefreshEvent.getInstance();
//        //设置刷新监听
////        TIMUserConfig userConfig = new TIMUserConfig();
////        RefreshEvent.getInstance().init(userConfig);
////        userConfig = MessageEvent.getInstance().init(userConfig);
////        TIMManager.getInstance().setUserConfig(userConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this,"需要权限",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
