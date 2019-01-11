package org.radf.apps.client.single.action;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.xml.crypto.Data;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.radf.apps.business.form.SaleForm;
import org.radf.apps.charge.facade.ChargeFacade;
import org.radf.apps.charge.form.ChargeForm;
import org.radf.apps.client.group.facade.GroupClientFacade;
import org.radf.apps.client.single.facade.SingleClientFacade;
import org.radf.apps.client.single.form.SingleClientForm;
import org.radf.apps.commons.entity.Audiogram;
import org.radf.apps.commons.entity.Charge;
import org.radf.apps.commons.entity.Customization;
import org.radf.apps.commons.entity.DebugData;
import org.radf.apps.commons.entity.Diagnose;
import org.radf.apps.commons.entity.GroupClient;
import org.radf.apps.commons.entity.Order;
import org.radf.apps.commons.entity.ReDiagnose;
import org.radf.apps.commons.entity.Repair;
import org.radf.apps.commons.entity.SingleClient;
import org.radf.apps.commons.entity.Task;
import org.radf.apps.order.form.OrderHeaderForm;
import org.radf.apps.task.facade.TaskFacade;
import org.radf.login.dto.LoginDTO;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.FileUtil;
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

import com.cm.test.CompressBook;
import com.cm.test.debugTest;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import java.text.SimpleDateFormat;

/**
 * 个人客户信息管理
 */
public class SingleClientDebugAction extends ActionLeafSupport {
	LogHelper log = new LogHelper(this.getClass().getName());

	public SingleClientDebugAction() {
	}

	public void enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		SingleClientForm saf = (SingleClientForm) form;
		FormFile formfile = saf.getUpFile();
		String filename = formfile.getFileName();
		String username = saf.getUsername();
		String huierid = saf.getHuierid();
		String jsid=saf.getJsid();
		String mac=saf.getMactype();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		String result = "";
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
					String checkfilexist="select id from tblfile where usercode='"+jsid+"' and filename='"+filename+"' and type='"+mac+"'";
					List filexistresult = (Vector) DBUtil.querySQL(conn, checkfilexist);
					//这个文件已经存在则覆盖
					if(filexistresult.size()>0){
						String id=((HashMap)filexistresult.get(0)).get("1").toString();
						byte[] bytes = formfile.getFileData();
						// DBUtil.saveClob(con,"zqb","clob1","id = "+id, id+"$" +
						// buildClob());
						 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 设置日期格式
        				    System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
						
        				    String updatetime="update tblfile set uploadtime=? where id='"+id+"'";
            				java.sql.Timestamp time = java.sql.Timestamp.valueOf(df.format(new Date()).toString()); 
                            pstmt=conn.prepareStatement(updatetime);
                            pstmt.setTimestamp(1, time);
                            pstmt.execute();
        				    String key = "id='" + id + "'";
						DBUtil.saveBlob(conn, "tblfile", "filecontent", key, bytes);
					}
				   //文件不存在
					else {
						String filecount="select * from tblfile where usercode='"+jsid +"' and type='"+mac+"'";
						List filelist = (Vector) DBUtil.querySQL(conn, filecount);
                        if(filelist.size()>=6){
                        	String querynew="delect * from tblfile where id in (select a.id from (select id from tblfile where usercode='"+jsid +"' and type='"+mac+"' order by uploadtime ASC)a where rownum=1)";
    					    pstmt=conn.prepareStatement(querynew);
    					    pstmt.executeUpdate();
                        }
                        else{
                        	
            				List debugval = (Vector) DBUtil.querySQL(conn,
            						"select SEQ_DEBUG.NEXTVAL from dual");
            				BigDecimal bigid = (BigDecimal) ((HashMap) debugval.get(0))
            						.get("1");
            				String id = bigid.toString();
            			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 设置日期格式
           				    System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            				java.sql.Timestamp time = java.sql.Timestamp.valueOf(df.format(new Date()).toString()); 
            				/*    System.out.println(f_timestamp.format(timestamp));
            				java.sql.Date time = (java.sql.Date) DateUtil.getSystemCurrentTime();*/
            		/*		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 设置日期格式
            				System.out.println(df.format(new Date()));
            				Date time=DateUtil.getSystemCurrentTime();*/
            				String sql = "insert into tblfile(id,usercode,filename,uploadtime,type) values(?,?,?,?,?)";
            				pstmt = conn.prepareStatement(sql);
            				pstmt.setString(1, id);
            				pstmt.setString(2, jsid);
            				pstmt.setString(3, filename);
            				pstmt.setTimestamp(4, time);
            				pstmt.setString(5, mac);
            				pstmt.executeUpdate();
            				byte[] bytes = formfile.getFileData();
            				// DBUtil.saveClob(con,"zqb","clob1","id = "+id, id+"$" +
            				// buildClob());
            				String key = "id='" + id + "'";
            				DBUtil.saveBlob(conn, "tblfile", "filecontent", key, bytes);
                        }
					}					    
				result = "success";
				DBUtil.commit(conn);
				
		} catch (Exception e) {
			result = e.getMessage();
		} finally {
			DBUtil.closeStatement(pstmt);
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}
		res.setCharacterEncoding("GBK");
		PrintWriter writer = res.getWriter();
		writer.write(result);
		writer.flush();
		writer.close();

	}

	public void downuserinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		SingleClientForm saf = (SingleClientForm) form;
		String username = saf.getUsername();
		String huierid = saf.getHuierid();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		String responseresult = "";
		try {
			conn=DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			String queryinfo = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.icttel from tblindclient i where i.ictid='"
					+ huierid + "' and i.ictnm='" + username + "'";
			System.out.println(queryinfo);
			List result = (Vector) DBUtil.querySQL(conn, queryinfo);
			if (result.size() >= 1) {
       				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					int i1 = 0;
					if (((HashMap) result.get(i1)).get("1") == null) {
					} else {
						map.put("m_clientno",
								((HashMap) result.get(i1)).get("1").toString());
					}
					if (((HashMap) result.get(i1)).get("2") == null) {
					} else {
						String name = ((HashMap) result.get(i1)).get("2")
								.toString();
						String lastname = name.substring(0, 1);
						String firstname = name.substring(1, name.length());
						map.put("m_firstname", lastname);
						map.put("m_lastname", firstname);
					}
					if (((HashMap) result.get(i1)).get("3") == null) {
						map.put("m_occup", "");
					} else {
						map.put("m_occup", ((HashMap) result.get(i1)).get("3")
								.toString());
					}
					if (((HashMap) result.get(i1)).get("4") == null) {
						map.put("m_sex", "1");
					} else {
						map.put("m_sex", ((HashMap) result.get(i1)).get("4")
								.toString());
					}
					if (((HashMap) result.get(i1)).get("5") == null) {
						map.put("m_birdate","");
					} else {
						map.put("m_birdate", ((HashMap) result.get(i1))
								.get("5").toString().substring(0, 10));
					}
					if (((HashMap) result.get(i1)).get("6") == null) {
						map.put("m_address", "");
					} else {
						map.put("m_address", ((HashMap) result.get(i1))
								.get("6").toString());
					}

					if (((HashMap) result.get(i1)).get("7") == null) {
						map.put("m_tel", "");
					} else {
						map.put("m_tel", ((HashMap) result.get(i1)).get("7")
								.toString());
					}
					System.out.println(map.toString());
					// userlist.add(map);
					JSONObject jsonstr = JSONObject.fromObject(map);
					responseresult = jsonstr.toString();
					System.out.println(responseresult);
				}
			else {
				responseresult="nobody";
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseresult="sqlexception";
		} finally {
			DBUtil.closeStatement(pstmt);
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}

		res.setCharacterEncoding("GBK");
		PrintWriter writer = res.getWriter();
		writer.write(responseresult);
		writer.flush();
		writer.close();
	}
	//获取用户信息
	public void getuserinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		SingleClientForm saf = (SingleClientForm) form;
		String username = saf.getUsername();
		String huierid = saf.getHuierid();
		String mactype=saf.getMactype();
		String jsid=saf.getJsid();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		String responseresult = "";
		String queryinfo;
		try {
			conn=DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			if(mactype.equals("0")){//定制机
				queryinfo = "select distinct t.ictnm,t.ictgender, t.ictbdt, t.ictaddr FROM tblindclient t LEFT OUTER JOIN tblmaking m on t.ictid = m.tmkcltid where 1=1 AND m.tmksid ='"+jsid+"' and t.ictnm='"+username+"'";
			}
			else {//耳背机
				queryinfo="SELECT i.ictnm,  i.ictgender,  i.ictbdt,  i.ictaddr FROM tblnorchg n LEFT JOIN tblnorchgdetail d ON n.chgid=d.ncdid LEFT JOIN tblindclient i on n.chgcltid  =i.ictid WHERE d.ncdjsid ='"+jsid +"' and i.ictnm='"+username+"'";
			}
			System.out.println(queryinfo);
			List result = (Vector) DBUtil.querySQL(conn, queryinfo);
			if (result.size() >= 1) {
       				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					int i1 = 0;
					
					if (((HashMap) result.get(i1)).get("1") == null) {
					} else {
						String name = ((HashMap) result.get(i1)).get("1")
								.toString();
						String lastname = name.substring(0, 1);
						String firstname = name.substring(1, name.length());
						map.put("m_firstname", lastname);
						map.put("m_lastname", firstname);
					}
					
					if (((HashMap) result.get(i1)).get("2") == null) {
						map.put("m_sex", "1");
					} else {
						map.put("m_sex", ((HashMap) result.get(i1)).get("2")
								.toString());
					}
					if (((HashMap) result.get(i1)).get("3") == null) {
						map.put("m_birdate",DateUtil.getDate().toString());
					} else {
						map.put("m_birdate", ((HashMap) result.get(i1))
								.get("3").toString().substring(0, 10));
					}
					if (((HashMap) result.get(i1)).get("4") == null) {
						map.put("m_address", "");
					} else {
						map.put("m_address", ((HashMap) result.get(i1))
								.get("4").toString());
					}
					System.out.println(map.toString());
					// userlist.add(map);
					JSONObject jsonstr = JSONObject.fromObject(map);
					responseresult = jsonstr.toString();
					System.out.println(responseresult);
				}
			else {
				responseresult="nobody";
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseresult="sqlexception";
		} finally {
			DBUtil.closeStatement(pstmt);
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}

		res.setCharacterEncoding("GBK");
		PrintWriter writer = res.getWriter();
		writer.write(responseresult);
		writer.flush();
		writer.close();
	}
	
public void downtldata(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {
	SingleClientForm saf = (SingleClientForm) form;
	/*String username = saf.getUsername();*/
	String huierid = saf.getHuierid();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	String checkuser="";
	try {
		checkuser=valiteuser(huierid);
		if(checkuser.equals("has"))
		{
			con=DBUtil.getConnection();
			LinkedHashMap<String, String> lqmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> lgmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> rqmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> rgmap = new LinkedHashMap<String, String>();
			// 查询听力复查表的听力信息
			String sqlfctldata = "SELECT adglre,  adgtp,  adgfq,  ADGADT,  adglre||ADGTP AS flag,  row_number() over (partition BY adglre,adgtp order by adgfq ) FROM  (SELECT *  FROM tblfctlgraph  WHERE fctlid IN (SELECT t.fcttlid    FROM      (SELECT FCTTLID FROM tblfctl WHERE FCTLICTID='"
					+ huierid
					+ "' ORDER BY FCTLDT DESC      ) t    where rownum<=1    ) and adgadt is not null    )";
			// 查询听力表的数据
			String sqlusertldata = "SELECT adglre,  adgtp,  adgfq,  adgadt, adglre||adgtp as flag,  row_number() over (partition BY adglre,adgtp order by adgfq )from tblaudgraph WHERE ADGCTID='"
					+ huierid + "' and adgadt is not null";
			// Map<String, LinkedHashMap<String, String>> tldatamap = new
			// HashMap<String, LinkedHashMap<String, String>>();
			List result2 = (Vector) DBUtil.querySQL(con, sqlfctldata);
			List result1 = (Vector) DBUtil.querySQL(con, sqlusertldata);
			if (result2.size() >= 1) {
				for (int i1 = 0; i1 < result2.size(); i1++) {
					String frequency = ((HashMap) result2.get(i1)).get("3")
							.toString();
					String value = ((HashMap) result2.get(i1)).get("4")
							.toString();
					String flag = ((HashMap) result2.get(i1)).get("5")
							.toString();
					int j = 0;
					if (flag.equals("LQ")) {
						j = 1;
					} else if (flag.equals("LG")) {
						j = 2;

					} else if (flag.equals("RQ")) {
						j = 3;
					} else if (flag.equals("RG")) {
						j = 4;
					}
					switch (j) {
					case 1:
						lqmap.put(frequency, value);
						break;
					case 2:
						lgmap.put(frequency, value);
						break;
					case 3:
						rqmap.put(frequency, value);
						break;
					case 4:
						rgmap.put(frequency, value);
						break;
					default:
						break;
					}
				}

			} else {
				for (int i1 = 0; i1 < result1.size(); i1++) {
					String frequency = ((HashMap) result1.get(i1)).get("3")
							.toString();
					String value = ((HashMap) result1.get(i1)).get("4")
							.toString();
					String flag = ((HashMap) result1.get(i1)).get("5")
							.toString();
					int j = 0;
					if (flag.equals("LQ")) {
						j = 1;
					} else if (flag.equals("LG")) {
						j = 2;

					} else if (flag.equals("RQ")) {
						j = 3;
					} else if (flag.equals("RG")) {
						j = 4;
					}
					switch (j) {
					case 1:
						lqmap.put(frequency, value);
						break;
					case 2:
						lgmap.put(frequency, value);
						break;
					case 3:
						rqmap.put(frequency, value);
						break;
					case 4:
						rgmap.put(frequency, value);
						break;
					default:
						break;
					}
				}
			}
			File file1 = new File("D:\\debug\\" + huierid);
			// 如果文件夹不存在则创建
			if (!file1.exists() && !file1.isDirectory()) {
				System.out.println("//不存在");
				file1.mkdir();
			} else {
				System.out.println("//目录存在");
			}
			FileWriter fw = new FileWriter(new File(file1.getPath() + "/"
					+ "tldata.dat"));
			// 写入中文字符时会出现乱码
			BufferedWriter bw = new BufferedWriter(fw);
			// BufferedWriter bw=new BufferedWriter(new BufferedWriter(new
			// OutputStreamWriter(new FileOutputStream(new
			// File("E:/phsftp/evdokey/evdokey_201103221556.txt")),
			// "UTF-8")));
			bw.write("[LEFTHTL]" + "\t\n");
			for (Entry<String, String> s : lqmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[LEFTBCL]" + "\t\n");
			for (Entry<String, String> s : lgmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[RIGHTHTL]" + "\t\n");
			for (Entry<String, String> s : rqmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[RIGHTHTL]" + "\t\n");
			for (Entry<String, String> s : rgmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.close();
			fw.close();
			res.setContentType("application/unknown");
			// 设置消息体的编码
			String filename = "tldata.dat";
			// res.setContentType("application/unknown");
			// 设置Content-Disposition
			res.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			// 读取目标文件，通过response将目标文件写到客户端
			// 获取目标文件的绝对路径
			String fullFileName = file1.getPath() +"/"+ filename;
			// System.out.println(fullFileName);
			// 读取文件
			InputStream in = new FileInputStream(fullFileName);
			OutputStream out = res.getOutputStream();

			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		
		} 
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		DBUtil.closeStatement(pstmt);
		DBUtil.rollback(con);
		DBUtil.closeConnection(con);
	}

}
public String valiteuser(String huierid){
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	String responseresult = "";
	try {
		conn=DBUtil.getConnection();
		DBUtil.beginTrans(conn);
		String queryinfo = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.icttel from tblindclient i where i.ictid='"
				+ huierid + "'";
		System.out.println(queryinfo);
		List result = (Vector) DBUtil.querySQL(conn, queryinfo);
		if (result.size() >= 1) {
			responseresult="has";
   							}
		else {
			responseresult="nobody";
		}
	} catch (Exception e) {
		e.printStackTrace();
		responseresult="sqlexception";
	} finally {
		DBUtil.closeStatement(pstmt);
		DBUtil.rollback(conn);
		DBUtil.closeConnection(conn);
	}

	
	return responseresult;
	
}


public void getFilecount(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {
	SingleClientForm saf = (SingleClientForm) form;
	FormFile formfile = saf.getUpFile();
	String huierid = saf.getHuierid();
	String jsid=saf.getJsid();
	String mactype=saf.getMactype();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	String result = "";
	try {
		conn = DBUtil.getConnection();
		DBUtil.beginTrans(conn);

		String debugfile="select filename,id from tblfile where usercode='"+jsid+"' and type='"+mactype+"'";
		
		
		System.out.println(debugfile);
		List fileresult = (Vector) DBUtil.querySQL(conn, debugfile);
		HashMap<String , String> map=new HashMap<String, String>();
		
		String filenamelist="";
		String idlist="";
		if (fileresult.size() >= 1) {
			String count=String.valueOf(fileresult.size());
			for (int i = 0; i < fileresult.size(); i++) {
				if(i==0){
					filenamelist = filenamelist+((HashMap) fileresult.get(i)).get("1")
							.toString();
					idlist = idlist+((HashMap) fileresult.get(i)).get("2")
							.toString();
					
					
				}
				else {
					filenamelist = filenamelist+","+((HashMap) fileresult.get(i)).get("1")
							.toString();
					idlist = idlist+","+((HashMap) fileresult.get(i)).get("2")
							.toString();
					
				}
			    map.put("count",count);
			    map.put("filenamelist", filenamelist);
			    map.put("idlist", idlist);
			    result=JSONObject.fromObject(map).toString();
				
			}
				
			
		} else {
			result = "nofile";

		}
	} catch (Exception e) {
		result = e.getMessage();
	} finally {
		DBUtil.closeStatement(pstmt);
		DBUtil.rollback(conn);
		DBUtil.closeConnection(conn);
	}
	res.setCharacterEncoding("GBK");
	PrintWriter writer = res.getWriter();
	writer.write(result);
	writer.flush();
	writer.close();

}
public ActionForward downDebug(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {
	String id = req.getParameter("id");
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	// String sql =
	// "insert into tblres(id,attachment)values(?,EMPTY_BLOB())";
	String sql = "select filecontent,filename from tblfile where id = ?";

	try {
		con = DBUtil.getJDBCConnection();
		DBUtil.beginTrans(con);
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		resultset = pstmt.executeQuery();
		if (resultset.next()) {
			String fileName = resultset.getString(2);
			Blob blob = resultset.getBlob(1);
			InputStream ins = blob.getBinaryStream();
			String filenameencoder = URLEncoder.encode(fileName, "utf-8");
			// res.setContentType("application/unknown");
			res.setContentType("application/unknown");
			res.addHeader("Content-Disposition", "attachment; filename="
					+ filenameencoder);
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
//下载听力数据
public void downtldatabyjsid(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {
	SingleClientForm saf = (SingleClientForm) form;
	/*String username = saf.getUsername();*/
	String jsid=saf.getJsid();
	String mactype=saf.getMactype();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	String checkuser="";
	String queryinfo="";
	try {
		
		con=DBUtil.getConnection();
//		checkuser=valiteuser(huierid);
		if(mactype.equals("0")){
			queryinfo = "select distinct t.ictid FROM tblindclient t LEFT OUTER JOIN tblmaking m on t.ictid = m.tmkcltid where 1=1 AND m.tmksid ='"+jsid+"'";
		}
		else{
			queryinfo="SELECT i.ictid FROM tblnorchg n LEFT JOIN tblnorchgdetail d ON n.chgid=d.ncdid LEFT JOIN tblindclient i on n.chgcltid  =i.ictid WHERE d.ncdjsid ='"+jsid +"' ";
		}
		List result = (Vector) DBUtil.querySQL(con, queryinfo);
		String huierid = ((HashMap) result.get(0)).get("1")
				.toString();
		System.out.println(queryinfo);
		
//		string
		if(true)
		{
			
			LinkedHashMap<String, String> lqmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> lgmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> rqmap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> rgmap = new LinkedHashMap<String, String>();
			// 查询听力复查表的听力信息
			String sqlfctldata = "SELECT adglre,  adgtp,  adgfq,  ADGADT,  adglre||ADGTP AS flag,  row_number() over (partition BY adglre,adgtp order by adgfq ) FROM  (SELECT *  FROM tblfctlgraph  WHERE fctlid IN (SELECT t.fcttlid    FROM      (SELECT FCTTLID FROM tblfctl WHERE FCTLICTID='"
					+ huierid
					+ "' ORDER BY FCTLDT DESC      ) t    where rownum<=1    ) and adgadt is not null    )";
			// 查询听力表的数据
			String sqlusertldata = "SELECT adglre,  adgtp,  adgfq,  adgadt, adglre||adgtp as flag,  row_number() over (partition BY adglre,adgtp order by adgfq )from tblaudgraph WHERE ADGCTID='"
					+ huierid + "' and adgadt is not null";
			// Map<String, LinkedHashMap<String, String>> tldatamap = new
			// HashMap<String, LinkedHashMap<String, String>>();
			List result2 = (Vector) DBUtil.querySQL(con, sqlfctldata);
			List result1 = (Vector) DBUtil.querySQL(con, sqlusertldata);
			if (result2.size() >= 1) {
				for (int i1 = 0; i1 < result2.size(); i1++) {
					String frequency = ((HashMap) result2.get(i1)).get("3")
							.toString();
					String value = ((HashMap) result2.get(i1)).get("4")
							.toString();
					String flag = ((HashMap) result2.get(i1)).get("5")
							.toString();
					int j = 0;
					if (flag.equals("LQ")) {
						j = 1;
					} else if (flag.equals("LG")) {
						j = 2;

					} else if (flag.equals("RQ")) {
						j = 3;
					} else if (flag.equals("RG")) {
						j = 4;
					}
					switch (j) {
					case 1:
						lqmap.put(frequency, value);
						break;
					case 2:
						lgmap.put(frequency, value);
						break;
					case 3:
						rqmap.put(frequency, value);
						break;
					case 4:
						rgmap.put(frequency, value);
						break;
					default:
						break;
					}
				}

			} else {
				for (int i1 = 0; i1 < result1.size(); i1++) {
					String frequency = ((HashMap) result1.get(i1)).get("3")
							.toString();
					String value = ((HashMap) result1.get(i1)).get("4")
							.toString();
					String flag = ((HashMap) result1.get(i1)).get("5")
							.toString();
					int j = 0;
					if (flag.equals("LQ")) {
						j = 1;
					} else if (flag.equals("LG")) {
						j = 2;

					} else if (flag.equals("RQ")) {
						j = 3;
					} else if (flag.equals("RG")) {
						j = 4;
					}
					switch (j) {
					case 1:
						lqmap.put(frequency, value);
						break;
					case 2:
						lgmap.put(frequency, value);
						break;
					case 3:
						rqmap.put(frequency, value);
						break;
					case 4:
						rgmap.put(frequency, value);
						break;
					default:
						break;
					}
				}
			}
			File file1 = new File("D:\\debug\\" + huierid);
			// 如果文件夹不存在则创建
			if (!file1.exists() && !file1.isDirectory()) {
				System.out.println("//不存在");
				file1.mkdir();
			} else {
				System.out.println("//目录存在");
			}
			FileWriter fw = new FileWriter(new File(file1.getPath() + "/"
					+ "tldata.dat"));
			// 写入中文字符时会出现乱码
			BufferedWriter bw = new BufferedWriter(fw);
			// BufferedWriter bw=new BufferedWriter(new BufferedWriter(new
			// OutputStreamWriter(new FileOutputStream(new
			// File("E:/phsftp/evdokey/evdokey_201103221556.txt")),
			// "UTF-8")));
			bw.write("[LEFTHTL]" + "\t\n");
			for (Entry<String, String> s : lqmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[LEFTBCL]" + "\t\n");
			for (Entry<String, String> s : lgmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[RIGHTHTL]" + "\t\n");
			for (Entry<String, String> s : rqmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.write("[RIGHTBCL]" + "\t\n");
			for (Entry<String, String> s : rgmap.entrySet()) {
				bw.write(s + "\t\n");
				// System.out.println(s);
			}
			bw.close();
			fw.close();
			res.setContentType("application/unknown");
			// 设置消息体的编码
			String filename = "tldata.dat";
			// res.setContentType("application/unknown");
			// 设置Content-Disposition
			res.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			// 读取目标文件，通过response将目标文件写到客户端
			// 获取目标文件的绝对路径
			String fullFileName = file1.getPath() +"/"+ filename;
			// System.out.println(fullFileName);
			// 读取文件
			InputStream in = new FileInputStream(fullFileName);
			OutputStream out = res.getOutputStream();

			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		
		} 
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		DBUtil.closeStatement(pstmt);
		DBUtil.rollback(con);
		DBUtil.closeConnection(con);
	}

}
}
