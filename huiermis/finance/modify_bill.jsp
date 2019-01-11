<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%	
	String notype = (String)request.getSession().getAttribute("notype");
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("fintype", "开票类型"));
	header.add(new Formatter("notype", "订单类型"));
	header.add(new Formatter("gctnm", "店铺编号"));
	header.add(new Formatter("pdtid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("pdtnum", "数量"));
	header.add(new Formatter("finrate", "开票扣率"));
	header.add(new Formatter("finprc", "含税单价"));
	header.add(new Formatter("finprccount", "总额"));
	header.add(new Formatter("finnt", "开票备注"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("撤销开票","revokeFin(document.all.tableform)");
	buttons.put("提交备注","subFinnt(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("fintype", "开票类型");
	hidden.put("notype", "订单类型");
	hidden.put("gctid", "店铺编号");
	hidden.put("pdtid", "商品代码");
	hidden.put("pdtnum", "数量");
	hidden.put("finno", "编号");

	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "fintype", "开票类型"));
	editors.add(new Editor("text", "notype", "订单类型"));
	editors.add(new Editor("text", "gctnm", "店铺名称"));
	editors.add(new Editor("text", "pdtid", "商品编号"));
	
	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "finnt", "开票备注"));

	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
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
	//商品代码下拉框
	$(document).ready(function(){
			$.ajax({
				 type:'post',
				 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
				 dataType:'json',
				 error:function(){
				   alert('获取数据错误！');
				 },
				 success:function(data){
							$("input[name=pdtid]").autocomplete(data,{
								max:10,
								matchContains:true,
								formatItem:function(data,i,max){
									return (data.proid + "=" + data.proname);
								}
							});	
							$("input[name=pdtid]").result(function(event, data, formatted) {
								if (data){
									var pid=data.proid;
									$("input[name=pdtid]").val(pid);
									

								}
							});
						}
				});
		});	
</script>

<script type="text/javascript">
		$(document).ready(function(){
			var grCli="";
			grCli=<%=dto.getBsc001()%>;
			if(grCli=="1501000000")
			{
				var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
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

<script language="javascript">

function subFinnt(obj) {
	if (!checkValue(document.forms[0])) {
		return false;
	}
	if (!delObj("chk")) {
		return false;
	}
	if (!preCheckForBatch()) {
		return false;
	}
	
	if (confirm("确认提交备注？")) {
		window.location.href = "/"
			+ lemis.WEB_APP_NAME
			+ "/finance/FinanceAction.do?method=saveFinnt&"
			+ getAlldata(document.all.tableform);
	}
};

function revokeFin(obj){
	if (!checkValue(document.forms[0])) {
		return false;
	}
	if (!delObj("chk")) {
		return false;
	}
	if (!preCheckForBatch()) {
		return false;
	}
	
	if (confirm("确认撤销开票？")) {
		window.location.href = "/"
			+ lemis.WEB_APP_NAME
			+ "/finance/FinanceAction.do?method=revokeFin&"
			+ getAlldata(document.all.tableform);
	}
};
 
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="修改开票申请" />
	<lemis:query action="/FinanceAction.do?method=queryBind&flag=store" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="FinanceAction.do" headerMeta="header" topic="开票信息" batchInputMeta="batchInput"
		hiddenMeta="hidden" mode="checkbox" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


