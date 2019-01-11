package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class CusReReport extends EntitySupport {
	
	private String folno; //������
	private String gctnm; //����ͻ�
	private String fdtcltnm; //���˿ͻ�
	private String fdtpid; //��Ʒ����
	private String pdtnm; //����
	private String fdtexadt; //�˻����ʱ��

    
  
    public CusReReport(){
    	
    }

	public CusReReport(String folno, String gctnm, String fdtcltnm,
			String fdtpid, String pdtnm, String fdtexadt) {
		super();
		this.folno = folno;
		this.gctnm = gctnm;
		this.fdtcltnm = fdtcltnm;
		this.fdtpid = fdtpid;
		this.pdtnm = pdtnm;
		this.fdtexadt = fdtexadt;
	}



	public String getFolno() {
		return folno;
	}



	public void setFolno(String folno) {
		this.folno = folno;
	}



	public String getGctnm() {
		return gctnm;
	}



	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}



	public String getFdtcltnm() {
		return fdtcltnm;
	}



	public void setFdtcltnm(String fdtcltnm) {
		this.fdtcltnm = fdtcltnm;
	}



	public String getFdtpid() {
		return fdtpid;
	}



	public void setFdtpid(String fdtpid) {
		this.fdtpid = fdtpid;
	}



	public String getPdtnm() {
		return pdtnm;
	}



	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}



	public String getFdtexadt() {
		return fdtexadt;
	}



	public void setFdtexadt(String fdtexadt) {
		this.fdtexadt = fdtexadt;
	}
	
}
