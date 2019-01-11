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
	header.add(new Formatter("fdtfno", "订单号")); //
	header.add(new Formatter("folctnm", "所属团体"));
	header.add(new Formatter("fdtcltnm", "个人客户")); 
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("fdtqnt", "数量"));
	header.add(new Formatter("fdtprc", "售价", "color:#000000;", TagConstants.DT_MONEY));
	header.add(new Formatter("folsdt", "发货日期","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("fdtodt", "退机日期","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("fdtexadt", "审核日期","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("gctarea", "所属区域"));
	header.add(new Formatter("fdtreason", "退机原因"));
	header.add(new Formatter("storetype", "直属加盟"));
	header.add(new Formatter("jqtype", "机器类型"));

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("fdtfno", "订单号码");
	hidden.put("folno","订单号");
	hidden.put("fdtqnt","数量");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "fdtfno", "订单号"));
	editors.add(new Editor("text", "gctarea", "所属区域"));
	editors.add(new Editor("text","folctnm","所属团体"));
	editors.add(new Editor("text", "fdtcltnm", "个人客户"));
	editors.add(new Editor("text", "foldreason", "退机原因")); 
	editors.add(new Editor("text", "beginend", "日期类型"));
	editors.add(new Editor("date","start","日期从"));
	editors.add(new Editor("date","end","到"));
	editors.add(new Editor("text", "storetype", "直属加盟"));
	editors.add(new Editor("text", "mtype", "机器类型"));
	
	
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	$(document).ready(function(){
		$(document).ready(function(){
			 var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		     
				if(shops.length==1)
				{
					$("input[name=folctnm]").val(shops[0].substring(shops[0].indexOf("=")+1,shops[0].length));
					$("input[name=folctnm]").attr("readonly","true");
				}
				
				$("input[name=folctnm]").autocomplete(shops,{
					max:10,
					matchContains:true,
					formatItem:function(data,i,max){
						return data[0].substring(0);
					}
				});
				
				$("input[name=folctnm]").result(function(event,data,formatted){
					if(data){
						var gid = data[0].substring(0,data[0].indexOf("="));
						var gnm=data[0].substring(data[0].indexOf("=")+1,data[0].length);
						$("input[name=folctnm]").val(gnm);
					/* 	$("input[@type=hidden][name=folctid]").val(gid);
						$("input[@type=hidden][name=folctid]").val(); */
						
					}
				});
		});
		
	});
</script>
<lemis:base />
<lemis:body>
	<lemis:title title="退机查询" />
	<lemis:query action="/OrderAction.do?method=requery2" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="OrderAction.do" headerMeta="header" topic="退机数据"
		hiddenMeta="hidden" mode="radio" />
			
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


