package com.gcexe.qqdata.persistence.dao;

import com.gcexe.qqdata.persistence.entity.MobileVirusProvinceStatHistoryKey;

public interface MobileVirusProvinceStatHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:49:32 CST 2018
     */
    int deleteByPrimaryKey(MobileVirusProvinceStatHistoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:49:32 CST 2018
     */
    int insert(MobileVirusProvinceStatHistoryKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:49:32 CST 2018
     */
    int insertSelective(MobileVirusProvinceStatHistoryKey record);
}