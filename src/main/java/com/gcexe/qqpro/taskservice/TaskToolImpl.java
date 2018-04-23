package com.gcexe.qqpro.taskservice;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskToolImpl implements TaskTool {

	@Autowired
	private Scheduler quartzScheduler;

	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends Job> cls, String cron) {
		// 创建一个任务
		JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();
		// 创建一个触发器
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		// 调度器安排任务作业
		try {
			quartzScheduler.scheduleJob(job, trigger);
			// 如果调度器关闭 则开启调度
			if (!quartzScheduler.isShutdown()) {
				quartzScheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public boolean updateJob(String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup,
			String jobName, String jobGroup, String triggerName, String triggerGroup, String cron) {
		try {
			// 获取一个原有触发器
			CronTrigger trigger = (CronTrigger) quartzScheduler
					.getTrigger(TriggerKey.triggerKey(oldtriggerName, oldtriggerGroup));
			// 触发器不存在 修改失败
			if (trigger == null) {
				return false;
			} else {
				// 获取一个原有任务键
				JobKey jobKey = JobKey.jobKey(oldjobName, oldjobGroup);
				// 获取一个原有触发器键
				TriggerKey triggerKey = TriggerKey.triggerKey(oldtriggerName, oldtriggerGroup);
				// 获取任务类
				JobDetail job = quartzScheduler.getJobDetail(jobKey);
				Class<? extends Job> jobClass = job.getJobClass();
				// 停止触发器
				quartzScheduler.pauseTrigger(triggerKey);
				// 移除触发器
				quartzScheduler.unscheduleJob(triggerKey);
				// 删除任务
				quartzScheduler.deleteJob(jobKey);
				// 添加任务
				addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass, cron);
				return true;
			}

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void modifyJobTime(String triggerName, String triggerGroupName, String cron) {
		try {
			CronTrigger trigger = (CronTrigger) quartzScheduler
					.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
			if (trigger != null) {
				String oldTime = trigger.getCronExpression();
				if (!oldTime.equalsIgnoreCase(cron)) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					// 修改时间
					cronTrigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
					// 重启触发器
					quartzScheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
				}
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void pauseJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resumeJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {		
		try {
			// 停止触发器
			quartzScheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
			// 移除触发器
			quartzScheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
			// 删除任务
			quartzScheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void startSchedule() {
		try {
			quartzScheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdownSchedule() {
		try {
			if (!quartzScheduler.isShutdown()) {
				quartzScheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
