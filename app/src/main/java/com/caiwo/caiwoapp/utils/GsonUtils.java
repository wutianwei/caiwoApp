package com.caiwo.caiwoapp.utils;

import com.google.gson.Gson;

/**
 *
 * Gson解析
 */
public class GsonUtils {

	static String str = "{\"code\":\"-100\",\"msg\":\"返回数据错误\"}";

	/**
	 * 
	 * @Title: jsonToBean
	 * @Description: 解析JSON字符串
	 * @param @param jsonResult
	 * @param @param clz
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws
	 */
	public static <T> T jsonToBean(String jsonResult, Class<T> clz) {
		
		/**
		 * 服务器宕机之后，进行赋默认值
		 */
		if(jsonResult == null || jsonResult.trim().equals("")){

			jsonResult = str;
		}else{
			/**
			 * 如果返回的字符串第一个字符不是{的话，也给赋个默认值
			 */
			if(!jsonResult.trim().substring(0, 1).equals("{")){
				jsonResult = str;
			}
		}
		Gson gson = new Gson();
		T t = gson.fromJson(jsonResult, clz);
		return t;
	}



}
