<!-- lemis/recommendation/query/statzp.jsp -->
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>

<lemis:base />
<script src="<html:rewrite forward="lemistree"/>"></script>
<%
    String tmp001 = (String) request.getSession().getAttribute("tmp001");
			//tmp001 = tmp001.substring(5);
			if (tmp001 != null && !"".equals(tmp001)) {
				tmp001 = tmp001.toLowerCase();
			}
			System.out.println("tmp001: " + tmp001);

			String menuId = (String) request.getParameter("menuId");
		//	String type = (String) request.getSession().getAttribute("type");


			//����ͳ����� ����Դ
			TreeMap data = new TreeMap();
			//if("qz".equals(type))
			//{
				data.put("CB21.ACA111","����");				
				data.put("ZPDWS","��Ƹ��λ��");	
				data.put("ZPRYS","��Ƹ������");	
				data.put("ZPGZS","��Ƹ������");	
				
			//}else
			//{
			//	data.put("AB01.AAB019","��λ����");
			//	data.put("AB01.AAB020","��������");
			//	data.put("AB01.AAB048","��Ӫ��ʽ");				
			//	data.put("CB21.BAC299","��Ա���");
			//	data.put("CB21.AAC048","�ù���ʽ");
			//	data.put("CB21.ACA111","����");
			//	data.put("CB21.AAC004","�Ա�");
			//	data.put("CB21.AAC009","��������");	
			//	data.put("CB21.AAC014","רҵ����ְ��");
			//	data.put("CB21.AAC015","����ְҵ�ʸ�ȼ�");
			//	data.put("CB21.AAC017","����״��");
			//	data.put("CB21.AAC024","������ò");
			//}
					
			
			
			//data.put("CC03.BAC298","��Ա���"); ���ټ�
			pageContext.setAttribute("data",data);
						
			
			List header = new ArrayList();
			header.add(new Formatter(""+tmp001+"", "���"));
			header.add(new Formatter("tmp002", "����"));
			pageContext.setAttribute("header", header);

			Map hidden = new LinkedHashMap();
			pageContext.setAttribute("hidden", hidden);

			//		���尴ť
			List buttons = new ArrayList();//���尴ť����
			//if("qz".equals(type))
			//{
			buttons.add(new Button("stat", "��ѯ", "rec030801","stat(document.forms[0])"));
			buttons.add(new Button("resetForm", "�� ��", "rec030802","resetForm(document.forms[0])"));

			pageContext.setAttribute("button", buttons);

			session.setAttribute("tableheader", "��Ƹ��Ϣͳ�Ʊ�");//��ͷ
			%>
<html>
<lemis:body>
	<!--��Ϣ�б���ֻ����-->
	<lemis:errors />
	<lemis:title title="��Ƹ��Ϣͳ�Ʋ�ѯ" />
	<table border="1" cellspacing="0" cellpadding="2" style="">
		<tr>
			<table border="0" width="100%">
				<tr>
					<TD style="vertical-align:top"><html:form
						action="/Rec0305Action.do?method=zpStat&" method="post">
						<lemis:tabletitle title="��Ƹ��Ϣͳ��ͳ��" />
						<table class="TableInput">
							<lemis:editorlayout />
							<tr>
								<lemis:codelisteditor type="acb201" label="��Ƹ��ʽ" required="true" isSelect="true" redisplay="true"/>								
								<lemis:codelisteditor type="stat01" label="���" required="true" isSelect="true" redisplay="true" dataMeta="data" colspan="3"/>
							</tr>
							<tr>
								<lemis:formateditor property="date01" mask="date" disable="false" required="false" label="ʱ���" />
								<lemis:formateditor property="date02" mask="date" disable="false" required="false" label="��" />
								<lemis:texteditor property="acb231" label="��Ƹ����" required="false" disable="false" maxlength="20" />

							</tr>
							<tr>
							   <lemis:texteditor property="ssjqnm" required="false" label="����"
									disable="false" style="CURSOR:hand"
									onclick="setSSJDTree(this,document.all.ssjqnm,document.all.cce001)" />
								<html:hidden property="cce001" />
								<lemis:codelisteditor type="bsc006" label="�������" required="false" isSelect="true" redisplay="true" />

							</tr>
					</html:form>
			</table>

			<lemis:buttons buttonMeta="button" /></TD>
		</tr>
		<tr>
			<lemis:table action="Rec0305Action.do" headerMeta="header"
				hiddenMeta="hidden" topic="��Ƹ��Ϣͳ����Ϣ" mode="radio" />
		</tr>
	</table>
	</table>

	<lemis:bottom />
</lemis:body>

<SCRIPT language="javascript">
//ͳ�Ʋ�ѯ
  function stat(obj){
     var t = checkValue(obj);
     if(!t){
       return t;
     }
     var cce001=obj.cce001.value;
     var bsc006=obj.bsc006.value;
     //alert(cce001);
     //alert(bsc006);
     if(cce001=="" && bsc006=="")
     {
       alert("�������������������ѡ��һ��");
       return null;
     }
    obj.action= '<html:rewrite page="/Rec0305Action.do?method=zpStat"/>';             
    obj.submit();    
  }
</SCRIPT>
</html>
