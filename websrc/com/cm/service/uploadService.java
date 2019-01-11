package com.cm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.radf.apps.commons.entity.Audiogram;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.util.exception.NoConnectionException;

public class uploadService {
	// @Test
	public String uploadData(String guid, String filecontent) {
		String result = null;
		// String guid = "1111";
		String type = "";
		String time = "";
		String usercode = "";
		String username = "";
		String shopid = "";
		String uploadWay = "autoupload";
		// String filecontent =
		// "{type='听力数据',time='2008-01-01 15:23:01',usercode='000017',shopid='A0001',username='郑宏升',other='aaaaa',value=[{ear:'L',typ:'S',value:[{hz=500,db=50,mask=0},{hz:700,db:100,mask:1}]},{ear:'L',typ:'Q',value:[{hz=500,db=50,mask=0},{hz:700,db:100,mask:1}]}]}";
		List<Audiogram> audlist = new ArrayList<Audiogram>();
		/*
		 * JSONObject a =JSONObject.fromObject(json);
		 * System.out.println(a.toString());
		 */
		JSONObject filecontentjson = JSONObject.fromObject(filecontent);
		System.out.println(filecontent.toString());
		type = filecontentjson.getString("type");
		if (type.equals("听力数据")) {
			time = filecontentjson.getString("time");
			usercode = filecontentjson.getString("usercode");
			shopid = filecontentjson.getString("shopid");
			username = filecontentjson.getString("username");
			JSONArray listendata = JSONArray.fromObject(filecontentjson
					.get("value"));
			/* System.out.println(listendata.toString()); */
			for (int i = 0; i < listendata.size(); i++) {
				JSONObject eachlistendata = JSONObject.fromObject(listendata
						.get(i));
				System.out.println(eachlistendata.toString());
				String ear = eachlistendata.getString("ear");
				String typ = eachlistendata.getString("typ");
				JSONArray eachlistenvalue = JSONArray.fromObject(eachlistendata
						.get("value"));
				for (int j = 0; j < eachlistenvalue.size(); j++) {
					JSONObject eachlistendatadetail = (JSONObject) eachlistenvalue
							.get(j);
					System.out.println(eachlistendatadetail.toString());
					String hz = eachlistendatadetail.getString("hz");
					String db = eachlistendatadetail.getString("db");
					String mask = eachlistendatadetail.getString("mask");
					Audiogram audi = new Audiogram();
					// audi.setAdgctid("01234");
					audi.setAdglre(ear);
					audi.setAdgtp(typ);
					audi.setAdgfq(Integer.valueOf(hz));
					audi.setAdgadt(db);
					audi.setAdgshie(mask);
					audlist.add(audi);
				}
			}
			System.out.println(audlist.toString());
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				DBUtil.beginTrans(conn);
				String sqlusertl = "select * from TBLAUDGRAPH where ADGCTID='"
						+ usercode + "'";
				List result1 = (Vector) DBUtil.querySQL(conn,
						"select SEQ_FCTL.NEXTVAL from dual");
				BigDecimal id = (BigDecimal) ((HashMap) result1.get(0))
						.get("1");
				String fctlid = id.toString();
				int rownum = DBUtil.getRowCount(conn, sqlusertl);
				if (rownum >= 1) {
					String sql1 = "insert into tblfctl(fcttlid,fctldt,fctlictid,fctlgtid,fctlitnm,type) values(?,?,?,?,?,?)";
					PreparedStatement pr1 = conn.prepareStatement(sql1);
					pr1.setString(1, fctlid);
					pr1.setDate(2, (Date) DateUtil.converToDate(time));
					pr1.setString(3, usercode);
					pr1.setString(4, shopid);
					pr1.setString(5, username);
					pr1.setString(6, uploadWay);
					int i = pr1.executeUpdate();
					if (i > 0) {
						System.out.println("插入复诊听力表成功");
					}
				} else {
					System.out.println("该用户已经有过听力数据");
				}
				String sql = "insert into tblfctlgraph (fctlid,adglre,adgtp,adgfq,adgadt,adgshie) values (?,?,?,?,?,?)";
				String sql1 = "insert into tblaudgraph(ADGCTID,adglre,adgtp,adgfq,adgadt,adgshie) values(?,?,?,?,?,?)";
				PreparedStatement pr = null;
				String insertid = null;

				if (rownum >= 1) {
					pr = conn.prepareStatement(sql);
					insertid = fctlid;
				} else {
					insertid = usercode;
					pr = conn.prepareStatement(sql1);
				}
				for (Audiogram ag : audlist) {

					pr.setString(1, insertid);
					pr.setString(2, ag.getAdglre());
					pr.setString(3, ag.getAdgtp());
					pr.setInt(4, ag.getAdgfq());
					pr.setString(5, ag.getAdgadt());
					pr.setString(6, ag.getAdgshie());

					/*
					 * int i=pr.executeUpdate(); conn.commit(); if(i==1){
					 * System.out.println("插入"+ag.toString()+"数据成功"); }
					 */
					pr.addBatch();
				}
				pr.executeBatch();

				// 将字符串内容保存到TBLGUID
				String sql3 = "insert into tblguid (guid) values(?)";
				PreparedStatement stmt = conn.prepareStatement(sql3);
				stmt.setString(1, guid);
				stmt.execute();
				DBUtil.closeStatement(stmt);
				String key = "guid='" + guid + "'";
				DBUtil.saveClob(conn, "tblguid", "filecontent", key,
						filecontent);
				conn.commit();
				result = "用户" + username + "的听力数据上传成功";

			} catch (NoConnectionException e) {
				// TODO Auto-generated catch block
				result = "用户" + username + "的听力数据上传失败原因：" + e.getMessage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = "用户" + username + "的听力数据上传失败原因：" + e.getMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result = "用户" + username + "的听力数据上传失败原因：" + e.getMessage();
			} finally {
				DBUtil.rollback(conn);
				DBUtil.closeConnection(conn);
			}

		}
		return result;
	}

	/*
	 * @Test public void testclob() throws SQLException, IOException,
	 * NoConnectionException{ Connection conn=DBUtil.getConnection(); String
	 * guid="123456"; String sql3 = "insert into tblguid (guid) values(?)";
	 * String filecontent="1233333333333333333333333"; PreparedStatement stmt =
	 * conn.prepareStatement(sql3); stmt.setString(1, guid); stmt.execute();
	 * DBUtil.closeStatement(stmt); String key="guid='"+guid+"'";
	 * DBUtil.saveClob(conn, "tblguid", "filecontent", key,filecontent);
	 * DBUtil.commit(conn); }
	 */
	// @Test
	public String downByguid(String guid) {
		Connection conn = null;
		String reString = "";
		String result = "没有信息";
		try {
			String sql = "select filecontent from tblguid where guid='" + guid
					+ "'";
			conn = DBUtil.getConnection();
			Clob clob = null;
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				clob = (Clob) rs.getObject(1);
				Reader is = clob.getCharacterStream();// 得到流
				BufferedReader br = new BufferedReader(is);
				String s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
					sb.append(s);
					s = br.readLine();
				}
				result = sb.toString();
			}

			System.out.println(result);

		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}
		return result;

	}

	/**
	 * 依据客户id获取客户的听力图信息
	 */
	//@Test
	public String getlistenclient(String usercode) {

//		String usercode = "063432";
		String responseresult = "";
		// 获取复诊里面的听力数据
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			List useralldata = new ArrayList();
			String sql1 = "SELECT distinct a.* from (SELECT g.fctlid, f.fctlitnm,f.fctlgtid,f.fctldt FROM tblfctl f ,  tblfctlgraph g WHERE f.fcttlid=g.fctlid AND f.fctlictid='"
					+ usercode + "' and g.adgadt is not null ) a";
			String sql2 = "select i.ictdate,i.ictnm,i.ictgctid,a.* from tblindclient i,tblaudgraph a WHERE a.adgctid=i.ictid and a.adgadt is not null and a.adgctid='"
					+ usercode + "'";
			List sql1result = (Vector) DBUtil.querySQL(conn, sql1);
			List sql2result = (Vector) DBUtil.querySQL(conn, sql2);
			if (sql1result.size() >= 1) {
				for (int i = 0; i < sql1result.size(); i++) {
					Map map1 = new HashMap();
					String id = ((HashMap) sql1result.get(i)).get("1")
							.toString();
					String username = ((HashMap) sql1result.get(i)).get("2")
							.toString();
					String shopid = ((HashMap) sql1result.get(i)).get("3")
							.toString();
					String time = ((HashMap) sql1result.get(i)).get("4")
							.toString();
					map1.put("time", time);
					map1.put("usercode", usercode);
					map1.put("shopid", shopid);
					map1.put("username", username);
					String tldata = "select * from tblfctlgraph where fctlid='"
							+ id + "' and adgadt is not null";
					List tldatalist = (Vector) DBUtil.querySQL(conn, tldata);
					List tldatalist1 = new ArrayList();
					for (int j = 0; j < tldatalist.size(); j++) {
						Map map2 = new HashMap();
						map2.put("ear", ((HashMap) tldatalist.get(j)).get("2")
								.toString());
						map2.put("typ", ((HashMap) tldatalist.get(j)).get("3")
								.toString());
						map2.put("hz", ((HashMap) tldatalist.get(j)).get("4")
								.toString());
						map2.put("db", ((HashMap) tldatalist.get(j)).get("5")
								.toString());
						String mask = (((HashMap) tldatalist.get(j)).get("6") != null ? ((HashMap) tldatalist
								.get(j)).get("6").toString() : "0");
						map2.put("mask", mask);
						tldatalist1.add(map2);
					}
					map1.put("value", tldatalist1);
					/*JSONObject json = JSONObject.fromObject(map1);

					System.out.println(json.toString());*/
					useralldata.add(map1);

				}

			}
			if (sql2result.size() >= 1) {
				Map map1 = new HashMap();
				String time = "";
				String shopid = "";
				String username = "";
				List tldatalist2 = new ArrayList();
				for (int i = 0; i < sql2result.size(); i++) {
					time = (((HashMap) sql2result.get(i)).get("1") == null ? ""
							: ((HashMap) sql2result.get(i)).get("1").toString());
					username = ((HashMap) sql2result.get(i)).get("2")==null?"":((HashMap) sql2result.get(i)).get("2").toString();
					shopid = ((HashMap) sql2result.get(i)).get("3").toString();
					Map map2 = new HashMap();
					map2.put("ear", ((HashMap) sql2result.get(i)).get("5")
							.toString());
					map2.put("typ", ((HashMap) sql2result.get(i)).get("6")
							.toString());
					map2.put("hz", ((HashMap) sql2result.get(i)).get("7")
							.toString());
					map2.put("db", ((HashMap) sql2result.get(i)).get("8")
							.toString());
					String mask = (((HashMap) sql2result.get(i)).get("9") != null ? ((HashMap) sql2result
							.get(i)).get("9").toString() : "0");
					map2.put("mask", mask);
					tldatalist2.add(map2);

				}
				map1.put("time", time);
				map1.put("shopid", shopid);
				map1.put("username", username);
				map1.put("usercode", usercode);
				map1.put("value", tldatalist2);
				/*JSONObject json = JSONObject.fromObject(map1);

				System.out.println(json.toString());*/
				useralldata.add(map1);
			}
			if (useralldata.size() == 0) {
				responseresult = "没有听力数据";
			} else {
				responseresult = JSONArray.fromObject(useralldata).toString();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseresult = "异常原因:" + e.getMessage();
		}
		return responseresult;
	}

//	@Test
	public String  getlistenshop(String shopid) {
//		String shopid = "A0065";
		String usercode = "";
		String responseresult = "";
		// 获取复诊里面的听力数据

		String sql3 = "select distinct ictid fROM tblindclient where ictgctid='"
				+ shopid + "'";
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			List useralldata = new ArrayList();
			long start=System.currentTimeMillis();
			List sql3result = (Vector) DBUtil.querySQL(conn, sql3);
			if (sql3result.size() >= 1) {
				for(int i=0;i<sql3result.size();i++){
					
					usercode=((HashMap)sql3result.get(i)).get("1").toString();
					String sql1 = "SELECT distinct a.* from (SELECT g.fctlid, f.fctlitnm,f.fctlgtid,f.fctldt FROM tblfctl f ,  tblfctlgraph g WHERE f.fcttlid=g.fctlid AND f.fctlictid='"
							+ usercode + "' and g.adgadt is not null ) a";
					String sql2 = "select i.ictdate,i.ictnm,i.ictgctid,a.* from tblindclient i,tblaudgraph a WHERE a.adgctid=i.ictid and a.adgadt is not null and a.adgctid='"
							+ usercode + "'";
					List sql1result = (Vector) DBUtil.querySQL(conn, sql1);
					List sql2result = (Vector) DBUtil.querySQL(conn, sql2);
					if (sql1result.size() >= 1) {
						for (int i1 = 0; i1 < sql1result.size(); i1++) {
							
							Map map1 = new HashMap();
							String id = ((HashMap) sql1result.get(i1)).get("1")
									.toString();
							String username = ((HashMap) sql1result.get(i1)).get("2")
									.toString();
						/*	String shopid = ((HashMap) sql1result.get(i1)).get("3")
									.toString();*/
							String time = ((HashMap) sql1result.get(i1)).get("4")
									.toString();
							map1.put("time", time);
							map1.put("usercode", usercode);
							map1.put("shopid", shopid);
							map1.put("username", username);
							String tldata = "select * from tblfctlgraph where fctlid='"
									+ id + "' and adgadt is not null";
							List tldatalist = (Vector) DBUtil.querySQL(conn, tldata);
							List tldatalist1 = new ArrayList();
							for (int j = 0; j < tldatalist.size(); j++) {
								Map map2 = new HashMap();
								map2.put("ear", ((HashMap) tldatalist.get(j)).get("2")
										.toString());
								map2.put("typ", ((HashMap) tldatalist.get(j)).get("3")
										.toString());
								map2.put("hz", ((HashMap) tldatalist.get(j)).get("4")
										.toString());
								map2.put("db", ((HashMap) tldatalist.get(j)).get("5")
										.toString());
								String mask = (((HashMap) tldatalist.get(j)).get("6") != null ? ((HashMap) tldatalist
										.get(j)).get("6").toString() : "0");
								map2.put("mask", mask);
								tldatalist1.add(map2);
							}
							map1.put("value", tldatalist1);
						/*	JSONObject json = JSONObject.fromObject(map1);

							System.out.println(json.toString());*/
							useralldata.add(map1);

						}

					}
					if (sql2result.size() >= 1) {
						Map map1 = new HashMap();
						String time = "";
					/*	String shopid = "";*/
						String username = "";
						List tldatalist2 = new ArrayList();
						for (int i1 = 0; i1 < sql2result.size(); i1++) {
							time = (((HashMap) sql2result.get(i1)).get("1") == null ? ""
									: ((HashMap) sql2result.get(i1)).get("1").toString());
							username = ((HashMap) sql2result.get(i1)).get("2")==null?"":((HashMap) sql2result.get(i1)).get("2").toString();
							shopid = ((HashMap) sql2result.get(i1)).get("3").toString();
							Map map2 = new HashMap();
							map2.put("ear", ((HashMap) sql2result.get(i1)).get("5")
									.toString());
							map2.put("typ", ((HashMap) sql2result.get(i1)).get("6")
									.toString());
							map2.put("hz", ((HashMap) sql2result.get(i1)).get("7")
									.toString());
							map2.put("db", ((HashMap) sql2result.get(i1)).get("8")
									.toString());
							String mask = (((HashMap) sql2result.get(i1)).get("9") != null ? ((HashMap) sql2result
									.get(i1)).get("9").toString() : "0");
							map2.put("mask", mask);
							tldatalist2.add(map2);

						}
						map1.put("time", time);
						map1.put("shopid", shopid);
						map1.put("username", username);
						map1.put("usercode", usercode);
						map1.put("value", tldatalist2);
						/*JSONObject json = JSONObject.fromObject(map1);

						System.out.println(json.toString());*/
						useralldata.add(map1);
					}
					 long end = System.currentTimeMillis();             
					 
					      
				}

			}
			if (useralldata.size() == 0) {
				responseresult = "没有听力数据";
			} else {

				responseresult = JSONArray.fromObject(useralldata).toString();
				System.out.println();
			}
			long end=System.currentTimeMillis();
			 System.out.println("运行时间："+(end-start)/1000+"秒");  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(responseresult = "异常原因:" +usercode+ e.getMessage());
		}
		return responseresult;
		/*System.out.println(responseresult);*/
		/*System.out.println(responseresult);*/
		
	}
}
