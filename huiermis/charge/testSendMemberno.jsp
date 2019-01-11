<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String score=(String)request.getSession().getAttribute("score");
	String change=(String)request.getSession().getAttribute("change");
	String user=(String)request.getSession().getAttribute("user");
	String sco=(String)request.getSession().getAttribute("sco");
	String fee=(String)request.getSession().getAttribute("fee");
	String rate=(String)request.getSession().getAttribute("rate");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("收 费","saveData(document.forms[0])");
	//buttons.put("重 置","document.forms[0].reset();");//这个模块下公用的按钮
    pageContext.setAttribute("button", buttons);
    
%>

<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
	<script language="javascript">
	
		function saveData(obj){
			var score=$('input[name=score]').val();
			var sco=$('input[name=sco]').val();
			var num=$('input[name=num]').val();
			var mon=$('input[name=mon]').val();
			var money=$('input[name=money]').val();
			var fee=$('input[name=fee]').val();
			if(parseInt(num)!=num){
				alert("代金券数目输入非整数!");
				$("input[name=num]").val(0);
				return false;
			}
			if(score<(sco*num)){
				alert("积分不足");
				return false;
			}
			if(money!=(mon-fee*num)){
				alert("实付金额输入错误！");
				return false;
			}
			//提交前判断输入积分小于实有积分
			if(!checkValue(obj)){
				return false;
			}
			obj.submit();
		}
				
		function computeL() {
			var mon=$('input[name=mon]').val();
			var fee=$('input[name=fee]').val();
			var num=$('input[name=num]').val();
			var rate=$('input[name=rate]').val();
			var sum=num*fee;
			if((mon*rate/100)<sum){
				alert("优惠金额不得高于"+rate+"%");
				$("input[name=num]").val(0);
			}else{
				var money=mon-sum;
				$("input[name=money]").val(money);
			}
		}
	</script>
	<lemis:body>
		<lemis:base />
		<lemis:errors />
		<lemis:title title="惠耳积分测试接口" />
		<lemis:tabletitle title="test" />
		<table class="tableinput">
			<lemis:editorlayout />
			<html:form action="/ChargeAction.do?method=testCharge" method="POST">
				<tr>
			    	 <lemis:texteditor property="mon" label="应付金额" required="false"
						disable="false" />
					<lemis:texteditor property="user" label="用户编号" required="false"
						disable="true" value="<%=user %>"/>
					<lemis:texteditor property="score" label="积分" required="false"
						disable="true" value="<%=score %>"/>
				</tr>
				<tr>
					<lemis:texteditor property="num" label="代金券兑换数量" required="false"
						disable="false" value="0" onkeyup="computeL()"/>
					<lemis:texteditor property="change" label="代金券" required="false"
						disable="true" value="<%=change %>"/>
					 <lemis:texteditor property="money" label="实付金额" required="false"
						disable="false" />
				</tr>
				<input type="hidden" name="sco" id="sco" value="<%=sco %>">
				<input type="hidden" name="fee" id="fee" value="<%=fee %>">
				<input type="hidden" name="rate" id="rate" value="<%=rate %>">
			</html:form>
		</table>
		<lemis:buttons buttonMeta="button" />
		<lemis:bottom />
	</lemis:body>
</html>

