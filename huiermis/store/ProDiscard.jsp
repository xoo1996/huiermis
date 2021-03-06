<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<%  

	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("报废","discard(document.all.tableform)");
	/*buttons.put("惠耳打印","huier_print()");
	buttons.put("捷文打印","jiewen_print()");*/
	buttons.put("关 闭","closeWindow(\"\")");

	List<Formatter> header=new ArrayList<Formatter>();
	header.add(new Formatter("gctnm","所属团体"));
	header.add(new Formatter("stoproid","商品代码"));
	header.add(new Formatter("stoproname","商品名称"));
	header.add(new Formatter("stoproprice","单价",TagConstants.DF_LEFT,TagConstants.DT_MONEY));
	header.add(new Formatter("stoamount","数量"));
	header.add(new Formatter("stoamountun","数量单位"));
	header.add(new Formatter("stoactype","动作"));
	header.add(new Formatter("stodate","日期","color:#000000;", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("stodisc","是否已报废"));
	header.add(new Formatter("storemark","备注"));

	
	
	List<Editor> editors = new ArrayList<Editor>();
	//editors.add(new Editor("text", "stoproid", "年","true"));
	//editors.add(new Editor("text", "stoproname", "月","true")); 
	editors.add(new Editor("text", "gctnm", "所属团体")); 
	editors.add(new Editor("text","stoproid","商品代码"));
	editors.add(new Editor("text","stoproname","商品名称"));
	editors.add(new Editor("text","stodisc","是否已报废"));
	editors.add(new Editor("date","start","入库日期从"));
	editors.add(new Editor("date","end","到"));
	
	
	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "stoamount", "数量"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("stoid", "库存编号");
	hidden.put("stogrcliid", "团体客户代码");
	hidden.put("stoproid", "商品代码");
	hidden.put("stoproname", "商品名称");
	hidden.put("stoproprice", "单价");
	hidden.put("stoamountun", "数量单位");
	


	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("batchInput", batchInput);
	pageContext.setAttribute("hidden",hidden);
	pageContext.setAttribute("header",header);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<script language="javascript">	
$(document).ready(function(){
	 var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
    
		if(shops.length==1)
		{
			$("input[name=gctnm]").val(shops[0].substring(shops[0].indexOf("=")+1,shops[0].length));
			$("input[name=gctnm]").attr("readonly","true");
		}
		
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
			/* 	$("input[@type=hidden][name=folctid]").val(gid);
				$("input[@type=hidden][name=folctid]").val(); */
				
			}
		});
		
		//$("input[name=stoproid]").bind("click",function(){
			$.ajax({
				 type:'post',
				 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
				 data:"proType=all",
				 dataType:'json',
				 error:function(){
				   alert('获取数据错误！');
				 },
				 success:function(data){
							$("input[name=stoproid]").autocomplete(data,{
								max:10,
								matchContains:true,
								formatItem:function(data,i,max){
									return (data.proid + "=" + data.proname);
								}
							});	
							$("input[name=stoproid]").result(function(event, data, formatted) {
								if (data){
									var pid = data.proid;
									var pnm = data.proname;
									$("input[name=stoproid]").val(pid);
									$("input[name=stoproname]").val(pnm);
										
								}
							});
						}
				});
		//});
});
	
	 function discard(obj) {
		/* obj.action = '<html:rewrite page="/ProDiscardAction.do?method=modifyState"/>';
		obj.submit(); */
		
		 window.location.href = "/" + lemis.WEB_APP_NAME
			+ "/store/ProDiscardAction.do?method=modifyState&"
			+ getAlldata(document.all.tableform);
	};  
</script>

<script type="text/javascript">
	function compute(e) {
		var id = $(e.target).attr("id");
		var suffix = id.substr(13);
		$("#subCheckbox" + suffix).attr("checked",true);
	};
	
	 $(document).ready(function(e) {
			$("input[name=stoamount]").bind("mousedown", function(e) {
				compute(e);
			});
	});
</script>


<lemis:base />
<lemis:body>
	<lemis:title title="报废" />
	<lemis:query action="/ProDiscardAction.do?method=query" 
		editorMeta="editor" topic="查询条件" />
	<lemis:table action="/ProDiscardAction.do" headerMeta="header" hiddenMeta="hidden"
	batchInputMeta="batchInput" batchInputType="update"
	 topic="库存明细" mode="radio"/>

	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>