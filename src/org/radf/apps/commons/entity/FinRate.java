package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class FinRate extends EntitySupport {
	Long finrateid;
	String band;
	String series;
	String rate;
	String fpdtnm;
	
	

	public Long getFinrateid() {
		return finrateid;
	}
	public void setFinrateid(Long finrateid) {
		this.finrateid = finrateid;
	}
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getFpdtnm() {
		return fpdtnm;
	}
	public void setFpdtnm(String fpdtnm) {
		this.fpdtnm = fpdtnm;
	}
	
}
