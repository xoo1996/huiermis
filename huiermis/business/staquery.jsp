<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("gctnm","所属团体"));
	header.add(new Formatter("gctarea", "所属区域"));
	header.add(new Formatter("webyear", "年份"));	
	header.add(new Formatter("webmonth", "月份"));	
	header.add(new Formatter("websource", "网络来源人数"));
	header.add(new Formatter("websell","网络销售人数"));
	header.add(new Formatter("webnum", "网络销售台数"));
	//header.add(new Formatter("webcvt", "总部转诊销售额 "));
	header.add(new Formatter("weblocal","地方网络销售额"));
	header.add(new Formatter("webcvt","总部转诊销售额"));
	header.add(new Formatter("webtotal","合计网络销售额"));
	//header.add(new Formatter("webmoney","当月销售额"));
	header.add(new Formatter("webrate", "网络销售额占当月销售额百分比"));
	header.add(new Formatter("webmark", "地图标注"));
	header.add(new Formatter("webcheck","发帖登记"));
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("修改","modify(document.all.tableform)");
	buttons.put("详情","detail(document.all.tableform)");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","gctnm","所属团体"));
	editors.add(new Editor("text","gctarea","所属区域"));
	editors.add(new Editor("text","webmark","地图标注"));
	editors.add(new Editor("text","ivtyear","年份","true"));
	editors.add(new Editor("text","ivtmonth","月份","true"));
	editors.add(new Editor("text","webcommit","门店是否提交"));
	//editors.add(new Editor("date","stodate","入库日期"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ivtyear", "年");
	hidden.put("ivtmonth", "月");
	hidden.put("gctnm", "所属团体");
	hidden.put("webyear", "年");
	hidden.put("webmonth", "月");
	
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript">
	function detail(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/BusinessAction.do?method=queryWebSell&tp=t&"/>'+getAlldata(obj);			
		obj.submit();
		//将数据提交到后台 注意：getAlldata(document.all.tableform)是得到所有要提交的数据。
	};
	
	function modify(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/BusinessAction.do?method=modifyWebSell&"/>'+getAlldata(obj);			
		obj.submit();
		//将数据提交到后台 注意：getAlldata(document.all.tableform)是得到所有要提交的数据。
	};

$(document).ready(function(){
	$("select[name=webcommit]").val("yes");//N否Y是
	
	var grCli="";
	grCli=<%=dto.getBsc001()%>;
	var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
	if(grCli=="1501000000")
	{
		$("input[name=gctnm]").autocomplete(shops,{
			max:10,
			matchContains:true,
			formatItem:function(data,i,max){
			return data[0].substring(0);
			}
		});

		$("input[name=gctnm]").result(function(event,data,formatted){
			if(data){
				var gid = data[0].substring(0,data[0].indexOf("="));
				var gnm=data[0].substring(data[0].indexOf("=")+1,data[0].length);
				$("input[name=gctnm]").val(gnm);
			
				}
			});
	}
	else
	{
		$("input[name=gctnm]").val("<%=dto.getBsc012()%>");
		$("input[name=gctnm]").attr("readonly","true");
		$("input[value=重置[R]]").bind("click",function(e){
		$("input[name=gctnm]").val("<%=dto.getBsc012()%>");
		}); 
	}
	
});
</script>
<lemis:base />
<lemis:body>
	<lemis:title title="网络销售来源查询" />
	<lemis:query action="/BusinessAction.do?method=staquery" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="网络销售来源信息"
		hiddenMeta="hidden"  mode="radio"  />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


