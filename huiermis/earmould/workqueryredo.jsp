<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script language="javascript">
	function print(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="耳模工作量统计" />
	<lemis:tabletitle title="耳模工作量统计" />
	<table class="TableInput">
		<lemis:editorlayout />
		<html:form action="/EarMouldAction.do?method=worknum&qe=earredo" method="POST">
			<tr>
				<lemis:texteditor property="tmemaker" required="false"
					label="制作人员编号" disable="false" />
				<lemis:formateditor mask="date" format="true" property="tmepdt"
					label="时间从" required="true" disable="false" />
				<lemis:formateditor mask="date" format="true" property="tmefmdt"
					label="到" required="true" disable="false" />
			</tr>
		</html:form>
	</table>
	<br />
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
