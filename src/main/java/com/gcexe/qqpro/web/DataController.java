package com.gcexe.qqpro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcexe.qqpro.service.QQDataService;
import com.gcexe.qqpro.utils.ResultCodeVo;

@Controller
public class DataController {

	@Autowired
	QQDataService dataService;

	@RequestMapping(value = "/qqjob")
	@ResponseBody
	public ResultCodeVo qqData() {
		dataService.importantHostMonitorResult();
		//dataService.importantHost();
		//dataService.pcVirusProvinceStatHistory();
		//dataService.pcVirusTypeStatHistory();
		ResultCodeVo resultCoceVo = new ResultCodeVo(true, 0, "成功", null);
		return resultCoceVo;
	}

}
