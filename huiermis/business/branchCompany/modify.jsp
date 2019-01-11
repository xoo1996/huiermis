<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
    buttons.put("�� ��","closeWindow(\"\")");
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
	   //���¹���toFixed����,IE5.0+ 
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
	<lemis:title title="�޸ķֹ�˾����" />
	<lemis:tabletitle title="�޸ķֹ�˾����" />
	<table class="tableinput">
		<lemis:editorlayout />
		  <html:form action="/BranchFinancialAction.do?method=saveModified" method="POST">
				<tr>
				<lemis:texteditor property="gctid" label="�ͻ�����" disable="true" 
				 required="true" />
				 <lemis:texteditor property="gctname" label="�ͻ�����" disable="true" 
				 required="true" />
				<lemis:texteditor property="iyear" label="��" disable="true" 
				 required="true" maxlength="4" />
				 </tr>
			   <tr>
				<lemis:texteditor property="imonth" label="��" disable="true" 
				 required="true" maxlength="2"  />
				</tr>
				<tr>
			  	<lemis:formateditor mask="-nnnnnnnnnnnn.nn" format="true" required="true" property="operator" 
				label="����Ա" disable="true"/>
				<lemis:formateditor mask="date" format="true" required="true" property="operatedate" 
				label="����ʱ��" disable="true"/>
			  </tr>
				
		 <lemis:tabletitle title="���¿�Ʊ���" />
		<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="true" property="totalreturns"
						label="�����۶�" disable="false" />
					<lemis:texteditor required="true" property="invoiceamount"
						label="��Ʊ��" disable="false" />
					<lemis:texteditor required="true" property="invoicerate"
						label="��Ʊ����" disable="false" onclick="autoInvoicerate()" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuyearinvoiceamount"
						label="�ۼ�ʮ�����¿�Ʊ��" disable="false" onclick="autoAccuyearinvoiceamount()" />
				</tr>
			</table>

			<lemis:tabletitle title="���»����ʽ����" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="premoneyfunds"
						label="�����ʽ�" disable="false" />
					<lemis:texteditor required="false" property="purchasecosts"
						label="���½�����" disable="false" />
					<lemis:texteditor required="false" property="actualpurchasecosts"
						label="ʵ������" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="monthcosts"
						label="���·���" disable="false" />
					<lemis:texteditor required="false" property="monthtax"
						label="����˰��" disable="false" />
					<lemis:texteditor required="false" property="wagesreturn"
						label="���ʴ��" disable="false" />
					
				</tr>
				<tr>
					<lemis:texteditor required="false" property="elsecosts"
						label="��������" disable="false" />
					<lemis:texteditor required="true" property="moneyfunds"
						label="���»����ʽ�" disable="false" onclick="autoMoneyfunds()"/>
				</tr>
			</table>
			<lemis:tabletitle title="Ӧ����Ӧ�տ����" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="preaccountpayable"
						label="����Ӧ���˿�" disable="false" />
					<lemis:texteditor required="true" property="accountpayable"
						label="����Ӧ���˿�" disable="false" onclick="autoAccountpayable()"/>
					<lemis:texteditor required="false" property="inventory"
						label="���" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="paidincapital"
						label="ʵ���ʱ�" disable="false" />
					<lemis:texteditor required="false" property="accountrecievable"
						label="����Ӧ���˿�" disable="false" />
					<lemis:texteditor required="false" property="wagespayable"
						label="Ӧ������" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="false" property="elseaccountpayable"
						label="����Ӧ������" disable="false" />
					<lemis:texteditor required="false" property="elsereceivables"
						label="����Ӧ�տ���" disable="false" />
				</tr>
			</table>
			<lemis:tabletitle title="�������" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor required="false" property="preyearundisprofits"
						label="�����ۼ�δ��������" disable="false" />
					<lemis:texteditor required="false" property="preaccuprofit"
						label="�����ۼ�����" disable="false" />
					<lemis:texteditor required="false" property="monthprofit"
						label="��������" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="yearprofit"
						label="��������" disable="false" onclick="autoYearprofit()" />
					<lemis:texteditor required="true" property="accuundisprofits"
						label="�ۼ�δ��������" disable="false" onclick="autoAccuundisprofits()" />
					<lemis:texteditor required="false" property="preaccuinvoiceamount"
						label="�����ۼƿ�Ʊ���۶�" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuinvoiceamount"
						label="�ۼƿ�Ʊ���۶�" disable="false" onclick="autoAccuinvoiceamount()" />
					<lemis:texteditor required="false" property="preaccuactualsales"
						label="�����ۼ�ʵ�����۶�" disable="false" />
					<lemis:texteditor required="true" property="accuactualsales"
						label="�ۼ�ʵ�����۶�" disable="false" onclick="autoAccuactualsales()" />
				</tr>
				<tr>
					<lemis:texteditor required="true" property="accuinvoicerate"
						label="�ۼƿ�Ʊ��" disable="false" onclick="autoAccuinvoicerate()"/>
					<lemis:texteditor required="false" property="preaccucostofsales"
						label="�����ۼ���Ӫҵ��ɱ�" disable="false" />
					<lemis:texteditor required="true" property="accucostofsales"
						label="�ۼ���Ӫҵ��ɱ�" disable="false" onclick="autoAccucostofsales()"/>
				</tr>
				<%-- <tr>
					<lemis:texteditor required="false" property="operatingandmanagementcosts"
						label="Ӫҵ���ü��������" disable="false" />
					<lemis:texteditor required="false" property="taxandaccoiatecharge"
						label="��Ӫҵ��˰�𼰸���" disable="false" />
					<lemis:texteditor required="false" property="financingcosts"
						label="�������" disable="false" />
				</tr> --%>
				<tr>
					<lemis:texteditor required="true" property="profitrate"
						label="������" disable="false" onclick="autoProfitrate()"/>
					<lemis:texteditor required="false" property="tax"
						label="����˰" disable="false" />
				</tr>
			</table>
			<table class="tableinput">
				<COLGROUP><COL width='5%'><COL width='80%'></COLGROUP>
				<tr>
				<lemis:textareaeditor required="false" label="��ע" disable="false" property="remark"></lemis:textareaeditor>
					<%-- <lemis:texteditor required="false" property="remark"
						label="��ע" disable="false" />
						<td></td> --%>
				</tr>
				
			</table>
	</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
