package org.radf.apps.finance.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FinanceHeaderForm extends ActionForm {
	private String fintype; //开票类型
	private String fintype2; //开票类型
	private String notype; //编号类型
	private String typeno; //类型编号
	private String gctnm;//团体客户
	private String ictnm;//个人客户
	private String pdtid;//商品编号
	private String pdtnm;//商品名称
	private Date start;//开始时间
	private Date end;//结束时间
	
	
	private String gctid; // 客户代码
	private String gctaddr; // 客户地址
	private String gctptcd; // 客户邮编
	private String gctemail; // 客户Email
	private String gctcnm; // 联系人姓名
	private String gcttel;// 联系电话
	private String gctnt; // 备注
	private String gctoprcd;// 操作员代码
	private Date gctdate; // 日期
	private Date gctstart; // 开业时间
	private String gctfax; // 传真
	private String gctarea; // 所属区域
	private String gctprovince; // 省份
	private String gcttype;// 类型
	private String gctvalid; // 是否有效
	private String gctsname;//简称
	private String taxno;//税号
	private String gctmobilephone;//手机号
	private String gctdepositbank;//开户行
	private String gctdepositid;//开户账号

	private String finsubdtstart;
	private String finsubdtend;
	private String finpassdtstart;
	private String finpassdtend;
	private String chgdtstart;
	private String chgdtend;
	
	private String taxrate;
	private String finisver;
	
	
	
	public String getGctdepositbank() {
		return gctdepositbank;
	}
	public void setGctdepositbank(String depositbank) {
		this.gctdepositbank = depositbank;
	}
	public String getGctdepositid() {
		return gctdepositid;
	}
	public void setGctdepositid(String depositid) {
		this.gctdepositid = depositid;
	}
	public String getFintype2() {
		return fintype2;
	}
	public void setFintype2(String fintype2) {
		this.fintype2 = fintype2;
	}
	public String getIctnm() {
		return ictnm;
	}
	public void setIctnm(String ictnm) {
		this.ictnm = ictnm;
	}
	public String getGctmobilephone() {
		return gctmobilephone;
	}
	public void setGctmobilephone(String gctmobilephone) {
		this.gctmobilephone = gctmobilephone;
	}
	public String getFinisver() {
		return finisver;
	}
	public void setFinisver(String finisver) {
		this.finisver = finisver;
	}
	public String getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}
	public String getFintype() {
		return fintype;
	}
	public void setFintype(String fintype) {
		this.fintype = fintype;
	}
	public String getGctnm() {
		return gctnm;
	}
	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}
	public String getPdtid() {
		return pdtid;
	}
	public void setPdtid(String pdtid) {
		this.pdtid = pdtid;
	}
	public String getPdtnm() {
		return pdtnm;
	}
	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}
	public String getNotype() {
		return notype;
	}
	public void setNotype(String notype) {
		this.notype = notype;
	}
	public String getTypeno() {
		return typeno;
	}
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getGctid() {
		return gctid;
	}
	public void setGctid(String gctid) {
		this.gctid = gctid;
	}
	public String getGctaddr() {
		return gctaddr;
	}
	public void setGctaddr(String gctaddr) {
		this.gctaddr = gctaddr;
	}
	public String getGctptcd() {
		return gctptcd;
	}
	public void setGctptcd(String gctptcd) {
		this.gctptcd = gctptcd;
	}
	public String getGctemail() {
		return gctemail;
	}
	public void setGctemail(String gctemail) {
		this.gctemail = gctemail;
	}
	public String getGctcnm() {
		return gctcnm;
	}
	public void setGctcnm(String gctcnm) {
		this.gctcnm = gctcnm;
	}
	public String getGcttel() {
		return gcttel;
	}
	public void setGcttel(String gcttel) {
		this.gcttel = gcttel;
	}
	public String getGctnt() {
		return gctnt;
	}
	public void setGctnt(String gctnt) {
		this.gctnt = gctnt;
	}
	public String getGctoprcd() {
		return gctoprcd;
	}
	public void setGctoprcd(String gctoprcd) {
		this.gctoprcd = gctoprcd;
	}
	public Date getGctdate() {
		return gctdate;
	}
	public void setGctdate(Date gctdate) {
		this.gctdate = gctdate;
	}
	public Date getGctstart() {
		return gctstart;
	}
	public void setGctstart(Date gctstart) {
		this.gctstart = gctstart;
	}
	public String getGctfax() {
		return gctfax;
	}
	public void setGctfax(String gctfax) {
		this.gctfax = gctfax;
	}
	public String getGctarea() {
		return gctarea;
	}
	public void setGctarea(String gctarea) {
		this.gctarea = gctarea;
	}
	public String getGctprovince() {
		return gctprovince;
	}
	public void setGctprovince(String gctprovince) {
		this.gctprovince = gctprovince;
	}
	public String getGcttype() {
		return gcttype;
	}
	public void setGcttype(String gcttype) {
		this.gcttype = gcttype;
	}
	public String getGctvalid() {
		return gctvalid;
	}
	public void setGctvalid(String gctvalid) {
		this.gctvalid = gctvalid;
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
	public String getFinsubdtstart() {
		return finsubdtstart;
	}
	public void setFinsubdtstart(String finsubdtstart) {
		this.finsubdtstart = finsubdtstart;
	}
	public String getFinsubdtend() {
		return finsubdtend;
	}
	public void setFinsubdtend(String finsubdtend) {
		this.finsubdtend = finsubdtend;
	}
	public String getFinpassdtstart() {
		return finpassdtstart;
	}
	public void setFinpassdtstart(String finpassdtstart) {
		this.finpassdtstart = finpassdtstart;
	}
	public String getFinpassdtend() {
		return finpassdtend;
	}
	public void setFinpassdtend(String finpassdtend) {
		this.finpassdtend = finpassdtend;
	}
	public String getChgdtstart() {
		return chgdtstart;
	}
	public void setChgdtstart(String chgdtstart) {
		this.chgdtstart = chgdtstart;
	}
	public String getChgdtend() {
		return chgdtend;
	}
	public void setChgdtend(String chgdtend) {
		this.chgdtend = chgdtend;
	}
	
}
