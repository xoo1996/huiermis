<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.radf.plat.taglib.Editor"%>
<%@ page import="org.radf.plat.taglib.Button"%>
<%@ page import="com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/plat.tld" prefix="lemis"%>
<%@ page import="org.radf.plat.taglib.TagConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link rel="stylesheet" type="text/css" href="/huiermis/css/jquery.autocomplete.css" />
<link href='/huimis/css/style.css' rel='stylesheet' type='text/css'>
<script src="/huiermis/js/jquery-1.3.2.min.js"></script>
<script src="/huimis/js/lemisTree.js"></script>
<script src='/huiermis/js/jquery.autocomplete.js'></script>

<%  
	String totalYSK = (String)request.getSession().getAttribute("totalYSK");

    Map<String, String> buttons = new LinkedHashMap<String, String>();
    //buttons.put("结算","change_ivtpqnt()");
    //	buttons.put("录入备注","save(document.all.tableform)");
    //buttons.put("惠耳打印","huier_print() ");
    //buttons.put("杰闻打印","jiewen_print()");
   
    
    //buttons.put("打印月报表","");
    buttons.put("批量保存","batchSubmit(document.all.tableform)");
    //buttons.put("返 回","history.back()");
    //buttons.put("返 回","back(document.all.tableform)");
	buttons.put("关 闭","closeWindow(\"\")");
	
	List<Formatter> header = new ArrayList<Formatter>();
	header.add(new Formatter("gctnm", "团体客户"));
	header.add(new Formatter("ivtcltnm", "个人客户"));
	header.add(new Formatter("ivttype", "帐单类型"));
	header.add(new Formatter("pdtnm", "商品名称"));
	header.add(new Formatter("ivtfee", "费用"));
	header.add(new Formatter("ivtlmqnt", "结存数"));
	header.add(new Formatter("ivtlsqnt", "补入数"));
	header.add(new Formatter("temp01", "小计"));
	header.add(new Formatter("ivtpqnt", "回款台数"));
	header.add(new Formatter("discount", "扣率"));
	header.add(new Formatter("ivtpamnt", "回款金额"));
	
/* 	
	header.add(new Formatter("ivtfree","赠送"));
	header.add(new Formatter("ivttry","试机")); */
	
	header.add(new Formatter("temp02", "月结存", TagConstants.DF_CENTER));
	header.add(new Formatter("ivtnote", "备注"));//可以隐藏则为hidden

	List<Editor> batchInput = new ArrayList<Editor>();
	batchInput.add(new Editor("text", "ivtfee", "费用"));
	batchInput.add(new Editor("text", "ivtpqnt", "本月回款数"));
	batchInput.add(new Editor("text", "discount", "折扣率"));
	batchInput.add(new Editor("text", "ivtpamnt", "回款金额"));
	batchInput.add(new Editor("text", "ivtnote", "备注"));
	batchInput.add(new Editor("text", "ivtcltnm", "个人客户"));
	
/* 	batchInput.add(new Editor("text", "ivtfree", "赠送"));
	batchInput.add(new Editor("text", "ivttry", "试机")); */
	
	batchInput.add(new Editor("text", "temp02", "月结存"));
	batchInput.add(new Editor("text", "temp01", "小计"));
	
	Map<String, String> hidden = new LinkedHashMap<String, String>();
	hidden.put("ivtid", "id");
	hidden.put("ivtpdtid", "产品ID");
	hidden.put("ivtgcltid", "团体客户ID");
	hidden.put("ivtyear","年");
	hidden.put("ivtmonth", "月");
	
	
	List<Editor> editors = new ArrayList<Editor>();
	editors.add(new Editor("text", "ivtyear", "年"));
	editors.add(new Editor("text", "ivtmonth", "月"));
	editors.add(new Editor("text", "ivtgcltid", "团体代码",true));
	editors.add(new Editor("text", "ivttype", "账单类型"));
	editors.add(new Editor("text", "pdtnm", "商品名称"));

	pageContext.setAttribute("editor", editors);
	pageContext.setAttribute("header", header);
	pageContext.setAttribute("button", buttons);
	pageContext.setAttribute("hidden", hidden);
	pageContext.setAttribute("batchInput", batchInput);
%>
<script language="javascript">	
	$(document).ready(function(e) {
		var totalYSK = parseFloat(<%=totalYSK%>);
		var diffNum = parseFloat(0);//回款金额
		$("input[name=ivtfee]").attr('disabled','disabled');
		//$("input[value=批量保存]").after("&nbsp;&nbsp;&nbsp;&nbsp;<input id='totalYSK' disabled='disabled' value="+ totalYSK +"></input>");
		/* $("input[name=ivtpamnt]").bind("focus", function(e) {
			diffNum = $(e.target).val();
		});*/
		//2012-3-29点击回款金额后触发事件
		 $("input[name=ivtpamnt]").bind("click", function(e) {
			diffNum = $(e.target).val();
			var id = $(e.target).attr("id");//获取id此处的id为ivtpamnt_rowx
			var suffix = id.substr(8);//id后的位置为8的后缀，id从0开始计算，此处suffix为_rowx
			var discount = parseFloat(0);
			if ($("#discount" + suffix).val() != "") {
				discount = $("#discount" + suffix).val();
			}
			
			var value = Math.round($("#ivtfee" + suffix).val() * $("#ivtpqnt" + suffix).val()* discount);
			if ( value != 0) {
				$("#ivtpamnt" + suffix).val(value);
			}
		});  
		

		$("input[name=ivtpamnt]").bind("blur", function(e) {
			totalYSK =  totalYSK - diffNum + parseFloat($(e.target).val());
			$("#totalYSK").val(totalYSK);
		}); 
		
		//原来代码
		$("input[name=ivtpqnt]").bind("blur", function(e) {
			var id = $(e.target).attr("id");
			var suffix = id.substr(7);

			var discount = parseFloat(0);//扣率
			if ($("#discount" + suffix).val() != "") {
				discount = $("#discount" + suffix).val();//ro
				/* alert("discount:" + discount); */
			}
			/* alert("discount1:" + discount); */
			 var value = Math.round($("#ivtfee" + suffix).val() * $("#ivtpqnt" + suffix).val()
					* discount);
			 /* alert("value2:" + value); */
			var leftNum = $("#temp01" + suffix).val() - $("#ivtpqnt" + suffix).val();
			/* alert("ivtfree:"+$("#ivtfree" + suffix).val());
			alert("leftNum:" + leftNum) */;
			diffNum = $("#ivtpamnt" + suffix).val();
			/* alert("diffNum:" + diffNum); */
			if (value != 0) {
				$("#ivtpamnt" + suffix).val(value);
			}
			$("#temp02" + suffix).val(leftNum);
			totalYSK = totalYSK - diffNum + value;
			$("#totalYSK").val(totalYSK);
		});   
       var shops = "<%=session.getServletContext().getAttribute("shopList")%>".replace("{","").replace("}","").split(", ");
		
		$("input[name=ivtgcltid]").autocomplete(shops,{
			max : 10,
			matchContains : true
		});
		$("input[name=ivtgcltid]").result(function(event, data, formatted) {
			if (data){
				var gnm = data[0].substring(data[0].indexOf("=")+1);
				var gid = data[0].substring(0,data[0].indexOf("="));
				$("input[name=ivtgcltid]").val(gid);
				$(this).parent().next().find("input").val(gid);
			}
		});
		$("input[name=ivtpamnt]").bind("propertychange",function(e) {
			$("input[name=ivtpamnt]").each(function(index) {
				totalYSK += $(this).val();
			});
		});
	});
	function batchSubmit(obj) {
		var t = delObj("chk");//校验有没有可提交项
		if (!t) {
			return t;
		}
		t = preCheckForBatch();//对必录项校验
		if (!t) {
			return t;
		}
		var li=$(".buttonGray");
		for(var i=0;i<li.length;i++){
			if(li[i].value=="批量保存") {
				li[i].disabled=true;
			}
		}
		obj.action = '<html:rewrite page="/BusinessAction.do?method=batchSubmitOnly&mark=jmself&year="/>' + $("input");
		obj.submit();
	}
</script>

<script language="javascript">

	function back(obj){
		obj.action='<html:rewrite page="/BusinessAction.do?method=JMyuejiequery&mark=back"/>';
		obj.submit();
	};
	
	function huier_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/business/BusinessAction.do?method=report&type=huier&"
				+ getAlldata(document.all.tableform);
	};
	function jiewen_print() {
		window.location.href = "/" + lemis.WEB_APP_NAME
				+ "/business/BusinessAction.do?method=report&type=jiewen&"
				+ getAlldata(document.all.tableform);
	};
	function save(obj){
		obj.action = '<html:rewrite page="/SaleAction.do?method=enter&menuId=savent&"/>' + 'mgctid='+$("input[name=ivtgcltid]").val()
        +'&myear='+$("input[name=ivtyear]").val()+'&mmonth='+$("input[name=ivtmonth]").val();
		obj.submit();
	}
	function change_ivtpqnt() {
		   var PagenumInput=parseInt($("#PagenumInput").val());
		for( var i = 1;i<=PagenumInput; i++){
			var suffix="_row"+i;
			var value1=$("#temp01"+suffix).val();
			$("#ivtpqnt"+suffix).val(value1);//回款台数
			var discount = parseFloat(0);//扣率
			if ($("#discount" + suffix).val() != "") {
				discount = $("#discount" + suffix).val();
				/* alert("discount:" + discount); */
			}
			/* alert("discount1:" + discount); */
			 var value = Math.round($("#ivtfee" + suffix).val() * $("#ivtpqnt" + suffix).val()
					* discount);
			 /* alert("value2:" + value); */
			var leftNum = $("#temp01" + suffix).val() - $("#ivtpqnt" + suffix).val();
			/* alert("ivtfree:"+$("#ivtfree" + suffix).val());
			alert("leftNum:" + leftNum) */;
			diffNum = $("#ivtpamnt" + suffix).val();
			/* alert("diffNum:" + diffNum); */
			if (value != 0) {
				$("#ivtpamnt" + suffix).val(value);
			}
			$("#temp02" + suffix).val(leftNum);
			
			}
	};
</script>

<lemis:base />
<lemis:body>
	<lemis:title title="月报表查询" />
	<lemis:query action="/BusinessAction.do?method=JMyuejiebackup&mark=edit"
		editorMeta="editor" topic="查询条件" />
	<lemis:table action="/BusinessAction.do" headerMeta="header"
		batchInputMeta="batchInput" topic="月报表明细" hiddenMeta="hidden"
		mode="checkbox" batchInputType="update" />
	<lemis:buttons buttonMeta="button" />
	<lemis:errors />
	<lemis:bottom />
</lemis:body>
</html>
