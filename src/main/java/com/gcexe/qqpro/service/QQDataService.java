package com.gcexe.qqpro.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.gcexe.qqpro.qqaes.AesException;
import com.gcexe.qqpro.qqaes.WXBizMsgCrypt;
import com.gcexe.qqpro.utils.ITools;
import com.gcexe.qqpro.utils.SecretKeyConstant;
import com.gcexe.qqpro.utils.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class QQDataService {
	
	public static String getQQData(String country, String province, String city,  int type)
			throws AesException {
		ITools itool = new Tools();
		// ===========生成sign================
		long timeStamp = new Date().getTime() / 1000;
		String sign = itool.MD5Make(timeStamp + SecretKeyConstant.AES_SECRET_KEY);
		String sign16 = sign.substring(16, 32);

		// =======================================
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

		reqinfo.element("country", country);
		reqinfo.element("province", province);
		reqinfo.element("city", city);
		reqinfo.element("startTime", getQQStartTime());
		reqinfo.element("endTime", getQQEndTime());
		reqinfo.element("type", type);
		postUrls.add(reqinfo);
		// ===========加密reqinfo=============
		WXBizMsgCrypt pc = new WXBizMsgCrypt(SecretKeyConstant.AES_SECRET_KEY);

		String miwen = pc.encrypt(postUrls.toString().getBytes());

		httpProto.element("reqinfo", miwen);

		String result = itool.sendPost("http://sa.cloud.urlsec.qq.com", httpProto.toString());
		JSONObject resultObj = JSONObject.fromObject(result);	
		int status = resultObj.getInt("status");
		if(status==0)
		{
			String str =  resultObj.getString("rsp");
			String resultStr = str.substring(2, str.length()-2);
			byte [] mingwen = pc.decrypt(resultStr);
			try {
				String success = new String(mingwen,"UTF-8");
				return success;
			} catch (UnsupportedEncodingException e) {
				
			}catch(Exception e)
			{
				
			}
		}
		return null;
	}

	public static long getQQStartTime() {
		GregorianCalendar gre = new GregorianCalendar();
		gre.setTime(new Date());
		//gre.add(Calendar.HOUR, -1);
		gre.add(Calendar.MINUTE,-40);
		return gre.getTimeInMillis()/1000;
	}

	
	public static long getQQEndTime() {
		GregorianCalendar gre = new GregorianCalendar();
		gre.setTime(new Date());
		gre.add(Calendar.MINUTE,-30);
		return gre.getTimeInMillis()/1000;
	}
}
