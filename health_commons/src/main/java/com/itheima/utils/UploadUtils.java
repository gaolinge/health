package com.itheima.utils;

import java.util.Random;
import java.util.UUID;

public class UploadUtils {
	
	/**
	 * 获取随机名称
	 * @param
	 * @return uuid 随机名称
	 */
	public static String getUUIDName(String fileName){
		//realname  eg: 1.jpg  
		//获取后缀名
		int index = fileName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+fileName.substring(index);
		}
	}


}
