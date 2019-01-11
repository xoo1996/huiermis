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
	Map<String,String> buttons=new LinkedHashMap<String,String>();
    buttons.put("�޸�","edit(document.all.tableform)");
    buttons.put("�� ��","closeWindow(\"\")");
    
    List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "iyear", "��","true"));
	editors.add(new Editor("text", "imonth", "��","true"));
	 editors.add(new Editor("text","gctid","�ͻ�����"));
	 editors.add(new Editor("text","gctarea","��������"));
	
	List<Formatter> header = new ArrayList<Formatter>();
	//header.add(new Formatter("feegctid","�ͻ�����"));
	
	header.add(new Formatter("gctname","�ͻ�����"));
	header.add(new Formatter("totalreturns","�����۶�"));
	header.add(new Formatter("invoiceamount","��Ʊ��"));
	header.add(new Formatter("invoicerate","��Ʊ����"));
	header.add(new Formatter("premoneyfunds","���»����ʽ�"));
	header.add(new Formatter("purchasecosts","���½�����"));
	header.add(new Formatter("actualpurchasecosts","ʵ������"));
	header.add(new Formatter("monthcosts","���·���"));
	header.add(new Formatter("monthtax","����˰��"));
	header.add(new Formatter("wagesreturn","���ʴ��"));
	header.add(new Formatter("elsecosts","����"));
	header.add(new Formatter("moneyfunds","���»����ʽ�"));
	header.add(new Formatter("preaccountpayable","����Ӧ���˿�"));
	header.add(new Formatter("accountpayable","����Ӧ���˿�"));
	header.add(new Formatter("inventory","���"));
	header.add(new Formatter("paidincapital","ʵ���ʱ�"));
	header.add(new Formatter("accountrecievable","����Ӧ���˿�"));
	header.add(new Formatter("wagespayable","Ӧ������"));
	header.add(new Formatter("elseaccountpayable","����Ӧ���˿�"));
	header.add(new Formatter("elsereceivables","����Ӧ���˿�"));
	header.add(new Formatter("preyearundisprofits","�����ۼ�δ��������"));
	header.add(new Formatter("preaccuprofit","�����ۼ�����"));
	header.add(new Formatter("monthprofit","��������",true));
	header.add(new Formatter("yearprofit","��������",true));
	header.add(new Formatter("accuundisprofits","�ۼ�δ��������",true));
	header.add(new Formatter("preaccuinvoiceamount","�����ۼƿ�Ʊ��",true));
	header.add(new Formatter("accuinvoiceamount","�ۼƿ�Ʊ��",true));
	header.add(new Formatter("preaccuactualsales","�����ۼ�ʵ�����۶�",true));
	header.add(new Formatter("accuactualsales","�ۼ�ʵ�����۶�",true));
	header.add(new Formatter("accuinvoicerate","�ۼƿ�Ʊ��",true));	
	header.add(new Formatter("preaccucostofsales","�����ۼ���Ӫҵ��ɱ�",true));
	header.add(new Formatter("accucostofsales","�ۼ���Ӫҵ��ɱ�",true));
	/* header.add(new Formatter("operatingandmanagementcosts","Ӫҵ���������",true));
	header.add(new Formatter("taxandaccoiatecharge","��Ӫҵ��˰�𼰸���",true));
	header.add(new Formatter("financingcosts","�������",true));  */
	header.add(new Formatter("profitrate","������",true));
	header.add(new Formatter("tax","����˰",true));
	header.add(new Formatter("bsc012","¼��Ա",true));
	header.add(new Formatter("operatedate","¼��ʱ��","",TagConstants.DT_YEAR_MONTH_DATE,true));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("gctid", "�û����");
	hidden.put("iyear", "��");
	hidden.put("imonth", "��");

	
	pageContext.setAttribute("editor",editors);
    pageContext.setAttribute("button", buttons);
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
	function edit(obj){
	var t = editObj("chk");
	if(!t){
		return t;
	}
	obj.action = '<html:rewrite page="/BranchFinancialAction.do?method=edit&"/>'+getAlldata(obj);			
	obj.submit();
	};
	$(document).ready( function() {
		
	       var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
			
			$("input[name=gctid]").autocomplete(shops,{
				max : 10,
				matchContains : true
			});
			$("input[name=gctid]").result(function(event, data, formatted) {
				if (data){
					var gnm = data[0].substring(data[0].indexOf("=")+1);
					var gid = data[0].substring(0,data[0].indexOf("="));
					$("input[name=gctid]").val(gid);
					$("input[name=gctname]").val(gnm);
					$(this).parent().next().find("input").val(gid);
				}
			});
		});
</script>

<lemis:body>
	<lemis:base />
	<lemis:errors />
	<lemis:title title="�ֹ�˾�����Ϣ" />
	<lemis:query action="/BranchFinancialAction.do?method=query" editorMeta="editor" topic="�ֹ�˾�����ѯ" />
	<lemis:table action="/BranchFinancialAction.do" topic="��ѯ�ֹ�˾���" headerMeta="header" hiddenMeta="hidden" 
	   mode="radio" />
	<lemis:buttons buttonMeta="button" />
	<lemis:bottom />
</lemis:body>
</html>


		
		
