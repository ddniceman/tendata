package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.MobileVirustypeProvinceStatHistoryKey;
import com.gcexe.qqdata.persistence.statistics.VirusTypeStatistics;

public interface MobileVirustypeProvinceStatHistoryMapper {

	int deleteByPrimaryKey(MobileVirustypeProvinceStatHistoryKey key);

	int insert(MobileVirustypeProvinceStatHistoryKey record);

	int insertSelective(MobileVirustypeProvinceStatHistoryKey record);

	List<VirusTypeStatistics> getMobileVirusTypeStatistics();

	int getDayNum();
	
	int getDayMobileVirusStatistics();

	int getYestodayMobileVirusStatistics();

	int getWeekMobileVirusStatistics();

	int getUpWeekMobileVirusStatistics();
}