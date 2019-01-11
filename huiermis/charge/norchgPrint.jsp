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
	//共12个属性项
	header.add(new Formatter("chgid", "收费号"));
	header.add(new Formatter("folctnm", "所属团体客户"));
	header.add(new Formatter("ictnm", "用户名称"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("pdtprc", "商品单价"));
	header.add(new Formatter("ncdqnt", "售出数量"));
	header.add(new Formatter("ncddis", "商品扣率"));
	header.add(new Formatter("ncdmon", "实际收费"));
	header.add(new Formatter("ncdnt", "备注"));

	//Map button1 = new LinkedHashMap();
	//button1.put("查询[Q]","return query(this.form)");
	//button1.put("重置[R]","resetForm(this.form);");
	//pageContext.setAttribute("button1",button1);
	   
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("普通商品收费打印","print1(document.all.tableform)");
    buttons.put("检查项目收费打印","print2(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "收费号"); 
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "chgid", "收费号"));
	editors.add(new Editor("text", "folctnm", "所属团体客户"));
	editors.add(new Editor("text", "ictnm", "用户名称"));	//cltnm在数据表AA10中不存在，所以不显示下拉列表
	editors.add(new Editor("text", "pdtnm", "商品名称"));
	
	
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

    
    <lemis:title title="普通商品收费查询" />
	<lemis:query action="/ChargeAction.do?method=query&charge=norchgprint" editorMeta="editor" topic="查询条件" />	
	<lemis:table action="ChargeAction.do" headerMeta="header" topic="普通商品收费信息"
		hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


