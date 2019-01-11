<!-- recommendation/personguidance/Viewguidance.jsp -->
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%
	List buttons = new ArrayList();
	buttons.add(new Button("Btn_back", "返 回", "rec999997","go2Page(\"recommendation\",\"2\")"));
	buttons.add(new Button("Btn_close", "关 闭", "rec999999","closeWindow(\"Rec0201Form\")"));
	pageContext.setAttribute("button", buttons);

%>
<lemis:base />
<html>
<lemis:body>
	<lemis:errors />
	<lemis:title title="个人指导信息" />
	<html:form action="/Rec05Action.do" method="POST">
		<lemis:tabletitle title="个人指导信息" />
		<table class="tableInput">
			<lemis:editorlayout />	
			<tr>
			    <html:hidden property="aac001" />
				<lemis:formateditor mask="card" property="aac002" label="公民身份号码"	disable="true" required="false" />
				<lemis:texteditor property="aac003" label="姓名" required="false" disable="true" />
				<lemis:codelisteditor type="aac004" isSelect="false" redisplay="true" label="性别" />
			</tr>
			<tr>
				<lemis:formateditor mask="date" property="acc231" label="指导日期"	disable="true" required="false" format="true" />
				<lemis:texteditor property="acc232" label="指导内容" required="false" disable="true" />
				<lemis:texteditor property="acc233" label="指导人员" required="false" disable="true" />
			</tr>
			<tr>
				<lemis:texteditor property="acc234" label="指导效果" required="false" disable="true" />
				<lemis:texteditor property="acc235" label="素质评测情况" required="false" disable="true" />
				<lemis:codelisteditor type="acc236" isSelect="false" redisplay="true" label="是否参加职业培训" />
			</tr>
			<tr>
				<lemis:texteditor property="acc237" label="职业培训情况" disable="true" maxlength="100" colspan="5" />

			</tr>

			<tr>
				<td>经办人</td>
				<td><lemis:operator /></td>
				<td>经办机构</td>
				<td><lemis:operorg /></td>
				<td>经办日期</td>
				<td><lemis:operdate /></td>
			</tr>
		</table>

	</html:form>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

