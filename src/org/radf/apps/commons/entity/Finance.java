package org.radf.apps.commons.entity;

import java.util.Date;
import org.radf.plat.util.entity.EntitySupport;

//��Ʊ��Ϣ��
public class Finance extends EntitySupport {
	
	private String finno; //��Ʊ���
	private String fintype;//��Ʊ����
	private String typeno; //���ͱ��
	private String notype; // �������
	private String gctnm; //������
	private String gctid; //���̱��
	private String ictid;
	private String ictnm;
	private String pdtnm; //��Ʒ����
	private String band;
	private String pdtid; //��Ʒ���
	private String finnt; //��Ʊ��ע
	private Date finsubdt; //�ύ��������
	private Date finpassdt; //ͨ����������
	private String tmksid; //���ƻ�������
	private String invoicecode; //��Ʊ����
	private String invoiceno; //��Ʊ���
	private String retailname; //���ۿ�Ʊ̧ͷ
	private String retailtaxno; //���ۿ�Ʊ˰��
	private String comaddr; //��˾��ַ
	private String depositbank; //������
	private String depositid; //�����˺�
	private String specialtel; //ר�ÿ�Ʊ�绰
	
	private String bindno;//������
	private String rate;//����
	
	private String sellprc;//���ۼ۸�
		
	private String pdtnum; //����
	private String finrate;//��Ʊ����
	private Date chgdt;//�շ�����
	private Date redt;//�˻�����
	private String finprccount;//�ܶ�
	private String finprc;

	private String pdtprc;
	private String fintel;
	
	private Date start;//��ʼʱ��
	private Date end;//����ʱ��
	
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