package com.caiwo.caiwoapp.customview;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.caiwo.caiwoapp.utils.ApkUtils;
import com.caiwo.caiwoapp.utils.DeviceUtils;


public class CustomWebView extends WebView {

    public CustomWebView(Context paramContext) {
        super(paramContext);
        init();
    }


    public CustomWebView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public CustomWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void configSapiWebView() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSaveFormData(false);
        getSettings().setDomStorageEnabled(true);
        getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = this.getContext().getCacheDir().getAbsolutePath();
        getSettings().setAppCachePath(appCachePath);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCacheEnabled(true);
    }

    public boolean back() {
        if (canGoBackOrForward(-1)) {
            goBackOrForward(-1);
            return true;
        }
        return false;
    }

    public void finish() {
        ((Activity) getContext()).finish();
    }

    public boolean forward() {
        if (canGoBackOrForward(1)) {
            goBackOrForward(1);
            return true;
        }
        return false;
    }

    public void init() {
        configSapiWebView();

        addJavascriptInterface(new HotouziWebViewShell(), "交互时双方定义的名字");
    }

    public void loadUrl(String paramString) {
        super.loadUrl(paramString);
    }


    final class HotouziWebViewShell {
        HotouziWebViewShell() {
        }

        @JavascriptInterface
        public String getCUID() {
            return DeviceUtils.getIMEI(CustomWebView.this.getContext()) + "";
        }

        @JavascriptInterface
        public String getVersion() {
            String versionName = ApkUtils.getVersionName(CustomWebView.this.getContext());
            return versionName;
        }

        @JavascriptInterface
        public int getVersioncode() {
            int versionCode = ApkUtils.getVersionCode(CustomWebView.this.getContext());
            return versionCode;
        }

        @JavascriptInterface
        public void showUpload(String paramString1, String paramString2) {
            ActivityManager am = (ActivityManager) CustomWebView.this.getContext().getSystemService(CustomWebView.this.getContext().ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;//

        }

        //H5交互
        @JavascriptInterface
        public String saveData(String jsonStr) {
            return "";
        }


        @JavascriptInterface
        public String getversionName() {
            String versionName = ApkUtils.getVersionName(CustomWebView.this.getContext());
            return versionName;
        }

        @JavascriptInterface
        public void updateInfo() {
            finish();
        }
    }
}
