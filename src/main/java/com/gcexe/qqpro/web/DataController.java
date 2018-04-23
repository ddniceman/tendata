package com.gcexe.qqpro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.gcexe.qqpro.taskservice.TaskJob;
import com.gcexe.qqpro.taskservice.TaskTool;
import com.gcexe.qqpro.utils.ResultCodeVo;

@Controller
public class DataController {

	@Autowired
	TaskTool quartzTool;

	@RequestMapping(value = "/qqjob")
	@ResponseBody
	public ResultCodeVo qqData() {
		quartzTool.addJob("ping1", "ping", "ping1", "ping", TaskJob.class, "*/10 * * * * ?");

		ResultCodeVo resultCoceVo = new ResultCodeVo(true, 0, "成功", null);
		return resultCoceVo;
	}

}
