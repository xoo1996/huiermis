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
	//header.add(new Formatter("ncdid", "收费号"));  
	header.add(new Formatter("ncdid", "收费号")); 
	header.add(new Formatter("gctnm", "团体客户"));
	header.add(new Formatter("ictnm", "个人客户"));
	header.add(new Formatter("ncdpid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("ncdqnt", "售出数量"));
	header.add(new Formatter("ncdrnt", "退货数量"));
	header.add(new Formatter("ncdmon", "实际收费"));
	header.add(new Formatter("ncdrecmon", "退货金额"));
	header.add(new Formatter("chgdt", "收费日期"));
	header.add(new Formatter("ncdrecdt", "退机日期"));
	header.add(new Formatter("ncdrecheaddt", "退回总部日期"));
	header.add(new Formatter("ncdsta", "状态"));
	header.add(new Formatter("ncdnt", "备注"));

	//List<Button> buttons=new ArrayList<Button>();
	//buttons.add(new Button("tui","退 机","ord020001","tuiji(document.all.tableform)"));
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("审核","examine(document.forms[0])");
	buttons.put("回退","rollback(document.forms[0])");
	buttons.put("打印","print(document.forms[0])");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chggcltid", "团体客户代码");
	hidden.put("chgcltid,", "个人客户代码");
	
	
	List<Editor> editors = new ArrayList<Editor>();
	//editors.add(new Editor("text", "ncdid", "收费号"));
	editors.add(new Editor("text", "ncdoid", "收费号"));
	editors.add(new Editor("text", "gctnm", "团体客户"));
	editors.add(new Editor("text", "ictnm", "个人客户"));
	//editors.add(new Editor("text", "sid", "机身编号"));
	editors.add(new Editor("text", "pdtnm", "商品名称"));
	editors.add(new Editor("text", "ncdsta", "状态"));
	editors.add(new Editor("text", "ncddtty", "日期类型"));
	editors.add(new Editor("date","start","日期从"));
	editors.add(new Editor("date","end","到"));
	
	
	List<Editor> batchInput = new ArrayList<Editor>();
	//batchInput.add(new Editor("text", "ncdsta", "状态"));
	batchInput.add(new Editor("text", "ncdrnt", "退货数量"));
	batchInput.add(new Editor("text", "ncdpid", "商品代码"));
	batchInput.add(new Editor("text", "ncdid", "收费号"));

	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("batchInput", batchInput);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script>
	$(document).ready(function(){
		for(var i = 1; i <= 10; i ++){
			$("input[id=ncdid_row" + i + "]").attr('readonly','readonly');
			$("input[id=ncdpid_row" + i + "]").attr('readonly','readonly');
			$("input[id=ncdrnt_row" + i + "]").attr('readonly','readonly');
			
		}
		$("select[name=ncdsta]").val("commited");

		
			var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
			$("input[name=gctnm]").autocomplete(shops,{
				max:10,
				matchContains:true,
				formatItem:function(data,i,max){
					return data[0];
				}
			});
			
			$("input[name=gctnm]").result(function(event,data,formatted){
				if(data){
					var gid = data[0].substring(0,data[0].indexOf("="));
					var gnm=data[0].substring(data[0].indexOf("=")+1);
					$("input[name=gctnm]").val(gnm);
					$(this).parent().next().find("input").val(gid);
				}
			});
	});
</script>

<script language="javascript">
function rollback(obj) {
	var t = editObj("chk");
	if(!t){
		return t;
	}
	if (confirm("确实要回退该普通商品退货吗？")) {
	obj.action = '<html:rewrite href="/huiermis/charge/ChargeAction.do?method=exaNomRec&tp=r&"/>'+ getAlldata(document.all.tableform);
	obj.submit();
	}
	//将数据提交到后台 注意：getAlldata(document.all.tableform)是得到所有要提交的数据。
	else
		return t;
};

function examine(obj){
	var t = editObj("chk");
	if(!t){
		return t;
	}
	if (confirm("确实要审核通过该普通商品退机吗？")) {
	obj.action = '<html:rewrite href="/huiermis/charge/ChargeAction.do?method=exaNomRec&tp=e&"/>'+ getAlldata(document.all.tableform);
	obj.submit(); 
	}
	//将数据提交到后台 注意：getAlldata(document.all.tableform)是得到所有要提交的数据。
	else
		return t;
}

function print(obj){
	var t = editObj("chk");
	if(!t){
		return t;
	}
	if (confirm("即将打印该条记录")) {
	obj.action = '<html:rewrite href="/huiermis/charge/ChargeAction.do?method=printNorBeforeVerify&"/>'+ getAlldata(document.all.tableform);
	obj.submit(); 
	}
	//将数据提交到后台 注意：getAlldata(document.all.tableform)是得到所有要提交的数据。
	else
		return t;
}

function saveData(obj) {
	t = preCheckForBatch();
	if(!t){
		return t;
	}
	if (!checkValue(obj)) {
		return false;
	}
	var t = delObj("chk");
	if(!t){
		return t;
	}
	obj.action='<html:rewrite href="/huiermis/charge/ChargeAction.do?method=nomRecExa&tp=s&"/>'+ getAlldata(document.all.tableform);
	if (confirm("确实要保存吗？")) {
		obj.submit();
	}
};

function commit(obj) {
	if (!checkValue(obj)) {
		return false;
	}
	obj.action='<html:rewrite href="/huiermis/charge/ChargeAction.do?method=nomRecExa&tp=c&"/>' + getAlldata(obj);
	obj.submit();
};
	</script>

<lemis:base />
<lemis:body>
	<lemis:title title="退机查询" />
	<lemis:query action="/OrderAction.do?method=query&order=nomRecExa" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="OrderAction.do" headerMeta="header" topic="退机订单信息" batchInputMeta="batchInput"
		hiddenMeta="hidden" mode="checkbox" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


