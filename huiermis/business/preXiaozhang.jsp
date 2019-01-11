<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@page import="org.radf.plat.taglib.TagConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ivtyear", "��");
	hidden.put("ivtmonth", "��");
	
	pageContext.setAttribute("hidden", hidden);
%>
<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>

<script language="javascript">
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	function createQueryString() {
		var queryString = {
			year : $('input[name=ivtyear]').val(),
			month : $('input[name=ivtmonth]').val(),
			gcltid : $('input[name=ivtgcltid]').val()
		};
		return queryString;
	};
	function startRequest(obj) {
		$.getJSON(
						"/huiermis/business/BusinessAction.do?method=preXiaozhang&time="
								+ new Date(),
						createQueryString(),
						function(data) {
							if (data[0].sign == '0') {
								alert("������ɸ��µĽ��㣡");
							} else if(data[0].sign == '1'){
								if (confirm("�������������Ԥ����Ҫ�����𣿴˲����޷����ˣ�")) {
									if (confirm("ȷ��Ҫ�����𣿴˲����޷����ˣ�")) {
										alert("����ִ�� "+$('input[name=ivtyear]').val() + "��" + $('input[name=ivtmonth]').val()+"������Ԥ����,�����ĵȴ�...");
										obj.action = '<html:rewrite page="/BusinessAction.do?method=copyYuejie&mark=redo"/>' + getAlldata(obj);
										obj.submit();
									}
								}
							}else if(data[0].sign == '2'){
								if (confirm("��ȷ����"
										+ $('input[name=ivtyear]').val() + "��"
										+ $('input[name=ivtmonth]').val()
										+ "�µ�����Ԥ����")) {
									alert("����ִ�� "+$('input[name=ivtyear]').val() + "��" + $('input[name=ivtmonth]').val()+"�µ�����Ԥ����,�����ĵȴ�..."); 
									obj.action = '<html:rewrite page="/BusinessAction.do?method=copyYuejie"/>' + getAlldata(obj);
									obj.submit();
								}
							}
						});
	};
	function yuejie(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		startRequest(obj);
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="ȫ���ݶ�������Ԥ����" />
	<lemis:tabletitle title="����Ԥ����" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=report" method="POST">
			<tr>
				<lemis:texteditor property="ivtyear" label="��" required="true"
					disable="false" />
				<lemis:texteditor property="ivtmonth" label="��" required="true"
					disable="false" />
			</tr>
		</html:form>
	</table>
	<br />
	<div align="right">
		<input type="button" name="openwin"
		value="&nbsp;Ԥ&nbsp;��&nbsp;��&nbsp;" class='buttonGray' onclick=yuejie(document.forms[0]);>
		<input type="reset"
		name="Submit" value="&nbsp;��&nbsp;&nbsp;��&nbsp;" class='buttonGray'>
	</div>
	<lemis:errors />

	<lemis:bottom />
</lemis:body>
</html>
