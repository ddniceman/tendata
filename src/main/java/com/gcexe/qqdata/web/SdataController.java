package com.gcexe.qqdata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcexe.qqdata.service.DataService;
import com.gcexe.qqdata.uitls.ResultCodeVo;

@Controller
@RequestMapping("/qqdata")
public class SdataController {
	@Autowired
	private DataService dataService;

	/**
	 * 手机病毒和电脑病毒的类型
	 * @return
	 */
	@RequestMapping(value = "/pcandmobilevirustype", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getPCAndMobileType() {
		return dataService.getPCAndMobileType();
		
	}
	/**
	 * 今日手机病毒和电脑病毒数量
	 * @return
	 */
	@RequestMapping(value = "/pcandmobilevirusnum", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getPCAndMobileNum() {
		return dataService.getPCAndMobileNum();
		
	}
	
	/**
	 * 重点网站数量
	 * @return
	 */
	@RequestMapping(value = "/hostnum", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getHostNum() {
		return dataService.getHostNum();
		
	}
	/**
	 * 重点网站排行
	 * @return
	 */
	@RequestMapping(value = "/hostorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getHostOrder() {
		return dataService.getHostOrder();
	}
	//================================================
	
	/**
	 * 流量攻击排行
	 * @return
	 */
	@RequestMapping(value = "/ddosorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getDDOSOrder() {
		return dataService.getDDOSOrder();
	}
	
	/**
	 * 今日流量攻击活跃量、新增量
	 * @return
	 */
	@RequestMapping(value = "/ddosnum", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getDDOSActiveNum() {
		return dataService.getDDOSActiveNum();
	}
	/**
	 * 新增流量攻击趋势图
	 */
	@RequestMapping(value = "/ddosnewview", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getDDOSNewView() {
		return this.dataService.getDDOSNewView();
	}
	
	/**
	 * 活跃流量攻击趋势图
	 */
	@RequestMapping(value = "/ddosactiveview", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getDDOSActiveView()
	{
		return this.dataService.getDDOSActiveView();
	}
	//===========================================
	
	/**
	 * 恶意网站排行
	 */
	@RequestMapping(value = "/urlorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getUrlOrder()
	{
		return this.dataService.getUrlOrder();
	}
	/**
	 * 恶意网站排行
	 */
	@RequestMapping(value = "/urlratio", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getUrlRatio()
	{
		return this.dataService.getUrlRatio();
	}
	/**
	 * 恶意网址趋势图
	 */
	@RequestMapping(value = "/urlview", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getUrlView()
	{
		return this.dataService.getUrlView();
	}
	/**
	 * 恶意网址日趋势图
	 */
	@RequestMapping(value = "/urldayview", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getUrlDayView()
	{
		return this.dataService.getUrlDayView();
	}
	/**
	 * 恶意网址数
	 * @return
	 */
	@RequestMapping(value = "/urlnum", method = RequestMethod.POST)
	@ResponseBody
	public ResultCodeVo getUrlNum() {
		return dataService.getUrlNum();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
