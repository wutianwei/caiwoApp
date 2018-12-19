package com.caiwo.caiwoapp.login.http;

import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.http.ParentControllor;
import net.tsz.afinal.http.AjaxParams;

/**
 * 手机号密码登录
 */
public class LoginHttp extends ParentControllor {
    private String moblie;
    private String password;
    private String type;

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginHttp(IResultHandler resultHandler, String requestCode) {
        super(resultHandler, requestCode);
    }

    @Override
    public void setDomain(AjaxParams ajaxParams) {
      putDomain("apiapp/user/login");
    }

    @Override
    public void setParams() {
        ajaxParams.put("moblie",moblie);
        ajaxParams.put("password",password);
        ajaxParams.put("type",type);
    }
}
