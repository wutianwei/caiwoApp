package com.caiwo.caiwoapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.message.activity.MessageActivity;

public class MainActivity extends Activity implements View.OnClickListener, IResultHandler {

    private ImageView home_mine, home_message;
    private EditText search_screen_edt;
    private ImageView home_kehuguanli, home_qudaoguanli;
    private TextView home_screen_yewuleixing, home_screen_edu;
    private TextView home_screen_yewuzhuangtai, home_screen_time;
    private ListView home_list;
    private MainAdapter adapter;
    private ImageView home_screen_time_img,home_screen_yewuzhuangtai_img,
            home_screen_edu_img,home_screen_yewuleixing_img;
    private int time = 0;
//    private TextBannerView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    /**
     * 控件初始化
     */
    public void initView() {
        home_mine = findViewById(R.id.home_mine);
        home_message = findViewById(R.id.home_message);
        search_screen_edt = findViewById(R.id.search_screen_edt);
        home_kehuguanli = findViewById(R.id.home_kehuguanli);
        home_qudaoguanli = findViewById(R.id.home_qudaoguanli);
        home_screen_yewuleixing = findViewById(R.id.home_screen_yewuleixing);
        home_screen_yewuzhuangtai = findViewById(R.id.home_screen_yewuzhuangtai);
        home_screen_time = findViewById(R.id.home_screen_time);
        home_screen_edu = findViewById(R.id.home_screen_edu);
        home_list = findViewById(R.id.home_list);
        home_screen_time_img = findViewById(R.id.home_screen_time_img);
        home_screen_yewuzhuangtai_img = findViewById(R.id.home_screen_yewuzhuangtai_img);
        home_screen_edu_img = findViewById(R.id.home_screen_edu_img);
        home_screen_yewuleixing_img = findViewById(R.id.home_screen_yewuleixing_img);
        home_screen_time_img = findViewById(R.id.home_screen_time_img);
        home_mine.setOnClickListener(this);
        home_message.setOnClickListener(this);
        home_kehuguanli.setOnClickListener(this);
        home_qudaoguanli.setOnClickListener(this);
        home_screen_yewuleixing.setOnClickListener(this);
        home_screen_yewuzhuangtai.setOnClickListener(this);
        home_kehuguanli.setOnClickListener(this);
        home_screen_time.setOnClickListener(this);
        home_screen_edu.setOnClickListener(this);
    }

    public void initData() {
        adapter = new MainAdapter();
        home_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_mine:
                break;
            case R.id.home_message:
                startActivity(new Intent(MainActivity.this,MessageActivity.class));
                break;
            case R.id.home_kehuguanli:
                break;
            case R.id.home_qudaoguanli:
                break;
            case R.id.home_screen_yewuleixing://业务类型
                funImg("leixing");
                break;
            case R.id.home_screen_edu://额度
                funImg("edu");
                break;
            case R.id.home_screen_yewuzhuangtai://业务状态
                funImg("state");
                break;
            case R.id.home_screen_time://时间
                funImg("shijian");
                break;
            default:
                break;
        }
    }

    /**
     * 设置筛选条件
     * @param state
     */
    private void funImg(String state) {
        if(state.equals("leixing")){
            home_screen_yewuleixing_img.setImageResource(R.drawable.home_shanxuan_xia);
            home_screen_edu_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_time_img.setImageResource(R.drawable.home_time);
        }else  if(state.equals("edu")){
            home_screen_edu_img.setImageResource(R.drawable.home_shanxuan_xia);
            home_screen_yewuleixing_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_time_img.setImageResource(R.drawable.home_time);
        }else  if(state.equals("state")){
            home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shanxuan_xia);
            home_screen_edu_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_yewuleixing_img.setImageResource(R.drawable.home_shaixuanshang);
            home_screen_time_img.setImageResource(R.drawable.home_time);
        }else  if(state.equals("shijian")){
            if(time ==0){
                time++;
                home_screen_time_img.setImageResource(R.drawable.home_shijian_shang);
                home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_edu_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_yewuleixing_img.setImageResource(R.drawable.home_shaixuanshang);
            }else if(time ==1){
                time  ++;
                home_screen_time_img.setImageResource(R.drawable.home_shijian_xia);
                home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_edu_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_yewuleixing_img.setImageResource(R.drawable.home_shaixuanshang);
            }else{
                time =0;
                home_screen_time_img.setImageResource(R.drawable.home_time);
                home_screen_yewuzhuangtai_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_edu_img.setImageResource(R.drawable.home_shaixuanshang);
                home_screen_yewuleixing_img.setImageResource(R.drawable.home_shaixuanshang);
            }

        }
    }

    @Override
    public void handleResult(String result, String code) {

    }
}
