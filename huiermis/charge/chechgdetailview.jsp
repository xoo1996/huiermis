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
	header.add(new Formatter("pdtid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("fdtprc", "商品单价"));
	header.add(new Formatter("fdtqnt", "售出数量"));
	//header.add(new Formatter("fdtdisc", "商品扣率"));
	header.add(new Formatter("fdtrprc", "实际收费"));
	//header.add(new Formatter("chgnt", "备注"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "商品代码", "true"));
	batchInput.add(new Editor("text", "pdtnm", "商品名称", "true"));
	batchInput.add(new Editor("text", "fdtprc", "商品单价", "true"));
	//batchInput.add(new Editor("text", "fdtdisc", "商品扣率", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "售出数量", "true"));
	batchInput.add(new Editor("text", "fdtrprc", "实际收费", "true"));
	//batchInput.add(new Editor("text", "chgnt", "备注", "false"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("打 印","print()");
	//buttons.put("批量保存", "batchSubmit(document.all.tableform)");
	//buttons.put("返 回","history.back()");
	buttons.put("关 闭", "closeWindow(\"\")");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "收费号"); 
	
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
			alert("无收费信息，请保存后再打印！");
			return false;
		}
		window.location.href = "/" + lemis.WEB_APP_NAME
		+ "/charge/ChargeAction.do?method=printCheck&chgid=" + $("input[name=chgid]").val() + "&"
		+ getAlldata(document.all.tableform);
	}
</script>

<lemis:body>
<lemis:base />

    <lemis:title title="检查项目收费详情" />
   
	<lemis:tabletitle title="收费信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		 <html:form action="/ChargeAction.do?method=saveCheckCharge"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<html:hidden property = "chgid"/>
				<lemis:texteditor property="folctnm" label="所属团体客户" disable="true"/>
				<lemis:texteditor property="ictnm" label="用户名称" disable="true"/>
				<lemis:texteditor property="ictgender" label="性别" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="ictaddr" label="用户地址" disable="true" />
				<lemis:texteditor property="icttel" label="用户电话" disable="true"/>
				<lemis:texteditor property="ictpnm" label="家长姓名" disable="true"/>
			</tr>
			<tr>
				<td>收费日期</td>	
				<td><lemis:operdate /></td>
				<%-- <lemis:texteditor property="chgid" label="收费号" disable="true" /> --%>
				<lemis:texteditor property="ictgctid" label="客户代码" disable="true" />
				<lemis:texteditor property="ncdypname" label="验配师姓名" disable="false" />		
			</tr>
		</html:form>
	</table>
	<lemis:table topic="商品明细录入"
		action="/ChargeAction.do?method=batchSubmit" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false"
		 batchInputType="insert" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	
</lemis:body>
</html>


