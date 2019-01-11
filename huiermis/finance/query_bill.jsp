<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%	
	String notype = (String)request.getSession().getAttribute("notype");
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("notype", "��������"));
	header.add(new Formatter("gctnm", "���̱��"));
	header.add(new Formatter("pdtid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("pdtnum", "����"));
	header.add(new Formatter("finrate", "��Ʊ����"));
	header.add(new Formatter("finprc", "��˰����"));
	header.add(new Formatter("finprccount", "�ܶ�"));
	header.add(new Formatter("finnt", "��Ʊ��ע"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("������ͨ�������(����)","creinfo(document.all.tableform)");
	buttons.put("������ͨ�������(����)","creretailinfo(document.all.tableform)");
	buttons.put("����ר�ö������(����)","crespecialinfo(document.all.tableform)");
	buttons.put("����ר�ö������(����)","cresperetailinfo(document.all.tableform)");
	buttons.put("���˿�Ʊ����","refuseFin(document.all.tableform)");
	buttons.put("�� ��","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("fintype", "��Ʊ����");
	hidden.put("notype", "��������");
	hidden.put("gctid", "���̱��");
	hidden.put("pdtid", "��Ʒ����");
	hidden.put("pdtnum", "����");
	hidden.put("finno", "���");

	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "fintype2", "��Ʊ����"));
	editors.add(new Editor("text", "notype", "��������"));
	editors.add(new Editor("text", "gctnm", "��������"));
	editors.add(new Editor("text", "ictnm", "��������"));
	editors.add(new Editor("text", "pdtid", "��Ʒ���"));
	editors.add(new Editor("text", "finisver", "�Ƿ������"));
	
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>

<script>
	//��Ʒ����������
	$(document).ready(function(){
			$.ajax({
				 type:'post',
				 url:'<%=basePath%>ProductAction.do?method=queryEMPro',
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
									var pid=data.proid;
									$("input[name=pdtid]").val(pid);
									

								}
							});
						}
				});
		});	
</script>

<script type="text/javascript">
		$(document).ready(function(){
			var grCli="";
			grCli=<%=dto.getBsc001()%>;
			if(grCli=="1501000000")
			{
				var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
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

<script language="javascript">

function creinfo(obj) {
	var li=$(".buttonGray");
	for(var i=0;i<li.length;i++){
		if(li[i].value=="������ͨ�������(����)") {
			li[i].disabled=true;
		}
	}
	window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/finance/FinanceAction.do?method=creInfo";
};

function creretailinfo(obj) {
	var li=$(".buttonGray");
	for(var i=0;i<li.length;i++){
		if(li[i].value=="������ͨ�������(����)") {
			li[i].disabled=true;
		}
	}
	window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/finance/FinanceAction.do?method=creRetailInfo";
};

function crespecialinfo(obj) {
	var li=$(".buttonGray");
	for(var i=0;i<li.length;i++){
		if(li[i].value=="����ר�ö������(����)") {
			li[i].disabled=true;
		}
	}
	window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/finance/FinanceAction.do?method=creSpecialInfo";
};

function cresperetailinfo(obj) {
	var li=$(".buttonGray");
	for(var i=0;i<li.length;i++){
		if(li[i].value=="����ר�ö������(����)") {
			li[i].disabled=true;
		}
	}
	window.location.href = "/"
		+ lemis.WEB_APP_NAME
		+ "/finance/FinanceAction.do?method=creSperetailInfo";
};

function refuseFin(obj){
	if (!checkValue(document.forms[0])) {
		return false;
	}
	if (!delObj("chk")) {
		return false;
	}
	if (!preCheckForBatch()) {
		return false;
	}
	
	if (confirm("ȷ�ϻ��˿�Ʊ���룿")) {
		
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="���˿�Ʊ����") {
				li[i].disabled=true;
			}
		}
		window.location.href = "/"
			+ lemis.WEB_APP_NAME
			+ "/finance/FinanceAction.do?method=refuseFin&"
			+ getAlldata(document.all.tableform);
	}
};
 
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="��ѯ��Ʊ����" />
	<lemis:query action="/FinanceAction.do?method=queryBind&flag=master" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="FinanceAction.do" headerMeta="header" topic="��Ʊ��Ϣ" 
		hiddenMeta="hidden" mode="checkbox" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


