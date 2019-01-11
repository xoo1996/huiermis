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
	header.add(new Formatter("pdtid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("fdtprc", "��Ʒ����"));
	header.add(new Formatter("fdtqnt", "�۳�����"));
	//header.add(new Formatter("ncdnum", "�ͻ�����"));
	header.add(new Formatter("fdtdisc", "��Ʒ����"));
	header.add(new Formatter("fdtrprc", "ʵ���շ�"));
	header.add(new Formatter("chgnt", "��ע"));
	

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "pdtid", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "pdtnm", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "fdtprc", "��Ʒ����", "true"));
	batchInput.add(new Editor("text", "fdtdisc", "��Ʒ����", "true"));
	batchInput.add(new Editor("-nnnnn", "fdtqnt", "�۳�����", "true"));
	//batchInput.add(new Editor("-nnn", "ncdnum", "�ͻ�����", "true"));
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
<script src="/huiermis/js/BatBrandJson.js"></script>
<script src="/huiermis/js/BatTypeJson.js"></script>
 <script type="text/javascript">
 //��ؼ���
        $(function () {
            $.each(batbrand, function (k, p) { 
                var option = "<option value='" + p.braID + ',' + p.braName +"'>" + p.braName + "</option>";
                $("#batbra").append(option);
            });
            $("select[name=batbra]").val("0,��");
            if($("select[name=batbra]").val()=="0,��"){
            	var option = "<option value='0'>��</option>";
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
			
		var gby=$('input[name=gby]').val();
		var bm=$('select[name=bm]').val();
		var phone = $('input[name=icttel]').val();
		
		//�޸� ���ж����͵��Ϊ0 --�� �Ƿ�ֱ���� gby!="0"
		if("<%=bi%>"=="t"){
			phone = trim(phone);
			if(phone.length!=10&&phone.length!=11&&phone.length!=12){
				alert("��ǰ�û���ϵ��ʽ��������Ӧ����д��ȷ���ֻ����������ŵĹ̶��绰");
				$('input[name=icttel]').attr("disable",false);
				$('input[name=icttel]').val("");
				return false;
			}
		}
		
		if (gby==null||gby==""){
			alert("���͵������δ��д");
			return false;
		}
		if (bm==null||bm==""){
			alert("���͵���ͺ�δ��д");
			return false;
		}
		
		if(gby!="0"&&bm=="0"){
			alert("����ͺ�δ��д");
			return false;
		}
		
		if(gby=="0"&&bm!="0"){
			alert("������δ��д");
			return false;
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
					"&gby="+$("input[name=gby]").val()+"&bm="+$("select[name=bm]").val(); 
		}
		else
			return t;
		//}
	}
	
	
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
	
	/* function compute3(e) {
		var id = $(e.target).attr("id");
		var suffix = id.substr(6);
		//var bs = ($("#fdtqnt" + suffix).val())/($("#ncdnum" + suffix).val());
		var a=parseInt($("#fdtqnt" + suffix).val());
		var b=parseInt($("#ncdnum" + suffix).val());
		var c=(a/b);
		//alert(a+' '+b+' '+c);
		if(c<1||c>2){
			alert("�ͻ��������������۳�����/�ͻ���������ֵӦ����1-2֮��");
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
				<lemis:texteditor property="icttel" label="�û��绰" disable="false" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
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
			</tr>
			<tr id="bi" style="display:none">
			<td>
					<font color='#FF0000'>*</font>���͵��Ʒ��</td>
					<td colspan="1">
					<select label='���͵��Ʒ��' style='font-size:12px' name='batbra' id='batbra'
					class='select'  required='true'><option
						value='' selected> ��ѡ��</option></select>
					</td>
					
					<td><font color='#FF0000'>*</font>���͵���ͺ�</td>
					<td colspan="1">
					<select label='���͵���ͺ�' style='font-size:12px' name='bm' id='bm'
					class='select'  required='true'><option
						value='' selected> ��ѡ��</option></select>
					</td>
					<%-- <lemis:codelisteditor type="gby" isSelect="true" label="���͵�ؼ��꣨�ţ�"
					redisplay="true" required="true" /> --%>
					<lemis:texteditor property="gby"  label="���͵���������ţ�" disable="false" 
						required="true" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/>
			</tr>
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


