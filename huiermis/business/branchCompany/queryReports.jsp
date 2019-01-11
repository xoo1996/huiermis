<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	$(document).ready(function(){
		var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		
		$("input[name=gctid]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=gctid]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=gctid]").val(gid);
				$(this).parent().next().find("input").val(gid);
			}
		});
	});
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="�ֹ�˾�����Ϣ��" />
	 <lemis:tabletitle title="�����ֹ�˾�����Ϣ��" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/BranchFinancialAction.do?method=exportReport" method="POST">
			<tr>
				<lemis:texteditor property="startyear" required="true" label="��ʼ���"
					disable="false" maxlength="4" />
				<lemis:texteditor property="startmonth" required="true" label="��ʼ�·�"
					disable="false" maxlength="4" />
			
			
				<lemis:texteditor property="endyear" required="true" label="�������"
					disable="false" maxlength="4" />
			</tr>
			<tr>
				<lemis:texteditor property="endmonth" required="true" label="�����·�"
					disable="false" maxlength="4" />
			
				<lemis:texteditor property="gctid" required="false" label="�ͻ�����"
					disable="false" maxlength="5" />
				<lemis:codelisteditor type="gctarea" isSelect="true" label="��������"
					redisplay="true" required="false" />
			</tr>
		</html:form>
	</table>
	<div align="right">
		<input type="button" name="openwin" value="&nbsp;��&nbsp;&nbsp;��&nbsp;" class='buttonGray' onclick=print(document.forms[0]);;>
		<input type="reset" name="Submit" value="&nbsp;��&nbsp;&nbsp;��&nbsp;" class='buttonGray'>
	</div>
	<lemis:errors />
	<lemis:bottom/>
</lemis:body>
</html>