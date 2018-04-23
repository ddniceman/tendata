package com.gcexe.qqpro.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gcexe.qqdata.persistence.dao.BlackUrlDistributeCompareTableMapper;
import com.gcexe.qqdata.persistence.entity.BlackUrlDistributeCompareTable;
import com.gcexe.qqpro.qqaes.AesException;
import com.gcexe.qqpro.qqaes.WXBizMsgCrypt;
import com.gcexe.qqpro.utils.ITools;
import com.gcexe.qqpro.utils.SecretKeyConstant;
import com.gcexe.qqpro.utils.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class QQDataService {

	@Autowired
	private ITools itools;
	@Autowired
	private BlackUrlDistributeCompareTableMapper blackUrlDistributeCompareTableMapper;
	
	
	private String getQQData(String country, String province, String city, int type)
			throws AesException, UnsupportedEncodingException {
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
		if (status == 0) {
			if (result.indexOf("rs") != -1) {
				String str = resultObj.getString("rsp");
				String resultStr = str.substring(2, str.length() - 2);
				byte[] mingwen = pc.decrypt(resultStr);
				String success = new String(mingwen, "UTF-8");
				return success;
			}
		}
		return null;
	}

	private long getQQStartTime() {
		GregorianCalendar gre = new GregorianCalendar();
		gre.setTime(getBeforDate(-1));
		gre.add(Calendar.MINUTE, -10);
		return gre.getTimeInMillis() / 1000;
	}

	private long getQQEndTime() {
		GregorianCalendar gre = new GregorianCalendar();
		gre.setTime(getBeforDate(-1));
		return gre.getTimeInMillis() / 1000;
	}

	private Date getBeforDate(int hour) {
		GregorianCalendar gre = new GregorianCalendar();
		gre.setTime(new Date());
		gre.add(Calendar.HOUR, hour);
		return new Date(gre.getTimeInMillis());
	}

	// 重点网站 153 black_url_distribute_compare_table
	public void getBlackUrlDistributeCompareTable() {
		try {
			//获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 153);
			if(result != null && result.indexOf("body")!=-1)
			{
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray  arrayObj = JSONArray.fromObject(resultObj.get("body"));		
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					BlackUrlDistributeCompareTable blackUrlDistributeCompareTable = new BlackUrlDistributeCompareTable();
					blackUrlDistributeCompareTable.setCity(obj.getString("city"));
					blackUrlDistributeCompareTable.setCountry(obj.getString("country"));
					blackUrlDistributeCompareTable.setProvince(obj.getString("province"));
					blackUrlDistributeCompareTable.setCnt(obj.getInt("cnt"));
					blackUrlDistributeCompareTable.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					blackUrlDistributeCompareTableMapper.insertSelective(blackUrlDistributeCompareTable);
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	
	
}
