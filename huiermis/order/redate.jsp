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
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("fdtfno", "������")); //
	header.add(new Formatter("folctnm", "��������"));
	header.add(new Formatter("fdtcltnm", "���˿ͻ�")); 
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("fdtqnt", "����"));
	header.add(new Formatter("fdtprc", "�ۼ�", "color:#000000;", TagConstants.DT_MONEY));
	header.add(new Formatter("folsdt", "��������","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("fdtodt", "�˻�����","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("fdtexadt", "�������","", TagConstants.DT_YEAR_MONTH_DATE));
	header.add(new Formatter("gctarea", "��������"));
	header.add(new Formatter("fdtreason", "�˻�ԭ��"));
	header.add(new Formatter("storetype", "ֱ������"));
	header.add(new Formatter("jqtype", "��������"));

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("fdtfno", "��������");
	hidden.put("folno","������");
	hidden.put("fdtqnt","����");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "fdtfno", "������"));
	editors.add(new Editor("text", "gctarea", "��������"));
	editors.add(new Editor("text","folctnm","��������"));
	editors.add(new Editor("text", "fdtcltnm", "���˿ͻ�"));
	editors.add(new Editor("text", "foldreason", "�˻�ԭ��")); 
	editors.add(new Editor("text", "beginend", "��������"));
	editors.add(new Editor("date","start","���ڴ�"));
	editors.add(new Editor("date","end","��"));
	editors.add(new Editor("text", "storetype", "ֱ������"));
	editors.add(new Editor("text", "mtype", "��������"));
	
	
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">
	$(document).ready(function(){
		$(document).ready(function(){
			 var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		     
				if(shops.length==1)
				{
					$("input[name=folctnm]").val(shops[0].substring(shops[0].indexOf("=")+1,shops[0].length));
					$("input[name=folctnm]").attr("readonly","true");
				}
				
				$("input[name=folctnm]").autocomplete(shops,{
					max:10,
					matchContains:true,
					formatItem:function(data,i,max){
						return data[0].substring(0);
					}
				});
				
				$("input[name=folctnm]").result(function(event,data,formatted){
					if(data){
						var gid = data[0].substring(0,data[0].indexOf("="));
						var gnm=data[0].substring(data[0].indexOf("=")+1,data[0].length);
						$("input[name=folctnm]").val(gnm);
					/* 	$("input[@type=hidden][name=folctid]").val(gid);
						$("input[@type=hidden][name=folctid]").val(); */
						
					}
				});
		});
		
	});
</script>
<lemis:base />
<lemis:body>
	<lemis:title title="�˻���ѯ" />
	<lemis:query action="/OrderAction.do?method=requery2" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="OrderAction.do" headerMeta="header" topic="�˻�����"
		hiddenMeta="hidden" mode="radio" />
			
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


