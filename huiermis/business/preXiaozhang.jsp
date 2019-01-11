<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@page import="org.radf.plat.taglib.TagConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ivtyear", "年");
	hidden.put("ivtmonth", "月");
	
	pageContext.setAttribute("hidden", hidden);
%>
<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>

<script language="javascript">
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	function createQueryString() {
		var queryString = {
			year : $('input[name=ivtyear]').val(),
			month : $('input[name=ivtmonth]').val(),
			gcltid : $('input[name=ivtgcltid]').val()
		};
		return queryString;
	};
	function startRequest(obj) {
		$.getJSON(
						"/huiermis/business/BusinessAction.do?method=preXiaozhang&time="
								+ new Date(),
						createQueryString(),
						function(data) {
							if (data[0].sign == '0') {
								alert("请先完成该月的结算！");
							} else if(data[0].sign == '1'){
								if (confirm("该月已完成销账预处理，要重做吗？此操作无法回退！")) {
									if (confirm("确定要重做吗？此操作无法回退！")) {
										alert("正在执行 "+$('input[name=ivtyear]').val() + "年" + $('input[name=ivtmonth]').val()+"月销账预处理,请耐心等待...");
										obj.action = '<html:rewrite page="/BusinessAction.do?method=copyYuejie&mark=redo"/>' + getAlldata(obj);
										obj.submit();
									}
								}
							}else if(data[0].sign == '2'){
								if (confirm("请确认做"
										+ $('input[name=ivtyear]').val() + "年"
										+ $('input[name=ivtmonth]').val()
										+ "月的销账预处理？")) {
									alert("正在执行 "+$('input[name=ivtyear]').val() + "年" + $('input[name=ivtmonth]').val()+"月的销账预处理,请耐心等待..."); 
									obj.action = '<html:rewrite page="/BusinessAction.do?method=copyYuejie"/>' + getAlldata(obj);
									obj.submit();
								}
							}
						});
	};
	function yuejie(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		startRequest(obj);
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="全部惠耳点销账预处理" />
	<lemis:tabletitle title="销账预处理" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=report" method="POST">
			<tr>
				<lemis:texteditor property="ivtyear" label="年" required="true"
					disable="false" />
				<lemis:texteditor property="ivtmonth" label="月" required="true"
					disable="false" />
			</tr>
		</html:form>
	</table>
	<br />
	<div align="right">
		<input type="button" name="openwin"
		value="&nbsp;预&nbsp;处&nbsp;理&nbsp;" class='buttonGray' onclick=yuejie(document.forms[0]);>
		<input type="reset"
		name="Submit" value="&nbsp;重&nbsp;&nbsp;置&nbsp;" class='buttonGray'>
	</div>
	<lemis:errors />

	<lemis:bottom />
</lemis:body>
</html>
