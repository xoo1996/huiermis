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
    buttons.put("�� ��","document.forms[0].reset();");
    buttons.put("�� ��","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%>

<html>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script language="javascript">
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	}
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="�û������¼����" />
	<lemis:tabletitle title="�û�������Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do?method=addSC" method="POST">
			<html:hidden property="ictid" />
			<tr>
				<lemis:formateditor mask="date" property="fctcdt" label="��������"
					required="true" disable="false" />
				<lemis:texteditor property="fctreason" label="����ԭ��" required="false"
					disable="false" colspan="5" />
			</tr>
			<tr>
				<lemis:texteditor property="fctdgn" label="������" required="false"
					disable="false" colspan="7" />
			</tr>
			<tr>
				<lemis:texteditor property="fcttmt" label="�������" required="false"
					disable="false" colspan="7" />
			</tr>
			<tr>
				<lemis:texteditor property="fctdoc" label="ҽ��" required="false"
					disable="false" />
				<lemis:texteditor property="fctnt" label="��ע" required="false"
					disable="false" colspan="5" />
			</tr>
			<tr>
				<td>������</td>
				<td><lemis:operator /></td>
				<td>�������</td>
				<td><lemis:operorg /></td>
				<td>��������</td>
				<td><lemis:operdate /></td>
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>
