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
	//header.add(new Formatter("fctctid", "�û����"));
	//header.add(new Formatter("ictnm", "�û�����"));
	header.add(new Formatter("id", "�ϴ����"));
	header.add(new Formatter("ictnm", "�û�����"));
	header.add(new Formatter("gctnm", "��������"));
	header.add(new Formatter("filename", "�ļ�����"));
	header.add(new Formatter("uploadtime", "�ϴ�ʱ��"));

	Map<String, String> buttons = new LinkedHashMap<String, String>();
	buttons.put("�ϴ�", "upload(document.forms[0])");
	buttons.put("����", "down(document.all.tableform)");
	/* buttons.put("�鿴����","querySctlDetail(document.all.tableform)");
	buttons.put("�޸�","updateSCTL(document.all.tableform)");
	buttons.put("ɾ��","deleteSCTL(document.all.tableform)"); */
	buttons.put("ɾ��", "deleteDebug(document.all.tableform)");
	buttons.put("�� ��", "closeWindow(\"\")");
	buttons.put("����", "history.back()");

	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("id", "�����������");
	/* 	hidden.put("usercode", "��������"); */
	hidden.put("filename", "�û����");
	hidden.put("ictnm", "�û�����");
	hidden.put("gctnm", "��������");
	hidden.put("ictid", "�û�����");
	hidden.put("ictgctid", "��������");

	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	/* 	session.setAttribute("tableheader", "���˿ͻ�������Ϣ��");//��ͷ */
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
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=downDebug"/>';
		obj.submit();
	};
	function deleteDebug(obj) {
		var t = delObj("chk");//У����û�п��ύ��
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//�Ա�¼��У��
		if (!t) {
			return t;
		}
		obj.action = '<html:rewrite page="/SingleClientAction.do?method=deleteDebug"/>';
		obj.submit();
	};
	function showcl() {
		alert("���");
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
	<lemis:title title="�û����Լ�¼�ϴ�" />
	<lemis:tabletitle title="�û�������Ϣ" />
	<table class="tableInput">
		<lemis:editorlayout />
		<html:form action="/SingleClientAction.do" method="POST">
			<tr>
				<lemis:texteditor property="ictid" label="�û����" required="false"
					value="<%=fctlictid%>" disable="true" />
				<lemis:texteditor property="ictnm" label="�û�����" required="false"
					value="<%=fctlictnm%>" disable="true" />
			</tr>
			<tr>
				<lemis:texteditor property="ictgctid" label="������" required="false"
					value="<%=fctlictgctid%>" disable="true" />
				<lemis:texteditor property="gctnm" label="��������" required="false"
					value="<%=fctlgctnm%>" disable="true" />

			</tr>
		</html:form>
	</table>
	<lemis:tabletitle title="�û�������Ϣ" />
	<TBODY>
		<TR>
			<TD>
				<TABLE id=resultset cellSpacing=1 width="100px" align=center
					border=0>
					<TBODY>
						<tr>
						<TR class=listColorA text-align=center>
							<TD style="text-align: center"  style="WORD-BREAK:break-all">
								<a id="href" class=n href="/huiermis/client/SingleClientAction.do?method=downUser&ictid=${reslist[0].ictid}&ictnm=${reslist[0].ictnm}" onclick="showcl()">�����û��Ļ�����Ϣ </a>
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
													<TD>��������</TD>
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
								<TD class=tableHead noWrap>���</TD>
								<TD class=tableHead noWrap>������</TD>
								<td class='tableHead' noWrap>�ļ�����</td>
								<td class='tableHead' noWrap>�ϴ�ʱ��</td>
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


