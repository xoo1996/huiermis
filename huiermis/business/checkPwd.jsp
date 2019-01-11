<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link rel="stylesheet" type="text/css" href="/huiermis/css/jquery.autocomplete.css" />
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<style type="text/css">
 .container {
 	max-width: 700px;
  }
  .inputContainer {
   	
  	margin: 0 auto;
  	margin-left: 150px;
  	margin-top: 80px;
  }
  .container {
  	font-size: 20px;
  }
  
  .container input {
  	line-height: 20px;
  	height: 20px;
  	width: 200px;
  }
  .container button {
  	font-size: 16px;
  }

</style>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<%
String password = (String)request.getSession().getAttribute("password");
%>
<script language="javascript">
	function check(){
		var password = <%=password%>;
		//alert(password);
		var pwd=document.getElementById("pwd").value;
		//alert(pwd);
		if(pwd==password){
			window.location.href = "/" + lemis.WEB_APP_NAME+"/business/BusinessAction.do?method=enterJM&menuId=JMxiaozhangQuery&";
		}else{
			alert("√‹¬Î¥ÌŒÛ!");
		}
	}
</script>
<lemis:base />
<body>
<div class="container">
<div class="inputContainer">
<span>≤È—Ø√‹¬Î£∫</span><input type="password" id="pwd" />	&nbsp;<button onclick="check()">»∑∂®</button>
</div>
</div>
</body>
<lemis:body>
</lemis:body>
</html>
