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
	header.add(new Formatter("bsc011", "�ŵ����",TagConstants.DF_CENTER));
	header.add(new Formatter("bsc012", "�ŵ�",TagConstants.DF_CENTER));
	header.add(new Formatter("scoredate", "����ʱ��",TagConstants.DF_CENTER));
	header.add(new Formatter("score", "����",TagConstants.DF_CENTER));
	header.add(new Formatter("detail", "��ϸ",TagConstants.DF_CENTER));
	header.add(new Formatter("pdtnm", "��Ʒ����",TagConstants.DF_CENTER));
	header.add(new Formatter("num", "����",TagConstants.DF_CENTER));

	Map<String,String> buttons = new LinkedHashMap<String,String>();
	buttons.put("�� ��","history.back()");

	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	session.setAttribute("tableheader", "�ŵ������ϸ��ѯ");//��ͷ

	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
%>
<html>
	<lemis:base />
	<lemis:body>
		<lemis:title title="�ŵ������ϸ��ѯ" />
		<lemis:table action="ChargeAction.do" headerMeta="header" topic="��ϸ��Ϣ" mode="radio" />
		<lemis:buttons buttonMeta="button" />
		<lemis:errors />
		<lemis:bottom />
	</lemis:body>
</html>


