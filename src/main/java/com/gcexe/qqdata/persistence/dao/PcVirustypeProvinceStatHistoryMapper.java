package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.PcVirustypeProvinceStatHistoryKey;
import com.gcexe.qqdata.persistence.statistics.VirusTypeStatistics;

public interface PcVirustypeProvinceStatHistoryMapper {

	int deleteByPrimaryKey(PcVirustypeProvinceStatHistoryKey key);

	int insert(PcVirustypeProvinceStatHistoryKey record);

	int insertSelective(PcVirustypeProvinceStatHistoryKey record);

	List<VirusTypeStatistics> getPCVirusTypeStatistics();

	int getDayNum();

	int getDayPCVirusTypeStatistics();

	int getYestodayPCVirusTypeStatistics();

	int getWeekPCVirusTypeStatistics();

	int getUpWeekPCVirusTypeStatistics();
}