<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�� ��","saveData(document.forms[0])");
    buttons.put("�� ��","document.forms[0].reset();");
    buttons.put("�� ��","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
    String path = request.getContextPath();
  	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/lemis/js/lemisTree.js"></script>
<script src="/huiermis/js/CityJson.js"></script>
<script src="/huiermis/js/DistrictJson.js"></script>
<script src="/huiermis/js/ProJson.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script language="javascript">
	function saveData(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	}
</script>
<script language="javascript">
$(document).ready(function(){
	//$("input[name=pdtid]").bind("click",function(){
		$.ajax({
			 type:'post',
			 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
			 dataType:'json',
			 error:function(){
			   alert('��ȡ���ݴ���');
			 },
			 success:function(data){
						$("input[name=bvrighttype]").autocomplete(data,{
							max:10,
							matchContains:true,
							formatItem:function(data,i,max){
								return (data.proid + "=" + data.proname);
							}
						});	
						$("input[name=bvrighttype]").result(function(event, data, formatted) {
							if (data){
								var proname=data.proname;
								$("input[name=bvrighttype]").val(proname);
								}
						});
					}
			});
	});	
	$(document).ready(function(){
		//$("input[name=pdtid]").bind("click",function(){
			$.ajax({
				 type:'post',
				 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
				 dataType:'json',
				 error:function(){
				   alert('��ȡ���ݴ���');
				 },
				 success:function(data){
							$("input[name=bvlefttype]").autocomplete(data,{
								max:10,
								matchContains:true,
								formatItem:function(data,i,max){
									return (data.proid + "=" + data.proname);
								}
							});	
							$("input[name=bvlefttype]").result(function(event, data, formatted) {
								if (data){
									var proname=data.proname;
									$("input[name=bvlefttype]").val(proname);
									}
							});
						}
				});
		});	
	function lgvag() {

		var l1 = $("input[name=lg250]").val() == "" ? 0
				: $("input[name=lg250]").val();
		var l2 = $("input[name=lg500]").val() == "" ? 0
				: $("input[name=lg500]").val();
		var l3 = $("input[name=lg1000]").val() == "" ? 0 : $(
				"input[name=lg1000]").val();
		var l4 = $("input[name=lg2000]").val() == "" ? 0 : $(
				"input[name=lg2000]").val();
		var l5 = $("input[name=lg4000]").val() == "" ? 0 : $(
				"input[name=lg4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=lgavg]").val(lvag.toFixed(2));
	};
	function lsvag() {

		var l1 = $("input[name=ls250]").val() == "" ? 0
				: $("input[name=ls250]").val();
		var l2 = $("input[name=ls500]").val() == "" ? 0
				: $("input[name=ls500]").val();
		var l3 = $("input[name=ls1000]").val() == "" ? 0 : $(
				"input[name=ls1000]").val();
		var l4 = $("input[name=ls2000]").val() == "" ? 0 : $(
				"input[name=ls2000]").val();
		var l5 = $("input[name=ls4000]").val() == "" ? 0 : $(
				"input[name=ls4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=lsavg]").val(lvag.toFixed(2));
	};
	function rsvag() {

		var l1 = $("input[name=rs250]").val() == "" ? 0
				: $("input[name=rs250]").val();
		var l2 = $("input[name=rs500]").val() == "" ? 0
				: $("input[name=rs500]").val();
		var l3 = $("input[name=rs1000]").val() == "" ? 0 : $(
				"input[name=rs1000]").val();
		var l4 = $("input[name=rs2000]").val() == "" ? 0 : $(
				"input[name=rs2000]").val();
		var l5 = $("input[name=rs4000]").val() == "" ? 0 : $(
				"input[name=rs4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=rsavg]").val(lvag.toFixed(2));
	};
	function lqvag() {

		var l1 = $("input[name=lq250]").val() == "" ? 0
				: $("input[name=lq250]").val();
		var l2 = $("input[name=lq500]").val() == "" ? 0
				: $("input[name=lq500]").val();
		var l3 = $("input[name=lq1000]").val() == "" ? 0 : $(
				"input[name=lq1000]").val();
		var l4 = $("input[name=lq2000]").val() == "" ? 0 : $(
				"input[name=lq2000]").val();
		var l5 = $("input[name=lq4000]").val() == "" ? 0 : $(
				"input[name=lq4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=lqavg]").val(lvag.toFixed(2));
	};
	function rgvag() {

		var l1 = $("input[name=rg250]").val() == "" ? 0
				: $("input[name=rg250]").val();
		var l2 = $("input[name=rg500]").val() == "" ? 0
				: $("input[name=rg500]").val();
		var l3 = $("input[name=rg1000]").val() == "" ? 0 : $(
				"input[name=rg1000]").val();
		var l4 = $("input[name=rg2000]").val() == "" ? 0 : $(
				"input[name=rg2000]").val();
		var l5 = $("input[name=rg4000]").val() == "" ? 0 : $(
				"input[name=rg4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=rgavg]").val(lvag.toFixed(2));
	};
	function rqvag() {

		var l1 = $("input[name=rq250]").val() == "" ? 0
				: $("input[name=rq250]").val();
		var l2 = $("input[name=rq500]").val() == "" ? 0
				: $("input[name=rq500]").val();
		var l3 = $("input[name=rq1000]").val() == "" ? 0 : $(
				"input[name=rq1000]").val();
		var l4 = $("input[name=rq2000]").val() == "" ? 0 : $(
				"input[name=rq2000]").val();
		var l5 = $("input[name=rq4000]").val() == "" ? 0 : $(
				"input[name=rq4000]").val();
		//var lvag = (parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5))/5;
		var lvag = (parseInt(l2) + parseInt(l3) + parseInt(l4)) / 3;
		$("input[name=rqavg]").val(lvag.toFixed(2));
	};
</script>
<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="�û�����������������" />
	<lemis:tabletitle title="�û�������Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do?method=addSCTL"
			method="POST">
			<html:hidden property="ictgctid" />
			<tr>

				<lemis:texteditor property="ictid" label="�û����" required="false"
					disable="true" />
				<lemis:texteditor property="ictnm" label="�û�����" required="false"
					disable="true" />
				<lemis:texteditor property="gctnm" label="��������" required="false"
					disable="true" />
			</tr>
			<tr>
				<lemis:texteditor property="bvlefttype" label="����������ͺ�"
					required="false" disable="false" />
				<lemis:texteditor property="bvleftnum" label="����������������"
					required="false" disable="false" />
			</tr>
			<tr>
				<lemis:texteditor property="bvrighttype" label="�Ҷ��������ͺ�"
					required="false" disable="false" />
				<lemis:texteditor property="bvrightnum" label="�Ҷ��������������"
					required="false" disable="false" />
			</tr>
			<tr>

				<lemis:formateditor mask="date" property="fctcdt" label="��������"
					required="true" disable="false" />

			</tr>
			<tr>
				<lemis:texteditor property="fctnt" label="��ע" required="false"
					disable="false" colspan="20" />
			</tr>
			<lemis:tabletitle title="���Լ�¼" />
				<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<html:textarea property="debugrecord" rows="2" disabled="true"></html:textarea>
					</tr>
				</table>
       
       
			<lemis:tabletitle title="����ǵ�(�ڱ����)" />
			<table class="tableinput">
				<tr>
					<td width="90px"><input type="checkbox" name="lgyb250"
						value="1">250</td>
					<td colspan="1" width="35px"><input type="text" name="lg250"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb500"
						value="1">500</td>
					<td colspan="1" width="35px"><input type="text" name="lg500"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb750"
						value="1">750</td>
					<td colspan="1" width="35px"><input type="text" name="lg750"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb1000"
						value="1">1000</td>
					<td colspan="1" width="35px"><input type="text" name="lg1000"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb1500"
						value="1">1500</td>
					<td colspan="1" width="35px"><input type="text" name="lg1500"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb2000"
						value="1">2000</td>
					<td colspan="1" width="35px"><input type="text" name="lg2000"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb3000"
						value="1">3000</td>
					<td colspan="1" width="35px"><input type="text" name="lg3000"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb4000"
						value="1">4000</td>
					<td colspan="1" width="35px"><input type="text" name="lg4000"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lgyb6000"
						value="1">6000</td>
					<td colspan="1" width="35px"><input type="text" name="lg6000"
						required="false" disable="false" value="" onblur="lgvag()"
						class="text"></td>
					<lemis:texteditor property="lgavg" label="ƽ��" required="false"
						disable="false" onclick="lgvag()" />
				</tr>
			</table>

			<lemis:tabletitle title="�������(�ڱ����)" />
			<table class="tableinput">
				<tr>
					<td width="90px"><input type="checkbox" name="lqyb250"
						value="1">250</td>
					<td colspan="1" width="35px"><input type="text" name="lq250"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb500"
						value="1">500</td>
					<td colspan="1" width="35px"><input type="text" name="lq500"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb750"
						value="1">750</td>
					<td colspan="1" width="35px"><input type="text" name="lq750"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb1000"
						value="1">1000</td>
					<td colspan="1" width="35px"><input type="text" name="lq1000"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb1500"
						value="1">1500</td>
					<td colspan="1" width="35px"><input type="text" name="lq1500"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb2000"
						value="1">2000</td>
					<td colspan="1" width="35px"><input type="text" name="lq2000"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb3000"
						value="1">3000</td>
					<td colspan="1" width="35px"><input type="text" name="lq3000"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb4000"
						value="1">4000</td>
					<td colspan="1" width="35px"><input type="text" name="lq4000"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="lqyb6000"
						value="1">6000</td>
					<td colspan="1" width="35px"><input type="text" name="lq6000"
						required="false" disable="false" value="" onblur="lqvag()"
						class="text"></td>
					<lemis:texteditor property="lqavg" label="ƽ��" required="false"
						disable="false" onclick="lqvag()" />
				</tr>
			</table>

			<lemis:tabletitle title="�Ҷ��ǵ�(�ڱ����)" />
			<table class="tableinput">
				<tr>
					<td width="90px"><input type="checkbox" name="rgyb250"
						value="1">250</td>
					<td colspan="1" width="35px"><input type="text" name="rg250"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb500"
						value="1">500</td>
					<td colspan="1" width="35px"><input type="text" name="rg500"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb750"
						value="1">750</td>
					<td colspan="1" width="35px"><input type="text" name="rg750"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb1000"
						value="1">1000</td>
					<td colspan="1" width="35px"><input type="text" name="rg1000"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb1500"
						value="1">1500</td>
					<td colspan="1" width="35px"><input type="text" name="rg1500"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb2000"
						value="1">2000</td>
					<td colspan="1" width="35px"><input type="text" name="rg2000"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rg3000yb"
						value="1">3000</td>
					<td colspan="1" width="35px"><input type="text" name="rg3000"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb4000"
						value="1">4000</td>
					<td colspan="1" width="35px"><input type="text" name="rg4000"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rgyb6000"
						value="1">6000</td>
					<td colspan="1" width="35px"><input type="text" name="rg6000"
						required="false" disable="false" value="" onblur="rgvag()"
						class="text"></td>
					<lemis:texteditor property="rgavg" label="ƽ��" required="false"
						disable="false" onclick="rgvag()" />
				</tr>
			</table>

			<lemis:tabletitle title="�Ҷ�����(�ڱ����)" />
			<table class="tableinput">
				<tr>
					<td width="90px"><input type="checkbox" name="rqyb250"
						value="1">250</td>
					<td colspan="1" width="35px"><input type="text" name="rq250"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb500"
						value="1">500</td>
					<td colspan="1" width="35px"><input type="text" name="rq500"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb750"
						value="1">750</td>
					<td colspan="1" width="35px"><input type="text" name="rq750"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb1000"
						value="1">1000</td>
					<td colspan="1" width="35px"><input type="text" name="rq1000"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb1500"
						value="1">1500</td>
					<td colspan="1" width="35px"><input type="text" name="rq1500"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb2000"
						value="1">2000</td>
					<td colspan="1" width="35px"><input type="text" name="rq2000"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb3000"
						value="1">3000</td>
					<td colspan="1" width="35px"><input type="text" name="rq3000"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb4000"
						value="1">4000</td>
					<td colspan="1" width="35px"><input type="text" name="rq4000"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<td width="90px"><input type="checkbox" name="rqyb6000"
						value="1">6000</td>
					<td colspan="1" width="35px"><input type="text" name="rq6000"
						required="false" disable="false" value="" onblur="rqvag()"
						class="text"></td>
					<lemis:texteditor property="rqavg" label="ƽ��" required="false"
						disable="false" onclick="rqvag()" />
				</tr>
			</table>
			<lemis:tabletitle title="�������" />
			<table class="tableinput">
				<tr>
					<lemis:texteditor property="ls250" label="250" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls500" label="500" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls750" label="750" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls1000" label="1000" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls1500" label="1500" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls2000" label="2000" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls3000" label="3000" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls4000" label="4000" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="ls6000" label="6000" required="false"
						disable="false" onblur="lsvag()" />
					<lemis:texteditor property="lsavg" label="ƽ��" required="false"
						disable="false" onclick="lsvag()" />
				</tr>
			</table>
			<lemis:tabletitle title="�Ҷ�����" />
			<table class="tableinput">
				<tr>
					<lemis:texteditor property="rs250" label="250" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs500" label="500" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs750" label="750" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs1000" label="1000" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs1500" label="1500" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs2000" label="2000" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs3000" label="3000" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs4000" label="4000" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rs6000" label="6000" required="false"
						disable="false" onblur="rsvag()" />
					<lemis:texteditor property="rsavg" label="ƽ��" required="false"
						disable="false" onclick="rsvag()" />
				</tr>
			</table>
			<lemis:tabletitle title="�����������Խ��" />
			<table class="tableinput">

				<tr>
					<lemis:texteditor property="fcypgl" label="����ǿ����" required="false"
						disable="false" />
					<td width="5px">(db)</td>
					<lemis:texteditor property="fcypgr" label="����ǿ����" required="false"
						disable="false" />
					<td width="5px">(db)</td>
					<lemis:texteditor property="fcypwzt" label="δ����" required="false"
						disable="false" />
					<td width="5px">%</td>
					<lemis:texteditor property="fcypdzl" label="����������" required="false"
						disable="false" />
					<td width="5px">%</td>
					<lemis:texteditor property="fcypdzr" label="����������" required="false"
						disable="false" />
					<td width="5px">%</td>
					<lemis:texteditor property="fcypsz" label="˫������" required="false"
						disable="false" />
					<td width="5px">%</td>
				</tr>
			</table>
		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>

