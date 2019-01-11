<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("保 存","saveData(document.forms[0])");
    buttons.put("关 闭","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%>

<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/lemis/js/lemisTree.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
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
	Number.prototype.toFixed=function(num) 
	   { 
	   //重新构造toFixed方法,IE5.0+ 
	   with(Math)this.NO=round(this.valueOf()*pow(10,num))/pow(10,num); 
	   return String(/\./g.exec(this.NO)?this.NO:this.NO+"."+String(Math.pow(10,num)).substr(1,num)); 
	   };
	   function autoInvoicerate () {
			var totalreturns = $("input[name=totalreturns]").val();
			var invoiceamount = $("input[name=invoiceamount]").val();
			var rate = Number(invoiceamount)*100/Number(totalreturns);
			$("input[name=invoicerate]").val(rate.toFixed(2));
		};
		function autoMoneyfunds () {
			var premoneyfunds = $("input[name=premoneyfunds]").val();
			var invoiceamount = $("input[name=invoiceamount]").val();
			var actualpurchasecosts = $("input[name=actualpurchasecosts]").val();
			var monthcosts = $("input[name=monthcosts]").val();
			var wagesreturn = $("input[name=wagesreturn]").val();
			var elsecosts = $("input[name=elsecosts]").val();
			var monthtax = $("input[name=monthtax]").val();
			var moneyfunds = Number(premoneyfunds)+Number(invoiceamount)-Number(actualpurchasecosts)-Number(monthcosts)-Number(wagesreturn)-Number(elsecosts)-Number(monthtax);
			$("input[name=moneyfunds]").val(moneyfunds.toFixed(2));
		};
		function autoAccountpayable () {
			var preaccountpayable = $("input[name=preaccountpayable]").val();
			var purchasecosts = $("input[name=purchasecosts]").val();
			var actualpurchasecosts = $("input[name=actualpurchasecosts]").val();
			var accountpayable = Number(preaccountpayable)+Number(purchasecosts)-Number(actualpurchasecosts);
			$("input[name=accountpayable]").val(accountpayable.toFixed(2));
		};
		function autoYearprofit () {
			var preaccuprofit = $("input[name=preaccuprofit]").val();
			var monthprofit = $("input[name=monthprofit]").val();
			var yearprofit = Number(preaccuprofit)+Number(monthprofit);
			$("input[name=yearprofit]").val(yearprofit.toFixed(2));
		};
		function autoAccuundisprofits () {
			var preyearundisprofits = $("input[name=preyearundisprofits]").val();
			var yearprofit = $("input[name=yearprofit]").val();
			var accuundisprofits = Number(preyearundisprofits)+Number(yearprofit);
			$("input[name=accuundisprofits]").val(accuundisprofits.toFixed(2));
		};
		function autoAccuinvoiceamount () {
			var preaccuinvoiceamount = $("input[name=preaccuinvoiceamount]").val();
			var invoiceamount = $("input[name=invoiceamount]").val();
			var accuinvoiceamount = Number(invoiceamount)/1.03+Number(preaccuinvoiceamount);
			$("input[name=accuinvoiceamount]").val(accuinvoiceamount.toFixed(2));
		};
		function autoAccuactualsales () {
			var totalreturns = $("input[name=totalreturns]").val();
			var preaccuactualsales = $("input[name=preaccuactualsales]").val();
			var accuactualsales = Number(preaccuactualsales)+Number(totalreturns);
			$("input[name=accuactualsales]").val(accuactualsales.toFixed(2));
		};
		function autoAccuinvoicerate () {
			var accuinvoiceamount = $("input[name=accuinvoiceamount]").val();
			var accuactualsales = $("input[name=accuactualsales]").val();
			var accuinvoicerate = Number(accuinvoiceamount)*1.03/Number(accuactualsales);
			$("input[name=accuinvoicerate]").val(accuinvoicerate.toFixed(2));
		};
		function autoAccucostofsales () {
			var preaccucostofsales = $("input[name=preaccucostofsales]").val();
			var purchasecosts = $("input[name=purchasecosts]").val();
			var accucostofsales = Number(preaccucostofsales)+Number(purchasecosts);
			$("input[name=accucostofsales]").val(accucostofsales.toFixed(2));
		};
		function autoProfitrate () {
			var yearprofit = $("input[name=yearprofit]").val();
			var accuinvoiceamount = $("input[name=accuinvoiceamount]").val();
			var profitrate = Number(yearprofit)*100/Number(accuinvoiceamount);
			$("input[name=profitrate]").val(profitrate.toFixed(2));
		};
		function autoAccuyearinvoiceamount(){
			var gctid = $("input[name=gctid]").val();
			var iyear = $("input[name=iyear]").val();
			var imonth = $("input[name=imonth]").val();
			var invoiceamount = $("input[name=invoiceamount]").val();
			if(gctid != '' && iyear != '' && imonth != ''){
				$.getJSON("/huiermis/business/BranchFinancialAction.do?method=getAccuyearinvoiceamount",
						createInvoiceamountString(), function(data) {
							var accuyearinvoiceamount=Number(data[0].accuyearinvoiceamount)+Number(invoiceamount);
							$('input[name=accuyearinvoiceamount]').val(accuyearinvoiceamount.toFixed(2));
						});	
			}
		}
			
		function createInvoiceamountString() {
			var queryString = {
				gctid : $("input[name=gctid]").val(),
				iyear : $("input[name=iyear]").val(),
				imonth : $("input[name=imonth]").val()
			};
			return queryString;
		};
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="修改分公司数据" />
	<lemis:tabletitle title="修改分公司数据" />
	<table class="tableinput">
		<lemis:editorlayout />
		  <html:form action="/BranchFinancialAction.do?method=saveModified" method="POST">
				<tr>
				<lemis:texteditor property="gctid" label="客户代码" disable="true" 
				 required="true" />
				 <lemis:texteditor property="gctname" label="客户名称" disable="true" 
				 required="true" />
				<lemis:texteditor property="iyear" label="年" disable="true" 
				 required="true" maxlength="4" />
				 </tr>
			   <tr>
				<lemis:texteditor property="imonth" label="月" disable="true" 
				 required="true" maxlength="2"  />
				</tr>
				<tr>
			  	<lemis:formateditor mask="-nnnnnnnnnnnn.nn" format="true" required="true" property="operator" 
				label="操作员" disable="true"/>
				<lemis:formateditor mask="date" format="true" required="true" property="operatedate" 
				label="操作时间" disable="true"/>
			  </tr>
				
		 <lemis:tabletitle title="本月开票情况" />
		<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="true" property="totalreturns"
						label="总销售额" disable="false" />
					<lemis:texteditor required="true" property="invoiceamount"
						label="开票额" disable="false" />
					<lemis:texteditor required="true" property="invoicerate"
						label="开票比例" disable="false" onclick="autoInvoicerate()" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuyearinvoiceamount"
						label="累计十二个月开票额" disable="false" onclick="autoAccuyearinvoiceamount()" />
				</tr>
			</table>

			<lemis:tabletitle title="本月货币资金情况" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="premoneyfunds"
						label="上月资金" disable="false" />
					<lemis:texteditor required="false" property="purchasecosts"
						label="本月进货款" disable="false" />
					<lemis:texteditor required="false" property="actualpurchasecosts"
						label="实付货款" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="monthcosts"
						label="本月费用" disable="false" />
					<lemis:texteditor required="false" property="monthtax"
						label="本月税金" disable="false" />
					<lemis:texteditor required="false" property="wagesreturn"
						label="工资打回" disable="false" />
					
				</tr>
				<tr>
					<lemis:texteditor required="false" property="elsecosts"
						label="其他费用" disable="false" />
					<lemis:texteditor required="true" property="moneyfunds"
						label="本月货币资金" disable="false" onclick="autoMoneyfunds()"/>
				</tr>
			</table>
			<lemis:tabletitle title="应付、应收款情况" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="preaccountpayable"
						label="上月应付账款" disable="false" />
					<lemis:texteditor required="true" property="accountpayable"
						label="本月应付账款" disable="false" onclick="autoAccountpayable()"/>
					<lemis:texteditor required="false" property="inventory"
						label="存货" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="paidincapital"
						label="实收资本" disable="false" />
					<lemis:texteditor required="false" property="accountrecievable"
						label="本月应收账款" disable="false" />
					<lemis:texteditor required="false" property="wagespayable"
						label="应付工资" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="elseaccountpayable"
						label="其他应付款项" disable="false" />
					<lemis:texteditor required="false" property="elsereceivables"
						label="其他应收款项" disable="false" />
				</tr>
			</table>
			<lemis:tabletitle title="利润情况" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="preyearundisprofits"
						label="上年累计未分配利润" disable="false" />
					<lemis:texteditor required="false" property="preaccuprofit"
						label="上月累计利润" disable="false" />
					<lemis:texteditor required="false" property="monthprofit"
						label="本月利润" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="yearprofit"
						label="本年利润" disable="false" onclick="autoYearprofit()" />
					<lemis:texteditor required="true" property="accuundisprofits"
						label="累计未分配利润" disable="false" onclick="autoAccuundisprofits()" />
					<lemis:texteditor required="false" property="preaccuinvoiceamount"
						label="上月累计开票销售额" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuinvoiceamount"
						label="累计开票销售额" disable="false" onclick="autoAccuinvoiceamount()" />
					<lemis:texteditor required="false" property="preaccuactualsales"
						label="上月累计实际销售额" disable="false" />
					<lemis:texteditor required="true" property="accuactualsales"
						label="累计实际销售额" disable="false" onclick="autoAccuactualsales()" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuinvoicerate"
						label="累计开票量" disable="false" onclick="autoAccuinvoicerate()"/>
					<lemis:texteditor required="false" property="preaccucostofsales"
						label="上月累计主营业务成本" disable="false" />
					<lemis:texteditor required="true" property="accucostofsales"
						label="累计主营业务成本" disable="false" onclick="autoAccucostofsales()"/>
				</tr>
				<%-- <tr>
					<lemis:texteditor required="false" property="operatingandmanagementcosts"
						label="营业费用及管理费用" disable="false" />
					<lemis:texteditor required="false" property="taxandaccoiatecharge"
						label="主营业务税金及附加" disable="false" />
					<lemis:texteditor required="false" property="financingcosts"
						label="财务费用" disable="false" />
				</tr> --%>
				<tr>
					<lemis:texteditor required="true" property="profitrate"
						label="利润率" disable="false" onclick="autoProfitrate()"/>
					<lemis:texteditor required="false" property="tax"
						label="所得税" disable="false" />
				</tr>
			</table>
			<table class="tableinput">
				<COLGROUP><COL width='5%'><COL width='80%'></COLGROUP>
				<tr>
				<lemis:textareaeditor required="false" label="备注" disable="false" property="remark"></lemis:textareaeditor>
					<%-- <lemis:texteditor required="false" property="remark"
						label="备注" disable="false" />
						<td></td> --%>
				</tr>
				
			</table>
	</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
