<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<lemis:base/>
<lemis:body><lemis:errors/>
  <%
//  response.sendRedirect((String)request.getAttribute("rurl"));
  %>
<script language="javascript">
function page_init() { 
  window.history.back(1);
}
</script>
</lemis:body>
</html>

