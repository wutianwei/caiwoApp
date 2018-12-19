package com.caiwo.caiwoapp.http;

public interface IResultHandler {
	/**
	 * 请求处理接口
	 * @param result  返回值
	 * @param code  请求标志位
	 */
	public void handleResult(String result, String code);
}
