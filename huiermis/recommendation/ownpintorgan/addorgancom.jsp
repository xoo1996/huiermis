<!--lemis/recommendation/employassembly/addorgancom.jsp-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<% 

  List buttons=new ArrayList();//���尴ť����
  buttons.add(new Button("Btn_save","�� ��","rec080402","addsave(document.forms[0])"));
  buttons.add(new Button("Btn_back","�� ��","rec999997","go2Page(\"recommendation\",\"1\")"));
  buttons.add(new Button("Btn_close","�� ��","rec999999","closeWindow(\"Rec08Form\")"));
  pageContext.setAttribute("buttons",buttons);
  %>
<html>
<lemis:base/>
<lemis:body>
<script src="/lemis/js/lemisTree.js"></script>

  <%//���ⲿ��%>
  <lemis:title title="���ӻ���Ͷ����Ϣ"/>
  <%//¼�벿��%>
  <html:form action="/Rec0804Action.do" method="POST">
  <lemis:tabletitle title="����������Ϣ"/>
  <table class="tableInput">
  <lemis:editorlayout/>
   <html:hidden property="acb240"/>
   <html:hidden property="acb241"/>
   <tr>
      <lemis:texteditor property="acb241" label="��������" maxlength="30" disable="true" required="false"/>
      <lemis:texteditor property="ssjqnm" required="false" label="��������" disable="true" style="CURSOR:hand" onclick="setSSJDTree(this,document.all.ssjqnm,document.all.cce001)" />
	  <html:hidden property="cce001" />
	 <lemis:codelisteditor type="aab019" label="��ҵ����" required="false" isSelect="false" redisplay="true"/>
   </tr>
   <tr>
   <lemis:texteditor property="aae006" label="Ӫҵ��ַ" maxlength="30" disable="true" colspan="3" required="false"/>
   <lemis:formateditor mask="money" property="acb242" label="���" disable="true" required="false" format="false"/>
   </tr>
   <tr>
    <lemis:texteditor property="acb243" label="��׼֤���" maxlength="30" disable="true"  required="false"/>
   <lemis:texteditor property="aae005" label="��ϵ�绰" maxlength="30" disable="true"  required="false"/>
   <lemis:formateditor mask="money" property="aab049" label="ע���ʽ�" disable="true" required="false" format="false" />
   </tr>
    <tr>
     <lemis:formateditor property="acb247" label='������Ա����' disable="true" mask="nnnn" required="false"/>
     <lemis:formateditor property="acb248" label='��ְ���ʸ�����' disable="true" mask="nnnn" required="false"/>
     <lemis:texteditor property="acb243" label="��׼֤���" maxlength="30" disable="true"  required="false"/>
    </tr>
     <tr>
      <lemis:texteditor property="acb24a" label="�С����������������" maxlength="100"  disable="true" colspan="5"/> 
    </tr>  
    </table>
     <lemis:tabletitle title="����Ͷ�߻�����Ϣ"/>
  <table class="tableInput">
  <lemis:editorlayout/>
   <tr>
	 <lemis:codelisteditor type="acb261" label="����" required="true" isSelect="true" redisplay="true"/>
	 <lemis:formateditor property="acb262" label='����' disable="false" mask="nnnn" required="true"/>
   </tr>
   <tr>
      <lemis:texteditor property="acb263" label="ԭ��" maxlength="100"  disable="false" colspan="5"/> 
    </tr>
    <tr>
      <lemis:texteditor property="acb264" label="�������" maxlength="100"  disable="false" colspan="5"/> 
    </tr>
      <tr>
      <lemis:texteditor property="aae013" label="��ע" maxlength="100"  disable="false" colspan="5"/> 
    </tr>
	<tr>
		<td>������</td>
		<td><lemis:operator /></td>
		<td>�������</td>
		<td><lemis:operorg /></td>
		<td>��������</td>
		<td><lemis:operdate /></td> 			
   </tr>
     </html:form>
  </table>
  <lemis:buttons buttonMeta="buttons"/>
  <lemis:errors/>
  <lemis:bottom/></lemis:body>
  </html>
   <script language="javascript">
     function addsave(obj)
     {
      var t  = checkValue(obj);
          if(!t){
                  return t;
          }  
	   obj.action = '<html:rewrite page="/Rec0804Action.do?method=saveorgancom"/>';
	   obj.submit();
     }

   </script>
 
