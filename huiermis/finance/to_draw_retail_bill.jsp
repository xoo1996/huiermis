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
	header.add(new Formatter("typeno", "编号"));   
	header.add(new Formatter("gctnm", "店铺名称"));
	header.add(new Formatter("ictnm", "个人客户"));
	header.add(new Formatter("pdtid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("tmksid", "定制机条形码"));
	header.add(new Formatter("chgdt", "收费日期"));
	header.add(new Formatter("pdtprc", "原价"));
	header.add(new Formatter("sellprc", "售价"));
	header.add(new Formatter("pdtnum", "数量"));
	header.add(new Formatter("finprccount", "总额"));
	
	header.add(new Formatter("retailname", "开票抬头"));
	header.add(new Formatter("retailtaxno", "开票税号"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("提交开票","submitFin(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("typeno", "编号");
	hidden.put("gctnm", "店铺名称");
	hidden.put("gctid", "店铺编号");
	hidden.put("ictnm", "个人客户");
	hidden.put("ictid", "客户编号");
	hidden.put("pdtid", "商品代码");
	hidden.put("pdtnm", "商品名称");
	hidden.put("band", "商品品牌");
	hidden.put("tmksid", "定制机条形码");
	hidden.put("chgdt", "收费日期");
	hidden.put("redt", "收费日期");
	hidden.put("pdtprc", "原价");
	hidden.put("pdtnum", "数量");
	hidden.put("finrate", "开票扣率");
	hidden.put("finprc", "含税单价");
	hidden.put("finprccount", "总额");
	hidden.put("invoicecode", "发票代码");
	hidden.put("invoiceno", "发票编号");
	hidden.put("sellprc", "实际售价");	
	hidden.put("finrate", "开票扣率");
	hidden.put("finprc", "开票单价");
	hidden.put("comaddr", "公司地址");
	hidden.put("depositbank", "开户行");
	hidden.put("depositid", "开户账号");
	hidden.put("specialtel", "开户账号");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "notype", "订单类型"));
	editors.add(new Editor("text", "typeno", "订单号或者收费号"));
	editors.add(new Editor("text", "gctnm", "团体客户"));
	editors.add(new Editor("text", "ictnm", "个人客户"));
	editors.add(new Editor("text", "pdtid", "商品编号"));
	editors.add(new Editor("date","start","收费日期从"));
	editors.add(new Editor("date","end","到"));
/* 	editors.add(new Editor("text", "fintel", "开票人电话")); */

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "retailname", "开票抬头", "true"));
    batchInput.add(new Editor("text", "retailtaxno", "开票税号", "true"));
	

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

function submitFin(obj) {
	var t = delObj("chk");//校验有没有可提交项
	if (!t) {
		return t;
	}
	t = preCheckForBatch();//对必录项校验
	if (!t) {
		return t;
	}
	
/* 	var messageStr = "请输入本次开票人电话";
	var defaultStr = "在此输入电话";
	var fintel = window.prompt(messageStr, defaultStr);
	
	if(isMobileNumber(fintel)!=true){
		alert(isMobileNumber(fintel));
		return false;
	}	 */
	
	if (confirm("确认提交开票")) {
		window.location.href = "/"
			+ lemis.WEB_APP_NAME
			+ "/finance/FinanceAction.do?method=saveFinance&notype="
			+ "<%=notype%>" + "&fintype="
			+"retail"+"&"
			+ getAlldata(document.all.tableform);
	}
};

/* function isMobileNumber(phone) {  
    var flag = false;  
    var message = "";  
    var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[4-9]{1})|(18[0-9]{1})|(199))+\d{8})$/;  
    if (phone == '') {  
        // console.log("手机号码不能为空");  
        message = "手机号码不能为空！";
        return message;
    } else if (phone.length != 11) {  
        //console.log("请输入11位手机号码！");  
        message = "请输入11位手机号码！";  
        return message;
    } else if (!myreg.test(phone)) {  
        //console.log("请输入有效的手机号码！");  
        message = "请输入有效的手机号码！";
        return message;        
    } else {  
        flag = true;  
    }  
    if (message != "") {  
        // alert(message);  
    }  
    return flag;  
};
  */
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="普通和定制商品开发票（零售）" />
	<lemis:query action="/FinanceAction.do?method=queryToDrawRetailBill" editorMeta="editor"
		topic="查询条件" />
	<lemis:table action="FinanceAction.do" headerMeta="header" topic="订单信息" batchInputMeta="batchInput" 
		hiddenMeta="hidden" mode="checkbox" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


