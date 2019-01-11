package com.cm.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.radf.plat.commons.DBUtil;

public class debugTest {

	@Test
	public void testdowload() {
		String userid = "000008";
		Connection conn = null;
		String qatestft = null;
		String userinfo = null;
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			String sql = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.icttel from tblindclient i where i.ictid='"
					+ userid + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			Map<String, LinkedHashMap<String, String>> userinfomap = new HashMap<String, LinkedHashMap<String, String>>();
//			List<LinkedHashMap<String, String>> userlist = new ArrayList<LinkedHashMap<String, String>>();
			if (result.size() >= 1) {
				LinkedHashMap<String, String> lqmap = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> lgmap = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> rqmap = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> rgmap = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0; i < result.size(); i++) {
                    
					

					if (((HashMap) result.get(i)).get("1") == null) {
					} else {
						map.put("clientNo", ((HashMap) result.get(i)).get("1")
								.toString());
					}
					if (((HashMap) result.get(i)).get("2") == null) {
					} else {
						String name = ((HashMap) result.get(i)).get("2")
								.toString();
						String lastname = name.substring(0, 1);
						String firstname = name.substring(1, name.length());
						map.put("lastname", lastname);
						map.put("firstname", firstname);
					}
					if (((HashMap) result.get(i)).get("3") == null) {
					} else {
						map.put("coname", ((HashMap) result.get(i)).get("3")
								.toString());
					}
					if (((HashMap) result.get(i)).get("4") == null) {
					} else {
						map.put("sex", ((HashMap) result.get(i)).get("4")
								.toString());
					}
					if (((HashMap) result.get(i)).get("5") == null) {
					} else {
						map.put("birthday", ((HashMap) result.get(i)).get("5")
								.toString().substring(0, 10));
					}
					if (((HashMap) result.get(i)).get("6") == null) {
					} else {
						map.put("address", ((HashMap) result.get(i)).get("6")
								.toString());
					}

					if (((HashMap) result.get(i)).get("7") == null) {
					} else {
						map.put("conametel", ((HashMap) result.get(i)).get("7")
								.toString());
					}
					userinfomap.put("[CLIENTINFO]", map);
					System.out.println(userinfomap.toString());
//					userlist.add(map);

				}
				
				//查询听力复查表的听力信息			
				String sqlfctldata = "SELECT adglre,  adgtp,  adgfq,  ADGADT,  adglre||ADGTP AS flag,  row_number() over (partition BY adglre,adgtp order by adgfq ) FROM  (SELECT *  FROM tblfctlgraph  WHERE fctlid IN (SELECT t.fcttlid    FROM      (SELECT FCTTLID FROM tblfctl WHERE FCTLICTID='000008' ORDER BY FCTLDT DESC      ) t    where rownum<=1    ) and adgadt is not null    )";
				//查询听力表的数据
				String sqlusertldata = "select * from tblaudgraph where ADGCTID='000008'";

//				Map<String, LinkedHashMap<String, String>> tldatamap = new HashMap<String, LinkedHashMap<String, String>>();
			

				List result2 = (Vector) DBUtil.querySQL(conn, sqlfctldata);

				if (result2.size() >= 1) {
					for (int i = 0; i < result2.size(); i++) {
						String frequency = ((HashMap) result2.get(i)).get("3")
								.toString();
						String value = ((HashMap) result2.get(i)).get("4")
								.toString();
						String flag = ((HashMap) result2.get(i)).get("5")
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

				}
		   	   FileWriter fw=new FileWriter(new File("E:/huierdebugtest/debugtest.dat"));
			        //写入中文字符时会出现乱码
			        BufferedWriter  bw=new BufferedWriter(fw);
			        //BufferedWriter  bw=new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8")));
			        bw.write("[CLIENTINFO]"+"\t\n");
			        for(Entry<String, String> s:map.entrySet()){
			        	bw.write(s+"\t\n");
			        }
			        bw.write("[LEFTHTL]"+"\t\n");
			        for (Entry<String, String> s : lqmap.entrySet()) {
			        	bw.write(s+"\t\n");
//			        	System.out.println(s);
					}
			        bw.write("[LEFTBCL]"+"\t\n");
			        for (Entry<String, String> s : lgmap.entrySet()) {
			        	bw.write(s+"\t\n");
//			        	System.out.println(s);
					}
			        bw.write("[RIGHTHTL]"+"\t\n");
			        for (Entry<String, String> s : rqmap.entrySet()) {
			        	bw.write(s+"\t\n");
//			        	System.out.println(s);
					}
			        bw.write("[RIGHTHTL]"+"\t\n");
			        for (Entry<String, String> s : rgmap.entrySet()) {
			        	bw.write(s+"\t\n");
//			        	System.out.println(s);
					}
//			        for(String arr:arrs){
//			            bw.write(arr+"\t\n");
//			        }
			        bw.close();
			        fw.close();
			        CompressBook book = new CompressBook();
			        book.zip("E:\\huierdebugtest");
			        
			        
				/*System.out.println("[LEFTHTL]");
				for (Entry<String, String> s : lqmap.entrySet()) {
					System.out.println(s);
				}
				System.out.println("[LEFTBCL]");
				for (Entry<String, String> s : lgmap.entrySet()) {
					System.out.println(s);
				}
				System.out.println("[RIGHTHTL]");
				for (Entry<String, String> s : rqmap.entrySet()) {
					System.out.println(s);
				}
				System.out.println("[RIGHTBCL]");
				for (Entry<String, String> s : rgmap.entrySet()) {
					System.out.println(s);
				}*/
	/*			tldatamap.put("[LEFTHTL]", lqmap);
				tldatamap.put("[LEFTBCL]", lgmap);
				tldatamap.put("[RIGHTHTL]", rqmap);
				tldatamap.put("[RIGHTBCL]", rgmap);*/
//				System.out.println(tldatamap);
//				JSONArray jsonstr = JSONArray.fromObject(userlist);
//				userinfo = jsonstr.toString();
				
			} else {
				userinfo = "没有此客户的信息";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}

	}

}
