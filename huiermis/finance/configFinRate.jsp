<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Integer count=(Integer)request.getSession().getAttribute("count");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("����˰��","setTaxRate()");
	buttons.put("�������","batchSave(document.all.tableform)");
	buttons.put("ɾ������","batchDelete(document.all.tableform)");
	buttons.put("�� ��","closeWindow(\"\")");

    List<Formatter> header = new ArrayList<Formatter>();
    header.add(new Formatter("finrateid","���"));
    header.add(new Formatter("band","Ʒ��"));
    header.add(new Formatter("series","ϵ��"));
    header.add(new Formatter("rate","����"));
    header.add(new Formatter("fpdtnm","��Ʒ���"));
	
    List<Editor> batchInput = new ArrayList<Editor>();
    batchInput.add(new Editor("text", "finrateid", "���", "true"));
    batchInput.add(new Editor("text", "band", "Ʒ��", "true"));
    batchInput.add(new Editor("text", "series", "ϵ��", "true"));
    batchInput.add(new Editor("text", "rate", "����", "true"));
    batchInput.add(new Editor("text", "fpdtnm", "��Ʒ���", "true"));
        

	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("batchInput",batchInput);
	pageContext.setAttribute("header",header);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>


<script language="javascript">
	
	function batchSave(obj) {
		if (!checkValue(document.forms[0])) {
			return false;
		}
		if (!delObj("chk")) {
			return false;
		}
		if (!preCheckForBatch()) {
			return false;
		}
		
		if (confirm("ȷ�ϱ�����ģ�")) {
			window.location.href = "/"
				+ lemis.WEB_APP_NAME
				+ "/finance/FinanceAction.do?method=setFinRate&"
				+ getAlldata(document.all.tableform);
		}
	};
	
	function batchDelete(obj) {
		if (!checkValue(document.forms[0])) {
			return false;
		}
		if (!delObj("chk")) {
			return false;
		}
		if (!preCheckForBatch()) {
			return false;
		}
		
		if (confirm("ȷ��ɾ�����ã�")) {
			window.location.href = "/"
				+ lemis.WEB_APP_NAME
				+ "/finance/FinanceAction.do?method=deleteFinRate&"
				+ getAlldata(document.all.tableform);
		}
	};
	
	function setTaxRate() {
		window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/finance/FinanceAction.do?method=setTaxRate";

	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="��Ʊ��������" />

	<lemis:table topic="��Ʊ�����޸���¼��" action="/FinanceAction.do" headerMeta="header" mode="checkbox" batchInputMeta="batchInput" pageSize="<%=count%>" hiddenMeta="hidden"/>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>