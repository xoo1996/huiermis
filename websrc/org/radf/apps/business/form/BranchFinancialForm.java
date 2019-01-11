package org.radf.apps.business.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class BranchFinancialForm extends ActionForm{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//7
	private String gctarea;//����
	private Integer iyear;//��
	private Integer imonth;//��
	private String smonth;//�ַ�����
    private String gctname;//�ͻ�����
	private String gctid;//�ͻ�(�ŵ�)���
	private String operator;//����Ա���
    private String bsc012;//����Ա����
	private Date operatedate;//����ʱ��
	
	//��ӡʱ��ѡ��
	private Integer startyear;
	private Integer startmonth;
	private Integer endyear;
	private Integer endmonth;
	
	//���¿�Ʊ��� 3
	private Double totalreturns;//�����۶�
	private Double invoiceamount;//��Ʊ��
	private Double invoicerate;//��Ʊ����=��Ʊ��/�����۶�
	private Double accuyearinvoiceamount;//�ۼ�12���¿�Ʊ��
	
	//���»����ʽ���� 7
	private Double premoneyfunds;//�����ʽ�=���µ� ���»����ʽ�
	private Double purchasecosts;//���½�����
	private Double actualpurchasecosts;//ʵ������
	private Double monthcosts;//���·���
	private Double monthtax;//����˰��
	private Double wagesreturn;//���ʴ��
	private Double elsecosts;//������������
	private Double moneyfunds;//���»����ʽ�=�����ʽ�+��Ʊ��-ʵ����-����-����-����
	
	//Ӧ����Ӧ�տ���� 7
	private Double preaccountpayable;//����Ӧ���˿�=ȡ���� Ӧ������
	private Double accountpayable;//����Ӧ���˿�=����Ӧ���˿�+���½�����-ʵ������
	private Double inventory;//���
	private Double paidincapital;//ʵ���ʱ�
	private Double elseaccountpayable;//����Ӧ���˿�
	private Double wagespayable;//Ӧ������
	private Double elsereceivables;//����Ӧ�տ���
	private Double accountrecievable;//����Ӧ���˿�

	//�������17   accu=accumulated undis=undistributed
	private Double preyearundisprofits;//�����ۼ�δ��������
	private Double preaccuprofit;//�����ۼ�����=���µ� ��������
	private Double monthprofit;//��������
	private Double yearprofit;//��������=�����ۼ�����+��������
	private Double accuundisprofits;//�ۼ�δ��������=�����ۼ�δ��������+��������
	private Double preaccuinvoiceamount;//�����ۼƿ�Ʊ���۶�=���µ� �ۼƿ�Ʊ���۶�
	private Double accuinvoiceamount;//�ۼƿ�Ʊ���۶�=�����ۼƿ�Ʊ���۶�+��Ʊ��/1.03
	private Double preaccuactualsales;//�����ۼ�ʵ�����۶�=ȡ���� �ۼ�ʵ�����۶�
	private Double accuactualsales;//�ۼ�ʵ�����۶�=�����ۼ����۶�+�����۶�
	private Double accuinvoicerate;//�ۼƿ�Ʊ��=�ۼƿ�Ʊ���۶�*1.03/�ۼ�ʵ�����۶�
	private Double preaccucostofsales;//�����ۼ���Ӫҵ��ɱ�=���� �ۼ���Ӫҵ��ɱ�
	private Double accucostofsales;//�ۼ���Ӫҵ��ɱ�=�����ۼ���Ӫҵ��ɱ�+���½�����
	private Double operatingandmanagementcosts;//Ӫҵ���ü��������
	private Double taxandaccoiatecharge;//��Ӫҵ��˰�𼰸���
	private Double financingcosts;//�������
	private Double profitrate;//������=��������/�ۼƿ�Ʊ���۶�
	private Double tax;//����˰
	private String remark;//��ע
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGctarea() {
		return gctarea;
	}
	public void setGctarea(String gctarea) {
		this.gctarea = gctarea;
	}
	public Double getMonthtax() {
		return monthtax;
	}
	public void setMonthtax(Double monthtax) {
		this.monthtax = monthtax;
	}
	public Integer getIyear() {
		return iyear;
	}
	public void setIyear(Integer iyear) {
		this.iyear = iyear;
	}
	public Integer getImonth() {
		return imonth;
	}
	public void setImonth(Integer imonth) {
		this.imonth = imonth;
	}
	public String getSmonth() {
		return smonth;
	}
	public void setSmonth(String smonth) {
		this.smonth = smonth;
	}
	public Double getAccuyearinvoiceamount() {
		return accuyearinvoiceamount;
	}
	public void setAccuyearinvoiceamount(Double accuyearinvoiceamount) {
		this.accuyearinvoiceamount = accuyearinvoiceamount;
	}
	public String getGctname() {
		return gctname;
	}
	public void setGctname(String gctname) {
		this.gctname = gctname;
	}
	public String getGctid() {
		return gctid;
	}
	public void setGctid(String gctid) {
		this.gctid = gctid;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Double getAccountrecievable() {
		return accountrecievable;
	}
	public void setAccountrecievable(Double accountrecievable) {
		this.accountrecievable = accountrecievable;
	}
	public String getBsc012() {
		return bsc012;
	}
	public void setBsc012(String bsc012) {
		this.bsc012 = bsc012;
	}
	public Date getOperatedate() {
		return operatedate;
	}
	public void setOperatedate(Date operatedate) {
		this.operatedate = operatedate;
	}
	public Double getTotalreturns() {
		return totalreturns;
	}
	public void setTotalreturns(Double totalreturns) {
		this.totalreturns = totalreturns;
	}
	public Double getInvoiceamount() {
		return invoiceamount;
	}
	public void setInvoiceamount(Double invoiceamount) {
		this.invoiceamount = invoiceamount;
	}
	
	public Double getPremoneyfunds() {
		return premoneyfunds;
	}
	public void setPremoneyfunds(Double premoneyfunds) {
		this.premoneyfunds = premoneyfunds;
	}
	public Double getPurchasecosts() {
		return purchasecosts;
	}
	public void setPurchasecosts(Double purchasecosts) {
		this.purchasecosts = purchasecosts;
	}
	public Double getActualpurchasecosts() {
		return actualpurchasecosts;
	}
	public void setActualpurchasecosts(Double actualpurchasecosts) {
		this.actualpurchasecosts = actualpurchasecosts;
	}
	public Double getMonthcosts() {
		return monthcosts;
	}
	public void setMonthcosts(Double monthcosts) {
		this.monthcosts = monthcosts;
	}
	public Double getWagesreturn() {
		return wagesreturn;
	}
	public void setWagesreturn(Double wagesreturn) {
		this.wagesreturn = wagesreturn;
	}
	public Double getElsecosts() {
		return elsecosts;
	}
	public void setElsecosts(Double elsecosts) {
		this.elsecosts = elsecosts;
	}
	public Double getMoneyfunds() {
		return moneyfunds;
	}
	public void setMoneyfunds(Double moneyfunds) {
		this.moneyfunds = moneyfunds;
	}
	public Double getPreaccountpayable() {
		return preaccountpayable;
	}
	public void setPreaccountpayable(Double preaccountpayable) {
		this.preaccountpayable = preaccountpayable;
	}
	public Double getAccountpayable() {
		return accountpayable;
	}
	public void setAccountpayable(Double accountpayable) {
		this.accountpayable = accountpayable;
	}
	public Double getInventory() {
		return inventory;
	}
	public void setInventory(Double inventory) {
		this.inventory = inventory;
	}
	public Double getPaidincapital() {
		return paidincapital;
	}
	public void setPaidincapital(Double paidincapital) {
		this.paidincapital = paidincapital;
	}
	public Double getElseaccountpayable() {
		return elseaccountpayable;
	}
	public void setElseaccountpayable(Double elseaccountpayable) {
		this.elseaccountpayable = elseaccountpayable;
	}
	public Double getWagespayable() {
		return wagespayable;
	}
	public void setWagespayable(Double wagespayable) {
		this.wagespayable = wagespayable;
	}
	public Double getElsereceivables() {
		return elsereceivables;
	}
	public void setElsereceivables(Double elsereceivables) {
		this.elsereceivables = elsereceivables;
	}
	public Double getPreyearundisprofits() {
		return preyearundisprofits;
	}
	public void setPreyearundisprofits(Double preyearundisprofits) {
		this.preyearundisprofits = preyearundisprofits;
	}
	public Double getPreaccuprofit() {
		return preaccuprofit;
	}
	public void setPreaccuprofit(Double preaccuprofit) {
		this.preaccuprofit = preaccuprofit;
	}
	public Double getMonthprofit() {
		return monthprofit;
	}
	public void setMonthprofit(Double monthprofit) {
		this.monthprofit = monthprofit;
	}
	public Double getYearprofit() {
		return yearprofit;
	}
	public void setYearprofit(Double yearprofit) {
		this.yearprofit = yearprofit;
	}
	public Double getAccuundisprofits() {
		return accuundisprofits;
	}
	public void setAccuundisprofits(Double accuundisprofits) {
		this.accuundisprofits = accuundisprofits;
	}
	public Double getPreaccuinvoiceamount() {
		return preaccuinvoiceamount;
	}
	public void setPreaccuinvoiceamount(Double preaccuinvoiceamount) {
		this.preaccuinvoiceamount = preaccuinvoiceamount;
	}
	public Double getAccuinvoiceamount() {
		return accuinvoiceamount;
	}
	public void setAccuinvoiceamount(Double accuinvoiceamount) {
		this.accuinvoiceamount = accuinvoiceamount;
	}
	public Double getPreaccuactualsales() {
		return preaccuactualsales;
	}
	public void setPreaccuactualsales(Double preaccuactualsales) {
		this.preaccuactualsales = preaccuactualsales;
	}
	public Double getAccuactualsales() {
		return accuactualsales;
	}
	public void setAccuactualsales(Double accuactualsales) {
		this.accuactualsales = accuactualsales;
	}
	
	public Double getPreaccucostofsales() {
		return preaccucostofsales;
	}
	public void setPreaccucostofsales(Double preaccucostofsales) {
		this.preaccucostofsales = preaccucostofsales;
	}
	public Double getAccucostofsales() {
		return accucostofsales;
	}
	public void setAccucostofsales(Double accucostofsales) {
		this.accucostofsales = accucostofsales;
	}
	public Double getOperatingandmanagementcosts() {
		return operatingandmanagementcosts;
	}
	public void setOperatingandmanagementcosts(Double operatingandmanagementcosts) {
		this.operatingandmanagementcosts = operatingandmanagementcosts;
	}
	public Double getTaxandaccoiatecharge() {
		return taxandaccoiatecharge;
	}
	public void setTaxandaccoiatecharge(Double taxandaccoiatecharge) {
		this.taxandaccoiatecharge = taxandaccoiatecharge;
	}
	public Double getFinancingcosts() {
		return financingcosts;
	}
	public void setFinancingcosts(Double financingcosts) {
		this.financingcosts = financingcosts;
	}
	
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getStartyear() {
		return startyear;
	}
	public void setStartyear(Integer startyear) {
		this.startyear = startyear;
	}
	public Integer getStartmonth() {
		return startmonth;
	}
	public void setStartmonth(Integer startmonth) {
		this.startmonth = startmonth;
	}
	public Integer getEndyear() {
		return endyear;
	}
	public void setEndyear(Integer endyear) {
		this.endyear = endyear;
	}
	public Integer getEndmonth() {
		return endmonth;
	}
	public void setEndmonth(Integer endmonth) {
		this.endmonth = endmonth;
	}
	public Double getInvoicerate() {
		return invoicerate;
	}
	public void setInvoicerate(Double invoicerate) {
		this.invoicerate = invoicerate;
	}
	public Double getAccuinvoicerate() {
		return accuinvoicerate;
	}
	public void setAccuinvoicerate(Double accuinvoicerate) {
		this.accuinvoicerate = accuinvoicerate;
	}
	public Double getProfitrate() {
		return profitrate;
	}
	public void setProfitrate(Double profitrate) {
		this.profitrate = profitrate;
	}
	
}
