package org.radf.apps.qa.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.radf.apps.client.group.facade.GroupClientFacade;
import org.radf.apps.client.group.form.GroupClientForm;
import org.radf.apps.commons.entity.Customization;
import org.radf.apps.commons.entity.GroupClient;
import org.radf.apps.commons.entity.PrintBeforeVerify;
import org.radf.apps.commons.entity.PrintBeforeVerifyFactory;
import org.radf.apps.commons.entity.PrintFangXing;
import org.radf.apps.commons.entity.PrintFangXingFactory;
import org.radf.apps.commons.entity.QA;
import org.radf.apps.commons.entity.Repair;
import org.radf.apps.customization.facade.CustomizationFacade;
import org.radf.apps.customization.form.CustomizationForm;
import org.radf.apps.qa.facade.QAFacade;
import org.radf.apps.qa.form.QAForm;
import org.radf.apps.repair.facade.RepairFacade;
import org.radf.apps.repair.form.RepairForm;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.FindLog;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.commons.SubmitDataMap;
import org.radf.plat.log.LogHelper;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.global.GlobalNames;

/**
 * 质量管理
 */
public class QAAction extends ActionLeafSupport {
	LogHelper log = new LogHelper(this.getClass().getName());

	public QAAction() {
	}

	/**
	 * 进入质检界面
	 */
	public ActionForward test(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("test");
	}
	
	public ActionForward test1before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return mapping.findForward("test1before");
	}
	
	public ActionForward test2before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return mapping.findForward("test2before");
	}
	/**
	 * 进入质检2界面 //2012-2-25新增质量检测2
	 */
	public ActionForward test2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("test2");
	}
	
	/*
	 * 质量检测1的步骤1
	 */
	public ActionForward updateTest1before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QAForm qaForm = (QAForm) form;
		QA qa = new QA();
		String pnl = null;
		String pnm = null;
		Boolean flag=true;
		String qapnm=qaForm.getQapnm();
		ClassHelper.copyProperties(qaForm, qa);  //9.1
		try {
			
			// 设定经办信息
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // 质检日期
				if(null != qa.getQatype() && !("make".equals(qa.getQatype()))){
					qa.setQastatus("finish");
				}
				//if(null != qa.getQapid() && ("唯听".equals(qa.getPdttype()) || qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))
				if(null != qa.getQapid() && ("唯听".equals(qa.getPdttype()) ||qa.getQapid().equals("3197100")||qa.getQapid().equals("3197101")||qa.getQapid().equals("3198100")||qa.getQapid().equals("3198102")||qa.getQapid().equals("3199100")||qa.getQapid().equals("3199101")||qa.getQapid().equals("3198101")||qa.getQapid().equals("3198103")||qa.getQapid().equals("3198001")||qa.getQapid().equals("3199003")||qa.getQapid().equals("3197001")||qa.getQapid().equals("3198002")||qa.getQapid().equals("3199004")||qa.getQapid().equals("3197002")|| qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))

				{
					qa.setQastatus("finish");
				}
				
			// 获取服务接口
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
				ResponseEnvelop resEnv = facade.finish2before(requestEnvelop);
			// 处理返回结果
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "质检信息与订单信息不匹配!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "质检信息保存成功!");
				return mapping.findForward("backspace");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(request, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(request, e);
			return mapping.findForward("backspace");
		}
	}
	
	
	/*
	 * 质量检测2的步骤1
	 */
	public ActionForward updateTest2before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QAForm qaForm = (QAForm) form;
		QA qa = new QA();
		String pnl = null;
		String pnm = null;
		Boolean flag=true;
		String qapnm=qaForm.getQapnm();
		ClassHelper.copyProperties(qaForm, qa);  //9.1
		try {
			
			
			// 设定经办信息
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // 质检日期
				if(null != qa.getQatype() && !("make".equals(qa.getQatype()))){
					//qa.setQastatus("finish");
					qa.setQastatus("waitfx");
				}
				//if(null != qa.getQapid() && ("唯听".equals(qa.getPdttype()) || qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))
				if(null != qa.getQapid() && ("唯听".equals(qa.getPdttype()) || qa.getQapid().equals("3197100")||qa.getQapid().equals("3197101")||qa.getQapid().equals("3198100")||qa.getQapid().equals("3198102")||qa.getQapid().equals("3199100")||qa.getQapid().equals("3199101")||qa.getQapid().equals("3198101")||qa.getQapid().equals("3198103")||qa.getQapid().equals("3198001")||qa.getQapid().equals("3199003")||qa.getQapid().equals("3197001")||qa.getQapid().equals("3198002")||qa.getQapid().equals("3199004")||qa.getQapid().equals("3197002")||qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))

				{
					//qa.setQastatus("finish");
					qa.setQastatus("waitfx");
				}
				
			// 获取服务接口
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
				ResponseEnvelop resEnv = facade.finish2before(requestEnvelop);
			// 处理返回结果
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "质检信息与订单信息不匹配!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "质检信息保存成功!");
				return mapping.findForward("backspace");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(request, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(request, e);
			return mapping.findForward("backspace");
		}
	}
	/**
	 * 质检－更新质检记录
	 */
	public ActionForward updateTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QAForm qaForm = (QAForm) form;
		QA qa = new QA();
		String pnl = null;
		String pnm = null;
		Boolean flag=true;
		String qapnm=qaForm.getQapnm();
		ClassHelper.copyProperties(qaForm, qa);  //9.1
		try {
			
		
			//隔离维修
			if(null != qa.getQatype() && "make".equals(qa.getQatype())){
			
			File file1 = new File(request.getSession().getServletContext().getRealPath("\\qa\\check.csv"));
			FileReader reader1 =new FileReader(file1);
			BufferedReader br1 =new BufferedReader(reader1);
			String s1 = null;
			int index1;
			while((s1 = br1.readLine()) != null)
			{
				index1 = s1.indexOf(",");
				pnl = s1.substring(0, index1);   //面板编码
				pnm = s1.substring(index1 + 1);  //定制机型号
				if(qapnm.endsWith(pnm))
				{
					flag=false;
					break;
				}
			}
			reader1.close();
			br1.close();
			
			
//			if(flag)
//			{
//			
//				File file = new File(request.getSession().getServletContext().getRealPath("\\qa\\测试.csv"));
//				FileReader reader = new FileReader(file);
//				BufferedReader br = new BufferedReader(reader);
//				String s = null;
//				int index;
//				while((s = br.readLine()) != null)
//				{
//					index = s.indexOf(",");
//					pnl = s.substring(0, index);   //面板编码
//					pnm = s.substring(index + 1);  //定制机型号
//				}
//				reader.close();
//				br.close();
//			}
		}
			
			// 设定经办信息
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // 质检日期
				//qa.setQastatus("finish");
				qa.setQastatus("waitfx");
			// 获取服务接口
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
				ResponseEnvelop resEnv = facade.finish(requestEnvelop);
			// 处理返回结果
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "质检信息与订单信息不匹配!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "质检信息保存成功!");
				return mapping.findForward("backspace");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(request, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(request, e);
			return mapping.findForward("backspace");
		}
	}
	
	/**
	 * 质检2－更新质检记录
	 */
	public ActionForward updateTest2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QAForm qaForm = (QAForm) form;
		QA qa = new QA();
		try {
			// 设定经办信息
			ClassHelper.copyProperties(qaForm, qa);
			LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
			qa.setQachkopr(dto1.getBsc011());
			qa.setQachkdt(DateUtil.getDate()); // 质检日期
			
			//qa.setQastatus("finish");
			qa.setQastatus("waitfx");
			
			// 获取服务接口
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
			HashMap<String,Object> mapRequest = new HashMap<String,Object>();
			mapRequest.put("beo", qa);
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.finish(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "质检信息保存成功!");
				return mapping.findForward("backspace");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(request, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(request, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * 打印质检报告1
	 */
	public ActionForward printTestReport(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String type = req.getParameter("type");
		BigDecimal id = new BigDecimal(req.getParameter("id"));
		QAForm qaForm = (QAForm) actionForm;
		Connection conn = null;
		try {
			// 连接到数据库
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			//查询qatestft属性是否为空
			String sql = "select nvl(qatestft,0) as qatestft from tblqa where qaid='" + id + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			String qatestft = null;
			if (result.size() >= 1) {
				qatestft = ((HashMap) result.get(0)).get("1").toString();
			}
			File reportFile = null;
			// 报表编译之后生成的.jasper 文件的存放位置
			if ("jiewen".equals(type)) {
				if (qatestft.equals("0")) {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report6_1.jasper"));
				} else {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report7_1.jasper"));
				}
			} else {
				if (qatestft.equals("0")) {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report6.jasper"));
				} else {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report7.jasper"));
				}
			}

			// 传递报表中用到的参数值
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"是报表中定义过的一个参数名称,其类型为String 型
			parameters.put("qaid", id);

			

			// 在控制台显示一下报表文件的物理路径
			// System.out.println(reportFile.getPath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * 打印质检报告2
	 */
	public ActionForward printTestReport2(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String type = req.getParameter("type");
		BigDecimal id = new BigDecimal(req.getParameter("id"));
		QAForm qaForm = (QAForm) actionForm;
		Connection conn = null;
		try {
			File reportFile = null;
			// 报表编译之后生成的.jasper 文件的存放位置
			if ("jiewen".equals(type)) {
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report7_1.jasper"));
			} else {
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report7.jasper"));
			}

			// 传递报表中用到的参数值
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"是报表中定义过的一个参数名称,其类型为String 型
			parameters.put("qaid", id);

			// 连接到数据库
			conn = DBUtil.getConnection();

			// 在控制台显示一下报表文件的物理路径
			// System.out.println(reportFile.getPath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}

	/**
	 * 打印质检报告3
	 */
	public ActionForward printTestReport3(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String type = req.getParameter("type");
		BigDecimal id = new BigDecimal(req.getParameter("id"));
		QAForm qaForm = (QAForm) actionForm;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			String sql = "select nvl(qatestft,0) as qatestft from tblqa where qaid='" + id + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			String qatestft = null;
			if (result.size() >= 1) {
				qatestft = ((HashMap) result.get(0)).get("1").toString();
			}
			File reportFile = null;
			// 报表编译之后生成的.jasper 文件的存放位置
			if ("jiewen".equals(type)) {
				if (qatestft.equals("0")) {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report6_1.jasper"));
				} else {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report7_1.jasper"));
				}

			} else {
				if (qatestft.equals("0")) {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report6.jasper"));
				} else {
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\report7.jasper"));
				}

			}

			// 传递报表中用到的参数值
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"是报表中定义过的一个参数名称,其类型为String 型
			parameters.put("qaid", id);

			// 连接到数据库

			// 在控制台显示一下报表文件的物理路径
			// System.out.println(reportFile.getPath());
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}

	/**
	 * 查询质检简略信息
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String order = req.getParameter("order");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		ActionForward af = new ActionForward();
		String forward = null;
		String filekey = "qat01_000";
		if (qaForm.getQatype().equals("make")) {
			if(qaForm.getQachkdt()==null||qaForm.getQachkdt().equals("")){
				filekey = "qat01_002";
			}else{
				filekey = "qat01_004";
			}
		} else {
			if(qaForm.getQachkdt()==null||qaForm.getQachkdt().equals("")){
				filekey="qat01_000";
			}else{
				filekey="qat01_003";
			}
		}
		if ("modify".equals(order)) {
			forward = "/qa/modify.jsp";
		} else if ("test".equals(order)) {
			forward = "/qa/queryzj.jsp";
			filekey = "qat01_001";
		}else if("test2".equals(order)){ //2012-2-25新增质量检测2
			forward = "/qa/queryzj2.jsp";
			filekey = "qat01_001";
		}/*else if ("earreptest".equals(order)) {
			forward = "/qa/earrepzj.jsp";
			filekey = "qat01_002";
		}*/
		else
			{forward = "/qa/query.jsp";}
		try {
			ClassHelper.copyProperties(qaForm, qa);
			qa.setFileKey(filekey);
			String hql = queryEnterprise(qa);
			af = super.init(req, forward, hql);
			// 检查是否存在？
			if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
				String msg = "没有查询到符合条件的记录！";
				super.saveSuccessfulMsg(req, msg);
			}
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}
		
	/**
	 * 放行查询
	 */
	public ActionForward queryFangxing(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String order = req.getParameter("order");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		ActionForward af = new ActionForward();
		String forward = null;
		String filekey = null;

		forward = "/qa/queryfangxing.jsp";
		filekey = "qat01_005";

		try {
			ClassHelper.copyProperties(qaForm,qa);
			qa.setFileKey(filekey);
			String hql = null;
			hql = (String) req.getSession().getAttribute("SqlForFangxing");
		
			
			if (qa.getQasid() == null || "".equals(qa.getQasid())) { // 输入框无内容
				req.getSession().removeAttribute("SqlForFangxing");
				hql = queryEnterprise(qa);
			} else {//输入框有内容
				if (hql == null || "".equals(hql)) { // hql为空		
					hql = queryEnterprise(qa);
					hql += " and (q.qasid='" + qa.getQasid() + "')";
					req.getSession().setAttribute("SqlForFangxing", hql);
				} else if (hql.indexOf(qa.getQasid()) == -1) { // hql不为空且不重复
					if (hql.indexOf("q.qasid") == -1) {//还没有机身编码的条件
						hql += " and (q.qasid='" + qa.getQasid() + "')";
					} else {//已经有机身编号的条件
						hql = hql.substring(0, hql.length() - 1);
						hql += " or q.qasid='" + qa.getQasid() + "')";
					}
					
					req.getSession().setAttribute("SqlForFangxing", hql);
				}
			}

			af = super.init(req, forward, hql,20);
			// 检查是否存在？
			if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
				String msg = "没有查询到符合条件的记录！";
				super.saveSuccessfulMsg(req, msg);
			}
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}
	
	
	
	/**
	 * 显示质检详细信息
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "主键为空，请重新查询");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// 将HashMap对象放入requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.find(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");
				ClassHelper.copyProperties(listci.get(0), qaForm);
				
				//将gctnm设置进qaform
				String sql="select g.gctnm from tblqa q left join tblfolio f on f.folno = q.qafno left join tblgrpclient g on g.gctid=f.folctid where q.qafno='"+qaForm.getQafno()+"'";
				Connection con = null;
				con = DBUtil.getConnection();
				Statement pstmt1;
				pstmt1 = con.createStatement();
				ResultSet rs1 = pstmt1.executeQuery(sql);
				rs1.next();
				qaForm.setGctnm(rs1.getString("gctnm"));
				rs1.close();
				pstmt1.close();
				DBUtil.closeConnection(con);
				
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		}
		if(qaForm.getQatestft()==null||qaForm.getQatestft()==""){//频响（检2）没有的话，跳转只显示质检1结果页面view2.jsp
			return mapping.findForward("view2");
		}
		return mapping.findForward("view");//频响（检2）有的话则代表进行了质检2，跳转无最大值增益有平均声增益页面view.jsp
	}

	/**
	 * 修改维修机信息
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "主键为空，请重新查询");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// 将HashMap对象放入requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.find(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");
				ClassHelper.copyProperties(listci.get(0), qaForm);
				
				//将gctnm设置进qaform
				String sql="select g.gctnm from tblqa q left join tblfolio f on f.folno = q.qafno left join tblgrpclient g on g.gctid=f.folctid where q.qafno='"+qaForm.getQafno()+"'";
				Connection con = null;
				con = DBUtil.getConnection();
				Statement pstmt1;
				pstmt1 = con.createStatement();
				ResultSet rs1 = pstmt1.executeQuery(sql);
				rs1.next();
				qaForm.setGctnm(rs1.getString("gctnm"));
				rs1.close();
				pstmt1.close();
				DBUtil.closeConnection(con);
				
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		}
		return mapping.findForward("alter");
	}

	/**
	 * 保存修改后的质检信息
	 */
	public ActionForward saveModified(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		try {
			// 设定经办信息
			ClassHelper.copyProperties(qaForm, qa);
			// 获取服务接口
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.modify(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "修改质检记录成功!");
				return go2Page(req, mapping, "qa");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * 删除质检记录
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "主键为空，请重新查询");
			return mapping.findForward("backspace");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// 将Application对象放入HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// 将HashMap对象放入requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.remove(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "删除质检记录成功！");
				FindLog.insertLog(req, qaid, "删除质检记录");
				return go2Page(req, mapping, "qa");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		}
	}
	
	/**
	 * 放行
	 */
	public ActionForward fangxing(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listQaid = data.getParameterValues("qaid"); // 质检编号
		String[] listQafno = data.getParameterValues("qafno"); // 订单编号
		String[] listQachkopr = data.getParameterValues("qachkopr"); // 质检员
		String[] listQachkopr2 = data.getParameterValues("qachkopr2"); //质检员2
		
		LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute(
				"LoginDTO");
		String qafxman=dto1.getBsc011();
		
		//判断质检员与放行人是否重复
		for(int i=0;i<listQaid.length;++i){
			if(listQachkopr[i]==null)
				listQachkopr[i]="";
			if(listQachkopr2[i]==null)
				listQachkopr2[i]="";
			if(qafxman.equals(listQachkopr[i]) || qafxman.equals(listQachkopr2[i])){
				super.saveSuccessfulMsg(req, "质检员1和质检员2不能与放行人相同！放行失败！");
				return actionMapping.findForward("backspace");
			}
		}
		
		Connection conn = null;	
		try {
			// 连接到数据库
			conn = DBUtil.getConnection();
			
			for(int i=0;i<listQafno.length;i++){
				//设定质检状态为之间完成
				String sql_update_qa="update tblqa set qastatus='finish',qafxman='"+qafxman+"' where qaid='"+listQaid[i]+"'";
				//设定定单状态为代发货
				String sql_update_fol="update tblfolio set folsta='waiting' where folno='"+listQafno[i]+"' and folsta='waitfangxing'";
				System.out.println(sql_update_fol);
				DBUtil.execSQL(conn, sql_update_qa);
				DBUtil.execSQL(conn, sql_update_fol);
			}
			DBUtil.commit(conn);
			super.saveSuccessfulMsg(req, "放行成功！");
		} catch (Exception e) {
			super.saveErrors(req, e);
			e.printStackTrace();
		} finally {
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}

		return actionMapping.findForward("backspace");
	}
	
	/**
	 * 打印放行
	 */
	public ActionForward printFangxing(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listQaid = data.getParameterValues("qaid"); // 质检编号
		
		Connection conn = null;
		try {

			// 报表编译之后生成的.jasper 文件的存放位置
			File reportFile = null;
			
			reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report_fangxing.jasper"));
			
			// 传递报表中用到的参数值
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 连接到数据库
			conn = DBUtil.getConnection();
			
			PrintFangXingFactory pf = new PrintFangXingFactory();
			List<PrintFangXing> data1 = pf.setCollection(listQaid);
			JRDataSource dataSource = new JRBeanCollectionDataSource(data1);
			//JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, dataSource); 
			// 在控制台显示一下报表文件的物理路径
			//System.out.println(reportFile.getPath());
			//byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
			
			byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, dataSource);
			
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return null;
	}

}
