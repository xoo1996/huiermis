package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;
import java.util.Date;

public class OrderAndStore extends EntitySupport {
	//订单信息
	private String folno; // 订单号码
	private String fdtfno; // 订单号码
	private String folctid;// 客户代码
	private String gctnm; // 客户名称
	private String fdtpid;//商品编号
	private Date foldt; //订货日期
	private String folsta; // 订单状态
	private String folischg;// 是否已收费
	private Date folchgdt; //收费日期
	private Date fdtodt; //退货日期
	private Date fdtrecheaddt; //退货到总部日期
	private Date fdtexadt; //总部审核退货日期
	
	//库存信息
	private String stoid;// 库存编号
	private String stogrcliid;// 团体客户代码
	private String stoproid;// 商品代码
	private String stoprotype;// 商品类型
	private String stoproname;// 商品名称
	private Integer stoamount;// 数量(入库/出库)
	private String stoamountun;// 数量单位
	private Double stoproprice;// 单价
	private Date stodate;// 出入库日期
	private Integer stoactype;// 动作（1、入库-1、出库）
	private String storemark;// 备注
	private String stooprcd;// 操作员代码
	private String stodisc;// 是否已报废
	private String isrepair;// 是否维修订单
	
	
	public String getFdtfno() {
		return fdtfno;
	}
	public void setFdtno(String fdtno) {
		this.fdtfno = fdtno;
	}
	public String getFolno() {
		return folno;
	}
	public void setFolno(String folno) {
		this.folno = folno;
	}
	public String getFolctid() {
		return folctid;
	}
	public void setFolctid(String folctid) {
		this.folctid = folctid;
	}
	public String getGctnm() {
		return gctnm;
	}
	public void setGctnm(String gctnm) {
		this.gctnm = gctnm;
	}
	public String getFdtpid() {
		return fdtpid;
	}
	public void setFdtpid(String fdtpid) {
		this.fdtpid = fdtpid;
	}
	public Date getFoldt() {
		return foldt;
	}
	public void setFoldt(Date foldt) {
		this.foldt = foldt;
	}
	public String getFolsta() {
		return folsta;
	}
	public void setFolsta(String folsta) {
		this.folsta = folsta;
	}
	public String getFolischg() {
		return folischg;
	}
	public void setFolischg(String folischg) {
		this.folischg = folischg;
	}
	public Date getFolchgdt() {
		return folchgdt;
	}
	public void setFolchgdt(Date folchgdt) {
		this.folchgdt = folchgdt;
	}
	public Date getFdtodt() {
		return fdtodt;
	}
	public void setFdtodt(Date fdtodt) {
		this.fdtodt = fdtodt;
	}
	public Date getFdtrecheaddt() {
		return fdtrecheaddt;
	}
	public void setFdtrecheaddt(Date fdtrecheaddt) {
		this.fdtrecheaddt = fdtrecheaddt;
	}
	public Date getFdtexadt() {
		return fdtexadt;
	}
	public void setFdtexadt(Date fdtexadt) {
		this.fdtexadt = fdtexadt;
	}
	public String getStoid() {
		return stoid;
	}
	public void setStoid(String stoid) {
		this.stoid = stoid;
	}
	public String getStogrcliid() {
		return stogrcliid;
	}
	public void setStogrcliid(String stogrcliid) {
		this.stogrcliid = stogrcliid;
	}
	public String getStoproid() {
		return stoproid;
	}
	public void setStoproid(String stoproid) {
		this.stoproid = stoproid;
	}
	public String getStoprotype() {
		return stoprotype;
	}
	public void setStoprotype(String stoprotype) {
		this.stoprotype = stoprotype;
	}
	public String getStoproname() {
		return stoproname;
	}
	public void setStoproname(String stoproname) {
		this.stoproname = stoproname;
	}
	public Integer getStoamount() {
		return stoamount;
	}
	public void setStoamount(Integer stoamount) {
		this.stoamount = stoamount;
	}
	public String getStoamountun() {
		return stoamountun;
	}
	public void setStoamountun(String stoamountun) {
		this.stoamountun = stoamountun;
	}
	public Double getStoproprice() {
		return stoproprice;
	}
	public void setStoproprice(Double stoproprice) {
		this.stoproprice = stoproprice;
	}
	public Date getStodate() {
		return stodate;
	}
	public void setStodate(Date stodate) {
		this.stodate = stodate;
	}
	public Integer getStoactype() {
		return stoactype;
	}
	public void setStoactype(Integer stoactype) {
		this.stoactype = stoactype;
	}
	public String getStoremark() {
		return storemark;
	}
	public void setStoremark(String storemark) {
		this.storemark = storemark;
	}
	public String getStooprcd() {
		return stooprcd;
	}
	public void setStooprcd(String stooprcd) {
		this.stooprcd = stooprcd;
	}
	public String getStodisc() {
		return stodisc;
	}
	public void setStodisc(String stodisc) {
		this.stodisc = stodisc;
	}
	public String getIsrepair() {
		return isrepair;
	}
	public void setIsrepair(String isrepair) {
		this.isrepair = isrepair;
	}
	
	
}
