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
	String user=(String)request.getSession().getAttribute("user");
	String bi=(String)request.getSession().getAttribute("bi");
	/*
	String gby=(String)request.getSession().getAttribute("gby");
	String bm=(String)request.getSession().getAttribute("bm");
	String phone=(String)request.getSession().getAttribute("phone"); */
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
<script src="/huiermis/js/BatBrandJson.js"></script>
<script src="/huiermis/js/BatTypeJson.js"></script>
<script>
 	$(document).ready(function(){
		if("<%=bi%>"=="t"){
			$("#bi").css('display','block'); 
		}
	});
</script>
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
		//$("select[name=folischg]").val("1");
		$("select[name=folurgischg]").val("0");
		$('input[name=icttel]').attr("disable",true);
	});
	function saveData(obj) {
		var gby=$('input[name=gby]').val();
		var bm=$('select[name=bm]').val();
		
		var phone = $('input[name=icttel]').val();
		//修改 
		if("<%=bi%>"=="t"){
			phone = trim(phone);
			if(phone.length!=11&&phone.length!=12){
				alert("当前用户联系方式不完整，应该填写正确的手机号码或带区号的固定电话");
				$('input[name=icttel]').attr("disable",false);
				$('input[name=icttel]').val("");
				return false;
			}
		}
		
		if(gby!="0"&&bm=="0"){
			alert("电池型号未填写");
			return false;
		}
		
		if(gby=="0"&&bm!="0"){
			alert("电池年份未填写");
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
	};

</script>
<script>
	$(document).ready(function(){
		$("input[name=gby]").val('0');
	});
</script>

<script>
	$(document).ready(function(){
		$("select[name=bm]").val('0');
	});
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
			</tr>
			<tr>
				
				<lemis:texteditor property="pdtnm" label="定制机型号" disable="true"
					required="false"/>
				<html:hidden property="pdtid"/>
				<lemis:texteditor property="ictpcd" label="邮编" required="false"
					disable="true" maxlength="10" />
				<lemis:texteditor property="icttel" label="联系电话" required="false"
					disable="false" maxlength="20" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
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
				<lemis:texteditor property="balance"  label="实收余额" disable="true" 
						required="true" />
			</tr>
			<tr>
						
				<%-- <lemis:formateditor mask="########" property="balance"  label="实收余额" disable="false" 
						required="true" /> --%>
						
				<lemis:texteditor property="folurgfee" label="加急费" required="true"
					disable="true" maxlength="30" />
				<lemis:codelisteditor type="folurgischg" isSelect="true" label="是否收加急费"
					redisplay="true" required="true" />
				<!--<lemis:texteditor property="folnt" label="备注" required="false"
					disable="false" colspan="3" maxlength="80" />
			--></tr>
			
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

					<input type="hidden" name="fdtcltid" id="fdtcltid" value="<%=user %>">
			
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


