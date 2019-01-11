<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    
    List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "feeyear", "��","true"));
	editors.add(new Editor("text", "feemonth", "��","true"));
	editors.add(new Editor("text","gctarea","��������"));
	
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("feegctid","�ͻ����"));
	header.add(new Formatter("feegctname","�ͻ�����"));
	header.add(new Formatter("gctarea","��������"));
	header.add(new Formatter("feestatus","��д״̬"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("feegctid", "�û����");
	hidden.put("feeyear", "��");
	hidden.put("feemonth", "��");
	hidden.put("gctarea", "��������");

	pageContext.setAttribute("editor",editors);
    pageContext.setAttribute("header", header);
    pageContext.setAttribute("hidden",hidden);
%>


<%@page import="org.radf.plat.taglib.Editor"%>
<%@page import="org.radf.plat.taglib.TagConstants"%><html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/lemis/js/lemisTree.js"></script>
<script src="/lemis/js/BaseObj.js"></script>
<script src="/lemis/js/EAPObjsMgr.js"></script>
<script src="/lemis/js/SelectObj.js"></script>
<script src="/lemis/js/QuickSelectObj.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script language="javascript">	
	/* function query(obj) {
		if (!checkValue(obj)) {
			return false;
		}
		obj.submit();
	}; */
	
		$(document).ready( function() {
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
						$(this).parent().next().find("input").val(gid);
					}
				});
			});
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="��ѯδ�ύ�ŵ�" />
	<lemis:query action="/FeeAction.do?method=queryNoSubmit" editorMeta="editor" topic="��ѯ����" />
	<lemis:table action="/FeeAction.do" topic="��ѯδ�ύ�ŵ�" headerMeta="header" hiddenMeta="hidden" mode="radio" />
	<lemis:bottom />
</lemis:body>
</html>