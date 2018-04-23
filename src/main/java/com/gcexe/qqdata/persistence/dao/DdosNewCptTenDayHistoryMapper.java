package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.DdosNewCptTenDayHistoryKey;
import com.gcexe.qqdata.persistence.statistics.DDOSStatistics;
import com.gcexe.qqdata.persistence.statistics.StatisticsView;

public interface DdosNewCptTenDayHistoryMapper {

	int deleteByPrimaryKey(DdosNewCptTenDayHistoryKey key);

	int insert(DdosNewCptTenDayHistoryKey record);

	int insertSelective(DdosNewCptTenDayHistoryKey record);

	// 流量攻击排行榜 新增量，活跃量
	List<DDOSStatistics> getDDOSOrder();

	int dayNum();

	int nowNum();

	int yestodayNum();

	int weekNum();

	int upWeekNum();
	
	List<StatisticsView> getDDOSView();
}