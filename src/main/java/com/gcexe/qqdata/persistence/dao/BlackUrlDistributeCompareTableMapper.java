package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.BlackUrlDistributeCompareTable;
import com.gcexe.qqdata.persistence.entity.BlackUrlDistributeCompareTableKey;
import com.gcexe.qqdata.persistence.statistics.StatisticsView;

public interface BlackUrlDistributeCompareTableMapper {

	int deleteByPrimaryKey(BlackUrlDistributeCompareTableKey key);

	int insert(BlackUrlDistributeCompareTable record);

	int insertSelective(BlackUrlDistributeCompareTable record);

	BlackUrlDistributeCompareTable selectByPrimaryKey(BlackUrlDistributeCompareTableKey key);

	int updateByPrimaryKeySelective(BlackUrlDistributeCompareTable record);

	int updateByPrimaryKey(BlackUrlDistributeCompareTable record);

	List<StatisticsView> getUrlOrder();
	
	List<StatisticsView> getUrlRatio();
	
	List<StatisticsView> getUrlView();
	
	List<StatisticsView> getUrlDayView();
	
	int dayNum();

	int nowNum();

	int yestodayNum();

	int weekNum();

	int upWeekNum();
	
}