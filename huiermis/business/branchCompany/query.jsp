<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("修改","edit(document.all.tableform)");
    buttons.put("关 闭","closeWindow(\"\")");
    
    List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "iyear", "年","true"));
	editors.add(new Editor("text", "imonth", "月","true"));
	 editors.add(new Editor("text","gctid","客户代码"));
	 editors.add(new Editor("text","gctarea","所属区域"));
	
	List<Formatter> header = new ArrayList<Formatter>();
	//header.add(new Formatter("feegctid","客户代码"));
	
	header.add(new Formatter("gctname","客户名称"));
	header.add(new Formatter("totalreturns","总销售额"));
	header.add(new Formatter("invoiceamount","开票额"));
	header.add(new Formatter("invoicerate","开票比例"));
	header.add(new Formatter("premoneyfunds","上月货币资金"));
	header.add(new Formatter("purchasecosts","本月进货款"));
	header.add(new Formatter("actualpurchasecosts","实付货款"));
	header.add(new Formatter("monthcosts","本月费用"));
	header.add(new Formatter("monthtax","本月税金"));
	header.add(new Formatter("wagesreturn","工资打回"));
	header.add(new Formatter("elsecosts","其他"));
	header.add(new Formatter("moneyfunds","本月货币资金"));
	header.add(new Formatter("preaccountpayable","上月应付账款"));
	header.add(new Formatter("accountpayable","本月应付账款"));
	header.add(new Formatter("inventory","存货"));
	header.add(new Formatter("paidincapital","实收资本"));
	header.add(new Formatter("accountrecievable","本月应收账款"));
	header.add(new Formatter("wagespayable","应付工资"));
	header.add(new Formatter("elseaccountpayable","其他应付账款"));
	header.add(new Formatter("elsereceivables","其他应收账款"));
	header.add(new Formatter("preyearundisprofits","上年累计未分配利润"));
	header.add(new Formatter("preaccuprofit","上月累计利润"));
	header.add(new Formatter("monthprofit","本月利润",true));
	header.add(new Formatter("yearprofit","本年利润",true));
	header.add(new Formatter("accuundisprofits","累计未分配利润",true));
	header.add(new Formatter("preaccuinvoiceamount","上月累计开票额",true));
	header.add(new Formatter("accuinvoiceamount","累计开票额",true));
	header.add(new Formatter("preaccuactualsales","上月累计实际销售额",true));
	header.add(new Formatter("accuactualsales","累计实际销售额",true));
	header.add(new Formatter("accuinvoicerate","累计开票量",true));	
	header.add(new Formatter("preaccucostofsales","上月累计主营业务成本",true));
	header.add(new Formatter("accucostofsales","累计主营业务成本",true));
	/* header.add(new Formatter("operatingandmanagementcosts","营业及管理费用",true));
	header.add(new Formatter("taxandaccoiatecharge","主营业务税金及附加",true));
	header.add(new Formatter("financingcosts","财务费用",true));  */
	header.add(new Formatter("profitrate","利润率",true));
	header.add(new Formatter("tax","所得税",true));
	header.add(new Formatter("bsc012","录入员",true));
	header.add(new Formatter("operatedate","录入时间","",TagConstants.DT_YEAR_MONTH_DATE,true));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("gctid", "用户编号");
	hidden.put("iyear", "年");
	hidden.put("imonth", "月");

	
	pageContext.setAttribute("editor",editors);
    pageContext.setAttribute("button", buttons);
    pageContext.setAttribute("header", header);
    pageContext.setAttribute("hidden",hidden);
%>


<%@page import="org.radf.plat.taglib.Editor"%>
<%@page import="org.radf.plat.taglib.TagConstants"%><html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	function edit(obj){
	var t = editObj("chk");
	if(!t){
		return t;
	}
	obj.action = '<html:rewrite page="/BranchFinancialAction.do?method=edit&"/>'+getAlldata(obj);			
	obj.submit();
	};
	$(document).ready( function() {
		
	       var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
			
			$("input[name=gctid]").autocomplete(shops,{
				max : 10,
				matchContains : true
			});
			$("input[name=gctid]").result(function(event, data, formatted) {
				if (data){
					var gnm = data[0].substring(data[0].indexOf("=")+1);
					var gid = data[0].substring(0,data[0].indexOf("="));
					$("input[name=gctid]").val(gid);
					$("input[name=gctname]").val(gnm);
					$(this).parent().next().find("input").val(gid);
				}
			});
		});
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="分公司情况信息" />
	<lemis:query action="/BranchFinancialAction.do?method=query" editorMeta="editor" topic="分公司情况查询" />
	<lemis:table action="/BranchFinancialAction.do" topic="查询分公司情况" headerMeta="header" hiddenMeta="hidden" 
	   mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
