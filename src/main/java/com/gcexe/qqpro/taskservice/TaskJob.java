package com.gcexe.qqpro.taskservice;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.gcexe.qqpro.qqaes.AesException;
import com.gcexe.qqpro.service.QQDataService;

public class TaskJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {	
		try {
			String result = QQDataService.getQQData("中国", "新疆", "0", 158);
			System.out.println("添加任务:" + result);
		} catch (AesException e) {
			e.printStackTrace();
		}

	}

}
