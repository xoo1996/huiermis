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
	//��12��������
	header.add(new Formatter("chgid", "�շѺ�"));
	header.add(new Formatter("folctnm", "��������ͻ�"));
	header.add(new Formatter("ictnm", "�û�����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("pdtprc", "��Ʒ����"));
	header.add(new Formatter("ncdqnt", "�۳�����"));
	header.add(new Formatter("ncddis", "��Ʒ����"));
	header.add(new Formatter("ncdmon", "ʵ���շ�"));
	header.add(new Formatter("ncdnt", "��ע"));

	//Map button1 = new LinkedHashMap();
	//button1.put("��ѯ[Q]","return query(this.form)");
	//button1.put("����[R]","resetForm(this.form);");
	//pageContext.setAttribute("button1",button1);
	   
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("��ͨ��Ʒ�շѴ�ӡ","print1(document.all.tableform)");
    buttons.put("�����Ŀ�շѴ�ӡ","print2(document.all.tableform)");
	buttons.put("�� ��","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "�շѺ�"); 
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "chgid", "�շѺ�"));
	editors.add(new Editor("text", "folctnm", "��������ͻ�"));
	editors.add(new Editor("text", "ictnm", "�û�����"));	//cltnm�����ݱ�AA10�в����ڣ����Բ���ʾ�����б�
	editors.add(new Editor("text", "pdtnm", "��Ʒ����"));
	
	
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
	function print1(obj) {
		var t = editObj("chk");
		if (!t) {
			return t;
		}

		obj.action = '<html:rewrite page="/ChargeAction.do?method=print&"/>' + getAlldata(obj);
		obj.submit();
	};

	function print2(obj) {
		var t = editObj("chk");
		if (!t) {
			return t;
		}

		obj.action = '<html:rewrite page="/ChargeAction.do?method=printCheck&"/>' + getAlldata(obj);
		obj.submit();
	};
</script>

<lemis:base />
<lemis:body>

    
    <lemis:title title="��ͨ��Ʒ�շѲ�ѯ" />
	<lemis:query action="/ChargeAction.do?method=query&charge=norchgprint" editorMeta="editor" topic="��ѯ����" />	
	<lemis:table action="ChargeAction.do" headerMeta="header" topic="��ͨ��Ʒ�շ���Ϣ"
		hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


