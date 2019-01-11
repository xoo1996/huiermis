<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	
	String sta=(String)request.getSession().getAttribute("sta");
	String folstaorigin=(String)request.getSession().getAttribute("folstaorigin");

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("保存", "saveModify(document.forms[1])");
	buttons.put("返回", "window.history.back();");

	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("stoid", "库存编号"));
	header.add(new Formatter("stogrcliid", "所属团体"));
	header.add(new Formatter("stoproid", "商品代码")); 
	header.add(new Formatter("stoproname", "商品名称"));
	header.add(new Formatter("stoamount","数量"));
	header.add(new Formatter("stoactype", "动作"));
	header.add(new Formatter("stodate", "日期"));
	header.add(new Formatter("storemark", "备注"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("stoid", "库存编号");
	
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("hidden", hidden);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	function saveModify(obj) {
		if (!checkValue(document.forms[0])) {
			return false;
		}
		if (!delObj("chk")) {
			return false;
		}
		if (!preCheckForBatch()) {
			return false;
		}

		if( "<%=folstaorigin%>" ==$("#folsta").val()){
			alert("请选择需改变的订单状态");
			return false;
		}

		if(!($("#folsta").val()=="charged" || $("#folsta").val()=="finish")){
			alert("不支持修改为该订单状态");
			return false;
		}
		
		if (confirm("确认修改订单")) {
			window.location.href = "/"
				+ lemis.WEB_APP_NAME
				+ "/order/OrderAction.do?method=savemodifyStatus&folsta="
				+ $("#folsta").val() + "&fdtfno="
				+ $("input[name=fdtfno]").val() + "&sta="
				+ "<%=sta%>" + "&folstaorigin"
				+ "<%=folstaorigin%>" + "&"				
				+ getAlldata(document.all.tableform);
		}

	};
	
</script>
</head>
<lemis:base />
<lemis:body>
	<lemis:title title="订单修改" />
	<html:form action="/OrderAction.do?method=modifyStatus2" method="POST">
	
		<lemis:tabletitle title="基本信息" />
			<table class="tableInput">
				<lemis:editorlayout />
					<tr>
						<lemis:texteditor property="fdtfno" label="订单号" disable="true" />
						<lemis:texteditor property="folctid" label="店铺编号" disable="true" />
						<lemis:texteditor property="gctnm" label="店铺名称" disable="true" />
					</tr>
					<tr>
						
						<lemis:texteditor property="fdtpid" label="商品编号" disable="true" />
						<lemis:formateditor property="foldt" label="订货日期" required="false" mask="date" format="true"/>
					</tr>
					<tr>
						<lemis:codelisteditor type="folsta" label="订单状态" required="false"/>
					</tr>
			</table>
		
		<lemis:tabletitle title="收费信息" />
			<table class="tableInput">
				<lemis:editorlayout />
				<tr>
					<lemis:codelisteditor type="folischg" label="是否已收费" required="false"/>
					<lemis:formateditor property="folchgdt" label="收费日期" required="false" mask="date" format="true"/>
					<lemis:codelisteditor type="tianchong" label="" required="false"/>
				</tr>
			</table>
		
		<lemis:tabletitle title="退机信息" />
			<table class="tableinput">
			<lemis:editorlayout />
				<tr>
					<lemis:formateditor property="fdtodt" label="退机到直属店日期" required="false" mask="date" format="true" />
					<lemis:formateditor property="fdtrecheaddt" label="退机到总部店日期" required="false" mask="date" format="true" />
					<lemis:formateditor required="false" property="fdtexadt" mask="date" label="总部审核退机日期" format="true"/>
				</tr>
			</table>

 	</html:form>   
	<lemis:table topic="库存信息"
		action="OrderAction.do" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false" hiddenMeta="hidden"
		 batchInputType="update" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />

</lemis:body>
</html>