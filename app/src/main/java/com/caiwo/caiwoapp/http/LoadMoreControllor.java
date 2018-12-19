package com.caiwo.caiwoapp.http;


import com.caiwo.caiwoapp.utils.CommonUtils;
import com.caiwo.caiwoapp.utils.ToastUtils;

import com.caiwo.caiwoapp.app.MyApplication;

public abstract class LoadMoreControllor extends ParentControllor {

    //java
    private int pageNo = 1;//页面下标
    private int pageSize = 10;


    public LoadMoreControllor(IResultHandler resultHandler,
                              String requestCode) {
        super(resultHandler, requestCode);
    }

    public LoadMoreControllor(IResultHandler resultHandler,
                              String requestCode, String url) {
        super(resultHandler, requestCode, url);
    }

    /**
     * 请求第一次数据或者下拉刷新数据时调用这个方法
     */
    public void requestFirst() {
        if (netUsable()) {
            pageNo = 1;
            post();
        } else {
            ToastUtils.getInstance(MyApplication.getInstance()).show("网络不可用，请检查后重试");
        }
    }

    /**
     * 请求更多时调用这个
     */
    public void requestMore() {
        if (netUsable()) {
//			if(isJava){
            ++pageNo;
//			}else{
//				++currPage;
//			}
            post();
        } else {
            ToastUtils.getInstance(MyApplication.getInstance()).show("网络不可用，请检查后重试");
        }
    }

    /**
     * 检查网络是否可用
     *
     * @return true可用 <br/>
     * false 不可用
     */
    private boolean netUsable() {
        int statusCode = CommonUtils.isNetworkAvailable(MyApplication.getInstance());
        if (statusCode == 1 || statusCode == 2 || statusCode == 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setParams() {
//		if(isJava){
//        ajaxParams.remove("pageIndex");
//        ajaxParams.remove("pageSize");
        ajaxParams.put("pageNo", pageNo + "");
        ajaxParams.put("pageSize", pageSize + "");
//		}else{
//			ajaxParams.remove("page");
//			ajaxParams.remove("listnum");
//			ajaxParams.put("page", currPage + "");
//			//ajaxParams.put("listnum", countInPage + "");
//		}
    }

}
