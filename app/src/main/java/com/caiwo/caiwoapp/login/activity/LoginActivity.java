package com.caiwo.caiwoapp.login.activity;

/**
 * 登陆 页面
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.login.bean.LoginBean;
import com.caiwo.caiwoapp.login.http.LoginHttp;
import com.caiwo.caiwoapp.utils.GsonUtils;

public class LoginActivity extends Activity implements View.OnClickListener, IResultHandler {

    private ImageView back;
    private TextView title;
    private TextView rigthTx;
    private LoginHttp mLoginHttp;
    private LoginBean loginBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {

        back = findViewById(R.id.view_header_back_Img);
        title = findViewById(R.id.view_header_title_Tx);
        rigthTx = findViewById(R.id.view_header_rightTx);
        title.setText("登陆");
        rigthTx.setText("游客注册");
        back.setOnClickListener(this);
        rigthTx.setOnClickListener(this);

    }

    public void login() {
        if (null == mLoginHttp) {
            mLoginHttp = new LoginHttp(this, "login");
        }
        mLoginHttp.setMoblie("");
        mLoginHttp.setPassword("");
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
//        loginBean = GsonUtils.jsonToBean()
    }
}
