<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<lemis:base />
<lemis:body>
    <lemis:title title="����������Դͳ��" />
	<lemis:tabletitle title="����������Դ��Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=staSource" method="POST">
			<tr>
				<lemis:texteditor  property="webyear" label="���" disable="true" required="true"  />	
				<lemis:texteditor property="webmonth" label="�·�" disable="true" required="true" />
			</tr>
			<tr>
				<lemis:texteditor  property="websource" label="������Դ����" disable="true" required="true"  />	
				<lemis:texteditor property="websell" label="������������" disable="true" required="true" />
				<lemis:texteditor  property="webnum" label="��������̨��" disable="true" required="true"  />				
			</tr>
			<tr>
				<lemis:texteditor  property="webcvt" label="�ܲ�ת��" disable="true" required="true"  />	
				<lemis:texteditor property="weblocal" label="�ط�����" disable="true" required="true" />
				<lemis:texteditor  property="webtotal" label="�ϼ���������" disable="true" required="true"  />	
			</tr>
			<tr>
				<lemis:texteditor  property="webrate" label="ռ�������۶�ٷֱ�" disable="true" required="true"  />				
				<lemis:codelisteditor type="webmark" isSelect="false" label="��ͼ��ע" redisplay="true" required="true" />
				<lemis:texteditor  property="webcheck" label="�����Ǽ�" disable="true" required="true"  />	
			</tr>
		</html:form>
	</table>
	<lemis:bottom />
</lemis:body>
</html>