<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//����������ȯ���ͻ���ţ����ٻ��ֵֶ���Ǯ���ֿ���,�ݶ���
String coin=(String)request.getSession().getAttribute("coin");
String user=(String)request.getSession().getAttribute("user");
String idcard=(String)request.getSession().getAttribute("idcard");
String phone=(String)request.getSession().getAttribute("phone");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("�� ��","saveData(document.forms[0])");
	buttons.put("�� ��","history.back()");
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
	$(document).ready(function(){
		var mon=$('input[name=repamt]').val();
		$("input[name=money]").val(parseInt(mon));
	});  
	function saveData(obj) {
		var coin=$('input[name=coin]').val();
		var coinnum=$('input[name=coinnum]').val();
		var mon=$('input[name=repamt]').val();
		var money=$('input[name=money]').val();
		if((parseInt(coinnum)!=coinnum)||(parseInt(coinnum)<0)){
			alert("ʹ�ûݶ�����Ŀ�������!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("�ݶ��Ҳ���");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum))){
			alert("ʵ������������");
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if (!checkValue(obj)) {
			return false;
		}
		if (confirm("ȷʵҪ�շ���")) {
			obj.submit();
		}
		else
			return t;
	}
	
	function computeL2() {
		var pdtprc=$('input[name=repamt]').val();//gai
		var mon=$('input[name=repamt]').val();
		var coinnum=$('input[name=coinnum]').val();
		//if(coinnum<=pdtprc-1){
			if((pdtprc*0.5)<coinnum){
				alert("�ݶ��ҵֿ۲��ø���50%������ά�޼۸�");
				$("input[name=coinnum]").val(0);
				$("input[name=changecoin]").val(0);
				$("input[name=money]").val(parseInt(pdtprc));
			//}
		}else{
			var money=mon-coinnum;
			$("input[name=money]").val(parseInt(money));
			$("input[name=changecoin]").val(coinnum);
		}
	}
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="ά���շ�����" />
	<lemis:tabletitle title="ά����Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveRepairCharge">
			<tr>
				<html:hidden property="repidentity" />
				<lemis:texteditor property="repfolid" label="������" required="true"
					disable="true" />
				<lemis:texteditor property="repcltnm" label="�û�����" required="true"
					disable="true" />
				<lemis:texteditor property="reppnm" label="�������ͺ�" disable="true"
					required="true" />
				<html:hidden property="pdtid" />
			</tr>
			<tr>
				<lemis:texteditor property="repid" label="������" disable="true"
					required="true" />
				<lemis:texteditor property="repgctnm" label="���޵�λ" disable="true"
					required="true" />
				<html:hidden property="repgctid" />
				<lemis:formateditor mask="date" format="true" property="repdate"
					label="��������" disable="true" required="true" />
			</tr>
			<tr>
				<lemis:codelisteditor type="repfree" isSelect="false" label="������"
					redisplay="true" required="false" />
				<lemis:codelisteditor type="reppreamt" isSelect="false"
					label="ά�޷�֪ͨ" redisplay="true" required="false" />
				<lemis:codelisteditor type="repcls" isSelect="false" label="�������"
					redisplay="true" required="false" />
			</tr>
			<tr>
				<lemis:texteditor property="repdeclare" label="��������" disable="true"
					required="false" colspan="5" />
			</tr>
			<tr>
				<lemis:texteditor property="repnote" label="��ע" disable="true"
					required="false" colspan="5" />
			</tr>
			<lemis:tabletitle title="ά�޽��" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor property="repconfirmed" label="����ȷ��"
						disable="true" required="false" colspan="5" />
				</tr>
				<tr>
					<lemis:texteditor property="repaction1" label="ά�޴�ʩһ"
						disable="true" required="false" />
					<lemis:texteditor property="repaction2" label="ά�޴�ʩ��"
						disable="true" required="false" />
					<lemis:texteditor property="repaction3" label="ά�޴�ʩ��"
						disable="true" required="false" />
				</tr>
				<tr>
					<lemis:texteditor property="repaction4" label="ά�޴�ʩ��"
						disable="true" required="false" />
					<lemis:texteditor property="repaction5" label="ά�޴�ʩ��"
						disable="true" required="false" />
					<lemis:texteditor property="repaction6" label="ά�޴�ʩ��"
						disable="true" required="false" />
				</tr>
				<tr>
				   <lemis:texteditor property="repaprc" label="����Ԥ��" disable="true"
						required="false" />
					<lemis:texteditor property="repamt" label="���úϼ�" disable="true"
						required="false" />
					<lemis:codelisteditor type="repoprcd" isSelect="false" label="ά��Ա"
						redisplay="true" required="false" dataMeta="userList" />
				</tr>
				<tr>
				     <lemis:codelisteditor type="repregnames" isSelect="false"
						label="����ά��Ա" redisplay="true" required="false"
						dataMeta="userList" />
					<lemis:texteditor property="repattention" label="ע������"
						disable="true" required="false" colspan="3" />
				</tr>
				<tr>
					<lemis:texteditor property="reptip" label="��ܰ��ʾ1" disable="true"
						required="false" />
					<lemis:texteditor property="reptip1" label="��ע" disable="true"
						required="false" />
					<lemis:formateditor mask="date" format="true" property="repfdate"
						label="�깤����" required="false" />
				</tr>
				<tr>
					<%-- <lemis:codelisteditor type="folischg" isSelect="true" label="ά�޷��Ƿ�����"
						redisplay="true" required="true" /> --%>
					<td>�շ�����</td>
					<td><lemis:operdate/></td>
				<lemis:texteditor property="coin" label="�ݶ������" required="false"
						disable="true" value="<%=coin %>"/>
			</tr>
			<tr>
				<lemis:texteditor property="coinnum" label="ʹ�ûݶ�������" required="false"
						disable="false" value="0" onkeyup="computeL2()"/>
				<lemis:texteditor property="changecoin" label="�ֿ��ֽ�(Ԫ)" required="false"
						disable="false" value="0"/>
					<lemis:texteditor property="money" label="ʵ�ս��" required="false"
						disable="false" />
			</tr>
			</table>
				<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="user" id="rate" value="<%=user %>">
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

