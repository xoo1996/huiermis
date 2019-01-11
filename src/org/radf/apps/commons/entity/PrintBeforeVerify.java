package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class PrintBeforeVerify extends EntitySupport {
	
	private String folno; //������
	private String gctnm; //��������
	private String fdtcltnm; //���˿ͻ�
	private String pdtnm; //��Ʒ����
	private String tmksid; //�ɱ�����
	private String fdtqnt; //��Ʒ���
	private String fdtnt; //��ע

   
    public PrintBeforeVerify(){
    	
    }


	public PrintBeforeVerify(String folno, String gctnm, String fdtcltnm,
			String pdtnm, String tmksid, String fdtqnt, String fdtnt) {
		super();
		this.folno = folno;
		this.gctnm = gctnm;
		this.fdtcltnm = fdtcltnm;
		this.pdtnm = pdtnm;
		this.tmksid = tmksid;
		this.fdtqnt = fdtqnt;
		this.fdtnt = fdtnt;
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


	public String getPdtnm() {
		return pdtnm;
	}


	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}


	public String getTmksid() {
		return tmksid;
	}


	public void setTmksid(String tmksid) {
		this.tmksid = tmksid;
	}


	public String getFdtqnt() {
		return fdtqnt;
	}


	public void setFdtqnt(String fdtqnt) {
		this.fdtqnt = fdtqnt;
	}


	public String getFdtnt() {
		return fdtnt;
	}


	public void setFdtnt(String fdtnt) {
		this.fdtnt = fdtnt;
	}
    
    

}
