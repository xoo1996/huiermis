package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class PrintFangXing extends EntitySupport {
	
	private String qafno; //������
	private String gctnm; //��������
	private String qacltnm; //���˿ͻ�
	private String qasid; //������
	private String pdtnm; //�������ͺ�
	private String isfangxing; //�Ƿ����
	private String qafxman; //������

   
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
