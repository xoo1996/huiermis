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
	header.add(new Formatter("webname", "姓名"));
	header.add(new Formatter("pdtnm", "机型型号"));
	//header.add(new Formatter("pdtnm", "机型名称"));
	header.add(new Formatter("webnum", "台数"));
	header.add(new Formatter("webmoney", "销售金额"));
	header.add(new Formatter("webcall", "电话"));
	header.add(new Formatter("webfrom", "来源"));
	//header.add(new Formatter("webintro", "介绍人"));

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "webname", "姓名", "true"));
	batchInput.add(new Editor("text", "pdtnm", "机型型号", "true"));
	//batchInput.add(new Editor("text", "pdtnm", "机型名称", "true"));
	batchInput.add(new Editor("nnnn", "webnum", "台数", "true"));
	batchInput.add(new Editor("text", "webmoney", "销售金额", "true"));
	batchInput.add(new Editor("text", "webcall", "电话", "false"));
	batchInput.add(new Editor("text", "webfrom", "来源", "true"));
	//batchInput.add(new Editor("text", "webintro", "介绍人", "false"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("批量保存", "commit(document.forms[0])");
	buttons.put("返 回","history.back()");
	buttons.put("关 闭", "closeWindow(\"\")");

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
	function chk(e) {
		var id = $(e.target).attr("id");  //
		var suffix = id.substr(10); 
		var suffix2 = id.substr(6); 
		var webname=$("#webname" + suffix2).val();
	    if(webname!='')
	    {
	    	$("input[name=chk][value="+suffix+"]").attr("checked",true);
	    }
	    else
	    {
	    	$("input[name=chk][value="+suffix+"]").attr("checked",false);
	    }
			
	};
	$("input[name=webname]").bind("blur",function(e){
		chk(e);
	});
	function commit(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		var t = delObj("chk");
		if(!t){
			return t;
		}
		t = preCheckForBatch();
		if(!t){
			return t;
		}
		obj.action = '<html:rewrite href="/huiermis/business/BusinessAction.do?method=saveWebSell&tp=s&"/>'+getAlldata(document.all.tableform);
		if (confirm("确实要录入并提交吗？")) {
			obj.submit();
		}
	};
    
</script>

<script>

	<%-- $(document).ready(function(e) {
		
		$.ajax({
			 type:'post',
			 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
			 data:"proType=nom",
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
								var pid = data.proid;
								var pnm = data.proname;
								var id = $(this).parent().find("input").attr("id");
								var suffix = id.substr(5);
								$("#pdtid" + suffix).val(pid);
								$("#pdtnm" + suffix).val(pnm);
							}
						});
					}
			});
	//});
	
	}); --%>
</script>

<lemis:body>
<lemis:base />

    <lemis:title title="地方网络销售统计" />
	<lemis:table topic="地方网络销售统计"
		action="/BusinessAction.do?method=saveWebSell" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false"
		 batchInputType="insert" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	
</lemis:body>
</html>


