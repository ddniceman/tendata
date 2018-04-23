package com.gcexe.qqdata.persistence.dao;

import java.util.List;

import com.gcexe.qqdata.persistence.entity.ImportantHost;
import com.gcexe.qqdata.persistence.statistics.ImportntHostStatistics;

public interface ImportantHostMapper {

	int insert(ImportantHost record);

	int insertSelective(ImportantHost record);

	int updateByPrimaryKeySelective(ImportantHost record);

	int updateByPrimaryKey(ImportantHost record);
	
	int getStatisticsNum();
	
	List<ImportntHostStatistics>  selectOrder();
	
	
	
}