package org.radf.apps.business.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class BatForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer batYear; // ��ʼ��
	private Integer batYearEnd;//�������
	private Integer batMonth; // ��ʼ�·�
	private Integer batMonthEnd; // �����·�
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