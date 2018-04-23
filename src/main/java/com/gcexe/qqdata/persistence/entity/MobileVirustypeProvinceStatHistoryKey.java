package com.gcexe.qqdata.persistence.entity;

import java.util.Date;

public class MobileVirustypeProvinceStatHistoryKey {

	private Integer cnt;

	private String province;

	private Date ts;

	private String virusType;

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getVirusType() {
		return virusType;
	}

	public void setVirusType(String virusType) {
		this.virusType = virusType;
	}
}