package com.gcexe.qqdata.service;

import com.gcexe.qqdata.uitls.ResultCodeVo;

public interface DataService {
	/**
	 * 手机病毒和电脑病毒类型
	 * @return
	 */
	public ResultCodeVo getPCAndMobileType();
	
	/**
	 * 今日手机病毒和电脑病毒数量
	 * @return
	 */
	public ResultCodeVo getPCAndMobileNum();
	
	
	/**
	 * 重点网址监控统计
	 * @return
	 */
	public ResultCodeVo getHostNum();
	
	/**
	 * 重点网址监控排行
	 * @return
	 */
	public ResultCodeVo getHostOrder();
	//==============================================
	
	/**
	 * 流量攻击排行
	 * @return
	 */
	public ResultCodeVo getDDOSOrder();
	
	/**
	 * 今日流量攻击活跃量
	 * @return
	 */
	public ResultCodeVo getDDOSActiveNum();
	
	
	/**
	 * 新增流量攻击趋势图
	 */
	public ResultCodeVo getDDOSNewView();
	
	/**
	 * 活跃流量攻击趋势图
	 */
	public ResultCodeVo getDDOSActiveView();
	
	//=======================================
	/**
	 * 恶意网站排行
	 */
	public ResultCodeVo getUrlOrder();
	/**
	 * 恶意网址大类占比
	 */
	public ResultCodeVo getUrlRatio();
	/**
	 * 恶意网址趋势图
	 */
	public ResultCodeVo getUrlView();
	/**
	 * 恶意网址日趋势图
	 */
	public ResultCodeVo getUrlDayView();
	/**
	 * 恶意网址数
	 */
	public ResultCodeVo getUrlNum();
	
	
	
}
