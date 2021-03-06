<!--lemis/recommendation/employassembly/addorgan.jsp-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.radf.plat.taglib.Button" %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<% 

  List buttons=new ArrayList();//定义按钮属性
  buttons.add(new Button("Btn_save","保 存","rec080102","addsave(document.forms[0])"));
  buttons.add(new Button("Btn_back","返 回","rec999997","go2Page(\"recommendation\",\"1\")"));
  buttons.add(new Button("Btn_close","关 闭","rec999999","closeWindow(\"Rec08Form\")"));
  pageContext.setAttribute("buttons",buttons);
  %>
<html>
<lemis:base/>
<lemis:body>
<script src="/lemis/js/lemisTree.js"></script>

  <%//标题部分%>
  <lemis:title title="增加机构信息"/>
  <%//录入部分%>
  <html:form action="/Rec08Action.do?method=addorgan" method="POST">
  <lemis:tabletitle title="机构基本信息"/>
  <table class="tableInput">
  <lemis:editorlayout/>
   <tr>
      <lemis:texteditor property="acb241" label="机构名称" maxlength="30" disable="false" required="true"/>
      <lemis:texteditor property="ssjqnm" required="true" label="管理机构" disable="false" style="CURSOR:hand" onclick="setSSJDTree(this,document.all.ssjqnm,document.all.cce001)" />
	  <html:hidden property="cce001" />
	 <lemis:codelisteditor type="aab019" label="企业性质" required="true" isSelect="true" redisplay="true"/>
   </tr>
   <tr>
   <lemis:texteditor property="aae006" label="营业地址" maxlength="30" disable="false" colspan="3" required="true"/>
   <lemis:formateditor mask="money" property="acb242" label="面积" disable="false" required="false" format="false"/>
   </tr>
  
   <tr>
    <lemis:texteditor property="acb243" label="核准证编号" maxlength="10" disable="false"  required="false"/>
    <lemis:texteditor property="aae005" label="联系电话" disable="false" maxlength="20" required="false"/>  
   <lemis:texteditor property="acb244" label="摊位号" maxlength="30" disable="false"  required="false"/>  
   </tr>
   <tr>
    <lemis:texteditor property="acb245" label="微机型号" maxlength="30" disable="false"  required="false"/>
    <lemis:texteditor property="aac003" label="姓名" maxlength="30" disable="false"  required="true"/>
    <lemis:formateditor mask="card" property="aac002" label="公民身份号码" required="true" disable="false"/>
   </tr>
   <tr> 
     <lemis:codelisteditor type="aac004" label="性别" required="false" isSelect="true" redisplay="false"/>
     <lemis:formateditor mask="date" property="aac006" label="出生年月" disable="false" required="false"/>
     <lemis:codelisteditor type="aac011" label="文化程度" required="false" isSelect="true" redisplay="false"/>
   </tr>
   <tr>
      <lemis:texteditor property="acb246" label="个人简历" maxlength="100"  disable="false" colspan="5"/> 
    </tr>  
    
    <tr>
    <lemis:formateditor mask="money" property="aab049" label="注册资金" disable="false" required="false" format="false" />
     <lemis:formateditor property="acb247" label='工作人员数量' disable="false" mask="nnnn" required="false"/>
     <lemis:formateditor property="acb248" label='有职介资格人数' disable="false" mask="nnnn" required="false"/>
    </tr>
     <tr>
      <lemis:texteditor property="acb249" label="业务范围" maxlength="100"  disable="false" colspan="5"/> 
    </tr>  
     <tr>
      <lemis:texteditor property="acb24a" label="市、县劳人厅审批意见" maxlength="100"  disable="false" colspan="5"/> 
    </tr>  
    <tr>
      <lemis:texteditor property="aae013" label="备注" maxlength="100"  disable="false" colspan="5"/>
     
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
   <script language="javascript">
     function addsave(obj)
     {
      var t  = checkValue(obj);
          if(!t){
                  return t;
          }  
	   obj.action = '<html:rewrite page="/Rec08Action.do?method=saveorgan"/>';
	   obj.submit();
     }

   </script>
 

