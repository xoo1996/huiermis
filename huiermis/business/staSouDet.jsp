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
    <lemis:title title="网络销售来源统计" />
	<lemis:tabletitle title="网络销售来源信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/BusinessAction.do?method=staSource" method="POST">
			<tr>
				<lemis:texteditor  property="webyear" label="年份" disable="true" required="true"  />	
				<lemis:texteditor property="webmonth" label="月份" disable="true" required="true" />
			</tr>
			<tr>
				<lemis:texteditor  property="websource" label="网络来源人数" disable="true" required="true"  />	
				<lemis:texteditor property="websell" label="网络销售人数" disable="true" required="true" />
				<lemis:texteditor  property="webnum" label="网络销售台数" disable="true" required="true"  />				
			</tr>
			<tr>
				<lemis:texteditor  property="webcvt" label="总部转诊" disable="true" required="true"  />	
				<lemis:texteditor property="weblocal" label="地方网络" disable="true" required="true" />
				<lemis:texteditor  property="webtotal" label="合计网络销售" disable="true" required="true"  />	
			</tr>
			<tr>
				<lemis:texteditor  property="webrate" label="占当月销售额百分比" disable="true" required="true"  />				
				<lemis:codelisteditor type="webmark" isSelect="false" label="地图标注" redisplay="true" required="true" />
				<lemis:texteditor  property="webcheck" label="发帖登记" disable="true" required="true"  />	
			</tr>
		</html:form>
	</table>
	<lemis:bottom />
</lemis:body>
</html>