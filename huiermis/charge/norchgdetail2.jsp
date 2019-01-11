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
String score=(String)request.getSession().getAttribute("score");
String coin=(String)request.getSession().getAttribute("coin");
String change=(String)request.getSession().getAttribute("change");
String user=(String)request.getSession().getAttribute("user");
String sco=(String)request.getSession().getAttribute("sco");
String fee=(String)request.getSession().getAttribute("fee");
String rate=(String)request.getSession().getAttribute("rate");
String idcard=(String)request.getSession().getAttribute("idcard");
String phone=(String)request.getSession().getAttribute("phone");
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("pdtid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("fdtprc", "商品单价"));
	header.add(new Formatter("fdtqnt", "售出数量"));
	header.add(new Formatter("fdtdisc", "商品扣率"));
	header.add(new Formatter("fdtrprc", "实际收费"));
	header.add(new Formatter("chgnt", "备注"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "商品代码", "true"));
	batchInput.add(new Editor("text", "pdtnm", "商品名称", "true"));
	batchInput.add(new Editor("text", "fdtprc", "商品单价", "true"));
	batchInput.add(new Editor("text", "fdtdisc", "商品扣率", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "售出数量", "true"));
	batchInput.add(new Editor("text", "fdtrprc", "实际收费", "true"));
	batchInput.add(new Editor("text", "chgnt", "备注", "false"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("打 印","print()");
	buttons.put("批量保存", "batchSubmit(document.all.tableform)");
	buttons.put("返 回","history.back()");
	buttons.put("关 闭", "closeWindow(\"\")");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "收费号"); 
	
	pageContext.setAttribute("hidden", hidden);
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
	function batchSubmit() {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		} 
		$.getJSON("<%=basePath%>ChargeAction.do?method=checkNormalDisc&tp=q&" +
				 getAlldata(document.all.tableform)+"&ictgctid=" + $("input[name=ictgctid]").val(),
				 function(data) {
					 var flag;
					 var str="";
					 if(null!=data && data.tdspvo!='')
				     {
						 $.each(data,function(i,mindis)
						 {
							 str+="第"+(i+1)+"行商品的最小扣率为"+data[i].mindis+"\n";
							 flag=false;
						 });
						
				     }
					 if(flag==false)
					 {
					 	if(confirm(str+"输入的扣率已低于最低扣率，确实要批量保存吗?","是","否"))
					 	{  
						 	save();
					 	}
					 }
					 else 
					 {
						  save();
					 }  
				 
			});
	}
    
	function save() {
		compute3();
		var num = $("input[name=ncdnum]").val();
		if(num){
		}else{
			alert("请输入客户人数！");
			return false;
		}
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}/* 
		if(!confirm("确认输入无误?")){
			window.event.returnValue = false;
		}else{ */
			//知音系统判断
		check();
		var coin=$('input[name=coin]').val();
		var coinnum=$('input[name=coinnum]').val();
		var score=$('input[name=score]').val();
		var sco=$('input[name=sco]').val();
		var num=$('input[name=num]').val();
		var mon=$('input[name=balance]').val();
		var money=$('input[name=money]').val();
		var fee=$('input[name=fee]').val();
		$('input[name=money]').val(parseInt(mon-coinnum-fee*num));
		if((parseInt(coinnum)!=coinnum)||(parseInt(coinnum)<0)){
			alert("使用惠耳币数目输入错误!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$('input[name=money]').val(parseInt(mon-fee*num));
			return false;
		}
		if((parseInt(num)!=num)||(parseInt(num)<0)){
			alert("代金券数目输入错误!");
			$("input[name=num]").val(0);
			$('input[name=money]').val(parseInt(mon-coinnum));
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("惠耳币不足");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$('input[name=money]').val(parseInt(mon-fee*num));
			return false;
		}
		if(parseInt(score)<parseInt(sco*num)){
			alert("积分不足");
			$("input[name=num]").val(0);
			$('input[name=money]').val(parseInt(mon-coinnum));
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum-fee*num))){
			var m=parseInt(mon-coinnum-fee*num);
			alert("耳背机实付金额为"+m+",确认后请再次提交");
			$('input[name=money]').val(m);
			return false;
		}
		if(<%=phone.length() %>!=8&&<%=phone.length() %>!=11&&<%=phone.length() %>!=12){
			alert("该客户信息中联系方式无效,若要成为惠耳积分商城会员，请先完整客户信息");
		}
		
		if (confirm("确实要收费吗？")) {
			$("input[value='批量保存']").attr('disabled','true');
			window.location.href = "/" + lemis.WEB_APP_NAME
					+ "/charge/ChargeAction.do?method=saveNormalCharge&" +
					 getAlldata(document.all.tableform)+ "&ictid=" + $("input[name=ictid]").val() +
					 "&ictgctid=" + $("input[name=ictgctid]").val() +
					"&folctnm=" + $("input[name=folctnm]").val() + "&ictnm=" + $("input[name=ictnm]").val() +
					"&ictgender=" + $("input[name=ictgender]").val() + "&ictaddr=" + $("input[name=ictaddr]").val() +
					"&icttel=" + $("input[name=icttel]").val() + "&ictpnm=" + $("input[name=ictpnm]").val() +
					"&ncdypname=" + $("input[name=ncdypname]").val()+"&ncdnum=" + $("input[name=ncdnum]").val()+
					"&balance=" + $("input[name=balance]").val()+"&sco=" + $("input[name=sco]").val()+
					"&fee=" + $("input[name=fee]").val()+"&num=" + $("input[name=num]").val()+
					"&coinnum=" + $("input[name=coinnum]").val()+"&money=" + $("input[name=money]").val(); 
		}
		else
			return t;
		//}
	}
	function check(){
		//计算勾选耳背机总价
		obj = document.getElementsByName("chk");
		var pdtid = '';
		var rprc = '';
		var num=0;
		for(var i=1;$("#fdtrprc_row" + i).val();i++){
			if(obj[i-1].checked){
				pdtid+=','+($("#pdtid_row" + i).val());
				rprc+=','+($("#fdtrprc_row" + i).val());
			}
		}
		$.ajax({
			 type:'post',
			 url:'<%=basePath%>ChargeAction.do?method=queryPdtid&pdtid='+
					 pdtid+'&fdtrprc='+rprc,
			 async: false,
			 data:"",
			 dataType:'json',
			 error:function(){
			   alert('获取数据错误！');
			 },
			 success:function(data){
					var num = data[0].num;
					$("input[name=balance]").val(num);
					}
			});
	};
	
	function print(){
		var cid = $("input[name=chgid]").val();
		if(cid == -1){
			alert("无收费信息，请保存后再打印！");
			return false;
		}
		window.location.href = "/" + lemis.WEB_APP_NAME
		+ "/charge/ChargeAction.do?method=print&chgid=" + $("input[name=chgid]").val() + "&"
		+ getAlldata(document.all.tableform);
	}
</script>

<script>

	function compute1(e) {
		var id = $(e.target).attr("id");
		var suffix = id.substr(7);
		var value = $("#fdtqnt" + suffix).val() * $("#fdtprc" + suffix).val()*$("#fdtdisc" + suffix).val();
		value = value.toFixed(2);//保留两位有效数字
		$("#fdtrprc" + suffix).val(value);
		//计算耳背机总价
		var pdtid = $("#pdtid_row1").val();
		var rprc = $("#fdtrprc_row1").val();
		var num=0;
		for(var i=2;$("#fdtrprc_row" + i).val();i++){
			pdtid+=','+($("#pdtid_row" + i).val());
			rprc+=','+($("#fdtrprc_row" + i).val());
		}
		$.ajax({
			 type:'post',
			 url:'<%=basePath%>ChargeAction.do?method=queryPdtid&pdtid='+
					 pdtid+'&fdtrprc='+rprc,
			 data:"",
			 dataType:'json',
			 error:function(){
			   alert('获取数据错误！');
			 },
			 success:function(data){
					var num = data[0].num;
					$("input[name=balance]").val(num);
					$("input[name=money]").val(num);
					}
			});
	};
	function compute2(e) {
		var id = $(e.target).attr("id");
		var suffix = id.substr(7);
		var value = $("#fdtrprc" + suffix).val()/$("#fdtqnt" + suffix).val()/$("#fdtprc" + suffix).val();
		//value = changeTwoDecimal(value);
		//value = value.toString().substring(0, value.toString().indexOf(".")+ 3);
		value = value.toFixed(2);//保留两位有效数字
		$("#fdtdisc" + suffix).val(value);
		
	};
	
	function compute3() {
		var pdtid = $("#pdtid_row1").val();
		var qnt = $("#fdtqnt_row1").val();
		var num=0;
		for(var i=2;$("#fdtrprc_row" + i).val();i++){
			pdtid+=','+($("#pdtid_row" + i).val());
			qnt+=','+($("#fdtqnt_row" + i).val());
		}
		$.ajax({
			 type:'post',
			 url:'<%=basePath%>ChargeAction.do?method=queryFdtqnt&pdtid='+
					 pdtid+'&fdtqnt='+qnt,
			 data:"",
			 dataType:'json',
			 error:function(){
			   alert('获取数据错误！');
			 },
			 success:function(data){
					var num = parseInt(data[0].num);
						var p = parseInt($("input[name=ncdnum]").val());
						var c=(num/p);
						 if(num==0){
							 ;
						 }else if(c<1||c>2){
							alert("客户人数输入有误，耳背机售出数量/客户人数 的值应该在1-2之间");
							$("input[name=ncdnum]").val('');
						}
					}
			});
	}; 
	
	function computeL1() {
		var pdtprc=$('input[name=balance]').val();//gai
		var mon=$('input[name=balance]').val();
		var fee=$('input[name=fee]').val();
		var num=$('input[name=num]').val();
		var rate=$('input[name=rate]').val();
		var coinnum=$('input[name=coinnum]').val();
		var sum=num*fee;
		if((pdtprc*rate/100)<sum){
			alert("购买积分代金券金额不得高于"+rate+"%");
			$("input[name=num]").val(0);
			//$("input[name=money]").val(parseInt(pdtprc));
			$('input[name=money]').val(parseInt(mon-coinnum));
		}else{
			var money=mon-sum-coinnum;
			$("input[name=money]").val(parseInt(money));
		}
	}
	function computeL2() {
		var pdtprc=$('input[name=balance]').val();//gai
		var mon=$('input[name=balance]').val();
		var coinnum=$('input[name=coinnum]').val();
		var fee=$('input[name=fee]').val();
		var num=$('input[name=num]').val();
		var sum=num*fee;
		//if(coinnum<=pdtprc-1){
			if(((coinnum<=pdtprc-1)&&(pdtprc*0.3)<coinnum)){
				alert("惠耳币抵扣100%助听器金额或者不得高于30%助听器价格");
				$("input[name=coinnum]").val(0);
				$("input[name=changecoin]").val(0);
				$("input[name=money]").val(parseInt(mon-sum));
			//}
		}else{
			var money=mon-coinnum-sum;
			$("input[name=money]").val(parseInt(money));
			$("input[name=changecoin]").val(coinnum);
		}
	}
	
	$(document).ready(function(e) {
		$("input[name=fdtprc]").attr('readonly','readonly');
		$("input[name=pdtnm]").attr('readonly','readonly');
		
		$("input[name=fdtrprc]").bind("click",function(e){
			compute1(e);
			compute3();
		}).bind("blur",function(e){
			compute2(e);
		});
		$("input[name=ncdnum]").bind("keyup",function(e){
			compute3();
		});
		$("input[name=ncdnum]").val(1);
		$("input[name=balance]").val(0.0);
		/* var mon=$('input[name=balance]').val();
		$("input[name=money]").val(parseInt(mon)); */
		/* $("input[name=fdtdisc]").bind("blur",function(){
			var discount = $("input[name=fdtdisc]").val();//商品扣率
			if(discount < 0.7){
				alert("扣率不能低于0.7，如果要低于0.7请想总部申请！");
				$("input[name=fdtdisc]").val("");
			}
		}); */
		
	
		
<%-- 	var products = "<%=session.getServletContext().getAttribute("nomProductList")%>".replace("{","").replace("}","").split(", ");
	
	$("input[name=pdtid]").autocomplete(products,{
		max : 10,
		matchContains : true,
		formatItem: function(data, i, max) {
			return data[0].substring(0,data[0].indexOf(":"));
		}
	}); 
	$("input[name=pdtid]").result(function(event, data, formatted) {
		if (data){
			var pid = data[0].substring(0,data[0].indexOf("="));
			var pnm = data[0].substring(data[0].indexOf("=")+1,data[0].indexOf(":"));
			var prc = data[0].substring(data[0].indexOf(":")+1);
			var id = $(this).parent().find("input").attr("id");
			var suffix = id.substr(5);
			$("#pdtid" + suffix).val(pid);
			$("#pdtnm" + suffix).val(pnm);
			$("#fdtprc" + suffix).val(prc);
		}
	}); --%>
	
	//$("input[name=pdtid]").bind("click",function(){
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
								var prc=data.proprc;
								var id = $(this).parent().find("input").attr("id");
								var id = $(this).parent().find("input").attr("id");
								var suffix = id.substr(5);
								$("#pdtid" + suffix).val(pid);
								$("#pdtnm" + suffix).val(pnm);
								$("#fdtprc" + suffix).val(prc);
									
							}
						});
					}
			});
	//});
	
	});
</script>

<lemis:body>
<lemis:base />

    <lemis:title title="普通商品收费详情" />
   
	<lemis:tabletitle title="收费信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		 <html:form action="/ChargeAction.do?method=saveNormalCharge"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<html:hidden property = "chgid"/>
				<lemis:texteditor property="folctnm" label="所属团体客户" disable="true"/>
				<lemis:texteditor property="ictnm" label="用户名称" disable="true"/>
				<lemis:texteditor property="ictgender" label="性别" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="ictaddr" label="用户地址" disable="true" />
				<lemis:texteditor property="icttel" label="用户电话" disable="true"/>
				<lemis:texteditor property="ictpnm" label="家长姓名" disable="true"/>
			</tr>
			<tr>
				<td>收费日期</td>	
				<td><lemis:operdate /></td>
				<%-- <lemis:texteditor property="chgid" label="收费号" disable="true" /> --%>
				<lemis:texteditor property="ictgctid" label="客户代码" disable="true" />
				<lemis:texteditor property="ncdypname" label="验配师姓名" disable="false" />		
			</tr>
			<tr>
				<lemis:texteditor property="ncdnum" label="客户人数" disable="false" required="true"/>
				<lemis:texteditor property="balance" label="耳背机销售金额" disable="false" required="true"/>
				<lemis:texteditor property="score" label="积分余额" required="false"
						disable="true" value="<%=score %>"/>
			</tr>
			<tr>
				<lemis:texteditor property="coin" label="惠耳币余额" required="false"
						disable="true" value="<%=coin %>"/>
				<lemis:texteditor property="coinnum" label="使用惠耳币数量" required="false"
						disable="false" value="0" onkeyup="computeL2()"/>
				<lemis:texteditor property="changecoin" label="抵扣现金：(元)" required="false"
						disable="false" value="0"/>
			</tr>
			<tr>
					<lemis:texteditor property="change" label="代金券" required="false"
						disable="true" value="<%=change %>"/>
					<lemis:texteditor property="num" label="代金券兑换数量" required="false"
						disable="false" value="0" onkeyup="computeL1()"/>
					<lemis:texteditor property="money" label="耳背机实收金额" required="false"
						disable="false" />
			</tr>
			<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="sco" id="sco" value="<%=sco %>">
				<input type="hidden" name="fee" id="fee" value="<%=fee %>">
				<input type="hidden" name="rate" id="rate" value="<%=rate %>">
		</html:form>
	</table>
	<lemis:table topic="商品明细录入"
		action="/ChargeAction.do?method=batchSubmit" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false"
		 batchInputType="insert" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	
</lemis:body>
</html>


