<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("保 存","saveData(document.forms[0])");
	buttons.put("惠耳打印","huier_print(document.forms[0])");
	buttons.put("杰闻打印","jiewen_print(document.forms[0])");
	buttons.put("返回","history.back()");
    pageContext.setAttribute("button", buttons);
%>

<html>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script language="javascript">

	function saveData(obj) {
		/* if($("select[name=qachkopr]").val()==$("input[name=qafxman]").val()||$("select[name=qachkopr2]").val()==$("input[name=qafxman]").val()){
			alert("质检员1和质检员2不能与放行人相同！");
			return false;
		} */
		if($("select[name=qachkopr]").val()==$("input[name=qafxman]").val()){
			alert("质检员1和质检员2不能与放行人相同！");
			return false;
		}
		
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	function huier_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=printTestReport&type=huier&id="
				+ $("input[name=qaid]").val();
	};
	function jiewen_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=printTestReport&type=jiewen&id="
				+ $("input[name=qaid]").val();
	};
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="修改质检记录" />
	<lemis:tabletitle title="质检基本信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/QAAction.do?method=saveModified" method="POST">
			<html:hidden property="qaid" />
			<tr>
				<lemis:texteditor property="qafno" label="订单号" required="true"
					disable="true" />
				<lemis:texteditor property="qasid" label="机身编号" disable="true"
					required="true" />
				<lemis:texteditor property="qapnm" label="助听器型号" disable="true"
					required="true" />
			</tr>
			<tr>
				<lemis:texteditor property="qacltnm" label="用户姓名" required="true"
					disable="true" />
				<lemis:codelisteditor type="qatype" label="质检类别" isSelect="false"
					required="true" />
				<lemis:codelisteditor type="qastatus" label="质检状态" isSelect="false"
					required="true" />
			</tr>
			<tr>
				<lemis:texteditor property="gctnm" label="客户名称" required="true"
					disable="true" />
			</tr>
			<lemis:tabletitle title="质检结果" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:codelisteditor type="qatest1" label="外观" isSelect="true"
						required="true" />
					
				</tr>
				<tr>
					<lemis:texteditor property="qatest2" label="饱和声输出" required="true"
						disable="false" />
					<%-- <lemis:texteditor property="qatest3" label="最大值增益" required="true"
						disable="false" /> --%>
					<lemis:texteditor property="qatest4" label="1600Hz增益"
						required="true" disable="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qatest5" label="频率响应范围（检1）" isSelect="true"
						required="true" />
					<lemis:texteditor property="qatest6" label="等效输入噪声" required="true"
						disable="false" />
					<lemis:texteditor property="qatest7" label="电池电流" required="true"
						disable="false" />
				</tr>
				<tr>
					<lemis:texteditor property="qatest8" label="总谐波失真(500Hz)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest9" label="总谐波失真(800Hz)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest10" label="总谐波失真(1600Hz)"
						required="true" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor property="qatest11" label="高频平均声输出(检2)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest3" label="高频平均声增益(检2)" required="true"
						disable="false" />
					<lemis:texteditor property="qatestft" label="频率响应范围(检2)"
						required="true" disable="false" />
				</tr>
				<tr>
				<lemis:texteditor property="qashellele" label="外壳漏电流（不大于0.1mA）" required="true"
					disable="false" />
				<lemis:texteditor property="qaeledc" label="患者漏电流dc（不大于0.01mA）" disable="false"
					required="true" />
				<lemis:texteditor property="qaeleac" label="患者漏电流ac（不大于0.1mA）" disable="false"
					required="true" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qatestsound" label="电声性能" isSelect="true"
						required="true" />
					<lemis:codelisteditor type="qatestsafe" label="安全性能" isSelect="true"
						required="true" />
					<lemis:codelisteditor type="qachk" label="检验结果" required="true"
						isSelect="true" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qachkopr" isSelect="true" label="质检员1"
						redisplay="true" required="false" dataMeta="userList" />
					<td>经办机构</td>
					<td><lemis:operorg /></td>
					<lemis:formateditor mask="date" format="true" property="qachkdt"
						label="质检日期" required="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qachkopr2" isSelect="true" label="质检员2"
						redisplay="true" required="false" dataMeta="userList" />
					<td>经办机构</td>
					<td><lemis:operorg /></td>
					<lemis:formateditor mask="date" format="true" property="qachkdt2"
						label="质检日期" required="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qafxman" isSelect="false" label="放行人"
						redisplay="true" required="false" dataMeta="userList" />
					<lemis:codelisteditor type="qadev" label="设备编号及编码" isSelect="false"
					required="true" />
				</tr>
			</table>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

