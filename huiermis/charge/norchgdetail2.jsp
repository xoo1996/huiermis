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
	header.add(new Formatter("pdtid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("fdtprc", "��Ʒ����"));
	header.add(new Formatter("fdtqnt", "�۳�����"));
	header.add(new Formatter("fdtdisc", "��Ʒ����"));
	header.add(new Formatter("fdtrprc", "ʵ���շ�"));
	header.add(new Formatter("chgnt", "��ע"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "pdtnm", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "fdtprc", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "fdtdisc", "��Ʒ����", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "�۳�����", "true"));
	batchInput.add(new Editor("text", "fdtrprc", "ʵ���շ�", "true"));
	batchInput.add(new Editor("text", "chgnt", "��ע", "false"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("�� ӡ","print()");
	buttons.put("��������", "batchSubmit(document.all.tableform)");
	buttons.put("�� ��","history.back()");
	buttons.put("�� ��", "closeWindow(\"\")");

	 Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("chgid", "�շѺ�"); 
	
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
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
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
							 str+="��"+(i+1)+"����Ʒ����С����Ϊ"+data[i].mindis+"\n";
							 flag=false;
						 });
						
				     }
					 if(flag==false)
					 {
					 	if(confirm(str+"����Ŀ����ѵ�����Ϳ��ʣ�ȷʵҪ����������?","��","��"))
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
			alert("������ͻ�������");
			return false;
		}
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}/* 
		if(!confirm("ȷ����������?")){
			window.event.returnValue = false;
		}else{ */
			//֪��ϵͳ�ж�
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
			alert("ʹ�ûݶ�����Ŀ�������!");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$('input[name=money]').val(parseInt(mon-fee*num));
			return false;
		}
		if((parseInt(num)!=num)||(parseInt(num)<0)){
			alert("����ȯ��Ŀ�������!");
			$("input[name=num]").val(0);
			$('input[name=money]').val(parseInt(mon-coinnum));
			return false;
		}
		if(parseInt(coin)<parseInt(coinnum)){
			alert("�ݶ��Ҳ���");
			$("input[name=coinnum]").val(0);
			$("input[name=changecoin]").val(0);
			$('input[name=money]').val(parseInt(mon-fee*num));
			return false;
		}
		if(parseInt(score)<parseInt(sco*num)){
			alert("���ֲ���");
			$("input[name=num]").val(0);
			$('input[name=money]').val(parseInt(mon-coinnum));
			return false;
		}
		if(money<0||(money!=parseInt(mon-coinnum-fee*num))){
			var m=parseInt(mon-coinnum-fee*num);
			alert("������ʵ�����Ϊ"+m+",ȷ�Ϻ����ٴ��ύ");
			$('input[name=money]').val(m);
			return false;
		}
		if(<%=phone.length() %>!=8&&<%=phone.length() %>!=11&&<%=phone.length() %>!=12){
			alert("�ÿͻ���Ϣ����ϵ��ʽ��Ч,��Ҫ��Ϊ�ݶ������̳ǻ�Ա�����������ͻ���Ϣ");
		}
		
		if (confirm("ȷʵҪ�շ���")) {
			$("input[value='��������']").attr('disabled','true');
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
		//���㹴ѡ�������ܼ�
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
			   alert('��ȡ���ݴ���');
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
			alert("���շ���Ϣ���뱣����ٴ�ӡ��");
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
		value = value.toFixed(2);//������λ��Ч����
		$("#fdtrprc" + suffix).val(value);
		//����������ܼ�
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
			   alert('��ȡ���ݴ���');
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
		value = value.toFixed(2);//������λ��Ч����
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
			   alert('��ȡ���ݴ���');
			 },
			 success:function(data){
					var num = parseInt(data[0].num);
						var p = parseInt($("input[name=ncdnum]").val());
						var c=(num/p);
						 if(num==0){
							 ;
						 }else if(c<1||c>2){
							alert("�ͻ������������󣬶������۳�����/�ͻ����� ��ֵӦ����1-2֮��");
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
			alert("������ִ���ȯ���ø���"+rate+"%");
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
				alert("�ݶ��ҵֿ�100%�����������߲��ø���30%�������۸�");
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
			var discount = $("input[name=fdtdisc]").val();//��Ʒ����
			if(discount < 0.7){
				alert("���ʲ��ܵ���0.7�����Ҫ����0.7�����ܲ����룡");
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
			   alert('��ȡ���ݴ���');
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

    <lemis:title title="��ͨ��Ʒ�շ�����" />
   
	<lemis:tabletitle title="�շ���Ϣ" />
	<table class="tableinput">
		<lemis:editorlayout />
		 <html:form action="/ChargeAction.do?method=saveNormalCharge"
			method="POST">
			<tr>
				<html:hidden property = "ictid"/>
				<html:hidden property = "chgid"/>
				<lemis:texteditor property="folctnm" label="��������ͻ�" disable="true"/>
				<lemis:texteditor property="ictnm" label="�û�����" disable="true"/>
				<lemis:texteditor property="ictgender" label="�Ա�" disable="true"/>
			</tr>
			<tr>
				<lemis:texteditor property="ictaddr" label="�û���ַ" disable="true" />
				<lemis:texteditor property="icttel" label="�û��绰" disable="true"/>
				<lemis:texteditor property="ictpnm" label="�ҳ�����" disable="true"/>
			</tr>
			<tr>
				<td>�շ�����</td>	
				<td><lemis:operdate /></td>
				<%-- <lemis:texteditor property="chgid" label="�շѺ�" disable="true" /> --%>
				<lemis:texteditor property="ictgctid" label="�ͻ�����" disable="true" />
				<lemis:texteditor property="ncdypname" label="����ʦ����" disable="false" />		
			</tr>
			<tr>
				<lemis:texteditor property="ncdnum" label="�ͻ�����" disable="false" required="true"/>
				<lemis:texteditor property="balance" label="���������۽��" disable="false" required="true"/>
				<lemis:texteditor property="score" label="�������" required="false"
						disable="true" value="<%=score %>"/>
			</tr>
			<tr>
				<lemis:texteditor property="coin" label="�ݶ������" required="false"
						disable="true" value="<%=coin %>"/>
				<lemis:texteditor property="coinnum" label="ʹ�ûݶ�������" required="false"
						disable="false" value="0" onkeyup="computeL2()"/>
				<lemis:texteditor property="changecoin" label="�ֿ��ֽ�(Ԫ)" required="false"
						disable="false" value="0"/>
			</tr>
			<tr>
					<lemis:texteditor property="change" label="����ȯ" required="false"
						disable="true" value="<%=change %>"/>
					<lemis:texteditor property="num" label="����ȯ�һ�����" required="false"
						disable="false" value="0" onkeyup="computeL1()"/>
					<lemis:texteditor property="money" label="������ʵ�ս��" required="false"
						disable="false" />
			</tr>
			<input type="hidden" name="pdtid" id="pdtid" >
				<input type="hidden" name="sco" id="sco" value="<%=sco %>">
				<input type="hidden" name="fee" id="fee" value="<%=fee %>">
				<input type="hidden" name="rate" id="rate" value="<%=rate %>">
		</html:form>
	</table>
	<lemis:table topic="��Ʒ��ϸ¼��"
		action="/ChargeAction.do?method=batchSubmit" headerMeta="header"
		mode="checkbox" batchInputMeta="batchInput" orderResult="false"
		 batchInputType="insert" pilot="false" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
	
</lemis:body>
</html>


