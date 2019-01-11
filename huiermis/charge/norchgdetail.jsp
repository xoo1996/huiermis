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
	String bi=(String)request.getSession().getAttribute("bi");

	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("pdtid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("fdtprc", "商品单价"));
	header.add(new Formatter("fdtqnt", "售出数量"));
	//header.add(new Formatter("ncdnum", "客户人数"));
	header.add(new Formatter("fdtdisc", "商品扣率"));
	header.add(new Formatter("fdtrprc", "实际收费"));
	header.add(new Formatter("chgnt", "备注"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "商品代码", "true"));
	batchInput.add(new Editor("text", "pdtnm", "商品名称", "true"));
	batchInput.add(new Editor("text", "fdtprc", "商品单价", "true"));
	batchInput.add(new Editor("text", "fdtdisc", "商品扣率", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "售出数量", "true"));
	//batchInput.add(new Editor("-nnn", "ncdnum", "客户人数", "true"));
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
<script src="/huiermis/js/BatBrandJson.js"></script>
<script src="/huiermis/js/BatTypeJson.js"></script>
 <script type="text/javascript">
 //电池级联
        $(function () {
            $.each(batbrand, function (k, p) { 
                var option = "<option value='" + p.braID + ',' + p.braName +"'>" + p.braName + "</option>";
                $("#batbra").append(option);
            });
            $("select[name=batbra]").val("0,无");
            if($("select[name=batbra]").val()=="0,无"){
            	var option = "<option value='0'>无</option>";
            	$("#bm").append(option);
            	$("select[name=bm]").val("0");
            }
            $("#batbra").change(function () {
                var selValue = $(this).val().split(","); 
                $("#bm option:gt(0)").remove();
                if(selValue[0] == "0"){
                	$.each(battype, function (k, p) { 
                        if (p.braID == selValue[0]) {
                            var option = "<option value='" + p.battype +"'>" + p.batName + "</option>";
                            $("#bm").append(option);
                        }
                    });
                	$("select[name=bm]").val("0");
                }else if(selValue[0]!=""){
                	$.each(battype, function (k, p) { 
                        if (p.braID == selValue[0]) {
                            var option = "<option value='" + p.battype +"'>" + p.batName + "</option>";
                            $("#bm").append(option);
                        }
                    });
                }
                
            });
        });
    </script>



<script>
	$(document).ready(function(){
		$("input[name=gby]").val('0');
	});
</script>

<script>
 	$(document).ready(function(){
		if("<%=bi%>"=="t"){
			$("#bi").css('display','block'); 
		}
	});
</script>

<script>
	$(document).ready(function(){
		$("select[name=bm]").val('0');
		$('input[name=icttel]').attr("disable",true);
	});
</script>

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
			
		var gby=$('input[name=gby]').val();
		var bm=$('select[name=bm]').val();
		var phone = $('input[name=icttel]').val();
		
		//修改 从判断赠送电池为0 --》 是否直属店 gby!="0"
		if("<%=bi%>"=="t"){
			phone = trim(phone);
			if(phone.length!=10&&phone.length!=11&&phone.length!=12){
				alert("当前用户联系方式不完整，应该填写正确的手机号码或带区号的固定电话");
				$('input[name=icttel]').attr("disable",false);
				$('input[name=icttel]').val("");
				return false;
			}
		}
		
		if (gby==null||gby==""){
			alert("赠送电池年数未填写");
			return false;
		}
		if (bm==null||bm==""){
			alert("赠送电池型号未填写");
			return false;
		}
		
		if(gby!="0"&&bm=="0"){
			alert("电池型号未填写");
			return false;
		}
		
		if(gby=="0"&&bm!="0"){
			alert("电池年份未填写");
			return false;
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
					"&gby="+$("input[name=gby]").val()+"&bm="+$("select[name=bm]").val(); 
		}
		else
			return t;
		//}
	}
	
	
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
	
	/* function compute3(e) {
		var id = $(e.target).attr("id");
		var suffix = id.substr(6);
		//var bs = ($("#fdtqnt" + suffix).val())/($("#ncdnum" + suffix).val());
		var a=parseInt($("#fdtqnt" + suffix).val());
		var b=parseInt($("#ncdnum" + suffix).val());
		var c=(a/b);
		//alert(a+' '+b+' '+c);
		if(c<1||c>2){
			alert("客户人数输入有误，售出数量/客户人数）的值应该在1-2之间");
			$("#ncdnum" + suffix).val('');
		}
	}; */
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
				<lemis:texteditor property="icttel" label="用户电话" disable="false" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
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
			</tr>
			<tr id="bi" style="display:none">
			<td>
					<font color='#FF0000'>*</font>赠送电池品牌</td>
					<td colspan="1">
					<select label='赠送电池品牌' style='font-size:12px' name='batbra' id='batbra'
					class='select'  required='true'><option
						value='' selected> 请选择</option></select>
					</td>
					
					<td><font color='#FF0000'>*</font>赠送电池型号</td>
					<td colspan="1">
					<select label='赠送电池型号' style='font-size:12px' name='bm' id='bm'
					class='select'  required='true'><option
						value='' selected> 请选择</option></select>
					</td>
					<%-- <lemis:codelisteditor type="gby" isSelect="true" label="赠送电池几年（颗）"
					redisplay="true" required="true" /> --%>
					<lemis:texteditor property="gby"  label="赠送电池数量（颗）" disable="false" 
						required="true" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
			</tr>
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


