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
	buttons.put("设置税率","setTaxRate()");
	buttons.put("保存更改","batchSave(document.all.tableform)");
	buttons.put("删除配置","batchDelete(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

    List<Formatter> header = new ArrayList<Formatter>();
    header.add(new Formatter("finrateid","序号"));
    header.add(new Formatter("band","品牌"));
    header.add(new Formatter("series","系列"));
    header.add(new Formatter("rate","扣率"));
    header.add(new Formatter("fpdtnm","商品类别"));
	
    List<Editor> batchInput = new ArrayList<Editor>();
    batchInput.add(new Editor("text", "finrateid", "序号", "true"));
    batchInput.add(new Editor("text", "band", "品牌", "true"));
    batchInput.add(new Editor("text", "series", "系列", "true"));
    batchInput.add(new Editor("text", "rate", "扣率", "true"));
    batchInput.add(new Editor("text", "fpdtnm", "商品类别", "true"));
        

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
		
		if (confirm("确认保存更改？")) {
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
		
		if (confirm("确认删除配置？")) {
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
	<lemis:title title="开票扣率配置" />

	<lemis:table topic="开票扣率修改与录入" action="/FinanceAction.do" headerMeta="header" mode="checkbox" batchInputMeta="batchInput" pageSize="<%=count%>" hiddenMeta="hidden"/>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>