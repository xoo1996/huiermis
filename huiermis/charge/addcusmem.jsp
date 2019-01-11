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
	String user=(String)request.getSession().getAttribute("user");
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
<script src="/huiermis/js/BatBrandJson.js"></script>
<script src="/huiermis/js/BatTypeJson.js"></script>

<script language="javascript">
$(document).ready(function(){
	if("<%=phone%>"!="null"){
		$("input[name=phone]").val("<%=phone%>");
	}
	$('input[name=phone]').attr("disable",true);
});
function saveData(obj) {
	var gby=$('input[name=gby]').val();
	var bm=$('select[name=bm]').val();
	
	var phone = $('input[name=phone]').val();
	
	if(phone.length!=11&&phone.length!=12){
		alert("��ǰ�û���ϵ��ʽ��������Ӧ����д��ȷ���ֻ����������ŵĹ̶��绰");
		$('input[name=phone]').attr("disable",false);
		$('input[name=phone]').val("");
		return false;
	}
	
	if(gby!="0"&&bm=="0"){
		alert("����ͺ�δ��д");
		return false;
	}
	
	if(gby=="0"&&bm!="0"){
		alert("�����������Ϊ0");
		return false;
	}
	if(Number(gby)>240){
		alert("����������ܳ���240��");
		return false;
	}
	/*if(Number(gby)%6!=0){
		alert("�������ֻ����6�ı���");
		return false;
	}*/
	if (!checkValue(obj)) {
		return false;
	}
	if (confirm("ȷʵҪ���͵����")) {
		obj.submit();
	}
	else
		return t;
};
</script>

<script>
	$(document).ready(function(){
		$("select[name=bm]").val('0');
	});
</script>
 <script type="text/javascript">
 //��ؼ���
        $(function () {
            $.each(batbrand, function (k, p) { 
                var option = "<option value='" + p.braID + ',' + p.braName +"'>" + p.braName + "</option>";
                $("#batbra").append(option);
            });
            $("select[name=batbra]").val("0,��");
            if($("select[name=batbra]").val()=="0,��"){
            	var option = "<option value='0'>��</option>";
            	$("#bm").append(option);
            	$("select[name=bm]").val("0");
            }
            $("#batbra").change(function () {
                var selValue = $(this).val().split(","); 
                $("#bm option:gt(0)").remove();
                if(selValue[0] == "0"){
                	$.each(battype, function (k, p) { 
                        if (p.braID == selValue[0]) {
                            var option = "<option value='" + p.battype +"'>" + p.batName + "</option>";
                            $("#bm").append(option);
                        }
                    });
                	$("select[name=bm]").val("0");
                }else if(selValue[0]!=""){
                	$.each(battype, function (k, p) { 
                        if (p.braID == selValue[0]) {
                            var option = "<option value='" + p.battype +"'>" + p.batName + "</option>";
                            $("#bm").append(option);
                        }
                    });
                }
                
            });
        });
    </script>



<lemis:body>
	<lemis:base />
    <lemis:title title="��Ա��¼" />
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveCusMember"
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
			</tr>
			<tr>
				
				<lemis:texteditor property="pdtnm" label="���ƻ��ͺ�" disable="true"
					required="false"/>
				<html:hidden property="pdtid"/>
				<lemis:texteditor property="ictpcd" label="�ʱ�" required="false"
					disable="true" maxlength="10" />
				<lemis:texteditor property="phone" label="��ϵ�绰" required="false"
					disable="false" maxlength="20" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
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
				<lemis:texteditor property="balance"  label="ʵ�����" disable="true" 
						required="true" />
			</tr>
             <tr>
					<input type="hidden" name="fdtcltid" id="fdtcltid" value="<%=user %>">
					<input type="hidden" name="oldphone" id="oldphone" value="<%=phone %>">
			</tr>
			
			<tr>
			<!-- ���� -->
					<td>
					<font color='#FF0000'>*</font>���͵��Ʒ��</td>
					<td colspan="1">
					<select label='���͵��Ʒ��' style='font-size:12px' name='batbra' id='batbra'
					class='select'  required='true'><option
						value='' selected> ��ѡ��</option></select>
					</td>
					
					<td><font color='#FF0000'>*</font>���͵���ͺ�</td>
					<td colspan="1">
					<select label='���͵���ͺ�' style='font-size:12px' name='bm' id='bm'
					class='select'  required='true'><option
						value='' selected> ��ѡ��</option></select>
					</td>
					<lemis:texteditor property="gby" label="��¼����������ţ�" disable="false" 
						required="true" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
						
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


