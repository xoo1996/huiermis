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
	header.add(new Formatter("typeno", "���"));   
	header.add(new Formatter("gctnm", "��������"));
	header.add(new Formatter("ictnm", "���˿ͻ�"));
	header.add(new Formatter("pdtid", "��Ʒ����"));
	header.add(new Formatter("pdtnm", "��Ʒ����"));
	header.add(new Formatter("tmksid", "���ƻ�������"));
	header.add(new Formatter("chgdt", "�շ�����"));
	header.add(new Formatter("pdtprc", "ԭ��"));
	header.add(new Formatter("sellprc", "�ۼ�"));
	header.add(new Formatter("pdtnum", "����"));
	header.add(new Formatter("finprccount", "�ܶ�"));
	
	header.add(new Formatter("retailname", "��Ʊ̧ͷ"));
	header.add(new Formatter("retailtaxno", "��Ʊ˰��"));
	header.add(new Formatter("comaddr", "��˾��ַ"));
	header.add(new Formatter("depositbank", "������"));
	header.add(new Formatter("depositid", "�����˺�"));
	header.add(new Formatter("specialtel", "�绰"));

	Map<String,String> buttons=new LinkedHashMap<String,String>();
	buttons.put("�ύ��Ʊ","submitFin(document.all.tableform)");
	buttons.put("�� ��","closeWindow(\"\")");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("typeno", "���");
	hidden.put("gctnm", "��������");
	hidden.put("gctid", "���̱��");
	hidden.put("ictnm", "���˿ͻ�");
	hidden.put("ictid", "�ͻ����");
	hidden.put("pdtid", "��Ʒ����");
	hidden.put("pdtnm", "��Ʒ����");
	hidden.put("band", "��ƷƷ��");
	hidden.put("tmksid", "���ƻ�������");
	hidden.put("chgdt", "�շ�����");
	hidden.put("redt", "�շ�����");
	hidden.put("pdtprc", "ԭ��");
	hidden.put("pdtnum", "����");
	hidden.put("finrate", "��Ʊ����");
	hidden.put("finprc", "��˰����");
	hidden.put("finprccount", "�ܶ�");
	hidden.put("invoicecode", "��Ʊ����");
	hidden.put("invoiceno", "��Ʊ���");
	hidden.put("sellprc", "ʵ���ۼ�");	
	hidden.put("finrate", "��Ʊ����");
	hidden.put("finprc", "��Ʊ����");

	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "notype", "��������"));
	editors.add(new Editor("text", "typeno", "�����Ż����շѺ�"));
	editors.add(new Editor("text", "gctnm", "����ͻ�"));
	editors.add(new Editor("text", "ictnm", "���˿ͻ�"));
	editors.add(new Editor("text", "pdtid", "��Ʒ���"));
	editors.add(new Editor("date","start","�շ����ڴ�"));
	editors.add(new Editor("date","end","��"));
/* 	editors.add(new Editor("text", "fintel", "��Ʊ�˵绰")); */

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "retailname", "��Ʊ̧ͷ", "true"));
    batchInput.add(new Editor("text", "retailtaxno", "��Ʊ˰��", "true"));
    batchInput.add(new Editor("text", "comaddr", "��˾��ַ", "true"));
    batchInput.add(new Editor("text", "depositbank", "������", "true"));
    batchInput.add(new Editor("text", "depositid", "�����˺�", "true"));
    batchInput.add(new Editor("text", "specialtel", "ר�õ绰", "true"));
	

	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("batchInput", batchInput);
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

function submitFin(obj) {
	var t = delObj("chk");//У����û�п��ύ��
	if (!t) {
		return t;
	}
	t = preCheckForBatch();//�Ա�¼��У��
	if (!t) {
		return t;
	}
	
	if (confirm("ȷ���ύ��Ʊ")) {
		window.location.href = "/"
			+ lemis.WEB_APP_NAME
			+ "/finance/FinanceAction.do?method=saveFinance&notype="
			+ "<%=notype%>" + "&fintype="
			+"speretail"+"&"
			+ getAlldata(document.all.tableform);
	}
};


</script>

<lemis:base />
<lemis:body>
	<lemis:title title="��ͨ�Ͷ�����Ʒ��ר�÷�Ʊ�����ۣ�" />
	<lemis:query action="/FinanceAction.do?method=queryToDrawSpeciaRetaillBill" editorMeta="editor"
		topic="��ѯ����" />
	<lemis:table action="FinanceAction.do" headerMeta="header" topic="������Ϣ" batchInputMeta="batchInput" 
		hiddenMeta="hidden" mode="checkbox" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>

