package com.gcexe.qqdata.persistence.dao;

import com.gcexe.qqdata.persistence.entity.PcVirusProvinceStatHistoryKey;

public interface PcVirusProvinceStatHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pc_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:53:28 CST 2018
     */
    int deleteByPrimaryKey(PcVirusProvinceStatHistoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pc_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:53:28 CST 2018
     */
    int insert(PcVirusProvinceStatHistoryKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pc_virus_province_stat_history
     *
     * @mbggenerated Fri Apr 20 15:53:28 CST 2018
     */
    int insertSelective(PcVirusProvinceStatHistoryKey record);
}