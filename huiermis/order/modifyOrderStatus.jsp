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
	buttons.put("����", "saveModify(document.forms[1])");
	buttons.put("����", "window.history.back();");

	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("stoid", "�����"));
	header.add(new Formatter("stogrcliid", "��������"));
	header.add(new Formatter("stoproid", "��Ʒ����")); 
	header.add(new Formatter("stoproname", "��Ʒ����"));
	header.add(new Formatter("stoamount","����"));
	header.add(new Formatter("stoactype", "����"));
	header.add(new Formatter("stodate", "����"));
	header.add(new Formatter("storemark", "��ע"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("stoid", "�����");
	
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
			alert("��ѡ����ı�Ķ���״̬");
			return false;
		}

		if(!($("#folsta").val()=="charged" || $("#folsta").val()=="finish")){
			alert("��֧���޸�Ϊ�ö���״̬");
			return false;
		}
		
		if (confirm("ȷ���޸Ķ���")) {
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
	<lemis:title title="�����޸�" />
	<html:form action="/OrderAction.do?method=modifyStatus2" method="POST">
	
		<lemis:tabletitle title="������Ϣ" />
			<table class="tableInput">
				<lemis:editorlayout />
					<tr>
						<lemis:texteditor property="fdtfno" label="������" disable="true" />
						<lemis:texteditor property="folctid" label="���̱��" disable="true" />
						<lemis:texteditor property="gctnm" label="��������" disable="true" />
					</tr>
					<tr>
						
						<lemis:texteditor property="fdtpid" label="��Ʒ���" disable="true" />
						<lemis:formateditor property="foldt" label="��������" required="false" mask="date" format="true"/>
					</tr>
					<tr>
						<lemis:codelisteditor type="folsta" label="����״̬" required="false"/>
					</tr>
			</table>
		
		<lemis:tabletitle title="�շ���Ϣ" />
			<table class="tableInput">
				<lemis:editorlayout />
				<tr>
					<lemis:codelisteditor type="folischg" label="�Ƿ����շ�" required="false"/>
					<lemis:formateditor property="folchgdt" label="�շ�����" required="false" mask="date" format="true"/>
					<lemis:codelisteditor type="tianchong" label="" required="false"/>
				</tr>
			</table>
		
		<lemis:tabletitle title="�˻���Ϣ" />
			<table class="tableinput">
			<lemis:editorlayout />
				<tr>
					<lemis:formateditor property="fdtodt" label="�˻���ֱ��������" required="false" mask="date" format="true" />
					<lemis:formateditor property="fdtrecheaddt" label="�˻����ܲ�������" required="false" mask="date" format="true" />
					<lemis:formateditor required="false" property="fdtexadt" mask="date" label="�ܲ�����˻�����" format="true"/>
				</tr>
			</table>

 	</html:form>   
	<lemis:table topic="�����Ϣ"
		action="OrderAction.do" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false" hiddenMeta="hidden"
		 batchInputType="update" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />

</lemis:body>
</html>