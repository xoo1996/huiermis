<%@ page contentType="text/html; charset=GBK"  %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html>
<head>
<title>欢迎登陆惠耳用户信息管理平台</title>
<link href="<html:rewrite forward='css'/>" rel="stylesheet" type="text/css">
<script src="<html:rewrite forward='md5'/>"> </script>
<script src="<html:rewrite forward='globals'/>"> </script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>

<script language="javascript">
<!--
//设置页面元素的CSS
//eleName 页面元素名称 
//className 要切换的CSS名称
function setClass(eleName,clsName) {
	document.all(eleName).className = clsName;
}
var topFrame = top.document.all("topFrame");
if(topFrame){
 // topFrame.src = "/" + lemis.WEB_APP_NAME + "/Top.jsp";
}
//-->
</script>

<script language="JavaScript">
function init(){
	  
	document.all("bsc011").focus();
	 if(window.parent.length>1){ 
        window.parent.location="/" + lemis.WEB_APP_NAME + "/logonDialog.jsp"; 
    }
}

function onLogin() {
  document.logonForm.bsc013.value = hex_md5(document.logonForm.bsc013.value);
  getMD5HMAC();
  
  
}

function checkKey()
{
  if(13 == window.event.keyCode){
    onLogin();
  }
}
document.onkeydown=checkKey;



</script></head>
<%
	String message=(String)request.getAttribute("errorMsg");

	if(message==null){
	message="&nbsp;";
	
}else{
	out.println("<script language=javascript>\n alert("
			+ "'" + message.trim() + "'"
			+ ");;history.back();</script>");
}

%>
<OBJECT id="ePass"  name="ePass" height="10" width="10" classid="clsid:C7672410-309E-4318-8B34-016EE77D6B58"  codebase="/ftd/cab/epass1000ND/install.cab" >
</OBJECT>
<Script Language="VBScript">
	'-----------------------------------------------------------------
	'-----------------------------------------------------------------
	' IE SPECIFIC:
	' Test to make sure ePass1000nd private interface loaded properly by calling a method on it.
	' For best results, the method we call should only be available in the 
	' most recent version of the control, however any method will detect
	' failure to create the object.
	Function ConfirmXEnrollLoaded()
		On Error Resume Next
		ePass.GetLibVersion
		If Err.number = &H1B6 Then
			ConfirmXEnrollLoaded = false
	        Exit function
	    end if
	    ConfirmXEnrollLoaded = true
		
	End Function
</Script>
<script Language=JavaScript>

{
	var nResult = ConfirmXEnrollLoaded();
	if (nResult == true) {
	}
	else
	{	if(confirm("尚未安装Ukey控件是否安装？")){
		document.location.href = '/huiermis/epass1000nd/welcome.html';
		}
		
	}	
}
</script>
<script language="javascript" type="text/javascript" ></script> 
<script type="text/javascript">
/**
* 获取计算结果
*pName   初始化的时候的项目名称
*userPIN 用户PIN码
*data    待计算的原文
*
**/
function  getHashToken()
{
		var pName = "huier";
		var obj=ePass;

		//打开设备
		try{
		ePass.OpenDevice(1,"");	
		}catch(e){
			//alert("检测Ukey失败，请检查是否插好！");
			return "0";
		}
		//读取设备序列号
		var snID = ePass.GetStrProperty(7,0,0);	
		$("#ukeysn").val(snID);

		try
		{
				ePass.VerifyPIN(0,"1234");
//				alert("验证Pin成功");
		}
		catch(e)
		{
				alert("验证pin失败");	
				return "0";
		}
		//生成随机数
		var rand=ePass.GenRandom(0);
		$("#rand").val(rand);

		/**
		*
		*计算MD5_HMAC
		* 前提需要key里面需要个人化信息
		**/	
		try
		{
				/*
				* 根据名称获取：0x0300
				*0x300   768
				*/
				ePass.ChangeDir(0x0300,0,pName);//改变目录    
		}
		catch(e)
		{
  				alert("Ukey尚未初始化，请先初始化");	
				return "0";
		}
		 
		ePass.OpenFile(0,1);//打开key里的文件夹
		
		// 1  代表初始化MD5
		var  lFlags=1;
		//第二个文件的ID,第一个文件为当前打开的文件
		var  lRefData=2;
		var s=ePass.HashToken(1,2,rand);

		//关闭设备
		ePass.CloseDevice();
		
		return s;
}	
/**
*
*前端计算结果
**/
function  getMD5HMAC()
{
	var pName=document.all("bsc011").value;	
	var s ="";
	
	s=getHashToken();
	
	$("#clientResult").val(s);
	
	document.logonForm.action='<html:rewrite page="/logonAction.do?method=userLogin"/>';
	document.logonForm.submit();
	
}
function myBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1){
  return "Chrome";
 }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //判断是否IE浏览器
}

//跳转到人事系统
function toHR(){
	//window.open("http://10.0.0.249:8888/huiermis");
	window.open("http://localhost:8891/huierhr");
}
function toHY(){	
	var mb = myBrowser();
	//window.open("http://10.0.0.249:8888/huiermis");
	if ("IE" != mb) {
		window.open("http://localhost:8890/huiermall");
	}
	else{
		window.open("http://localhost:8989/huiermis/HYRedirect.html");
		//response.sendRedirect("HYRedirect.html"); 
	}
}
</script>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<body onload="init();">
<html:form action="/logonAction.do?method=userLogin" method="post">
<table height="341" border="0" align="center" cellspacing="0" style="width:563px;text-align:center;background-image:url('images/login_new2.jpg')">
  <tr>
    <td width="563" align="left" valign="top">
      <table width="100%" height="220" border="0" cellspacing="0" style="background-color:transparent">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color:transparent">
        <tr>
          <td width="39%">&nbsp;</td>
          <td width="9%" class="LoginBodyFontSize">用户名：</td>
          <td width="29%" height="30"><input name="bsc011" type="text" size="21" style="width:160;height:20" value=""></td>
          <td width="23%">&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td class="LoginBodyFontSize">密&nbsp;&nbsp;码：</td>
          <td ><input name="bsc013" type="password" size="21" style="width:160;height:20" value=""></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td height="50"><table width="144" height="21" border="0" cellpadding="0" cellspacing="0" style="background-color:transparent;text-align:center">
              <tr>
                <td width="62" style="text-align:center" valign="bottom" class="ButtonA" id="hhh" onMouseOver="setClass('hhh','ButtonA1')" onMouseOut="setClass('hhh','ButtonA')">
                  <a onclick="onLogin();" class="BLink" style="cursor:pointer" >  登 录</a></td>
                <td width="14"></td>
                <td width="62" style="text-align:center" valign="bottom" class="ButtonA" id="ggg" onMouseOver="setClass('ggg','ButtonA1')" onMouseOut="setClass('ggg','ButtonA')">
                  <a onclick="window.close();" class="BLink" style="cursor:pointer" >  退  出 </a></td>
                  <td width="12"></td>
                 <td> <input type="hidden" id="clientResult" name="clientResult"></td>
                 <td> <input type="hidden" id="ukeysn" name="ukeysn"></td>
                 <td> <input type="hidden" id="rand" name="rand"></td>
                 <td> <input type="hidden" id="aae101" name="aae101"></td>
              </tr>
            </table></td>
         <td>
         	<input type="button" id="bbb" value="人事系统"  onclick="toHR()" class="ButtonA" onMouseOver="setClass('bbb','ButtonA1')" onMouseOut="setClass('bbb','ButtonA')" style="background-color:transparent;border: none;width: 68;height: 22;margin-left: 45px;margin-top: 5px" />  
         	<input type="button" id="vipsystem" value="会员系统"  onclick="toHY()" class="ButtonA" onMouseOver="setClass('vipsystem','ButtonA1')" onMouseOut="setClass('vipsystem','ButtonA')" style="background-color:transparent;border: none;width: 68;height: 22;margin-left: 45px;margin-top: 5px" />
         </td>
        </tr>
      </table> </td>
  </tr>
</table>
</html:form>
<lemis:errors />
</body>
</html>

