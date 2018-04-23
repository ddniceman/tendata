package com.gcexe.qqpro.utils;


import java.io.UnsupportedEncodingException;

import java.util.Date;


import com.gcexe.qqpro.qqaes.AesException;
import com.gcexe.qqpro.qqaes.WXBizMsgCrypt;
import com.gcexe.qqpro.service.QQDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class User {
	public static void main(String[] args) throws AesException, UnsupportedEncodingException {
		ITools itool = new Tools();
		//===========生成sign================
		long timeStamp = new Date().getTime()/1000;
		String sign =itool.MD5Make(timeStamp+SecretKeyConstant.AES_SECRET_KEY);
		String sign16 = sign.substring(16,32);	
		//=======================================
		JSONObject httpProto = new JSONObject();
		JSONObject header = new JSONObject();
		header.element("appid", 102);
		header.element("echostr", "1234567890");
		header.element("reqid", 1);
		header.element("sign", sign16);
		header.element("timeStamp", timeStamp);
		header.element("version", "1.0");
		header.element("ip", "0.0.0.0");		
		httpProto.element("header", header);
		
		JSONArray postUrls = new JSONArray();
		JSONObject reqinfo = new JSONObject();
		
		reqinfo.element("country", "中国");
		reqinfo.element("province", "新疆");
		reqinfo.element("city", "0");
		reqinfo.element("endTime", QQDataService.getQQEndTime());
		reqinfo.element("startTime",QQDataService.getQQStartTime());
		reqinfo.element("type", 153);
		postUrls.add(reqinfo);
		//===========加密reqinfo=============
		WXBizMsgCrypt pc = new WXBizMsgCrypt(SecretKeyConstant.AES_SECRET_KEY);
				
		String miwen = pc.encrypt(postUrls.toString().getBytes());
		
		httpProto.element("reqinfo", miwen);
		
		String result = itool.sendPost("http://sa.cloud.urlsec.qq.com", httpProto.toString());
		System.out.println(result);
		
		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			String ss =  resultObj.getString("rsp");
			String resultStr = ss.substring(2, ss.length()-2);
			byte [] mingwen = pc.decrypt(resultStr);
			System.out.println(new String(mingwen,"UTF-8"));
		}catch(Exception e)
		{
			System.out.println("没有数据");
		}
		
		
	
	
		
		
	}
}
