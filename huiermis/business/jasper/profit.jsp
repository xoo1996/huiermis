<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />

<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	$(document).ready(function(){
		var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		
		$("input[name=ivtgcltid]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=ivtgcltid]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=ivtgcltid]").val(gid);
				$(this).parent().next().find("input").val(gid);
			}
		});
	});
	function printScore(obj){
		if (!checkValue(obj)) {
			return false;
		}
		var id=$('input[name=ivtgcltid]').val();
		var year=$('input[name=ivtyear]').val();
		var month=$('input[name=ivtmonth]').val();
		obj.action = '<html:rewrite href="/huiermis/charge/ChargeAction.do?method=printScore&bsc011='+id+'&year='+year+'&month='+month+'&"/>'+getAlldata(obj);			
		obj.submit();
	}
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="打印利润表" />
	 <lemis:tabletitle title="打印利润表" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=profit" method="POST">
		<tr>
			<lemis:texteditor property="ivtgcltid" required="true" label="客户代码"
					disable="false" />
			<lemis:texteditor property="ivtyear" required="true" label="年份"
					disable="false" maxlength="4" />
			<lemis:texteditor property="ivtmonth" required="true" label="月份"
					disable="false" maxlength="2" />
		</tr>
		</html:form>
	</table>
	<div align="right">
	<input type="button"
		name="openwin" value="&nbsp;打&nbsp;印&nbsp;利&nbsp;润&nbsp;表" class='buttonGray'
		onclick="print(document.forms[0]);">
 	<input type="reset"
		name="Submit" value="&nbsp;重&nbsp;&nbsp;置&nbsp;" class='buttonGray'>
	<input type="button"
		name="openwin" value="&nbsp;打&nbsp;印&nbsp;积&nbsp;分&nbsp;表" class='buttonGray'
		onclick="printScore(document.forms[0]);">
	</div>
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>
