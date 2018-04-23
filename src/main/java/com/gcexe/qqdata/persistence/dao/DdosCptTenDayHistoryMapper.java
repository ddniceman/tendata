package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.DdosCptTenDayHistoryKey;
import com.gcexe.qqdata.persistence.statistics.StatisticsView;

public interface DdosCptTenDayHistoryMapper {
   
    int deleteByPrimaryKey(DdosCptTenDayHistoryKey key);

    int insert(DdosCptTenDayHistoryKey record);

    int insertSelective(DdosCptTenDayHistoryKey record);
    
    int dayNum();
    
    int nowNum();
    
    int yestodayNum();
    
    int weekNum();
    
    int upWeekNum();
    
    List<StatisticsView> getDDOSView();
}