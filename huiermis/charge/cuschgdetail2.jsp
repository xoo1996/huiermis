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
//����������ȯ���ͻ���ţ����ٻ��ֵֶ���Ǯ���ֿ���,�ݶ���
String score=(String)request.getSession().getAttribute("score");
String coin=(String)request.getSession().getAttribute("coin");
String change=(String)request.getSession().getAttribute("change");
String user=(String)request.getSession().getAttribute("user");
String sco=(String)request.getSession().getAttribute("sco");
String fee=(String)request.getSession().getAttribute("fee");
String rate=(String)request.getSession().getAttribute("rate");
String idcard=(String)request.getSession().getAttribute("idcard");
String phone=(String)request.getSession().getAttribute("phone");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
	buttons.put("�� ��","history.back()");
	
	//List<Editor> editors = new ArrayList<Editor>();
	//editors.add(new Editor("text", "folno", "������"));

	 //pageContext.setAttribute("editors", editors);
    pageContext.setAttribute("button", buttons);
%>


<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>

<script language="javascript">
	$(document).ready(function(){
		//$("select[name=folischg]").val("1");
		$("select[name=folurgischg]").val("0");
		var mon=$('input[name=balance]').val();
		$("input[name=money]").val(parseInt(mon));
	});
	function saveData(obj) {
		var coin=$('input[name=coin]').val();
		var coinnum=$('input[name=coinnum]').val();
		var score=$('input[name=score]').val();
		var sco=$('input[name=sco]').val();
		var num=$('input[name=num]').val();
		var mon=$('input[name=balance]').val();
		var money=$('input[name=money]').val();
		var fee=$('input[name=fee]').val();
		if((parseInt(coinnum)!=coinnum)||(parseInt(coinnum)<0)){
			alert("ʹ�ûݶ�����Ŀ�������!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			return false;
		}
		if((parseInt(num)!=num)||(parseInt(num)<0)){
			alert("����ȯ��Ŀ�������!");
			$("input[name=num]").val(0);
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("�ݶ��Ҳ���");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			return false;
		}
		if(parseInt(score)<parseInt(sco*num)){
			alert("���ֲ���");
			$("input[name=num]").val(0);
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum-fee*num))){
			alert("ʵ������������");
			return false;
		}
		if (!checkValue(obj)) {
			return false;
		}
		if(<%=phone.length() %>!=8&&<%=phone.length() %>!=11&&<%=phone.length() %>!=12){
			alert("�ÿͻ���Ϣ����ϵ��ʽ��Ч,��Ҫ��Ϊ�ݶ������̳ǻ�Ա�����������ͻ���Ϣ");
		}
		if (confirm("ȷʵҪ�շ���")) {
			obj.submit();
		}
		else
			return t;
	};

	function computeL1() {
		var pdtprc=$('input[name=balance]').val();//gai
		var mon=$('input[name=balance]').val();
		var fee=$('input[name=fee]').val();
		var num=$('input[name=num]').val();
		var rate=$('input[name=rate]').val();
		var coinnum=$('input[name=coinnum]').val();
		var sum=num*fee;
		if((pdtprc*rate/100)<sum){
			alert("������ִ���ȯ���ø���"+rate+"%");
			$("input[name=num]").val(0);
		}else{
			var money=mon-sum-coinnum;
			$("input[name=money]").val(parseInt(money));
		}
	}
	function computeL2() {
		var pdtprc=$('input[name=balance]').val();//gai
		var mon=$('input[name=balance]').val();
		var coinnum=$('input[name=coinnum]').val();
		var fee=$('input[name=fee]').val();
		var num=$('input[name=num]').val();
		var sum=num*fee;
		//if(coinnum<=pdtprc-1){
			if(((coinnum<=pdtprc-1)&&(pdtprc*0.3)<coinnum)){
				alert("�ݶ��ҵֿ�100%�����������߲��ø���30%�������۸�");
				$("input[name=coinnum]").val(0);
				$("input[name=changecoin]").val(0);
				$("input[name=money]").val(parseInt(pdtprc));
			//}
		}else{
			var money=mon-coinnum-sum;
			$("input[name=money]").val(parseInt(money));
			$("input[name=changecoin]").val(coinnum);
		}
	}
</script>



<lemis:body>
	<lemis:base />
    <lemis:title title="���ƻ��շ�����" />
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveCustomizedCharge"
			method="POST">
			<html:hidden property="folno" />
			<tr>
				<lemis:texteditor  property="folno" label="������" disable="true" required="true" 
					 maxlength="6" />
				
				<lemis:codelisteditor type="foltype" isSelect="false" label="��������" redisplay="true" required="true" />
				<lemis:texteditor property="folctnm" label="���Ƶ�λ" disable="true"
					required="true" />
				<html:hidden property="folctid"/>
			</tr>
			<tr>
			
				<%-- <lemis:codelisteditor type="folischg" isSelect="true" label="�Ƿ����շ�"
					redisplay="true" required="true" /> --%>
				<lemis:texteditor property="cltnm" label="�û�����" disable="true"
					 required="true" />
				<lemis:texteditor property="ictgender" label="�Ա�" disable="true"
					 required="true"/>
				<lemis:texteditor property="fdtcltid" label="�û����" required="true"
					disable="true" value="<%=user %>"/>
			</tr>
			<tr>
				
				<lemis:texteditor property="pdtnm" label="���ƻ��ͺ�" disable="true"
					required="false"/>
				<html:hidden property="pdtid"/>
				<lemis:texteditor property="ictpcd" label="�ʱ�" required="false"
					disable="true" maxlength="10" />
				<lemis:texteditor property="icttel" label="��ϵ�绰" required="false"
					disable="true" maxlength="20" />
			</tr>
			<tr>
				<td>�շ�����</td>
				<td><lemis:operdate/></td>
				<lemis:texteditor property="ictaddr" label="�û���ַ" disable="true"
					required="false" colspan="3" maxlength="80" />
			</tr>
			
			<tr>
				<lemis:texteditor property="pdtprc" label="ԭ��" disable="true"
						required="false"  />
				<lemis:texteditor property="discount" label="����" disable="true"
						required="false"  />
				<lemis:texteditor property="fdtprc" label="�ۼ�" disable="true"
						required="false"  />
			</tr>
			<tr>	
				<lemis:texteditor property="deposit" label="����" required="true"
					disable="true" maxlength="30" />
			   <lemis:texteditor property="xubaofee" label="������" required="false"
					disable="true" />
				<lemis:texteditor property="balance"  label="Ӧ�����" disable="true" 
						required="true" />
			</tr>
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="ʵ�����" disable="false" 
						required="true" /> --%>
						
				<lemis:texteditor property="folurgfee" label="�Ӽ���" required="true"
					disable="true" maxlength="30" />
				<lemis:codelisteditor type="folurgischg" isSelect="true" label="�Ƿ��ռӼ���"
					redisplay="true" required="true" />
				<lemis:texteditor property="score" label="�������" required="false"
						disable="true" value="<%=score %>"/>
			</tr>
			<tr>
				<lemis:texteditor property="coin" label="�ݶ������" required="false"
						disable="true" value="<%=coin %>"/>
				<lemis:texteditor property="coinnum" label="ʹ�ûݶ�������" required="false"
						disable="false" value="0" onkeyup="computeL2()"/>
				<lemis:texteditor property="changecoin" label="�ֿ��ֽ�(Ԫ)" required="false"
						disable="false" value="0"/>
			</tr>
			<tr>
					<lemis:texteditor property="change" label="����ȯ" required="false"
						disable="true" value="<%=change %>"/>
					<lemis:texteditor property="num" label="����ȯ�һ�����" required="false"
						disable="false" value="0" onkeyup="computeL1()"/>
					<lemis:texteditor property="money" label="ʵ�ս��" required="false"
						disable="false" />
			</tr>
				<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="sco" id="sco" value="<%=sco %>">
				<input type="hidden" name="fee" id="fee" value="<%=fee %>">
				<input type="hidden" name="rate" id="rate" value="<%=rate %>">
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="ʵ�����" disable="false" 
						required="true" /> --%>
	          
				<lemis:texteditor property="folnt" label="��ע" required="false"
					disable="false" colspan="3" maxlength="80" />
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


