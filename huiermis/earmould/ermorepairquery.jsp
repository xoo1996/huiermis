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
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("repfolid", "订单号"));
	header.add(new Formatter("repgctnm", "客户名称"));
	header.add(new Formatter("repcltnm", "用户姓名"));
	header.add(new Formatter("repid", "机身编号"));
	header.add(new Formatter("reppnm", "助听器型号"));
	header.add(new Formatter("repfdate", "完工日期"));
	header.add(new Formatter("repaction1","维修内容"));
	//header.add(new Formatter("repgctid", "送修单位"));
	/* header.add(new Formatter("reppdate", "计划完工")); */
	header.add(new Formatter("repsta", "维修状态"));
	header.add(new Formatter("repcpy", "维修厂商"));
	
	Map<String,String> buttons=new LinkedHashMap<String,String>();
	//buttons.put("定制机详细信息","detailci(document.all.tableform)");
	
	buttons.put("返 回","history.back()");
	buttons.put("关 闭","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("repidentity", "维修流水号");
	hidden.put("repcltnm", "用户姓名");
	hidden.put("reppnm", "助听器型号");
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text","repfolid","订单号"));
	editors.add(new Editor("text","repid","机身编号"));
	editors.add(new Editor("text","repcltnm", "用户姓名"));
	editors.add(new Editor("text","repcpy", "维修厂商"));
	editors.add(new Editor("date","start","完工日期"));
	editors.add(new Editor("date","end","到"));
	editors.add(new Editor("text","repsta", "维修状态"));
	
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
%>
<html>
<script language="javascript">
		//修改记录
  		function modify(obj){
			var t = editObj("chk");
			if(!t){
				return t;
			}
			obj.action = '<html:rewrite page="/RepairAction.do?method=modify&"/>'+getAlldata(obj);		
			obj.submit();
  		}
		//删除记录
  		function deleteci(obj){
			var t = editObj("chk");
			if(!t){
				return t;
			}
			if(confirm("确定要删除该维修记录吗?")) {
				obj.action = '<html:rewrite page="/RepairAction.do?method=delete&"/>'+getAlldata(obj);		
				obj.submit();
			}
  		}
	</script>

<lemis:base />
<lemis:body>
	<lemis:title title="维修记录修改查询" />
	<lemis:query action="/RepairAction.do?method=query&order=ermorepairquery"
		editorMeta="editor" topic="查询条件" />
	<lemis:table action="RepairAction.do" headerMeta="header" topic="维修记录"
		hiddenMeta="hidden" mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


