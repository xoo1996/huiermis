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
	header.add(new Formatter("webyear", "年份"));
	header.add(new Formatter("webmonth", "月份"));
	header.add(new Formatter("webname", "姓名"));
	header.add(new Formatter("pdtnm", "机型型号"));
	header.add(new Formatter("webnum", "台数"));
	header.add(new Formatter("webmoney", "销售金额"));
	header.add(new Formatter("webcall", "电话"));
	header.add(new Formatter("websc", "网络销售信息类型"));
	header.add(new Formatter("webfrom", "来源"));
	header.add(new Formatter("webintro", "介绍人"));
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "ivtyear", "年份","true"));
	editors.add(new Editor("text", "ivtmonth", "月份","true"));
	editors.add(new Editor("text", "websc", "网络销售信息类型"));
	editors.add(new Editor("text", "webname", "姓名"));

	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
%>

<%@page import="org.radf.plat.taglib.TagConstants"%><html>

<lemis:base />
<lemis:body>
	<lemis:title title="网络销售查询" />
	<lemis:query action="/BusinessAction.do?method=queryWebSell" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="网络销售信息"
		hiddenMeta="hidden" mode="radio" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


