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
 * ��������
 */
public class QAAction extends ActionLeafSupport {
	LogHelper log = new LogHelper(this.getClass().getName());

	public QAAction() {
	}

	/**
	 * �����ʼ����
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
	 * �����ʼ�2���� //2012-2-25�����������2
	 */
	public ActionForward test2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("test2");
	}
	
	/*
	 * �������1�Ĳ���1
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
			
			// �趨������Ϣ
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // �ʼ�����
				if(null != qa.getQatype() && !("make".equals(qa.getQatype()))){
					qa.setQastatus("finish");
				}
				//if(null != qa.getQapid() && ("Ψ��".equals(qa.getPdttype()) || qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))
				if(null != qa.getQapid() && ("Ψ��".equals(qa.getPdttype()) ||qa.getQapid().equals("3197100")||qa.getQapid().equals("3197101")||qa.getQapid().equals("3198100")||qa.getQapid().equals("3198102")||qa.getQapid().equals("3199100")||qa.getQapid().equals("3199101")||qa.getQapid().equals("3198101")||qa.getQapid().equals("3198103")||qa.getQapid().equals("3198001")||qa.getQapid().equals("3199003")||qa.getQapid().equals("3197001")||qa.getQapid().equals("3198002")||qa.getQapid().equals("3199004")||qa.getQapid().equals("3197002")|| qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))

				{
					qa.setQastatus("finish");
				}
				
			// ��ȡ����ӿ�
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.finish2before(requestEnvelop);
			// �����ؽ��
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ�붩����Ϣ��ƥ��!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ����ɹ�!");
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
	 * �������2�Ĳ���1
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
			
			
			// �趨������Ϣ
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // �ʼ�����
				if(null != qa.getQatype() && !("make".equals(qa.getQatype()))){
					//qa.setQastatus("finish");
					qa.setQastatus("waitfx");
				}
				//if(null != qa.getQapid() && ("Ψ��".equals(qa.getPdttype()) || qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))
				if(null != qa.getQapid() && ("Ψ��".equals(qa.getPdttype()) || qa.getQapid().equals("3197100")||qa.getQapid().equals("3197101")||qa.getQapid().equals("3198100")||qa.getQapid().equals("3198102")||qa.getQapid().equals("3199100")||qa.getQapid().equals("3199101")||qa.getQapid().equals("3198101")||qa.getQapid().equals("3198103")||qa.getQapid().equals("3198001")||qa.getQapid().equals("3199003")||qa.getQapid().equals("3197001")||qa.getQapid().equals("3198002")||qa.getQapid().equals("3199004")||qa.getQapid().equals("3197002")||qa.getQapid().equals("999906")||qa.getQapid().equals("999907")||qa.getQapid().equals("999812")||qa.getQapid().equals("999813")||qa.getQapid().equals("999720")||qa.getQapid().equals("999721")||qa.getQapid().equals("069950")||qa.getQapid().equals("069951")||qa.getQapid().equals("069952")||qa.getQapid().equals("069870")||qa.getQapid().equals("069871")||qa.getQapid().equals("069872")||qa.getQapid().equals("069750")||qa.getQapid().equals("069751")||qa.getQapid().equals("069752")))

				{
					//qa.setQastatus("finish");
					qa.setQastatus("waitfx");
				}
				
			// ��ȡ����ӿ�
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.finish2before(requestEnvelop);
			// �����ؽ��
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ�붩����Ϣ��ƥ��!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ����ɹ�!");
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
	 * �ʼ죭�����ʼ��¼
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
			
		
			//����ά��
			if(null != qa.getQatype() && "make".equals(qa.getQatype())){
			
			File file1 = new File(request.getSession().getServletContext().getRealPath("\\qa\\check.csv"));
			FileReader reader1 =new FileReader(file1);
			BufferedReader br1 =new BufferedReader(reader1);
			String s1 = null;
			int index1;
			while((s1 = br1.readLine()) != null)
			{
				index1 = s1.indexOf(",");
				pnl = s1.substring(0, index1);   //������
				pnm = s1.substring(index1 + 1);  //���ƻ��ͺ�
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
//				File file = new File(request.getSession().getServletContext().getRealPath("\\qa\\����.csv"));
//				FileReader reader = new FileReader(file);
//				BufferedReader br = new BufferedReader(reader);
//				String s = null;
//				int index;
//				while((s = br.readLine()) != null)
//				{
//					index = s.indexOf(",");
//					pnl = s.substring(0, index);   //������
//					pnm = s.substring(index + 1);  //���ƻ��ͺ�
//				}
//				reader.close();
//				br.close();
//			}
		}
			
			// �趨������Ϣ
			//ClassHelper.copyProperties(qaForm, qa);  //9.1
				LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
				qa.setQachkopr(dto1.getBsc011());
				qa.setQachkdt(DateUtil.getDate()); // �ʼ�����
				//qa.setQastatus("finish");
				qa.setQastatus("waitfx");
			// ��ȡ����ӿ�
				QAFacade facade = (QAFacade) getService("QAFacade");
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
				HashMap<String,Object> mapRequest = new HashMap<String,Object>();
				mapRequest.put("beo", qa);
				mapRequest.put("pnl", pnl);
				mapRequest.put("pnm", pnm);
				mapRequest.put("flag", flag);
				requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.finish(requestEnvelop);
			// �����ؽ��
				returnValue = processRevt(resEnv);
			
			/*String flag = (String)((HashMap)resEnv.getBody()).toString();
			if(flag.equals("false"))
			{
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ�붩����Ϣ��ƥ��!");
				return mapping.findForward("backspace");
			}*/
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ����ɹ�!");
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
	 * �ʼ�2�������ʼ��¼
	 */
	public ActionForward updateTest2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QAForm qaForm = (QAForm) form;
		QA qa = new QA();
		try {
			// �趨������Ϣ
			ClassHelper.copyProperties(qaForm, qa);
			LoginDTO dto1 = (LoginDTO) request.getSession().getAttribute(
					"LoginDTO");
			qa.setQachkopr(dto1.getBsc011());
			qa.setQachkdt(DateUtil.getDate()); // �ʼ�����
			
			//qa.setQastatus("finish");
			qa.setQastatus("waitfx");
			
			// ��ȡ����ӿ�
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String,Object> mapRequest = new HashMap<String,Object>();
			mapRequest.put("beo", qa);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.finish(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(request, "�ʼ���Ϣ����ɹ�!");
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
	 * ��ӡ�ʼ챨��1
	 */
	public ActionForward printTestReport(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String type = req.getParameter("type");
		BigDecimal id = new BigDecimal(req.getParameter("id"));
		QAForm qaForm = (QAForm) actionForm;
		Connection conn = null;
		try {
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			//��ѯqatestft�����Ƿ�Ϊ��
			String sql = "select nvl(qatestft,0) as qatestft from tblqa where qaid='" + id + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			String qatestft = null;
			if (result.size() >= 1) {
				qatestft = ((HashMap) result.get(0)).get("1").toString();
			}
			File reportFile = null;
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
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

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("qaid", id);

			

			// �ڿ���̨��ʾһ�±����ļ�������·��
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
	 * ��ӡ�ʼ챨��2
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
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			if ("jiewen".equals(type)) {
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report7_1.jasper"));
			} else {
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report7.jasper"));
			}

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("qaid", id);

			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			// �ڿ���̨��ʾһ�±����ļ�������·��
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
	 * ��ӡ�ʼ챨��3
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
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
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

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("qaid", id);

			// ���ӵ����ݿ�

			// �ڿ���̨��ʾһ�±����ļ�������·��
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
	 * ��ѯ�ʼ������Ϣ
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
		}else if("test2".equals(order)){ //2012-2-25�����������2
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
			// ����Ƿ���ڣ�
			if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
				String msg = "û�в�ѯ�����������ļ�¼��";
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
	 * ���в�ѯ
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
		
			
			if (qa.getQasid() == null || "".equals(qa.getQasid())) { // �����������
				req.getSession().removeAttribute("SqlForFangxing");
				hql = queryEnterprise(qa);
			} else {//�����������
				if (hql == null || "".equals(hql)) { // hqlΪ��		
					hql = queryEnterprise(qa);
					hql += " and (q.qasid='" + qa.getQasid() + "')";
					req.getSession().setAttribute("SqlForFangxing", hql);
				} else if (hql.indexOf(qa.getQasid()) == -1) { // hql��Ϊ���Ҳ��ظ�
					if (hql.indexOf("q.qasid") == -1) {//��û�л�����������
						hql += " and (q.qasid='" + qa.getQasid() + "')";
					} else {//�Ѿ��л����ŵ�����
						hql = hql.substring(0, hql.length() - 1);
						hql += " or q.qasid='" + qa.getQasid() + "')";
					}
					
					req.getSession().setAttribute("SqlForFangxing", hql);
				}
			}

			af = super.init(req, forward, hql,20);
			// ����Ƿ���ڣ�
			if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
				String msg = "û�в�ѯ�����������ļ�¼��";
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
	 * ��ʾ�ʼ���ϸ��Ϣ
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.find(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");
				ClassHelper.copyProperties(listci.get(0), qaForm);
				
				//��gctnm���ý�qaform
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
		if(qaForm.getQatestft()==null||qaForm.getQatestft()==""){//Ƶ�죨��2��û�еĻ�����תֻ��ʾ�ʼ�1���ҳ��view2.jsp
			return mapping.findForward("view2");
		}
		return mapping.findForward("view");//Ƶ�죨��2���еĻ������������ʼ�2����ת�����ֵ������ƽ��������ҳ��view.jsp
	}

	/**
	 * �޸�ά�޻���Ϣ
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.find(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");
				ClassHelper.copyProperties(listci.get(0), qaForm);
				
				//��gctnm���ý�qaform
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
	 * �����޸ĺ���ʼ���Ϣ
	 */
	public ActionForward saveModified(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		try {
			// �趨������Ϣ
			ClassHelper.copyProperties(qaForm, qa);
			// ��ȡ����ӿ�
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.modify(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "�޸��ʼ��¼�ɹ�!");
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
	 * ɾ���ʼ��¼
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String qaid = req.getParameter("qaid");
		QA qa = new QA();
		QAForm qaForm = (QAForm) form;
		if (null == qaid || "".equalsIgnoreCase(qaid)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
			return mapping.findForward("backspace");
		} else {
			ClassHelper.copyProperties(qaForm, qa);
			QAFacade facade = (QAFacade) getService("QAFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap mapRequest = new HashMap();
			mapRequest.put("beo", qa);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.remove(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "ɾ���ʼ��¼�ɹ���");
				FindLog.insertLog(req, qaid, "ɾ���ʼ��¼");
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
	 * ����
	 */
	public ActionForward fangxing(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listQaid = data.getParameterValues("qaid"); // �ʼ���
		String[] listQafno = data.getParameterValues("qafno"); // �������
		String[] listQachkopr = data.getParameterValues("qachkopr"); // �ʼ�Ա
		String[] listQachkopr2 = data.getParameterValues("qachkopr2"); //�ʼ�Ա2
		
		LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute(
				"LoginDTO");
		String qafxman=dto1.getBsc011();
		
		//�ж��ʼ�Ա��������Ƿ��ظ�
		for(int i=0;i<listQaid.length;++i){
			if(listQachkopr[i]==null)
				listQachkopr[i]="";
			if(listQachkopr2[i]==null)
				listQachkopr2[i]="";
			if(qafxman.equals(listQachkopr[i]) || qafxman.equals(listQachkopr2[i])){
				super.saveSuccessfulMsg(req, "�ʼ�Ա1���ʼ�Ա2�������������ͬ������ʧ�ܣ�");
				return actionMapping.findForward("backspace");
			}
		}
		
		Connection conn = null;	
		try {
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();
			
			for(int i=0;i<listQafno.length;i++){
				//�趨�ʼ�״̬Ϊ֮�����
				String sql_update_qa="update tblqa set qastatus='finish',qafxman='"+qafxman+"' where qaid='"+listQaid[i]+"'";
				//�趨����״̬Ϊ������
				String sql_update_fol="update tblfolio set folsta='waiting' where folno='"+listQafno[i]+"' and folsta='waitfangxing'";
				System.out.println(sql_update_fol);
				DBUtil.execSQL(conn, sql_update_qa);
				DBUtil.execSQL(conn, sql_update_fol);
			}
			DBUtil.commit(conn);
			super.saveSuccessfulMsg(req, "���гɹ���");
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
	 * ��ӡ����
	 */
	public ActionForward printFangxing(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listQaid = data.getParameterValues("qaid"); // �ʼ���
		
		Connection conn = null;
		try {

			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = null;
			
			reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report_fangxing.jasper"));
			
			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();

			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();
			
			PrintFangXingFactory pf = new PrintFangXingFactory();
			List<PrintFangXing> data1 = pf.setCollection(listQaid);
			JRDataSource dataSource = new JRBeanCollectionDataSource(data1);
			//JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, dataSource); 
			// �ڿ���̨��ʾһ�±����ļ�������·��
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
