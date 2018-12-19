package com.caiwo.caiwoapp.utils.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * 保存图片图片的方法
 */
public class SaveImgUtils extends PopupWindow {

    private Context mContext;
    private String mImageUrl;
    final LinearLayout ll_popup;
    private Bitmap bitmap;

    public void setmImageUrl(String ImageUrl) {
        this.mImageUrl = ImageUrl;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @SuppressWarnings("deprecation")
    public SaveImgUtils(Context mContext) {

        this.mContext = mContext;

        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_popupwindows2, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);

        TextView bt1 = (TextView) view
                .findViewById(R.id.item_popupwindows_camera);
        TextView bt2 = (TextView) view
                .findViewById(R.id.item_popupwindows_Photo);
        TextView bt3 = (TextView) view
                .findViewById(R.id.item_popupwindows_cancel);
        ImageView im = (ImageView) view
                .findViewById(R.id.popwindow_image_show);
        im.setVisibility(View.GONE);
        bt1.setVisibility(View.GONE);
        bt2.setText("保存图片");
        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ll_popup.clearAnimation();
            }
        });

        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mImageUrl.equals("liaotian")){
                    save2();
                }else{
                    saveBitmap(mImageUrl);
                }
                dismiss();
                ll_popup.clearAnimation();
            }
        });

        bt3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
                ll_popup.clearAnimation();
            }
        });

    }

    /**
     * 显示
     *
     * @param view
     */
    public void show(View view) {
        //添加动画
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 执行保存
     */
    private void saveBitmap(final String imgurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL pictureUrl = new URL(imgurl);
                    InputStream in = pictureUrl.openStream();
                    File imgfile = saveLocation(BitmapFactory.decodeStream(in));
                    in.close();
                    if (null != imgfile) {
                        // 最后通知图库更新
                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgfile)));
                        Looper.prepare();
                        ToastUtils.getInstance(mContext.getApplicationContext()).show("保存成功");

                        Looper.loop();

                    }
                } catch (Exception e) {
                    ToastUtils.getInstance(mContext.getApplicationContext()).show("保存失败");
                }
            }
        }).start();

    }

    public void save2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File imgfile = saveLocation(bitmap);
                    if (null != imgfile) {
                        // 最后通知图库更新
                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgfile)));
                        Looper.prepare();
                        ToastUtils.getInstance(mContext.getApplicationContext()).show("保存成功");

                        Looper.loop();
                    }
                    bitmap.recycle();
                    bitmap = null;
                } catch (Exception e) {
                    ToastUtils.getInstance(mContext.getApplicationContext()).show("保存失败");
                }
            }
        }).start();
    }

    /**
     * 保存本地
     */
    private File saveLocation(Bitmap bm) {


        String picName = System.currentTimeMillis() + ".jpg";

        String sdStatus = Environment.getExternalStorageState();

        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.getInstance(mContext.getApplicationContext()).show("SD卡不可用");
        }

        File appfile = new File(Environment.getExternalStorageDirectory(), "/rongba/");

        if (!appfile.exists()) {
            appfile.mkdirs();// 创建文件夹
        }

        File file = new File(appfile, picName);

        // 写入SD卡
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}
