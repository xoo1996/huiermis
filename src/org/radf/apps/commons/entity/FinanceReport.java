package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class FinanceReport extends EntitySupport {
	
	private Long bindno; 
	private String gctsname; 
	private String taxno;
	private String fpdtnm;
	private String taxrate;
	private String fpdtmodel;
	private String pdtut;
	private String pdtnum;
	private String finprc;
	private String tmksid;
	private String sellprc;
	private String gctemail;
	private String finnt;
	private String pdtprc;
	private String finrate;
	//private String fintel;
	private String gctmobilephone; 
	private String gctaddr; 
	private String gctdepositbank; 
	private String gctdepositid;
	private String gcttel;
	
	private String comaddr; 
	private String depositbank; 
	private String depositid;
	private String specialtel;
	
	
	private String gctnm;
	private String retailname; 
	private String retailtaxno;
	
	
	
	public FinanceReport() {
		super();
	}

	public FinanceReport(Long bindno, String gctsname, String taxno,
			String fpdtnm, String taxrate, String fpdtmodel, String pdtut,
			String pdtnum, String finprc, String tmksid, String sellprc,
			String gctemail, String finnt, String pdtprc, String finrate) {
		super();
		this.bindno = bindno;
		this.gctsname = gctsname;
		this.taxno = taxno;
		this.fpdtnm = fpdtnm;
		this.taxrate = taxrate;
		this.fpdtmodel = fpdtmodel;
		this.pdtut = pdtut;
		this.pdtnum = pdtnum;
		this.finprc = finprc;
		this.tmksid = tmksid;
		this.sellprc = sellprc;
		this.gctemail = gctemail;
		this.finnt = finnt;
		this.pdtprc = pdtprc;
		this.finrate = finrate;
	}

	public FinanceReport(Long bindno, String gctnm, String taxno,
			String fpdtnm, String taxrate, String fpdtmodel, String pdtut,
			String pdtnum, String finprc, String tmksid, String sellprc,
			String gctemail, String finnt, String pdtprc, String finrate, String retailname, String retailtaxno) {
		super();
		this.bindno = bindno;
		this.gctnm = gctnm;
		this.taxno = taxno;
		this.fpdtnm = fpdtnm;
		this.taxrate = taxrate;
		this.fpdtmodel = fpdtmodel;
		this.pdtut = pdtut;
		this.pdtnum = pdtnum;
		this.finprc = finprc;
		this.tmksid = tmksid;
		this.sellprc = sellprc;
		this.gctemail = gctemail;
		this.finnt = finnt;
		this.pdtprc = pdtprc;
		this.finrate = finrate;
		this.retailname = retailname;
		this.retailtaxno = retailtaxno;
	}
	
	
	public String getGctaddr() {
		return gctaddr;
	}

	public void setGctaddr(String gctaddr) {
		this.gctaddr = gctaddr;
	}

	public String getGctdepositbank() {
		return gctdepositbank;
	}

	public void setGctdepositbank(String gctdepositbank) {
		this.gctdepositbank = gctdepositbank;
	}

	public String getGctdepositid() {
		return gctdepositid;
	}

	public void setGctdepositid(String gctdepositid) {
		this.gctdepositid = gctdepositid;
	}

	public String getGcttel() {
		return gcttel;
	}

	public void setGcttel(String gcttel) {
		this.gcttel = gcttel;
	}

	public String getGctnm() {
		return gctnm;
	}

	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}

	public String getRetailname() {
		return retailname;
	}

	public void setRetailname(String retailname) {
		this.retailname = retailname;
	}

	public String getRetailtaxno() {
		return retailtaxno;
	}

	public void setRetailtaxno(String retailtaxno) {
		this.retailtaxno = retailtaxno;
	}

	public String getGctmobilephone() {
		return gctmobilephone;
	}

	public void setGctmobilephone(String gctmobilephone) {
		this.gctmobilephone = gctmobilephone;
	}

	public String getPdtprc() {
		return pdtprc;
	}

	public void setPdtprc(String pdtprc) {
		this.pdtprc = pdtprc;
	}

	public String getFinrate() {
		return finrate;
	}

	public void setFinrate(String finrate) {
		this.finrate = finrate;
	}

	public Long getBindno() {
		return bindno;
	}

	public void setBindno(Long bindno) {
		this.bindno = bindno;
	}

	public String getGctsname() {
		return gctsname;
	}

	public void setGctsname(String gctsname) {
		this.gctsname = gctsname;
	}

	public String getTaxno() {
		return taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	public String getFpdtnm() {
		return fpdtnm;
	}

	public void setFpdtnm(String fpdtnm) {
		this.fpdtnm = fpdtnm;
	}

	public String getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}

	public String getFpdtmodel() {
		return fpdtmodel;
	}

	public void setFpdtmodel(String fpdtmodel) {
		this.fpdtmodel = fpdtmodel;
	}

	public String getPdtut() {
		return pdtut;
	}

	public void setPdtut(String pdtut) {
		this.pdtut = pdtut;
	}

	public String getPdtnum() {
		return pdtnum;
	}

	public void setPdtnum(String pdtnum) {
		this.pdtnum = pdtnum;
	}

	public String getFinprc() {
		return finprc;
	}

	public void setFinprc(String finprc) {
		this.finprc = finprc;
	}

	public String getTmksid() {
		return tmksid;
	}

	public void setTmksid(String tmksid) {
		this.tmksid = tmksid;
	}

	public String getSellprc() {
		return sellprc;
	}

	public void setSellprc(String sellprc) {
		this.sellprc = sellprc;
	}

	public String getGctemail() {
		return gctemail;
	}

	public void setGctemail(String gctemail) {
		this.gctemail = gctemail;
	}

	public String getFinnt() {
		return finnt;
	}

	public void setFinnt(String finnt) {
		this.finnt = finnt;
	}

	public String getComaddr() {
		return comaddr;
	}

	public void setComaddr(String comaddr) {
		this.comaddr = comaddr;
	}

	public String getDepositbank() {
		return depositbank;
	}

	public void setDepositbank(String depositbank) {
		this.depositbank = depositbank;
	}

	public String getDepositid() {
		return depositid;
	}

	public void setDepositid(String depositid) {
		this.depositid = depositid;
	}

	public String getSpecialtel() {
		return specialtel;
	}

	public void setSpecialtel(String specialtel) {
		this.specialtel = specialtel;
	}
	
	
	

}
