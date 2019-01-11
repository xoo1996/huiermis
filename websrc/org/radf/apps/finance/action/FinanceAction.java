package org.radf.apps.finance.action;

import java.beans.Statement;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.radf.apps.business.facade.BusinessFacade;
import org.radf.apps.business.form.BranchFinancialForm;
import org.radf.apps.business.form.BusinessForm;
import org.radf.apps.client.group.facade.GroupClientFacade;
import org.radf.apps.client.group.form.GroupClientForm;
import org.radf.apps.commons.entity.BranchFinancialReport;
import org.radf.apps.commons.entity.Business;
import org.radf.apps.commons.entity.Charge;
import org.radf.apps.commons.entity.CusReReport;
import org.radf.apps.commons.entity.Customization;
import org.radf.apps.commons.entity.DisExamine;
import org.radf.apps.commons.entity.EarMould;
import org.radf.apps.commons.entity.Fee;
import org.radf.apps.commons.entity.FinRate;
import org.radf.apps.commons.entity.Finance;
import org.radf.apps.commons.entity.FinanceReport;
import org.radf.apps.commons.entity.GroupClient;
import org.radf.apps.commons.entity.Order;
import org.radf.apps.commons.entity.OrderDetail;
import org.radf.apps.commons.entity.PrintBeforeVerify;
import org.radf.apps.commons.entity.PrintBeforeVerifyFactory;
import org.radf.apps.commons.entity.Product;
import org.radf.apps.commons.entity.Repair;
import org.radf.apps.commons.entity.Report;
import org.radf.apps.commons.entity.ReportCusReFactory;
import org.radf.apps.commons.entity.ReportFactory2;
import org.radf.apps.commons.entity.ReportFinanceFactory;
import org.radf.apps.commons.entity.SingleClient;
import org.radf.apps.commons.entity.Store;
import org.radf.apps.finance.facade.FinanceFacade;
import org.radf.apps.finance.form.FinanceHeaderForm;
import org.radf.apps.order.facade.OrderFacade;
import org.radf.apps.order.form.OrderDetailForm;
import org.radf.apps.order.form.OrderHeaderForm;
import org.radf.apps.product.facade.ProductFacade;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.FindLog;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.commons.SubmitDataMap;
import org.radf.plat.commons.TypeCast;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.exception.NoConnectionException;
import org.radf.plat.util.global.GlobalNames;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/**
 * �������
 */
public class FinanceAction extends ActionLeafSupport {

	public ActionForward queryToDrawBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
	
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		
		if (notype.equals("order")) {
			fileKey = "fin01_001";
		} else if (notype.equals("common")) {
			fileKey = "fin01_002";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawbill");
		}
		
		String forward = "/finance/to_draw_bill.jsp";
		
		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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
	public ActionForward queryToDrawRetailBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		
		if (notype.equals("order")) {
			fileKey = "fin01_005";
		} else if (notype.equals("common")) {
			fileKey = "fin01_006";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawretailbill");
		}
		
		
		String forward = "/finance/to_draw_retail_bill.jsp";
		

		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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
	
	public ActionForward queryToDrawSpecialBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		
		if (notype.equals("order")) {
			fileKey = "fin01_005";
		} else if (notype.equals("common")) {
			fileKey = "fin01_006";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawspecialbill");
		}
		
		
		String forward = "/finance/to_draw_special_bill.jsp";
		

		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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
	
	public ActionForward queryToDrawSpeciaRetaillBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		
		if (notype.equals("order")) {
			fileKey = "fin01_005";
		} else if (notype.equals("common")) {
			fileKey = "fin01_006";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawspecialretailbill");
		}
		
		
		String forward = "/finance/to_draw_special_retail_bill.jsp";
		

		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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

	
	public ActionForward queryBind(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String flag = req.getParameter("flag");//�ж������ĸ�ҳ��
		
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey =null;
		if(flag.equals("master")){//��ѯ��Ʊ����
			
			if(financeForm.getFintype2()==null||financeForm.getFintype2().equals("")){
				super.saveSuccessfulMsg(req, "��ѡ��Ʊ���ͣ�");
				return mapping.findForward("backspace");
			}
			
			if(financeForm.getFinisver()==null||financeForm.getFinisver().equals("")){
				fileKey = "fin02_001";
			}else if (financeForm.getFinisver().equals("isver")){
				fileKey = "fin02_001_1";
			}else if (financeForm.getFinisver().equals("nover")){
				fileKey = "fin02_001_2";
			}
			if(financeForm.getFintype2().equals("retail")||financeForm.getFintype2().equals("speretail")){
				fileKey = "fin02_003";
				if (financeForm.getFinisver().equals("isver")){
					fileKey = "fin02_003_1";
				}else if (financeForm.getFinisver().equals("nover")){
					fileKey = "fin02_003_2";
				}
			}
		}else{//�޸Ŀ�Ʊ��Ϣ
			if(financeForm.getFintype()==null||financeForm.getFintype().equals("")){
				super.saveSuccessfulMsg(req, "��ѡ��Ʊ���ͣ�");
				return mapping.findForward("backspace");
			}
			fileKey = "fin02_002";
			if(financeForm.getFintype().equals("retail")||financeForm.getFintype().equals("speretail")){
				fileKey ="fin02_002_1";
			}
		}
		String forward=null;

		if(flag.equals("master")){
			forward = "/finance/query_bill.jsp";
		}else{
			forward = "/finance/modify_bill.jsp";
		}
		
		try {

			ClassHelper.copyProperties(financeForm, fin);
						
			if(flag.equals("master") ){
				fin.setFintype(financeForm.getFintype2());
			}
			
			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

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
	
	public ActionForward queryRedBind(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
			
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = null;
		
		if(financeForm.getFinisver()==null||financeForm.getFinisver().equals("")){
			fileKey = "fin03_001";
		}else if (financeForm.getFinisver().equals("isver")){
			fileKey = "fin03_001_1";
		}else if (financeForm.getFinisver().equals("nover")){
			fileKey = "fin03_001_2";
		}
		
		String forward= "/finance/query_red_bill.jsp";

		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

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

	public ActionForward queryToDrawRedBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		if (notype.equals("order")) {
			fileKey = "fin01_003";
		} else if (notype.equals("common")) {
			fileKey = "fin01_004";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawredbill");
		}

		String forward = "/finance/to_draw_red_bill.jsp";

		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);

			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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

	public ActionForward saveFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String fintype = req.getParameter("fintype");// ��Ʊ����
		String notype = req.getParameter("notype");// ��������
		//String fintel = req.getParameter("fintel");//��Ʊ�绰
		//System.out.println(fintel+"---------------------------");

		SubmitDataMap data = new SubmitDataMap(req);
		String[] listTypeno = data.getParameterValues("typeno"); // ���
		String[] listGctnm = data.getParameterValues("gctnm"); // ��������
		String[] listGctid = data.getParameterValues("gctid"); // ���̱��
		String[] listIctnm = data.getParameterValues("ictnm"); // ���˿ͻ�
		String[] listIctid = data.getParameterValues("ictid"); // �ͻ����
		String[] listPdtid = data.getParameterValues("pdtid"); // ��Ʒ����
		String[] listPdtnm = data.getParameterValues("pdtnm"); // ��Ʒ����
		String[] listBand = data.getParameterValues("band"); // ��Ʒ����
		String[] listTmksid = data.getParameterValues("tmksid"); // ���ƻ�������
		String[] listChgdt = data.getParameterValues("chgdt"); // �շ�����
		String[] listRedt = data.getParameterValues("redt"); // �շ�����
		String[] listPdtprc = data.getParameterValues("pdtprc"); // ԭ��
		String[] listPdtnum = data.getParameterValues("pdtnum"); // ����
		String[] listFinrate = data.getParameterValues("finrate"); // ��Ʊ����
		String[] listFinprc = data.getParameterValues("finprc"); // ��˰����
		String[] listFinprccount = data.getParameterValues("finprccount"); // �ܶ�
		String[] listInvoicecode = data.getParameterValues("invoicecode"); // �ܶ�
		String[] listInvoiceno = data.getParameterValues("invoiceno"); // �ܶ�
		String[] listSellprc = data.getParameterValues("sellprc"); // �ܶ�
		String[] listRetailname = data.getParameterValues("retailname"); //��Ʊ̧ͷ
		String[] listRetailtaxno = data.getParameterValues("retailtaxno"); //˰��
		String[] listComaddr = data.getParameterValues("comaddr"); //��˾��ַ
		String[] listDepositbank = data.getParameterValues("depositbank"); //������
		String[] listDepositid = data.getParameterValues("depositid"); //�����˺�
		String[] listSpecialtel= data.getParameterValues("specialtel"); //ר�ÿ�Ʊ�绰

		Connection con = null;
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
		
		//�ж�ʱ���Ƿ�Ϲ�
		if(fintype!=null && fintype.equals("normal")&&financeFacade.isTimeOK()){
			super.saveSuccessfulMsg(req, "�ύ��Ʊ���뿪��ʱ��Ϊÿ��26��֮ǰ�����������ύʱ��֮��");
			return mapping.findForward("backspace");
		}

		//�ж��Ƿ��ظ��ύ
		if(!financeFacade.ifHaveFin(fintype, listTypeno,listPdtid)){
			super.saveSuccessfulMsg(req, "�����ظ��ύ��");
			return mapping.findForward("backspace");
		}

		try {

			if (listTypeno.length != 0) {
				
				//����һ��fin��list
				List<Finance> financeList = financeFacade.createfin(fintype,
						notype, listTypeno, listGctnm, listGctid, listIctnm,
						listIctid, listPdtid, listPdtnm, listBand, listTmksid,
						listChgdt, listRedt, listPdtprc, listPdtnum,
						listFinrate, listFinprccount, listFinprc,
						listInvoicecode, listInvoiceno, listSellprc,
						listRetailname, listRetailtaxno, listComaddr,
						listDepositbank, listDepositid, listSpecialtel);
				//����fin list
				for (Finance finance : financeList) {
					
					con = DBUtil.getConnection();
					
					//�������ݿ����д�����tri_tblfolio����2011-12-26֮ǰ�����ݽ�ֹ�޸ģ���˷���ǰ�ߴ����粻����Ҫ��
					String sql1 = "update tblfolio set isfinbill = '1' where folno='"
							+ finance.getTypeno() + "'";
					String sql2 = "update tblnorchg set isfinbill = '1' where chgid='"
							+ finance.getTypeno() + "'";
					String sql3 = "update tblfolio set isfinredbill = '1' where folno='"
							+ finance.getTypeno() + "'";
					String sql4 = "update tblnorchg set isfinredbill = '1' where chgid='"
							+ finance.getTypeno() + "'";

					if (!fintype.equals("red") && notype.equals("order")) {
						DBUtil.execSQL(con, sql1);
					}else if (!fintype.equals("red")&& notype.equals("common")) {
						DBUtil.execSQL(con, sql2);
					}else if (fintype.equals("red")&& notype.equals("order")) {
						DBUtil.execSQL(con, sql3);
					}else if (fintype.equals("red")&& notype.equals("common")) {
						DBUtil.execSQL(con, sql4);

					}
					
					//����finance
					RequestEnvelop requestEnvelop = new RequestEnvelop();
					EventResponse returnValue = new EventResponse();
					HashMap<String, Object> mapRequest = new HashMap<String, Object>();
					mapRequest.put("beo", finance);
					requestEnvelop.setBody(mapRequest);
					// ���ö�Ӧ��Facadeҵ������
					ResponseEnvelop resEnv = financeFacade.save(requestEnvelop);
					// �����ؽ��
					returnValue = processRevt(resEnv);

					if (!returnValue.isSucessFlag()) {
						String[] aa = StringUtil.getAsStringArray(
								returnValue.getMsg(), "|");
						super.saveSuccessfulMsg(req, aa[3]);
						return mapping.findForward("backspace");
					}

					
					DBUtil.commit(con);
					DBUtil.closeConnection(con);


				}

			} else {

				super.saveSuccessfulMsg(req, "δѡ������");
				return mapping.findForward("backspace");

			}
			
			super.saveSuccessfulMsg(req, "�ύ�ɹ�");
			return mapping.findForward("backspace");

		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);

		}


	}

	public ActionForward saveFinnt(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		SubmitDataMap data = new SubmitDataMap(req);
		String[] listFinno = data.getParameterValues("finno"); //��Ʊ��ע
		String[] listFintype = data.getParameterValues("fintype"); // ��Ʊ����
		String[] listNotype = data.getParameterValues("notype"); // ��������
		String[] listGctid = data.getParameterValues("gctid"); // ��������
		String[] listPdtid = data.getParameterValues("pdtid"); // ��Ʒ���
		String[] listPdtnum = data.getParameterValues("pdtnum"); //��Ʒ����
		String[] listFinnt = data.getParameterValues("finnt"); //��Ʊ��ע
		
		Connection con = null;
		con = DBUtil.getConnection();
		try{
			if(listFintype[0].equals("retail")){
				for(int i=0;i<listFintype.length;i++){
					String sql = "update tblfinance set finnt='" + listFinnt[i]
							+ "' where bindno is null and finno='" + listFinno[i] + "' ";			
					DBUtil.execSQL(con, sql);
				}
			}else{
				for(int i=0;i<listFintype.length;i++){
					String sql = "update tblfinance set finnt='" + listFinnt[i]
							+ "' where bindno is null and fintype='" + listFintype[i]
							+ "' and notype='"+listNotype[i]
							+"' and gctid='"+listGctid[i]
							+"' and pdtid='"+listPdtid[i]
							+"'";			
					DBUtil.execSQL(con, sql);
				}
			}
			DBUtil.commit(con);
		} catch (Exception e) {

			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "�ύ��Ʊ��ע�ɹ�");
		return mapping.findForward("backspace");
	}

	public ActionForward revokeFin(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listFintype = data.getParameterValues("fintype"); // ��Ʊ����
		String[] listNotype = data.getParameterValues("notype"); // ��������
		String[] listGctid = data.getParameterValues("gctid"); // ��������
		String[] listPdtid = data.getParameterValues("pdtid"); // ��Ʒ���
		String[] listPdtnum = data.getParameterValues("pdtnum"); //��Ʒ����
		String[] listFinnt = data.getParameterValues("finnt"); //��Ʊ��ע
		String[] listFinno = data.getParameterValues("finno"); //��Ʊ���
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");

		try{
			financeFacade.revokeFin(listFinno, listFintype, listNotype, listGctid, listPdtid, listPdtnum, listFinnt);			
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} 
		super.saveSuccessfulMsg(req, "������Ʊ����ɹ�");
		return mapping.findForward("backspace");
	}
	
	public ActionForward refuseFin(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		SubmitDataMap data = new SubmitDataMap(req);
		String[] listFintype = data.getParameterValues("fintype"); // ��Ʊ����
		String[] listNotype = data.getParameterValues("notype"); // ��������
		String[] listGctid = data.getParameterValues("gctid"); // ��������
		String[] listPdtid = data.getParameterValues("pdtid"); // ��Ʒ���
		String[] listPdtnum = data.getParameterValues("pdtnum"); //��Ʒ����
		String[] listFinnt = data.getParameterValues("finnt"); //��Ʊ��ע
		String[] listFinno = data.getParameterValues("finno"); //��Ʊ���
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
		
		try{
			financeFacade.revokeFin(listFinno, listFintype, listNotype, listGctid, listPdtid, listPdtnum, listFinnt);			

		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		}
		super.saveSuccessfulMsg(req, "���˿�Ʊ�ɹ�");
		return mapping.findForward("backspace");
	}
	
	public ActionForward queryBaseInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		GroupClient gc = new GroupClient();
		FinanceHeaderForm gcf = (FinanceHeaderForm) form;
		ActionForward af = new ActionForward();
		String forward = "/finance/modify_baseinfo.jsp";

		try {
			ClassHelper.copyProperties(gcf, gc);
			gc.setFileKey("fin05_001");
			String hql = queryEnterprise(gc);
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
	
	public ActionForward modifyBaseInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String gctid = req.getParameter("gctid");
		GroupClient gc = new GroupClient();
		FinanceHeaderForm gcf = (FinanceHeaderForm) form;

		if (null == gctid || "".equalsIgnoreCase(gctid)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else {
			ClassHelper.copyProperties(gcf, gc);
			GroupClientFacade facade = (GroupClientFacade) getService("GroupClientFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, GroupClient> mapRequest = new HashMap<String, GroupClient>();
			mapRequest.put("beo", gc);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.print(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");// ����ͻ���Ϣ
				ClassHelper.copyProperties(listci.get(0), gcf);
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
			}
		}
		return mapping.findForward("alter");
	}

	/**
	 * �����޸ĺ������ͻ���Ϣ
	 */
	public ActionForward saveModified(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		FinanceHeaderForm gcf = (FinanceHeaderForm) form;
		
		Connection con = null;
		con = DBUtil.getConnection();
		String sql = "update tblgrpclient set gctsname ='" + gcf.getGctsname()
				+ "',gctemail='" + gcf.getGctemail() + "', taxno='"
				+ gcf.getTaxno() + "', gctmobilephone='" + gcf.getGctmobilephone()
				+ "', gcttel='" + gcf.getGcttel()
				+ "', gctdepositbank='" + gcf.getGctdepositbank()
				+ "', gctdepositid='" + gcf.getGctdepositid()
				+ "' where gctid='" + gcf.getGctid() + "'";
		try {
			con = DBUtil.getConnection();
			DBUtil.execSQL(con, sql);
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		} finally {
			DBUtil.commit(con);
			DBUtil.closeConnection(con);
		}
		
		super.saveSuccessfulMsg(req, "�����޸ĳɹ�");
		return mapping.findForward("backspace");
	}
	
	
	public ActionForward creInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
		
		//�ж�ʱ���Ƿ�Ϲ�
		if(!financeFacade.isTimeOK()){
			super.saveSuccessfulMsg(req, "��˿�Ʊ���뿪��ʱ��Ϊÿ��25��֮�󣬲����������ʱ��֮��");
			return mapping.findForward("backspace");
		}
	
		
		Connection con = null;
		con = DBUtil.getConnection();
		java.sql.Statement pstmt = con.createStatement();
		CallableStatement proc = null;
		String gctnm =null;
		String sql2=null;
		
		String bindno=null;
		
		try{
				String sql1 = "select distinct gctnm from tblfinance where bindno is null and fintype = 'normal' ";
				
				ResultSet rst = pstmt.executeQuery(sql1);

				while (rst.next()){
					gctnm = rst.getString("gctnm");
					
					proc = con.prepareCall("{ call PRC_CRE_FIN(?) }");
					proc.registerOutParameter(1, Types.VARCHAR);//��ô�������
					proc.execute();
					bindno = proc.getString(1);
					proc.close();
					
				sql2 = "update tblfinance set bindno ='" + bindno + "' ,finpassdt=sysdate "
						+ "where bindno is null and fintype = 'normal'"
						+ " and gctnm='"+gctnm+"'";
				
					DBUtil.execSQL(con, sql2);
				} 
				
				DBUtil.commit(con);
				rst.close();
				pstmt.close();
				
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "���ɿ�Ʊ������ųɹ�");
		return mapping.findForward("backspace");		
	}
	
	public ActionForward creSpecialInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
				
		Connection con = null;
		con = DBUtil.getConnection();
		java.sql.Statement pstmt = con.createStatement();
		CallableStatement proc = null;
		String gctnm =null;
		String sql2=null;
		
		String bindno=null;
		
		try{
				String sql1 = "select distinct gctnm from tblfinance where bindno is null and fintype = 'special' ";
				
				ResultSet rst = pstmt.executeQuery(sql1);

				while (rst.next()){
					gctnm = rst.getString("gctnm");
					
					proc = con.prepareCall("{ call PRC_CRE_FIN(?) }");
					proc.registerOutParameter(1, Types.VARCHAR);//��ô�������
					proc.execute();
					bindno = proc.getString(1);
					proc.close();
					
				sql2 = "update tblfinance set bindno ='" + bindno + "' ,finpassdt=sysdate "
						+ "where bindno is null and fintype = 'special'"
						+ " and gctnm='"+gctnm+"'";
				
					DBUtil.execSQL(con, sql2);
				} 
				
				DBUtil.commit(con);
				rst.close();
				pstmt.close();
				
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "���ɿ�Ʊ������ųɹ�");
		return mapping.findForward("backspace");		
	}
	
	public ActionForward creRetailInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
		
		//�ж�ʱ���Ƿ�Ϲ�
		if(!financeFacade.isTimeOK()){
			super.saveSuccessfulMsg(req, "��˿�Ʊ���뿪��ʱ��Ϊÿ��25��֮�󣬲����������ʱ��֮��");
			return mapping.findForward("backspace");
		}
		Connection con = null;
		con = DBUtil.getConnection();
		java.sql.Statement pstmt = con.createStatement();
		CallableStatement proc = null;
		String retailname =null;
		String sql2=null;
		
		String bindno=null;
		
		try{	
				//���ݿ�Ʊ̧ͷ����bindno
				String sql1 = "select distinct retailname from tblfinance where bindno is null and fintype = 'retail' ";
				
				ResultSet rst = pstmt.executeQuery(sql1);

				while (rst.next()){
					
					retailname = rst.getString("retailname");
					
					proc = con.prepareCall("{ call PRC_CRE_FIN(?) }");
					proc.registerOutParameter(1, Types.VARCHAR);//��ô�������
					proc.execute();
					bindno = proc.getString(1);
					proc.close();
					
				sql2 = "update tblfinance set bindno ='" + bindno + "' ,finpassdt=sysdate "
						+ "where bindno is null and fintype = 'retail'"
						+ " and retailname='"+retailname+"'";
				
					DBUtil.execSQL(con, sql2);
				} 
				
				DBUtil.commit(con);
				rst.close();
				pstmt.close();
				
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "���ɿ�Ʊ������ųɹ�");
		return mapping.findForward("backspace");		
	}
	
	public ActionForward creSperetailInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
		
		Connection con = null;
		con = DBUtil.getConnection();
		java.sql.Statement pstmt = con.createStatement();
		CallableStatement proc = null;
		String retailname =null;
		String sql2=null;
		
		String bindno=null;
		
		try{	
				//���ݿ�Ʊ̧ͷ����bindno
				String sql1 = "select distinct retailname from tblfinance where bindno is null and fintype = 'speretail' ";
				
				ResultSet rst = pstmt.executeQuery(sql1);

				while (rst.next()){
					
					retailname = rst.getString("retailname");
					
					proc = con.prepareCall("{ call PRC_CRE_FIN(?) }");
					proc.registerOutParameter(1, Types.VARCHAR);//��ô�������
					proc.execute();
					bindno = proc.getString(1);
					proc.close();
					
				sql2 = "update tblfinance set bindno ='" + bindno + "' ,finpassdt=sysdate "
						+ "where bindno is null and fintype = 'speretail'"
						+ " and retailname='"+retailname+"'";
				
					DBUtil.execSQL(con, sql2);
				} 
				
				DBUtil.commit(con);
				rst.close();
				pstmt.close();
				
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "���ɿ�Ʊ������ųɹ�");
		return mapping.findForward("backspace");		
	}
	
	public ActionForward creRedInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Connection con = null;
		con = DBUtil.getConnection();
		String sql=null;
		try{
			sql = "update tblfinance set bindno ='-----' ,finpassdt=sysdate "
					+ "where bindno is null and fintype = 'red' ";
			
				DBUtil.execSQL(con, sql);
				DBUtil.commit(con);
		}catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		
		super.saveSuccessfulMsg(req, "���к�ɫ��Ʊ�ѱ��Ϊ��ȡ");
		return mapping.findForward("backspace");	
	}
	
	public ActionForward exportBillExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		FinanceHeaderForm fhf = (FinanceHeaderForm) actionForm;
		String fintype = req.getParameter("fintype");//�ж������ĸ�ҳ��
		
		
		String gctid=fhf.getGctid();
		String finsubdtstart=fhf.getFinsubdtstart();
		String finsubdtend=fhf.getFinsubdtend();
		String finpassdtstart=fhf.getFinpassdtstart();
		String finpassdtend=fhf.getFinpassdtend();
		String chgdtstart=fhf.getChgdtstart();
		String chgdtend=fhf.getChgdtend();
		String gcttype=fhf.getGcttype();

		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			ReportFinanceFactory pf = new ReportFinanceFactory();
			List<FinanceReport> data=null;
			if(fintype.equals("normal")){
				data = pf.setCollection(gctid,finsubdtstart,finsubdtend,finpassdtstart,finpassdtend,chgdtstart,chgdtend,gcttype);
			}else if(fintype.equals("retail")){
				data = pf.setCollection3(gctid,finsubdtstart,finsubdtend,finpassdtstart,finpassdtend,chgdtstart,chgdtend,gcttype);
			}else if(fintype.equals("special")){
				data = pf.setCollection4(gctid,finsubdtstart,finsubdtend,finpassdtstart,finpassdtend,chgdtstart,chgdtend,gcttype);
			}else if(fintype.equals("speretail")){
				data = pf.setCollection5(gctid,finsubdtstart,finsubdtend,finpassdtstart,finpassdtend,chgdtstart,chgdtend,gcttype);
			}
			
			JRDataSource dataSource = new JRBeanCollectionDataSource(data);
			
			File reportFile =null;
			if(fintype.equals("normal")){
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\finReport.jasper"));
			}else if(fintype.equals("retail")){
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\finRetailReport.jasper"));
			}else if(fintype.equals("special")){
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\finSpecialReport.jasper"));
			}else if(fintype.equals("speretail")){
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\finSperetailReport.jasper"));
			}
			ServletOutputStream ouputStream = res.getOutputStream();
            String reportclass = "finance";
			res.setContentType("application/vnd.ms-excel"); 
			res.setHeader("content-disposition", 
			"attachment;filename=" + reportclass + ".xls"); 

			JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, dataSource); 

			JRXlsExporter exporter = new JRXlsExporter(); 
         
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, rptpnt); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream); 
  
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward configFinRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		FinRate finrate = new FinRate();
		ActionForward af = new ActionForward();
		String forward = "/finance/configFinRate.jsp";
		
		Connection con = null;
		con = DBUtil.getConnection();
		
		try {
			//��õ�ǰ����
			String sql = "select count(*) from tblfinrate ";

			List result = (Vector) DBUtil.querySQL(con,sql);
	    	Integer count = Integer.parseInt(((HashMap) result.get(0)).get("1").toString())+10;//���Ǳ�ʵ�ʵĶ��10��
	    		    	
	    	req.getSession().setAttribute("count",count);
					
			finrate.setFileKey("config_finrate");
			String hql = queryEnterprise(finrate);
			af = init(req, forward, hql,count);
			
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return af;
	}
	
	public ActionForward setFinRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		SubmitDataMap data = new SubmitDataMap(req);
		String[] listId = data.getParameterValues("finrateid"); //���
		String[] listBand = data.getParameterValues("band"); //Ʒ��
		String[] listSeries = data.getParameterValues("series"); //ϵ��
		String[] listRate = data.getParameterValues("rate"); //����
		String[] listFpdtnm = data.getParameterValues("fpdtnm"); //��Ʒ���

		
		Connection con = null;
		con = DBUtil.getConnection();
		try{
			//���ԭ������Ŀ
			String sql_count = "select count(*) from tblfinrate ";
			List result = (Vector) DBUtil.querySQL(con,sql_count);
	    	Integer count = Integer.parseInt(((HashMap) result.get(0)).get("1").toString());
	    	
	    	String sql=null;
			for(int i=0;i<listId.length;i++){
				if(Integer.parseInt(listId[i])<=count){
					sql= "update tblfinrate set band ='" + listBand[i]+"', " +
							"series ='" + listSeries[i]+"', " +
							"rate ='" + listRate[i]+"', " +
							"fpdtnm ='" + listFpdtnm[i]+"' " +
							"where finrateid ='"+listId[i]+"'";	
				}else{
					sql = "insert into tblfinrate values ('" + listId[i]
							+ "','" + listBand[i] + "','" + listSeries[i]
							+ "','" + listRate[i] + "','"+listFpdtnm[i]+"')";
				}
				DBUtil.execSQL(con, sql);
			}
			DBUtil.commit(con);
		} catch (Exception e) {

			super.saveErrors(req, e);
			return mapping.findForward("backspace");

		} finally {	
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "�������óɹ�");
		return mapping.findForward("configfinrate");
	}
	
	public ActionForward deleteFinRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		SubmitDataMap data = new SubmitDataMap(req);
		String[] listId = data.getParameterValues("finrateid"); //���
		
		Connection con = null;
		con = DBUtil.getConnection();
		
		String sql_delete=null;
		
		try{
			for(int i=0;i<listId.length;i++){
				sql_delete= "delete from tblfinrate where finrateid ='"+listId[i]+"'";			
				DBUtil.execSQL(con, sql_delete);
			}
			DBUtil.commit(con);
		}catch(Exception e){

			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}finally{
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		super.saveSuccessfulMsg(req, "ɾ�����óɹ���");
		return mapping.findForward("configfinrate");
	}
	
	public ActionForward setTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		FinanceHeaderForm ff = (FinanceHeaderForm) form;
		Connection con = null;
		con = DBUtil.getConnection();
		try{
			String sql_count = "select taxrate from tblfinconfig where id='1' ";
			
			List result = (Vector) DBUtil.querySQL(con,sql_count);
	    	String taxrate = ((HashMap) result.get(0)).get("1").toString();
	    	
	    	ff.setTaxrate(taxrate);
		}catch(Exception e){
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}finally{
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
			
		return mapping.findForward("taxrate");
	}
	
	public ActionForward saveTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		FinanceHeaderForm ff = (FinanceHeaderForm) form;
		
		Connection con = null;
		con = DBUtil.getConnection();
		String sql = "update tblfinconfig set taxrate ='" + ff.getTaxrate()+ "' where id='1' ";
		try {
			con = DBUtil.getConnection();
			DBUtil.execSQL(con, sql);
			DBUtil.commit(con);
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		
		super.saveSuccessfulMsg(req, "�����޸ĳɹ�");
		return mapping.findForward("taxrate");
	}
	
	public ActionForward queryToDrawNotBill(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ActionForward af = new ActionForward();
		FinanceHeaderForm financeForm = (FinanceHeaderForm) form;
		Finance fin = new Finance();
		String hql = null;
		String fileKey = "";
		String notype = financeForm.getNotype();
		if (notype.equals("order")) {
			fileKey = "fin04_001";
		} else if (notype.equals("common")) {
			fileKey = "fin04_002";
		} else {
			super.saveSuccessfulMsg(req, "��ѡ�񶩵�����");
			return mapping.findForward("todrawnotbill");
		}

		String forward = "/finance/to_draw_not_bill.jsp";
		//String forward = "/finance/to_draw_bill.jsp";
		try {

			ClassHelper.copyProperties(financeForm, fin);

			fin.setFileKey(fileKey);
			hql = queryEnterprise(fin);
			
/*			if (notype.equals("order")) {
			hql ="select * from (" +hql;
			hql +=") where chgdt between (select to_char(last_day(add_months(sysdate, -2)),'yyyy-mm-dd hh24:mi:ss') from dual) and (select to_char(last_day(add_months(sysdate, -1)),'yyyy-mm-dd hh24:mi:ss') from dual) ";
			} else if (notype.equals("common")) {
			hql ="select * from (" +hql;
			hql +=") where chgdt between (select to_char(last_day(add_months(sysdate, -2)),'yyyy-mm-dd hh24:mi:ss') from dual) and (select to_char(last_day(add_months(sysdate, -1)),'yyyy-mm-dd hh24:mi:ss') from dual) ";
			}*/
			af = super.init(req, forward, hql);

			req.getSession().setAttribute("notype", notype);

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
	
	public ActionForward saveNotFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
			String s = DateUtil.getSystemCurrentTime("yyyy-mm-dd");
			String day = s.substring(8, 10);
			if (Integer.parseInt(day) > Integer.parseInt("20") || Integer.parseInt(day) < Integer.parseInt("11")){
				super.saveSuccessfulMsg(req, "����ʧ�ܣ�����ÿ��11-20�Ŵ���δ��Ʊ����");
				return mapping.findForward("backspace");
			}
			
			FinanceFacade financeFacade = (FinanceFacade) getService("FinanceFacade");
			Connection con = null;
			
			try {
				long startTime = System.currentTimeMillis(); 
				//����δ��Ʊ�б�
				List<Finance> financeList = financeFacade.createNotBillFin();
				System.out.println(financeList.size()+"==================");
				if(financeFacade.save(financeList)){
				//if(financeFacade.savetest()){
					long endTime = System.currentTimeMillis(); 
					String time="δ��Ʊ���ɿ�Ʊ�ɹ�����ʱ��"+String.valueOf((endTime - startTime) / 1000)+"s";
					super.saveSuccessfulMsg(req, time);
					return mapping.findForward("backspace");
				}else{
					super.saveSuccessfulMsg(req, "ʧ��");
					return mapping.findForward("backspace");
				}
			} catch (Exception e) {

				super.saveErrors(req, e);
				return mapping.findForward("backspace");

			} 
	}
	
	public ActionForward exportNotBillExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			ReportFinanceFactory pf = new ReportFinanceFactory();
			List<FinanceReport> data = pf.setCollection2();
			JRDataSource dataSource = new JRBeanCollectionDataSource(data);

			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\finReport.jasper"));
			ServletOutputStream ouputStream = res.getOutputStream();
            String reportclass = "finance";
			res.setContentType("application/vnd.ms-excel"); 
			res.setHeader("content-disposition", 
			"attachment;filename=" + reportclass + ".xls"); 

			JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, dataSource); 

			JRXlsExporter exporter = new JRXlsExporter(); 
         
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, rptpnt); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream); 
  
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
