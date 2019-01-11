<!--sysmanager/user/SaveUser.jsp-->
<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.manage.user.form.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.radf.manage.entity.Sc06"%>
<%@ page import="org.radf.manage.user.dto.*"%>
<%@ page import="org.radf.plat.commons.ClassHelper"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String function = "";
	String title = "";
	function = request.getParameter("function");
	//��� sc06List
	ArrayList sc06List = (ArrayList) request.getAttribute("sc06List");
	ArrayList sc06List2 = (ArrayList) request.getAttribute("sc06List2");
	//��� sc04List
	ArrayList sc04List = (ArrayList) request.getAttribute("sc04List");
	ArrayList sc04List2 = (ArrayList) request.getAttribute("sc04List2");

	
	if ("add".equals(function)) {
		title = "�����Ա";
	} else if ("mod".equals(function)) {
		title = "�޸���Ա";
	}

	Map buttons = new LinkedHashMap();
	if ("add".equals(function)) {
		//title = "�����Ա";
		buttons.put("�� ��", "addData(document.forms[0])");
	} else if ("mod".equals(function)) {
		//title = "�޸���Ա";
		buttons.put("�� ��", "saveData(document.forms[0])");
	}
	buttons.put("�� ��", "document.forms[0].reset();");
	buttons.put("�� ��", "go2Page(\"sysmanager\",\"\")");
	buttons.put("�� ��", "closeWindow(\"UserForm\")");
	pageContext.setAttribute("button", buttons);
%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/md5.js"></script>
<html>
<head>
<title>��Ա����Ϣ</title>
<lemis:base />
</head>
<lemis:body>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
	</table>
	<!--���ⲿ��-->
	<lemis:title title="<%=title%>" />
	<lemis:tabletitle title="��Ա�б���Ϣ" />
	<table width="95%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tableInput">
		<html:form action="/userAction.do?method=userAdd" method="POST">
			<lemis:editorlayout />
			<html:hidden property="bsc001" />
      		<html:hidden property="bsc008" />
			<!--¼�벿��-->
			<tr>
				<td height="0" align="right" nowrap><font color='#FF0000'>*</font>�û�����<br>
				</td>
				<td height="0" align="center"><lemis:text property="bsc011" maxlength="10" 
					label="�û�����" required="true" disable="false"></lemis:text></td>
				<td height="0" align="right" nowrap><font color='#FF0000'>*</font>�û�����<br>
				</td>
				<td height="0" align="center"><lemis:text property="bsc012" maxlength="40" 
					label="�û�����" required="true" disable="false"></lemis:text></td>
			</tr>
			<%if ("mod".equals(function)) { %>
			<tr>
				<td height="0" align="right" nowrap></font>��������<br>
				</td>
				<td height="0" align="center"><lemis:text property="aab300" maxlength="64" 
					label="��������" required="false" readonly="true"></lemis:text></td>
				<td height="0" align="right" nowrap></font>��������<br>
				</td>
				<td height="0" align="center"><lemis:text property="bsc009" maxlength="64" 
					label="��������" required="false" readonly="true"></lemis:text></td>
			</tr>
			<%} %>
			<tr>

				<td height="0" align="right" nowrap><font color='#FF0000'>*</font>��ϵ�绰<br>
				</td>
				<td height="0" align="center"><lemis:text property="aae005"  maxlength="64" 
					label="��ϵ�绰" required="true" disable="false"></lemis:text></td>
				<td height="0" align="right" nowrap>&nbsp;<br>
				</td>
				<td height="0" align="center">&nbsp;</td>
			</tr>
			<!--������ɫ����-->
			<tr>
				<td height="0" align="right" nowrap><font color='#FF0000'>*</font>������ɫ</td>
				<td width=40%>
				<table cellspacing="0" cellpadding="0"
					style="height:100px" align="left">
					<tr>
						<td align="center">��ѡ��ɫ</td>
						<td align="center" style="width:10px"></td>
						<td align="center">��ѡ��ɫ</td>
					</tr>
					<tr>
						<td align="center"><select name="roleListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">
							<%
									if (sc06List != null && sc06List.size() > 0) {
									Sc06 sc06 = new Sc06();
									for (int i = 0; i < sc06List.size(); i++) {
										ClassHelper.copyProperties(sc06List.get(i), sc06);
							%>
							<option value="<%=sc06.getBsc014()%>"><%=sc06.getBsc015()%></option>
							<%
										}
										}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="roleListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">
							<%
									if (sc06List2 != null && sc06List2.size() > 0) {
									Sc05DTO sc05dto = new Sc05DTO();
									for (int i = 0; i < sc06List2.size(); i++) {
										ClassHelper.copyProperties(sc06List2.get(i),
										sc05dto);
							%>
							<option value="<%=sc05dto.getBsc014()%>"><%=sc05dto.getBsc015()%></option>
							<%
										}
										}
							%>
						</select></td>
					</tr>
				</table>
				</td>
				<!--������������-->
				<td height="0" align="right" nowrap><font color='#FF0000'>*</font>��������</td>
				<td width=40%>
				<table cellspacing="0" cellpadding="0"
					style="height:100px" align="left">
					<tr>
						<td align="center">��ѡ����</td>
						<td align="center" style="width:10px"></td>
						<td align="center">��ѡ����</td>
					</tr>
					<tr>
						<td align="center"><select name="groupListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','groupListLeft','groupListRight',false)">
							<%
									if (sc04List != null && sc04List.size() > 0) {
									Sc05DTO sc04n01 = new Sc05DTO();
									for (int i = 0; i < sc04List.size(); i++) {
										ClassHelper
										.copyProperties(sc04List.get(i), sc04n01);
							%>
							<option
								value="<%=sc04n01.getBsc008()+";"+sc04n01.getBsc001()+";"+sc04n01.getBsc009()+";"+sc04n01.getAab300()%>"><%=sc04n01.getBsc009()%></option>
							<%
										}
										}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','groupListLeft','groupListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','groupListLeft','groupListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','groupListLeft','groupListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','groupListLeft','groupListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="groupListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','groupListLeft','groupListRight',false)">
							<%
									if (sc04List2 != null && sc04List2.size() > 0) {
									Sc05DTO sc04n01 = new Sc05DTO();
									for (int i = 0; i < sc04List2.size(); i++) {
										ClassHelper.copyProperties(sc04List2.get(i),
										sc04n01);
							%>
							<option
								value="<%=sc04n01.getBsc008()+";"+sc04n01.getBsc001()+";"+sc04n01.getBsc009()+";"+sc04n01.getAab300()%>"><%=sc04n01.getBsc009()%></option>
							<%
										}
										}
							%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
			
			
			<%
			UserForm uf = (UserForm) request.getSession().getAttribute("userForm");
			%>
			<input id="bsc010t" type="hidden" value="<%=uf.getBsc010() %>" />
			<input id="bsc011t" type="hidden" value="<%=uf.getBsc011() %>" />
			<input id="roleList" type="hidden" />
			<input id="groupList" type="hidden" />
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	<lemis:errors />
</lemis:body>
</html>
<script language="javascript">
function page_init(){ //����ǩ�����ܸı�

}
	function addData(obj){
		if(!checkValue(obj)){
			return false;
		}
		if(!check()){
			return false;
		}
		obj.action ="/" + lemis.WEB_APP_NAME + "/sysmanager/userAction.do?method=modifySc05&function=<%=function%>" +
		"&bsc013=" + hex_md5("888888") +
		"&aae100=1" + 
		"&roleList=" + document.all("roleList").value + 
		"&groupList=" + document.all("groupList").value;
		
		obj.submit();
	}
	
	function saveData(obj){
		if(!checkValue(obj)){
			return false;
		}
		if(!check()){
			return false;
		}
		obj.action ="/" + lemis.WEB_APP_NAME + "/sysmanager/userAction.do?method=modifySc05&function=<%=function%>" +
		"&bsc010=" + document.all("bsc010t").value +
		"&bsc011t=" + document.all("bsc011t").value +
		"&roleList=" + document.all("roleList").value + 
		"&groupList=" + document.all("groupList").value;
		obj.submit();
	}
	
	function toBack(){
		window.location.href="/" + lemis.WEB_APP_NAME + "/sysmanager/userAction.do?method=goBack";
	}
	
	function confirmPassword(){
  var pd = document.userForm.passwd.value;
  var confirm = document.userForm.confirm.value;
  if(pd != confirm){
  	alert("�����ȷ�����벻һ�£�");
    return false;
  }
  return true;
}

//У���û�id
	function IsDigit(cCheck) { return (('0'<=cCheck) && (cCheck<='9')); }
	function IsAlpha(cCheck) { return ((('a'<=cCheck) && (cCheck<='z')) || (('A'<=cCheck) && (cCheck<='Z'))) }
	function VerifyInput(strUserID){
	    if (strUserID == ""){
	        alert("�����������û���");
	        document.frmUserInfo.UserID.focus();
	        return false;
	    }
	    for (nIndex=0; nIndex<strUserID.length; nIndex++){
	        cCheck = strUserID.charAt(nIndex);
	        if ( nIndex==0 && ( cCheck =='-' || cCheck =='_') ){
	            alert("�û������ַ�����Ϊ��ĸ������");
	            document.userForm.userid.focus();
	            return false;
	        }
	
	        if (!(IsDigit(cCheck) || IsAlpha(cCheck) || cCheck=='-' || cCheck=='_' )){
	            alert("�û���ֻ��ʹ��Ӣ����ĸ�������Լ�-��_���������ַ�����Ϊ��ĸ������");
	            document.userForm.userid.focus();
	            return false;
	        }
	    }
	    return true;
	}
		
function check(){

	document.userForm.roleList.value = getSelectedData('userForm','roleListRight');
	document.userForm.groupList.value = getSelectedData('userForm','groupListRight');
	var groupListRight = document.all('userForm').all('groupListRight');
	
  if(groupListRight.length){
    if(groupListRight.length > 1){
      alert("��������ֻ����һ����");
      document.userForm.groupListRight.focus();
      return false;
    }
  }else if(groupListRight.length==0){
  	alert("������������Ϊ�գ�");
  	return false;
  }
	return true;
} 
</script>

