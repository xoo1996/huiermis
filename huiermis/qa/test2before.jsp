<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	TreeMap<String,String> list = new TreeMap<String,String>();
	list.put("0.01","1%");
	list.put("0.02","2%");   
	list.put("0.03","3%");
	list.put("0.04","4%");
	list.put("0.05","5%");
	list.put("0.06","6%");
	list.put("0.07","7%");   
	list.put("0.08","8%");
	list.put("0.09","9%");
	list.put("0.10","10%");
	pageContext.setAttribute("qaData",list);
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("保存检测结果","saveData(document.forms[0])");
    buttons.put("惠耳打印","huier_print(document.forms[0])");
	buttons.put("杰闻打印","jiewen_print(document.forms[0])");
    buttons.put("重 置","document.forms[0].reset();");
    buttons.put("返回","history.back()");
    buttons.put("关 闭","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%>

<html>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script language="javascript">
	$(document).ready(function() {
		$("#qatest1").val("yes");
		//$("#qatest5").val("yes");
		$("#qatestsound").val("yes");
		$("#qatestsafe").val("yes");
		$("#qachk").val("yes");
		$("#qatest8").val("1%");
		$("#qatest9").val("1%");
		$("#qatest10").val("1%");
	});
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	function print(obj) {
		obj.action = '<html:rewrite page="/QAAction.do?method=printTestReport2&"/>' + getAlldata(obj);
		obj.submit();
	};
	function huier_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=printTestReport2&type=huier&id="
				+ $("input[name=qaid]").val();
	};
	function jiewen_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=printTestReport2&type=jiewen&id="
				+ $("input[name=qaid]").val();
	};
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="质量检测" />
	<lemis:tabletitle title="检验结果" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/QAAction.do?method=updateTest2before" method="POST">
			<html:hidden property="qaid" />
			<html:hidden property="pdttype" />
			<tr>
				<lemis:texteditor property="qafno" label="订单号" required="true"
					disable="true" />
				<lemis:texteditor property="qasid" label="机身编号" disable="true"
					required="true" />
				<lemis:texteditor property="qapnm" label="助听器型号" disable="true"
					required="true" />
				<html:hidden property="qapid" />
			</tr>
			<tr>
				<lemis:texteditor property="qacltnm" label="用户姓名" required="true"
					disable="true" />
				<lemis:codelisteditor type="qatype" label="质检类别" isSelect="false"
					required="true" />
			</tr>
			<tr>
				<lemis:codelisteditor type="qatest1" label="外观" isSelect="true"
					required="true" />
				
			</tr>
			<tr>
				<lemis:formateditor mask="nnn.nn" property="qatest2" label="饱和声输出"
					required="true" disable="false" />
				<lemis:formateditor mask="nnn.nn" property="qatest11" label="高频平均声输出"
					required="true" disable="false" />
				<lemis:formateditor mask="nn.nn" property="qatest3" label="高频平均声增益" required="true"
					disable="false" />
			</tr>
			<tr>
				<lemis:texteditor  property="qatestft" label="频率响应范围"
					required="true" disable="false" />
				<lemis:formateditor mask="nn.nn" property="qatest6" label="等效输入噪声" required="true"
					disable="false" />
				<lemis:formateditor mask="n.nn" property="qatest7" label="电池电流" required="true"
					disable="false" />
			</tr>
			<tr>
				<lemis:codelisteditor type="qatest8" label="总谐波失真(500Hz)"
					isSelect="true" required="true" dataMeta="qaData" />
				<lemis:codelisteditor type="qatest9" label="总谐波失真(800Hz)"
					isSelect="true" required="true" dataMeta="qaData" />
				<lemis:codelisteditor type="qatest10" label="总谐波失真(1600Hz)"
					isSelect="true" required="true" dataMeta="qaData" />
			</tr>
			<tr>
				<lemis:texteditor property="qashellele" label="外壳漏电流（不大于0.1mA）" required="true"
					disable="false" value="＜0.01"/>
				<lemis:texteditor property="qaeledc" label="患者漏电流dc（不大于0.01mA）" disable="false"
					required="true" value="＜0.01"/>
				<lemis:texteditor property="qaeleac" label="患者漏电流ac（不大于0.1mA）" disable="false"
					required="true" value="＜0.01"/>
			</tr>
			<tr>
				<lemis:codelisteditor type="qatestsound" label="电声性能" isSelect="true"
					required="true" />
				<lemis:codelisteditor type="qatestsafe" label="安全性能" isSelect="true"
					required="true" />
				<lemis:codelisteditor type="qachk" label="检验结果" isSelect="true"
					required="true" />
			</tr>
			<tr>
				<lemis:codelisteditor type="qadev" label="检测设备及编号" isSelect="true"
					required="true" />
			</tr>
			<tr>
				<td>质检员</td>
				<td><lemis:operator /></td>
				<td>经办机构</td>
				<td><lemis:operorg /></td>
				<td>质检日期</td>
				<td><lemis:operdate /></td>
			</tr>
		</html:form>
	</table>
	
	
	
	
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>