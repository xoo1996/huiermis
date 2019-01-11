<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("gctnm", "门店名称"));
	header.add(new Formatter("gctarea", "所属区域"));
	header.add(new Formatter("bb", "电池品牌"));
	header.add(new Formatter("bmresult", "电池名称"));
	header.add(new Formatter("batcountnum","赠送数量"));


	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","ivtyear","从年份","true"));
	editors.add(new Editor("text","ivtmonths","月","true"));
	editors.add(new Editor("text","ivtyearEnd","到年","true"));
	editors.add(new Editor("text","ivtmontht","月","true"));
	editors.add(new Editor("text","ivtgcltid","门店代码"));
	editors.add(new Editor("text","gctarea","所属区域"));
	editors.add(new Editor("text","bb","电池品牌"));
	editors.add(new Editor("text","bm","电池名称"));
	

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("关 闭","closeWindow(\"\")");
	
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("editor", editors);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script type="text/javascript">

$(document).ready(function(){
	
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

	var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
	
	$("input[name=ivtgcltid]").autocomplete(shops,{
		max : 10,
		matchContains : true
	});
 	$("input[name=ivtgcltid]").result(function(event, data, formatted) {
		if (data){
			var gnm = data[0].substring(data[0].indexOf("=")+1);
			var gid = data[0].substring(0,data[0].indexOf("="));
			$("input[name=ivtgcltid]").val(gid);
			$(this).parent().next().find("input").val(gid);
		}
	}); 


<%-- 	$.ajax({
		 type:'post',
		 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
		 dataType:'json',
		 error:function(){
		   alert('获取数据错误！');
		 },
		 success:function(data){
					$("input[name=pdtnm]").autocomplete(data,{
						max:10,
						matchContains:true,
						formatItem:function(data,i,max){
							return (data.proid + "=" + data.proname);
						}
					});	
					$("input[name=pdtnm]").result(function(event, data, formatted) {
						if (data){
							var pid=data.proid;
							var pnm = data.proname;
							$("input[name=pdtnm]").val(pnm);

						}
					});
				}
		}); --%>
//});

});
</script>
<lemis:base />
<lemis:body>
	<lemis:title title="赠送电池信息查询" />
	<lemis:query action="/BusinessAction.do?method=batCount" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="赠送电池详细信息"
		mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


