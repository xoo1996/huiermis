<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("webyear", "���"));
	header.add(new Formatter("webmonth", "�·�"));
	header.add(new Formatter("webname", "����"));
	header.add(new Formatter("pdtnm", "�����ͺ�"));
	header.add(new Formatter("webnum", "̨��"));
	header.add(new Formatter("webmoney", "���۽��"));
	header.add(new Formatter("webcall", "�绰"));
	header.add(new Formatter("websc", "����������Ϣ����"));
	header.add(new Formatter("webfrom", "��Դ"));
	header.add(new Formatter("webintro", "������"));
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "ivtyear", "���","true"));
	editors.add(new Editor("text", "ivtmonth", "�·�","true"));
	editors.add(new Editor("text", "websc", "����������Ϣ����"));
	editors.add(new Editor("text", "webname", "����"));

	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
%>

<%@page import="org.radf.plat.taglib.TagConstants"%><html>

<lemis:base />
<lemis:body>
	<lemis:title title="�������۲�ѯ" />
	<lemis:query action="/BusinessAction.do?method=queryWebSell" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="����������Ϣ"
		hiddenMeta="hidden" mode="radio" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


