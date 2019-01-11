package org.radf.apps.business.action;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.radf.apps.business.facade.BusinessFacade;
import org.radf.apps.business.form.BranchFinancialForm;
import org.radf.apps.commons.entity.BranchFinancialReport;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.FindLog;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.log.LogHelper;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.global.GlobalNames;

public class BranchFinancialAction extends ActionLeafSupport{
	
	LogHelper log = new LogHelper(this.getClass().getName());
	
	/**
	 * ��ת
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		LoginDTO dto = (LoginDTO)req.getSession().getAttribute("LoginDTO");
		String menuId = req.getParameter("menuId");
		String forward = menuId;
		BranchFinancialForm bf = new BranchFinancialForm();
		bf.setIyear(DateUtil.getYear());
		Calendar calendar = Calendar.getInstance();
		bf.setImonth(new Integer(calendar.get(2) + 1));
		/*if("fee".equals(menuId)){
			bf.setGctid(dto.getBsc011());
		}*/
		ClassHelper.copyProperties(bf, form);
		return mapping.findForward(forward);
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * ¼��ֹ�˾������Ϣ
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		BranchFinancialReport report = new BranchFinancialReport();
		BranchFinancialForm fform= (BranchFinancialForm) form;
		try {
			ClassHelper.copyProperties(fform, report);
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			report.setOperator(dto1.getBsc011());
			report.setBsc012(dto1.getBsc012());
			report.setOperatedate(DateUtil.getSystemCurrentTime());
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", report);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.insertReport(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "¼��ֹ�˾�����Ϣ�ɹ�!");
				String gctid = (String) ((HashMap) resEnv.getBody())
						.get("gctId");
				// ��ô�ҵ��㷵�ص���־��Ϣ
				String workString = (String) ((HashMap) resEnv.getBody())
						.get("workString");
				FindLog.insertLog(req, gctid, workString);
				return mapping.findForward("insertData");
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
	 * �޸�ҳ���ѯ�ֹ�˾���
	 */
	public ActionForward query(ActionMapping actionMapping,ActionForm actionForm, 
			HttpServletRequest req,HttpServletResponse res) throws Exception {
		BranchFinancialReport report=new BranchFinancialReport();
		BranchFinancialForm ff= (BranchFinancialForm) actionForm;
		ActionForward af = new ActionForward();
		String forward = "/business/branchCompany/query.jsp";
		try {
				ClassHelper.copyProperties(ff, report);
				report.setFileKey("report_query");
				String hql = queryEnterprise(report);
				af = init(req, forward, hql);
				if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
					String msg = "�������Ϣ��û¼�룡";
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
	 * ��ת�޸ķֹ�˾�����Ϣ
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
			BranchFinancialReport report=new BranchFinancialReport();
			BranchFinancialForm ff= (BranchFinancialForm) form;
			ClassHelper.copyProperties(ff, report);
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, BranchFinancialReport> mapRequest = new HashMap<String, BranchFinancialReport>();
			mapRequest.put("beo", report);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.queryReport(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				HashMap mapResponse = (HashMap) resEnv.getBody();
				List listci = (ArrayList) mapResponse.get("beo");// �ͻ�������Ϣ
			
				ClassHelper.copyProperties(listci.get(0), ff);
				return mapping.findForward("modify");
				
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
	}
	
	/**
	 * �����޸���Ϣ
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveModified(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		BranchFinancialReport report  = new BranchFinancialReport();
		BranchFinancialForm ff = (BranchFinancialForm) form;
		try {
			// �趨������Ϣ
			ClassHelper.copyProperties(ff, report);
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			report.setOperator(dto1.getBsc011());
			report.setBsc012(dto1.getBsc012());
			report.setOperatedate(DateUtil.getSystemCurrentTime());
			// ��ȡ����ӿ�
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, BranchFinancialReport> mapRequest = new HashMap<String, BranchFinancialReport>();
			mapRequest.put("beo", report);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.updateReport(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "�޸ķֹ�˾����ɹ�!");
				//return mapping.findForward("backspace");
				return go2Page(req,mapping,"business");
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
	 * �����ֹ�˾���excel���
	 * @param actionMapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward exportReport(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		BranchFinancialForm bf = (BranchFinancialForm) actionForm;
		Connection conn1 = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = null;
			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			String gctarea = bf.getGctarea();
			String gctid = bf.getGctid();
			if(gctid == null || "".equals(gctid)){
				if(gctarea == null || "".equals(gctarea)){
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\branchcompany_1.jasper"));//�ͻ�����Ϊ�գ�����Ϊ��
				}else{
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\branchcompany_2.jasper"));//�ͻ�����Ϊ�գ�����Ϊ��
					  parameters.put("gctarea",gctarea );
				}
			}else{
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\branchcompany_3.jasper"));//�ͻ����Ʋ�Ϊ��
				  parameters.put("gctid",gctid );
			}
			String webRootPath = req.getSession().getServletContext()
					.getRealPath("/");
			parameters.put("ccyear", bf.getStartyear());
			parameters.put("endYear", bf.getEndyear());
			parameters.put("startMonth", bf.getStartmonth());
			parameters.put("endMonth", bf.getEndmonth());
			parameters.put("SUBREPORT_DIR", webRootPath + "WEB-INF\\report\\");
		
			// �ڿ���̨��ʾһ�±����ļ�������·��
			System.out.println(reportFile.getPath());
			
			conn1 = DBUtil.getConnection();
			
			ServletOutputStream ouputStream = res.getOutputStream();
            String reportclass = "branchCompany_report";
			res.setContentType("application/vnd.ms-excel"); 
			res.setHeader("content-disposition", 
			"attachment;filename=" + reportclass + ".xls"); 

			JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn1); 

			JRXlsExporter exporter = new JRXlsExporter(); 
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//���ü�ⵥԪ���ʽ�������excel���ı���ʾ���֡�
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, rptpnt); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream); 
//			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,   
//            "�ͻ���Ϣ��.xls");   
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, 
			Boolean.FALSE); 
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, 
			Boolean.FALSE); 
			//��ӵ����Կ���
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
            //����GridLine
            //��С������䵥Ԫ��
            exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,true);

			exporter.exportReport(); 

			ouputStream.flush();
			ouputStream.close();
//			}
		} catch (Exception e) {
//			res.setContentType("text/html;charset=GB2312");   
//            PrintWriter   out   =   res.getWriter();   
            e.printStackTrace();   
		} finally {
			
			DBUtil.closeConnection(conn1);
		}
		return null;
	}
	
	/**
	 * �����ֹ�˾���excel���
	 * @param actionMapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward exportReport1(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		BranchFinancialForm bf = (BranchFinancialForm) actionForm;
		Connection conn1 = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = null;
			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			String gctarea = bf.getGctarea();
			String gctid = bf.getGctid();
			if(gctid == null || "".equals(gctid)){
				if(gctarea == null || "".equals(gctarea)){
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\branchreport_1.jasper"));//�ͻ�����Ϊ�գ�����Ϊ��
				}else{
					reportFile = new File(req.getSession().getServletContext()
							.getRealPath("\\WEB-INF\\report\\branchreport_2.jasper"));//�ͻ�����Ϊ�գ�����Ϊ��
					  parameters.put("gctarea",gctarea );
				}
			}else{
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\branchreport_3.jasper"));//�ͻ����Ʋ�Ϊ��
				  parameters.put("gctid",gctid );
			}
			String webRootPath = req.getSession().getServletContext()
					.getRealPath("/");
			//���ò�ѯ����
			parameters.put("qyear", bf.getStartyear());
			parameters.put("qmonth", bf.getStartmonth());
			parameters.put("SUBREPORT_DIR", webRootPath + "WEB-INF\\report\\");
		
			// �ڿ���̨��ʾһ�±����ļ�������·��
			System.out.println(reportFile.getPath());
			
			conn1 = DBUtil.getConnection();
			
			ServletOutputStream ouputStream = res.getOutputStream();
            String reportclass = "branchCompany_report";
			res.setContentType("application/vnd.ms-excel"); 
			res.setHeader("content-disposition", 
			"attachment;filename=" + reportclass + ".xls"); 

			JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn1); 

			JRXlsExporter exporter = new JRXlsExporter(); 
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//���ü�ⵥԪ���ʽ�������excel���ı���ʾ���֡�
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, rptpnt); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream); 
//			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,   
//            "�ͻ���Ϣ��.xls");   
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, 
			Boolean.FALSE); 
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, 
			Boolean.FALSE); 
			//��ӵ����Կ���
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
            //����GridLine
            //��С������䵥Ԫ��
            exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,true);

			exporter.exportReport(); 

			ouputStream.flush();
			ouputStream.close();
//			}
		} catch (Exception e) {
//			res.setContentType("text/html;charset=GB2312");   
//            PrintWriter   out   =   res.getWriter();   
            e.printStackTrace();   
		} finally {
			
			DBUtil.closeConnection(conn1);
		}
		return null;
	}
	/**
	 * ������������
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLastMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		BranchFinancialReport report = new BranchFinancialReport();
		try {
			report.setGctid(req.getParameter("gctid"));
			Integer year = Integer.valueOf(req.getParameter("iyear"));
			Integer month = Integer.valueOf(req.getParameter("imonth"));
			if(month != 1)
				month = month -1;
			else{
				month = 12;
				year = year - 1;
			}
			report.setIyear(year);
			report.setImonth(month);
			
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", report);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.lastMonth(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "��ѯ��������!");
				List list = (ArrayList) ((HashMap) resEnv.getBody()).get("beo");
				System.out.println("LLLLLLLLL" + list.size());
				if (list.size() > 1)
					throw new Exception();
				ClassHelper.copyProperties(list.get(0), report);
			
				res.setCharacterEncoding("GBK");
				res.getWriter().write("[{premoneyfunds:" + report.getMoneyfunds() +",preaccountpayable:" + report.getAccountpayable() +",preyearundisprofits:" + report.getPreyearundisprofits() +",preaccuprofit:" + report.getYearprofit() +",preaccuinvoiceamount:" + report.getAccuinvoiceamount()+",preaccuactualsales:"+ report.getAccuactualsales()+",accucostofsales:" +report.getAccucostofsales()+",paidincapital:" +report.getPaidincapital()+",elsereceivables:" +report.getElsereceivables()+",inventory:" +report.getInventory()+",elseaccountpayable:" +report.getElseaccountpayable()+"}]");
			}
		} catch (Exception e) {
			super.saveErrors(req, e);
		}
		return null;
	}
	
	/**
	 * ��ȡǰʮһ�����ۼƿ�Ʊ��
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccuyearinvoiceamount(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String gctid = req.getParameter("gctid");
		Integer iyear = Integer.valueOf(req.getParameter("iyear"));
		Integer imonth = Integer.valueOf(req.getParameter("imonth"));
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			String sql="SELECT SUM(invoiceamount) from( SELECT r.invoiceamount FROM TBLBRANCHFINANCIALREPORT r where TO_DATE (TO_CHAR (r.iyear, '9999') || TO_CHAR (r.imonth, '99'),'yyyy-mm') < TO_DATE (TO_CHAR ('"+ iyear +"', '9999') || TO_CHAR ('"+imonth+"', '99'),'yyyy-mm') and r.GCTID='"+gctid+"' ORDER BY r.gctid,r.iyear,r.imonth) WHERE ROWNUM <=11";
			List result = (Vector)DBUtil.querySQL(con, sql);
			Double accuyearinvoiceamount;
			if(((HashMap) result.get(0)).get("1")==null){
				accuyearinvoiceamount=(double)0.00;
			}else{
				accuyearinvoiceamount = Double.parseDouble(((HashMap) result.get(0)).get("1").toString());
			}
			DBUtil.commit(con);	
			res.setCharacterEncoding("GBK");
			res.getWriter().write("[{accuyearinvoiceamount:" + accuyearinvoiceamount +"}]");	
		
	}catch (Exception e) {
		super.saveErrors(req, e);
	}finally {
		DBUtil.rollback(con);
		DBUtil.closeConnection(con);
	}
	return null;
	}
}
