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
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("提 交","saveData(document.forms[0])");
	buttons.put("重 置","document.forms[0].reset()");
	
    pageContext.setAttribute("button", buttons);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//$("select[name=folurgischg]").val("0");
		//var mon=$('input[name=balance]').val();
		//$("input[name=money]").val(parseInt(mon));
	});
	function saveData(obj) {
		if (confirm("确实要提交吗？")) {
			obj.submit();
		}
	};
	
	</script>

	<script>
	function computeL1() {
		var webcvt=$('input[name=webcvt]').val();//gai
		var weblocal=$('input[name=weblocal]').val();
		var sum=parseFloat(webcvt)+parseFloat(weblocal);
		sum = sum.toFixed(2);//保留两位有效数字
		$("input[name=webtotal]").val(parseFloat(sum));
	}
	function computeL2() {
		var webmoney=$('input[name=webmoney]').val();//gai
		var webtotal=$('input[name=webtotal]').val();
		var sum=parseFloat(webtotal)/parseFloat(webmoney);
		sum = sum.toFixed(2);//保留两位有效数字
		$("input[name=webrate]").val(parseFloat(sum));
	}
</script>
<lemis:body>
<lemis:base />
    <lemis:title title="网络销售来源数据统计" />
	<lemis:tabletitle title="网络销售来源信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=staSource" method="POST">
			<tr>
				<lemis:texteditor  property="webyear" label="年份" disable="false" required="true"  />	
				<lemis:texteditor property="webmonth" label="月份" disable="false" required="true" />
			</tr>
			<tr>
				<lemis:texteditor  property="websource" label="网络来源人数" disable="false" required="true"  />	
				<lemis:texteditor property="websell" label="网络销售人数" disable="false" required="true" />
				<lemis:texteditor  property="webnum" label="网络销售台数" disable="false" required="true"  />				
			</tr>
			<tr>
				<lemis:texteditor  property="webcvt" label="总部转诊销售额" disable="false" required="true"  />	
				<lemis:texteditor property="weblocal" label="地方网络销售额" disable="false" required="true" />
				<lemis:texteditor  property="webtotal" label="合计网络销售额" disable="false" required="true" onclick="computeL1()" />	
			</tr>
			<tr>
				<lemis:texteditor  property="webrate" label="网络销售占当月销售额百分比" disable="false" required="false" />				
				<lemis:codelisteditor type="webmark" isSelect="true" label="地图标注" redisplay="true" required="true" />
				<lemis:texteditor  property="webcheck" label="发帖登记" disable="false" required="true"  />	
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>