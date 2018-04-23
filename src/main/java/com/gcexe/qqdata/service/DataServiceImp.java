package com.gcexe.qqdata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcexe.qqdata.persistence.dao.BlackUrlDistributeCompareTableMapper;
import com.gcexe.qqdata.persistence.dao.DdosCptTenDayHistoryMapper;
import com.gcexe.qqdata.persistence.dao.DdosNewCptTenDayHistoryMapper;
import com.gcexe.qqdata.persistence.dao.ImportantHostMapper;
import com.gcexe.qqdata.persistence.dao.ImportantHostMonitorResultMapper;
import com.gcexe.qqdata.persistence.dao.MobileVirustypeProvinceStatHistoryMapper;
import com.gcexe.qqdata.persistence.dao.PcVirustypeProvinceStatHistoryMapper;
import com.gcexe.qqdata.persistence.statistics.DDOSStatistics;
import com.gcexe.qqdata.persistence.statistics.ImportntHostStatistics;
import com.gcexe.qqdata.persistence.statistics.StatisticsView;
import com.gcexe.qqdata.persistence.statistics.VirusTypeStatistics;
import com.gcexe.qqdata.uitls.ResultCodeVo;
import com.gcexe.qqpro.utils.ITools;

@Service
public class DataServiceImp implements DataService {

	@Autowired
	private MobileVirustypeProvinceStatHistoryMapper mvpsm;
	@Autowired
	private PcVirustypeProvinceStatHistoryMapper pvpsm;
	@Autowired
	private ImportantHostMapper ihm;
	@Autowired
	private ImportantHostMonitorResultMapper ihmrm;
	@Autowired
	private DdosNewCptTenDayHistoryMapper ddosnewmpper;
	@Autowired
	private DdosCptTenDayHistoryMapper ddosmapper;
	@Autowired
	private BlackUrlDistributeCompareTableMapper urlmapper;

	@Autowired
	ITools itools;

	public ResultCodeVo getPCAndMobileType() {

		List<VirusTypeStatistics> mobilevts = mvpsm.getMobileVirusTypeStatistics();
		List<VirusTypeStatistics> pcvts = pvpsm.getPCVirusTypeStatistics();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m", mobilevts);
		map.put("pc", pcvts);

		return new ResultCodeVo(true, 0, "success", map);
	}

	public ResultCodeVo getPCAndMobileNum() {
		// 当前数量
		int dayNum = this.mvpsm.getDayNum();
		int pcDayNum = this.pvpsm.getDayNum();
		// 今天当前时间段数量（小时）
		int dayTimeNum = this.mvpsm.getDayMobileVirusStatistics();
		int pcDayTimeNum = this.pvpsm.getDayPCVirusTypeStatistics();
		// 昨天当前时间段数量（小时）
		int yestodayTimeNum = this.mvpsm.getYestodayMobileVirusStatistics();
		int pcYestoryTimeNum = this.pvpsm.getYestodayPCVirusTypeStatistics();
		// 本周当前时间段数量
		int weekNum = this.mvpsm.getWeekMobileVirusStatistics();
		int pcWeekNum = this.pvpsm.getWeekPCVirusTypeStatistics();
		// 上周当前时间段数量
		int upweekNum = this.mvpsm.getUpWeekMobileVirusStatistics();
		int pcUpWeekNum = this.pvpsm.getUpWeekPCVirusTypeStatistics();

		// 计算比例
		double dayper = (dayTimeNum - yestodayTimeNum) / 100d;

		dayper = itools.getTwoPointNum(dayper);
		double weekper = (weekNum - upweekNum) / 100d;
		weekper = itools.getTwoPointNum(weekper);

		double pcdayper = (pcDayTimeNum - pcYestoryTimeNum) / 100d;
		pcdayper = itools.getTwoPointNum(pcdayper);
		double pcweekper = (pcWeekNum - pcUpWeekNum) / 100d;
		pcweekper = itools.getTwoPointNum(pcweekper);

		Map<String, Object> mobile = new HashMap<String, Object>();
		mobile.put("mcount", dayNum);
		mobile.put("mday", dayper);
		mobile.put("mweek", weekper);
		Map<String, Object> pc = new HashMap<String, Object>();
		pc.put("pccount", pcDayNum);
		pc.put("pcday", pcdayper);
		pc.put("pcweek", pcweekper);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m", mobile);
		map.put("pc", pc);

		return new ResultCodeVo(true, 0, "success", map);
	}

	public ResultCodeVo getHostNum() {
		int count = ihm.getStatisticsNum();
		int black = ihmrm.getStatisticsNum();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("black", black);

		return new ResultCodeVo(true, 0, "success", map);
	}

	public ResultCodeVo getHostOrder() {
		List<ImportntHostStatistics> listStatistics = ihm.selectOrder();
		return new ResultCodeVo(true, 0, "success", listStatistics);
	}

	// =============流量攻击============================
	public ResultCodeVo getDDOSOrder() {
		List<DDOSStatistics> ddossOrders = ddosnewmpper.getDDOSOrder();
		return new ResultCodeVo(true, 0, "success", ddossOrders);
	}

	public ResultCodeVo getDDOSActiveNum() {
		// 当前数量
		int newDayNum = this.ddosnewmpper.dayNum();
		int dayNum = ddosmapper.dayNum();
		// 今天当前时间段数量（小时）
		int newNowNum = ddosnewmpper.nowNum();
		int nowNum = ddosmapper.nowNum();
		// 昨天当前时间段数量（小时）
		int newyestodayNum = ddosnewmpper.yestodayNum();
		int yestodayNum = ddosmapper.yestodayNum();
		// 本周当前时间段数量
		int newWeekNum = ddosnewmpper.weekNum();
		int weekNum = ddosmapper.weekNum();
		// 上周当前时间段数量
		int newUpWeekNum = ddosnewmpper.upWeekNum();
		int upWeekNum = ddosmapper.upWeekNum();

		// 计算比例
		double dayper = (newNowNum - newyestodayNum) / 100d;
		dayper = itools.getTwoPointNum(dayper);
		double weekper = (newWeekNum - newUpWeekNum) / 100d;
		weekper = itools.getTwoPointNum(weekper);

		double adayper = (nowNum - yestodayNum) / 100d;
		adayper = itools.getTwoPointNum(adayper);
		double aweekper = (weekNum - upWeekNum) / 100d;
		aweekper = itools.getTwoPointNum(aweekper);

		Map<String, Object> newddos = new HashMap<String, Object>();
		newddos.put("newcount", newDayNum);
		newddos.put("newday", dayper);
		newddos.put("newweek", weekper);
		Map<String, Object> activeddos = new HashMap<String, Object>();
		activeddos.put("activecount", dayNum);
		activeddos.put("activeday", adayper);
		activeddos.put("activeweek", aweekper);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newAdd", newddos);
		map.put("active", activeddos);

		return new ResultCodeVo(true, 0, "success", map);
	}

	public ResultCodeVo getDDOSNewView() {
		int count = ddosnewmpper.dayNum();
		List<StatisticsView> views = ddosnewmpper.getDDOSView();
		Map<String,Object> dataview = new HashMap<String,Object>();
		dataview.put("count", count);
		dataview.put("viewdata", views);
		return new ResultCodeVo(true, 0, "success", dataview);
	}

	public ResultCodeVo getDDOSActiveView() {
		
		int count = ddosmapper.dayNum();
		List<StatisticsView> views = ddosmapper.getDDOSView();
		Map<String,Object> dataview = new HashMap<String,Object>();
		dataview.put("count", count);
		dataview.put("viewdata", views);
		return new ResultCodeVo(true, 0, "success", dataview);
	}

	// ==================================================
	public ResultCodeVo getUrlOrder() {
		List<StatisticsView> views = urlmapper.getUrlOrder();
		return new ResultCodeVo(true, 0, "success", views);
	}

	public ResultCodeVo getUrlRatio() {
		List<StatisticsView> views = urlmapper.getUrlRatio();
		return new ResultCodeVo(true, 0, "success", views);
	}

	public ResultCodeVo getUrlView() {
		List<StatisticsView> views = urlmapper.getUrlView();
		return new ResultCodeVo(true, 0, "success", views);
	}

	public ResultCodeVo getUrlDayView() {
		List<StatisticsView> views = urlmapper.getUrlDayView();
		return new ResultCodeVo(true, 0, "success", views);
	}

	public ResultCodeVo getUrlNum() {
		// 当前数量
		int dayNum = urlmapper.dayNum();
		// 今天当前时间段数量（小时）
		int nowNum = urlmapper.nowNum();
		// 昨天当前时间段数量（小时）
		int yestodayNum = urlmapper.yestodayNum();
		// 本周当前时间段数量
		int weekNum = urlmapper.weekNum();
		// 上周当前时间段数量
		int upWeekNum = urlmapper.upWeekNum();

		// 计算比例
		double adayper = (nowNum - yestodayNum) / 100d;
		adayper = itools.getTwoPointNum(adayper);
		double aweekper = (weekNum - upWeekNum) / 100d;
		aweekper = itools.getTwoPointNum(aweekper);

		Map<String, Object> urlnum = new HashMap<String, Object>();
		urlnum.put("count", dayNum);
		urlnum.put("day", adayper);
		urlnum.put("week", aweekper);
		
		return new ResultCodeVo(true, 0, "success", urlnum);
	}

}
