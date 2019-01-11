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
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("ncdpid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("pdtprc", "��Ʒ����"));
	header.add(new Formatter("ncdqnt", "�۳�����"));
	header.add(new Formatter("ncddis", "��Ʒ����"));
	header.add(new Formatter("ncdmon", "ʵ���շ�"));
	//header.add(new Formatter("ncdrecmon", "�˻����"));
	header.add(new Formatter("ncdnt", "��ע"));
	header.add(new Formatter("ncdsta", "״̬"));

	String phone=(String)request.getSession().getAttribute("phone");
	String user=(String)request.getSession().getAttribute("user");

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("�����Ա","saveData(document.forms[0])");
	buttons.put("����","history.back()");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ncdid", "�շѺ�"); 
	hidden.put("ncdpid", "��Ʒ����"); 
	hidden.put("ncdsta", "״̬"); 
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/BatBrandJson.js"></script>
<script src="/huiermis/js/BatTypeJson.js"></script>


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

<script language="javascript">
$(document).ready(function(){
	if("<%=user%>"!="null"){
		$("input[name=user]").val("<%=user%>");
	}
	$('input[name=user]').attr("disable",true);
	
	if("<%=phone%>"!="null"){
		$("input[name=phone]").val("<%=phone%>");
	}
	$('input[name=phone]').attr("disable",true);
});

function saveData(obj) {
	if (!checkValue(obj)) {
		return false;
	}

	var phone = $('input[name=phone]').val();
	
	if(phone.length!=11&&phone.length!=12){
		alert("��ǰ�û���ϵ��ʽ��������Ӧ����д��ȷ���ֻ����������ŵĹ̶��绰");
		$('input[name=phone]').attr("disable",false);
		$('input[name=phone]').val("");
		return false;
	}
	
  	var bm= $("select[name=bm]").val();
	var gby= $("input[name=gby]").val(); 
	
	if (bm!='0'&&gby=='0'){
		alert("���͵��������Ҫ����0");
		return false;
	}
	
	if (bm =='0'&&gby !='0'){
		alert("��ѡ�������ͺ�");
		return false;
	}
	
 	if(Number(gby)==0){
		alert("���͵������Ӧ�ô���0");
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
	if (confirm("ȷʵҪ��ӻ�Ա�����͵����")) {
	obj.submit();
	}
	else
		return t;
};

</script>

<lemis:body>
	<lemis:base />
    <lemis:title title="��ͨ��Ʒ��Ա��¼" />
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveNorMember"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<lemis:texteditor property="chgid" label="�շѺ�" disable="true" /> 
				<lemis:texteditor property="gctnm" label="��������" disable="true"/>
				<lemis:texteditor property="ictnm" label="�û�����" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="user" label="�û����" disable="false" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
				<lemis:texteditor property="phone" label="��ϵ��ʽ" disable="false"/>
				<lemis:formateditor required="false" property="chgdt" mask="date" label="�շ�����" format="true"/>
			</tr>
			<tr>
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
					<lemis:texteditor property="gby"  label="��¼����������ţ�" disable="false" 
						required="true" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
			</tr>
			 <tr>
					<input type="hidden" name="oldphone" id="oldphone" value="<%=phone %>">
			</tr>
		</html:form>
	</table>
	<lemis:table topic="��Ʒ��ϸ"
		action="#" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false" hiddenMeta="hidden"
		 batchInputType="update" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


