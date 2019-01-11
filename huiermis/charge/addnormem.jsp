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
	header.add(new Formatter("ncdpid", "商品代码"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("pdtprc", "商品单价"));
	header.add(new Formatter("ncdqnt", "售出数量"));
	header.add(new Formatter("ncddis", "商品扣率"));
	header.add(new Formatter("ncdmon", "实际收费"));
	//header.add(new Formatter("ncdrecmon", "退货金额"));
	header.add(new Formatter("ncdnt", "备注"));
	header.add(new Formatter("ncdsta", "状态"));

	String phone=(String)request.getSession().getAttribute("phone");
	String user=(String)request.getSession().getAttribute("user");

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("保存会员","saveData(document.forms[0])");
	buttons.put("返回","history.back()");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ncdid", "收费号"); 
	hidden.put("ncdpid", "商品代码"); 
	hidden.put("ncdsta", "状态"); 
	
	pageContext.setAttribute("hidden", hidden);
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

<script language="javascript">
$(document).ready(function(){
	if("<%=user%>"!="null"){
		$("input[name=user]").val("<%=user%>");
	}
	$('input[name=user]').attr("disable",true);
	
	if("<%=phone%>"!="null"){
		$("input[name=phone]").val("<%=phone%>");
	}
	$('input[name=phone]').attr("disable",true);
});

function saveData(obj) {
	if (!checkValue(obj)) {
		return false;
	}

	var phone = $('input[name=phone]').val();
	
	if(phone.length!=11&&phone.length!=12){
		alert("当前用户联系方式不完整，应该填写正确的手机号码或带区号的固定电话");
		$('input[name=phone]').attr("disable",false);
		$('input[name=phone]').val("");
		return false;
	}
	
  	var bm= $("select[name=bm]").val();
	var gby= $("input[name=gby]").val(); 
	
	if (bm!='0'&&gby=='0'){
		alert("赠送电池数量需要大于0");
		return false;
	}
	
	if (bm =='0'&&gby !='0'){
		alert("请选择赠送型号");
		return false;
	}
	
 	if(Number(gby)==0){
		alert("赠送电池数量应该大于0");
		return false;
	}
 	if(Number(gby)>240){
		alert("电池数量不能超过240颗");
		return false;
	}
 	/*if(Number(gby)%6!=0){
		alert("电池数量只能是6的倍数");
		return false;
	}*/
	if (confirm("确实要添加会员并赠送电池吗？")) {
	obj.submit();
	}
	else
		return t;
};

</script>

<lemis:body>
	<lemis:base />
    <lemis:title title="普通商品会员补录" />
	<lemis:tabletitle title="收费信息" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/ChargeAction.do?method=saveNorMember"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<lemis:texteditor property="chgid" label="收费号" disable="true" /> 
				<lemis:texteditor property="gctnm" label="所属团体" disable="true"/>
				<lemis:texteditor property="ictnm" label="用户名称" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="user" label="用户编号" disable="false" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
				<lemis:texteditor property="phone" label="联系方式" disable="false"/>
				<lemis:formateditor required="false" property="chgdt" mask="date" label="收费日期" format="true"/>
			</tr>
			<tr>
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
					<lemis:texteditor property="gby"  label="补录电池数量（颗）" disable="false" 
						required="true" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
			</tr>
			 <tr>
					<input type="hidden" name="oldphone" id="oldphone" value="<%=phone %>">
			</tr>
		</html:form>
	</table>
	<lemis:table topic="商品明细"
		action="#" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false" hiddenMeta="hidden"
		 batchInputType="update" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


