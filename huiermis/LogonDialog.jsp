<%@ page contentType="text/html; charset=GBK"  %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html>
<head>
<title>��ӭ��½�ݶ��û���Ϣ����ƽ̨</title>
<link href="<html:rewrite forward='css'/>" rel="stylesheet" type="text/css">
<script src="<html:rewrite forward='md5'/>"> </script>
<script src="<html:rewrite forward='globals'/>"> </script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>

<script language="javascript">
<!--
//����ҳ��Ԫ�ص�CSS
//eleName ҳ��Ԫ������ 
//className Ҫ�л���CSS����
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
	{	if(confirm("��δ��װUkey�ؼ��Ƿ�װ��")){
		document.location.href = '/huiermis/epass1000nd/welcome.html';
		}
		
	}	
}
</script>
<script language="javascript" type="text/javascript" ></script> 
<script type="text/javascript">
/**
* ��ȡ������
*pName   ��ʼ����ʱ�����Ŀ����
*userPIN �û�PIN��
*data    �������ԭ��
*
**/
function  getHashToken()
{
		var pName = "huier";
		var obj=ePass;

		//���豸
		try{
		ePass.OpenDevice(1,"");	
		}catch(e){
			//alert("���Ukeyʧ�ܣ������Ƿ��ã�");
			return "0";
		}
		//��ȡ�豸���к�
		var snID = ePass.GetStrProperty(7,0,0);	
		$("#ukeysn").val(snID);

		try
		{
				ePass.VerifyPIN(0,"1234");
//				alert("��֤Pin�ɹ�");
		}
		catch(e)
		{
				alert("��֤pinʧ��");	
				return "0";
		}
		//���������
		var rand=ePass.GenRandom(0);
		$("#rand").val(rand);

		/**
		*
		*����MD5_HMAC
		* ǰ����Ҫkey������Ҫ���˻���Ϣ
		**/	
		try
		{
				/*
				* �������ƻ�ȡ��0x0300
				*0x300   768
				*/
				ePass.ChangeDir(0x0300,0,pName);//�ı�Ŀ¼    
		}
		catch(e)
		{
  				alert("Ukey��δ��ʼ�������ȳ�ʼ��");	
				return "0";
		}
		 
		ePass.OpenFile(0,1);//��key����ļ���
		
		// 1  �����ʼ��MD5
		var  lFlags=1;
		//�ڶ����ļ���ID,��һ���ļ�Ϊ��ǰ�򿪵��ļ�
		var  lRefData=2;
		var s=ePass.HashToken(1,2,rand);

		//�ر��豸
		ePass.CloseDevice();
		
		return s;
}	
/**
*
*ǰ�˼�����
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
    var userAgent = navigator.userAgent; //ȡ���������userAgent�ַ���
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //�ж��Ƿ�Opera�����
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //�ж��Ƿ�Firefox�����
    if (userAgent.indexOf("Chrome") > -1){
  return "Chrome";
 }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //�ж��Ƿ�Safari�����
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //�ж��Ƿ�IE�����
}

//��ת������ϵͳ
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
          <td width="9%" class="LoginBodyFontSize">�û�����</td>
          <td width="29%" height="30"><input name="bsc011" type="text" size="21" style="width:160;height:20" value=""></td>
          <td width="23%">&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td class="LoginBodyFontSize">��&nbsp;&nbsp;�룺</td>
          <td ><input name="bsc013" type="password" size="21" style="width:160;height:20" value=""></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td height="50"><table width="144" height="21" border="0" cellpadding="0" cellspacing="0" style="background-color:transparent;text-align:center">
              <tr>
                <td width="62" style="text-align:center" valign="bottom" class="ButtonA" id="hhh" onMouseOver="setClass('hhh','ButtonA1')" onMouseOut="setClass('hhh','ButtonA')">
                  <a onclick="onLogin();" class="BLink" style="cursor:pointer" >  �� ¼</a></td>
                <td width="14"></td>
                <td width="62" style="text-align:center" valign="bottom" class="ButtonA" id="ggg" onMouseOver="setClass('ggg','ButtonA1')" onMouseOut="setClass('ggg','ButtonA')">
                  <a onclick="window.close();" class="BLink" style="cursor:pointer" >  ��  �� </a></td>
                  <td width="12"></td>
                 <td> <input type="hidden" id="clientResult" name="clientResult"></td>
                 <td> <input type="hidden" id="ukeysn" name="ukeysn"></td>
                 <td> <input type="hidden" id="rand" name="rand"></td>
                 <td> <input type="hidden" id="aae101" name="aae101"></td>
              </tr>
            </table></td>
         <td>
         	<input type="button" id="bbb" value="����ϵͳ"  onclick="toHR()" class="ButtonA" onMouseOver="setClass('bbb','ButtonA1')" onMouseOut="setClass('bbb','ButtonA')" style="background-color:transparent;border: none;width: 68;height: 22;margin-left: 45px;margin-top: 5px" />  
         	<input type="button" id="vipsystem" value="��Աϵͳ"  onclick="toHY()" class="ButtonA" onMouseOver="setClass('vipsystem','ButtonA1')" onMouseOut="setClass('vipsystem','ButtonA')" style="background-color:transparent;border: none;width: 68;height: 22;margin-left: 45px;margin-top: 5px" />
         </td>
        </tr>
      </table> </td>
  </tr>
</table>
</html:form>
<lemis:errors />
</body>
</html>

