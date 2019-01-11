package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class PrintFangXing extends EntitySupport {
	
	private String qafno; //订单号
	private String gctnm; //所属团体
	private String qacltnm; //个人客户
	private String qasid; //机身编号
	private String pdtnm; //助听器型号
	private String isfangxing; //是否放行
	private String qafxman; //放行人

   
    public PrintFangXing(){
    	
    }


	public PrintFangXing(String qafno, String gctnm, String qacltnm,
			String qasid, String pdtnm, String isfangxing, String qafxman) {
		super();
		this.qafno = qafno;
		this.gctnm = gctnm;
		this.qacltnm = qacltnm;
		this.qasid = qasid;
		this.pdtnm = pdtnm;
		this.isfangxing = isfangxing;
		this.qafxman = qafxman;
	}


	public String getQafno() {
		return qafno;
	}


	public void setQafno(String qafno) {
		this.qafno = qafno;
	}


	public String getGctnm() {
		return gctnm;
	}


	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}


	public String getQacltnm() {
		return qacltnm;
	}


	public void setQacltnm(String qacltnm) {
		this.qacltnm = qacltnm;
	}


	public String getQasid() {
		return qasid;
	}


	public void setQasid(String qasid) {
		this.qasid = qasid;
	}


	public String getPdtnm() {
		return pdtnm;
	}


	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}


	public String getIsfangxing() {
		return isfangxing;
	}


	public void setIsfangxing(String isfangxing) {
		this.isfangxing = isfangxing;
	}


	public String getQafxman() {
		return qafxman;
	}


	public void setQafxman(String qafxman) {
		this.qafxman = qafxman;
	}


}
