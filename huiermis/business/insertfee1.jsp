<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("保 存","saveData(document.forms[0])");
	buttons.put("提 交","submitData(document.forms[0])");
    //buttons.put("重 置","document.forms[0].reset();");
    buttons.put("关 闭","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%>
<%
Map<String,String> button1=new LinkedHashMap<String,String>();
	button1.put("查 询","findData(document.forms[0])");
  pageContext.setAttribute("button1", button1);
%>

<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/lemis/js/lemisTree.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>

<script language="javascript">
	function findData(obj){
		 if ($("input[name=feeyear]").val()==""||$("input[name=feemonth]").val()=="") {
			alert("请填写年月");
		}else{
			obj.action='<html:rewrite page="/FeeAction.do?method=findData&"/>' + getAlldata(obj);
			obj.submit();
		}
	};
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	};
	function submitData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.action='<html:rewrite page="/FeeAction.do?method=submitFee&"/>' + getAlldata(obj);
		var con;
		con=confirm("提交之后将不能修改，确定要提交吗？"); //在页面上弹出对话框
		if(con==true) {
			
			obj.submit();
		}
	};
	
	Number.prototype.toFixed=function(num) 
	   { 
	   //重新构造toFixed方法,IE5.0+ 
	   with(Math)this.NO=round(this.valueOf()*pow(10,num))/pow(10,num); 
	   return String(/\./g.exec(this.NO)?this.NO:this.NO+"."+String(Math.pow(10,num)).substr(1,num)); 
	   };
	function autoin () {
		var m = $("input[name=feesales]").val();
		var mon = m*0.05;
		$("input[name=feemanage]").val(mon.toFixed(2));
	};

		
	function createQueryString() {
		var queryString = {
			gctId : $("input[name=feegctid]").val(),
			year : $("input[name=feeyear]").val(),
			month : $("input[name=feemonth]").val()
		};
		return queryString;
	};
	
	
	$(document).ready( function() {
		//输入框变灰
		//$("input[name=rfeebusiness]").attr("disable",true);
		//$("input[name=rfeetotalin]").attr("disable",true);
		//$("input[name=rfeesales]").attr("disable",true);
		//$("input[name=rfeesurplus]").attr("disable",true);
		//$("input[name=nfeetotal]").attr("disable",true);
		//$("input[name=bfeetotal]").attr("disable",true);
		//$("input[name=ffeetotal]").attr("disable",true);
		autoSurplus();
		autoRfeesales();
		autoBusiness();
		autoTotalin();
		autobfeetotal();
		autoffeetotal();
		autonfeetotal();
       var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		
		$("input[name=feegctid]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=feegctid]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=feegctid]").val(gid);
				$("input[name=feegctname]").val(gnm);
				$(this).parent().next().find("input").val(gid);
			}
		});
	});
	
	
	function autoRfeesales () {
		var rfeehuier = $("input[name=rfeehuier]").val();
		var rfeeweiting = $("input[name=rfeeweiting]").val();
		var rfeeqie = $("input[name=rfeeqie]").val();
		var rfeeelse = $("input[name=rfeeelse]").val();
		var rfeerepair = $("input[name=rfeerepair]").val();
		var rfeeearmould = $("input[name=rfeeearmould]").val();
		var rfeeparts = $("input[name=rfeeparts]").val();
		var rfeebenefitm = $("input[name=rfeebenefitm]").val();
		var rfeesales = Number(rfeehuier)+Number(rfeeweiting)+Number(rfeeqie)+Number(rfeeelse)+Number(rfeerepair)+Number(rfeeearmould)+Number(rfeeparts)-Number(rfeebenefitm);
		$("input[name=rfeesales]").attr("disable",false);
		$("input[name=rfeesales]").val(rfeesales.toFixed(2));
		$("input[name=rfeesales]").attr("disable",true);
		autoBusiness();
		autoTotalin();
	};
	
	function autoBusiness () {
		var rfeesales = $("input[name=rfeesales]").val();
		var rfeefinance = $("input[name=rfeefinance]").val();
		var rfeecollection = $("input[name=rfeecollection]").val();
		var rfeebusiness = Number(rfeesales)-Number(rfeefinance)-Number(rfeecollection);
		$("input[name=rfeebusiness]").attr("disable",false);
		$("input[name=rfeebusiness]").val(rfeebusiness.toFixed(2));
		$("input[name=rfeebusiness]").attr("disable",true);
		autoTotalin();
	};
	function autoTotalin () {
		var rfeewagein = $("input[name=rfeewagein]").val();
		var rfeeelin = $("input[name=rfeeelin]").val();
		var rfeebenefitin = $("input[name=rfeebenefitin]").val();
		var rfeebusiness = $("input[name=rfeebusiness]").val();
		var rfeetotalin = Number(rfeebusiness)+Number(rfeeelin)+Number(rfeewagein)-Number(rfeebenefitin);
		$("input[name=rfeetotalin]").attr("disable",false);
		$("input[name=rfeetotalin]").val(rfeetotalin.toFixed(2));
		$("input[name=rfeetotalin]").attr("disable",true);
	};
	function autoSurplus () {
		var rfeepresurplus = $("input[name=rfeepresurplus]").val();
		var rfeebusin = $("input[name=rfeebusin]").val();
		var rfeerealpay = $("input[name=rfeerealpay]").val();
		var rfeesurplus = Number(rfeepresurplus)+Number(rfeebusin)-Number(rfeerealpay);
		$("input[name=rfeesurplus]").attr("disable",false);
		$("input[name=rfeesurplus]").val(rfeesurplus.toFixed(2));
		$("input[name=rfeesurplus]").attr("disable",true);
	};
	function autobfeetotal () {
		var bfeerent = $("input[name=bfeerent]").val();
		var bfeeassets = $("input[name=bfeeassets]").val();
		var bfeebuilt = $("input[name=bfeebuilt]").val();
		var bfeetax = $("input[name=bfeetax]").val();
		var bfeeaccount = $("input[name=bfeeaccount]").val();
		var bfeeprocess = $("input[name=bfeeprocess]").val();
		var bfeead = $("input[name=bfeead]").val();
		var bfeephone = $("input[name=bfeephone]").val();
		var bfeewater = $("input[name=bfeewater]").val();
		var bfeetrip = $("input[name=bfeetrip]").val();
		var bfeepostage = $("input[name=bfeepostage]").val();
		var bfeeoffice = $("input[name=bfeeoffice]").val();
		var bfeesocial = $("input[name=bfeesocial]").val();
		/* var bfeebenefit = $("input[name=bfeebenefit]").val(); */
		var bfeeothercharges = $("input[name=bfeeothercharges]").val();
		var bzbfee = $("input[name=bzbfee]").val();
		var bfeetotal = Number(bfeerent)+Number(bfeeassets)+Number(bfeebuilt)+
		Number(bfeetax)+Number(bfeeaccount)+Number(bfeeprocess)+Number(bfeead)+
		Number(bfeephone)+Number(bfeewater)+Number(bfeetrip)+Number(bfeepostage)+
		Number(bfeeoffice)+Number(bfeesocial)+Number(bfeeothercharges)+Number(bzbfee);
		$("input[name=bfeetotal]").attr("disable",false);
		$("input[name=bfeetotal]").val(bfeetotal.toFixed(2));
		$("input[name=bfeetotal]").attr("disable",true);
	};
	function autoffeetotal () {
		var ffeerent = $("input[name=ffeerent]").val();
		var ffeeassets = $("input[name=ffeeassets]").val();
		var ffeebuilt = $("input[name=ffeebuilt]").val();
		var ffeetax = $("input[name=ffeetax]").val();
		var ffeepension = $("input[name=ffeepension]").val();
		var ffeehousingfund = $("input[name=ffeehousingfund]").val();
		var ffeeaccount = $("input[name=ffeeaccount]").val();
		var ffeeprocess = $("input[name=ffeeprocess]").val();
		var ffeewage = $("input[name=ffeewage]").val();
		var ffeead = $("input[name=ffeead]").val();
		var ffeephone = $("input[name=ffeephone]").val();
		var ffeewater = $("input[name=ffeewater]").val();
		var ffeetrip = $("input[name=ffeetrip]").val();
		var ffeepostage = $("input[name=ffeepostage]").val();
		var ffeeoffice = $("input[name=ffeeoffice]").val();
		var ffeesocial = $("input[name=ffeesocial]").val();
		var fzbfee = $("input[name=fzbfee]").val();
		//var ffeebenefit = $("input[name=ffeebenefit]").val();
		var ffeeothercharges = $("input[name=ffeeothercharges]").val();
		var ffeetotal = Number(ffeerent)+Number(ffeeassets)+Number(ffeebuilt)
		+Number(ffeetax)+Number(ffeepension)+Number(ffeehousingfund)+Number(ffeeaccount)
		+Number(ffeeprocess)+Number(ffeewage)+Number(ffeead)+Number(ffeephone)
		+Number(ffeewater)+Number(ffeetrip)+Number(ffeepostage)+Number(ffeeoffice)
		+Number(ffeesocial)+Number(ffeeothercharges)+Number(fzbfee);
		$("input[name=ffeetotal]").attr("disable",false);
		$("input[name=ffeetotal]").val(ffeetotal.toFixed(2));
		$("input[name=ffeetotal]").attr("disable",true);
	};
	function autonfeetotal () {
		var nfeerent = $("input[name=nfeerent]").val();
		var nfeeassets = $("input[name=nfeeassets]").val();
		var nfeebuilt = $("input[name=nfeebuilt]").val();
		var nfeetax = $("input[name=nfeetax]").val();
		var nfeepension = $("input[name=nfeepension]").val();
		var nfeehousingfund = $("input[name=nfeehousingfund]").val();
		var nfeeaccount = $("input[name=nfeeaccount]").val();
		var nfeeprocess = $("input[name=nfeeprocess]").val();
		var nfeewage = $("input[name=nfeewage]").val();
		var nfeead = $("input[name=nfeead]").val();
		var nfeephone = $("input[name=nfeephone]").val();
		var nfeewater = $("input[name=nfeewater]").val();
		var nfeetrip = $("input[name=nfeetrip]").val();
		var nfeepostage = $("input[name=nfeepostage]").val();
		var nfeeoffice = $("input[name=nfeeoffice]").val();
		var nfeesocial = $("input[name=nfeesocial]").val();
		var nzbfee = $("input[name=nzbfee]").val();
		//var bfeebenefit = $("input[name=bfeebenefit]").val();
		var nfeeothercharges = $("input[name=nfeeothercharges]").val();
		var nfeetotal = Number(nfeerent)+Number(nfeeassets)+Number(nfeebuilt)+
		Number(nfeetax)+Number(nfeepension)+Number(nfeehousingfund)+Number(nfeeaccount)
		+Number(nfeeprocess)+Number(nfeewage)+Number(nfeead)+
		Number(nfeephone)+Number(nfeewater)+Number(nfeetrip)+Number(nfeepostage)+
		Number(nfeeoffice)+Number(nfeesocial)+Number(nfeeothercharges)+Number(nzbfee);
		$("input[name=nfeetotal]").attr("disable",false);
		$("input[name=nfeetotal]").val(nfeetotal.toFixed(2));
		$("input[name=nfeetotal]").attr("disable",true);
	};
	
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="团体客户费用信息" />
	<lemis:tabletitle title="<h3>录入费用信息" />
	<table class="tableinput">

		<lemis:editorlayout />
		  <html:form action="/FeeAction.do?method=saveFee" method="POST">
			<tr>
				<lemis:texteditor property="feeyear" label="年" disable="false"
					required="true" maxlength="4" />
				<lemis:texteditor property="feemonth" label="月" disable="false"
					required="true" maxlength="2"/>
				<lemis:buttons buttonMeta="button1"/>
			</tr>

	
			<lemis:tabletitle title="<h3>商务支出"/>
			<table class="tableinput">
				<lemis:editorlayout />
				
				<tr>
					<lemis:texteditor   property="bfeerent" 
					label="房租(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeeassets" 
					label="固定资产(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeebuilt" 
					label="装修费(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
					
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="bfeetax" label="税收(不可控)" disable="false" onblur="autobfeetotal()"/>
					<%-- <lemis:texteditor   property="bfeepension" 
					label="社保费(不可控)" disable="false" required="false" />
					<lemis:texteditor   property="bfeehousingfund"
					label="公积金(不可控) " disable="false" required="false" /> --%>
					<lemis:texteditor   property="bfeeaccount"
					label="会计工资(不可控) " disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeeprocess" 
					label="手续费(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				
				<tr>			
					<lemis:texteditor   property="bzbfee" 
					label="总部费用(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<%-- <tr>
					
					 <lemis:texteditor   property="bfeewage" 
					label="财税工资(不可控)" disable="false" required="false" /> 
					<lemis:texteditor   property="bfee1othercharges"
					label="其他(不可控)" disable="false" required="false" />
				</tr> --%>
				
				<tr>
					<lemis:texteditor   property="bfeead" 
					label="广告费(可控)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
					property="bfeephone" label="电话费(可控)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
					property="bfeewater" label="水电费(可控)" disable="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="bfeetrip" label="差旅费(可控)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
						property="bfeepostage" label="邮费(可控)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
						property="bfeeoffice" label="办公费(可控)" disable="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>				
					<lemis:texteditor   required="false"
					property="bfeesocial" label="公关费(可控)" disable="false" onblur="autobfeetotal()"/>
					<%-- <lemis:texteditor   property="bfeebenefit"
					label="优惠(可控)" disable="false" required="false" onblur="autobfeetotal()"/> --%>
					<lemis:texteditor   property="bfeeothercharges"
					label="其他(可控)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="true" onclick="autobfeetotal()"
						property="bfeetotal" label="商务合计"  disable="false" />
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="bfeent" label="备注" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>

			<lemis:tabletitle title="<h3>财税支出" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="ffeerent" 
					label="房租(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeeassets" 
					label="固定资产(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeebuilt" 
					label="装修费(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="ffeetax" label="税收(不可控)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeepension" 
					label="社保费(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeehousingfund"
					label="公积金(不可控) " disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="ffeeaccount"
					label="会计工资 (不可控)" disable="false" required="false" onblur="autoffeetotal()"/>	
					<lemis:texteditor   property="ffeeprocess" 
					label="手续费(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeewage" 
					label="财税工资(不可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				 <tr>
					<lemis:texteditor   property="fzbfee" 
					label="总部费用(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="ffeead" 
					label="广告费(可控)" disable="false" required="false" onblur="autoffeetotal()"/>            
					<lemis:texteditor   required="false"
					property="ffeephone" label="电话费(可控)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
					property="ffeewater" label="水电费(可控)" disable="false" onblur="autoffeetotal()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="ffeetrip" label="差旅费(可控)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
						property="ffeepostage" label="邮费(可控)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
						property="ffeeoffice" label="办公费(可控)" disable="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="ffeesocial" label="公关费(可控)" disable="false" onblur="autoffeetotal()"/>
					<%-- <lemis:texteditor   property="ffeebenefit"
					label="优惠(可控)" disable="false" required="false" /> --%>
					<lemis:texteditor   property="ffeeothercharges"
					label="其他(可控)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="true" onclick="autoffeetotal()"
						property="ffeetotal" label="财税合计"  disable="false" />
					
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="ffeent" label="备注" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>


			<lemis:tabletitle title="<h3>下月预算" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="nfeerent" 
					label="房租(不可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeeassets" 
					label="固定资产(不可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeebuilt" 
					label="装修费(不可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="nfeetax" label="税收(不可控)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeepension" 
					label="社保费(不可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeehousingfund"
					label="公积金(不可控) " disable="false" required="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="nfeeaccount"
					label="会计工资(不可控) " disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeeprocess" 
					label="手续费(不可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
					property="nfeewage" label="财税工资(不可控)" disable="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="nzbfee" 
					label="总部费用(不可控)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="nfeead" 
					label="广告费(可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeephone" label="电话费(可控)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeewater" label="水电费(可控)" disable="false" onblur="autonfeetotal()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="nfeetrip" label="差旅费(可控)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeepostage" label="邮费(可控)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeeoffice" label="办公费(可控)" disable="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="nfeesocial" label="公关费(可控)" disable="false" onblur="autonfeetotal()"/>
					<%-- <lemis:texteditor   property="nfeebenefit"
					label="优惠(可控)" disable="false" required="false" /> --%>
					<lemis:texteditor   property="nfeeothercharges"
					label="其他(可控)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="true" onclick="autonfeetotal()" property="nfeetotal" label="预算合计"  disable="false" />
				</tr>
				
				<tr> 
					<lemis:textareaeditor required="false" property="nfeent" label="备注" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>
				<lemis:tabletitle title="<h3>本月收支汇总"/>
				<lemis:tabletitle title="<h3>收入情况" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeehuier" 
					label="惠耳产品" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeweiting" 
					label="唯听产品" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeqie" 
					label="全额提奖的厂家产品" disable="false" required="false" onblur="autoRfeesales()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="rfeeelse" label="其他厂家产品" disable="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeerepair" 
					label="修理费" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeearmould"
					label="耳模" disable="false" required="false" onblur="autoRfeesales()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="rfeeparts" 
					label="电池配件等" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeebenefitm" 
					label="优惠(减销售)" disable="false" required="false" onblur="autoRfeesales()"/>
					<td colspan="2" align="left"><font size="6" color="red" ></font></td>
					<!-- <td><font></font></td> -->
				</tr>
			</table>
				<lemis:tabletitle title="<h3>汇款情况" />
				<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeesales" 
					label="本月销售额" disable="false" required="true" onblur="autoBusiness()" />
					<lemis:texteditor   required="true"
						property="rfeefinance" label="财务" disable="false" onblur="autoBusiness()"/>
					<lemis:texteditor   required="false"
						property="rfeecollection" label="代收情况" disable="false"  onblur="autoBusiness()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="true" onclick="autoBusiness()"
						property="rfeebusiness" label="商务" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="rfeewagein" label="工资汇入" disable="false" onblur="autoTotalin()"/>
					<lemis:texteditor   property="rfeeelin"
					label="其他汇入" disable="false" required="false" onblur="autoTotalin()"/>
					<lemis:texteditor   property="rfeebenefitin"
					label="减商务支出" disable="false" required="false" onblur="autoTotalin()" />
				</tr>
				<tr>
					<lemis:texteditor   property="rfeetotalin" onclick="autoTotalin()"
					label="合计汇入" disable="false"   required="true" />
				</tr>
				</table>
				<lemis:tabletitle title="<h3>商务支出情况" />
				<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeepresurplus" 
						label="上月结余" disable="false" required="false" onblur="autoSurplus()"/>
					<lemis:texteditor   required="false"
						property="rfeebusin" label="商务汇入" disable="false" onblur="autoSurplus()"/>
					<lemis:texteditor   required="false"
						property="rfeerealpay" label="实际支出" disable="false" onblur="autoSurplus()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="true"
						property="rfeesurplus" label="本月结余" disable="false" />
					<lemis:texteditor   required="false"
						property="rfeecosales" label="本月合作点销售额" disable="false" />
					<lemis:texteditor   required="false"
						property="rfeeaccucosales" label="本年累计合作店销售额" disable="false" />
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="rfeent" label="备注" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>
			

		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
