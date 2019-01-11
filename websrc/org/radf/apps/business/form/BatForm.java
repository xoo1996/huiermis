package org.radf.apps.business.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class BatForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer batYear; // 开始年
	private Integer batYearEnd;//结束年份
	private Integer batMonth; // 开始月份
	private Integer batMonthEnd; // 结束月份
	public Integer getBatYear() {
		return batYear;
	}
	public void setBatYear(Integer batYear) {
		this.batYear = batYear;
	}
	public Integer getBatYearEnd() {
		return batYearEnd;
	}
	public void setBatYearEnd(Integer batYearEnd) {
		this.batYearEnd = batYearEnd;
	}
	public Integer getBatMonth() {
		return batMonth;
	}
	public void setBatMonth(Integer batMonth) {
		this.batMonth = batMonth;
	}
	public Integer getBatMonthEnd() {
		return batMonthEnd;
	}
	public void setBatMonthEnd(Integer batMonthEnd) {
		this.batMonthEnd = batMonthEnd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}