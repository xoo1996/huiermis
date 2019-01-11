package org.radf.apps.commons.entity;

import java.util.Date;

import org.radf.plat.util.entity.EntitySupport;

public class Fee extends EntitySupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer feeyear;//��
	private Integer feemonth;//��
	private String feemonths;//�ַ�����
	private String feegctid;//�ͻ����
	private Double feesales;//���۶�
	private Double feemanage;//������
	
	private Double feerent;//����
	private Double zbfee;//�ܲ�����
	private Double feetax;//˰��
	private Double feepension;//���Ͻ�
	private Double feehousingfund;//������
	private Double feeassets;//�̶��ʲ�
	private Double feepay;//����
	private Double feebuilt;//װ�޷�
	private Double feeopen;//�����
	private Double feothersa;//������������
	private Double feead;//����
	private Double feephone;//�绰��
	private Double feewater;//ˮ���
	private Double feetrip;//���÷�
	private Double feepostage;//�ʷ�
	private Double feeoffice;//�칫��
	private Double feesocial;//���ط�
	private Double feemedical;//ת���
	private Double feeaccount;//��ƹ���
	private Double feeothersb;//���ڵ���������
	private Double feedevice;//�豸����
	private Double feeinvoice;//��Ʊ��
	private Double feetrans;//ת�÷�
	private Double feeprocess;//������
	private Double feepromotion;//����
	private Double feeback;//�˻���
	private Double feewage;//��˰����
	private Double feebenefit;//�Ż�
	private Double feeothercharges;//��������(�ɿ�)
	private Double fee1othercharges;//��������(���ɿ�)
	private Double feecontrol;//�ɿط���
	
	
	private String feeop;//����Ա���
	private Date feeopdata;//����ʱ��

    private String bsc012;//����Ա����
    private String gctarea;//����
    private String feegctname;//�ͻ�����
    
    private String feetype;//֧������
    
    
    
    //����֧���ֶ�(23��)
    private Double bfeerent;//����
    private Double bfeetax;//˰��
    private Double bfeepension;//�籣��(���Ͻ�)
    private Double bfeehousingfund;//������
	
	private Double bfeeassets;//�̶��ʲ�
	private Double bfeepay;//����
	private Double bfeebuilt;//װ�޷�
	
	private Double bfeeopen;//�����
	private Double bfeothersa;//13%������������
	private Double bfeead;//����
	
	private Double bfeephone;//�绰��
	private Double bfeewater;//ˮ���
	private Double bfeetrip;//���÷�
	
	private Double bfeepostage;//�ʷ�
	private Double bfeeoffice;//�칫��
	private Double bfeesocial;//���ط�
	

	private Double bfeemedical;//�������(ת���)
	private Double bfeeaccount;//��ƹ��� 
	private Double bfeeothersb;//13%���ڵ���������
	
	private Double bfeedevice;//�豸̯��
	private Double bfeeinvoice;//��Ʊ��
	private Double bfeetrans;//ת�÷�
	
	private Double bfeeprocess;//������
	private String bfeent;//��ע
	private Double bfeewage;//��˰����
	
	private Double bzbfee;//�ܲ�����
	
	private Double bfeebenefit;//�Ż�
	private Double bfeeothercharges;//��������
	private Double bfee1othercharges;//��������(�ɿ�)
	
	
	//��˰֧���ֶ�(23��)
    private Double ffeerent;//����
    private Double ffeetax;//˰��
    private Double ffeepension;//�籣��(���Ͻ�)
	private Double ffeehousingfund;//������
	
	private Double ffeeassets;//�̶��ʲ�
	private Double ffeepay;//����
	private Double ffeebuilt;//װ�޷�
	
	private Double ffeeopen;//�����
	private Double ffeothersa;//13%������������
	private Double ffeead;//����
	
	private Double ffeephone;//�绰��
	private Double ffeewater;//ˮ���
	private Double ffeetrip;//���÷�
	
	private Double ffeepostage;//�ʷ�
	private Double ffeeoffice;//�칫��
	private Double ffeesocial;//���ط�
	

	private Double ffeemedical;//�������(ת���)
	private Double ffeeaccount;//��ƹ��� 
	private Double ffeeothersb;//13%���ڵ���������
	
	private Double ffeedevice;//�豸̯��
	private Double ffeeinvoice;//��Ʊ��
	private Double ffeetrans;//ת�÷�
	
	private Double ffeeprocess;//������
	private String ffeent;//��ע
	private Double ffeewage;//��˰����
	
	private Double ffeebenefit;//�Ż�
	private Double ffeeothercharges;//��������
	private Double ffee1othercharges;//��������(���ɿ�)
	private Double fzbfee;//�ܲ�����
	
	//����Ԥ��֧���ֶ�(23��)
    private Double nfeerent;//����
    private Double nfeetax;//˰��
    private Double nfeepension;//�籣��(���Ͻ�)
	private Double nfeehousingfund;//������
	
	private Double nfeeassets;//�̶��ʲ�
	private Double nfeepay;//����
	private Double nfeebuilt;//װ�޷�
	
	private Double nfeeopen;//�����
	private Double nfeothersa;//13%������������
	private Double nfeead;//����
	
	private Double nfeephone;//�绰��
	private Double nfeewater;//ˮ���
	private Double nfeetrip;//���÷�
	
	private Double nfeepostage;//�ʷ�
	private Double nfeeoffice;//�칫��
	private Double nfeesocial;//���ط�
	

	private Double nfeemedical;//�������(ת���)
	private Double nfeeaccount;//��ƹ��� 
	private Double nfeeothersb;//13%���ڵ���������
	
	private Double nfeedevice;//�豸̯��
	private Double nfeeinvoice;//��Ʊ��
	private Double nfeetrans;//ת�÷�
	private Double nzbfee;//�ܲ�����
	
	private Double nfeeprocess;//������
	private String nfeent;//��ע
	private Double nfeewage;//��˰����
	private Double nfeebenefit;//�Ż�
	private Double nfeeothercharges;//��������
	private Double nfee1othercharges;//��������(�ɿ�)


	private String amotype;
	private Date operDate;
	private Double amomoney;
	private Integer feelong;
	
	private Date feestart;
	private Date feeend;
	private Double money;
	private String note;
	private Integer feeamoid;
	
	//������֧���ܱ�
	//����
	private Double rfeehuier;//�ݶ���Ʒ
	private Double rfeeweiting;//Ψ����Ʒ
	private Double rfeeqie;//���������/ȫ���ά�ĳ��Ҳ�Ʒ
	private Double rfeeelse;//�������Ҳ�Ʒ
	private Double rfeerepair;//������
	private Double rfeeearmould;//��Ĥ
	private Double rfeeparts;//��������
	private Double rfeebenefitm;//�Żݣ������ۣ�
	
	//������
	private Double rfeesales;//�������۶�
	private Double rfeebusiness;//����
	private Double rfeefinance;//����
	private Double rfeecollection;//�������
	//�����ܲ�
	private Double rfeewagein;//���ʻ���
	private Double rfeeelin;//��������
	private Double rfeebenefitin;//���Ż�
	private Double rfeetotalin;//�ϼƻ���
	
	//�ݶ�֧��
	private Double rfeepresurplus;//���½���
	private Double rfeebusin;//�������
	private Double rfeerealpay;//ʵ��֧��
	private Double rfeesurplus;//���½���
	private Double rfeecosales;//���º��������۶�
	private Double rfeeaccucosales;//�����ۼƺ��������۶�
	
	private String rfeent;
	
	private Integer isgrant;//�Ƿ���Ȩ�޸ı�־��0δ��1��
	
	
	

	public Integer getIsgrant() {
		return isgrant;
	}

	public void setIsgrant(Integer isgrant) {
		this.isgrant = isgrant;
	}

	public Integer getFeeamoid() {
		return feeamoid;
	}

	public Double getRfeehuier() {
		return rfeehuier;
	}

	
	public Double getFee1othercharges() {
		return fee1othercharges;
	}

	public void setFee1othercharges(Double fee1othercharges) {
		this.fee1othercharges = fee1othercharges;
	}

	public Double getBfee1othercharges() {
		return bfee1othercharges;
	}

	public void setBfee1othercharges(Double bfee1othercharges) {
		this.bfee1othercharges = bfee1othercharges;
	}

	public Double getFfee1othercharges() {
		return ffee1othercharges;
	}

	public void setFfee1othercharges(Double ffee1othercharges) {
		this.ffee1othercharges = ffee1othercharges;
	}

	public Double getNfee1othercharges() {
		return nfee1othercharges;
	}

	public Double getRfeebenefitm() {
		return rfeebenefitm;
	}

	public void setRfeebenefitm(Double rfeebenefitm) {
		this.rfeebenefitm = rfeebenefitm;
	}

	public Double getRfeetotalin() {
		return rfeetotalin;
	}

	public void setRfeetotalin(Double rfeetotalin) {
		this.rfeetotalin = rfeetotalin;
	}

	public void setNfee1othercharges(Double nfee1othercharges) {
		this.nfee1othercharges = nfee1othercharges;
	}

	public Double getFeehousingfund() {
		return feehousingfund;
	}

	public void setRfeehuier(Double rfeehuier) {
		this.rfeehuier = rfeehuier;
	}

	public Double getRfeeweiting() {
		return rfeeweiting;
	}

	public void setRfeeweiting(Double rfeeweiting) {
		this.rfeeweiting = rfeeweiting;
	}

	public Double getRfeeqie() {
		return rfeeqie;
	}

	public void setRfeeqie(Double rfeeqie) {
		this.rfeeqie = rfeeqie;
	}



	public Double getFeehousingfound() {
		return feehousingfund;
	}

	public void setFeehousingfund(Double feehousingfund) {
		this.feehousingfund = feehousingfund;
	}

	public Double getFeeothercharges() {
		return feeothercharges;
	}

	public void setFeeothercharges(Double feeothercharges) {
		this.feeothercharges = feeothercharges;
	}

	public Double getBfeehousingfund() {
		return bfeehousingfund;
	}

	public void setBfeehousingfund(Double bfeehousingfund) {
		this.bfeehousingfund = bfeehousingfund;
	}

	public Double getBfeeothercharges() {
		return bfeeothercharges;
	}

	public void setBfeeothercharges(Double bfeeothercharges) {
		this.bfeeothercharges = bfeeothercharges;
	}

	public Double getFfeehousingfund() {
		return ffeehousingfund;
	}

	public void setFfeehousingfund(Double ffeehousingfund) {
		this.ffeehousingfund = ffeehousingfund;
	}

	public Double getFfeeothercharges() {
		return ffeeothercharges;
	}

	public void setFfeeothercharges(Double ffeeothercharges) {
		this.ffeeothercharges = ffeeothercharges;
	}

	

	public Double getNfeehousingfund() {
		return nfeehousingfund;
	}

	public void setNfeehousingfund(Double nfeehousingfund) {
		this.nfeehousingfund = nfeehousingfund;
	}

	public Double getNfeeothercharges() {
		return nfeeothercharges;
	}

	public void setNfeeothercharges(Double nfeeothercharges) {
		this.nfeeothercharges = nfeeothercharges;
	}

	public Double getFeebenefit() {
		return feebenefit;
	}

	public void setFeebenefit(Double feebenefit) {
		this.feebenefit = feebenefit;
	}

	public Double getRfeeelse() {
		return rfeeelse;
	}

	public void setRfeeelse(Double rfeeelse) {
		this.rfeeelse = rfeeelse;
	}

	public Double getRfeerepair() {
		return rfeerepair;
	}

	public void setRfeerepair(Double rfeerepair) {
		this.rfeerepair = rfeerepair;
	}

	public Double getRfeeearmould() {
		return rfeeearmould;
	}

	public void setRfeeearmould(Double rfeeearmould) {
		this.rfeeearmould = rfeeearmould;
	}

	public Double getRfeeparts() {
		return rfeeparts;
	}

	public void setRfeeparts(Double rfeeparts) {
		this.rfeeparts = rfeeparts;
	}

	public Double getRfeesales() {
		return rfeesales;
	}

	public void setRfeesales(Double rfeesales) {
		this.rfeesales = rfeesales;
	}

	public Double getRfeebusiness() {
		return rfeebusiness;
	}

	public void setRfeebusiness(Double rfeebusiness) {
		this.rfeebusiness = rfeebusiness;
	}

	public Double getRfeefinance() {
		return rfeefinance;
	}

	public void setRfeefinance(Double rfeefinance) {
		this.rfeefinance = rfeefinance;
	}

	public Double getRfeecollection() {
		return rfeecollection;
	}

	public Double getRfeeelin() {
		return rfeeelin;
	}

	public void setRfeeelin(Double rfeeelin) {
		this.rfeeelin = rfeeelin;
	}

	public Double getRfeebusin() {
		return rfeebusin;
	}

	public void setRfeebusin(Double rfeebusin) {
		this.rfeebusin = rfeebusin;
	}

	public void setRfeecollection(Double rfeecollection) {
		this.rfeecollection = rfeecollection;
	}

	public Double getRfeewagein() {
		return rfeewagein;
	}

	public void setRfeewagein(Double rfeewagein) {
		this.rfeewagein = rfeewagein;
	}


	public Double getRfeebenefitin() {
		return rfeebenefitin;
	}

	public void setRfeebenefitin(Double rfeebenefitin) {
		this.rfeebenefitin = rfeebenefitin;
	}

	public Double getRfeepresurplus() {
		return rfeepresurplus;
	}

	public void setRfeepresurplus(Double rfeepresurplus) {
		this.rfeepresurplus = rfeepresurplus;
	}

	

	public Double getRfeerealpay() {
		return rfeerealpay;
	}

	public void setRfeerealpay(Double rfeerealpay) {
		this.rfeerealpay = rfeerealpay;
	}

	public Double getRfeesurplus() {
		return rfeesurplus;
	}

	public void setRfeesurplus(Double rfeesurplus) {
		this.rfeesurplus = rfeesurplus;
	}

	public Double getRfeecosales() {
		return rfeecosales;
	}

	public void setRfeecosales(Double rfeecosales) {
		this.rfeecosales = rfeecosales;
	}

	public Double getRfeeaccucosales() {
		return rfeeaccucosales;
	}

	public void setRfeeaccucosales(Double rfeeaccucosales) {
		this.rfeeaccucosales = rfeeaccucosales;
	}

	public String getRfeent() {
		return rfeent;
	}

	public void setRfeent(String rfeent) {
		this.rfeent = rfeent;
	}

	public void setFeeamoid(Integer feeamoid) {
		this.feeamoid = feeamoid;
	}

	public Date getFeestart() {
		return feestart;
	}

	public void setFeestart(Date feestart) {
		this.feestart = feestart;
	}

	public Date getFeeend() {
		return feeend;
	}

	

	public Double getBfeebenefit() {
		return bfeebenefit;
	}

	public void setBfeebenefit(Double bfeebenefit) {
		this.bfeebenefit = bfeebenefit;
	}


	public Double getFfeebenefit() {
		return ffeebenefit;
	}

	public void setFfeebenefit(Double ffeebenefit) {
		this.ffeebenefit = ffeebenefit;
	}


	public Double getFeewage() {
		return feewage;
	}

	public void setFeewage(Double feewage) {
		this.feewage = feewage;
	}

	public Double getBfeewage() {
		return bfeewage;
	}

	public void setBfeewage(Double bfeewage) {
		this.bfeewage = bfeewage;
	}

	public Double getFfeewage() {
		return ffeewage;
	}

	public void setFfeewage(Double ffeewage) {
		this.ffeewage = ffeewage;
	}

	public Double getNfeewage() {
		return nfeewage;
	}

	public void setNfeewage(Double nfeewage) {
		this.nfeewage = nfeewage;
	}

	public Double getNfeebenefit() {
		return nfeebenefit;
	}

	public void setNfeebenefit(Double nfeebenefit) {
		this.nfeebenefit = nfeebenefit;
	}

	public void setFeeend(Date feeend) {
		this.feeend = feeend;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	

	

	public Double getAmomoney() {
		return amomoney;
	}

	public void setAmomoney(Double amomoney) {
		this.amomoney = amomoney;
	}

	public Integer getFeelong() {
		return feelong;
	}

	public void setFeelong(Integer feelong) {
		this.feelong = feelong;
	}

	public String getAmotype() {
		return amotype;
	}

	public void setAmotype(String amotype) {
		this.amotype = amotype;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	

    
	public String getBsc012() {
		return bsc012;
	}

	public void setBsc012(String bsc012) {
		this.bsc012 = bsc012;
	}

	public Integer getFeeyear() {
		return feeyear;
	}

	public void setFeeyear(Integer feeyear) {
		this.feeyear = feeyear;
	}

	public Integer getFeemonth() {
		return feemonth;
	}

	public void setFeemonth(Integer feemonth) {
		this.feemonth = feemonth;
	}

	public String getFeegctid() {
		return feegctid;
	}

	public void setFeegctid(String feegctid) {
		this.feegctid = feegctid;
	}

	
	public String getFeeop() {
		return feeop;
	}

	public void setFeeop(String feeop) {
		this.feeop = feeop;
	}

	public Date getFeeopdata() {
		return feeopdata;
	}

	public void setFeeopdata(Date feeopdata) {
		this.feeopdata = feeopdata;
	}

	public Double getFeesales() {
		return feesales;
	}

	public void setFeesales(Double feesales) {
		this.feesales = feesales;
	}
	public Double getFeemanage() {
		return feemanage;
	}

	public void setFeemanage(Double feemanage) {
		this.feemanage = feemanage;
	}

	public String getFeegctname() {
		return feegctname;
	}

	public void setFeegctname(String feegctname) {
		this.feegctname = feegctname;
	}

	public String getGctarea() {
		return gctarea;
	}

	public void setGctarea(String gctarea) {
		this.gctarea = gctarea;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public Double getBfeerent() {
		return bfeerent;
	}

	public void setBfeerent(Double bfeerent) {
		this.bfeerent = bfeerent;
	}

	public Double getBfeetax() {
		return bfeetax;
	}

	public void setBfeetax(Double bfeetax) {
		this.bfeetax = bfeetax;
	}

	public Double getBfeepension() {
		return bfeepension;
	}

	public void setBfeepension(Double bfeepension) {
		this.bfeepension = bfeepension;
	}

	public Double getBfeeassets() {
		return bfeeassets;
	}

	public void setBfeeassets(Double bfeeassets) {
		this.bfeeassets = bfeeassets;
	}

	public Double getBfeepay() {
		return bfeepay;
	}

	public void setBfeepay(Double bfeepay) {
		this.bfeepay = bfeepay;
	}

	public Double getFeecontrol() {
		return feecontrol;
	}

	public void setFeecontrol(Double feecontrol) {
		this.feecontrol = feecontrol;
	}

	public Double getBfeebuilt() {
		return bfeebuilt;
	}

	public void setBfeebuilt(Double bfeebuilt) {
		this.bfeebuilt = bfeebuilt;
	}

	public Double getBfeeopen() {
		return bfeeopen;
	}

	public void setBfeeopen(Double bfeeopen) {
		this.bfeeopen = bfeeopen;
	}

	public Double getBfeothersa() {
		return bfeothersa;
	}

	public void setBfeothersa(Double bfeothersa) {
		this.bfeothersa = bfeothersa;
	}

	public Double getBfeead() {
		return bfeead;
	}

	public void setBfeead(Double bfeead) {
		this.bfeead = bfeead;
	}

	public Double getBfeephone() {
		return bfeephone;
	}

	public void setBfeephone(Double bfeephone) {
		this.bfeephone = bfeephone;
	}

	public Double getBfeewater() {
		return bfeewater;
	}

	public void setBfeewater(Double bfeewater) {
		this.bfeewater = bfeewater;
	}

	public Double getBfeetrip() {
		return bfeetrip;
	}

	public void setBfeetrip(Double bfeetrip) {
		this.bfeetrip = bfeetrip;
	}

	public Double getBfeepostage() {
		return bfeepostage;
	}

	public void setBfeepostage(Double bfeepostage) {
		this.bfeepostage = bfeepostage;
	}

	public Double getBfeeoffice() {
		return bfeeoffice;
	}

	public void setBfeeoffice(Double bfeeoffice) {
		this.bfeeoffice = bfeeoffice;
	}

	public Double getBfeesocial() {
		return bfeesocial;
	}

	public void setBfeesocial(Double bfeesocial) {
		this.bfeesocial = bfeesocial;
	}

	public Double getBfeemedical() {
		return bfeemedical;
	}

	public void setBfeemedical(Double bfeemedical) {
		this.bfeemedical = bfeemedical;
	}

	public Double getBfeeaccount() {
		return bfeeaccount;
	}

	public void setBfeeaccount(Double bfeeaccount) {
		this.bfeeaccount = bfeeaccount;
	}

	public Double getBfeeothersb() {
		return bfeeothersb;
	}

	public void setBfeeothersb(Double bfeeothersb) {
		this.bfeeothersb = bfeeothersb;
	}

	public Double getBfeedevice() {
		return bfeedevice;
	}

	public void setBfeedevice(Double bfeedevice) {
		this.bfeedevice = bfeedevice;
	}

	public Double getBfeeinvoice() {
		return bfeeinvoice;
	}

	public void setBfeeinvoice(Double bfeeinvoice) {
		this.bfeeinvoice = bfeeinvoice;
	}

	public Double getBfeetrans() {
		return bfeetrans;
	}

	public void setBfeetrans(Double bfeetrans) {
		this.bfeetrans = bfeetrans;
	}

	public Double getBfeeprocess() {
		return bfeeprocess;
	}

	public void setBfeeprocess(Double bfeeprocess) {
		this.bfeeprocess = bfeeprocess;
	}

	public String getBfeent() {
		return bfeent;
	}

	public void setBfeent(String bfeent) {
		this.bfeent = bfeent;
	}

	public Double getFfeerent() {
		return ffeerent;
	}

	public void setFfeerent(Double ffeerent) {
		this.ffeerent = ffeerent;
	}

	public Double getFfeetax() {
		return ffeetax;
	}

	public void setFfeetax(Double ffeetax) {
		this.ffeetax = ffeetax;
	}

	public Double getFfeepension() {
		return ffeepension;
	}

	public void setFfeepension(Double ffeepension) {
		this.ffeepension = ffeepension;
	}

	public Double getFfeeassets() {
		return ffeeassets;
	}

	public void setFfeeassets(Double ffeeassets) {
		this.ffeeassets = ffeeassets;
	}

	public Double getFfeepay() {
		return ffeepay;
	}

	public void setFfeepay(Double ffeepay) {
		this.ffeepay = ffeepay;
	}

	public Double getFfeebuilt() {
		return ffeebuilt;
	}

	public void setFfeebuilt(Double ffeebuilt) {
		this.ffeebuilt = ffeebuilt;
	}

	public Double getFfeeopen() {
		return ffeeopen;
	}

	public void setFfeeopen(Double ffeeopen) {
		this.ffeeopen = ffeeopen;
	}

	public Double getFfeothersa() {
		return ffeothersa;
	}

	public void setFfeothersa(Double ffeothersa) {
		this.ffeothersa = ffeothersa;
	}

	public Double getFfeead() {
		return ffeead;
	}

	public void setFfeead(Double ffeead) {
		this.ffeead = ffeead;
	}

	public Double getFfeephone() {
		return ffeephone;
	}

	public void setFfeephone(Double ffeephone) {
		this.ffeephone = ffeephone;
	}

	public Double getFfeewater() {
		return ffeewater;
	}

	public void setFfeewater(Double ffeewater) {
		this.ffeewater = ffeewater;
	}

	public Double getFfeetrip() {
		return ffeetrip;
	}

	public void setFfeetrip(Double ffeetrip) {
		this.ffeetrip = ffeetrip;
	}

	public Double getFfeepostage() {
		return ffeepostage;
	}

	public void setFfeepostage(Double ffeepostage) {
		this.ffeepostage = ffeepostage;
	}

	public Double getFfeeoffice() {
		return ffeeoffice;
	}

	public void setFfeeoffice(Double ffeeoffice) {
		this.ffeeoffice = ffeeoffice;
	}

	public Double getFfeesocial() {
		return ffeesocial;
	}

	public void setFfeesocial(Double ffeesocial) {
		this.ffeesocial = ffeesocial;
	}

	public Double getFfeemedical() {
		return ffeemedical;
	}

	public void setFfeemedical(Double ffeemedical) {
		this.ffeemedical = ffeemedical;
	}

	public Double getFfeeaccount() {
		return ffeeaccount;
	}

	public void setFfeeaccount(Double ffeeaccount) {
		this.ffeeaccount = ffeeaccount;
	}

	public Double getFfeeothersb() {
		return ffeeothersb;
	}

	public void setFfeeothersb(Double ffeeothersb) {
		this.ffeeothersb = ffeeothersb;
	}

	public Double getFfeedevice() {
		return ffeedevice;
	}

	public void setFfeedevice(Double ffeedevice) {
		this.ffeedevice = ffeedevice;
	}

	public Double getFfeeinvoice() {
		return ffeeinvoice;
	}

	public void setFfeeinvoice(Double ffeeinvoice) {
		this.ffeeinvoice = ffeeinvoice;
	}

	public Double getFfeetrans() {
		return ffeetrans;
	}

	public void setFfeetrans(Double ffeetrans) {
		this.ffeetrans = ffeetrans;
	}

	public Double getFfeeprocess() {
		return ffeeprocess;
	}

	public void setFfeeprocess(Double ffeeprocess) {
		this.ffeeprocess = ffeeprocess;
	}

	public String getFfeent() {
		return ffeent;
	}

	public void setFfeent(String ffeent) {
		this.ffeent = ffeent;
	}

	public Double getNfeerent() {
		return nfeerent;
	}

	public void setNfeerent(Double nfeerent) {
		this.nfeerent = nfeerent;
	}

	public Double getNfeetax() {
		return nfeetax;
	}

	public void setNfeetax(Double nfeetax) {
		this.nfeetax = nfeetax;
	}

	public Double getNfeepension() {
		return nfeepension;
	}

	public void setNfeepension(Double nfeepension) {
		this.nfeepension = nfeepension;
	}

	public Double getNfeeassets() {
		return nfeeassets;
	}

	public void setNfeeassets(Double nfeeassets) {
		this.nfeeassets = nfeeassets;
	}

	public Double getNfeepay() {
		return nfeepay;
	}

	public void setNfeepay(Double nfeepay) {
		this.nfeepay = nfeepay;
	}

	public Double getNfeebuilt() {
		return nfeebuilt;
	}

	public void setNfeebuilt(Double nfeebuilt) {
		this.nfeebuilt = nfeebuilt;
	}

	public Double getNfeeopen() {
		return nfeeopen;
	}

	public void setNfeeopen(Double nfeeopen) {
		this.nfeeopen = nfeeopen;
	}

	public Double getNfeothersa() {
		return nfeothersa;
	}

	public void setNfeothersa(Double nfeothersa) {
		this.nfeothersa = nfeothersa;
	}

	public Double getNfeead() {
		return nfeead;
	}

	public void setNfeead(Double nfeead) {
		this.nfeead = nfeead;
	}

	public Double getNfeephone() {
		return nfeephone;
	}

	public void setNfeephone(Double nfeephone) {
		this.nfeephone = nfeephone;
	}

	public Double getNfeewater() {
		return nfeewater;
	}

	public void setNfeewater(Double nfeewater) {
		this.nfeewater = nfeewater;
	}

	public Double getNfeetrip() {
		return nfeetrip;
	}

	public void setNfeetrip(Double nfeetrip) {
		this.nfeetrip = nfeetrip;
	}

	public Double getNfeepostage() {
		return nfeepostage;
	}

	public void setNfeepostage(Double nfeepostage) {
		this.nfeepostage = nfeepostage;
	}

	public Double getNfeeoffice() {
		return nfeeoffice;
	}

	public void setNfeeoffice(Double nfeeoffice) {
		this.nfeeoffice = nfeeoffice;
	}

	public Double getNfeesocial() {
		return nfeesocial;
	}

	public void setNfeesocial(Double nfeesocial) {
		this.nfeesocial = nfeesocial;
	}

	public Double getNfeemedical() {
		return nfeemedical;
	}

	public void setNfeemedical(Double nfeemedical) {
		this.nfeemedical = nfeemedical;
	}

	public Double getNfeeaccount() {
		return nfeeaccount;
	}

	public void setNfeeaccount(Double nfeeaccount) {
		this.nfeeaccount = nfeeaccount;
	}

	public Double getNfeeothersb() {
		return nfeeothersb;
	}

	public void setNfeeothersb(Double nfeeothersb) {
		this.nfeeothersb = nfeeothersb;
	}

	public Double getNfeedevice() {
		return nfeedevice;
	}

	public void setNfeedevice(Double nfeedevice) {
		this.nfeedevice = nfeedevice;
	}

	public Double getNfeeinvoice() {
		return nfeeinvoice;
	}

	public void setNfeeinvoice(Double nfeeinvoice) {
		this.nfeeinvoice = nfeeinvoice;
	}

	public Double getNfeetrans() {
		return nfeetrans;
	}

	public void setNfeetrans(Double nfeetrans) {
		this.nfeetrans = nfeetrans;
	}

	public Double getNfeeprocess() {
		return nfeeprocess;
	}

	public void setNfeeprocess(Double nfeeprocess) {
		this.nfeeprocess = nfeeprocess;
	}

	public String getNfeent() {
		return nfeent;
	}

	public void setNfeent(String nfeent) {
		this.nfeent = nfeent;
	}

	public Double getFeerent() {
		return feerent;
	}

	public void setFeerent(Double feerent) {
		this.feerent = feerent;
	}

	public Double getFeetax() {
		return feetax;
	}

	public void setFeetax(Double feetax) {
		this.feetax = feetax;
	}

	public Double getFeepension() {
		return feepension;
	}

	public void setFeepension(Double feepension) {
		this.feepension = feepension;
	}

	public Double getFeeassets() {
		return feeassets;
	}

	public void setFeeassets(Double feeassets) {
		this.feeassets = feeassets;
	}

	public Double getFeepay() {
		return feepay;
	}

	public void setFeepay(Double feepay) {
		this.feepay = feepay;
	}

	public Double getFeebuilt() {
		return feebuilt;
	}

	public void setFeebuilt(Double feebuilt) {
		this.feebuilt = feebuilt;
	}

	public Double getFeeopen() {
		return feeopen;
	}

	public void setFeeopen(Double feeopen) {
		this.feeopen = feeopen;
	}

	public Double getFeothersa() {
		return feothersa;
	}

	public void setFeothersa(Double feothersa) {
		this.feothersa = feothersa;
	}

	public Double getFeead() {
		return feead;
	}

	public void setFeead(Double feead) {
		this.feead = feead;
	}

	public Double getFeephone() {
		return feephone;
	}

	public void setFeephone(Double feephone) {
		this.feephone = feephone;
	}

	public Double getFeewater() {
		return feewater;
	}

	public void setFeewater(Double feewater) {
		this.feewater = feewater;
	}

	public Double getFeetrip() {
		return feetrip;
	}

	public void setFeetrip(Double feetrip) {
		this.feetrip = feetrip;
	}

	public Double getFeepostage() {
		return feepostage;
	}

	public void setFeepostage(Double feepostage) {
		this.feepostage = feepostage;
	}

	public Double getFeeoffice() {
		return feeoffice;
	}

	public void setFeeoffice(Double feeoffice) {
		this.feeoffice = feeoffice;
	}

	public Double getFeesocial() {
		return feesocial;
	}

	public void setFeesocial(Double feesocial) {
		this.feesocial = feesocial;
	}

	public Double getFeemedical() {
		return feemedical;
	}

	public void setFeemedical(Double feemedical) {
		this.feemedical = feemedical;
	}

	public Double getFeeaccount() {
		return feeaccount;
	}

	public void setFeeaccount(Double feeaccount) {
		this.feeaccount = feeaccount;
	}

	public Double getFeeothersb() {
		return feeothersb;
	}

	public void setFeeothersb(Double feeothersb) {
		this.feeothersb = feeothersb;
	}

	public Double getFeedevice() {
		return feedevice;
	}

	public void setFeedevice(Double feedevice) {
		this.feedevice = feedevice;
	}

	public Double getFeeinvoice() {
		return feeinvoice;
	}

	public void setFeeinvoice(Double feeinvoice) {
		this.feeinvoice = feeinvoice;
	}

	public Double getFeetrans() {
		return feetrans;
	}

	public void setFeetrans(Double feetrans) {
		this.feetrans = feetrans;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFeemonths() {
		return feemonths;
	}

	public void setFeemonths(String feemonths) {
		this.feemonths = feemonths;
	}

	public Double getFeeprocess() {
		return feeprocess;
	}

	public void setFeeprocess(Double feeprocess) {
		this.feeprocess = feeprocess;
	}

	public Double getFeepromotion() {
		return feepromotion;
	}

	public void setFeepromotion(Double feepromotion) {
		this.feepromotion = feepromotion;
	}

	public Double getFeeback() {
		return feeback;
	}

	public void setFeeback(Double feeback) {
		this.feeback = feeback;
	}

	public Double getBzbfee() {
		return bzbfee;
	}

	public void setBzbfee(Double bzbfee) {
		this.bzbfee = bzbfee;
	}

	public Double getFzbfee() {
		return fzbfee;
	}

	public void setFzbfee(Double fzbfee) {
		this.fzbfee = fzbfee;
	}
	public Double getNzbfee() {
		return nzbfee;
	}

	public void setNzbfee(Double nzbfee) {
		this.nzbfee = nzbfee;
	}

	public Double getZbfee() {
		return zbfee;
	}

	public void setZbfee(Double zbfee) {
		this.zbfee = zbfee;
	}
	
	

}