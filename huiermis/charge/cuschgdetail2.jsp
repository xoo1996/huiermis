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
//积分余额，代金券，客户编号，多少积分抵多少钱，抵扣率,惠耳币
String score=(String)request.getSession().getAttribute("score");
String coin=(String)request.getSession().getAttribute("coin");
String change=(String)request.getSession().getAttribute("change");
String user=(String)request.getSession().getAttribute("user");
String sco=(String)request.getSession().getAttribute("sco");
String fee=(String)request.getSession().getAttribute("fee");
String rate=(String)request.getSession().getAttribute("rate");
String idcard=(String)request.getSession().getAttribute("idcard");
String phone=(String)request.getSession().getAttribute("phone");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("保 存","saveData(document.forms[0])");
	buttons.put("返 回","history.back()");
	
	//List<Editor> editors = new ArrayList<Editor>();
	//editors.add(new Editor("text", "folno", "订单号"));

	 //pageContext.setAttribute("editors", editors);
    pageContext.setAttribute("button", buttons);
%>


<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>

<script language="javascript">
	$(document).ready(function(){
		//$("select[name=folischg]").val("1");
		$("select[name=folurgischg]").val("0");
		var mon=$('input[name=balance]').val();
		$("input[name=money]").val(parseInt(mon));
	});
	function saveData(obj) {
		var coin=$('input[name=coin]').val();
		var coinnum=$('input[name=coinnum]').val();
		var score=$('input[name=score]').val();
		var sco=$('input[name=sco]').val();
		var num=$('input[name=num]').val();
		var mon=$('input[name=balance]').val();
		var money=$('input[name=money]').val();
		var fee=$('input[name=fee]').val();
		if((parseInt(coinnum)!=coinnum)||(parseInt(coinnum)<0)){
			alert("使用惠耳币数目输入错误!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			return false;
		}
		if((parseInt(num)!=num)||(parseInt(num)<0)){
			alert("代金券数目输入错误!");
			$("input[name=num]").val(0);
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("惠耳币不足");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			return false;
		}
		if(parseInt(score)<parseInt(sco*num)){
			alert("积分不足");
			$("input[name=num]").val(0);
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum-fee*num))){
			alert("实付金额输入错误！");
			return false;
		}
		if (!checkValue(obj)) {
			return false;
		}
		if(<%=phone.length() %>!=8&&<%=phone.length() %>!=11&&<%=phone.length() %>!=12){
			alert("该客户信息中联系方式无效,若要成为惠耳积分商城会员，请先完整客户信息");
		}
		if (confirm("确实要收费吗？")) {
			obj.submit();
		}
		else
			return t;
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
				$("input[name=money]").val(parseInt(pdtprc));
			//}
		}else{
			var money=mon-coinnum-sum;
			$("input[name=money]").val(parseInt(money));
			$("input[name=changecoin]").val(coinnum);
		}
	}
</script>



<lemis:body>
	<lemis:base />
    <lemis:title title="定制机收费详情" />
	<lemis:tabletitle title="收费信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveCustomizedCharge"
			method="POST">
			<html:hidden property="folno" />
			<tr>
				<lemis:texteditor  property="folno" label="订单号" disable="true" required="true" 
					 maxlength="6" />
				
				<lemis:codelisteditor type="foltype" isSelect="false" label="订单类型" redisplay="true" required="true" />
				<lemis:texteditor property="folctnm" label="送制单位" disable="true"
					required="true" />
				<html:hidden property="folctid"/>
			</tr>
			<tr>
			
				<%-- <lemis:codelisteditor type="folischg" isSelect="true" label="是否已收费"
					redisplay="true" required="true" /> --%>
				<lemis:texteditor property="cltnm" label="用户姓名" disable="true"
					 required="true" />
				<lemis:texteditor property="ictgender" label="性别" disable="true"
					 required="true"/>
				<lemis:texteditor property="fdtcltid" label="用户编号" required="true"
					disable="true" value="<%=user %>"/>
			</tr>
			<tr>
				
				<lemis:texteditor property="pdtnm" label="定制机型号" disable="true"
					required="false"/>
				<html:hidden property="pdtid"/>
				<lemis:texteditor property="ictpcd" label="邮编" required="false"
					disable="true" maxlength="10" />
				<lemis:texteditor property="icttel" label="联系电话" required="false"
					disable="true" maxlength="20" />
			</tr>
			<tr>
				<td>收费日期</td>
				<td><lemis:operdate/></td>
				<lemis:texteditor property="ictaddr" label="用户地址" disable="true"
					required="false" colspan="3" maxlength="80" />
			</tr>
			
			<tr>
				<lemis:texteditor property="pdtprc" label="原价" disable="true"
						required="false"  />
				<lemis:texteditor property="discount" label="扣率" disable="true"
						required="false"  />
				<lemis:texteditor property="fdtprc" label="售价" disable="true"
						required="false"  />
			</tr>
			<tr>	
				<lemis:texteditor property="deposit" label="定金" required="true"
					disable="true" maxlength="30" />
			   <lemis:texteditor property="xubaofee" label="续保费" required="false"
					disable="true" />
				<lemis:texteditor property="balance"  label="应收余额" disable="true" 
						required="true" />
			</tr>
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="实收余额" disable="false" 
						required="true" /> --%>
						
				<lemis:texteditor property="folurgfee" label="加急费" required="true"
					disable="true" maxlength="30" />
				<lemis:codelisteditor type="folurgischg" isSelect="true" label="是否收加急费"
					redisplay="true" required="true" />
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
					<lemis:texteditor property="money" label="实收金额" required="false"
						disable="false" />
			</tr>
				<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="sco" id="sco" value="<%=sco %>">
				<input type="hidden" name="fee" id="fee" value="<%=fee %>">
				<input type="hidden" name="rate" id="rate" value="<%=rate %>">
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="实收余额" disable="false" 
						required="true" /> --%>
	          
				<lemis:texteditor property="folnt" label="备注" required="false"
					disable="false" colspan="3" maxlength="80" />
			</tr>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


