<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
List<Formatter> header = new ArrayList<Formatter>();
header.add(new Formatter("gctnm","����ͻ�"));
header.add(new Formatter("ictnm","�û�����"));
header.add(new Formatter("gctarea","��������"));
//header.add(new Formatter("pdtid", "��Ʒ����"));
header.add(new Formatter("pdtnm", "��Ʒ����"));
header.add(new Formatter("pdtxh", "�������ͺ�"));
header.add(new Formatter("pdtyj", "ԭ��"));
header.add(new Formatter("fdtqnt", "����"));
/* header.add(new Formatter("fdtdisc", "��Ʒ����")); */
header.add(new Formatter("fdtrprc", "�ؿ��"));
header.add(new Formatter("pdtyouhui", "�Żݶ�"));
header.add(new Formatter("jsid", "������Ϣ"));
header.add(new Formatter("tmkordate","��������"));
header.add(new Formatter("chgnt", "��ע"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	
	buttons.put("�� ��","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	
	List<Editor> editors = new ArrayList<Editor>();
	
	editors.add(new Editor("text","gctnm","�ͻ�����"));
	editors.add(new Editor("text","cltnm","�û�����"));
	editors.add(new Editor("text","gctarea","��������"));
	editors.add(new Editor("text","gcttype","�ͻ�����"));
    editors.add(new Editor("text","pdtnm","��Ʒ����"));
	editors.add(new Editor("date","start", "�շ����ڴ�"));
	editors.add(new Editor("date","end", "��"));
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<script language="javascript">
		//��ʾ��ϸ��Ϣ
  		function detailci(obj){
			var t = editObj("chk");
			if(!t){
				return t;
			}
			obj.action = '<html:rewrite page="/CustomizationAction.do?method=print&"/>'+getAlldata(obj);			
			obj.submit();
  		};
  		function print(obj) {
  			var t = editObj("chk");
  			if (!t) {
  				return t;
  			}
  			else {
  				obj.action = '<html:rewrite page="/CustomizationAction.do?method=barcode&"/>' + getAlldata(obj);
  				obj.submit();
  			} 
  		};
	</script>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
$(document).ready(function(){
	$("input[name=pdtnm]").val("�Ż�");
	$("input[name=pdtnm]").attr('readonly','readonly');
	//HF0001 ����һ��(����һ) HF0010 ���۶������������  HF0011�������� (������) HF0012 ��������   HF0019 �������� (������)
	//HF0005  �������� (������)  HF0014 ��������  (������)  ������������������
	var grCli_cx="<%=grCli_cx%>";
    //$("input[name=gctnm]").val(grCli_cx);
	if(grCli_cx=="HF0001"){
		$("select[name=gctarea]").val("����һ");
		$("select[name=gctarea]").attr("disabled",true);
		
		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����һ");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
		    $("select[name=gctarea]").val("����һ");
		});
	}
	if(grCli_cx=="HF0010"){
		$("select[name=gctarea]").val("�����");
		$("select[name=gctarea]").attr("disabled",true);
		
		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("�����");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("�����");
		});
	}
	if(grCli_cx=="HF0011"){
		$("select[name=gctarea]").val("������");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("������");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("������");
		});
	}
	if(grCli_cx=="HF0019"){
		$("select[name=gctarea]").val("������");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("������");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("������");
		});
	}
	if(grCli_cx=="HF0024"){
		$("select[name=gctarea]").val("������");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("������");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("������");
		});
	}
	if(grCli_cx=="HF0013"){
		$("select[name=gctarea]").val("������");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("������");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("������");
		});
	}
	if(grCli_cx=="HF0014"){
		$("select[name=gctarea]").val("������");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("������");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("������");
		});
	}
	if(grCli_cx=="HF0002"){
		$("select[name=gctarea]").val("�����");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("�����");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("�����");
		});
	}
	if(grCli_cx=="HF0023"){
		$("select[name=gctarea]").val("�����");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("�����");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("�����");
		});
	}
	if(grCli_cx=="HF0012"){
		$("select[name=gctarea]").val("����ʮ");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����ʮ");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("����ʮ");
		});
	}
	if(grCli_cx=="HF0025"){
		$("select[name=gctarea]").val("����ʮһ");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����ʮһ");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("����ʮһ");
		});
	}
	if(grCli_cx=="HF0026"){
		$("select[name=gctarea]").val("����ʮ��");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����ʮ��");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("����ʮ��");
		});
	}
	if(grCli_cx=="HF0027"){
		$("select[name=gctarea]").val("����ʮ��");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����ʮ��");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("����ʮ��");
		});
	}
	if(grCli_cx=="HF0028"){
		$("select[name=gctarea]").val("����ʮ��");
		$("select[name=gctarea]").attr("disabled",true);

		$("input[value=����[R]]").bind("click",function(e){
			$("select[name=gctarea]").val("����ʮ��");
			$("select[name=gctarea]").attr("disabled",true);
		});
		$("input[value=��ѯ[Q]]").bind("click",function(e){
			$("select[name=gctarea]").attr("disabled",false);
			$("select[name=gctarea]").val("����ʮ��");
		});
	}
	/*
	else{
		var shops = "0123456789����һ��=������,0123456789���۶���=�����,0123456789��������=������,0123456789��������=������,0123456789��������=������,0123456789��������=������,0123456789��������=������,0123456789=������".split(",");
		alert(shops);
		$("input[name=gctarea]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=gctarea]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=gctarea]").val(gnm);
			}
		});
	}
    */
	
	
	var grCli="";
	grCli=<%=dto.getBsc001()%>;
	var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
	if(grCli=="1501000000")
	{
		$("input[name=gctnm]").autocomplete(shops,{
			max:10,
			matchContains:true,
			formatItem:function(data,i,max){
			return data[0].substring(0);
			}
		});
	
		$("input[name=gctnm]").result(function(event,data,formatted){
			if(data){
				var gid = data[0].substring(0,data[0].indexOf("="));
				var gnm=data[0].substring(data[0].indexOf("=")+1,data[0].length);
				$("input[name=gctnm]").val(gnm);
				}
			});
	}
	else
	{
		$("input[name=gctnm]").val("<%=dto.getBsc012()%>");
		$("input[name=gctnm]").attr("readonly","true");
		$("input[value=����[R]]").bind("click",function(e){
		$("input[name=gctnm]").val("<%=dto.getBsc012()%>");
		}); 
 	}

});
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="���Ƽ�¼��ѯ" />
	<lemis:query action="/BusinessAction.do?method=youhuiquery"
		editorMeta="editor" topic="��ѯ����" />
	<lemis:table action="BusinessAction.do" headerMeta="header"
		topic="���Ƽ�¼" hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


