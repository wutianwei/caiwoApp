package com.caiwo.caiwoapp.login.http;

import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.http.ParentControllor;
import net.tsz.afinal.http.AjaxParams;

/**
 * 获取验证码
 */
public class CodeHttp extends ParentControllor {


    private String mobile;//手机号码
    private String datetime;//时间
    private String signature;//签名

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public CodeHttp(IResultHandler resultHandler, String requestCode) {
        super(resultHandler, requestCode);
    }

    @Override
    public void setDomain(AjaxParams ajaxParams) {
                putDomain("apiapp/verification_code/sendsms");
    }

    @Override
    public void setParams() {
        ajaxParams.put("mobile",mobile);
        ajaxParams.put("datetime",datetime);
        ajaxParams.put("signature",signature);
    }
}
