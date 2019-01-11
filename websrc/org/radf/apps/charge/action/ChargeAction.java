package org.radf.apps.charge.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.radf.apps.business.facade.BusinessFacade;
import org.radf.apps.business.form.BusinessForm;
import org.radf.apps.charge.facade.ChargeFacade;
import org.radf.apps.charge.form.ChargeForm;
import org.radf.apps.client.single.form.SingleClientForm;
import org.radf.apps.commons.entity.Audiogram;
import org.radf.apps.commons.entity.Charge;
import org.radf.apps.commons.entity.CusReReport;
import org.radf.apps.commons.entity.Order;
import org.radf.apps.commons.entity.OrderDetail;
import org.radf.apps.commons.entity.Product;
import org.radf.apps.commons.entity.Report;
import org.radf.apps.commons.entity.ReportCusReFactory;
import org.radf.apps.commons.entity.ReportFactory2;
import org.radf.apps.commons.entity.SingleClient;
import org.radf.apps.product.facade.ProductFacade;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.commons.SubmitDataMap;
import org.radf.plat.commons.TypeCast;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.global.GlobalNames;

import com.cm.service.BatteryService;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class ChargeAction extends ActionLeafSupport {
	public static final String huier=GlobalNames.HUIERMALL_LINK;
	//public static final String huier="http://10.0.0.249:8890/huiermall/services/testwebservice";
	//public static final String huier="http://10.0.0.249:8083/huiermall/services/testwebservice";
	/**
	 * ��ѯ��ͨ��Ʒ�շѻ�����Ϣ
	 */
	public ActionForward normalQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ChargeForm cForm = (ChargeForm) form;
		String forward = null;
		/*
		 * String fileKey = null;SAVE
		 * 
		 * fileKey = "chg01_000";
		 */
		forward = "/charge/norchgquery.jsp";

		ActionForward af = new ActionForward();
		Charge charge = new Charge();
		try {
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			ClassHelper.copyProperties(cForm, charge);

			if (dto.getBsc001().equals("1501000000")) {
				charge.setStogrcliid(dto.getBsc001());
			} else {
				charge.setStogrcliid(dto.getBsc011());
			}

			charge.setStoproid(cForm.getStoproid());
			charge.setPdtut(cForm.getPdtut());
			charge.setStart(cForm.getStart());
			charge.setEnd(cForm.getEnd());

			charge.setFileKey("chg01_000");
			String hql = queryEnterprise(charge);
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
	 * ��ͨ��Ʒ�շ�ʱ�û���Ϣ��ѯ
	 */
	public ActionForward clientQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ChargeForm cForm = (ChargeForm) form;
		String forward = null;
		String tp = req.getParameter("tp");
		if (tp.equals("normal")) {
			forward = "/charge/clientquery.jsp";
		}
		else if (tp.equals("youhui")) {
			forward="/charge/clientqueryyouhui.jsp";
		} else {
			forward="/charge/clientqueryerbei.jsp";

		}
		/*else {
			if (tp.equals("youhui")) {
				{
					forward = "/charge/clientquery.jsp";
				}

			} else {
				forward = "/charge/clientqueryerbei.jsp";
			}
		}*/
		ActionForward af = new ActionForward();

		Charge charge = new Charge();
		try {
			ClassHelper.copyProperties(cForm, charge);
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			charge.setIctgctid(dto.getBsc011());
			charge.setFileKey("chg01_002");
			String hql = queryEnterprise(charge);
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
	 * ��ͨ��Ʒ�շ�ʱ�����û���Ϣ
	 */
	public ActionForward addClient(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ChargeForm cForm = (ChargeForm) form;
		Charge charge = new Charge();
		String ictPro, ictCity, ictCounty;
		String ictPhone, ictLandLine;
		String tel = "";
		try {
			ClassHelper.copyProperties(cForm, charge);
			transToString(cForm, charge);
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			charge.setIctoprcd(dto.getBsc011());
			charge.setIctgctid(dto.getBsc011());
			ictPro = charge.getIctpro() == null ? "" : charge.getIctpro()
					.split(",")[1];// ʡ��
			ictCity = charge.getIctcity() == null ? "" : charge.getIctcity()
					.split(",")[1];// ��
			ictCounty = charge.getIctcounty() == null ? "" : charge
					.getIctcounty().split(",")[1];// ��
			ictPhone = charge.getIctphone() == null ? "" : charge.getIctphone();// �ֻ�
			ictLandLine = charge.getIctlandline() == null ? "" : charge
					.getIctlandline();// �̶��绰
			/*
			 * sc.setIctpro(sc.getIctpro()==null?"":sc.getIctpro().split(",")[0])
			 * ;
			 * sc.setIctcity(sc.getIctcity()==null?"":sc.getIctcity().split(","
			 * )[0]);
			 * sc.setIctcounty(sc.getIctcounty()==null?"":sc.getIctcounty(
			 * ).split(",")[0]);
			 */
			if (ictPro != null && !"".equals(ictPro)) {
				charge.setIctaddr(ictPro + ictCity + ictCounty
						+ charge.getIctdetailaddr());
			}
			if (!"".equals(ictPhone)) {
				tel = tel + ictPhone + "    ";
			}
			if (!"".equals(ictLandLine)) {
				tel = tel + ictLandLine;
			}
			if (!"".equals(tel) && tel != null) {
				charge.setIcttel(tel);
			}
			List<Audiogram> agList = new Vector<Audiogram>();
			Audiogram lg250 = new Audiogram();
			lg250.setAdglre("L");
			lg250.setAdgtp("G");
			lg250.setAdgfq(250);
			lg250.setAdgadt(cForm.getLg250());
			agList.add(lg250);
			Audiogram lg500 = new Audiogram();
			lg500.setAdglre("L");
			lg500.setAdgtp("G");
			lg500.setAdgfq(500);
			lg500.setAdgadt(cForm.getLg500());
			agList.add(lg500);
			Audiogram lg750 = new Audiogram();
			lg750.setAdglre("L");
			lg750.setAdgtp("G");
			lg750.setAdgfq(750);
			lg750.setAdgadt(cForm.getLg750());
			agList.add(lg750);
			Audiogram lg1000 = new Audiogram();
			lg1000.setAdglre("L");
			lg1000.setAdgtp("G");
			lg1000.setAdgfq(1000);
			lg1000.setAdgadt(cForm.getLg1000());
			agList.add(lg1000);
			Audiogram lg1500 = new Audiogram();
			lg1500.setAdglre("L");
			lg1500.setAdgtp("G");
			lg1500.setAdgfq(1500);
			lg1500.setAdgadt(cForm.getLg1500());
			agList.add(lg1500);
			Audiogram lg2000 = new Audiogram();
			lg2000.setAdglre("L");
			lg2000.setAdgtp("G");
			lg2000.setAdgfq(2000);
			lg2000.setAdgadt(cForm.getLg2000());
			agList.add(lg2000);
			Audiogram lg3000 = new Audiogram();
			lg3000.setAdglre("L");
			lg3000.setAdgtp("G");
			lg3000.setAdgfq(3000);
			lg3000.setAdgadt(cForm.getLg3000());
			agList.add(lg3000);
			Audiogram lg4000 = new Audiogram();
			lg4000.setAdglre("L");
			lg4000.setAdgtp("G");
			lg4000.setAdgfq(4000);
			lg4000.setAdgadt(cForm.getLg4000());
			agList.add(lg4000);
			Audiogram lg6000 = new Audiogram();
			lg6000.setAdglre("L");
			lg6000.setAdgtp("G");
			lg6000.setAdgfq(6000);
			lg6000.setAdgadt(cForm.getLg6000());
			agList.add(lg6000);

			Audiogram lq250 = new Audiogram();
			lq250.setAdglre("L");
			lq250.setAdgtp("Q");
			lq250.setAdgfq(250);
			lq250.setAdgadt(cForm.getLq250());
			agList.add(lq250);
			Audiogram lq500 = new Audiogram();
			lq500.setAdglre("L");
			lq500.setAdgtp("Q");
			lq500.setAdgfq(500);
			lq500.setAdgadt(cForm.getLq500());
			agList.add(lq500);
			Audiogram lq750 = new Audiogram();
			lq750.setAdglre("L");
			lq750.setAdgtp("Q");
			lq750.setAdgfq(750);
			lq750.setAdgadt(cForm.getLq750());
			agList.add(lq750);
			Audiogram lq1000 = new Audiogram();
			lq1000.setAdglre("L");
			lq1000.setAdgtp("Q");
			lq1000.setAdgfq(1000);
			lq1000.setAdgadt(cForm.getLq1000());
			agList.add(lq1000);
			Audiogram lq1500 = new Audiogram();
			lq1500.setAdglre("L");
			lq1500.setAdgtp("Q");
			lq1500.setAdgfq(1500);
			lq1500.setAdgadt(cForm.getLq1500());
			agList.add(lq1500);
			Audiogram lq2000 = new Audiogram();
			lq2000.setAdglre("L");
			lq2000.setAdgtp("Q");
			lq2000.setAdgfq(2000);
			lq2000.setAdgadt(cForm.getLq2000());
			agList.add(lq2000);
			Audiogram lq3000 = new Audiogram();
			lq3000.setAdglre("L");
			lq3000.setAdgtp("Q");
			lq3000.setAdgfq(3000);
			lq3000.setAdgadt(cForm.getLq3000());
			agList.add(lq3000);
			Audiogram lq4000 = new Audiogram();
			lq4000.setAdglre("L");
			lq4000.setAdgtp("Q");
			lq4000.setAdgfq(4000);
			lq4000.setAdgadt(cForm.getLq4000());
			agList.add(lq4000);
			Audiogram lq6000 = new Audiogram();
			lq6000.setAdglre("L");
			lq6000.setAdgtp("Q");
			lq6000.setAdgfq(6000);
			lq6000.setAdgadt(cForm.getLq6000());
			agList.add(lq6000);

			Audiogram rg250 = new Audiogram();
			rg250.setAdglre("R");
			rg250.setAdgtp("G");
			rg250.setAdgfq(250);
			rg250.setAdgadt(cForm.getRg250());
			agList.add(rg250);
			Audiogram rg500 = new Audiogram();
			rg500.setAdglre("R");
			rg500.setAdgtp("G");
			rg500.setAdgfq(500);
			rg500.setAdgadt(cForm.getRg500());
			agList.add(rg500);
			Audiogram rg750 = new Audiogram();
			rg750.setAdglre("R");
			rg750.setAdgtp("G");
			rg750.setAdgfq(750);
			rg750.setAdgadt(cForm.getRg750());
			agList.add(rg750);
			Audiogram rg1000 = new Audiogram();
			rg1000.setAdglre("R");
			rg1000.setAdgtp("G");
			rg1000.setAdgfq(1000);
			rg1000.setAdgadt(cForm.getRg1000());
			agList.add(rg1000);
			Audiogram rg1500 = new Audiogram();
			rg1500.setAdglre("R");
			rg1500.setAdgtp("G");
			rg1500.setAdgfq(1500);
			rg1500.setAdgadt(cForm.getRg1500());
			agList.add(rg1500);
			Audiogram rg2000 = new Audiogram();
			rg2000.setAdglre("R");
			rg2000.setAdgtp("G");
			rg2000.setAdgfq(2000);
			rg2000.setAdgadt(cForm.getRg2000());
			agList.add(rg2000);
			Audiogram rg3000 = new Audiogram();
			rg3000.setAdglre("R");
			rg3000.setAdgtp("G");
			rg3000.setAdgfq(3000);
			rg3000.setAdgadt(cForm.getRg3000());
			agList.add(rg3000);
			Audiogram rg4000 = new Audiogram();
			rg4000.setAdglre("R");
			rg4000.setAdgtp("G");
			rg4000.setAdgfq(4000);
			rg4000.setAdgadt(cForm.getRg4000());
			agList.add(rg4000);
			Audiogram rg6000 = new Audiogram();
			rg6000.setAdglre("R");
			rg6000.setAdgtp("G");
			rg6000.setAdgfq(6000);
			rg6000.setAdgadt(cForm.getRg6000());
			agList.add(rg6000);

			Audiogram rq250 = new Audiogram();
			rq250.setAdglre("R");
			rq250.setAdgtp("Q");
			rq250.setAdgfq(250);
			rq250.setAdgadt(cForm.getRq250());
			agList.add(rq250);
			Audiogram rq500 = new Audiogram();
			rq500.setAdglre("R");
			rq500.setAdgtp("Q");
			rq500.setAdgfq(500);
			rq500.setAdgadt(cForm.getRq500());
			agList.add(rq500);
			Audiogram rq750 = new Audiogram();
			rq750.setAdglre("R");
			rq750.setAdgtp("Q");
			rq750.setAdgfq(750);
			rq750.setAdgadt(cForm.getRq750());
			agList.add(rq750);
			Audiogram rq1000 = new Audiogram();
			rq1000.setAdglre("R");
			rq1000.setAdgtp("Q");
			rq1000.setAdgfq(1000);
			rq1000.setAdgadt(cForm.getRq1000());
			agList.add(rq1000);
			Audiogram rq1500 = new Audiogram();
			rq1500.setAdglre("R");
			rq1500.setAdgtp("Q");
			rq1500.setAdgfq(1500);
			rq1500.setAdgadt(cForm.getRq1500());
			agList.add(rq1500);
			Audiogram rq2000 = new Audiogram();
			rq2000.setAdglre("R");
			rq2000.setAdgtp("Q");
			rq2000.setAdgfq(2000);
			rq2000.setAdgadt(cForm.getRq2000());
			agList.add(rq2000);
			Audiogram rq3000 = new Audiogram();
			rq3000.setAdglre("R");
			rq3000.setAdgtp("Q");
			rq3000.setAdgfq(3000);
			rq3000.setAdgadt(cForm.getRq3000());
			agList.add(rq3000);
			Audiogram rq4000 = new Audiogram();
			rq4000.setAdglre("R");
			rq4000.setAdgtp("Q");
			rq4000.setAdgfq(4000);
			rq4000.setAdgadt(cForm.getRq4000());
			agList.add(rq4000);
			Audiogram rq6000 = new Audiogram();
			rq6000.setAdglre("R");
			rq6000.setAdgtp("Q");
			rq6000.setAdgfq(6000);
			rq6000.setAdgadt(cForm.getRq6000());
			agList.add(rq6000);

			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			mapRequest.put("beo1", agList);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.addClient(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				String ictid = (String) ((HashMap) resEnv.getBody())
						.get("ictid");
				super.saveSuccessfulMsg(req, "������˿ͻ���Ϣ�ɹ�!,�ͻ�����Ϊ��" + ictid);

				// ClassHelper.copyProperties(cForm, charge);
				// req.getSession().setAttribute("ictid", ictid);
				return mapping.findForward("clientQuery");
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
	 * ������ͨ��Ʒ�շ���Ϣ
	 */
	public ActionForward saveNormalCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String ictid = req.getParameter("ictid");
		String gctid = req.getParameter("ictgctid");

		String ictaddr = req.getParameter("ictaddr");
		String ictgender = req.getParameter("ictgender");
		String ictnm = req.getParameter("ictnm");
		String folctnm = req.getParameter("folctnm");
		String phone = req.getParameter("icttel");
		String ictpnm = req.getParameter("ictpnm");
		String ncdypname = req.getParameter("ncdypname");
		String ncdnum = req.getParameter("ncdnum");
		String balance = req.getParameter("balance");
		String tp = req.getParameter("tp");
		
		SubmitDataMap data = new SubmitDataMap(req);
		Connection con = null;
		// Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			con = DBUtil.getConnection();
			// DBUtil.beginTrans(con);

			String[] idList = data.getParameterValues("pdtid"); // ��Ʒ(����)����
			String[] disList = data.getParameterValues("fdtdisc");// ��Ʒ����
			String[] qntList = data.getParameterValues("fdtqnt"); // �۳�����
			String[] prcList = data.getParameterValues("fdtrprc"); // ʵ���շ�
			String[] ntList = data.getParameterValues("chgnt"); // ��ע
			//String[] numList = data.getParameterValues("ncdnum"); //��������

			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			int size = idList.length;
			List<Charge> chgList = new Vector<Charge>();
			//
			for (int i = 0; i < size; i++) {
				Integer amount = 0;
				String pdtnm = null;
				String pdtun = null;
				String sql1 = "select distinct nvl(sum(stoamount*stoactype)over(partition by stoproid,stogrcliid),0) as temp,stoproname,stoamountun from tblsto  where isrepair IS NULL AND stoproid = '"
						+ idList[i] + "'" + " and stogrcliid='" + gctid + "'";// ��ѯ��Ʒʣ����
				List result1 = (Vector) DBUtil.querySQL(con, sql1);
				if (result1.size() > 0) {

					amount = Integer.parseInt(((HashMap) result1.get(0)).get(
							"1").toString());// ĳ��Ʒ��������Ӧ����Ʒʣ����
					pdtnm = ((HashMap) result1.get(0)).get("2").toString();// ��Ʒ����
					if (null == ((HashMap) result1.get(0)).get("3")
							|| "".equals(((HashMap) result1.get(0)).get("3")
									.toString())) {// ��Ʒ��λ
						pdtun = "��";
					} else {
						pdtun = ((HashMap) result1.get(0)).get("3").toString();
					}

				} else {
					throw new AppException("��Ʒ����Ϊ��" + idList[i] + "����Ʒ���㹻��棡");
				}

				if (amount < Integer.parseInt(qntList[i])) {
					String msg = pdtnm + "û���㹻�Ŀ������";
					throw new AppException(msg);
					// return go2Page(req, mapping, "charge");
				}
				Charge charge = new Charge();
				// charge.setChgid(chgid);
				charge.setIctnm(ictnm);
				charge.setIctid(ictid);
				charge.setStoproname(pdtnm);
				charge.setStogrcliid(gctid);
				charge.setNcdnum(Integer.parseInt(ncdnum));
				charge.setStodate(DateUtil.getDate());
				charge.setStoamountun(pdtun);
				charge.setStooprcd(dto.getBsc011());

				// charge.setNcdid(chgid);
				charge.setNcdpid(idList[i]);
				charge.setNcddis(Double.parseDouble(disList[i]));
				charge.setNcdqnt(Integer.parseInt(qntList[i]));
				//�����Ƕ�����
				String sql = "SELECT pdtcls FROM TBLPRODUCT where pdtid='" + idList[i] + "'";
				List result = (Vector) DBUtil.querySQL(con, sql);
				String pdtcls=((HashMap)result.get(0)).get("1").toString();
				
				
				/*��Աϵͳ
				 * 
				 * if(checkAcc(dto.getBsc011())&&!("0.0".equals(balance))&&"BTE".equals(pdtcls)){
					int sco=Integer.parseInt(req.getParameter("sco"));
					int fee=Integer.parseInt(req.getParameter("fee"));
					int num=Integer.parseInt(req.getParameter("num"));
					int coinnum=Integer.parseInt(req.getParameter("coinnum"));
					int money=Integer.parseInt(req.getParameter("money"));
					double mon=Double.parseDouble(prcList[i])*money/Double.parseDouble(balance);
					charge.setNcdmon(mon);
					charge.setNcdnt(ntList[i]+";����"+fee+"����ȯ"+num+"�ţ���ʹ�û���"+sco*num+",ʹ�ûݶ���"+coinnum);
				}else{
					charge.setNcdmon(Double.parseDouble(prcList[i]));
					charge.setNcdnt(ntList[i]);
				}*/
				charge.setNcdmon(Double.parseDouble(prcList[i]));
				charge.setNcdnt(ntList[i]);
				//charge.setNcdnum(Integer.parseInt(numList[i]));
				charge.setNcdsta("2");
				// charge.setNcdrecdt(DateUtil.getDate());
				charge.setNcdypname(ncdypname);
				chgList.add(charge);

			}

			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("tp", tp);
			mapRequest.put("req", req);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			ResponseEnvelop resEnv = null;
			// ���ö�Ӧ��Facadeҵ������
			resEnv = facade.save2(requestEnvelop);
				// �����ؽ��
			returnValue = processRevt(resEnv);

			/*Object[] opAddEntryArgstest=new Object[] {};
			String urltest = huier;
			String methodtest = "testreturn";
			String a = (String)sendService(opAddEntryArgstest, urltest, methodtest);
			if("true".equals(a)){
			}*/
			String chargeId;
			
			if (returnValue.isSucessFlag()) {
				// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");

				// Charge charge1 =
				// (Charge)((HashMap)resEnv.getBody()).get("dto");
				List<Charge> list = (Vector<Charge>) ((HashMap) resEnv
						.getBody()).get("dto");
				Charge charge1 = new Charge();
				charge1.setIctaddr(ictaddr);
				charge1.setIctgender(ictgender);
				charge1.setIctnm(ictnm);
				charge1.setFolctnm(folctnm);
				charge1.setIcttel(phone);
				charge1.setIctpnm(ictpnm);
				charge1.setNcdypname(ncdypname);
				charge1.setChgid(list.get(0).getChgid());
				chargeId = list.get(0).getChgid();
				ClassHelper.copyProperties(charge1, form);
				ClassHelper.copyProperties(list.get(0), charge1);
				
				
				/*��Աϵͳ
				 * 
				 * if(checkAcc(dto.getBsc011())&&!("0.0".equals(balance))){
					int sco=Integer.parseInt(req.getParameter("sco"));
					int fee=Integer.parseInt(req.getParameter("fee"));
					int num=Integer.parseInt(req.getParameter("num"));
					int coinnum=Integer.parseInt(req.getParameter("coinnum"));
					int money=Integer.parseInt(req.getParameter("money"));
					String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + ictid + "'";
					List result = (Vector) DBUtil.querySQL(con,sql);
					String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
					if(result.size()>0){
						ictcard=(String)((HashMap)result.get(0)).get("1");
						ictphone=(String)((HashMap)result.get(0)).get("2");
						if(ictphone!=null&&ictphone.length()>13){
							String [] stringArr= ictphone.split(" ");
							ictphone=stringArr[0];
						}
					}
						try {
							String url = huier;
							String method = "test";
							Object[] opAddEntryArgs=new Object[] {ictid,money+"",sco*num+"",ictnm,ictphone,list.get(0).getChgid(),""+coinnum,dto.getBsc011(),"BTE",balance};
							String score=(String)sendService(opAddEntryArgs, url, method);
							BatteryService bs=new BatteryService();
							long score1,score2,coin1;
							for(Charge cg:chgList){
								String sql0 = "SELECT pdtcls FROM TBLPRODUCT where pdtid='" + cg.getNcdpid() + "'";
								List result0 = (Vector) DBUtil.querySQL(con,sql0);
								String pdtcls=(String)((HashMap)result0.get(0)).get("1");
								if("BTE".equals(pdtcls)){
									score2=Math.round(cg.getNcdmon()/money*Double.parseDouble(score));
									score1=Math.round(cg.getNcdmon()/money*sco*num);
									coin1=Math.round(cg.getNcdmon()/money*coinnum);
									bs.insertScore(dto.getBsc011(),"","�������շѺ�"+list.get(0).getChgid()+"����"+fee+"����ȯ"+num+"�ţ���ʹ�û���"+sco*num+",ʹ�ûݶ���"+coinnum+",�շѳɹ���������"+score,cg.getNcdpid(),cg.getNcdqnt()+"",""+score1,"",""+score2,"",""+coin1);
								}
							}
						}catch(Exception e){
							super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
							return mapping.findForward("backspace");
						}
				}*/
				
				String user=(String)req.getParameter("ictid");
				String name=(String)req.getParameter("ictnm");
				String storeid=(String)req.getParameter("ictgctid");
				String gby=(String)req.getParameter("gby");
				String bm=(String)req.getParameter("bm");
				
				String update_sql = "UPDATE TBLINDCLIENT SET icttel = '"+phone+"' WHERE ictid = '"+user+"'";
				DBUtil.execSQL(con, update_sql);
				con.close();
				//�޸�  ȥ��&&!"0".equals(gby)
				if(checkAcc(dto.getBsc011())){
					try {
						String url = huier;
						String method = "testgbi";
						Object[] opAddEntryArgs=new Object[] {user,name,phone.trim(),storeid,gby,bm,null,chargeId};
						String b = (String)sendService(opAddEntryArgs, url, method);
						if("true".equals(b)){
							super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
							return mapping.findForward("norChargeDetailView");
							
						}else if("ex".equals(b)){
							super.saveSuccessfulMsg(req, "�ö����Ѿ����͵��");
							return mapping.findForward("backspace");
						}else{
							super.saveSuccessfulMsg(req, "��ӻ�Աʧ��");
							return mapping.findForward("backspace");
						}
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
				return mapping.findForward("norChargeDetailView");
				
				// return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}

		}
		// catch (AppException ex) {
		// this.saveErrors(req, ex);
		// //return mapping.findForward("backspace");
		// // return mapping.findForward("norChargeDetail");
		// String forward = "/charge/clientquery.jsp";
		// return mapping.findForward(forward);
		// }
		catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}

	/**
	 * ��������Ŀ�շ���Ϣ
	 */
	public ActionForward saveCheckCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String ictid = req.getParameter("ictid");
		String gctid = req.getParameter("ictgctid");

		String ictaddr = req.getParameter("ictaddr");
		String ictgender = req.getParameter("ictgender");
		String ictnm = req.getParameter("ictnm");
		String folctnm = req.getParameter("folctnm");
		String icttel = req.getParameter("icttel");
		String ictpnm = req.getParameter("ictpnm");
		String ncdypname = req.getParameter("ncdypname");
		String tp = req.getParameter("tp");

		SubmitDataMap data = new SubmitDataMap(req);
		Connection con = null;
		// Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			con = DBUtil.getConnection();
			// DBUtil.beginTrans(con);

			String[] idList = data.getParameterValues("pdtid"); // ��Ʒ(����)����
			//String[] disList = data.getParameterValues("fdtdisc");// ��Ʒ����
			String[] qntList = data.getParameterValues("fdtqnt"); // �۳�����
			String[] prcList = data.getParameterValues("fdtrprc"); // ʵ���շ�
			//String[] ntList = data.getParameterValues("chgnt"); // ��ע

			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			int size = idList.length;
			List<Charge> chgList = new Vector<Charge>();
			//
			for (int i = 0; i < size; i++) {
				Integer amount = 0;
				String pdtnm = null;
				String pdtun = null;
				String sql1 = "select distinct nvl(sum(stoamount*stoactype)over(partition by stoproid,stogrcliid),0) as temp,stoproname,stoamountun from tblsto  where stoproid = '"
						+ idList[i] + "'" + " and stogrcliid='" + gctid + "'";// ��ѯ��Ʒʣ����
				List result1 = (Vector) DBUtil.querySQL(con, sql1);
				if (result1.size() > 0) {

					amount = Integer.parseInt(((HashMap) result1.get(0)).get(
							"1").toString());// ĳ��Ʒ��������Ӧ����Ʒʣ����
					pdtnm = ((HashMap) result1.get(0)).get("2").toString();// ��Ʒ����
					if (null == ((HashMap) result1.get(0)).get("3")
							|| "".equals(((HashMap) result1.get(0)).get("3")
									.toString())) {// ��Ʒ��λ
						pdtun = "��";
					} else {
						pdtun = ((HashMap) result1.get(0)).get("3").toString();
					}

				} else {
					throw new AppException("��Ʒ����Ϊ��" + idList[i] + "����Ʒ���㹻��棡");
				}

				if (amount < Integer.parseInt(qntList[i])) {
					String msg = pdtnm + "û���㹻�Ŀ������";
					throw new AppException(msg);
					// return go2Page(req, mapping, "charge");
				}
				Charge charge = new Charge();
				// charge.setChgid(chgid);
				charge.setIctnm(ictnm);
				charge.setIctid(ictid);
				charge.setStoproname(pdtnm);
				charge.setStogrcliid(gctid);
				charge.setStodate(DateUtil.getDate());
				charge.setStoamountun(pdtun);
				charge.setStooprcd(dto.getBsc011());

				// charge.setNcdid(chgid);
				charge.setNcdpid(idList[i]);
				charge.setNcddis(1.0);
				charge.setNcdqnt(Integer.parseInt(qntList[i]));
				charge.setNcdmon(Double.parseDouble(prcList[i]));
				//charge.setNcdnt(ntList[i]);
				charge.setNcdsta("2");
				// charge.setNcdrecdt(DateUtil.getDate());
				charge.setNcdypname(ncdypname);
				chgList.add(charge);

			}

			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("tp", tp);
			mapRequest.put("req", req);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.save2(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");

				// Charge charge1 =
				// (Charge)((HashMap)resEnv.getBody()).get("dto");
				List<Charge> list = (Vector<Charge>) ((HashMap) resEnv
						.getBody()).get("dto");
				Charge charge1 = new Charge();
				charge1.setIctaddr(ictaddr);
				charge1.setIctgender(ictgender);
				charge1.setIctnm(ictnm);
				charge1.setFolctnm(folctnm);
				charge1.setIcttel(icttel);
				charge1.setIctpnm(ictpnm);
				charge1.setNcdypname(ncdypname);
				charge1.setChgid(list.get(0).getChgid());
				ClassHelper.copyProperties(charge1, form);
				ClassHelper.copyProperties(list.get(0), charge1);
				super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
				return mapping.findForward("cheChargeDetailView");
				// return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}

		}
		// catch (AppException ex) {
		// this.saveErrors(req, ex);
		// //return mapping.findForward("backspace");
		// // return mapping.findForward("norChargeDetail");
		// String forward = "/charge/clientquery.jsp";
		// return mapping.findForward(forward);
		// }
		catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}
	/**
	 * ����������շ���Ϣ
	 */
	public ActionForward saveErbeiCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String ictid = req.getParameter("ictid");
		String gctid = req.getParameter("ictgctid");

		String ictaddr = req.getParameter("ictaddr");
		String ictgender = req.getParameter("ictgender");
		String ictnm = req.getParameter("ictnm");
		String folctnm = req.getParameter("folctnm");
		String icttel = req.getParameter("icttel");
		String ictpnm = req.getParameter("ictpnm");
		String ncdypname = req.getParameter("ncdypname");
		String tp = req.getParameter("tp");

		SubmitDataMap data = new SubmitDataMap(req);
		Connection con = null;
		// Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			con = DBUtil.getConnection();
			// DBUtil.beginTrans(con);

			String[] idList = data.getParameterValues("pdtid"); // ��Ʒ(����)����
			String[] disList = data.getParameterValues("fdtdisc");// ��Ʒ����
			String[] qntList = data.getParameterValues("fdtqnt"); // �۳�����
			String[] prcList = data.getParameterValues("fdtrprc"); // ʵ���շ�
			String[] ntList = data.getParameterValues("chgnt"); // ��ע
			String[] jsIdList = data.getParameterValues("jsid"); // ������
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			int size = idList.length;
			List<Charge> chgList = new Vector<Charge>();
			//
			for (int i = 0; i < size; i++) {
				Integer amount = 0;
				String pdtnm = null;
				String pdtun = null;
				String sql1 = "select distinct nvl(sum(stoamount*stoactype)over(partition by stoproid,stogrcliid),0) as temp,stoproname,stoamountun from tblsto  where stoproid = '"
						+ idList[i] + "'" + " and stogrcliid='" + gctid + "'";// ��ѯ��Ʒʣ����
				List result1 = (Vector) DBUtil.querySQL(con, sql1);
				if (result1.size() > 0) {

					amount = Integer.parseInt(((HashMap) result1.get(0)).get(
							"1").toString());// ĳ��Ʒ��������Ӧ����Ʒʣ����
					pdtnm = ((HashMap) result1.get(0)).get("2").toString();// ��Ʒ����
					if (null == ((HashMap) result1.get(0)).get("3")
							|| "".equals(((HashMap) result1.get(0)).get("3")
									.toString())) {// ��Ʒ��λ
						pdtun = "��";
					} else {
						pdtun = ((HashMap) result1.get(0)).get("3").toString();
					}

				} else {
					throw new AppException("��Ʒ����Ϊ��" + idList[i] + "����Ʒ���㹻��棡");
				}

				if (amount < Integer.parseInt(qntList[i])) {
					String msg = pdtnm + "û���㹻�Ŀ������";
					throw new AppException(msg);
					// return go2Page(req, mapping, "charge");
				}
				Charge charge = new Charge();
				// charge.setChgid(chgid);
				charge.setIctnm(ictnm);
				charge.setIctid(ictid);
				charge.setStoproname(pdtnm);
				charge.setStogrcliid(gctid);
				charge.setStodate(DateUtil.getDate());
				charge.setStoamountun(pdtun);
				charge.setStooprcd(dto.getBsc011());

				// charge.setNcdid(chgid);
				charge.setNcdpid(idList[i]);
				charge.setNcddis(Double.parseDouble(disList[i]));
				charge.setNcdqnt(Integer.parseInt(qntList[i]));
				charge.setNcdmon(Double.parseDouble(prcList[i]));
				charge.setNcdnt(ntList[i]);
				charge.setJsid(jsIdList[i]);// ������
				charge.setNcdsta("2");
				// charge.setNcdrecdt(DateUtil.getDate());
				charge.setNcdypname(ncdypname);
				chgList.add(charge);

			}

			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("tp", tp);
			mapRequest.put("req", req);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.save3(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");

				// Charge charge1 =
				// (Charge)((HashMap)resEnv.getBody()).get("dto");
				List<Charge> list = (Vector<Charge>) ((HashMap) resEnv
						.getBody()).get("dto");
				Charge charge1 = new Charge();
				charge1.setIctaddr(ictaddr);
				charge1.setIctgender(ictgender);
				charge1.setIctnm(ictnm);
				charge1.setFolctnm(folctnm);
				charge1.setIcttel(icttel);
				charge1.setIctpnm(ictpnm);
				charge1.setNcdypname(ncdypname);
				charge1.setChgid(list.get(0).getChgid());
				ClassHelper.copyProperties(charge1, form);
				ClassHelper.copyProperties(list.get(0), charge1);
				super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
				return mapping.findForward("norChargeDetailView");
				// return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}

		}
		// catch (AppException ex) {
		// this.saveErrors(req, ex);
		// //return mapping.findForward("backspace");
		// // return mapping.findForward("norChargeDetail");
		// String forward = "/charge/clientquery.jsp";
		// return mapping.findForward(forward);
		// }
		catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}
	/**
	 * �����Ż��շ���Ϣ
	 */
	public ActionForward saveYouhuiCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String ictid = req.getParameter("ictid");
		String gctid = req.getParameter("ictgctid");
		String ictaddr = req.getParameter("ictaddr");
		String ictgender = req.getParameter("ictgender");
		String ictnm = req.getParameter("ictnm");
		String folctnm = req.getParameter("folctnm");
		String icttel = req.getParameter("icttel");
		String ictpnm = req.getParameter("ictpnm");
		String ncdypname = req.getParameter("ncdypname");
		String tp = req.getParameter("tp");

		SubmitDataMap data = new SubmitDataMap(req);
		Connection con = null;
		// Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			con = DBUtil.getConnection();
			// DBUtil.beginTrans(con);

			String[] idList = data.getParameterValues("pdtid"); // ��Ʒ(����)����
			String[] xhList = data.getParameterValues("pdtxh"); // �������ͺ�
			String[] youhuiList = data.getParameterValues("pdtyouhui");//�Żݶ�
			String[] yjList = data.getParameterValues("pdtyj");// ��Ʒԭ��
			String[] qntList = data.getParameterValues("fdtqnt"); // �۳�����
			String[] prcList = data.getParameterValues("fdtrprc"); // ʵ���շ�
			String[] ntList = data.getParameterValues("chgnt"); // ��ע
			String[] jsIdList = data.getParameterValues("jsid"); // ������
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			int size = idList.length;
			List<Charge> chgList = new Vector<Charge>();
			//
			for (int i = 0; i < size; i++) {
				Integer amount = 0;
				String pdtnm = null;
				String pdtun = null;
				String sql1 = "select distinct nvl(sum(stoamount*stoactype)over(partition by stoproid,stogrcliid),0) as temp,stoproname,stoamountun from tblsto  where stoproid = '"
						+ idList[i] + "'" + " and stogrcliid='" + gctid + "'";// ��ѯ��Ʒʣ����
				List result1 = (Vector) DBUtil.querySQL(con, sql1);
				if (result1.size() > 0) {

					amount = Integer.parseInt(((HashMap) result1.get(0)).get(
							"1").toString());// ĳ��Ʒ��������Ӧ����Ʒʣ����
					pdtnm = ((HashMap) result1.get(0)).get("2").toString();// ��Ʒ����
					if (null == ((HashMap) result1.get(0)).get("3")
							|| "".equals(((HashMap) result1.get(0)).get("3")
									.toString())) {// ��Ʒ��λ
						pdtun = "��";
					} else {
						pdtun = ((HashMap) result1.get(0)).get("3").toString();
					}

				} else {
					throw new AppException("��Ʒ����Ϊ��" + idList[i] + "����Ʒ���㹻��棡");
				}

				if (amount < Integer.parseInt(qntList[i])) {
					String msg = pdtnm + "û���㹻�Ŀ������";
					throw new AppException(msg);
					// return go2Page(req, mapping, "charge");
				}
				Charge charge = new Charge();
				// charge.setChgid(chgid);
				charge.setIctnm(ictnm);
				charge.setIctid(ictid);
				charge.setStoproname(pdtnm);
				charge.setStogrcliid(gctid);
				charge.setStodate(DateUtil.getDate());
				charge.setStoamountun(pdtun);
				charge.setStooprcd(dto.getBsc011());

				// charge.setNcdid(chgid);
				charge.setNcdpid(idList[i]);
				charge.setPdtxh(xhList[i]);//�ͺ�
				charge.setPdtyj(Double.parseDouble(yjList[i]));//ԭ��
				charge.setPdtyouhui(Double.parseDouble(youhuiList[i]));//�Żݶ�
				charge.setNcdqnt(Integer.parseInt(qntList[i]));//����
				charge.setNcdmon(Double.parseDouble(prcList[i]));//�ؿ��
				charge.setNcddis(1.00);
				charge.setNcdnt(ntList[i]);//��ע
				charge.setJsid(jsIdList[i]);// ������
				charge.setNcdsta("2");
				// charge.setNcdrecdt(DateUtil.getDate());
				charge.setNcdypname(ncdypname);
				chgList.add(charge);

			}

			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("tp", tp);
			mapRequest.put("req", req);
			// ��HashMap�������rsaveequestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.save4(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");

				// Charge charge1 =
				// (Charge)((HashMap)resEnv.getBody()).get("dto");
				List<Charge> list = (Vector<Charge>) ((HashMap) resEnv
						.getBody()).get("dto");
				Charge charge1 = new Charge();
				charge1.setIctaddr(ictaddr);
				charge1.setIctgender(ictgender);
				charge1.setIctnm(ictnm);
				charge1.setFolctnm(folctnm);
				charge1.setIcttel(icttel);
				charge1.setIctpnm(ictpnm);
				charge1.setNcdypname(ncdypname);
				charge1.setChgid(list.get(0).getChgid());
				ClassHelper.copyProperties(charge1, form);
				ClassHelper.copyProperties(list.get(0), charge1);
				super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
				return mapping.findForward("youhuiChargeDetailView");
				// return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}

		}
		// catch (AppException ex) {
		// this.saveErrors(req, ex);
		// //return mapping.findForward("backspace");
		// // return mapping.findForward("norChargeDetail");
		// String forward = "/charge/clientquery.jsp";
		// return mapping.findForward(forward);
		// }
		catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}

	/**
	 * ������ͨ��Ʒ�շ���Ϣ
	 */
	public ActionForward checkNormalDisc(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String gctid = req.getParameter("ictgctid");
		SubmitDataMap data = new SubmitDataMap(req);
		// Charge charge = new Charge();
		// �ж���ֱ���껹�Ǽ��˵�
		String type = "";
		Connection con = null;
		try {

			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			String sql = "select t.GCTTYPE from TBLGRPCLIENT t where t.GCTID='"
					+ gctid + "'";
			List result = (Vector) DBUtil.querySQL(con, sql);

			if (result.size() > 0) {
				// pamnt = Double.parseDouble(((HashMap)
				// result.get(0)).get("1").toString());
				type = ((HashMap) result.get(0)).get("1").toString();
			}
			System.out.println(type);
			DBUtil.commit(con);
		} catch (Exception ex) {

		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		ChargeForm cf = (ChargeForm) form;
		try {
			String[] idList = data.getParameterValues("pdtid"); // ��Ʒ(����)����
			String[] disList = data.getParameterValues("fdtdisc");// ��Ʒ����
			int size = idList.length;
			List<Charge> chgList = new Vector<Charge>();
			for (int i = 0; i < size; i++) {
				Charge charge = new Charge();
				charge.setStogrcliid(gctid);
				charge.setNcdpid(idList[i]);
				charge.setNcddis(Double.parseDouble(disList[i]));
				chgList.add(charge);
			}
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("req", req);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.checkNormalDisc(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (!type.equals("A")) {
				String jsonStr;
				jsonStr = "{tdspvo:''}";
				res.getWriter().write(jsonStr);

			} else {
				if (returnValue.isSucessFlag()) {
					res.setCharacterEncoding("GBK");
					String jsonStr = "[";
					if (null != (HashMap) resEnv.getBody()) {
						List<Charge> list = (Vector<Charge>) ((HashMap) resEnv
								.getBody()).get("dto");

						// boolean flag=false;
						if (null != list) {
							for (Charge chg : list) {
								if (null != chg.getMindisi()) {
									jsonStr += "{i:" + chg.getMindisi()
											+ ",mindis:" + chg.getTdspvo()
											+ "},";
									// flag=true;
								}
							}
							// if(flag)
							// {
							req.getSession().setAttribute("chgList", list);
							jsonStr = jsonStr
									.substring(0, jsonStr.length() - 1);
							jsonStr += "]";
							res.getWriter().write(jsonStr);

							// }
						}
					} else {
						jsonStr = "{tdspvo:''}";
						res.getWriter().write(jsonStr);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			// return mapping.findForward("backspace");
		}
		return null;

	}

	/**
	 * ��ͨ��Ʒ�շ�ʱ������ͨ��Ʒ�շ�����ҳ��
	 */
	public ActionForward saveChgid(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		String tp = req.getParameter("tp");
		try {
			ClassHelper.copyProperties(cf, charge);

			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			charge.setIctgctid(dto.getBsc011());

			charge.setChgdt(DateUtil.getDate());
			charge.setChggcltid(dto.getBsc011());
			charge.setChgcltid(charge.getIctid());
			charge.setChgid("-1");

			ClassHelper.copyProperties(charge, cf);
			if (tp.equals("normal")) {
				Connection con = DBUtil.getConnection();
				//���»ݶ��ŵ��ж�
				
				
				
				LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
				String id = dto1.getBsc011();
				
				
				/*��Աϵͳ
				 * 
				 * if(checkAcc(id)){
					String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + charge.getIctid() + "'";
					List result = (Vector) DBUtil.querySQL(con,sql);
					String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
					if(result.size()>0){
						ictcard=(String)((HashMap)result.get(0)).get("1");
						ictphone=(String)((HashMap)result.get(0)).get("2");
						if(ictphone!=null&&ictphone.length()>13){
							String [] stringArr= ictphone.split(" ");
							ictphone=stringArr[0];
						}else if(ictphone==null||ictphone.length()<=2){
							super.saveSuccessfulMsg(req, "���û�û�е绰��Ϣ�������ܳ�Ϊ֪��ϵͳ��Ա");
							return mapping.findForward("backspace");
						}
					}
						try{
							String url = huier;
							String method = "rescore";
							Object[] opAddEntryArgs=new Object[] {ictphone};
							String str=(String)sendService(opAddEntryArgs, url, method);
							String[] array= new String[3];
							array=str.split(",");
							String change=array[1]+"���ֵֿ�"+array[2]+"�ֽ�";
							req.getSession().setAttribute("change", change);
							req.getSession().setAttribute("coin", array[4]);
							req.getSession().setAttribute("rate", array[3]);
							req.getSession().setAttribute("fee", array[2]);
							req.getSession().setAttribute("sco", array[1]);
							req.getSession().setAttribute("score", array[0]);
							req.getSession().setAttribute("user", charge.getIctid());
							req.getSession().setAttribute("idcard", ictcard);
							req.getSession().setAttribute("phone", ictphone);
						}catch(Exception e){
							super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
							return mapping.findForward("backspace");
						}
				}*/
				return mapping.findForward("norChargeDetail");
			}else if (tp.equals("check")) {
				return mapping.findForward("cheChargeDetail");
			}else if (tp.equals("youhui")) {
				return mapping.findForward("youhuiChargeDetail");
			} 
			else {
				return mapping.findForward("erbeijiChargeDetail");
			}

			/*
			 * ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			 * RequestEnvelop requestEnvelop = new RequestEnvelop();
			 * EventResponse returnValue = new EventResponse(); //
			 * ��Application�������HashMap HashMap<String, Charge> mapRequest = new
			 * HashMap<String, Charge>(); mapRequest.put("beo", charge); //
			 * ��HashMap�������requestEnvelop requestEnvelop.setBody(mapRequest); //
			 * ���ö�Ӧ��Facadeҵ������ ResponseEnvelop resEnv =
			 * facade.print2(requestEnvelop); // �����ؽ�� returnValue =
			 * processRevt(resEnv); if (returnValue.isSucessFlag()) {
			 * super.saveSuccessfulMsg(req, "�����շѺųɹ�!"); Charge charge1 =
			 * (Charge) ((HashMap) resEnv.getBody()).get("beo");
			 * 
			 * ClassHelper.copyProperties(charge1, cf);
			 * 
			 * return mapping.findForward("norChargeDetail");
			 * 
			 * } else { String[] aa =
			 * StringUtil.getAsStringArray(returnValue.getMsg(), "|");
			 * super.saveErrors(req, new AppException(aa[3])); return
			 * mapping.findForward("backspace"); }
			 */
		} /*
		 * catch (AppException ex) { this.saveErrors(req, ex); return
		 * mapping.findForward("backspace"); }
		 */catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/*
	 * ��ʾ��ͨ��Ʒ�շ���Ϣ
	 */

	public ActionForward norChgRecDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		if(checkAcc(dto.getBsc011())){
			req.getSession().setAttribute("bi","t");
		}
		try {
			ClassHelper.copyProperties(cf, charge);
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			// mapRequest.put("grCli", grCli);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.norChgRecDetail(requestEnvelop);

			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List<Charge> chglist = (ArrayList<Charge>) ((HashMap) resEnv
						.getBody()).get("beo1");

				ClassHelper.copyProperties(chglist.get(0), form);

				// return mapping.findForward("norChgRecDetail");
				return new ActionForward(
						"/ChargeAction.do?method=query&charge=detail");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}

	/**
	 * ��ʾ����¼����ͨ��Ʒҳ��
	 */
	public ActionForward normalChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();

		String forward = null;
		LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		String id = dto1.getBsc011();
		/*
		 * ��Աϵͳ
		 * 
		 * if(checkAcc(id)){
			forward = "/charge/norchgdetail2.jsp";
		}else
			forward = "/charge/norchgdetail.jsp";*/
		if(checkAcc(dto1.getBsc011())){
			req.getSession().setAttribute("bi","t");
		}
		forward = "/charge/norchgdetail.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg01_007");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}

	/**
	 * �������շ�ϸ��
	 */
	public ActionForward erbeiChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();
		String forward = null;
		forward = "/charge/erbeichgdetail.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg01_007");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}
	/**
	 * �������շ�ϸ��
	 */
	public ActionForward youhuiChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();
		String forward = null;
		forward = "/charge/youhuichgdetail.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg01_007");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}

	/**
	 * ��ʾ����¼����ͨ��Ʒҳ��
	 */
	public ActionForward normalChargeDetailView(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();

		String forward = null;
		forward = "/charge/norchgdetailview.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg01_007");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}
	/**
	 * ��ʾ����¼������Ŀҳ��
	 */
	public ActionForward checkChargeDetailView(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();

		String forward = null;
		forward = "/charge/chechgdetailview.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg01_007");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}
	/**
	 * ��ʾ����¼���Ż�ҳ��
	 */
	public ActionForward youhuiChargeDetailView(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();

		String forward = null;
		forward = "/charge/youhuichgdetailview.jsp";
		try {
			ClassHelper.copyProperties(cf, charge);
			charge.setFileKey("chg04_021");
			String hql = queryEnterprise(charge);
			af = super.init(req, forward, hql);
			ClassHelper.copyProperties(charge, form);
			// super.saveSuccessfulMsg(req, "�����շ���Ϣ�ɹ�!");
		} catch (AppException ex) {
			this.saveErrors(req, ex);
		} catch (Exception e) {
			this.saveErrors(req, e);
		}
		return af;
	}

	/**
	 * ��ͨ��Ʒ�շѴ�ӡ
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String type = req.getParameter("chgid");
		ChargeForm chargeForm = (ChargeForm) form;
		Connection conn = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_norchg_new.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("chgid", chargeForm.getChgid());
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			// res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			// �Զ���ӡ
			PdfReader reader = new PdfReader(bytes);
			StringBuffer script = new StringBuffer();
			script.append(
					"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
					.append("\r\nthis.closeDoc();");
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			PdfStamper stamp = new PdfStamper(reader, bos);
			stamp.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
			stamp.addJavaScript(script.toString());
			stamp.close();
			ouputStream.write(bos.toByteArray());
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
	 * �����Ŀ�շѴ�ӡ
	 */
	public ActionForward printCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String type = req.getParameter("chgid");
		ChargeForm chargeForm = (ChargeForm) form;
		Connection conn = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_checkCharge.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("chgid", chargeForm.getChgid());
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			// res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			// �Զ���ӡ
			PdfReader reader = new PdfReader(bytes);
			StringBuffer script = new StringBuffer();
			script.append(
					"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
					.append("\r\nthis.closeDoc();");
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			PdfStamper stamp = new PdfStamper(reader, bos);
			stamp.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
			stamp.addJavaScript(script.toString());
			stamp.close();
			ouputStream.write(bos.toByteArray());
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
	 * ������Ʒ�շѴ�ӡ
	 */
	public ActionForward print2(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ChargeForm chargeForm = (ChargeForm) form;
		Connection conn = null;
		
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_cuschg_new.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("fdtfno", chargeForm.getFolno());
			parameters.put("pdtnm", chargeForm.getPdtnm());
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			// res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			// �Զ���ӡ
			PdfReader reader = new PdfReader(bytes);
			StringBuffer script = new StringBuffer();
			script.append(
					"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
					.append("\r\nthis.closeDoc();");
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			PdfStamper stamp = new PdfStamper(reader, bos);
			stamp.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
			stamp.addJavaScript(script.toString());
			stamp.close();
			ouputStream.write(bos.toByteArray());
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
	 * ��ӡά���շѵ�
	 */
	public ActionForward print3(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String type = req.getParameter("type");
		BigDecimal id; 
		if("re".equals(type)){
			ChargeForm chargeForm = (ChargeForm) form;
			id=chargeForm.getRepidentity();
		}else{
			id= new BigDecimal(req.getParameter("id"));
		}
		Connection conn = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_repchg.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("repid", id);
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			ServletOutputStream ouputStream = res.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					ouputStream);
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
	 * ���ƻ��˻���ӡ
	 */
	public ActionForward printCusRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// String type = req.getParameter("chgid");
		ChargeForm chargeForm = (ChargeForm) form;
		Connection conn = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_recchg.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("fdtfno", chargeForm.getFolno());
			parameters.put("pdtnm", chargeForm.getPdtnm());
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			// res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			// �Զ���ӡ
			PdfReader reader = new PdfReader(bytes);
			StringBuffer script = new StringBuffer();
			script.append(
					"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
					.append("\r\nthis.closeDoc();");
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			PdfStamper stamp = new PdfStamper(reader, bos);
			stamp.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
			stamp.addJavaScript(script.toString());
			stamp.close();
			ouputStream.write(bos.toByteArray());
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
	 * ��ͨ��Ʒ�˻���ӡ
	 */
	public ActionForward printNorRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// String type = req.getParameter("chgid");
		ChargeForm chargeForm = (ChargeForm) form;
		Connection conn = null;
		try {
			// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
			File reportFile = new File(req.getSession().getServletContext()
					.getRealPath("\\WEB-INF\\report\\report_norrec.jasper"));

			// ���ݱ������õ��Ĳ���ֵ
			Map<String, Object> parameters = new HashMap<String, Object>();
			// "Name"�Ǳ����ж������һ����������,������ΪString ��
			parameters.put("chgid", chargeForm.getChgid());
			// parameters.put("pdtnm", chargeForm.getPdtnm());
			// ���ӵ����ݿ�
			conn = DBUtil.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameters, conn);

			res.setContentType("application/pdf");
			// res.setContentLength(bytes.length);
			ServletOutputStream ouputStream = res.getOutputStream();
			// �Զ���ӡ
			PdfReader reader = new PdfReader(bytes);
			StringBuffer script = new StringBuffer();
			script.append(
					"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
					.append("\r\nthis.closeDoc();");
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
			PdfStamper stamp = new PdfStamper(reader, bos);
			stamp.setViewerPreferences(PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
			stamp.addJavaScript(script.toString());
			stamp.close();
			ouputStream.write(bos.toByteArray());
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
	 * ��ѯ���ƺ�ά���շѻ�����Ϣ
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String order = req.getParameter("charge");
		String chargeid = req.getParameter("chgid");
		ChargeForm cForm = (ChargeForm) form;
		String forward = null;
		ActionForward af = new ActionForward();
		Charge charge = new Charge();
		try {
			ClassHelper.copyProperties(cForm, charge);
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String grCli = dto.getBsc001();
			
			if(checkAcc(dto.getBsc011())){
				req.getSession().setAttribute("bi","t");
			}
				
			if (!"1501000000".equals(grCli)) {
				charge.setChggcltid(dto.getBsc011());
			}
			// charge.setIctgctid(dto.getBsc011());
			if ("search".equals(order)) // searchΪ������Ʒ�շѲ�ѯ
			{
				if (null == charge.getFoltype()
						|| charge.getFoltype().equals("")) {
					charge.setFileKey("chg02_007");

				} else {
					if (charge.getFoltype().equals("make")) {
						charge.setFileKey("chg02_000");
					} else if (charge.getFoltype().equals("makeEar")) {
						charge.setFileKey("chg02_006");
					} else if (charge.getFoltype().equals("makeRec")) {
						charge.setFileKey("chg02_010");
					} else {
						charge.setFileKey("chg02_011");
					}
				}
				forward = "/charge/cuschgquery.jsp";

			} else if ("repair".equals(order)) // repairΪά����Ʒ�շѲ�ѯ
			{
				forward = "/charge/repchgquery.jsp";
				if ("0".equals(charge.getFolischg())) {
					charge.setFileKey("chg03_000");
				} else {
					if ("1".equals(charge.getFolischg())) {
						charge.setFileKey("chg03_005");
					} else {
						charge.setFileKey("chg03_006");
					}
				}
			} else if ("recoil".equals(order)) {
				// String reset = req.getParameter("reset");
				// if(null!=reset&&!reset.equals(" ")&&reset.equals("true"))
				// {
				// charge.setChggcltid(null);
				// charge.setNcdid(null);
				// charge.setNcdpid(null);
				// charge.setNcdsta(null);
				// ClassHelper.copyProperties(charge, form);
				// }
				// charge.setFileKey("chg04_003");
				// forward = "/charge/recoilquery.jsp";
				charge.setFileKey("chg04_009");
				forward = "/charge/recoilquery.jsp";
			} else if ("recoilhead".equals(order)) {
				charge.setFileKey("chg04_003");
			} else if ("detail".equals(order)) {
				// String tp=req.getParameter("tp");
				// String str="";
				// if(null!=tp&&tp.equals("s"))
				// {
				// str="���˻�ֱ����";
				// }
				// super.saveSuccessfulMsg(req, str);
				charge.setFileKey("chg04_008");
				
				if(checkAcc(dto.getBsc011())){
					try{
						String url = huier;
						String method = "getbi";
						Object[] opAddEntryArgs=new Object[] {null,chargeid};
						String ss = (String)sendService(opAddEntryArgs, url, method);
						if(ss!=null&&ss.length()>0){
							String type = ss.substring(0,ss.indexOf(","));
							String num = ss.substring(ss.indexOf(",")+1,ss.length());
							req.getSession().setAttribute("type", type);
							req.getSession().setAttribute("num", num);
						}else {
							req.getSession().setAttribute("type", "");
							req.getSession().setAttribute("num", "0");
						}
						
					}catch (Exception e) {
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				
				forward = "/charge/norchgrecdetail.jsp";
			}else if ("norchgprint".equals(order)) {
				charge.setFileKey("chg04_022");
				forward = "/charge/norchgPrint.jsp";
			}
			String hql = queryEnterprise(charge);
			if ("recoilhead".equals(order)) {
				if ((null != cForm.getNcddtty() && !"".equals(cForm
						.getNcddtty()))
						&& (null != cForm.getStart() && !"".equals(cForm
								.getStart()))) {
					hql += " and " + cForm.getNcddtty() + ">=to_date('"
							+ cForm.getStart() + "','yyyy-MM-DD')";
				}
				if ((null != cForm.getNcddtty() && !"".equals(cForm
						.getNcddtty()))
						&& (null != cForm.getEnd() && !""
								.equals(cForm.getEnd()))) {
					hql += " and " + cForm.getNcddtty() + "<=to_date('"
							+ cForm.getEnd() + "','yyyy-MM-DD')";
				}
				forward = "/charge/recoilheadquery.jsp";
			} else if ("search".equals(order)) {

				if (null == charge.getFoltype()
						|| charge.getFoltype().equals("")) {
					if ((null != cForm.getFoldtty() && !"".equals(cForm
							.getFoldtty()))
							&& (null != cForm.getStart() && !"".equals(cForm
									.getStart()))) {
						hql += " and " + cForm.getFoldtty() + ">=to_date('"
								+ cForm.getStart() + "','yyyy-MM-DD')";
					}
					if ((null != cForm.getFoldtty() && !"".equals(cForm
							.getFoldtty()))
							&& (null != cForm.getEnd() && !"".equals(cForm
									.getEnd()))) {
						hql += " and " + cForm.getFoldtty() + "<=to_date('"
								+ cForm.getEnd() + "','yyyy-MM-DD')";
					}
				}
			}
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
	 * ��ѯ���˵궨�ƺ�ά���շѻ�����Ϣ
	 */
	public ActionForward jmdquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String order = req.getParameter("charge");
		ChargeForm cForm = (ChargeForm) form;
		String forward = null;
		ActionForward af = new ActionForward();
		Charge charge = new Charge();
		try {
			ClassHelper.copyProperties(cForm, charge);
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String grCli = dto.getBsc001();
			
			if(checkAcc(dto.getBsc011())){
				req.getSession().setAttribute("bi","t");
			}
			
			if (!"1501000000".equals(grCli)) {
				charge.setChggcltid(dto.getBsc011());
			}
			// charge.setIctgctid(dto.getBsc011());
			if ("search".equals(order)) // searchΪ������Ʒ�շѲ�ѯ
			{
				if (null == charge.getFoltype()
						|| charge.getFoltype().equals("")) {
					charge.setFileKey("chg021_007");

				} else {
					if (charge.getFoltype().equals("make")) {
						charge.setFileKey("chg021_000");
					} else if (charge.getFoltype().equals("makeEar")) {
						charge.setFileKey("chg021_006");
					} else if (charge.getFoltype().equals("makeRec")) {
						charge.setFileKey("chg021_010");
					} else {
						charge.setFileKey("chg021_011");
					}
				}
				forward = "/charge/cuschgqueryjmd.jsp";

			} else if ("repair".equals(order)) // repairΪά����Ʒ�շѲ�ѯ
			{
				forward = "/charge/repchgquery.jsp";
				if ("0".equals(charge.getFolischg())) {
					charge.setFileKey("chg03_000");
				} else {
					if ("1".equals(charge.getFolischg())) {
						charge.setFileKey("chg03_005");
					} else {
						charge.setFileKey("chg03_006");
					}
				}
			} else if ("recoil".equals(order)) {
				// String reset = req.getParameter("reset");
				// if(null!=reset&&!reset.equals(" ")&&reset.equals("true"))
				// {
				// charge.setChggcltid(null);
				// charge.setNcdid(null);
				// charge.setNcdpid(null);
				// charge.setNcdsta(null);
				// ClassHelper.copyProperties(charge, form);
				// }
				// charge.setFileKey("chg04_003");
				// forward = "/charge/recoilquery.jsp";
				charge.setFileKey("chg04_009");
				forward = "/charge/recoilquery.jsp";
			} else if ("recoilhead".equals(order)) {
				charge.setFileKey("chg04_003");
			} else if ("detail".equals(order)) {
				// String tp=req.getParameter("tp");
				// String str="";
				// if(null!=tp&&tp.equals("s"))
				// {
				// str="���˻�ֱ����";
				// }
				// super.saveSuccessfulMsg(req, str);
				charge.setFileKey("chg04_008");
				forward = "/charge/norchgrecdetail.jsp";
			}
			String hql = queryEnterprise(charge);
			if ("recoilhead".equals(order)) {
				if ((null != cForm.getNcddtty() && !"".equals(cForm
						.getNcddtty()))
						&& (null != cForm.getStart() && !"".equals(cForm
								.getStart()))) {
					hql += " and " + cForm.getNcddtty() + ">=to_date('"
							+ cForm.getStart() + "','yyyy-MM-DD')";
				}
				if ((null != cForm.getNcddtty() && !"".equals(cForm
						.getNcddtty()))
						&& (null != cForm.getEnd() && !""
								.equals(cForm.getEnd()))) {
					hql += " and " + cForm.getNcddtty() + "<=to_date('"
							+ cForm.getEnd() + "','yyyy-MM-DD')";
				}
				forward = "/charge/recoilheadquery.jsp";
			} else if ("search".equals(order)) {

				if (null == charge.getFoltype()
						|| charge.getFoltype().equals("")) {
					if ((null != cForm.getFoldtty() && !"".equals(cForm
							.getFoldtty()))
							&& (null != cForm.getStart() && !"".equals(cForm
									.getStart()))) {
						hql += " and " + cForm.getFoldtty() + ">=to_date('"
								+ cForm.getStart() + "','yyyy-MM-DD')";
					}
					if ((null != cForm.getFoldtty() && !"".equals(cForm
							.getFoldtty()))
							&& (null != cForm.getEnd() && !"".equals(cForm
									.getEnd()))) {
						hql += " and " + cForm.getFoldtty() + "<=to_date('"
								+ cForm.getEnd() + "','yyyy-MM-DD')";
					}
				}
			}
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
	 * ׼�����涨�ƻ��շ���Ϣ��ҳ������
	 */
	public ActionForward customizedChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		System.out.println("hello");
		String folno = req.getParameter("folno");
		String fdtcltid = req.getParameter("fdtcltid");//���˿ͻ����
		Connection con = DBUtil.getConnection();
		//���»ݶ��ŵ��ж�
		LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		String id = dto1.getBsc011();
		
		/*
		 * �ݶ�����
		 * if(checkAcc(id)){
			String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + fdtcltid + "'";
			List result = (Vector) DBUtil.querySQL(con,sql);
			String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
			if(result.size()>0){
				ictcard=(String)((HashMap)result.get(0)).get("1");
				ictphone=(String)((HashMap)result.get(0)).get("2");
				if(ictphone!=null&&ictphone.length()>13){
					String [] stringArr= ictphone.split(" ");
					ictphone=stringArr[0];
				}
			}
				try{
					String url = huier;
					String method = "rescore";
					Object[] opAddEntryArgs=new Object[] {ictphone};
					String str=(String)sendService(opAddEntryArgs, url, method);
					String[] array= new String[3];
					array=str.split(",");
					String change=array[1]+"���ֵֿ�"+array[2]+"�ֽ�";
					req.getSession().setAttribute("change", change);
					req.getSession().setAttribute("coin", array[4]);
					req.getSession().setAttribute("rate", array[3]);
					req.getSession().setAttribute("fee", array[2]);
					req.getSession().setAttribute("sco", array[1]);
					req.getSession().setAttribute("score", array[0]);
					req.getSession().setAttribute("user", fdtcltid);
					req.getSession().setAttribute("idcard", ictcard);
					req.getSession().setAttribute("phone", ictphone);
				}catch(Exception e){
					super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
					return mapping.findForward("backspace");
				}
		}*/
		
		//���ӻ�Աϵͳ����ȡ�����Ϣ
		req.getSession().setAttribute("user", fdtcltid); //��ȡ��Ա���
		String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + fdtcltid + "'";
		List result = (Vector) DBUtil.querySQL(con,sql);
		String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
		if(result.size()>0){
			ictcard=(String)((HashMap)result.get(0)).get("1");
			ictphone=(String)((HashMap)result.get(0)).get("2");
			if(ictphone!=null&&ictphone.length()>13){
				String [] stringArr= ictphone.split(" ");
				ictphone=stringArr[0];
			}
		/*v
		 * 
		 * try{
			String url = huier;
			String method = "regbi";   //���ص����Ϣ����
			Object[] opAddEntryArgs=new Object[] {ictphone};
			String str=(String)sendService(opAddEntryArgs, url, method);
			String[] array= new String[2];
			array=str.split(",");
			req.getSession().setAttribute("bm", array[1]);
			req.getSession().setAttribute("gby", array[0]);
			req.getSession().setAttribute("user", fdtcltid);
			req.getSession().setAttribute("idcard", ictcard);
			req.getSession().setAttribute("phone", ictphone);
		}catch(Exception e){
			super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
			return mapping.findForward("backspace");
		}*/
		}
		
		
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		}
		if (!("finish".equals(cf.getFolsta()))
				&& !("recback".equals(cf.getFolsta()))) {
			super.saveSuccessfulMsg(req, "�˶��ƶ������շѻ������˻����������շѣ�");// �޸ĵ��շ��жϹ���
			return mapping.findForward("backspace");
		}
		if (cf.getFolischg().equals("1")) {
			super.saveSuccessfulMsg(req, "�˶��ƶ������շѣ������ظ��շѣ�");
			return mapping.findForward("backspace");
		} else {
			try {
				ClassHelper.copyProperties(cf, charge); //
				ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");

				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
				// ��Application�������HashMap
				HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
				mapRequest.put("beo", charge);
				// ��HashMap�������requestEnvelop
				requestEnvelop.setBody(mapRequest);
				// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.show(requestEnvelop);
				// �����ؽ��
				returnValue = processRevt(resEnv);
				if (returnValue.isSucessFlag()) {
					List listci = (ArrayList) ((HashMap) resEnv.getBody())
							.get("beo");// ������Ʒ�շ���Ϣ

					ClassHelper.copyProperties(listci.get(0), cf); // ԭ����
																	// cf��chargeʱ��ֵ
																	// �����ڸĳ�cf

				} else {
					String[] aa = StringUtil.getAsStringArray(
							returnValue.getMsg(), "|");
					super.saveErrors(req, new AppException(aa[3]));
				}
			} catch (AppException ex) {
				this.saveErrors(req, ex);
			} catch (Exception e) {
				this.saveErrors(req, e);
			}
		}
		/*�ݶ�����
		 * 
		 * if(checkAcc(id))
			return mapping.findForward("cusChargeDetail2");
		else return mapping.findForward("cusChargeDetail");*/
		if(checkAcc(dto1.getBsc011())){
			req.getSession().setAttribute("bi","t");
		}
		return mapping.findForward("cusChargeDetail");
	}
	
	

	/**
	 * ����
	 * ��ͨ������¼
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward addnormember(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		if(checkAcc(dto.getBsc011())){
		try {
			ClassHelper.copyProperties(cf, charge);
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			// mapRequest.put("grCli", grCli);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.norChgRecDetail(requestEnvelop);

			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List<Charge> chglist = (ArrayList<Charge>) ((HashMap) resEnv
						.getBody()).get("beo1");

				ClassHelper.copyProperties(chglist.get(0), form);

				// return mapping.findForward("norChgRecDetail");
				return new ActionForward(
						"/ChargeAction.do?method=querynor&charge=detail");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
		}else{
			super.saveSuccessfulMsg(req, "�õ�û�п�ͨ�������");
			return mapping.findForward("backspace");
		}

	}


	/**
	 * ����
	 * ��ѯ��Ա
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward querynor(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String order = req.getParameter("charge");
		String chargeid = req.getParameter("chgid");
		ChargeForm cForm = (ChargeForm) form;
		String forward = null;
		ActionForward af = new ActionForward();
		Charge charge = new Charge();
		Connection con = null;
			
		try {
			con = DBUtil.getConnection();
			String user=null;
			String sql1 = "SELECT CHGCLTID FROM TBLNORCHG where chgid='" + chargeid + "'";
			List result1 = (Vector) DBUtil.querySQL(con,sql1);
			if(result1.size()>0){
				user=(String)((HashMap)result1.get(0)).get("1");
			}
				
			String sql = "SELECT icttel FROM TBLINDCLIENT where ictid='" + user + "'";
			List result = (Vector) DBUtil.querySQL(con,sql);
			String ictphone=null;//"0"�����ж���null�͸�ֵΪ0
			if(result.size()>0){
				ictphone=(String)((HashMap)result.get(0)).get("1");
				if(ictphone!=null&&ictphone.length()>13){
					String [] stringArr= ictphone.split(" ");
					ictphone=stringArr[0].trim();
				}
			}
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("phone", ictphone);
			
			ClassHelper.copyProperties(cForm, charge);
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String grCli = dto.getBsc001();
			if (!"1501000000".equals(grCli)) {
				charge.setChggcltid(dto.getBsc011());
			}
			if ("detail".equals(order)) {
				// String tp=req.getParameter("tp");
				// String str="";
				// if(null!=tp&&tp.equals("s"))
				// {
				// str="���˻�ֱ����";
				// }
				// super.saveSuccessfulMsg(req, str);
				charge.setFileKey("chg04_008");
				forward = "/charge/addnormem.jsp";
			}
			String hql = queryEnterprise(charge);
			
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
		con.close();
		return af;
	}
	

	/**
	 * ����
	 * ������ͨ��Ա
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNorMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		
		Connection con = null;
		try {
			con = DBUtil.getConnection();

			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			
			String chargeId;
			
				chargeId = (String)req.getParameter("chgid");
				String user=(String)req.getParameter("user");
				String name=(String)req.getParameter("ictnm");
				String storeid=dto.getBsc011();
				String gby=(String)req.getParameter("gby");
				String bm=(String)req.getParameter("bm");
				String phone=(String)req.getParameter("phone");
				
				String update_sql = "UPDATE TBLINDCLIENT SET icttel = '"+phone+"' WHERE ictid = '"+user+"'";
				DBUtil.execSQL(con, update_sql);
				con.close();
				if(checkAcc(dto.getBsc011())){
					try {
						String url = huier;
						String method = "testgbi";
						Object[] opAddEntryArgs=new Object[] {user,name,phone,storeid,gby,bm,null,chargeId};
						String b = (String)sendService(opAddEntryArgs, url, method);
						if("true".equals(b)){
							super.saveSuccessfulMsg(req, "�����Ա�ɹ�");
							return mapping.findForward("backspace");
						}else if("ex".equals(b)){
							super.saveSuccessfulMsg(req, "�ö����Ѿ����͹����");
							return mapping.findForward("backspace");
						}else{
							super.saveSuccessfulMsg(req, "�����Աʧ��");
							return mapping.findForward("backspace");
						}
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				super.saveSuccessfulMsg(req, "�õ�û�п�ͨ�������");
				return mapping.findForward("backspace");
		}
		catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}

	}

	
	
	/**
	 * ����
	 * ��Ա��¼
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward addcusmember(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		System.out.println("hello");
		String folno = req.getParameter("folno");
		String fdtcltid = req.getParameter("fdtcltid");//���˿ͻ����
		Connection con = DBUtil.getConnection();
		
		req.getSession().setAttribute("user", fdtcltid); //��ȡ��Ա���
		String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + fdtcltid + "'";
		List result = (Vector) DBUtil.querySQL(con,sql);
		String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
		if(result.size()>0){
			ictcard=(String)((HashMap)result.get(0)).get("1");
			ictphone=(String)((HashMap)result.get(0)).get("2");
			if(ictphone!=null&&ictphone.length()>13){
				String [] stringArr= ictphone.split(" ");
				ictphone=stringArr[0].trim();
			}
		}
		
		con.close();
		
		req.getSession().setAttribute("phone", ictphone); //��ȡ�ֻ���
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		}
		LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		if (!("charged".equals(cf.getFolsta()))) {
			super.saveSuccessfulMsg(req, "�������շѶ��������ܲ�¼��");// �޸ĵ��շ��жϹ���
			return mapping.findForward("backspace");
		}else if(checkAcc(dto.getBsc011())){
			try {
				ClassHelper.copyProperties(cf, charge); //
				ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");

				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
				// ��Application�������HashMap
				HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
				mapRequest.put("beo", charge);
				// ��HashMap�������requestEnvelop
				requestEnvelop.setBody(mapRequest);
				// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.show(requestEnvelop);
				// �����ؽ��
				returnValue = processRevt(resEnv);
				if (returnValue.isSucessFlag()) {
					List listci = (ArrayList) ((HashMap) resEnv.getBody())
							.get("beo");// ������Ʒ�շ���Ϣ

					ClassHelper.copyProperties(listci.get(0), cf); // ԭ����
																	// cf��chargeʱ��ֵ
																	// �����ڸĳ�cf

				} else {
					String[] aa = StringUtil.getAsStringArray(
							returnValue.getMsg(), "|");
					super.saveErrors(req, new AppException(aa[3]));
				}
			} catch (AppException ex) {
				this.saveErrors(req, ex);
			} catch (Exception e) {
				this.saveErrors(req, e);
			}
			return mapping.findForward("addcusmem");
		}else{
			super.saveSuccessfulMsg(req, "�õ�û�п�ͨ�������");
			return mapping.findForward("backspace");
		}
		
		
	}
	
	/**
	 * ����
	 * ���油¼�Ļ�Ա
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCusMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			// �����е����ݴ���ʵ���
			ClassHelper.copyProperties(cf, charge);
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;//�·��Ǵ�0��ʼ����
			int day = calendar.get(Calendar.DATE);
			charge.setChgdt(DateUtil.getDate(year, month, day));

			if ("1".equals(charge.getFolurgischg())) {
				charge.setFolurgrfee(charge.getFolurgfee());
			} else {
				double rfee = 0;
				charge.setFolurgrfee(rfee);
				charge.setFolurgischg("0");
			}
			Connection con = DBUtil.getConnection();
			LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String id = dto1.getBsc011();
			
			
			charge.setFolnt(charge.getFolnt());
			charge.setBalance(charge.getBalance());
			
			String user=(String)req.getParameter("fdtcltid");

			String gby=(String)req.getParameter("gby");
			String bm=(String)req.getParameter("bm");
			String orderId = (String)req.getParameter("folno");
			String phone = (String)req.getParameter("phone");
			
			String update_sql = "UPDATE TBLINDCLIENT SET icttel = '"+phone+"' WHERE ictid = '"+user+"'";
			DBUtil.execSQL(con, update_sql);
			con.close();
			
			con.close();
			String b;
			if(checkAcc(dto1.getBsc011())){
				try {
					String url = huier;
					String method = "testgbi";
					Object[] opAddEntryArgs=new Object[] {user,charge.getCltnm(),phone,id,gby,bm,orderId,null};
					b = (String)sendService(opAddEntryArgs, url, method);
					if ("true".equals(b)) {
						super.saveSuccessfulMsg(req, "�����Ա�ɹ�");
						return mapping.findForward("backspace");
					}else if("ex".equals(b)){
						super.saveSuccessfulMsg(req, "�����Ѿ����͵��");
						return mapping.findForward("backspace");
					}else{
						super.saveSuccessfulMsg(req, "�����Աʧ��");
						return mapping.findForward("backspace");
					}
					
				}catch(Exception e){
					super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
					return mapping.findForward("backspace");
				}
			}
			super.saveSuccessfulMsg(req, "�����Ա�ɹ�");
			return mapping.findForward("backspace");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * ׼��������˵궨�ƻ��շ���Ϣ��ҳ������
	 */
	public ActionForward jmdcustomizedChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		System.out.println("hello");
		String folno = req.getParameter("folno");
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		}
		if (!("finish".equals(cf.getFolsta()))
				&& !("recback".equals(cf.getFolsta()))) {
			super.saveSuccessfulMsg(req, "�˶��ƶ������շѻ������˻������˻����ͨ�����������շѣ�");
			return mapping.findForward("backspace");
		}
		if (cf.getFolischg().equals("1")) {
			super.saveSuccessfulMsg(req, "�˶��ƶ������շѣ������ظ��շѣ�");
			return mapping.findForward("backspace");
		} else {
			try {
				ClassHelper.copyProperties(cf, charge); //
				ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");

				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
				// ��Application�������HashMap
				HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
				mapRequest.put("beo", charge);
				// ��HashMap�������requestEnvelop
				requestEnvelop.setBody(mapRequest);
				// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.show(requestEnvelop);
				// �����ؽ��
				returnValue = processRevt(resEnv);
				if (returnValue.isSucessFlag()) {
					List listci = (ArrayList) ((HashMap) resEnv.getBody())
							.get("beo");// ������Ʒ�շ���Ϣ

					ClassHelper.copyProperties(listci.get(0), cf); // ԭ����
																	// cf��chargeʱ��ֵ
																	// �����ڸĳ�cf

				} else {
					String[] aa = StringUtil.getAsStringArray(
							returnValue.getMsg(), "|");
					super.saveErrors(req, new AppException(aa[3]));
				}
			} catch (AppException ex) {
				this.saveErrors(req, ex);
			} catch (Exception e) {
				this.saveErrors(req, e);
			}
		}
		return mapping.findForward("jmdcusChargeDetail");
	}

	/**
	 * ��ת�����ƻ��˻��˷���Ϣ��ҳ������
	 */
	public ActionForward customizedRecoilDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String folno = req.getParameter("folno");
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		}

		// if (cf.getFolischg().equals("1")){
		// super.saveSuccessfulMsg(req, "�˶��ƶ������շѣ������ظ��շѣ�");
		// return mapping.findForward("backspace");
		// }
		// else
		// {
		try {
			ClassHelper.copyProperties(cf, charge); //
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");

			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
			mapRequest.put("beo", charge);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.show1(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				List listci = (ArrayList) ((HashMap) resEnv.getBody())
						.get("beo");// ������Ʒ�շ���Ϣ

				ClassHelper.copyProperties(listci.get(0), cf); // ԭ����
																// cf��chargeʱ��ֵ
																// �����ڸĳ�cf
				// String tp=req.getParameter("tp");
				// String str="���˻�";
				// if(null!=tp && !"".equals(tp))
				// {
				// if(tp.equals("s"))
				// {
				// str+="ֱ����";
				// }
				// else if(tp.equals("c"))
				// {
				// str+="�ܲ�";
				// }
				// super.saveSuccessfulMsg(req, str);
				// return mapping.findForward("backspace");
				// }
				LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
				if(checkAcc(dto1.getBsc011())){
					req.getSession().setAttribute("bi","t");
				}
				if(checkAcc(dto1.getBsc011())){
					try{
						String url = huier;
						String method = "getbi";
						Object[] opAddEntryArgs=new Object[] {folno,null};
						String ss = (String)sendService(opAddEntryArgs, url, method);
						if(ss!=null&&ss.length()>0){
							String type = ss.substring(0,ss.indexOf(","));
							String num = ss.substring(ss.indexOf(",")+1,ss.length());
							req.getSession().setAttribute("type", type);
							req.getSession().setAttribute("num", num);
						}else {
							req.getSession().setAttribute("type", "");
							req.getSession().setAttribute("num", "0");
						}
						
					}catch (Exception e) {
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				return mapping.findForward("cusRecDetail");

			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
		} catch (AppException ex) {
			this.saveErrors(req, ex);
			return mapping.findForward("backspace");
		} catch (Exception e) {
			this.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
		// }

	}

	/**
	 * ��ʾ�˻��շ���Ϣ��ҳ������
	 */
	public ActionForward recoilChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String folno = req.getParameter("fdtfno");
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else if (cf.getFolischg().equals("1")) {
			super.saveSuccessfulMsg(req, "���˻��������շѣ������ظ��շѣ�");
			return mapping.findForward("backspace");
		} else {
			try {
				ClassHelper.copyProperties(cf, charge);
				charge.setFdtfno(folno);
				ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");

				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
				// ��Application�������HashMap
				HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
				mapRequest.put("beo", charge);
				// ��HashMap�������requestEnvelop
				requestEnvelop.setBody(mapRequest);
				// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.print3(requestEnvelop);
				// �����ؽ��
				returnValue = processRevt(resEnv);
				if (returnValue.isSucessFlag()) {
					List listci = (ArrayList) ((HashMap) resEnv.getBody())
							.get("beo");// ������Ʒ�շ���Ϣ

					ClassHelper.copyProperties(listci.get(0), cf); // ԭ����
																	// cf��chargeʱ��ֵ
																	// �����ڸĳ�cf

				} else {
					String[] aa = StringUtil.getAsStringArray(
							returnValue.getMsg(), "|");
					super.saveErrors(req, new AppException(aa[3]));
				}
			} catch (AppException ex) {
				this.saveErrors(req, ex);
			} catch (Exception e) {
				this.saveErrors(req, e);
			}
		}
		return mapping.findForward("recChargeDetail");
	}

	/**
	 * ���涨�ƻ��շ���Ϣ������view����
	 */
	public ActionForward saveCustomizedCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			// �����е����ݴ���ʵ���
			ClassHelper.copyProperties(cf, charge);
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;//�·��Ǵ�0��ʼ����
			int day = calendar.get(Calendar.DATE);
			charge.setChgdt(DateUtil.getDate(year, month, day));
			/**/
			if ("1".equals(charge.getFolurgischg())) {
				charge.setFolurgrfee(charge.getFolurgfee());
			} else {
				double rfee = 0;
				charge.setFolurgrfee(rfee);
				charge.setFolurgischg("0");
				// double balance=charge.getBalance();
				// double urgfee=charge.getFolurgfee();
				// balance=balance-urgfee;
				// charge.setBalance(balance);
			}
			Connection con = DBUtil.getConnection();
			LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String id = dto1.getBsc011();

			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			
			/*
			 * ����ϵͳ
			 * if(checkAcc(id)){
				int sco=Integer.parseInt(req.getParameter("sco"));
				String user=(String)req.getParameter("fdtcltid");
				int fee=Integer.parseInt(req.getParameter("fee"));
				int num=Integer.parseInt(req.getParameter("num"));
				int coinnum=Integer.parseInt(req.getParameter("coinnum"));
				int money=Integer.parseInt(req.getParameter("money"));
				//�����̳���Ŀ�޸ģ���ҳ��������Ӧ�ս�����balance�ֶ�,����update
				charge.setFolnt(charge.getFolnt()+";����"+fee+"����ȯ"+num+"�ţ���ʹ�û���"+sco*num+",ʹ�ûݶ���"+coinnum);
				charge.setBalance(charge.getMoney());
			}else{
				charge.setFolnt(charge.getFolnt());
				charge.setBalance(charge.getBalance());
			}*/
			
			charge.setFolnt(charge.getFolnt());
			charge.setBalance(charge.getBalance());
			
			
			
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			mapRequest.put("id", id);
			requestEnvelop.setBody(mapRequest);
			
			/*
			 * 
			 * ����ϵͳ
			 *if(checkAcc(id)){
				int sco=Integer.parseInt(req.getParameter("sco"));
				String user=(String)req.getParameter("fdtcltid");
				int fee=Integer.parseInt(req.getParameter("fee"));
				int num=Integer.parseInt(req.getParameter("num"));
				int coinnum=Integer.parseInt(req.getParameter("coinnum"));
				int money=Integer.parseInt(req.getParameter("money"));
				String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + user + "'";
				List result = (Vector) DBUtil.querySQL(con,sql);
				String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
				if(result.size()>0){
					ictcard=(String)((HashMap)result.get(0)).get("1");
					ictphone=(String)((HashMap)result.get(0)).get("2");
					if(ictphone!=null&&ictphone.length()>13){
						String [] stringArr= ictphone.split(" ");
						ictphone=stringArr[0];
					}
				}
					try {
						String url = huier;
						String method = "test";
						Object[] opAddEntryArgs=new Object[] {user,money+"",sco*num+"",charge.getCltnm(),ictphone,charge.getFolno(),""+coinnum,id,charge.getPdtid(),""};
						String score=(String)sendService(opAddEntryArgs, url, method);
						BatteryService bs=new BatteryService();
						bs.insertScore(id,"","����"+fee+"����ȯ"+num+"�ţ���ʹ�û���"+sco*num+",ʹ�ûݶ���"+coinnum+",�շѳɹ���������"+score,charge.getPdtid(),num+"",""+sco*num,"",""+score,"",""+coinnum);
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
			}*/
			
			
			
			String user=(String)req.getParameter("fdtcltid");
			String phone=(String)req.getParameter("icttel");
			
			String gby=(String)req.getParameter("gby");
			
			String bm=(String)req.getParameter("bm");
			String orderId = (String)req.getParameter("folno");
			
			String update_sql = "UPDATE TBLINDCLIENT SET icttel = '"+phone+"' WHERE ictid = '"+user+"'";
			DBUtil.execSQL(con, update_sql);
			con.close();
			ResponseEnvelop resEnv = facade.charge2(requestEnvelop);
			returnValue = processRevt(resEnv);
			//�޸�  ȥ��&&!"0".equals(gby)
			if(checkAcc(dto1.getBsc011())){
				try {
					String url = huier;
					String method = "testgbi";
					Object[] opAddEntryArgs=new Object[] {user,charge.getCltnm(),phone.trim(),id,gby,bm,orderId,null};
					String b = (String)sendService(opAddEntryArgs, url, method);
					if("true".equals(b)){
					}
					
				}catch(Exception e){
					super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
					return mapping.findForward("backspace");
				}
			}
			
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "���涨���շ���Ϣ�ɹ�");
				String folno = (String) ((HashMap) resEnv.getBody())
						.get("folno");
				// return mapping.findForward("cusPrint");
				/*
				 * ����ϵͳ
				 * 
				 * if(checkAcc(id)){
					int fee=Integer.parseInt(req.getParameter("fee"));
					int num=Integer.parseInt(req.getParameter("num"));
					int coinnum=Integer.parseInt(req.getParameter("coinnum"));
					return new ActionForward("/ChargeAction.do?method=view&folno="+ folno+"&score="+fee*num+"&coin="+coinnum);
				}else{
					return new ActionForward("/ChargeAction.do?method=view&folno="+ folno);
				}*/
				
				return new ActionForward("/ChargeAction.do?method=view&folno="+ folno);
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
	 * ���涨�ƻ��˻��˷���Ϣ������view����
	 */
	public ActionForward saveCustomizedRec(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		String tp = req.getParameter("tp");
		try {
			// �����е����ݴ���ʵ���
			ClassHelper.copyProperties(cf, charge);
			// Calendar calendar = Calendar.getInstance();
			// int year = calendar.get(Calendar.YEAR);
			// int month = calendar.get(Calendar.MONTH) + 1;
			// int day = calendar.get(Calendar.DATE);
			// charge.setChgdt(DateUtil.getDate(year, month, day));
			if(tp.equals("c"))
			{
//				super.saveSuccessfulMsg(req, "���˻��ܲ�ǰ�������˻�ֱ����");
//				return mapping.findForward("cusRecDetail");
				if("recoiledhead".equals(charge.getFolsta()))
				{
					throw new AppException("�����ظ��˻��ܲ�");
				}
				else if("finish".equals(charge.getFolsta())||"recback".equals(charge.getFolsta())||"charged".equals(charge.getFolsta()))
				{
					throw new AppException("���˻��ܲ�ǰ�������˻�ֱ����");
				}
				else if("recpass".equals(charge.getFolsta()))
				{
					throw new AppException("���ͨ�������ٴ��˻�");
				}
				
			}
			
			if(tp.equals("s"))
			{
//				super.saveSuccessfulMsg(req, "���˻��ܲ�ǰ�������˻�ֱ����");
//				return mapping.findForward("cusRecDetail");
				if("recoiled".equals(charge.getFolsta()))
				{
					throw new AppException("�����ظ��˻�ֱ����");
				}
				else if("recoiledhead".equals(charge.getFolsta()))
				{
					throw new AppException("���˻��ܲ����������˻�ֱ����");
				}
				else if("recpass".equals(charge.getFolsta()))
				{
					throw new AppException("���ͨ�������ٴ��˻�");
				}
				
			}
			// charge.setFolrecdt(DateUtil.getDate());

			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			ResponseEnvelop resEnv = null;
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			requestEnvelop.setBody(mapRequest);
				LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			if (tp.equals("s")) {
				
				//����
				//ɾ����ص����͵����Ϣ
				if(checkAcc(dto.getBsc011())){
					try {
						String orderId = req.getParameter("folno");
						String url = huier;
						String method = "testdel";
						Object[] opAddEntryArgs=new Object[] {orderId,null};
						String b = (String)sendService(opAddEntryArgs, url, method);
						if(!"true".equals(b)){
							super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
							return mapping.findForward("backspace");
						}
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				String bm = req.getParameter("bm");
				String rebatnum = req.getParameter("rebatnum");
				String bsc011 = dto.getBsc011();
				BatteryService batteryService = new BatteryService();
				//batteryService.reBat(rebatnum, bsc011, bm);
				batteryService.battChange("-"+rebatnum, bsc011, bm);
				
				// ���ö�Ӧ��Facadeҵ������
				resEnv = facade.saveCustomizedRec(requestEnvelop);
				Connection con = null;
				try {
					con = DBUtil.getConnection();
					String update_sql = "update TBLFOLDETAIL set FDTRES='"+cf.getFoldreason()+"' where FDTFNO='"+cf.getFolno()+"'";
					DBUtil.execSQL(con, update_sql);
						con.close();
				}catch (Exception e) {
					super.saveErrors(req, e);
					return mapping.findForward("backspace");
				}
				
			} else if (tp.equals("c")) {
				resEnv = facade.commitCustomizedRec(requestEnvelop);
			}
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				String str = "���˻�";
				if (tp.equals("s")) {
					str += "ֱ����";
				} else if (tp.equals("c")) {
					str += "�ܲ�";
				}
				super.saveSuccessfulMsg(req, str);
				// String folno = (String) ((HashMap) resEnv.getBody())
				// .get("folno");
				// return mapping.findForward("cusPrint");

			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (AppException ex) {
			this.saveErrors(req, ex);
			// return mapping.findForward("cusRecDetail");
			return mapping.findForward("backspace");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
		// return go2Page(req, mapping, "charge");
		return new ActionForward(
				"/ChargeAction.do?method=customizedRecoilDetail&tp=" + tp);
	}

	/**
	 * ���α�����ͨ��Ʒ�˻��˷���Ϣ������view����
	 */
	public ActionForward saveNomRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// Charge charge = new Charge();
		String tp = req.getParameter("tp");
		String chgid = req.getParameter("chgid"); // ���chgid�Ƿ�Ϊ��ֵ
		ChargeForm cf = (ChargeForm) form;
		// SubmitDataMap data = new SubmitDataMap(req);
		Collection<Charge> collection = null;
		// String[] ncdidList=data.getParameterValues("ncdid");

		// String[] ncdpidList=data.getParameterValues("pdtid");
		// // String[] ncdstaList=data.getParameterValues("ncdsta");
		// String[] ncdrntList=data.getParameterValues("ncdrnt");
		// String[] ncdrecmonList=data.getParameterValues("ncdrecmon");

		collection = TypeCast.getEntities(new SubmitDataMap(req), Charge.class);

		// String tp = req.getParameter("tp");
		// if(null==ncdstaList)
		// {
		// throw new AppException("�ύǰҪ�Ƚ��б���");
		// }
		// String[] tdspvooutList=data.getParameterValues("tdspvoout");
		// String[] tdsntList=data.getParameterValues("tdsnt");
		try {
			// List<Charge> chgList=new ArrayList<Charge>();
			// String str1="";
			// String str2="";
			// String str3="";
			// String alt1="";
			// String alt2="";
			// String alt3="";
			int i = 1;

			for (Charge chg : collection) {
				// chg.setNcdid(chgid);
				if ("s".equals(tp)) {
					if ("nomrecoiled".equals(chg.getNcdsta())) {
						throw new AppException("��Ʒ����Ϊ" + chg.getNcdpid()
								+ "����Ʒ�����ظ��˻ص�ֱ����");
					}
					if ("commited".equals(chg.getNcdsta())) {
						throw new AppException("��Ʒ����Ϊ" + chg.getNcdpid()
								+ "����Ʒ���˻ص��ܲ��������ظ��˻ص�ֱ����");
					}
					if ("pass".equals(chg.getNcdsta())) {
						throw new AppException("��Ʒ����Ϊ" + chg.getNcdpid()
								+ "����Ʒ�����ͨ���������ظ��˻ص�ֱ����");
					}
				}
				if ("c".equals(tp)) {
					if ("commited".equals(chg.getNcdsta())) {
						throw new AppException("��Ʒ����Ϊ" + chg.getNcdpid()
								+ "����Ʒ�����ظ��˻ص��ܲ�");
					}
					if (!"nomrecoiled".equals(chg.getNcdsta())) {
						throw new AppException("��Ʒ����Ϊ" + chg.getNcdpid()
								+ "����Ʒ�����˻ص��ܲ��������˻ص�ֱ����");
					}

				}

				i++;
			}

			// [start]
			// for(int i=0;i<ncdpidList.length;i++)
			// {
			// for(Charge chg:collection)
			// {
			// Charge chg=new Charge();
			// if(tp.equals("s"))
			// {
			// chg.setNcdid(cf.getChgid());
			// if(null!=ncdrntList[i] && !"".equals(ncdrntList[i]))
			// {
			// chg.setNcdrnt(Integer.parseInt(ncdrntList[i]));
			// chg.setNcdqnt(-Math.abs(Integer.parseInt(ncdrntList[i])));
			// }
			//
			// if(null!=ncdrecmonList[i] && !"".equals(ncdrecmonList[i]))
			// {
			// chg.setNcdrecmon(Double.parseDouble(ncdrecmonList[i]));
			//
			// }
			// }
			// else if(tp.equals("c"))
			// {
			// String[] ncdoidList=data.getParameterValues("ncdoid");
			// chg.setNcdoid(ncdoidList[i]);
			// }

			// [end]

			// if(!"".equals(ncdstaList[i])&&(ncdstaList[i].equals("commited")
			// || ncdstaList[i].equals("pass")))
			// {
			// // throw new AppException("��"+(i+1)+"�����ݵ�״̬���ܱ�����ύ");
			// str1+=ncdpidList[i]+",";
			//
			// }
			// if(tp.equals("s"))
			// {
			// if(!"".equals(ncdstaList[i])&&(ncdstaList[i].equals("nomrecoiled")))
			// {
			// alt1=ncdpidList[i]+",";
			// }
			// }
			// if(tp.equals("m"))
			// {
			// if(!"".equals(ncdstaList[i])&&(ncdstaList[i].equals("modifyed")))
			// {
			// alt2=ncdpidList[i]+",";
			// }
			// if(!"".equals(ncdstaList[i])&&(!ncdstaList[i].equals("nomrecoiled")))
			// {
			// str2+=ncdpidList[i]+",";
			// }
			//
			// }

			// if(tp.equals("c"))
			// {
			// if(!"".equals(ncdstaList[i])&&(ncdstaList[i].equals("commited")))
			// {
			// alt3=ncdpidList[i]+",";
			//
			// }
			// if(!"".equals(ncdstaList[i])&&(!ncdstaList[i].equals("modifyed")))
			// {
			// str3+=ncdpidList[i]+",";
			// }
			//
			// }

			// chg.setNcdid(ncdpidList[i]);
			// [start]
			// chg.setNcdpid(ncdpidList[i]);
			// chgList.add(chg);

			// }
			// [end]

			// if(!"".equals(str1))
			// {
			// str1=str1.substring(0, str1.length()-1);
			// throw new AppException("��Ʒ����Ϊ"+str1+"����Ʒ״̬�����˻ص�ֱ������ܲ�");
			// }

			// if(!"".equals(str2))
			// {
			// str2=str2.substring(0, str2.length()-1);
			// throw new
			// AppException("��Ʒ����Ϊ"+str2+"����Ʒ���޸�ǰ�������˻ص�ֱ�����\n���˻��ܲ���Ͳ������޸�");
			// }

			// if(!"".equals(str3))
			// {
			// str3=str3.substring(0, str3.length()-1);
			// throw new AppException("��Ʒ����Ϊ"+str3+"����Ʒ���˻��ܲ�ǰ�������޸��˻���");
			// }
			//
			// if(!"".equals(alt1))
			// {
			// alt1=alt1.substring(0, alt1.length()-1);
			// throw new AppException("��Ʒ����Ϊ"+alt1+"����Ʒ�����ظ��˻�ֱ����");
			// }

			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			ResponseEnvelop resEnv = null;
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			// mapRequest.put("beo", chgList);
			mapRequest.put("beo", collection);
			requestEnvelop.setBody(mapRequest);
				LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");
				String bsc011 = dto.getBsc011();
			if (tp.equals("s")) {
				//����
				//ɾ����ص����͵����Ϣ
				if(checkAcc(bsc011)){
					try {
						String url = huier;
						String method = "testdel";
						Object[] opAddEntryArgs=new Object[] {null,chgid};
						String b = (String)sendService(opAddEntryArgs, url, method);
						if(!"true".equals(b)){
							super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
							return mapping.findForward("backspace");
						}
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
				String bm = req.getParameter("bm");
				String rebatnum = req.getParameter("rebatnum");
				BatteryService batteryService = new BatteryService();
				batteryService.battChange("-"+rebatnum, bsc011, bm);//�Ķ�
				
				// ���ö�Ӧ��Facadeҵ������
				resEnv = facade.saveNomRec(requestEnvelop);
			} else if (tp.equals("c")) {

				resEnv = facade.commitNomRec(requestEnvelop);
			}
			// else if(tp.equals("m"))
			// {
			// resEnv = facade.modifyNomRec(requestEnvelop);
			// }
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				// String str="��";
				// if(tp.equals("s"))
				// {
				// str+="�˻�ֱ����";
				// }
				// else if(tp.equals("c"))
				// {
				// str+="�˻��ܲ�";
				// }
				// else if(tp.equals("m"))
				// {
				// str+="�޸�";
				// }
				// super.saveSuccessfulMsg(req, str);
				// String folno = (String) ((HashMap) resEnv.getBody())
				// .get("folno");
				
				
				
				

				if (tp.equals("s")) {
					super.saveSuccessfulMsg(req, "���˻�ֱ����");
					// return new
					// ActionForward("/ChargeAction.do?method=query&charge=detail&tp=s");
					return go2Page(req, mapping, "charge");
					// return mapping.findForward("backspace");
				}

				super.saveSuccessfulMsg(req, "���˻��ܲ�");
				return go2Page(req, mapping, "charge");

				// return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (AppException ex) {
			this.saveErrors(req, ex);
			// ActionForward actionForward = new
			// ActionForward("/ChargeAction.do?method=query&charge=recoil");
			// actionForward.setRedirect(true);
			// return actionForward;
			// return new
			// ActionForward("/ChargeAction.do?method=query&charge=recoil&reset=true");
			return mapping.findForward("backspace");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * ���α�����ͨ��Ʒ�˻��˷���Ϣ������view����
	 */
	// public ActionForward saveNomRec(ActionMapping mapping, ActionForm form,
	// HttpServletRequest req, HttpServletResponse res) throws Exception{
	// // Charge charge = new Charge();
	// ChargeForm cf = (ChargeForm)form;
	// SubmitDataMap data = new SubmitDataMap(req);
	// String[] ncdrntList=data.getParameterValues("ncdrnt");
	// String[] ncdidList=data.getParameterValues("ncdid");
	// String[] ncdstaList=data.getParameterValues("ncdsta");
	// String[] ncdpidList=data.getParameterValues("ncdpid");
	// String tp = req.getParameter("tp");
	// // if(null==ncdstaList)
	// // {
	// // throw new AppException("�ύǰҪ�Ƚ��б���");
	// // }
	// // String[] tdspvooutList=data.getParameterValues("tdspvoout");
	// // String[] tdsntList=data.getParameterValues("tdsnt");
	// try{
	// List<Charge> chgList=new ArrayList<Charge>();
	// String str1="";
	// String str2="";
	// for(int i=0;i<ncdrntList.length;i++)
	// {
	// if(!"".equals(ncdstaList[i])&&(ncdstaList[i].equals("commited") ||
	// ncdstaList[i].equals("pass")))
	// {
	// // throw new AppException("��"+(i+1)+"�����ݵ�״̬���ܱ�����ύ");
	// str1+=ncdidList[i]+",";
	//
	// }
	//
	//
	// if(tp.equals("c"))
	// {
	// if(!"".equals(ncdstaList[i])&&(!ncdstaList[i].equals("nomrecoiled")))
	// {
	// str2+=ncdidList[i]+",";
	// // throw new AppException("�շѺ�Ϊ"+ncdidList[i]+"�����ύǰҪ�Ƚ��б���");
	// }
	//
	// }
	// Charge chg=new Charge();
	// if(null!=ncdrntList[i] && !"".equals(ncdrntList[i]))
	// {
	// chg.setNcdrnt(Integer.parseInt(ncdrntList[i]));
	// }
	// chg.setNcdid(ncdidList[i]);
	// chg.setNcdpid(ncdpidList[i]);
	// chgList.add(chg);
	//
	//
	// }
	//
	// if(!"".equals(str1))
	// {
	// str1=str1.substring(0, str1.length()-1);
	// throw new AppException("�շѺ�Ϊ"+str1+"������״̬���ܱ�����ύ");
	// }
	//
	// if(!"".equals(str2))
	// {
	// str2=str2.substring(0, str2.length()-1);
	// throw new AppException("�շѺ�Ϊ"+str2+"������״̬���ܱ�����ύ");
	// }
	// //��ȡ����ӿ�
	// ChargeFacade facade = (ChargeFacade)getService("ChargeFacade");
	// RequestEnvelop requestEnvelop = new RequestEnvelop();
	// EventResponse returnValue = new EventResponse();
	// ResponseEnvelop resEnv=null;
	// // ��Application�������HashMap
	// HashMap<String, Object> mapRequest = new HashMap<String, Object>();
	// mapRequest.put("beo", chgList);
	// requestEnvelop.setBody(mapRequest);
	// if(tp.equals("s"))
	// {
	// // ���ö�Ӧ��Facadeҵ������
	// resEnv = facade.saveNomRec(requestEnvelop);
	// }
	// else if(tp.equals("c"))
	// {
	//
	// resEnv = facade.commitNomRec(requestEnvelop);
	// }
	// // �����ؽ��
	// returnValue = processRevt(resEnv);
	// if(returnValue.isSucessFlag())
	// {
	// String str="";
	// if(tp.equals("s"))
	// {
	// str="����";
	// }
	// else if(tp.equals("c"))
	// {
	// str="�ύ";
	// }
	// super.saveSuccessfulMsg(req, str+"���ƻ��˻��˷���Ϣ�ɹ�");
	// // String folno = (String) ((HashMap) resEnv.getBody())
	// // .get("folno");
	// //return mapping.findForward("cusPrint");
	//
	// return go2Page(req, mapping, "charge");
	// }
	// else {
	// String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(), "|");
	// super.saveSuccessfulMsg(req, aa[3]);
	// return mapping.findForward("backspace");
	// }
	// }
	// catch (AppException ex) {
	// this.saveErrors(req, ex);
	// // ActionForward actionForward = new
	// ActionForward("/ChargeAction.do?method=query&charge=recoil");
	// // actionForward.setRedirect(true);
	// // return actionForward;
	// return new
	// ActionForward("/ChargeAction.do?method=query&charge=recoil&reset=true");
	// }
	// catch (Exception e) {
	// super.saveErrors(req, e);
	// return mapping.findForward("backspace");
	// }
	// }

	/**
	 * ������ͨ��Ʒ�˻��˷���Ϣ������view����
	 */
	public ActionForward exaNomRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// Charge charge = new Charge();
		// ChargeForm cf = (ChargeForm)form;
		SubmitDataMap data = new SubmitDataMap(req);
		// String[] ncdrntList=data.getParameterValues("ncdrnt");
		String[] ncdrntList = data.getParameterValues("ncdrnt");
		String[] ncdoidList = data.getParameterValues("ncdid");
		String[] ncdpidList = data.getParameterValues("ncdpid");
		// if(null==ncdstaList)
		// {
		// throw new AppException("�ύǰҪ�Ƚ��б���");
		// }
		// String[] tdspvooutList=data.getParameterValues("tdspvoout");
		// String[] tdsntList=data.getParameterValues("tdsnt");
		List<Charge> chgList = new ArrayList<Charge>();
		for (int i = 0; i < ncdrntList.length; i++) {
			Charge chg = new Charge();
			if (null != ncdrntList[i] && !"".equals(ncdrntList[i])) {
				chg.setNcdrnt(Math.abs(Integer.parseInt(ncdrntList[i])));
			}
			chg.setNcdid(ncdoidList[i]);
			// chg.setNcdid(folnoList[i]);
			chg.setNcdpid(ncdpidList[i]);
			chgList.add(chg);
			// if(!"".equals(ncdstaList[i])&&ncdstaList[i].equals("finish"))
			// {
			// throw new AppException("�ύǰҪ�Ƚ��б���");
			// }

		}
		String tp = req.getParameter("tp");
		try {
			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			ResponseEnvelop resEnv = null;
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", chgList);
			mapRequest.put("tp", tp);
			requestEnvelop.setBody(mapRequest);
			// if(tp.equals("s"))
			// {
			// ���ö�Ӧ��Facadeҵ������
			resEnv = facade.exaNomRec(requestEnvelop);
			// }
			// else if(tp.equals("c"))
			// {
			// resEnv = facade.commitNomRec(requestEnvelop);
			// }
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				String str = "";
				if (tp.equals("e")) {
					str = "���ͨ����";
				} else if (tp.equals("r")) {
					str = "�ѻ��ˣ�";
				}
				super.saveSuccessfulMsg(req, str);
				// String folno = (String) ((HashMap) resEnv.getBody())
				// .get("folno");
				// return mapping.findForward("norChgRecDetail");

				return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * ���涨�ƻ��˻��˷���Ϣ������view����
	 */
	public ActionForward exaCusRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// Charge charge = new Charge();
		// ChargeForm cf = (ChargeForm)form;
		SubmitDataMap data = new SubmitDataMap(req);
		// String[] ncdrntList=data.getParameterValues("ncdrnt");
		String[] folnoList = data.getParameterValues("folno");
		String[] fdtpidList = data.getParameterValues("fdtpid");
		// Collection<Order> collection = null;
		// collection = TypeCast.getEntities(new SubmitDataMap(req),
		// Order.class);
		// for (Order od : collection) {
		// od.getFolno();
		// }
		// if(null==ncdstaList)
		// {
		// throw new AppException("�ύǰҪ�Ƚ��б���");
		// }
		// String[] tdspvooutList=data.getParameterValues("tdspvoout");
		// String[] tdsntList=data.getParameterValues("tdsnt");
		String tp = req.getParameter("tp");
		List<Order> odList = new ArrayList<Order>();
		// if("e".equals(tp))
		// {

		for (int i = 0; i < fdtpidList.length; i++) {
			Order ord = new Order();
			// if(null!=fdtpidList[i] && !"".equals(fdtpidList[i]))
			// {
			// chg.setNcdrnt(Math.abs(Integer.parseInt(fdtpidList[i])));
			// }
			// chg.setNcdid(folnoList[i]);
			// chg.setNcdpid(fdtpidList[i])
			ord.setFolno(folnoList[i]);
			ord.setFdtpid(fdtpidList[i]);
			odList.add(ord);
			// if(!"".equals(ncdstaList[i])&&ncdstaList[i].equals("finish"))
			// {
			// throw new AppException("�ύǰҪ�Ƚ��б���");
			// }

		}
		// }

		try {
			// ��ȡ����ӿ�
			// OrderFacade facade = (OrderFacade) getService("OrderFacade");
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			ResponseEnvelop resEnv = null;
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			// if("e".equals(tp))
			// {
			mapRequest.put("beo", odList);
			// }
			mapRequest.put("tp", tp);
			requestEnvelop.setBody(mapRequest);

			resEnv = facade.exaCusRec(requestEnvelop);

			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				String str = "";
				if (tp.equals("e")) {
					str = "���ͨ����";
				} else if (tp.equals("r")) {
					str = "�ѻ��ˣ�";
				}
				super.saveSuccessfulMsg(req, str);
				return go2Page(req, mapping, "charge");
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveSuccessfulMsg(req, aa[3]);
				return mapping.findForward("backspace");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}

	/**
	 * �����˻��շ���Ϣ
	 */
	public ActionForward saveRecoilCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			LoginDTO dto = (LoginDTO) req.getSession().getAttribute("LoginDTO");

			// �����е����ݴ���ʵ���
			ClassHelper.copyProperties(cf, charge);

			charge.setChgdt(DateUtil.getDate());
			charge.setDeposit(new Double(0));

			charge.setStogrcliid(dto.getBsc011());
			charge.setStoproid(cf.getPdtid());
			if (charge.getFoltype().equals("make")) {
				charge.setStoprotype("4");
			} else if (charge.getFoltype().equals("normal")) {
				charge.setStoprotype("2");

			}
			charge.setStoproname(cf.getPdtnm());
			charge.setStoamount(-1);
			charge.setStoamountun(cf.getPdtut());
			charge.setStoproprice(cf.getPdtprc());
			charge.setStoactype("-1");
			charge.setStoremark("�˻�");
			charge.setStooprcd(dto.getBsc011());
			charge.setStodisc("0");
			charge.setStodate(DateUtil.getDate());

			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.charge4(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "�����˻��շ���Ϣ�ɹ�");
				return mapping.findForward("recoilquery");
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
	 * ��ʾ���ƻ��շ���ϸ��Ϣ
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String folno = req.getParameter("folno");
		String score = req.getParameter("score");
		req.getSession().setAttribute("score", score);
		if (folno == null) {
			folno = (String) req.getAttribute("folno");
		}
		req.getSession().setAttribute("folno", folno);
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		if (null == folno || "".equalsIgnoreCase(folno)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else {
			ClassHelper.copyProperties(cf, charge);
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
			mapRequest.put("beo", charge);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.print(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				HashMap mapResponse = (HashMap) resEnv.getBody();
				List listci = (ArrayList) mapResponse.get("beo");
				ClassHelper.copyProperties(listci.get(0), cf);
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
			}
		}
		return mapping.findForward("view");
	}

	/**
	 * ��ʾά���շ���ϸ��Ϣ
	 */
	/*
	 * public ActionForward repview(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest req, HttpServletResponse res) throws Exception {
	 * String folno = req.getParameter("folno"); if (folno == null) { folno =
	 * (String) req.getAttribute("folno"); }
	 * req.getSession().setAttribute("folno", folno); Charge charge = new
	 * Charge(); ChargeForm cf = (ChargeForm)form; if (null == folno ||
	 * "".equalsIgnoreCase(folno)) { saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ"); }
	 * else { ClassHelper.copyProperties(cf, charge); ChargeFacade facade =
	 * (ChargeFacade) getService("ChargeFacade"); RequestEnvelop requestEnvelop
	 * = new RequestEnvelop(); EventResponse returnValue = new EventResponse();
	 * // ��Application�������HashMap HashMap<String, Charge> mapRequest = new
	 * HashMap<String, Charge>(); mapRequest.put("beo", charge); //
	 * ��HashMap�������requestEnvelop requestEnvelop.setBody(mapRequest); //
	 * ���ö�Ӧ��Facadeҵ������ ResponseEnvelop resEnv = facade.print(requestEnvelop);
	 * // �����ؽ�� returnValue = processRevt(resEnv); if
	 * (returnValue.isSucessFlag()) { HashMap mapResponse = (HashMap)
	 * resEnv.getBody(); List listci = (ArrayList) mapResponse.get("beo");
	 * ClassHelper.copyProperties(listci.get(0), cf); } else { String[] aa =
	 * StringUtil.getAsStringArray(returnValue.getMsg(), "|");
	 * super.saveErrors(req, new AppException(aa[3])); } } return
	 * mapping.findForward("repPrint"); }
	 */

	// /**
	// * ��ѯά���շѻ�����Ϣ
	// */
	// public ActionForward repairQuery(ActionMapping mapping, ActionForm form,
	// HttpServletRequest req, HttpServletResponse res) throws Exception {
	// ChargeForm cForm = (ChargeForm) form;
	// String forward = null;
	// String fileKey = null;
	//
	// fileKey = "chg03_000";
	// forward = "/charge/repchgquery.jsp";
	//
	// ActionForward af = new ActionForward();
	// Charge charge = new Charge();
	// try {
	// ClassHelper.copyProperties(cForm, charge);
	// charge.setFileKey(fileKey);
	// String hql = queryEnterprise(charge);
	// af = super.init(req, forward, hql);
	// // ����Ƿ���ڣ�
	// if (null == req.getAttribute(GlobalNames.QUERY_DATA)) {
	// String msg = "û�в�ѯ�����������ļ�¼��";
	// super.saveSuccessfulMsg(req, msg);
	// }
	// } catch (AppException ex) {
	// this.saveErrors(req, ex);
	// } catch (Exception e) {
	// this.saveErrors(req, e);
	// }
	// return af;
	// }

	/**
	 * ά���շ���Ϣ����
	 */
	public ActionForward saveRepairCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;

		try {
			// �����е����ݴ���ʵ���
			ClassHelper.copyProperties(cf, charge);
			charge.setRepchgdt(DateUtil.getDate());
			
			LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String id = dto1.getBsc011();
//			if(checkAcc(id)){
//				int coinnum=Integer.parseInt(req.getParameter("coinnum"));
//				//�����̳���Ŀ�޸ģ���ҳ��������Ӧ�ս�����balance�ֶ�,����update
//				charge.setReptip1(charge.getReptip1()+";��ʹ�ûݶ���"+coinnum);
//				charge.setRepamt(charge.getMoney());
//			}
			// ��ȡ����ӿ�
			ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", charge);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.charge3(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			
			/*if(checkAcc(id)){
				Connection con=DBUtil.getConnection();
				String user=(String)req.getParameter("user");
				int coinnum=Integer.parseInt(req.getParameter("coinnum"));
				String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + user + "'";
				List result = (Vector) DBUtil.querySQL(con,sql);
				String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
				if(result.size()>0){
					ictcard=(String)((HashMap)result.get(0)).get("1");
					ictphone=(String)((HashMap)result.get(0)).get("2");
					if(ictphone!=null&&ictphone.length()>13){
						String [] stringArr= ictphone.split(" ");
						ictphone=stringArr[0];
					}
				}
				//ά�����⣺�������»�Ա,û�еֿ۵Ļ������м�¼
				if(!"".equals(ictphone)||ictphone!=null||coinnum!=0){
					try {
						String url = huier;
						String method = "repair";
						Object[] opAddEntryArgs=new Object[] {user,ictphone,""+coinnum,id};
						String score=(String)sendService(opAddEntryArgs, url, method);
						BatteryService bs=new BatteryService();
						bs.insertScore(id,"","��ʹ�ûݶ���"+coinnum,charge.getPdtid(),"","","","","",""+coinnum);
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
				}
			}*/
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "����ά���շ���Ϣ�ɹ�");
				String folno = (String) ((HashMap) resEnv.getBody())
						.get("folno");
				return mapping.findForward("repPrint");
				// return new
				// ActionForward("/ChargeAction.do?method=repview&folno=" +
				// folno);
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
	 * ��ʾά�޻��շ�ҳ��
	 */
	public ActionForward repairChargeDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String repfolid = req.getParameter("repfolid");
		String repgctnm = req.getParameter("repgctnm");
		String repdate = req.getParameter("repdate");
		String repcltid = req.getParameter("repcltid");
		System.out.println(repgctnm);
		Charge charge = new Charge();
		System.out.println(repdate);
		ChargeForm cf = (ChargeForm) form;
		ActionForward af = new ActionForward();

		String forward = "/business/realtimequery.jsp";
		//���»ݶ��ŵ��ж�
		LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
		String id = dto1.getBsc011();
		if (null == repfolid || "".equalsIgnoreCase(repfolid)) {
			saveSuccessfulMsg(req, "����Ϊ�գ������²�ѯ");
		} else if (cf.getFolischg().equals("1")) {
			super.saveSuccessfulMsg(req, "��ά�޶������շѣ������ظ��շѣ�");
			return mapping.findForward("backspace");
		} else {
			Connection con = DBUtil.getConnection();
			/*��Աϵͳ
			 * 
			 * if(checkAcc(id)){
				String sql = "SELECT ictcard,icttel FROM TBLINDCLIENT where ictid='" + repcltid + "'";
				List result = (Vector) DBUtil.querySQL(con,sql);
				String ictcard=null,ictphone=null;//"0"�����ж���null�͸�ֵΪ0
				if(result.size()>0){
					ictcard=(String)((HashMap)result.get(0)).get("1");
					ictphone=(String)((HashMap)result.get(0)).get("2");
					if(ictphone!=null&&ictphone.length()>13){
						String [] stringArr= ictphone.split(" ");
						ictphone=stringArr[0];
					}
				}
					try{
						String url = huier;
						String method = "rescore";
						Object[] opAddEntryArgs=new Object[] {ictphone};
						String str=(String)sendService(opAddEntryArgs, url, method);
						String[] array= new String[3];
						array=str.split(",");
						req.getSession().setAttribute("coin", array[4]);
						req.getSession().setAttribute("user", repcltid);
						req.getSession().setAttribute("idcard", ictcard);
						req.getSession().setAttribute("phone", ictphone);
					}catch(Exception e){
						super.saveSuccessfulMsg(req, "���ӻ����̳�ϵͳʧ��");
						return mapping.findForward("backspace");
					}
			}*/
			try {
				ClassHelper.copyProperties(cf, charge);
				ChargeFacade facade = (ChargeFacade) getService("ChargeFacade");
				BusinessFacade facade1 = (BusinessFacade) getService("BusinessFacade");
				if (!(facade1.queryStoreType(repgctnm)).equals("A")) {
					if (repdate.compareTo("2015-04-25 23:59:59") < 0) {
						super.saveSuccessfulMsg(req, "���˵���ǰ�Ķ��������շѣ�");
						return mapping.findForward("backspace");
					}
				}
				RequestEnvelop requestEnvelop = new RequestEnvelop();
				EventResponse returnValue = new EventResponse();
				// ��Application�������HashMap
				HashMap<String, Charge> mapRequest = new HashMap<String, Charge>();
				mapRequest.put("beo", charge);
				// ��HashMap�������requestEnvelop
				requestEnvelop.setBody(mapRequest);
				// ���ö�Ӧ��Facadeҵ������
				ResponseEnvelop resEnv = facade.print1(requestEnvelop);
				// �����ؽ��
				returnValue = processRevt(resEnv);
				if (returnValue.isSucessFlag()) {
					List listci = (ArrayList) ((HashMap) resEnv.getBody())
							.get("beo");
					ClassHelper.copyProperties(listci.get(0), cf);

				} else {
					String[] aa = StringUtil.getAsStringArray(
							returnValue.getMsg(), "|");
					super.saveErrors(req, new AppException(aa[3]));
					return mapping.findForward("backspace");
				}
			} catch (Exception e) {
				super.saveErrors(req, e);
				return mapping.findForward("backspace");
			}
		}
		/*if(checkAcc(id)){
			return mapping.findForward("repChargeDetail2");
		}else
			return mapping.findForward("repChargeDetail");*/
		return mapping.findForward("repChargeDetail");
	}

	public void transToString(ChargeForm form, Charge sc) {
		sc.setGumo(trans(form.getGumo()));
		sc.setJiancha(trans(form.getJiancha()));
		sc.setChuandao(trans(form.getChuandao()));
		sc.setGanyin(trans(form.getGanyin()));
		sc.setChuli(trans(form.getChuli()));
	}

	public String trans(String[] str) {
		if (str != null && str.length > 0) {
			String result = "";
			for (int i = 0; i < str.length; i++) {
				result += str[i] + ",";
			}
			return result;
		} else
			return "";
	}

	/**
	 * webservice���ͺ���
	 * @param opAddEntryArgs
	 * @param url
	 * @param method
	 * @return
	 */
	public static String sendService(Object[] opAddEntryArgs,String url,String method){
		String result=null;
		try {
			
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options =serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			 QName opAddEntry = new QName("http://webservice.hdu.edu.cn",method);
			 //list.toArray(a)
			// Object[] opAddEntryArgs = new Object[] {user};
			 Class[] classes = new Class[] { String.class };
			 result=(String)serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];
			 System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * ���ͻ�Ա���������̳ǣ����ػ�Ա���֣���ת������ҳ�棬ѡ��۳����ٻ��֣��»�Ա�½�
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward testws(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {

			String user=(String)req.getParameter("user");
			String url = "http://10.1.18.24:8080/huiermall/services/testwebservice";
			String method = "rescore";
			Object[] opAddEntryArgs=new Object[] {user};
			String str=(String)sendService(opAddEntryArgs, url, method);
			String[] array= new String[3];
			array=str.split(",");
			String change=array[1]+"���ֵֿ�"+array[2]+"�ֽ�";
			req.getSession().setAttribute("change", change);
			req.getSession().setAttribute("rate", array[3]);
			req.getSession().setAttribute("fee", array[2]);
			req.getSession().setAttribute("sco", array[1]);
			req.getSession().setAttribute("score", array[0]);
			req.getSession().setAttribute("user", user);
			super.saveSuccessfulMsg(req, "�ɹ�");
			return mapping.findForward("testSendMemberno");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}
	
	/**
	 * �շѣ��۳����֣������շ����͵Ļ��֣������»��֣��»�Ա���½��˺�
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward testCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {
			int sco=Integer.parseInt(req.getParameter("sco"));
			String user=(String)req.getParameter("user");
			String fee=(String)req.getParameter("fee");
			int num=Integer.parseInt(req.getParameter("num"));
			//if(num==0) num=0;
			String money=(String)req.getParameter("money");
			String url = "http://10.1.18.24:8080/huiermall/services/testwebservice";
			String method = "test";
			Object[] opAddEntryArgs=new Object[] {user,money,sco*num+""};
			String score=(String)sendService(opAddEntryArgs, url, method);
			LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String id = dto1.getBsc011();
			BatteryService bs=new BatteryService();
			//bs.insertScore(id, ""+sco*num,"����"+fee+"����ȯ���Ļ���","",num+"");
			//bs.insertScore(id, "-"+score,"�շѳɹ���������","","");
			//super.saveSuccessfulMsg(req, "���ͳɹ�");
			return mapping.findForward("testSendMemberno");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}
	
	/**
	 * �շѣ��ڻݶ���Աϵͳ��������͵����Ϣ���»�Ա���½��˺�
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward testChargeNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {
			String user=(String)req.getParameter("user");
			String gby=(String)req.getParameter("gby");
			String bm=(String)req.getParameter("bm");
			String url = huier;
			String method = "test"; //����»�Ա
			Object[] opAddEntryArgs=new Object[] {gby,bm};
			String score=(String)sendService(opAddEntryArgs, url, method);
			LoginDTO dto1 = (LoginDTO) req.getSession().getAttribute("LoginDTO");
			String id = dto1.getBsc011();
			BatteryService bs=new BatteryService();
			//bs.insertScore(id, ""+sco*num,"����"+fee+"����ȯ���Ļ���","",num+"");
			//bs.insertScore(id, "-"+score,"�շѳɹ���������","","");
			//super.saveSuccessfulMsg(req, "���ͳɹ�");
			return mapping.findForward("testSendMemberno");
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
	}
	/**
	 * �鿴�����ŵ��б�
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		String forward1 = "/charge/testsearch.jsp";
		ActionForward af = new ActionForward();
		try {
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			String id = dto1.getBsc011();
			Connection con = DBUtil.getConnection();
			ClassHelper.copyProperties(cf, charge);
			{
				charge.setFileKey("score_search");
				String hql = queryEnterprise(charge);
				af = super.init(req, forward1, hql);
			}
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
	/**�鿴�ŵ�����¼���ϸ
	 * 
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		Charge charge = new Charge();
		ChargeForm cf = (ChargeForm) form;
		String type = req.getParameter("type");
		String forward1 = "/charge/detailscore.jsp";
		ActionForward af = new ActionForward();
		try {
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			String id = dto1.getBsc011();
			Connection con = DBUtil.getConnection();
			ClassHelper.copyProperties(cf, charge);
			{
				charge.setFileKey("score_detail");
				String hql = queryEnterprise(charge);
				af = super.init(req, forward1, hql);
			}
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
	 * ��ӡ���ֱ�����
	 */
	
		public ActionForward printScore(ActionMapping mapping, ActionForm form,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
			ChargeForm header = (ChargeForm) form;
			Connection conn = null;
			String bsc011=req.getParameter("bsc011");
			String year=req.getParameter("year");
			String month=req.getParameter("month");
			try {
				File reportFile = null;
				// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
				reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\report_score.jasper"));
				// ���ݱ������õ��Ĳ���ֵ
				Map<String, Object> parameters = new HashMap<String, Object>();
				// "Name"�Ǳ����ж������һ����������,������ΪString ��
				parameters.put("bsc011", header.getIvtgcltid());
				parameters.put("year", header.getIvtyear());
				parameters.put("month", header.getIvtmonth());
				// ���ӵ����ݿ�
				conn = DBUtil.getConnection();
				JasperPrint rptpnt = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);
				String reportclass = "score";
				res.setContentType("application/vnd.ms-excel"); 
				res.setHeader("content-disposition", 
				"attachment;filename=" + reportclass + ".xls");
				// res.setContentLength(bytes.length);
				ServletOutputStream ouputStream = res.getOutputStream();
				// �Զ���ӡ
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
			} finally {
				DBUtil.closeConnection(conn);
			}
			return null;
		}
		
		/**
		 * ��ͨ��Ʒ�շ�-�����Ŀ�շ�
		 */
		public ActionForward checkChargeDetail(ActionMapping mapping,
				ActionForm form, HttpServletRequest req, HttpServletResponse res)
				throws Exception {
			Charge charge = new Charge();
			ChargeForm cf = (ChargeForm) form;
			ActionForward af = new ActionForward();

			String forward = null;
			forward = "/charge/chechgdetail.jsp";
			try {
				ClassHelper.copyProperties(cf, charge);
				charge.setFileKey("chg01_007");
				String hql = queryEnterprise(charge);
				af = super.init(req, forward, hql);
				ClassHelper.copyProperties(charge, form);
			} catch (AppException ex) {
				this.saveErrors(req, ex);
			} catch (Exception e) {
				this.saveErrors(req, e);
			}
			return af;
		}
		
		/**
		 * ������Ʒ���� �ж��Ƿ��Ƕ�����
		 */
		public ActionForward queryPdtid(ActionMapping mapping, ActionForm form,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
			try {
				String idList = req.getParameter("pdtid"); // ��Ʒ(����)����
				String prcList = req.getParameter("fdtrprc");// ��Ʒ�շ�
				String [] arr1= idList.split(",");
				String [] arr2= prcList.split(",");
				Connection conn = DBUtil.getConnection();
				String jsonStr="[";
				Double num=0.0;
				for(int i=0;i<arr1.length;i++){
					String sql = "SELECT pdtcls FROM TBLPRODUCT where pdtid='" + arr1[i] + "'";
					List result = (Vector) DBUtil.querySQL(conn, sql);
					if(result.size()<1) continue;
					String pdtcls=((HashMap)result.get(0)).get("1").toString();
					if("BTE".equals(pdtcls))num+=Double.parseDouble(arr2[i]);
				}
					jsonStr+="{'num':'"+num+"'}";
					//jsonStr=jsonStr.substring(0, jsonStr.length()-1);
					jsonStr+="]";
					res.setCharacterEncoding("GBK");
					res.getWriter().write(jsonStr);
			} catch (Exception e) {
				super.saveErrors(req, e);
			}
			return null;
		}
		
		/**
		 * ������Ʒ���� �ж��Ƿ��Ƕ�����
		 */
		public ActionForward queryFdtqnt(ActionMapping mapping, ActionForm form,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
			try {
				String idList = req.getParameter("pdtid"); // ��Ʒ(����)����
				String qntList = req.getParameter("fdtqnt");// ��Ʒ�շ�
				String [] arr1= idList.split(",");
				String [] arr2= qntList.split(",");
				Connection conn = DBUtil.getConnection();
				String jsonStr="[";
				int num=0;
				for(int i=0;i<arr1.length;i++){
					String sql = "SELECT pdtcls FROM TBLPRODUCT where pdtid='" + arr1[i] + "'";
					List result = (Vector) DBUtil.querySQL(conn, sql);
					if(result.size()<1) continue;
					String pdtcls=((HashMap)result.get(0)).get("1").toString();
					if("BTE".equals(pdtcls))num+=Integer.parseInt(arr2[i]);
				}
					jsonStr+="{'num':'"+num+"'}";
					jsonStr+="]";
					res.setCharacterEncoding("GBK");
					res.getWriter().write(jsonStr);
			} catch (Exception e) {
				super.saveErrors(req, e);
			}
			return null;
		}
		
		public static boolean checkAcc(String acc){
//			String a[] = {"A0338","A0065","A0002","A0093"};
//			for(int i=0;i<a.length;i++){
//				if(a[i].equals(acc))
//				return true;
//			}
//			return false;
			
/*			if(acc.startsWith("A")){
				return true;
			}*/
			try {
				Connection conn = DBUtil.getConnection();
				String sql = "SELECT GCTTYPE FROM TBLGRPCLIENT where GCTID='" + acc + "'";
				List result = (Vector) DBUtil.querySQL(conn, sql);
				if(result.size()<1) {
					DBUtil.closeConnection(conn);
					return false;
				}
				String gctType=((HashMap)result.get(0)).get("1").toString();
				if(gctType.equals("A")||gctType.equals("C")) {
					DBUtil.closeConnection(conn);
					return true;
				}
				
			} catch (Exception e) {
				//super.saveErrors(req, e);
			}
			
			return false;
		}
		
		
		/**
		 * �������ƻ��˻����ͨ���ı��
		 */
		public ActionForward exportDingZhiVerifyPass(ActionMapping actionMapping,
				ActionForm actionForm, HttpServletRequest req,
				HttpServletResponse res) throws Exception {

			Map<String, Object> parameters = new HashMap<String, Object>();
			try {
				ReportCusReFactory pf = new ReportCusReFactory();
				List<CusReReport> data = pf.setCollection();
				JRDataSource dataSource = new JRBeanCollectionDataSource(data);
	
				File reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\cusReVerify.jasper"));
				ServletOutputStream ouputStream = res.getOutputStream();
	            String reportclass = "cusReVerify";
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
		
		/**
		 * ��ͨ��Ʒ��Ӧ���ǰ��ӡ
		 */
		public ActionForward printNorBeforeVerify(ActionMapping mapping, ActionForm form,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
			
			SubmitDataMap data = new SubmitDataMap(req);
			String[] ncdidList = data.getParameterValues("ncdid");//������
/*			String[] gctnmList = data.getParameterValues("gctnm");//����ͻ�
			String[] pdtnmList = data.getParameterValues("pdtnm");//����
			String[] ncdrntList = data.getParameterValues("ncdrnt");//����
			String[] ncdntList = data.getParameterValues("ncdnt");//��ע
*/			
			Connection conn = null;
			
			try {
				// �������֮�����ɵ�.jasper �ļ��Ĵ��λ��
				File reportFile = new File(req.getSession().getServletContext()
						.getRealPath("\\WEB-INF\\report\\printNorBeforeVerify.jasper"));

				// ���ݱ������õ��Ĳ���ֵ
				Map<String, Object> parameters = new HashMap<String, Object>();
				// "Name"�Ǳ����ж������һ����������,������ΪString ��
				parameters.put("ncdid", ncdidList[0]);
/*				parameters.put("gctnm", gctnmList[0]);
				parameters.put("pdtnm", pdtnmList[0]);
				parameters.put("ncdrnt", ncdrntList[0]);
				parameters.put("ncdnt", ncdntList[0]);*/
				// ���ӵ����ݿ�
				conn = DBUtil.getConnection();

				byte[] bytes = JasperRunManager.runReportToPdf(
						reportFile.getPath(), parameters, conn);

				res.setContentType("application/pdf");
				// res.setContentLength(bytes.length);
				ServletOutputStream ouputStream = res.getOutputStream();
				// �Զ���ӡ
				PdfReader reader = new PdfReader(bytes);
				StringBuffer script = new StringBuffer();
				script.append(
						"this.print({bUI: false,bSilent: true,bShrinkToFit: true});")
						.append("\r\nthis.closeDoc();");
				ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
				PdfStamper stamp = new PdfStamper(reader, bos);
				stamp.setViewerPreferences(PdfWriter.HideMenubar
						| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
				stamp.addJavaScript(script.toString());
				stamp.close();
				ouputStream.write(bos.toByteArray());
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
