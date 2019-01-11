<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants" %>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("bsc011", "门店代码",TagConstants.DF_CENTER));
	header.add(new Formatter("bsc012", "门店",TagConstants.DF_CENTER));
	header.add(new Formatter("scoredate", "操作时间",TagConstants.DF_CENTER));
	header.add(new Formatter("score", "积分",TagConstants.DF_CENTER));
	header.add(new Formatter("detail", "明细",TagConstants.DF_CENTER));
	header.add(new Formatter("pdtnm", "商品名称",TagConstants.DF_CENTER));
	header.add(new Formatter("num", "数量",TagConstants.DF_CENTER));

	Map<String,String> buttons = new LinkedHashMap<String,String>();
	buttons.put("返 回","history.back()");

	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	session.setAttribute("tableheader", "门店积分详细查询");//表头

	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
%>
<html>
	<lemis:base />
	<lemis:body>
		<lemis:title title="门店积分详细查询" />
		<lemis:table action="ChargeAction.do" headerMeta="header" topic="详细信息" mode="radio" />
		<lemis:buttons buttonMeta="button" />
		<lemis:errors />
		<lemis:bottom />
	</lemis:body>
</html>


