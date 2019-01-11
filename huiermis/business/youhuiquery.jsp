<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
List<Formatter> header = new ArrayList<Formatter>();
header.add(new Formatter("gctnm","团体客户"));
header.add(new Formatter("ictnm","用户姓名"));
header.add(new Formatter("gctarea","所属区域"));
//header.add(new Formatter("pdtid", "商品代码"));
header.add(new Formatter("pdtnm", "商品名称"));
header.add(new Formatter("pdtxh", "助听器型号"));
header.add(new Formatter("pdtyj", "原价"));
header.add(new Formatter("fdtqnt", "数量"));
/* header.add(new Formatter("fdtdisc", "商品扣率")); */
header.add(new Formatter("fdtrprc", "回款额"));
header.add(new Formatter("pdtyouhui", "优惠额"));
header.add(new Formatter("jsid", "编码信息"));
header.add(new Formatter("tmkordate","销售日期"));
header.add(new Formatter("chgnt", "备注"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	
	List<Editor> editors = new ArrayList<Editor>();
	
	editors.add(new Editor("text","gctnm","客户名称"));
	editors.add(new Editor("text","cltnm","用户姓名"));
	editors.add(new Editor("text","gctarea","所属区域"));
	editors.add(new Editor("text","gcttype","客户类型"));
    editors.add(new Editor("text","pdtnm","商品名称"));
	editors.add(new Editor("date","start", "收费日期从"));
	editors.add(new Editor("date","end", "到"));
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<script language="javascript">
		//显示详细信息
  		function detailci(obj){
			var t = editObj("chk");
			if(!t){
				return t;
			}
			obj.action = '<html:rewrite page="/CustomizationAction.do?method=print&"/>'+getAlldata(obj);			
			obj.submit();
  		};
  		function print(obj) {
  			var t = editObj("chk");
  			if (!t) {
  				return t;
  			}
  			else {
  				obj.action = '<html:rewrite page="/CustomizationAction.do?method=barcode&"/>' + getAlldata(obj);
  				obj.submit();
  			} 
  		};
	</script>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
$(document).ready(function(){
	$("input[name=pdtnm]").val("优惠");
	$("input[name=pdtnm]").attr('readonly','readonly');
	//HF0001 销售一区(区域一) HF0010 销售二区（区域二）  HF0011销售三区 (区域三) HF0012 销售三区   HF0019 销售四区 (区域四)
	//HF0005  销售五区 (区域五)  HF0014 销售七区  (区域七)  销售六区（杭州区）
	var grCli_cx="<%=grCli_cx%>";
    //$("input[name=gctnm]").val(grCli_cx);
	if(grCli_cx=="HF0001"){
		$("select[name=gctarea]").val("区域一");
		$("select[name=gctarea]").attr("disabled",true);
		
		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域一");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
		    $("select[name=gctarea]").val("区域一");
		});
	}
	if(grCli_cx=="HF0010"){
		$("select[name=gctarea]").val("区域二");
		$("select[name=gctarea]").attr("disabled",true);
		
		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域二");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域二");
		});
	}
	if(grCli_cx=="HF0011"){
		$("select[name=gctarea]").val("区域三");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域三");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域三");
		});
	}
	if(grCli_cx=="HF0019"){
		$("select[name=gctarea]").val("区域四");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域四");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域四");
		});
	}
	if(grCli_cx=="HF0024"){
		$("select[name=gctarea]").val("区域五");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域五");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域五");
		});
	}
	if(grCli_cx=="HF0013"){
		$("select[name=gctarea]").val("区域六");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域六");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域六");
		});
	}
	if(grCli_cx=="HF0014"){
		$("select[name=gctarea]").val("区域七");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域七");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域七");
		});
	}
	if(grCli_cx=="HF0002"){
		$("select[name=gctarea]").val("区域八");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域八");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域八");
		});
	}
	if(grCli_cx=="HF0023"){
		$("select[name=gctarea]").val("区域九");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域九");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域九");
		});
	}
	if(grCli_cx=="HF0012"){
		$("select[name=gctarea]").val("区域十");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域十");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域十");
		});
	}
	if(grCli_cx=="HF0025"){
		$("select[name=gctarea]").val("区域十一");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域十一");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域十一");
		});
	}
	if(grCli_cx=="HF0026"){
		$("select[name=gctarea]").val("区域十二");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域十二");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域十二");
		});
	}
	if(grCli_cx=="HF0027"){
		$("select[name=gctarea]").val("区域十三");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域十三");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域十三");
		});
	}
	if(grCli_cx=="HF0028"){
		$("select[name=gctarea]").val("区域十四");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=重置[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("区域十四");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=查询[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("区域十四");
		});
	}
	/*
	else{
		var shops = "0123456789销售一区=杭州区,0123456789销售二区=区域二,0123456789销售三区=区域三,0123456789销售四区=区域四,0123456789销售五区=区域五,0123456789销售六区=区域六,0123456789销售七区=区域七,0123456789=其他类".split(",");
		alert(shops);
		$("input[name=gctarea]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=gctarea]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=gctarea]").val(gnm);
			}
		});
	}
    */
	
	
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
	<lemis:title title="定制记录查询" />
	<lemis:query action="/BusinessAction.do?method=youhuiquery"
		editorMeta="editor" topic="查询条件" />
	<lemis:table action="BusinessAction.do" headerMeta="header"
		topic="定制记录" hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


