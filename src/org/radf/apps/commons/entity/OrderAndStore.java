package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;
import java.util.Date;

public class OrderAndStore extends EntitySupport {
	//������Ϣ
	private String folno; // ��������
	private String fdtfno; // ��������
	private String folctid;// �ͻ�����
	private String gctnm; // �ͻ�����
	private String fdtpid;//��Ʒ���
	private Date foldt; //��������
	private String folsta; // ����״̬
	private String folischg;// �Ƿ����շ�
	private Date folchgdt; //�շ�����
	private Date fdtodt; //�˻�����
	private Date fdtrecheaddt; //�˻����ܲ�����
	private Date fdtexadt; //�ܲ�����˻�����
	
	//�����Ϣ
	private String stoid;// �����
	private String stogrcliid;// ����ͻ�����
	private String stoproid;// ��Ʒ����
	private String stoprotype;// ��Ʒ����
	private String stoproname;// ��Ʒ����
	private Integer stoamount;// ����(���/����)
	private String stoamountun;// ������λ
	private Double stoproprice;// ����
	private Date stodate;// ���������
	private Integer stoactype;// ������1�����-1�����⣩
	private String storemark;// ��ע
	private String stooprcd;// ����Ա����
	private String stodisc;// �Ƿ��ѱ���
	private String isrepair;// �Ƿ�ά�޶���
	
	
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
