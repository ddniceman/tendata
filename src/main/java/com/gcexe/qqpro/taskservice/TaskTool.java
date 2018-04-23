package com.gcexe.qqpro.taskservice;

import org.quartz.Job;

public interface TaskTool {

	/**
	 * 添加任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param cls
	 *            任务类
	 * @param cron
	 *            时间定义
	 */
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends Job> cls, String cron);

	/**
	 * 修改任务
	 * 
	 * @param oldjobName
	 *            原任务名
	 * @param oldjobGroup
	 *            原任务组名
	 * @param oldtriggerName
	 *            原触发器名
	 * @param oldtriggerGroup
	 *            原触发器组名
	 * @param jobName
	 *            新任务名
	 * @param jobGroup
	 *            新任务组名
	 * @param triggerName
	 *            新触发器名
	 * @param triggerGroup
	 *            新触发器组名
	 * @param cron
	 *            时间定义
	 * @return
	 */
	public boolean updateJob(String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup,
			String jobName, String jobGroup, String triggerName, String triggerGroup, String cron);

	/**
	 * 修改任务时间
	 * 
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param cron
	 *            时间定义
	 */
	public void modifyJobTime(String triggerName, String triggerGroupName, String cron);

	/**
	 * 暂停指定任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 */
	public void pauseJob(String jobName, String jobGroupName);

	/**
	 * 重启指定任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 */
	public void resumeJob(String jobName, String jobGroupName);

	/**
	 * 删除指定任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 */
	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName);

	/**
	 * 开始所有任务并启动调度器
	 */
	public void startSchedule();

	/**
	 * 关闭所有任务并启动调度器
	 */
	public void shutdownSchedule();

}
