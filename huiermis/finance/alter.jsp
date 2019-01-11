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
	buttons.put("返回","history.back()");
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
	<lemis:title title="团体客户信息修改" />
	<lemis:tabletitle title="团体客户信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/FinanceAction.do?method=saveModified"
			method="POST">
			<tr>
				<lemis:texteditor property="gctid" label="客户代码" disable="true"
					required="true" maxlength="6" />
				<lemis:texteditor property="gctnm" label="客户名称" disable="true"
					required="true" maxlength="20" />
			</tr>
			<tr>
			
				<lemis:texteditor property="gctsname"  label="名称全称" maxlength="50"
					disable="false" required="true" colspan="3" />
				<lemis:texteditor property="gcttel"  label="联系电话" maxlength="50"
					disable="false" required="true" colspan="3" />
			</tr>
			<tr>
				<lemis:texteditor property="gctemail" label="Email" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="taxno" label="税号" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctmobilephone" label="开票联系手机" required="true"
					disable="false" maxlength="30" />
			</tr>
			<tr>
				<lemis:texteditor property="gctaddr" label="公司地址" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctdepositbank" label="开户行" required="true"
					disable="false" maxlength="30" />
				<lemis:texteditor property="gctdepositid" label="开户账号" required="true"
					disable="false" maxlength="30" />
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

