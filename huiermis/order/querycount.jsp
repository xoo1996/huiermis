<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("fdtfno", "订单号"));
	header.add(new Formatter("gctnm", "所属团体"));
	header.add(new Formatter("cltnm", "个人客户")); 
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("tmksid", "机身编号"));
	header.add(new Formatter("fdtqnt", "数量"));
	header.add(new Formatter("fdtsqnt", "发货"));
	header.add(new Formatter("fdtdprc", "价格", "color:#000000;", TagConstants.DT_MONEY));
	header.add(new Formatter("foldt", "订货日期"));
	header.add(new Formatter("folexdt", "审核日期"));
	header.add(new Formatter("folsdt", "发货日期"));
	header.add(new Formatter("folway", "发货方式"));
	header.add(new Formatter("folsno","快件号"));
	header.add(new Formatter("folsta", "订单状态"));
	header.add(new Formatter("foltype", "订单类型"));
	header.add(new Formatter("foloprnm", "订单审核员"));
	header.add(new Formatter("fdtnt", "备注"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("订单明细","print(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("fdtfno", "订单号码");
	hidden.put("folno","订单号");
	hidden.put("foltype","订单类型");
	hidden.put("folindctid","个人客户代码");
	hidden.put("folsta","订单状态");
	hidden.put("gctnm","所属团体");
	hidden.put("gctid","所属团体");
	hidden.put("folctid","团体客户编号");
	hidden.put("foldt","订货日期");
	hidden.put("folsta","订单状态");
	hidden.put("cltnm","个人客户");
	hidden.put("pdtid","商品代码");
	
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","gctnm","所属团体"));
	editors.add(new Editor("text", "cltnm", "个人客户"));
	editors.add(new Editor("text", "foltype", "订单类型"));
	editors.add(new Editor("text", "pdtid", "商品代码"));
	editors.add(new Editor("text", "pdtnm", "商品名称"));
	editors.add(new Editor("date","exstart","审核通过日期从","true"));
	editors.add(new Editor("date","exend","到"));
	
	
	
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
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
						/* 	$("input[@type=hidden][name=folctid]").val(gid);
						$("input[@type=hidden][name=folctid]").val(); */
					
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
									var pnm = data.proname;
									$("input[name=pdtid]").val(pid);
									$("input[name=pdtnm]").val(pnm);
									$(this).parent().next().find("input").val(pid);

								}
							});
						}
				});
			//$("select[name=foltype]").val("make");
			
	});
</script>
<script language="javascript">
//查看订单明细
	function print(obj){
	var t = editObj("chk");
	if(!t){
		return t;
	}
	obj.action = '<html:rewrite page="/OrderAction.do?method=orderDetail&tp2=s&"/>'+getAlldata(obj);	
	obj.submit();
	};
	</script>

<lemis:base />
<lemis:body>
	<lemis:title title="订单查询" />
	<lemis:query action="/OrderAction.do?method=querycount" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="OrderAction.do" headerMeta="header" topic="订单信息"
		hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


