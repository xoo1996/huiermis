package org.radf.apps.commons.entity;

import java.util.Date;
import org.radf.plat.util.entity.EntitySupport;

//开票信息表
public class Finance extends EntitySupport {
	
	private String finno; //开票编号
	private String fintype;//开票类型
	private String typeno; //类型编号
	private String notype; // 编号类型
	private String gctnm; //店铺名
	private String gctid; //店铺编号
	private String ictid;
	private String ictnm;
	private String pdtnm; //商品名称
	private String band;
	private String pdtid; //商品编号
	private String finnt; //开票备注
	private Date finsubdt; //提交申请日期
	private Date finpassdt; //通过申请日期
	private String tmksid; //定制机机身码
	private String invoicecode; //发票代码
	private String invoiceno; //发票编号
	private String retailname; //销售开票抬头
	private String retailtaxno; //销售开票税号
	private String comaddr; //公司地址
	private String depositbank; //开户行
	private String depositid; //开户账号
	private String specialtel; //专用开票电话
	
	private String bindno;//捆绑编号
	private String rate;//扣率
	
	private String sellprc;//销售价格
		
	private String pdtnum; //数量
	private String finrate;//开票扣率
	private Date chgdt;//收费日期
	private Date redt;//退货日期
	private String finprccount;//总额
	private String finprc;

	private String pdtprc;
	private String fintel;
	
	private Date start;//开始时间
	private Date end;//结束时间
	
	public String getIctid() {
		return ictid;
	}
	public void setIctid(String ictid) {
		this.ictid = ictid;
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
	public String getFintel() {
		return fintel;
	}
	public void setFintel(String fintel) {
		this.fintel = fintel;
	}
	public String getSellprc() {
		return sellprc;
	}
	public void setSellprc(String sellprc) {
		this.sellprc = sellprc;
	}
	public Date getRedt() {
		return redt;
	}
	public void setRedt(Date redt) {
		this.redt = redt;
	}
	public String getFinno() {
		return finno;
	}
	public void setFinno(String finno) {
		this.finno = finno;
	}
	public String getFintype() {
		return fintype;
	}
	public void setFintype(String fintype) {
		this.fintype = fintype;
	}
	public String getTypeno() {
		return typeno;
	}
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}
	public String getNotype() {
		return notype;
	}
	public void setNotype(String notype) {
		this.notype = notype;
	}
	public String getGctnm() {
		return gctnm;
	}
	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}
	public String getGctid() {
		return gctid;
	}
	public void setGctid(String gctid) {
		this.gctid = gctid;
	}
	public String getPdtnm() {
		return pdtnm;
	}
	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}
	public String getPdtid() {
		return pdtid;
	}
	public void setPdtid(String pdtid) {
		this.pdtid = pdtid;
	}
	public String getFinnt() {
		return finnt;
	}
	public void setFinnt(String finnt) {
		this.finnt = finnt;
	}
	public Date getFinsubdt() {
		return finsubdt;
	}
	public void setFinsubdt(Date finsubdt) {
		this.finsubdt = finsubdt;
	}
	public Date getFinpassdt() {
		return finpassdt;
	}
	public void setFinpassdt(Date finpassdt) {
		this.finpassdt = finpassdt;
	}
	public String getTmksid() {
		return tmksid;
	}
	public void setTmksid(String tmksid) {
		this.tmksid = tmksid;
	}
	public String getInvoicecode() {
		return invoicecode;
	}
	public void setInvoicecode(String invoicecode) {
		this.invoicecode = invoicecode;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public String getBindno() {
		return bindno;
	}
	public void setBindno(String bindno) {
		this.bindno = bindno;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPdtnum() {
		return pdtnum;
	}
	public void setPdtnum(String pdtnum) {
		this.pdtnum = pdtnum;
	}
	public String getFinrate() {
		return finrate;
	}
	public void setFinrate(String finrate) {
		this.finrate = finrate;
	}
	public Date getChgdt() {
		return chgdt;
	}
	public void setChgdt(Date chgdt) {
		this.chgdt = chgdt;
	}
	public String getFinprccount() {
		return finprccount;
	}
	public void setFinprccount(String finprccount) {
		this.finprccount = finprccount;
	}
	public String getFinprc() {
		return finprc;
	}
	public void setFinprc(String finprc) {
		this.finprc = finprc;
	}
	public String getIctnm() {
		return ictnm;
	}
	public void setIctnm(String ictnm) {
		this.ictnm = ictnm;
	}
	public String getPdtprc() {
		return pdtprc;
	}
	public void setPdtprc(String pdtprc) {
		this.pdtprc = pdtprc;
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