package com.gcexe.qqdata.persistence.dao;

import com.gcexe.qqdata.persistence.entity.ImportantHostMonitorResult;

public interface ImportantHostMonitorResultMapper {
     
    int insert(ImportantHostMonitorResult record);
    int insertSelective(ImportantHostMonitorResult record);
    int updateByPrimaryKeySelective(ImportantHostMonitorResult record);
    int updateByPrimaryKey(ImportantHostMonitorResult record);
    int getStatisticsNum();
}