<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- <%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
    buttons.put("�� ��","document.forms[0].reset();");
    buttons.put("�� ��","closeWindow(\"\")");
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
		// ��ȡ�ϴ����Ƹ���
		var childnum = document.getElementById("files").getElementsByTagName(
				"input").length;
		var id = childnum - 1;
		var fullName = obj.value;
		// ����<div>Ԫ�ؼ�����Ԫ��
		var fileHtml = '';
		fileHtml += '<div  id = "file_preview' + id + '" style ="border-bottom: 1px solid #CCC;">';
		fileHtml += '<img  width =30 height = 30 src ="images/file.gif" title="' + fullName + '"/>';
		fileHtml += '<a href="javascript:;" onclick="removeFile(' + id
				+ ');">ɾ��</a> &nbsp;&nbsp;';
		fileHtml += fullName.substr(fullName.lastIndexOf('\\') + 1)
				+ '  </div>';

		var fileElement = document.getElementById("files_preview");
		fileElement.innerHTML = fileElement.innerHTML + fileHtml;
		obj.style.display = 'none'; // ���ص�ǰ��<input type=��file��/>Ԫ��
		addUploadFile(childnum); // �����µ�<input type=��file��/>Ԫ��
	}
	//  �����µ�<input type=��file��/>Ԫ�أ��ʺ��ڲ�ͬ�������������IE��FireFox�ȣ�
	function addUploadFile(index) {
		try // ����IE�����
		{
			var uploadHTML = document
					.createElement("<input type='file' id='file_" + index
							+ "' name='file[" + index
							+ "]' onchange='insertNextFile(this)'/>");
			document.getElementById("files").appendChild(uploadHTML);
		} catch (e) // �������������
		{
			var uploadObj = document.createElement("input");
			uploadObj.setAttribute("name", "file[" + index + "]");
			uploadObj.setAttribute("onchange", "insertNextFile(this)");
			uploadObj.setAttribute("type", "file");
			uploadObj.setAttribute("id", "file_" + index);
			document.getElementById("files").appendChild(uploadObj);
		}
	}
	function removeFile(index) // ɾ����ǰ�ļ���<div>��<input type=��file��/>Ԫ��
	{
		document.getElementById("files_preview").removeChild(
				document.getElementById("file_preview" + index));
		document.getElementById("files").removeChild(
				document.getElementById("file_" + index));
	}
	function showStatus(obj) // ��ʾ�������ϴ��ļ�����ʾ��Ϣ
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
	<lemis:title title="�ϴ��û��ĵ��Լ�¼" />
	<lemis:tabletitle title="�û�������Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do?method=uploadDebug"
			method="POST" enctype="multipart/form-data">
			<html:hidden property="ictgctid" />
			<tr>

				<lemis:texteditor property="ictid" label="�û����" required="false"
					disable="true" />
				<lemis:texteditor property="ictnm" label="�û�����" required="false"
					disable="true" />
				<lemis:texteditor property="gctnm" label="��������" required="false"
					disable="true" />
			</tr>
			<lemis:tabletitle title="�ϴ�������Ϣ" />
			<table class="tableinput">
				<span id="files"> <%--  �ڴ˴����������ϴ��ļ���inputԪ�� --%>
				<input
					type="file" id="file_0" name="file[0]"
					onchange="insertNextFile(this)" />
				</span>&nbsp;&nbsp;
				<html:submit value="�ϴ������ļ� " onclick="showStatus(this);" />
				</html:form>
				<p>
				<div id="status" style="visibility: hidden; color: Red">�����ϴ��ļ�</div>
				<p>
					<%--  �ڴ˴���DOM���������ϴ��ļ��б���  --%>
				<div id="files_preview"
					style="width: 500px; height: 500px; overflow: auto"></div>
			</table>
	</table>
	<%-- <lemis:buttons buttonMeta="button" /> --%>
	<lemis:bottom />
</lemis:body>
</html>

