<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("gctnm", "�ŵ�����"));
	header.add(new Formatter("gctarea", "��������"));
	header.add(new Formatter("bb", "���Ʒ��"));
	header.add(new Formatter("bmresult", "�������"));
	header.add(new Formatter("batcountnum","��������"));


	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","ivtyear","�����","true"));
	editors.add(new Editor("text","ivtmonths","��","true"));
	editors.add(new Editor("text","ivtyearEnd","����","true"));
	editors.add(new Editor("text","ivtmontht","��","true"));
	editors.add(new Editor("text","ivtgcltid","�ŵ����"));
	editors.add(new Editor("text","gctarea","��������"));
	editors.add(new Editor("text","bb","���Ʒ��"));
	editors.add(new Editor("text","bm","�������"));
	

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("�� ��","closeWindow(\"\")");
	
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("editor", editors);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script type="text/javascript">

$(document).ready(function(){
	
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

	var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
	
	$("input[name=ivtgcltid]").autocomplete(shops,{
		max : 10,
		matchContains : true
	});
 	$("input[name=ivtgcltid]").result(function(event, data, formatted) {
		if (data){
			var gnm = data[0].substring(data[0].indexOf("=")+1);
			var gid = data[0].substring(0,data[0].indexOf("="));
			$("input[name=ivtgcltid]").val(gid);
			$(this).parent().next().find("input").val(gid);
		}
	}); 


<%-- 	$.ajax({
		 type:'post',
		 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
		 dataType:'json',
		 error:function(){
		   alert('��ȡ���ݴ���');
		 },
		 success:function(data){
					$("input[name=pdtnm]").autocomplete(data,{
						max:10,
						matchContains:true,
						formatItem:function(data,i,max){
							return (data.proid + "=" + data.proname);
						}
					});	
					$("input[name=pdtnm]").result(function(event, data, formatted) {
						if (data){
							var pid=data.proid;
							var pnm = data.proname;
							$("input[name=pdtnm]").val(pnm);

						}
					});
				}
		}); --%>
//});

});
</script>
<lemis:base />
<lemis:body>
	<lemis:title title="���͵����Ϣ��ѯ" />
	<lemis:query action="/BusinessAction.do?method=batCount" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="���͵����ϸ��Ϣ"
		mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


