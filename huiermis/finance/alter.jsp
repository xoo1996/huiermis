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
	buttons.put("����","history.back()");
    pageContext.setAttribute("button", buttons);
%>

<html>
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
	<lemis:title title="����ͻ���Ϣ�޸�" />
	<lemis:tabletitle title="����ͻ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/FinanceAction.do?method=saveModified"
			method="POST">
			<tr>
				<lemis:texteditor property="gctid" label="�ͻ�����" disable="true"
					required="true" maxlength="6" />
				<lemis:texteditor property="gctnm" label="�ͻ�����" disable="true"
					required="true" maxlength="20" />
			</tr>
			<tr>
			
				<lemis:texteditor property="gctsname"  label="����ȫ��" maxlength="50"
					disable="false" required="true" colspan="3" />
				<lemis:texteditor property="gcttel"  label="��ϵ�绰" maxlength="50"
					disable="false" required="true" colspan="3" />
			</tr>
			<tr>
				<lemis:texteditor property="gctemail" label="Email" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="taxno" label="˰��" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctmobilephone" label="��Ʊ��ϵ�ֻ�" required="true"
					disable="false" maxlength="30" />
			</tr>
			<tr>
				<lemis:texteditor property="gctaddr" label="��˾��ַ" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctdepositbank" label="������" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctdepositid" label="�����˺�" required="true"
					disable="false" maxlength="30" />
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

