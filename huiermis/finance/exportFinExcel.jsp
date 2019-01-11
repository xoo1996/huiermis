<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"prefix="html"%>
	<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("������ͨ������Ʊ","exportExcel(document.forms[0])");
	buttons.put("������ͨ���۷�Ʊ","exportExcel2(document.forms[0])");
	buttons.put("����ר�ý�����Ʊ","exportSpecialExcel(document.forms[0])");
	buttons.put("����ר�����۷�Ʊ","exportSperetailExcel(document.forms[0])");
	
	
	pageContext.setAttribute("button", buttons);
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
%>

<script language="javascript">
	function exportExcel(obj) {
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="����������Ʊ") {
				li[i].disabled=true;
			}
		}
		
		obj.action = '<html:rewrite page="/FinanceAction.do?method=exportBillExcel&fintype=normal"/>';
		obj.submit();
	};
	
	function exportExcel2(obj) {
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="�������۷�Ʊ") {
				li[i].disabled=true;
			}
		}
		
		obj.action = '<html:rewrite page="/FinanceAction.do?method=exportBillExcel&fintype=retail"/>';
		obj.submit();
	};
	
	function exportSpecialExcel(obj) {
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="�������۷�Ʊ") {
				li[i].disabled=true;
			}
		}
		
		obj.action = '<html:rewrite page="/FinanceAction.do?method=exportBillExcel&fintype=special"/>';
		obj.submit();
	};
	
	function exportSperetailExcel(obj) {
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="�������۷�Ʊ") {
				li[i].disabled=true;
			}
		}
		
		obj.action = '<html:rewrite page="/FinanceAction.do?method=exportBillExcel&fintype=speretail"/>';
		obj.submit();
	};
	
	$(document).ready(function(){
		  var grCli="";
		    grCli=<%=dto.getBsc001()%>;
		    if(grCli=="1501000000")
		    {                        
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
				$(this).parent().next().find("input").val(gid);
			}
		});
		    }
		    else
		    {
		    	
		    	$("input[name=gctid]").val("<%=dto.getBsc011()%>");
				$("input[name=gctid]").attr("readonly","true");
		    }
	});
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="��ͨ��Ʊ����" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/FinanceAction.do?method=exportBillExcel" method="POST">
			<tr>
			 	<lemis:texteditor property="gctid" required="false" label="��������" disable="false" />
				<lemis:formateditor mask="date" property="finsubdtstart" required="false" label="�ύ��ʼʱ��" disable="false" format="true" />
				<lemis:formateditor mask="date" property="finsubdtend" required="false" label="�ύ����ʱ��" disable="false" format="true" />
			</tr>
			<tr>
				<lemis:formateditor mask="date" property="finpassdtstart" required="false" label="��˿�ʼʱ��" disable="false" format="true" />
				<lemis:formateditor mask="date" property="finpassdtend" required="false" label="��˽���ʱ��" disable="false" format="true" />
				<lemis:codelisteditor type="gcttype" isSelect="true" label="�ֹ�˾���"
					redisplay="true" required="false" />
			</tr>
			<tr>
				<lemis:formateditor mask="date" property="chgdtstart" required="false" label="�շѿ�ʼʱ��" disable="false" format="true" />
				<lemis:formateditor mask="date" property="chgdtend" required="false" label="�շѽ���ʱ��" disable="false" format="true" />
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button"/>
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>

