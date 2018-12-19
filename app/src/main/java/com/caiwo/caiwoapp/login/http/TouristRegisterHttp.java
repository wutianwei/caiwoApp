package com.caiwo.caiwoapp.login.http;

import com.caiwo.caiwoapp.http.IResultHandler;
import com.caiwo.caiwoapp.http.ParentControllor;
import net.tsz.afinal.http.AjaxParams;

/**
 * 游客登录
 */
public class TouristRegisterHttp extends ParentControllor {

    private String username;//姓名
    private String mobile;//手机号
    private String code;//验证码
    private String idcard;//身份证
    private String hy_id;//行业类型
     private String ywlx_id;//业务类型ID
     private String zy_id;//职业ID
     private String question1;//
     private String question2;//
     private String question3;//
     private String answer1;//
     private String answer2;//
     private String answer3;//
     private String step;//

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setHy_id(String hy_id) {
        this.hy_id = hy_id;
    }

    public void setYwlx_id(String ywlx_id) {
        this.ywlx_id = ywlx_id;
    }

    public void setZy_id(String zy_id) {
        this.zy_id = zy_id;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public TouristRegisterHttp(IResultHandler resultHandler, String requestCode) {
        super(resultHandler, requestCode);
    }

    @Override
    public void setDomain(AjaxParams ajaxParams) {
         putDomain("apiapp/user/reg");
    }

    @Override
    public void setParams() {
        ajaxParams.put("username",username);
        ajaxParams.put("mobile",mobile);
        ajaxParams.put("code",code);
        ajaxParams.put("idcard",idcard);
        ajaxParams.put("hy_id",hy_id);
        ajaxParams.put("ywlx_id",ywlx_id);
        ajaxParams.put("zy_id",zy_id);
        ajaxParams.put("question1",question1);
        ajaxParams.put("question2",question2);
        ajaxParams.put("question3",question3);
        ajaxParams.put("answer1",answer1);
        ajaxParams.put("answer2",answer2);
        ajaxParams.put("answer3",answer3);
        ajaxParams.put("step",step);

    }
}
