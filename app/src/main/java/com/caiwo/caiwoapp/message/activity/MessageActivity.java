package com.caiwo.caiwoapp.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.message.adapter.MessageAdapter;

public class MessageActivity extends Activity implements View.OnClickListener, IResultHandler {

    private ImageView back;
    private TextView title;
    private ListView activity_message_listview;
    private MessageAdapter adapter;

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
        back.setOnClickListener(this);
        title.setText("消息");
    }

    public void initData() {
        adapter = new MessageAdapter();
        activity_message_listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_header_back_Img:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void handleResult(String result, String code) {

    }
}
