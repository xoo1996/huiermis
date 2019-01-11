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
    buttons.put("�� ��","window.history.back();");
    
    List<Editor> editors = new ArrayList<Editor>();
	pageContext.setAttribute("editor",editors);
    pageContext.setAttribute("button", buttons);
%>


<%@page import="org.radf.plat.taglib.Editor"%><html>
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
	};
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="¼��������Ϣ" />
	<lemis:tabletitle title="������Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		  <html:form action="/SaleAction.do?method=savent" method="POST">
			<tr>
				<lemis:texteditor property="mgctid" label="�ͻ�����" disable="true" 
				 required="true" />
				<lemis:texteditor property="myear" label="��" disable="true" 
				 required="true" />
				<lemis:texteditor property="mmonth" label="��" disable="true" 
				 required="true" />
			</tr>
			<tr>
			    <lemis:textareaeditor required="false" property="mnote" label="��ע" disable="false"
				  maxlength="400" colspan="5" rowspan="5"/>
			</tr>
	</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		