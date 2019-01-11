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
 * 费用补充
 */
public class debugAction extends ActionLeafSupport {
	
	/**
	 * 跳转
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
		writer.write("已接收到文件："+filename+":");
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
	 * 添加数据
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
			// 将Application对象放入HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", sa);
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.save(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "录入月结费用信息成功!");
				String gctid = (String) ((HashMap) resEnv.getBody())
						.get("mgctid");
				// 获得从业务层返回的日志信息
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
	 * 录入加盟店销帐备注
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
			//将Application对象放入HashMap
			HashMap<String, Sale> mapRequest = new HashMap<String, Sale>();
			mapRequest.put("beo", sa);
			//将HashMap对象放入requestEnvelop
			requestEnvelop.setBody(mapRequest);
			//调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.savent(requestEnvelop);
			//处理返回结果
			returnValue = processRevt(resEnv);
			if(returnValue.isSucessFlag()){
				super.saveSuccessfulMsg(req, "录入销帐备注成功！");
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
			// 将Application对象放入HashMap
			HashMap<String, Sale> mapRequest = new HashMap<String, Sale>();
			mapRequest.put("beo", sa);
			// 将HashMap对象放入requestEnvelop
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.querysale(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				HashMap mapResponse = (HashMap) resEnv.getBody();
				List listci = (ArrayList) mapResponse.get("beo");// 客户费用信息
			
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
			// 将Application对象放入HashMap
			HashMap<String, Object> mapRequest = new HashMap<String, Object>();
			mapRequest.put("beo", sa);
			requestEnvelop.setBody(mapRequest);
			// 调用对应的Facade业务处理方法
			ResponseEnvelop resEnv = facade.edit(requestEnvelop);
			// 处理返回结果
			returnValue = processRevt(resEnv);
			if (returnValue.isSucessFlag()) {
				super.saveSuccessfulMsg(req, "修改月结费用信息成功!");
				// 获得从业务层返回的日志信息
				FindLog.insertLog(req, sa.getMgctid(), "修改月结费用");
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
