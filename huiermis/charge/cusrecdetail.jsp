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
	String num=(String)request.getSession().getAttribute("num");
	String type=(String)request.getSession().getAttribute("type");
	String bi=(String)request.getSession().getAttribute("bi");
	
	if(type==null||type.equals("")){
		type = "0";
	}
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�˻���ֱ����","saveData(document.forms[0])");
    buttons.put("�˻����ܲ�","commit(document.forms[0])");
    buttons.put("��ӡ","print(document.forms[0])");
	buttons.put("�� ��","history.back()");
	
	//List<Editor> editors = new ArrayList<Editor>();
	//editors.add(new Editor("text", "folno", "������"));

	 //pageContext.setAttribute("editors", editors);
    pageContext.setAttribute("button", buttons);
    
    Map<String, String> hidden = new LinkedHashMap<String, String>();
    hidden.put("folno","������");
   	hidden.put("folsta","����״̬");
%>


<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script>
 	$(document).ready(function(){
		if("<%=bi%>"=="t"){
			$("#bi").css('display','block'); 
		}
	});
</script>
<script language="javascript">
	$(document).ready(function(){
		$("select[name=folischg]").val("1");
		var fdtrecmon=$("input[name=fdtrecmon]").val();
		if(fdtrecmon=='')
		{
			$("input[name=fdtrecmon]").attr("disable","disable");
		}
	});
	$(document).ready(function(){
		$("select[name=bm]").val('<%=type%>');
	});
	
	$(document).ready(function(){
<%-- 		if(<%=num %> == "0")
		$("input[name=rebatnum]").val('<%=num%>'); --%>
		$("input[name=rebatnum]").val('0');
	});
	
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		var zyer= $("input[name=fdtmklr]").val();
		if(zyer != '��' && zyer != '' && zyer != null){
			if(!confirm("�˻����ѣ��ö��������Ҷ����Ϊ '"+zyer+" '���Ƿ������")){
				return false;
			}
		}
		
		var rebatnum= $("input[name=rebatnum]").val();
		var bm= $("select[name=bm]").val();
		
		
		if(bm !="<%=type%>"){
			alert("����ͺ��������ͺŲ���");
			return false;
		}
		
		if(parseInt(rebatnum) > <%=num %>){
			alert("�˻�ʵ������������������");
			return false;
		}
		
		if(parseInt(rebatnum) < 0){
			alert("�˻�ʵ����������Ϊ����");
			return false;
		}
			
		if (confirm("ȷʵҪ�˻���ֱ������")) {
			obj.action='<html:rewrite href="/huiermis/charge/ChargeAction.do?method=saveCustomizedRec&tp=s&"/>';
			obj.submit();
		}
		//�������ύ����̨ ע�⣺getAlldata(document.all.tableform)�ǵõ�����Ҫ�ύ�����ݡ�
		else
			return t;
		
	};
	
	function commit(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		var zyer= $("input[name=fdtmklr]").val();
		if(zyer != '��' && zyer != '' && zyer != null){
			if(!confirm("�˻����ѣ��ö��������Ҷ����Ϊ '"+zyer+" '���Ƿ������")){
				return false;
			}
		}
		if (confirm("ȷʵҪ�˻����ܲ���")) {
			obj.action='<html:rewrite href="/huiermis/charge/ChargeAction.do?method=saveCustomizedRec&tp=c&"/>';
			obj.submit();
		}
		//�������ύ����̨ ע�⣺getAlldata(document.all.tableform)�ǵõ�����Ҫ�ύ�����ݡ�
		else
			return t;
		
	};
	
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.action='<html:rewrite href="/huiermis/charge/ChargeAction.do?method=printCusRec"/>';
		obj.submit();
	};

</script>



<lemis:body>
	<lemis:base />
    <lemis:title title="���ƻ��˻��շ�����" />
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveCustomizedCharge"
			method="POST">
			<html:hidden property="folno" />
			<tr>
				<lemis:texteditor  property="folno" label="������" disable="true" required="true" 
					 maxlength="6" />
	
				<lemis:codelisteditor type="foltype" label="��������" isSelect="false"></lemis:codelisteditor>
				<lemis:texteditor property="folctnm" label="���Ƶ�λ" disable="true"
					required="true" />
				<html:hidden property="folctid"/>
				<html:hidden property="fdtmklr"/>
			</tr>
			<tr>
			
				<lemis:texteditor property="folischg"  label="�Ƿ����շ�" disable="true"
					 required="true" />
				<lemis:texteditor property="cltnm" label="�û�����" disable="true"
					 required="true" />
				<lemis:texteditor property="ictgender" label="�Ա�" disable="true"
					 required="true"/>
			</tr>
			<tr>
				
				
				<lemis:texteditor property="ictpcd" label="�ʱ�" required="false"
					disable="true" maxlength="10" />
				<lemis:texteditor property="icttel" label="��ϵ�绰" required="false"
					disable="true" maxlength="20" />
					<lemis:texteditor property="ictaddr" label="�û���ַ" disable="true"
					required="false" colspan="3" maxlength="80" />
					
			</tr>
			<tr>
				<html:hidden property="pdtid" />
			    <lemis:texteditor property="pdtnm" label="���ƻ��ͺ�" disable="true"
					required="false"/>
				<td>�˻�����</td>
				<td><lemis:operdate/></td>
				<lemis:formateditor required="false" property="chgdt" mask="date" format="true" label="�շ�����"/>
				
			</tr>
			
			<tr>
			    <lemis:codelisteditor type="folsta" label="״̬" isSelect="false"/>
				<lemis:texteditor property="pdtprc" label="ԭ��" disable="true"
						required="false"  />
				<lemis:texteditor property="discount" label="����" disable="true"
						required="false"  />
				
			</tr>
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="ʵ�����" disable="false" 
						required="true" /> --%>
				<lemis:texteditor property="deposit" label="����" required="true"
					disable="true" maxlength="30" />
				<lemis:texteditor property="folurgfee" label="�Ӽ���" required="true"
					disable="true" maxlength="30" />
				<lemis:texteditor property="xubaofee"  label="������" disable="true" 
						required="true" />	
						
			</tr>
			<tr>
			<lemis:texteditor property="balance"  label="ʵ�����" disable="true" 
						required="true" />	
			<lemis:texteditor  property="fdtmklr" label="���Ҷ�" disable="true" required="false" 
					 maxlength="6" />
			<lemis:texteditor property="fdtrecmon"  label="�˻����" disable="false" 
						required="true"/>
		
			
			</tr>
			<tr id="bi" style="display:none">
			<lemis:codelisteditor type="bm" isSelect="true" label="����ͺ�"
					redisplay="false" required="true" />
			<lemis:texteditor property="rebatnum" label="�˻�ʵ�����������ͣ�" required="true"
					disable="false" maxlength="5" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>		
			<lemis:texteditor property="num"  label="��������" disable="true" 
						required="true" value="<%=num %>"/>	
			</tr>
			<tr>
			<lemis:texteditor property="folnt" label="��ע" required="true"
					disable="false" colspan="3" maxlength="80" />
			<lemis:codelisteditor type="foldreason" isSelect="true" label="�˻�ԭ��"
					redisplay="false" required="true" />
				<html:hidden property="folsta"/>
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
		<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


