<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@page import="org.radf.plat.taglib.Editor"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
    buttons.put("�� ��","document.forms[0].reset();");
    buttons.put("����","window.history.back();");
    
    List<Editor> editors = new ArrayList<Editor>();
	pageContext.setAttribute("editor",editors);
    pageContext.setAttribute("button", buttons);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>

<script type="text/javascript">
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};

	$(document).ready( function() {
		autoin();
       var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		
		$("input[name=feegctid]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=feegctid]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=feegctid]").val(gid);
				$(this).parent().next().find("input").val(gid);
			}
		});
	});
	
 	function autoin () {
		var m = $("input[name=feesales]").val();
		var mon = m*0.05;
		$("input[name=feemanage]").val(mon);
	}; 
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="�޸Ŀͻ�������Ϣ" />
	<lemis:tabletitle title="�޸ķ�����Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		  <html:form action="/FeeAction.do?method=saveModified" method="POST">
		
				<tr>
				<lemis:texteditor property="feegctid" label="�ͻ�����" disable="true"  />
				<lemis:texteditor property="feegctname" label="�ͻ�����" disable="true" />
				<lemis:texteditor property="feeyear" label="��" disable="true" />
				</tr>
			<tr>
				<lemis:texteditor property="feemonth" label="�·�" disable="true" 
				 required="true" />
				 <lemis:texteditor property="feesales" label="���۶�" disable="false" 
				 required="true" />
				 <lemis:texteditor property="feemanage" label="�����" disable="false" 
				 required="true" onclick="autoin()" />
                <%-- <lemis:texteditor required="false" property="feesales" 
                label="���۶�" disable="false" />
			    <lemis:texteditor required="false" property="feemanage"
				 label="�����" disable="false" onclick="autoin()" /> --%>
			</tr>
			<tr>
			    <lemis:texteditor required="false" property="feeinvoice" 
			    label="��Ʊ˰" disable="false" />
				<%--  <lemis:texteditor required="false" property="feerent" 
			    label="����" disable="false" /> --%>
			     <%-- <lemis:texteditor required="false" property="feedevice"
				 label="�豸̯��" disable="false" />  --%>
			 
				<lemis:texteditor required="false" property="feetax" 
				label="˰��" disable="false" />
				<lemis:texteditor required="false" property="feepension"
				 label="�籣��������" disable="false" />
			  <%--  <lemis:texteditor required="false" property="feeassets" 
			   label="�̶��ʲ�" disable="false" /> --%>
			</tr>
			<tr>
			<%-- <lemis:texteditor required="false" property="feehousingfund"
				 label="������" disable="false" /> --%>
			    <lemis:texteditor required="true" property="feepay" 
			    label="����" disable="false" />
			    <lemis:texteditor required="false" property="feeaccount"
				 label="��ƹ���" disable="false" />
				   <lemis:texteditor required="false" property="feeprocess"
				 label="������" disable="false" />
		   </tr>
		    <%-- <tr>
			<lemis:texteditor required="false" property="fee1othercharges"
				 label="����(���ɿ�)" disable="false" />
			  <lemis:texteditor required="false" property="feebuilt" 
			     label="װ�޷�" disable="false" />
				<lemis:texteditor required="false" property="feeopen" 
				label="�����" disable="false" /> 
			
			<lemis:texteditor required="false" property="feetrans" 
				label="ת�÷�" disable="false" /> 
			     </tr> --%>
			<tr>
				<lemis:texteditor required="false" property="feead" 
				label="����" disable="false" />
			   <lemis:texteditor required="false" property="feephone" 
			   label="�绰��" disable="false" />
			    <lemis:texteditor required="false" property="feewater"
			     label="ˮ���" disable="false" />
			</tr>
			<tr>
			<lemis:texteditor required="false" property="feetrip" 
				label="���÷�" disable="false" />
				<lemis:texteditor required="false" property="feepostage"
				 label="�ʷ�" disable="false" />
				<lemis:texteditor required="false" property="feeoffice" 
				label="�칫��" disable="false" />
			</tr>
		    <tr>
		    <lemis:texteditor required="false" property="feesocial" 
			     label="���ط�" disable="false" />					
			   	<lemis:texteditor required="false" property="feothersa"
				 label="��������" disable="false" />
			    <lemis:texteditor required="false" property="zbfee"
				 label="�ܲ�����" disable="false" />
		    </tr>
		    <tr>
		    <%-- <lemis:texteditor required="false" property="feothersa"
				 label="����(�ɿ�)" disable="false" /> --%>
				  <lemis:texteditor required="false" property="feepromotion"
				 label="����" disable="false" />
		  		 <lemis:texteditor required="false" property="feeback"
				 label="�˻���" disable="false" />
			</tr>
			<tr>
				 <%-- <lemis:texteditor property="bsc012" label="¼��Ա" disable="true" required="false" /> --%>
				<lemis:formateditor mask="date" property="feeopdata" label="¼��ʱ��" disable="true" 
				required="false" />
			</tr>
	</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
