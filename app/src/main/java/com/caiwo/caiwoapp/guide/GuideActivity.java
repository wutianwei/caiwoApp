package com.caiwo.caiwoapp.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import com.caiwo.caiwoapp.home.MainActivity;
import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.app.MyApplication;
import com.caiwo.caiwoapp.customview.flowview.CircleFlowIndicator;
import com.caiwo.caiwoapp.customview.flowview.FlowView;
import com.caiwo.caiwoapp.utils.SpUtils;

import java.util.ArrayList;

/**
 * 欢迎页 指南
 */
public class GuideActivity extends Activity {
    boolean isFromMore = true;
    int currentPosition;
    View photoFliperWraper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SpUtils.getInstance(MyApplication.getInstance()).putIsfirst("no");
        setContentView(R.layout.activity_guide);

        init();
    }

    private void init() {
        photoFliperWraper = this.findViewById(R.id.photoFliperWraper);
        FlowView guideFlowView = (FlowView) findViewById(R.id.photoFlow);
        guideFlowView.setOnViewSwitchListener(new FlowView.ViewSwitchListener() {

            @Override
            public void onSwitched(View view, int position) {
                currentPosition = position;
                if (position == 2) {
                    if (isFromMore) {
//                        Toast.makeText(GuideActivity.this, "已经是最后一张了,点击关闭", Toast.LENGTH_SHORT).show();
                    } else {
//                        Intent intent = new Intent(GuideActivity.this, HomeWebActivity.class);
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onLastPageMove() {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        CircleFlowIndicator guideIndicator = (CircleFlowIndicator) findViewById(R.id.photoIndicator);

        ArrayList<Integer> photos = new ArrayList<Integer>();
//        photos.add(R.drawable.guide_1);
//        photos.add(R.drawable.guide_2);
//        photos.add(R.drawable.guide_3);
//        photos.add(R.drawable.guide_4);


        FlowGuideAdapter guideAdapter = new FlowGuideAdapter(this, photos);
        guideAdapter.setOnPhotoItemClickListener(new FlowGuideAdapter.OnPhotoItemClickListener(){

            @Override
            public void onClick(int position) {
                if (position == 3 && isFromMore) {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
        guideFlowView.setAdapter(guideAdapter);
        guideFlowView.setFlowIndicator(guideIndicator);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
