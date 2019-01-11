package org.radf.apps.business.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.radf.apps.business.facade.BusinessFacade;
import org.radf.apps.business.form.FeeForm;
import org.radf.apps.business.form.SaleForm;
import org.radf.apps.commons.entity.Fee;
import org.radf.apps.commons.entity.Sale;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.FindLog;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;

//import com.lowagie.text.xml.xmp.DublinCoreSchema;
/**
 * ���ò���
 */
public class debugAction extends ActionLeafSupport {
	
	/**
	 * ��ת
	 * @param 
	 */
	public void enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		SaleForm saf = (SaleForm) form;
		FormFile formfile=saf.getUpfile();
		String filename=formfile.getFileName();
	/*	String year=saf.getMyear();*/
		res.setCharacterEncoding("GBK");
		PrintWriter writer=res.getWriter();
		writer.write("�ѽ��յ��ļ���"+filename+":");
		writer.flush();
		writer.close();
	}
	
	
	
	public ActionForward downDebug(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		// String sql =
		// "insert into tblres(id,attachment)values(?,EMPTY_BLOB())";
		String sql = "select filecontent,filename from tblfile where id = 44";

		try {
			con = DBUtil.getJDBCConnection();
			DBUtil.beginTrans(con);
			pstmt = con.prepareStatement(sql);
		/*	pstmt.setString(1, id);*/
			resultset = pstmt.executeQuery();
			if (resultset.next()) {
				String fileName = resultset.getString(2);
				Blob blob = resultset.getBlob(1);
				InputStream ins = blob.getBinaryStream();
/*				String filenameencoder = URLEncoder.encode(fileName, "utf-8");
*/				// res.setContentType("application/unknown");
//				res.setCharacterEncoding("utf-8");
				res.setContentType("application/unknown");
				res.addHeader("Content-Disposition", "attachment; filename="
						+  new String( fileName.getBytes("gb2312"), "ISO8859-1" ) );
				OutputStream outStream = res.getOutputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = ins.read(bytes)) != -1) {
					outStream.write(bytes, 0, len);
				}
				ins.close();
				outStream.close();
				outStream = null;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return null;
	}
	/**
	 * �������
	 * @param mappi
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward savesale(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Sale sa = new Sale();
		SaleForm saf = (SaleForm) form;
		try {
			ClassHelper.copyProperties(saf, sa);
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			sa.setMop(dto1.getBsc011());
			sa.setMopdt(DateUtil.getSystemCurrentTime());
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", sa);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.save(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "¼���½������Ϣ�ɹ�!");
				String gctid = (String) ((HashMap) resEnv.getBody())
						.get("mgctid");
				// ��ô�ҵ��㷵�ص���־��Ϣ
				String workString = (String) ((HashMap) resEnv.getBody())
						.get("workString");
				FindLog.insertLog(req, gctid, workString);
				return mapping.findForward("backspace");
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
	 * ¼����˵����ʱ�ע
	 */
	@SuppressWarnings("unchecked")
	public ActionForward savent(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Sale sa = new Sale();
		SaleForm saf = (SaleForm) form;
		ActionForward af = new ActionForward();
		//String forward = "/business/xiaozhang.jsp";
		try {
			ClassHelper.copyProperties(saf, sa);
			
			BusinessFacade facade = (BusinessFacade)getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			//��Application�������HashMap
			HashMap<String, Sale> mapRequest = new HashMap<String, Sale>();
			mapRequest.put("beo", sa);
			//��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			//���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.savent(requestEnvelop);
			//�����ؽ��
			returnValue = processRevt(resEnv);
			if(returnValue.isSucessFlag()){
				super.saveSuccessfulMsg(req, "¼�����ʱ�ע�ɹ���");
				return go2Page(req,mapping,"business");

				//return mapping.findForward("xiaozhang");
			}
		} catch (Exception e) {
			super.saveErrors(req, e);
			return mapping.findForward("backspace");
		}
		return mapping.findForward("backspace");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		    Sale sa = new Sale();
		    SaleForm saf = (SaleForm) form;
			saf.setMgctid(req.getParameter("mgctid"));
			saf.setMyear(Integer.parseInt(req.getParameter("myear")));
			saf.setMmonth(Integer.parseInt(req.getParameter("mmonth")));
			ClassHelper.copyProperties(saf, sa);
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Sale> mapRequest = new HashMap<String, Sale>();
			mapRequest.put("beo", sa);
			// ��HashMap�������requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.querysale(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				HashMap mapResponse = (HashMap) resEnv.getBody();
				List listci = (ArrayList) mapResponse.get("beo");// �ͻ�������Ϣ
			
				ClassHelper.copyProperties(listci.get(0), saf);
				return mapping.findForward("edit");
				
			} else {
				String[] aa = StringUtil.getAsStringArray(returnValue.getMsg(),
						"|");
				super.saveErrors(req, new AppException(aa[3]));
				return mapping.findForward("backspace");
			}
	}	
	
	
	public ActionForward editsale(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Sale sa = new Sale();
		SaleForm saf = (SaleForm) form;
		
		try {
			ClassHelper.copyProperties(saf, sa);
			LoginDTO dto1 = (LoginDTO) req.getSession()
					.getAttribute("LoginDTO");
			sa.setMop(dto1.getBsc011());
			sa.setMopdt(DateUtil.getSystemCurrentTime());
			BusinessFacade facade = (BusinessFacade) getService("BusinessFacade");
			RequestEnvelop requestEnvelop = new RequestEnvelop();
			EventResponse returnValue = new EventResponse();
			// ��Application�������HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", sa);
			requestEnvelop.setBody(mapRequest);
			// ���ö�Ӧ��Facadeҵ������
			ResponseEnvelop resEnv = facade.edit(requestEnvelop);
			// �����ؽ��
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "�޸��½������Ϣ�ɹ�!");
				// ��ô�ҵ��㷵�ص���־��Ϣ
				FindLog.insertLog(req, sa.getMgctid(), "�޸��½����");
				return mapping.findForward("backspace");
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



}
