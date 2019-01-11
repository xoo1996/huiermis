<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("gctnm","��������"));
	header.add(new Formatter("gctarea", "��������"));
	header.add(new Formatter("webyear", "���"));	
	header.add(new Formatter("webmonth", "�·�"));	
	header.add(new Formatter("websource", "������Դ����"));
	header.add(new Formatter("websell","������������"));
	header.add(new Formatter("webnum", "��������̨��"));
	//header.add(new Formatter("webcvt", "�ܲ�ת�����۶� "));
	header.add(new Formatter("weblocal","�ط��������۶�"));
	header.add(new Formatter("webcvt","�ܲ�ת�����۶�"));
	header.add(new Formatter("webtotal","�ϼ��������۶�"));
	//header.add(new Formatter("webmoney","�������۶�"));
	header.add(new Formatter("webrate", "�������۶�ռ�������۶�ٷֱ�"));
	header.add(new Formatter("webmark", "��ͼ��ע"));
	header.add(new Formatter("webcheck","�����Ǽ�"));
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("�޸�","modify(document.all.tableform)");
	buttons.put("����","detail(document.all.tableform)");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","gctnm","��������"));
	editors.add(new Editor("text","gctarea","��������"));
	editors.add(new Editor("text","webmark","��ͼ��ע"));
	editors.add(new Editor("text","ivtyear","���","true"));
	editors.add(new Editor("text","ivtmonth","�·�","true"));
	editors.add(new Editor("text","webcommit","�ŵ��Ƿ��ύ"));
	//editors.add(new Editor("date","stodate","�������"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ivtyear", "��");
	hidden.put("ivtmonth", "��");
	hidden.put("gctnm", "��������");
	hidden.put("webyear", "��");
	hidden.put("webmonth", "��");
	
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
	
	LoginDTO dto = (LoginDTO)session.getAttribute("LoginDTO");
	String grCli_cx=dto.getBsc011();
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript">
	function detail(obj) {
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/BusinessAction.do?method=queryWebSell&tp=t&"/>'+getAlldata(obj);			
		obj.submit();
		//�������ύ����̨ ע�⣺getAlldata(document.all.tableform)�ǵõ�����Ҫ�ύ�����ݡ�
	};
	
	function modify(obj) {
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/BusinessAction.do?method=modifyWebSell&"/>'+getAlldata(obj);			
		obj.submit();
		//�������ύ����̨ ע�⣺getAlldata(document.all.tableform)�ǵõ�����Ҫ�ύ�����ݡ�
	};

$(document).ready(function(){
	$("select[name=webcommit]").val("yes");//N��Y��
	
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
	<lemis:title title="����������Դ��ѯ" />
	<lemis:query action="/BusinessAction.do?method=staquery" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="BusinessAction.do" headerMeta="header" topic="����������Դ��Ϣ"
		hiddenMeta="hidden"  mode="radio"  />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


