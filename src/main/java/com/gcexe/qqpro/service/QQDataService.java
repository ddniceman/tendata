package com.gcexe.qqpro.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gcexe.qqdata.persistence.dao.BlackUrlDistributeCompareTableMapper;
import com.gcexe.qqdata.persistence.dao.BlackUrlDistributeTableMapper;
import com.gcexe.qqdata.persistence.dao.BlackUrlEvilclassDistributeMapper;
import com.gcexe.qqdata.persistence.dao.BlackUrlHistoryDataMapper;
import com.gcexe.qqdata.persistence.dao.DdosCntHistoryMapper;
import com.gcexe.qqdata.persistence.dao.DdosCptTenDayHistoryMapper;
import com.gcexe.qqdata.persistence.dao.DdosNewCntHistoryMapper;
import com.gcexe.qqdata.persistence.dao.DdosNewCptTenDayHistoryMapper;
import com.gcexe.qqdata.persistence.dao.ImportantHostMapper;
import com.gcexe.qqdata.persistence.dao.ImportantHostMonitorResultMapper;
import com.gcexe.qqdata.persistence.dao.PcVirusProvinceCityStatHistoryMapper;
import com.gcexe.qqdata.persistence.dao.PcVirusProvinceStatHistoryMapper;
import com.gcexe.qqdata.persistence.dao.PcVirusTypeStatHistoryMapper;
import com.gcexe.qqdata.persistence.dao.PcVirustypeProvinceStatHistoryMapper;
import com.gcexe.qqdata.persistence.entity.BlackUrlDistributeCompareTable;
import com.gcexe.qqdata.persistence.entity.BlackUrlDistributeTable;
import com.gcexe.qqdata.persistence.entity.BlackUrlEvilclassDistribute;
import com.gcexe.qqdata.persistence.entity.BlackUrlHistoryData;
import com.gcexe.qqdata.persistence.entity.DdosCntHistoryKey;
import com.gcexe.qqdata.persistence.entity.DdosCptTenDayHistoryKey;
import com.gcexe.qqdata.persistence.entity.DdosNewCntHistoryKey;
import com.gcexe.qqdata.persistence.entity.DdosNewCptTenDayHistoryKey;
import com.gcexe.qqdata.persistence.entity.ImportantHost;
import com.gcexe.qqdata.persistence.entity.ImportantHostMonitorResult;
import com.gcexe.qqdata.persistence.entity.PcVirusProvinceCityStatHistoryKey;
import com.gcexe.qqdata.persistence.entity.PcVirusProvinceStatHistoryKey;
import com.gcexe.qqdata.persistence.entity.PcVirusTypeStatHistoryKey;
import com.gcexe.qqdata.persistence.entity.PcVirustypeProvinceStatHistoryKey;
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
	// =================================================
	@Autowired
	private BlackUrlDistributeCompareTableMapper blackUrlDistributeCompareTableMapper;
	@Autowired
	private BlackUrlDistributeTableMapper blackUrlDistributeTableMapper;
	@Autowired
	private BlackUrlEvilclassDistributeMapper blackUrlEvilclassDistributeMapper;
	@Autowired
	private BlackUrlHistoryDataMapper blackUrlHistoryDataMapper;
	// ==================================================
	@Autowired
	private DdosCptTenDayHistoryMapper ddosCptTenDayHistoryMapper;
	@Autowired
	private DdosNewCptTenDayHistoryMapper ddosNewCptTenDayHistoryMapper;
	@Autowired
	private DdosCntHistoryMapper ddosCntHistoryMapper;
	@Autowired
	private DdosNewCntHistoryMapper ddosNewCntHistoryMapper;
	// =======================================================
	@Autowired
	private PcVirustypeProvinceStatHistoryMapper pcVirustypeProvinceStatHistoryMapper;
	@Autowired
	private PcVirusProvinceCityStatHistoryMapper pcVirusProvinceCityStatHistoryMapper;
	@Autowired
	private PcVirusProvinceStatHistoryMapper pcVirusProvinceStatHistoryMapper;
	@Autowired
	private PcVirusTypeStatHistoryMapper pcVirusTypeStatHistoryMapper;
	//=========================================================
	@Autowired
	private ImportantHostMapper importantHostMapper;
	@Autowired
	private ImportantHostMonitorResultMapper importantHostMonitorResultMapper;

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
	public void blackUrlDistributeCompareTable() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 153);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
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

	// 重点网站 180 BlackUrlDistributeTable
	public void blackUrlDistributeTable() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 180);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					BlackUrlDistributeTable blackUrlDistributeTable = new BlackUrlDistributeTable();
					blackUrlDistributeTable.setCountry(obj.getString("country"));
					blackUrlDistributeTable.setCity(obj.getString("city"));
					blackUrlDistributeTable.setProvince(obj.getString("province"));
					blackUrlDistributeTable.setEvilclass(obj.getInt("evilclass"));
					blackUrlDistributeTable.setEviltype(obj.getInt("eviltype"));
					blackUrlDistributeTable.setCnt(obj.getInt("cnt"));
					blackUrlDistributeTable.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					blackUrlDistributeTableMapper.insertSelective(blackUrlDistributeTable);
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

	// 重点网站 149 black_url_evilclass_distribute
	public void blackUrlEvilclassDistribute() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 149);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					BlackUrlEvilclassDistribute blackUrlEvilclassDistribute = new BlackUrlEvilclassDistribute();
					blackUrlEvilclassDistribute.setCountry(obj.getString("country"));
					blackUrlEvilclassDistribute.setProvince(obj.getString("province"));
					blackUrlEvilclassDistribute.setCity(obj.getString("city"));

					blackUrlEvilclassDistribute.setEvilclass(obj.getInt("evilclass"));
					blackUrlEvilclassDistribute.setRatio(obj.getDouble("ratio"));
					blackUrlEvilclassDistribute.setCnt(obj.getInt("cnt"));
					blackUrlEvilclassDistribute.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					blackUrlEvilclassDistributeMapper.insertSelective(blackUrlEvilclassDistribute);
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

	// 重点网站 152 black_url_history_data
	public void blackUrlHistoryData() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 152);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					BlackUrlHistoryData blackUrlHistoryData = new BlackUrlHistoryData();
					blackUrlHistoryData.setCountry(obj.getString("country"));
					blackUrlHistoryData.setProvince(obj.getString("province"));

					blackUrlHistoryData.setEvilclass(obj.getInt("evilclass"));
					blackUrlHistoryData.setCnt(obj.getInt("cnt"));
					blackUrlHistoryData.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					blackUrlHistoryDataMapper.insertSelective(blackUrlHistoryData);
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

	// DDOS 162 ddos_cpt_ten_day_history
	public void ddosCptTenDayHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 162);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					DdosCptTenDayHistoryKey ddosCptTenDayHistoryKey = new DdosCptTenDayHistoryKey();
					ddosCptTenDayHistoryKey.setCity(obj.getString("city"));
					ddosCptTenDayHistoryKey.setCountry(obj.getString("country"));
					ddosCptTenDayHistoryKey.setProvince(obj.getString("province"));
					ddosCptTenDayHistoryKey.setCnt(obj.getInt("cnt"));
					ddosCptTenDayHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					ddosCptTenDayHistoryMapper.insertSelective(ddosCptTenDayHistoryKey);
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

	// DDOS 165 ddos_new_cpt_ten_day_history
	public void ddosNewCptTenDayHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 165);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					DdosNewCptTenDayHistoryKey ddosNewCptTenDayHistoryKey = new DdosNewCptTenDayHistoryKey();
					ddosNewCptTenDayHistoryKey.setCountry(obj.getString("country"));
					ddosNewCptTenDayHistoryKey.setCity(obj.getString("city"));
					ddosNewCptTenDayHistoryKey.setProvince(obj.getString("province"));

					ddosNewCptTenDayHistoryKey.setCnt(obj.getInt("cnt"));
					ddosNewCptTenDayHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					ddosNewCptTenDayHistoryMapper.insertSelective(ddosNewCptTenDayHistoryKey);
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

	// DDOS 163 ddos_cnt_history
	public void ddosCntHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 163);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					DdosCntHistoryKey ddosCntHistoryKey = new DdosCntHistoryKey();

					ddosCntHistoryKey.setCnt(obj.getInt("cnt"));
					ddosCntHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					ddosCntHistoryMapper.insertSelective(ddosCntHistoryKey);
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

	// DDOS 166 ddos_new_cnt_history
	public void ddosNewCntHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 166);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					DdosNewCntHistoryKey ddosNewCntHistoryKey = new DdosNewCntHistoryKey();

					ddosNewCntHistoryKey.setCnt(obj.getInt("cnt"));
					ddosNewCntHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					ddosNewCntHistoryMapper.insertSelective(ddosNewCntHistoryKey);
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

	// PC病毒 158 pc_virustype_province_stat_history
	public void pcVirustypeProvinceStatHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 158);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					PcVirustypeProvinceStatHistoryKey pcVirustypeProvinceStatHistoryKey = new PcVirustypeProvinceStatHistoryKey();
					pcVirustypeProvinceStatHistoryKey.setVirusType(obj.getString("virus_type"));

					pcVirustypeProvinceStatHistoryKey.setProvince(obj.getString("province"));
					pcVirustypeProvinceStatHistoryKey.setCnt(obj.getInt("cnt"));
					pcVirustypeProvinceStatHistoryKey
							.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					pcVirustypeProvinceStatHistoryMapper.insertSelective(pcVirustypeProvinceStatHistoryKey);
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

	// PC病毒 160 pc_virus_province_city_stat_history
	public void pcVirusProvinceCityStatHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 160);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					PcVirusProvinceCityStatHistoryKey pcVirusProvinceCityStatHistoryKey = new PcVirusProvinceCityStatHistoryKey();

					pcVirusProvinceCityStatHistoryKey.setCity(obj.getString("city"));
					pcVirusProvinceCityStatHistoryKey.setProvince(obj.getString("province"));

					pcVirusProvinceCityStatHistoryKey.setCnt(obj.getInt("cnt"));
					pcVirusProvinceCityStatHistoryKey
							.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					pcVirusProvinceCityStatHistoryMapper.insertSelective(pcVirusProvinceCityStatHistoryKey);
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

	// PC病毒 161 pc_virus_province_stat_history
	public void pcVirusProvinceStatHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 161);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					PcVirusProvinceStatHistoryKey pcVirusProvinceStatHistoryKey = new PcVirusProvinceStatHistoryKey();
					pcVirusProvinceStatHistoryKey.setProvince(obj.getString("province"));
					pcVirusProvinceStatHistoryKey.setCnt(obj.getInt("cnt"));
					pcVirusProvinceStatHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					pcVirusProvinceStatHistoryMapper.insertSelective(pcVirusProvinceStatHistoryKey);
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

	// PC病毒 159 pc_virus_type_stat_history
	public void pcVirusTypeStatHistory() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 159);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					PcVirusTypeStatHistoryKey pcVirusTypeStatHistoryKey = new PcVirusTypeStatHistoryKey();
					pcVirusTypeStatHistoryKey.setVirusType(obj.getString("virus_type"));
					pcVirusTypeStatHistoryKey.setCnt(obj.getInt("cnt"));
					pcVirusTypeStatHistoryKey.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					pcVirusTypeStatHistoryMapper.insertSelective(pcVirusTypeStatHistoryKey);
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

	// 监控 181 important_host
	public void importantHost() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 181);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					ImportantHost importantHost = new ImportantHost();
					importantHost.setCity(obj.getString("city"));
					importantHost.setProvince(obj.getString("province"));
					importantHost.setCnt(obj.getInt("cnt"));
					importantHost.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					importantHostMapper.insertSelective(importantHost);
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

	// 监控 182 important_host_monitor_result
	public void importantHostMonitorResult() {
		try {
			// 获取qq数据
			String result = this.getQQData("中国", "新疆", "0", 182);
			if (result != null && result.indexOf("body") != -1) {
				JSONObject resultObj = JSONObject.fromObject(result);
				JSONArray arrayObj = JSONArray.fromObject(resultObj.get("body"));
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject obj = arrayObj.getJSONObject(i);
					ImportantHostMonitorResult importantHostMonitorResult = new ImportantHostMonitorResult();
					importantHostMonitorResult.setProvince(obj.getString("province"));
					importantHostMonitorResult.setCity(obj.getString("city"));
					
					importantHostMonitorResult.setCnt(obj.getInt("cnt"));
					importantHostMonitorResult.setTs(itools.formatDate(obj.getString("ts"), "yyyy-MM-dd HH:mm:ss"));
					importantHostMonitorResultMapper.insertSelective(importantHostMonitorResult);
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

	public static void main(String[] args) throws UnsupportedEncodingException, AesException {
		QQDataService dd = new QQDataService();
		String result = dd.getQQData("中国", "新疆", "0", 182);
		System.out.println(result);
	}

}
