<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants" %>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("bsc011", "�ŵ����",TagConstants.DF_CENTER));
	header.add(new Formatter("bsc012", "�ŵ�",TagConstants.DF_CENTER));
	header.add(new Formatter("scoredate", "����ʱ��",TagConstants.DF_CENTER));
	header.add(new Formatter("detail", "��ϸ",TagConstants.DF_CENTER));
	header.add(new Formatter("pdtnm", "��Ʒ����",TagConstants.DF_CENTER));
	header.add(new Formatter("num", "����",TagConstants.DF_CENTER));
	header.add(new Formatter("score", "��Ʒ�һ�����",TagConstants.DF_CENTER));
	header.add(new Formatter("changemon", "����ȯ���Ļ���",TagConstants.DF_CENTER));
	header.add(new Formatter("buyscore", "�������ͻ���",TagConstants.DF_CENTER));
	header.add(new Formatter("actiscore", "����ͨ�����ͻ���",TagConstants.DF_CENTER));
	header.add(new Formatter("applycoin", "�������ͻݶ���",TagConstants.DF_CENTER));
	header.add(new Formatter("changecoin", "�ݶ��ҵֿ��ֽ�",TagConstants.DF_CENTER));

	Map<String,String> buttons = new LinkedHashMap<String,String>();
	buttons.put("�� ��","detail(document.all.tableform)");
	buttons.put("�� ӡ","print(document.all.tableform)");
	buttons.put("�� ��","history.back()");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("bsc011", "�ŵ�");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "bsc011", "�ŵ�"));
	editors.add(new Editor("date", "startdate", "��ʼʱ��"));
	editors.add(new Editor("date", "enddate", "����ʱ��"));
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	session.setAttribute("tableheader", "�ŵ����ͳ�Ʋ�ѯ");//��ͷ

	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>
<script src="/lemis/js/lemisTree.js"></script>
	<script src="/lemis/js/BaseObj.js"></script>
	<script src="/lemis/js/EAPObjsMgr.js"></script>
	<script src="/lemis/js/SelectObj.js"></script>
	<script src="/lemis/js/QuickSelectObj.js"></script>
	<script type="text/javascript">
	function detail(obj){
		var t = editObj("chk");
		if(!t){
			return t;
		}
		obj.action = '<html:rewrite page="/ChargeAction.do?method=detailScore&"/>'+getAlldata(obj);			
		obj.submit();
		}
	//print
	function print(obj){
		var startdate=$("#startdate").val();
		var enddate=$("#enddate").val();
		if(startdate==null||enddate==null){
			alert('��ʼʱ��ͽ���ʱ�䲻��Ϊ��');
			return false;
		}
		obj.action = '<html:rewrite page="/ChargeAction.do?method=printScore&startdate='+startdate+'&enddate='+enddate+'&"/>'+getAlldata(obj);			
		obj.submit();
	}
	
	$(document).ready(function(){
		var grCli="";
		grCli=<%=dto.getBsc001()%>;
		var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{", "").replace("}", "").split(", ");
		
		if(grCli=="1501000000")
		{
			$("input[name=bsc011]").autocomplete(shops,{
				max:10,
				matchContains:true
			});
			
			$("input[name=bsc011]").result(function(event,data,formatted){
				if(data){
					var gid = data[0].substring(0,data[0].indexOf("="));
					$("input[name=bsc011]").val(gid);
					$(this).parent().next().find("input").val(gid);
				}
			});
		}
		else{
			$("input[name=bsc011]").val("<%=dto.getBsc011()%>");
			$("input[name=bsc011]").attr("readonly","true");
			$("input[value=����[R]]").bind("click",function(e){
			$("input[name=bsc011]").val("<%=dto.getBsc011()%>");
					
			});
		}
		
	});
</script>
	<lemis:base />
	<lemis:body>
		<lemis:title title="�ŵ����ͳ�Ʋ�ѯ" />
		<lemis:query action="/ChargeAction.do?method=queryScore" editorMeta="editor" topic="��Ϣ��ѯ" />
		<lemis:table action="ChargeAction.do" headerMeta="header" topic="�ŵ���Ϣ" hiddenMeta="hidden" mode="radio" />
		<lemis:buttons buttonMeta="button" />
		<lemis:errors />
		<lemis:bottom />
	</lemis:body>
</html>


