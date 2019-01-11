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
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("qafno", "������"));
	header.add(new Formatter("gctnm", "�ͻ�����"));
	header.add(new Formatter("qacltnm", "�û�����"));
	header.add(new Formatter("qasid", "������"));
	header.add(new Formatter("qapnm", "�������ͺ�"));
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("��ӡ","print(document.all.tableform)");
	buttons.put("����","fangxing(document.all.tableform)");
	buttons.put("�� ��","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("qaid", "�ʼ���ˮ��");
	hidden.put("qafno", "������");
	hidden.put("qachkopr","�ʼ�Ա1");
	hidden.put("qachkopr2","�ʼ�Ա2");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","qafno","������"));
	editors.add(new Editor("text","qasid","������"));
	editors.add(new Editor("text","qacltnm","�û�����"));
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	$(document).ready(function() {
		$("input[name=qasid]:first").focus();
		$("input[name=qasid]:first").val("");
		$("input[name=qasid]").blur(function() {
			if ($("input[name=qasid]:first").val() != "") {
				$("#queryForm").submit();
			}
		});
	});
	function fangxing(obj) {
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		
		if (confirm("�����ϸ��Ƿ���У�")) {
			window.location.href = "/"
				+ lemis.WEB_APP_NAME
				+ "/qa/QAAction.do?method=fangxing&"
				+ getAlldata(document.all.tableform);
		} 
		
	}
	
	function print(obj) {
		
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		//console.log(getAlldata(document.all.tableform));
		window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/qa/QAAction.do?method=printFangxing&"
		+ getAlldata(document.all.tableform);
		
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="����" />
	<lemis:query action="/QAAction.do?method=queryFangxing"
		editorMeta="editor" topic="��ѯ����" />
	<lemis:table action="QAAction.do" headerMeta="header" topic="�ȴ����м�¼"
		hiddenMeta="hidden" mode="checkbox" pageSize="20"/>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


