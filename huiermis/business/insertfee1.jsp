<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("�� ��","saveData(document.forms[0])");
	buttons.put("�� ��","submitData(document.forms[0])");
    //buttons.put("�� ��","document.forms[0].reset();");
    buttons.put("�� ��","closeWindow(\"\")");
    pageContext.setAttribute("button", buttons);
%>
<%
Map<String,String> button1=new LinkedHashMap<String,String>();
	button1.put("�� ѯ","findData(document.forms[0])");
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
			alert("����д����");
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
		con=confirm("�ύ֮�󽫲����޸ģ�ȷ��Ҫ�ύ��"); //��ҳ���ϵ����Ի���
		if(con==true) {
			
			obj.submit();
		}
	};
	
	Number.prototype.toFixed=function(num) 
	   { 
	   //���¹���toFixed����,IE5.0+ 
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
		//�������
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
	<lemis:title title="����ͻ�������Ϣ" />
	<lemis:tabletitle title="<h3>¼�������Ϣ" />
	<table class="tableinput">

		<lemis:editorlayout />
		  <html:form action="/FeeAction.do?method=saveFee" method="POST">
			<tr>
				<lemis:texteditor property="feeyear" label="��" disable="false"
					required="true" maxlength="4" />
				<lemis:texteditor property="feemonth" label="��" disable="false"
					required="true" maxlength="2"/>
				<lemis:buttons buttonMeta="button1"/>
			</tr>

	
			<lemis:tabletitle title="<h3>����֧��"/>
			<table class="tableinput">
				<lemis:editorlayout />
				
				<tr>
					<lemis:texteditor   property="bfeerent" 
					label="����(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeeassets" 
					label="�̶��ʲ�(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeebuilt" 
					label="װ�޷�(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
					
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="bfeetax" label="˰��(���ɿ�)" disable="false" onblur="autobfeetotal()"/>
					<%-- <lemis:texteditor   property="bfeepension" 
					label="�籣��(���ɿ�)" disable="false" required="false" />
					<lemis:texteditor   property="bfeehousingfund"
					label="������(���ɿ�) " disable="false" required="false" /> --%>
					<lemis:texteditor   property="bfeeaccount"
					label="��ƹ���(���ɿ�) " disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   property="bfeeprocess" 
					label="������(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				
				<tr>			
					<lemis:texteditor   property="bzbfee" 
					label="�ܲ�����(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<%-- <tr>
					
					 <lemis:texteditor   property="bfeewage" 
					label="��˰����(���ɿ�)" disable="false" required="false" /> 
					<lemis:texteditor   property="bfee1othercharges"
					label="����(���ɿ�)" disable="false" required="false" />
				</tr> --%>
				
				<tr>
					<lemis:texteditor   property="bfeead" 
					label="����(�ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
					property="bfeephone" label="�绰��(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
					property="bfeewater" label="ˮ���(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="bfeetrip" label="���÷�(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
						property="bfeepostage" label="�ʷ�(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
					<lemis:texteditor   required="false"
						property="bfeeoffice" label="�칫��(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>				
					<lemis:texteditor   required="false"
					property="bfeesocial" label="���ط�(�ɿ�)" disable="false" onblur="autobfeetotal()"/>
					<%-- <lemis:texteditor   property="bfeebenefit"
					label="�Ż�(�ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/> --%>
					<lemis:texteditor   property="bfeeothercharges"
					label="����(�ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="true" onclick="autobfeetotal()"
						property="bfeetotal" label="����ϼ�"  disable="false" />
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="bfeent" label="��ע" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>

			<lemis:tabletitle title="<h3>��˰֧��" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="ffeerent" 
					label="����(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeeassets" 
					label="�̶��ʲ�(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeebuilt" 
					label="װ�޷�(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="ffeetax" label="˰��(���ɿ�)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeepension" 
					label="�籣��(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeehousingfund"
					label="������(���ɿ�) " disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="ffeeaccount"
					label="��ƹ��� (���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>	
					<lemis:texteditor   property="ffeeprocess" 
					label="������(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   property="ffeewage" 
					label="��˰����(���ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					
				</tr>
				 <tr>
					<lemis:texteditor   property="fzbfee" 
					label="�ܲ�����(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="ffeead" 
					label="����(�ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>            
					<lemis:texteditor   required="false"
					property="ffeephone" label="�绰��(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
					property="ffeewater" label="ˮ���(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="ffeetrip" label="���÷�(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
						property="ffeepostage" label="�ʷ�(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="false"
						property="ffeeoffice" label="�칫��(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="ffeesocial" label="���ط�(�ɿ�)" disable="false" onblur="autoffeetotal()"/>
					<%-- <lemis:texteditor   property="ffeebenefit"
					label="�Ż�(�ɿ�)" disable="false" required="false" /> --%>
					<lemis:texteditor   property="ffeeothercharges"
					label="����(�ɿ�)" disable="false" required="false" onblur="autoffeetotal()"/>
					<lemis:texteditor   required="true" onclick="autoffeetotal()"
						property="ffeetotal" label="��˰�ϼ�"  disable="false" />
					
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="ffeent" label="��ע" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>


			<lemis:tabletitle title="<h3>����Ԥ��" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="nfeerent" 
					label="����(���ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeeassets" 
					label="�̶��ʲ�(���ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeebuilt" 
					label="װ�޷�(���ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="nfeetax" label="˰��(���ɿ�)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeepension" 
					label="�籣��(���ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeehousingfund"
					label="������(���ɿ�) " disable="false" required="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="nfeeaccount"
					label="��ƹ���(���ɿ�) " disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   property="nfeeprocess" 
					label="������(���ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
					property="nfeewage" label="��˰����(���ɿ�)" disable="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   property="nzbfee" 
					label="�ܲ�����(���ɿ�)" disable="false" required="false" onblur="autobfeetotal()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="nfeead" 
					label="����(�ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeephone" label="�绰��(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeewater" label="ˮ���(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="false"
						property="nfeetrip" label="���÷�(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeepostage" label="�ʷ�(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="false"
						property="nfeeoffice" label="�칫��(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
					
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="nfeesocial" label="���ط�(�ɿ�)" disable="false" onblur="autonfeetotal()"/>
					<%-- <lemis:texteditor   property="nfeebenefit"
					label="�Ż�(�ɿ�)" disable="false" required="false" /> --%>
					<lemis:texteditor   property="nfeeothercharges"
					label="����(�ɿ�)" disable="false" required="false" onblur="autonfeetotal()"/>
					<lemis:texteditor   required="true" onclick="autonfeetotal()" property="nfeetotal" label="Ԥ��ϼ�"  disable="false" />
				</tr>
				
				<tr> 
					<lemis:textareaeditor required="false" property="nfeent" label="��ע" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>
				<lemis:tabletitle title="<h3>������֧����"/>
				<lemis:tabletitle title="<h3>�������" />
			<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeehuier" 
					label="�ݶ���Ʒ" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeweiting" 
					label="Ψ����Ʒ" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeqie" 
					label="ȫ���ά�ĳ��Ҳ�Ʒ" disable="false" required="false" onblur="autoRfeesales()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="rfeeelse" label="�������Ҳ�Ʒ" disable="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeerepair" 
					label="�����" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeeearmould"
					label="��ģ" disable="false" required="false" onblur="autoRfeesales()"/>
				</tr>
				<tr>
					<lemis:texteditor   property="rfeeparts" 
					label="��������" disable="false" required="false" onblur="autoRfeesales()"/>
					<lemis:texteditor   property="rfeebenefitm" 
					label="�Ż�(������)" disable="false" required="false" onblur="autoRfeesales()"/>
					<td colspan="2" align="left"><font size="6" color="red" ></font></td>
					<!-- <td><font></font></td> -->
				</tr>
			</table>
				<lemis:tabletitle title="<h3>������" />
				<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeesales" 
					label="�������۶�" disable="false" required="true" onblur="autoBusiness()" />
					<lemis:texteditor   required="true"
						property="rfeefinance" label="����" disable="false" onblur="autoBusiness()"/>
					<lemis:texteditor   required="false"
						property="rfeecollection" label="�������" disable="false"  onblur="autoBusiness()"/>
						
				</tr>
				<tr>
					<lemis:texteditor   required="true" onclick="autoBusiness()"
						property="rfeebusiness" label="����" disable="false" />
				</tr>
				<tr>
					<lemis:texteditor   required="false"
					property="rfeewagein" label="���ʻ���" disable="false" onblur="autoTotalin()"/>
					<lemis:texteditor   property="rfeeelin"
					label="��������" disable="false" required="false" onblur="autoTotalin()"/>
					<lemis:texteditor   property="rfeebenefitin"
					label="������֧��" disable="false" required="false" onblur="autoTotalin()" />
				</tr>
				<tr>
					<lemis:texteditor   property="rfeetotalin" onclick="autoTotalin()"
					label="�ϼƻ���" disable="false"   required="true" />
				</tr>
				</table>
				<lemis:tabletitle title="<h3>����֧�����" />
				<table class="tableinput">
				<lemis:editorlayout />
				<tr>
					<lemis:texteditor   property="rfeepresurplus" 
						label="���½���" disable="false" required="false" onblur="autoSurplus()"/>
					<lemis:texteditor   required="false"
						property="rfeebusin" label="�������" disable="false" onblur="autoSurplus()"/>
					<lemis:texteditor   required="false"
						property="rfeerealpay" label="ʵ��֧��" disable="false" onblur="autoSurplus()"/>
				</tr>
				<tr>
					<lemis:texteditor   required="true"
						property="rfeesurplus" label="���½���" disable="false" />
					<lemis:texteditor   required="false"
						property="rfeecosales" label="���º��������۶�" disable="false" />
					<lemis:texteditor   required="false"
						property="rfeeaccucosales" label="�����ۼƺ��������۶�" disable="false" />
				</tr>
				<tr> 
					<lemis:textareaeditor required="false" property="rfeent" label="��ע" disable="false"
					  maxlength="200" colspan="5" rowspan="5"/>
				</tr> 
			</table>
			

		</html:form>
	</table>
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
