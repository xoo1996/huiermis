<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<lemis:base/>
<lemis:body><lemis:errors/>
  <% 
//  response.sendRedirect((String)request.getAttribute("rurl"));
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
  %>
<script language="javascript">
function page_init() {
  go2Page('<%=(String)request.getAttribute("go2pagemod")%>','<%=(String)request.getAttribute("go2pagenum")%>');
}
</script>
</lemis:body>
</html>

