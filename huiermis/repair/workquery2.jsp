<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ page import="org.radf.manage.entity.Sc01"%>
<%@ page import="org.radf.plat.commons.ClassHelper"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
%>
<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script language="javascript">
$(document).ready(function(){
	var grCli="";
	grCli="<%=dto.getBsc011()%>";
	if(grCli=="HC0001"||grCli=="admin"||grCli=="HD0002"){
		
	}
	else{
		$("input[name=repregnames]").val("<%=dto.getBsc011()%>");
		$("input[name=repregnames]").attr("readonly","true");
		$("input[value=重置[R]]").bind("click",function(e){
		$("input[name=repregnames]").val("<%=dto.getBsc011()%>");
				
		}); 
	}
});
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="辅修工作量统计" />
	<lemis:tabletitle title="辅修工作量统计" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/RepairAction.do?method=worknum2" method="POST">
			<tr>
				<lemis:texteditor property="repregnames" required="false"
					label="辅修人员编号" disable="false" />
				<lemis:formateditor mask="date" format="true" property="repdate"
					label="时间从" required="true" disable="false" />
				<lemis:formateditor mask="date" format="true" property="repfdate"
					label="到" required="true" disable="false" />
			</tr>
		</html:form>
	</table>

	<div align="right"><input type="button" name="openwin"
		value="&nbsp;打&nbsp;&nbsp;印&nbsp;" class='buttonGray'
		onclick=
	print(document.forms[0]);;
> <input type="reset"
		name="Submit" value="&nbsp;重&nbsp;&nbsp;置&nbsp;" class='buttonGray'></div>
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>
