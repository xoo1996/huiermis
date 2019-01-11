<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- <%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("保 存","saveData(document.forms[0])");
    buttons.put("重 置","document.forms[0].reset();");
    buttons.put("关 闭","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%> --%>
<html>
<style>
.input {
	background-color: #efefef;
	border-color: #000000;
	border-style: solid;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 1px;
	border-left-width: 0px;
}
</style>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/huiermis/js/CityJson.js"></script>
<script src="/huiermis/js/DistrictJson.js"></script>
<script src="/huiermis/js/ProJson.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script language="javascript">
	function insertNextFile(obj) {
		// 获取上传控制个数
		var childnum = document.getElementById("files").getElementsByTagName(
				"input").length;
		var id = childnum - 1;
		var fullName = obj.value;
		// 插入<div>元素及其子元素
		var fileHtml = '';
		fileHtml += '<div  id = "file_preview' + id + '" style ="border-bottom: 1px solid #CCC;">';
		fileHtml += '<img  width =30 height = 30 src ="images/file.gif" title="' + fullName + '"/>';
		fileHtml += '<a href="javascript:;" onclick="removeFile(' + id
				+ ');">删除</a> &nbsp;&nbsp;';
		fileHtml += fullName.substr(fullName.lastIndexOf('\\') + 1)
				+ '  </div>';

		var fileElement = document.getElementById("files_preview");
		fileElement.innerHTML = fileElement.innerHTML + fileHtml;
		obj.style.display = 'none'; // 隐藏当前的<input type=”file”/>元素
		addUploadFile(childnum); // 插入新的<input type=”file”/>元素
	}
	//  插入新的<input type=”file”/>元素，适合于不同的浏览器（包括IE、FireFox等）
	function addUploadFile(index) {
		try // 用于IE浏览器
		{
			var uploadHTML = document
					.createElement("<input type='file' id='file_" + index
							+ "' name='file[" + index
							+ "]' onchange='insertNextFile(this)'/>");
			document.getElementById("files").appendChild(uploadHTML);
		} catch (e) // 用于其他浏览器
		{
			var uploadObj = document.createElement("input");
			uploadObj.setAttribute("name", "file[" + index + "]");
			uploadObj.setAttribute("onchange", "insertNextFile(this)");
			uploadObj.setAttribute("type", "file");
			uploadObj.setAttribute("id", "file_" + index);
			document.getElementById("files").appendChild(uploadObj);
		}
	}
	function removeFile(index) // 删除当前文件的<div>和<input type=”file”/>元素
	{
		document.getElementById("files_preview").removeChild(
				document.getElementById("file_preview" + index));
		document.getElementById("files").removeChild(
				document.getElementById("file_" + index));
	}
	function showStatus(obj) // 显示“正在上传文件”提示信息
	{
		document.getElementById("status").style.visibility = "visible";
	}
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	}
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="上传用户的调试记录" />
	<lemis:tabletitle title="用户基本信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do?method=uploadDebug"
			method="POST" enctype="multipart/form-data">
			<html:hidden property="ictgctid" />
			<tr>

				<lemis:texteditor property="ictid" label="用户编号" required="false"
					disable="true" />
				<lemis:texteditor property="ictnm" label="用户姓名" required="false"
					disable="true" />
				<lemis:texteditor property="gctnm" label="所属团体" required="false"
					disable="true" />
			</tr>
			<lemis:tabletitle title="上传调试信息" />
			<table class="tableinput">
				<span id="files"> <%--  在此处插入用于上传文件的input元素 --%>
				<input
					type="file" id="file_0" name="file[0]"
					onchange="insertNextFile(this)" />
				</span>&nbsp;&nbsp;
				<html:submit value="上传调试文件 " onclick="showStatus(this);" />
				</html:form>
				<p>
				<div id="status" style="visibility: hidden; color: Red">正在上传文件</div>
				<p>
					<%--  在此处用DOM技术插入上传文件列表项  --%>
				<div id="files_preview"
					style="width: 500px; height: 500px; overflow: auto"></div>
			</table>
	</table>
	<%-- <lemis:buttons buttonMeta="button" /> --%>
	<lemis:bottom />
</lemis:body>
</html>

