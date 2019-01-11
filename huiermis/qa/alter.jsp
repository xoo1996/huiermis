<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
	buttons.put("�ݶ���ӡ","huier_print(document.forms[0])");
	buttons.put("���Ŵ�ӡ","jiewen_print(document.forms[0])");
	buttons.put("����","history.back()");
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
			alert("�ʼ�Ա1���ʼ�Ա2�������������ͬ��");
			return false;
		} */
		if($("select[name=qachkopr]").val()==$("input[name=qafxman]").val()){
			alert("�ʼ�Ա1���ʼ�Ա2�������������ͬ��");
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
	<lemis:title title="�޸��ʼ��¼" />
	<lemis:tabletitle title="�ʼ������Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/QAAction.do?method=saveModified" method="POST">
			<html:hidden property="qaid" />
			<tr>
				<lemis:texteditor property="qafno" label="������" required="true"
					disable="true" />
				<lemis:texteditor property="qasid" label="������" disable="true"
					required="true" />
				<lemis:texteditor property="qapnm" label="�������ͺ�" disable="true"
					required="true" />
			</tr>
			<tr>
				<lemis:texteditor property="qacltnm" label="�û�����" required="true"
					disable="true" />
				<lemis:codelisteditor type="qatype" label="�ʼ����" isSelect="false"
					required="true" />
				<lemis:codelisteditor type="qastatus" label="�ʼ�״̬" isSelect="false"
					required="true" />
			</tr>
			<tr>
				<lemis:texteditor property="gctnm" label="�ͻ�����" required="true"
					disable="true" />
			</tr>
			<lemis:tabletitle title="�ʼ���" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:codelisteditor type="qatest1" label="���" isSelect="true"
						required="true" />
					
				</tr>
				<tr>
					<lemis:texteditor property="qatest2" label="���������" required="true"
						disable="false" />
					<%-- <lemis:texteditor property="qatest3" label="���ֵ����" required="true"
						disable="false" /> --%>
					<lemis:texteditor property="qatest4" label="1600Hz����"
						required="true" disable="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qatest5" label="Ƶ����Ӧ��Χ����1��" isSelect="true"
						required="true" />
					<lemis:texteditor property="qatest6" label="��Ч��������" required="true"
						disable="false" />
					<lemis:texteditor property="qatest7" label="��ص���" required="true"
						disable="false" />
				</tr>
				<tr>
					<lemis:texteditor property="qatest8" label="��г��ʧ��(500Hz)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest9" label="��г��ʧ��(800Hz)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest10" label="��г��ʧ��(1600Hz)"
						required="true" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor property="qatest11" label="��Ƶƽ�������(��2)"
						required="true" disable="false" />
					<lemis:texteditor property="qatest3" label="��Ƶƽ��������(��2)" required="true"
						disable="false" />
					<lemis:texteditor property="qatestft" label="Ƶ����Ӧ��Χ(��2)"
						required="true" disable="false" />
				</tr>
				<tr>
				<lemis:texteditor property="qashellele" label="���©������������0.1mA��" required="true"
					disable="false" />
				<lemis:texteditor property="qaeledc" label="����©����dc��������0.01mA��" disable="false"
					required="true" />
				<lemis:texteditor property="qaeleac" label="����©����ac��������0.1mA��" disable="false"
					required="true" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qatestsound" label="��������" isSelect="true"
						required="true" />
					<lemis:codelisteditor type="qatestsafe" label="��ȫ����" isSelect="true"
						required="true" />
					<lemis:codelisteditor type="qachk" label="������" required="true"
						isSelect="true" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qachkopr" isSelect="true" label="�ʼ�Ա1"
						redisplay="true" required="false" dataMeta="userList" />
					<td>�������</td>
					<td><lemis:operorg /></td>
					<lemis:formateditor mask="date" format="true" property="qachkdt"
						label="�ʼ�����" required="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qachkopr2" isSelect="true" label="�ʼ�Ա2"
						redisplay="true" required="false" dataMeta="userList" />
					<td>�������</td>
					<td><lemis:operorg /></td>
					<lemis:formateditor mask="date" format="true" property="qachkdt2"
						label="�ʼ�����" required="false" />
				</tr>
				<tr>
					<lemis:codelisteditor type="qafxman" isSelect="false" label="������"
						redisplay="true" required="false" dataMeta="userList" />
					<lemis:codelisteditor type="qadev" label="�豸��ż�����" isSelect="false"
					required="true" />
				</tr>
			</table>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

