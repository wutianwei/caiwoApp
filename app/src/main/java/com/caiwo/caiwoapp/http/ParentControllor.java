package com.caiwo.caiwoapp.http;


import android.content.Context;
import android.util.Log;

import com.caiwo.caiwoapp.utils.AppConstants;
import com.caiwo.caiwoapp.utils.SpUtils;
import com.caiwo.caiwoapp.utils.ToastUtils;
import com.easemob.util.NetUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.caiwo.caiwoapp.app.MyApplication;


/**
 * @category HTTP请求父类
 * @since 新加入一个 请求码作为请求标志位， 每次使用传入一个 然后在handleResult方法里面对请求码进行判断，
 * 即可区分开来一个Activity或者一个Fragment里面存在多次请求无法区分的问题 <br/>
 * 新版的请求父类在调用上有所调整，目的是加入HTTP-get 请求 。基地址全部是一样的，所以写在ConString 类中。 <br/>
 * 例如原来http://app.51fanxing.com/app/index.php?act=store_goods&op=
 * homepage_adv 这样的URL请求地址现在修改为 http://app.51fanxing.com/app/index.php<br/>
 * "?"作为分割符来拆分开参数和请求地址的<br/>
 * "?"后面的所有内容作为参数传入，所以在父类请求会额外增加一个添加控制器标志的抽象来专门存储控制器（类似：act=store_goods&
 * op=homepage_adv这两个参数） <br/>
 * 主要实现逻辑是，初始化数据->设置请求参数->发起请求->获得返回结果
 */
public abstract class ParentControllor {

    /**
     * 全局HTTP请求
     */
    public FinalHttp finalHttp;
    /**
     * 全局参数集合
     */
    public AjaxParams ajaxParams;
    /**
     * 全局回调函数
     */
    public AjaxCallBack<String> ajaxCallBack;
    /**
     * 完整的URL
     */
    public String url;
    /**
     * 数据返回结果处理器
     */
    public IResultHandler resultHandler;
    /**
     * memberName
     */
    public String memberName;
    /**
     * 安全码
     */
    public String safeCode;


    /**
     * 全局上下文，如果不需要则不用管
     */
    public Context mContext;

    /**
     * 请求码，每一次请求都有唯一的标准
     */
    public String requestCode;


    /**
     * 设置请求URL的作用域,每个请求都必须设置act和op这两个参数，否则请求url不成立
     *
     * @param ajaxParams 请求参数体
     */
    public abstract void setDomain(AjaxParams ajaxParams);


    /**
     * 每一个访问控制器都必须使用 super来初始化这些参数，否则会报空指针异常， 接口参数可以为空，如果为空了，说明不需要返回，则不用实现这个接口
     *
     * @param resultHandler IResultHandler接口类型，需要返回数据时必须实现接口， 如果不需要返回，则直接设为null就可以了
     */
    public ParentControllor(IResultHandler resultHandler,
                            String requestCode) {
        this.finalHttp = new FinalHttp();
        this.finalHttp.configTimeout(12000);
        this.finalHttp.configCharset("UTF-8");
        this.finalHttp.configUserAgent("rongba_xiaoxindai");
        this.ajaxParams = new AjaxParams();
        this.url = AppConstants.BASE_URL;
        this.resultHandler = resultHandler;
        this.requestCode = requestCode;
    }

    /**
     * 每一个访问控制器都必须使用 super来初始化这些参数，否则会报空指针异常， 接口参数可以为空，如果为空了，说明不需要返回，则不用实现这个接口
     *
     * @param url           支付专用
     *                      必传的 接口访问地址
     * @param resultHandler IResultHandler接口类型，需要返回数据时必须实现接口， 如果不需要返回，则直接设为null就可以了
     */
    public ParentControllor(IResultHandler resultHandler,
                            String requestCode, String url) {
        this.finalHttp = new FinalHttp();
        //this.finalHttp.configSSLSocketFactory();
        this.finalHttp.configTimeout(12000);
        this.finalHttp.configCharset("UTF-8");
        this.finalHttp.configUserAgent("rongba_xiaoxindai");

        this.ajaxParams = new AjaxParams();
        this.url = url;
        this.resultHandler = resultHandler;
        this.requestCode = requestCode;
    }

    /**
     * 设置非常规url拼接地址，如果该方法被调用， 则不需要再实现setDomain和setParams方法，意味着不需要额外继续添加参数
     *
     * @param url
     */
    public void setURl(String url) {
        this.url = url;
    }

    /**
     * 设置请求吗，请求一次之后，修改该请求码，然后再次调用get或者post方法即可
     *
     * @param requestCode
     */
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * 设置请求参数，每一个请求都必须实现，传入参数后即可使用<br/>
     * 2015/04/26 这个方法 是用来传入参数的每一次都必须去实现， 否则，一个完整的请求无法构成而倒是无法发起请求
     */
    public abstract void setParams();

    /**
     * 初始化回调方法， 新加入一个 请求码作为请求标志位， 每次使用传入一个 然后在handleResult方法里面对请求码进行判断，
     * 即可区分开来一个Activity或者一个Fragment里面存在多次请求无法区分的问题
     */
    public void initCallback() {
        this.ajaxCallBack = new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                if (resultHandler != null) {
                    try {
                        resultHandler.handleResult(result, requestCode);
//						if(result.contains("code") && result.contains("data") && result.contains("msg")){
//							resultHandler.handleResult(result, requestCode);
//						}else{
//							resultHandler.handleResult("error", requestCode);
//						}
                    } catch (Exception e) {
                        e.printStackTrace();
//                        resultHandler.handleResult("error", requestCode);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (resultHandler != null) {
                    try {
                        Log.e("this", " ,错误信息是：" + requestCode);
                        resultHandler.handleResult("error", requestCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("this",e.toString()+"---Exception");
                    }
                }
                Log.e("this", "错误码是：" + errorNo + " ,错误信息是：" + strMsg);
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override
            public void onStart() {
                super.onStart();
                //				LogUtils.info("请求开始");
            }

            @Override
            public boolean isProgress() {
                //				LogUtils.info("请求正在进行中");
                return super.isProgress();
            }

            @Override
            public AjaxCallBack<String> progress(boolean progress, int rate) {
                //				LogUtils.info("请求是否在进行：" + progress + "  进度是：" + rate);
                return super.progress(progress, rate);
            }
        };
    }

    /**
     * 请求参数中加入登录信息，需要传入时调用，不需要时不调用
     */
    public void setUserInfo(Context mContext) {
//		memberName = SpUtils.getInstance(mContext).getMemberName();
//		safeCode = SpUtils.getInstance(mContext).getSafecode();
        putLogininfo();
    }

    /**
     * 向请求参数中添加登录信息，选择性加入，如果不需要则直接不调用
     */
    public void putLogininfo() {
        if (memberName != null && !memberName.equals("")) {
            ajaxParams.put("member_name", memberName);
        }
        if (safeCode != null && !safeCode.equals("")) {
            ajaxParams.put("safecode", safeCode);
        }
    }

    /**
     * 传递android平台和版本号
     */
    private void setAndroidVesrionInfo() {
        ajaxParams.put("app_version", "3.2.1");
        ajaxParams.put("app_platform", "android");
    }

    /**
     * 配置好URL ，Parameter，callback 参数之后调用此方法，就可以发起请求， 介于当前请求只有 httpPost方式请求，
     * 暂时只做Post请求方式，后续这里可以进行修改，来适配get方法
     */
    public void post() {
        setAndroidVesrionInfo();
        this.setDomain(this.ajaxParams);
        setParams();
        setRequestData();
        final String loginkey = SpUtils.getInstance(MyApplication.getInstance()).getMemberName();
        String jsonStr ="\""+loginkey+"\"";
//        finalHttp.addHeader("cookie","ADVISOR_SESSION_LOGINKEY="+jsonStr);
        finalHttp.post(url, ajaxParams, ajaxCallBack);
		if(!NetUtils.hasNetwork(MyApplication.getInstance())){
        ToastUtils.getInstance(MyApplication.getInstance()).show("网络不可用，请检查后重试");
		}
        Log.e("this", ajaxParams.toString() + "---post");
    }


    /**
     * 发起get请方法,操作方式和HTTP post类似
     */
    public void get() {
        setAndroidVesrionInfo();
        this.setDomain(this.ajaxParams);
        setParams();
        setRequestData();
        finalHttp.get(url, ajaxParams, ajaxCallBack);
    }


    /**
     * 配置请求参数，请求之前最后一步，私有方法，外界不许调用。
     */
    private void setRequestData() {
        if (ajaxCallBack == null) {
            initCallback();
        }
    }


    /**
     * 设置控制域
     * 无需后缀
     */
    public void putDomain(String urlDomain) {
        if (!url.contains(urlDomain)) {
            url = url + urlDomain;
        }
    }


    /**
     * destroy 销毁引用
     */
    public void destroyHttp() {
        try {
            finalHttp = null;
            ajaxParams = null;
            ajaxCallBack = null;
            resultHandler = null;
            mContext = null;
        } catch (Exception ex) {
        }
    }


}
