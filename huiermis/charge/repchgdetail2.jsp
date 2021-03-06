<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//积分余额，代金券，客户编号，多少积分抵多少钱，抵扣率,惠耳币
String coin=(String)request.getSession().getAttribute("coin");
String user=(String)request.getSession().getAttribute("user");
String idcard=(String)request.getSession().getAttribute("idcard");
String phone=(String)request.getSession().getAttribute("phone");
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("保 存","saveData(document.forms[0])");
	buttons.put("返 回","history.back()");
    pageContext.setAttribute("button", buttons);
%>

<html>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script language="javascript">
	$(document).ready(function(){
		var mon=$('input[name=repamt]').val();
		$("input[name=money]").val(parseInt(mon));
	});  
	function saveData(obj) {
		var coin=$('input[name=coin]').val();
		var coinnum=$('input[name=coinnum]').val();
		var mon=$('input[name=repamt]').val();
		var money=$('input[name=money]').val();
		if((parseInt(coinnum)!=coinnum)||(parseInt(coinnum)<0)){
			alert("使用惠耳币数目输入错误!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("惠耳币不足");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum))){
			alert("实付金额输入错误！");
			$("input[name=money]").val(parseInt(mon));
			return false;
		}
		if (!checkValue(obj)) {
			return false;
		}
		if (confirm("确实要收费吗？")) {
			obj.submit();
		}
		else
			return t;
	}
	
	function computeL2() {
		var pdtprc=$('input[name=repamt]').val();//gai
		var mon=$('input[name=repamt]').val();
		var coinnum=$('input[name=coinnum]').val();
		//if(coinnum<=pdtprc-1){
			if((pdtprc*0.5)<coinnum){
				alert("惠耳币抵扣不得高于50%助听器维修价格");
				$("input[name=coinnum]").val(0);
				$("input[name=changecoin]").val(0);
				$("input[name=money]").val(parseInt(pdtprc));
			//}
		}else{
			var money=mon-coinnum;
			$("input[name=money]").val(parseInt(money));
			$("input[name=changecoin]").val(coinnum);
		}
	}
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="维修收费详情" />
	<lemis:tabletitle title="维修信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveRepairCharge">
			<tr>
				<html:hidden property="repidentity" />
				<lemis:texteditor property="repfolid" label="订单号" required="true"
					disable="true" />
				<lemis:texteditor property="repcltnm" label="用户姓名" required="true"
					disable="true" />
				<lemis:texteditor property="reppnm" label="助听器型号" disable="true"
					required="true" />
				<html:hidden property="pdtid" />
			</tr>
			<tr>
				<lemis:texteditor property="repid" label="机身编号" disable="true"
					required="true" />
				<lemis:texteditor property="repgctnm" label="送修单位" disable="true"
					required="true" />
				<html:hidden property="repgctid" />
				<lemis:formateditor mask="date" format="true" property="repdate"
					label="送修日期" disable="true" required="true" />
			</tr>
			<tr>
				<lemis:codelisteditor type="repfree" isSelect="false" label="保修期"
					redisplay="true" required="false" />
				<lemis:codelisteditor type="reppreamt" isSelect="false"
					label="维修费通知" redisplay="true" required="false" />
				<lemis:codelisteditor type="repcls" isSelect="false" label="修理类别"
					redisplay="true" required="false" />
			</tr>
			<tr>
				<lemis:texteditor property="repdeclare" label="故障详情" disable="true"
					required="false" colspan="5" />
			</tr>
			<tr>
				<lemis:texteditor property="repnote" label="备注" disable="true"
					required="false" colspan="5" />
			</tr>
			<lemis:tabletitle title="维修结果" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor property="repconfirmed" label="故障确认"
						disable="true" required="false" colspan="5" />
				</tr>
				<tr>
					<lemis:texteditor property="repaction1" label="维修措施一"
						disable="true" required="false" />
					<lemis:texteditor property="repaction2" label="维修措施二"
						disable="true" required="false" />
					<lemis:texteditor property="repaction3" label="维修措施三"
						disable="true" required="false" />
				</tr>
				<tr>
					<lemis:texteditor property="repaction4" label="维修措施四"
						disable="true" required="false" />
					<lemis:texteditor property="repaction5" label="维修措施五"
						disable="true" required="false" />
					<lemis:texteditor property="repaction6" label="维修措施六"
						disable="true" required="false" />
				</tr>
				<tr>
				   <lemis:texteditor property="repaprc" label="费用预计" disable="true"
						required="false" />
					<lemis:texteditor property="repamt" label="费用合计" disable="true"
						required="false" />
					<lemis:codelisteditor type="repoprcd" isSelect="false" label="维修员"
						redisplay="true" required="false" dataMeta="userList" />
				</tr>
				<tr>
				     <lemis:codelisteditor type="repregnames" isSelect="false"
						label="其他维修员" redisplay="true" required="false"
						dataMeta="userList" />
					<lemis:texteditor property="repattention" label="注意事项"
						disable="true" required="false" colspan="3" />
				</tr>
				<tr>
					<lemis:texteditor property="reptip" label="温馨提示1" disable="true"
						required="false" />
					<lemis:texteditor property="reptip1" label="备注" disable="true"
						required="false" />
					<lemis:formateditor mask="date" format="true" property="repfdate"
						label="完工日期" required="false" />
				</tr>
				<tr>
					<%-- <lemis:codelisteditor type="folischg" isSelect="true" label="维修费是否已收"
						redisplay="true" required="true" /> --%>
					<td>收费日期</td>
					<td><lemis:operdate/></td>
				<lemis:texteditor property="coin" label="惠耳币余额" required="false"
						disable="true" value="<%=coin %>"/>
			</tr>
			<tr>
				<lemis:texteditor property="coinnum" label="使用惠耳币数量" required="false"
						disable="false" value="0" onkeyup="computeL2()"/>
				<lemis:texteditor property="changecoin" label="抵扣现金：(元)" required="false"
						disable="false" value="0"/>
					<lemis:texteditor property="money" label="实收金额" required="false"
						disable="false" />
			</tr>
			</table>
				<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="user" id="rate" value="<%=user %>">
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

