<!--lemis/recommendation/employassembly/vieworgancom.jsp-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<% 

  List buttons=new ArrayList();//定义按钮属性
  buttons.add(new Button("Btn_back","返 回","rec999997","go2Page(\"recommendation\",\"1\")"));
  buttons.add(new Button("Btn_close","关 闭","rec999999","closeWindow(\"Rec08Form\")"));
  pageContext.setAttribute("buttons",buttons);
  %>
<html>
<lemis:base/>
<lemis:body>
<script src="/lemis/js/lemisTree.js"></script>

  <%//标题部分%>
  <lemis:title title="查看机构投诉信息"/>
  <%//录入部分%>
  <html:form action="/Rec0804Action.do" method="POST">
  <lemis:tabletitle title="机构基本信息"/>
  <table class="tableInput">
  <lemis:editorlayout/>
   <html:hidden property="acb240"/>
   <html:hidden property="acb241"/>
   <tr>
      <lemis:texteditor property="acb241" label="机构名称" maxlength="30" disable="true" required="false"/>
      <lemis:texteditor property="ssjqnm" required="false" label="管理机构" disable="true" style="CURSOR:hand" onclick="setSSJDTree(this,document.all.ssjqnm,document.all.cce001)" />
	  <html:hidden property="cce001" />
	 <lemis:codelisteditor type="aab019" label="企业性质" required="false" isSelect="false" redisplay="true"/>
   </tr>
   <tr>
   <lemis:texteditor property="aae006" label="营业地址" maxlength="30" disable="true" colspan="3" required="false"/>
   <lemis:formateditor mask="money" property="acb242" label="面积" disable="true" required="false" format="false"/>
   </tr>
   <tr>
    <lemis:texteditor property="acb243" label="核准证编号" maxlength="30" disable="true"  required="false"/>
   <lemis:texteditor property="aae005" label="联系电话" maxlength="30" disable="true"  required="false"/>
   <lemis:formateditor mask="money" property="aab049" label="注册资金" disable="true" required="false" format="false" />
   </tr>
    <tr>
     <lemis:formateditor property="acb247" label='工作人员数量' disable="true" mask="nnnn" required="false"/>
     <lemis:formateditor property="acb248" label='有职介资格人数' disable="true" mask="nnnn" required="false"/>
     <lemis:texteditor property="acb243" label="核准证编号" maxlength="30" disable="true"  required="false"/>
    </tr>
     <tr>
      <lemis:texteditor property="acb24a" label="市、县劳人厅审批意见" maxlength="100"  disable="true" colspan="5"/> 
    </tr>  
    </table>
     <lemis:tabletitle title="机构投诉基本信息"/>
  <table class="tableInput">
  <lemis:editorlayout/>
   <tr>
	 <lemis:codelisteditor type="acb261" label="类型" required="false" isSelect="false" redisplay="true"/>
	 <lemis:formateditor property="acb262" label='分数' disable="true" mask="nnnn" required="false"/>
   </tr>
   <tr>
      <lemis:texteditor property="acb263" label="原因" maxlength="100"  disable="true" colspan="5"/> 
    </tr>
    <tr>
      <lemis:texteditor property="acb264" label="处理结果" maxlength="100"  disable="true" colspan="5"/> 
    </tr>
      <tr>
      <lemis:texteditor property="aae013" label="备注" maxlength="100"  disable="true" colspan="5"/> 
    </tr>
	<tr>
		<td>经办人</td>
		<td><lemis:operator /></td>
		<td>经办机构</td>
		<td><lemis:operorg /></td>
		<td>经办日期</td>
		<td><lemis:operdate /></td> 			
   </tr>
     </html:form>
  </table>
  <lemis:buttons buttonMeta="buttons"/>
  <lemis:errors/>
  <lemis:bottom/></lemis:body>
  </html>
   
 

