<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("qafno", "订单号"));
	header.add(new Formatter("gctnm", "客户名称"));
	header.add(new Formatter("qacltnm", "用户姓名"));
	header.add(new Formatter("qasid", "机身编号"));
	header.add(new Formatter("qapnm", "助听器型号"));
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("打印","print(document.all.tableform)");
	buttons.put("放行","fangxing(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("qaid", "质检流水号");
	hidden.put("qafno", "订单号");
	hidden.put("qachkopr","质检员1");
	hidden.put("qachkopr2","质检员2");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","qafno","订单号"));
	editors.add(new Editor("text","qasid","机身编号"));
	editors.add(new Editor("text","qacltnm","用户姓名"));
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	$(document).ready(function() {
		$("input[name=qasid]:first").focus();
		$("input[name=qasid]:first").val("");
		$("input[name=qasid]").blur(function() {
			if ($("input[name=qasid]:first").val() != "") {
				$("#queryForm").submit();
			}
		});
	});
	function fangxing(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		
		if (confirm("经检测合格是否放行？")) {
			window.location.href = "/"
				+ lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=fangxing&"
				+ getAlldata(document.all.tableform);
		} 
		
	}
	
	function print(obj) {
		
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		//console.log(getAlldata(document.all.tableform));
		window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/qa/QAAction.do?method=printFangxing&"
		+ getAlldata(document.all.tableform);
		
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="放行" />
	<lemis:query action="/QAAction.do?method=queryFangxing"
		editorMeta="editor" topic="查询条件" />
	<lemis:table action="QAAction.do" headerMeta="header" topic="等待放行记录"
		hiddenMeta="hidden" mode="checkbox" pageSize="20"/>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


