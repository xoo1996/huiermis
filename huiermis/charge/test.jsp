<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("发送会员号","saveData(document.forms[0])");
	//buttons.put("重 置","document.forms[0].reset();");//这个模块下公用的按钮
    pageContext.setAttribute("button", buttons);
    
%>

<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
	<script language="javascript">
		function saveData(obj){
			if(!checkValue(obj)){
				return false;
			}
			obj.submit();
		}
				
	</script>
	<lemis:body>
		<lemis:base />
		<lemis:errors />
		<lemis:title title="惠耳积分测试接口" />
		<lemis:tabletitle title="test" />
		<table class="tableinput">
			<html:form action="/ChargeAction.do?method=testws" method="POST">
				<tr>
					<lemis:texteditor property="user" label="用户编号" required="false"
						disable="false" />
				</tr>
				
			</html:form>
		</table>
		<lemis:buttons buttonMeta="button" />
		<lemis:bottom />
	</lemis:body>
</html>

