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
	header.add(new Formatter("pdtid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("fdtprc", "��Ʒ����"));
	header.add(new Formatter("fdtqnt", "�۳�����"));
	//header.add(new Formatter("fdtdisc", "��Ʒ����"));
	header.add(new Formatter("fdtrprc", "ʵ���շ�"));
	//header.add(new Formatter("chgnt", "��ע"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "pdtnm", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "fdtprc", "��Ʒ����", "true"));
	//batchInput.add(new Editor("text", "fdtdisc", "��Ʒ����", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "�۳�����", "true"));
	batchInput.add(new Editor("text", "fdtrprc", "ʵ���շ�", "true"));
	//batchInput.add(new Editor("text", "chgnt", "��ע", "false"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("�� ӡ","print()");
	//buttons.put("��������", "batchSubmit(document.all.tableform)");
	//buttons.put("�� ��","history.back()");
	buttons.put("�� ��", "closeWindow(\"\")");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "�շѺ�"); 
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("batchInput", batchInput);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script language="javascript">
	function print(){
		var cid = $("input[name=chgid]").val();
		if(cid == -1){
			alert("���շ���Ϣ���뱣����ٴ�ӡ��");
			return false;
		}
		window.location.href = "/" + lemis.WEB_APP_NAME
		+ "/charge/ChargeAction.do?method=printCheck&chgid=" + $("input[name=chgid]").val() + "&"
		+ getAlldata(document.all.tableform);
	}
</script>

<lemis:body>
<lemis:base />

    <lemis:title title="�����Ŀ�շ�����" />
   
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		 <html:form action="/ChargeAction.do?method=saveCheckCharge"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<html:hidden property = "chgid"/>
				<lemis:texteditor property="folctnm" label="��������ͻ�" disable="true"/>
				<lemis:texteditor property="ictnm" label="�û�����" disable="true"/>
				<lemis:texteditor property="ictgender" label="�Ա�" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="ictaddr" label="�û���ַ" disable="true" />
				<lemis:texteditor property="icttel" label="�û��绰" disable="true"/>
				<lemis:texteditor property="ictpnm" label="�ҳ�����" disable="true"/>
			</tr>
			<tr>
				<td>�շ�����</td>	
				<td><lemis:operdate /></td>
				<%-- <lemis:texteditor property="chgid" label="�շѺ�" disable="true" /> --%>
				<lemis:texteditor property="ictgctid" label="�ͻ�����" disable="true" />
				<lemis:texteditor property="ncdypname" label="����ʦ����" disable="false" />		
			</tr>
		</html:form>
	</table>
	<lemis:table topic="��Ʒ��ϸ¼��"
		action="/ChargeAction.do?method=batchSubmit" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false"
		 batchInputType="insert" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	
</lemis:body>
</html>


