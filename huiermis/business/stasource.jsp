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
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
	buttons.put("�� ��","document.forms[0].reset()");
	
    pageContext.setAttribute("button", buttons);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//$("select[name=folurgischg]").val("0");
		//var mon=$('input[name=balance]').val();
		//$("input[name=money]").val(parseInt(mon));
	});
	function saveData(obj) {
		if (confirm("ȷʵҪ�ύ��")) {
			obj.submit();
		}
	};
	
	</script>

	<script>
	function computeL1() {
		var webcvt=$('input[name=webcvt]').val();//gai
		var weblocal=$('input[name=weblocal]').val();
		var sum=parseFloat(webcvt)+parseFloat(weblocal);
		sum = sum.toFixed(2);//������λ��Ч����
		$("input[name=webtotal]").val(parseFloat(sum));
	}
	function computeL2() {
		var webmoney=$('input[name=webmoney]').val();//gai
		var webtotal=$('input[name=webtotal]').val();
		var sum=parseFloat(webtotal)/parseFloat(webmoney);
		sum = sum.toFixed(2);//������λ��Ч����
		$("input[name=webrate]").val(parseFloat(sum));
	}
</script>
<lemis:body>
<lemis:base />
    <lemis:title title="����������Դ����ͳ��" />
	<lemis:tabletitle title="����������Դ��Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=staSource" method="POST">
			<tr>
				<lemis:texteditor  property="webyear" label="���" disable="false" required="true"  />	
				<lemis:texteditor property="webmonth" label="�·�" disable="false" required="true" />
			</tr>
			<tr>
				<lemis:texteditor  property="websource" label="������Դ����" disable="false" required="true"  />	
				<lemis:texteditor property="websell" label="������������" disable="false" required="true" />
				<lemis:texteditor  property="webnum" label="��������̨��" disable="false" required="true"  />				
			</tr>
			<tr>
				<lemis:texteditor  property="webcvt" label="�ܲ�ת�����۶�" disable="false" required="true"  />	
				<lemis:texteditor property="weblocal" label="�ط��������۶�" disable="false" required="true" />
				<lemis:texteditor  property="webtotal" label="�ϼ��������۶�" disable="false" required="true" onclick="computeL1()" />	
			</tr>
			<tr>
				<lemis:texteditor  property="webrate" label="��������ռ�������۶�ٷֱ�" disable="false" required="false" />				
				<lemis:codelisteditor type="webmark" isSelect="true" label="��ͼ��ע" redisplay="true" required="true" />
				<lemis:texteditor  property="webcheck" label="�����Ǽ�" disable="false" required="true"  />	
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>