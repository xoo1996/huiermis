package org.radf.apps.business.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class BranchFinancialForm extends ActionForm{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//7
	private String gctarea;//区域
	private Integer iyear;//年
	private Integer imonth;//月
	private String smonth;//字符串月
    private String gctname;//客户名称
	private String gctid;//客户(门店)编号
	private String operator;//操作员编号
    private String bsc012;//操作员姓名
	private Date operatedate;//操作时间
	
	//打印时间选项
	private Integer startyear;
	private Integer startmonth;
	private Integer endyear;
	private Integer endmonth;
	
	//本月开票情况 3
	private Double totalreturns;//总销售额
	private Double invoiceamount;//开票额
	private Double invoicerate;//开票比例=开票额/总销售额
	private Double accuyearinvoiceamount;//累计12个月开票额
	
	//本月货币资金情况 7
	private Double premoneyfunds;//上月资金=上月的 本月货币资金
	private Double purchasecosts;//本月进货款
	private Double actualpurchasecosts;//实付货款
	private Double monthcosts;//本月费用
	private Double monthtax;//本月税金
	private Double wagesreturn;//工资打回
	private Double elsecosts;//本月其他费用
	private Double moneyfunds;//本月货币资金=上月资金+开票额-实付款-费用-工资-其他
	
	//应付、应收款情况 7
	private Double preaccountpayable;//上月应付账款=取上月 应付款项
	private Double accountpayable;//本月应付账款=上月应付账款+本月进货款-实付货款
	private Double inventory;//存货
	private Double paidincapital;//实收资本
	private Double elseaccountpayable;//其他应付账款
	private Double wagespayable;//应付工资
	private Double elsereceivables;//其他应收款项
	private Double accountrecievable;//本月应收账款

	//利润情况17   accu=accumulated undis=undistributed
	private Double preyearundisprofits;//上年累计未分配利润
	private Double preaccuprofit;//上月累计利润=上月的 本年利润
	private Double monthprofit;//本月利润
	private Double yearprofit;//本年利润=上月累计利润+本月利润
	private Double accuundisprofits;//累计未分配利润=上年累计未分配利润+本年利润
	private Double preaccuinvoiceamount;//上月累计开票销售额=上月的 累计开票销售额
	private Double accuinvoiceamount;//累计开票销售额=上月累计开票销售额+开票额/1.03
	private Double preaccuactualsales;//上月累计实际销售额=取上月 累计实际销售额
	private Double accuactualsales;//累计实际销售额=上月累计销售额+总销售额
	private Double accuinvoicerate;//累计开票量=累计开票销售额*1.03/累计实际销售额
	private Double preaccucostofsales;//上月累计主营业务成本=上月 累计主营业务成本
	private Double accucostofsales;//累计主营业务成本=上月累计主营业务成本+本月进货款
	private Double operatingandmanagementcosts;//营业费用及管理费用
	private Double taxandaccoiatecharge;//主营业务税金及附加
	private Double financingcosts;//财务费用
	private Double profitrate;//利润率=本年利润/累计开票销售额
	private Double tax;//所得税
	private String remark;//备注
	
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
