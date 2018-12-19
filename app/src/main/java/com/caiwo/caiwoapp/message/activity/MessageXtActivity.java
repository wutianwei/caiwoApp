package com.caiwo.caiwoapp.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.message.adapter.MessageXtAdapter;

public class MessageXtActivity extends Activity implements View.OnClickListener, IResultHandler {

    private ImageView back;
    private TextView title, rigthTx;
    private ListView activity_message_listview;
    private MessageXtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        back = findViewById(R.id.view_header_back_Img);
        title = findViewById(R.id.view_header_title_Tx);
        back = findViewById(R.id.view_header_back_Img);
        activity_message_listview = findViewById(R.id.activity_message_listview);
        rigthTx = findViewById(R.id.view_header_rightTx);
        back.setOnClickListener(this);
        rigthTx.setOnClickListener(this);
        title.setText("系统消息");
        rigthTx.setText("全部已读");
    }

    public void initData() {
        adapter = new MessageXtAdapter();
        activity_message_listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_header_back_Img:
                this.finish();
                break;
            case R.id.view_header_rightTx:
                break;
            default:
                break;
        }
    }

    @Override
    public void handleResult(String result, String code) {

    }
}
