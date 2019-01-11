package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class PrintBeforeVerify extends EntitySupport {
	
	private String folno; //订单号
	private String gctnm; //所属团体
	private String fdtcltnm; //个人客户
	private String pdtnm; //商品名称
	private String tmksid; //成本扣率
	private String fdtqnt; //商品金额
	private String fdtnt; //备注

   
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
