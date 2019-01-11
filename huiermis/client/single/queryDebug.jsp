<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %> 
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String fctlictid = (String) request.getSession().getAttribute(
			"fctlictid");
	String fctlictnm = (String) request.getSession().getAttribute(
			"fctlictnm");
	String fctlictgctid = (String) request.getSession().getAttribute(
			"fctlictgctid");
	String fctlgctnm = (String) request.getSession().getAttribute(
			"fctlgctnm");
	List<Formatter> header = new ArrayList<Formatter>();
	//header.add(new Formatter("fctctid", "用户编号"));
	//header.add(new Formatter("ictnm", "用户姓名"));
	header.add(new Formatter("id", "上传编号"));
	header.add(new Formatter("ictnm", "用户姓名"));
	header.add(new Formatter("gctnm", "团体姓名"));
	header.add(new Formatter("filename", "文件内容"));
	header.add(new Formatter("uploadtime", "上传时间"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("上传", "upload(document.forms[0])");
	buttons.put("下载", "down(document.all.tableform)");
	/* buttons.put("查看详情","querySctlDetail(document.all.tableform)");
	buttons.put("修改","updateSCTL(document.all.tableform)");
	buttons.put("删除","deleteSCTL(document.all.tableform)"); */
	buttons.put("删除", "deleteDebug(document.all.tableform)");
	buttons.put("关 闭", "closeWindow(\"\")");
	buttons.put("返回", "history.back()");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("id", "复诊听力编号");
	/* 	hidden.put("usercode", "复诊日期"); */
	hidden.put("filename", "用户编号");
	hidden.put("ictnm", "用户姓名");
	hidden.put("gctnm", "所属团体");
	hidden.put("ictid", "用户姓名");
	hidden.put("ictgctid", "所属团体");

	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	/* 	session.setAttribute("tableheader", "个人客户复诊信息表");//表头 */
%>
<html>
<link rel="stylesheet" type="text/css"
	href="/huiermis/css/jquery.autocomplete.css" />
	<style>a{TEXT-DECORATION:none}</style> 
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huiermis/js/CityJson.js"></script>
<script src="/huiermis/js/DistrictJson.js"></script>
<script src="/huiermis/js/ProJson.js"></script>
<script src="/huiermis/js/jquery.bgiframe.min.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>
<script src="/huiermis/js/proxyRequest.js"></script>
<style>.n{ TEXT-DECORATION:none }</style> 
<%@ page import="org.radf.login.dto.LoginDTO"%>
<%@ page import="org.radf.manage.entity.Sc08"%>
<script language="javascript">
	function upload(obj) {
		//var t = editObj("chk");
		//if(!t){
		//	return t;
		//}
		/*  var ictid=$("input[name=ictid]").val(); 
		alert(ictid); */
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=enterUploadDebug&"/>'
				+ getAlldata(obj);
		obj.submit();
	};
	function down(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=downDebug"/>';
		obj.submit();
	};
	function deleteDebug(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=deleteDebug"/>';
		obj.submit();
	};
	function showcl() {
		alert("你好");
		$(".zhs").show();
	};
	/* function querySctlDetail(obj) {
		var t = editObj("chk");
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=showdetailSCTL&type=show&"/>' + getAlldata(obj);
		obj.submit();
	};
	function updateSCTL(obj) {
		var t = editObj("chk");
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=showdetailSCTL&type=update&"/>' + getAlldat
	a(obj);
		obj.submit();
	}; */
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="用户调试记录上传" />
	<lemis:tabletitle title="用户基本信息" />
	<table class="tableInput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do" method="POST">
			<tr>
				<lemis:texteditor property="ictid" label="用户编号" required="false"
					value="<%=fctlictid%>" disable="true" />
				<lemis:texteditor property="ictnm" label="用户姓名" required="false"
					value="<%=fctlictnm%>" disable="true" />
			</tr>
			<tr>
				<lemis:texteditor property="ictgctid" label="团体编号" required="false"
					value="<%=fctlictgctid%>" disable="true" />
				<lemis:texteditor property="gctnm" label="所属团体" required="false"
					value="<%=fctlgctnm%>" disable="true" />

			</tr>
		</html:form>
	</table>
	<lemis:tabletitle title="用户基本信息" />
	<TBODY>
		<TR>
			<TD>
				<TABLE id=resultset cellSpacing=1 width="100px" align=center
					border=0>
					<TBODY>
						<tr>
						<TR class=listColorA text-align=center>
							<TD style="text-align: center"  style="WORD-BREAK:break-all">
								<a id="href" class=n href="/huiermis/client/SingleClientAction.do?method=downUser&ictid=${reslist[0].ictid}&ictnm=${reslist[0].ictnm}" onclick="showcl()">下载用户的基本信息 </a>
							</TD>
						</tr>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>


	<TABLE cellSpacing=0 cellPadding=0 width="95%" align=center border=0>
		<TBODY>
			<TR>
				<TD>
					<TABLE height=21 cellSpacing=0 cellPadding=0 width="100%" border=0>
						<TBODY>
							<TR>
								<FORM id=orderForm method=post name=orderForm action="">
									<TD style="WORD-BREAK: keep-all" vAlign=bottom align=left>
										<TABLE class=tableTitle>
											<TBODY>
												<TR>
													<TD>调试数据</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD width=10>&nbsp;</TD>
									<TD>&nbsp;</TD>
									<TD width="40%">&nbsp;</TD>
								</FORM>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>


	<TABLE class=tableList cellSpacing=0 cellPadding=0 width="95%"
		align=center border=0>
		<TBODY>
			<TR>
				<TD>
					<TABLE id=resultset cellSpacing=1 width="100%" align=center
						border=0>
						<TBODY>
							<TR align=center>
								<TD class=tableHead noWrap>编号</TD>
								<TD class=tableHead noWrap>机身编号</TD>
								<td class='tableHead' noWrap>文件名称</td>
								<td class='tableHead' noWrap>上传时间</td>
							</TR>
							<FORM id=tableform method=post name=tableform>
								<c:forEach items="${requestScope.reslist}" var="debugdata"
									varStatus="obj">
									<c:if test="${obj.count%2 == '0'}">
										<TR class=listColorA>
									</c:if>
									<c:if test="${obj.count%2 != '0'}">
										<TR class=listColorB>
									</c:if>
									<TD style="text-align: center" style="WORD-BREAK: break-all"><c:out
											value="${debugdata.id }" /></TD>
									<TD style="text-align: center" style="WORD-BREAK: break-all"><c:out
											value="${debugdata.ictid } " /></TD>

									<TD style="text-align: center" style="WORD-BREAK: break-all"><c:out
											value="${debugdata.filename}" /></TD>
									<TD style="text-align: center" style="WORD-BREAK: break-all"><c:out
											value="${debugdata.uploadtime }" /></TD>
								</c:forEach>
							</FORM>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>


