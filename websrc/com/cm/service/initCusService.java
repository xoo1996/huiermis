package com.cm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Test;
import org.radf.apps.client.group.facade.GroupClientFacade;
import org.radf.apps.client.group.form.GroupClientForm;
import org.radf.apps.commons.entity.Audiogram;
import org.radf.apps.commons.entity.Customization;
import org.radf.apps.commons.entity.GroupClient;
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
import org.radf.plat.commons.FileUtil;
import org.radf.plat.commons.FindLog;
import org.radf.plat.commons.StringUtil;
import org.radf.plat.log.LogHelper;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.sieaf.event.EventResponse;
import org.radf.plat.util.action.ActionLeafSupport;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.exception.NoConnectionException;
import org.radf.plat.util.global.GlobalNames;
import org.radf.plat.util.imp.IMPSupport;

import com.ctc.wstx.util.DataUtil;

public class initCusService{
         public String initCustomer (String id) {
        	 Connection conn = null;
        	 String qatestft = null;
        	 String userinfo=null;
     		try {
     			conn = DBUtil.getConnection();
     			DBUtil.beginTrans(conn);
     			String sql = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.ictpcd, i.icttel, i.ictage, i.ictpro, i.ictcity, i.ictcounty, i.ictphone from tblindclient i where i.ictgctid='" + id + "'";
     			List result = (Vector) DBUtil.querySQL(conn, sql);
     			List<LinkedHashMap<String, String>> userlist = new ArrayList<LinkedHashMap<String, String>>();
     			if (result.size() >= 1) {
/*     				String a=result.get(0).toString();
     				qatestft = ((HashMap) result.get(0)).get("1").toString();*/
//     				for(HashMap<K, V>)
     				for(int i=0;i<result.size();i++)
     				{
     					
     					LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
     					/*for(int j=1;j<13;j++){
     						String key=String.valueOf(j);
     						key=((HashMap)result.get(i)).get(key).toString();
     						map.put("ictid", result.get(i).get())*/
     						
     					
     					if(((HashMap)result.get(i)).get("1")==null){
     						map.put("ictid", "");
     					}
     					else {
     						map.put("ictid", ((HashMap)result.get(i)).get("1").toString());
						}
     					if(((HashMap)result.get(i)).get("2")==null){
     						map.put("ictnm", "");
     					}
     					else {
     						map.put("ictnm", ((HashMap)result.get(i)).get("2").toString());
						}
     					if(((HashMap)result.get(i)).get("3")==null){
     						map.put("ictpnm", "");
     					}
     					else {
     						map.put("ictpnm", ((HashMap)result.get(i)).get("3").toString());
						}
     					if(((HashMap)result.get(i)).get("4")==null){
     						map.put("ictgender", "");
     					}
     					else {
     						map.put("ictgender", ((HashMap)result.get(i)).get("4").toString());
						}
     					if(((HashMap)result.get(i)).get("5")==null){
     						map.put("ictbdt", "");
     					}
     					else {
     						map.put("ictbdt", ((HashMap)result.get(i)).get("5").toString());
						}
     					if(((HashMap)result.get(i)).get("6")==null){
     						map.put("ictaddr", "");
     					}
     					else {
     						map.put("ictaddr", ((HashMap)result.get(i)).get("6").toString());
						}
     					if(((HashMap)result.get(i)).get("7")==null){
     						map.put("ictpcd", "");
     					}
     					else {
     						map.put("ictpcd", ((HashMap)result.get(i)).get("7").toString());
						}
     					if(((HashMap)result.get(i)).get("8")==null){
     						map.put("icttel", "");
     					}
     					else {
     						map.put("icttel", ((HashMap)result.get(i)).get("8").toString());
						}
     					if(((HashMap)result.get(i)).get("9")==null){
     						map.put("ictage", "");
     					}
     					else {
     						map.put("ictage", ((HashMap)result.get(i)).get("9").toString());
						}
     					if(((HashMap)result.get(i)).get("10")==null){
     						map.put("ictpro", "");
     					}
     					else {
     						map.put("ictpro", ((HashMap)result.get(i)).get("10").toString());
						}
     					if(((HashMap)result.get(i)).get("11")==null){
     						map.put("ictcity", "");
     					}
     					else {
     						map.put("ictcity", ((HashMap)result.get(i)).get("11").toString());
						}
     					if(((HashMap)result.get(i)).get("12")==null){
     						map.put("ictcountry", "");
     					}
     					else {
     						map.put("ictcountry", ((HashMap)result.get(i)).get("12").toString());
						}
     					if(((HashMap)result.get(i)).get("13")==null){
     						map.put("ictphone", "");
     					}
     					else {
     						map.put("ictphone", ((HashMap)result.get(i)).get("13").toString());
						}
                       map.toString();
                       userlist.add(map);
               		  
     				}
     				 JSONArray jsonstr=JSONArray.fromObject(userlist);
             		   userinfo=jsonstr.toString();
     			}
     			else {
					userinfo="没有此门店的用户信息";
				}
     		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			DBUtil.closeConnection(conn);
    		}
        	 return userinfo;
		}
        public String queryUserById (String userid){
        	 Connection conn = null;
        	 String qatestft = null;
        	 String userinfo=null;
     		try {
     			conn = DBUtil.getConnection();
     			DBUtil.beginTrans(conn);
     			String sql = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.ictpcd, i.icttel, i.ictage, i.ictpro, i.ictcity, i.ictcounty, i.ictphone from tblindclient i where i.ictid='" + userid + "'";
     			List result = (Vector) DBUtil.querySQL(conn, sql);
     			List<LinkedHashMap<String, String>> userlist = new ArrayList<LinkedHashMap<String, String>>();
     			if (result.size() >= 1) {
/*     				String a=result.get(0).toString();
     				qatestft = ((HashMap) result.get(0)).get("1").toString();*/
//     				for(HashMap<K, V>)
     				for(int i=0;i<result.size();i++)
     				{
     					
     					LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
     					/*for(int j=1;j<13;j++){
     						String key=String.valueOf(j);
     						key=((HashMap)result.get(i)).get(key).toString();
     						map.put("ictid", result.get(i).get())*/
     						
     					
     					if(((HashMap)result.get(i)).get("1")==null){
     						map.put("ictid", "");
     					}
     					else {
     						map.put("ictid", ((HashMap)result.get(i)).get("1").toString());
						}
     					if(((HashMap)result.get(i)).get("2")==null){
     						map.put("ictnm", "");
     					}
     					else {
     						map.put("ictnm", ((HashMap)result.get(i)).get("2").toString());
						}
     					if(((HashMap)result.get(i)).get("3")==null){
     						map.put("ictpnm", "");
     					}
     					else {
     						map.put("ictpnm", ((HashMap)result.get(i)).get("3").toString());
						}
     					if(((HashMap)result.get(i)).get("4")==null){
     						map.put("ictgender", "");
     					}
     					else {
     						map.put("ictgender", ((HashMap)result.get(i)).get("4").toString());
						}
     					if(((HashMap)result.get(i)).get("5")==null){
     						map.put("ictbdt", "");
     					}
     					else {
     						map.put("ictbdt", ((HashMap)result.get(i)).get("5").toString());
						}
     					if(((HashMap)result.get(i)).get("6")==null){
     						map.put("ictaddr", "");
     					}
     					else {
     						map.put("ictaddr", ((HashMap)result.get(i)).get("6").toString());
						}
     					if(((HashMap)result.get(i)).get("7")==null){
     						map.put("ictpcd", "");
     					}
     					else {
     						map.put("ictpcd", ((HashMap)result.get(i)).get("7").toString());
						}
     					if(((HashMap)result.get(i)).get("8")==null){
     						map.put("icttel", "");
     					}
     					else {
     						map.put("icttel", ((HashMap)result.get(i)).get("8").toString());
						}
     					if(((HashMap)result.get(i)).get("9")==null){
     						map.put("ictage", "");
     					}
     					else {
     						map.put("ictage", ((HashMap)result.get(i)).get("9").toString());
						}
     					if(((HashMap)result.get(i)).get("10")==null){
     						map.put("ictpro", "");
     					}
     					else {
     						map.put("ictpro", ((HashMap)result.get(i)).get("10").toString());
						}
     					if(((HashMap)result.get(i)).get("11")==null){
     						map.put("ictcity", "");
     					}
     					else {
     						map.put("ictcity", ((HashMap)result.get(i)).get("11").toString());
						}
     					if(((HashMap)result.get(i)).get("12")==null){
     						map.put("ictcountry", "");
     					}
     					else {
     						map.put("ictcountry", ((HashMap)result.get(i)).get("12").toString());
						}
     					if(((HashMap)result.get(i)).get("13")==null){
     						map.put("ictphone", "");
     					}
     					else {
     						map.put("ictphone", ((HashMap)result.get(i)).get("13").toString());
						}
                       map.toString();
                       userlist.add(map);
                       
     				}
     				 JSONArray jsonstr=JSONArray.fromObject(userlist);
             		   userinfo=jsonstr.toString();
     			}
     			else {
					userinfo="没有此客户的信息";
				}
     		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			DBUtil.closeConnection(conn);
    		}
        	 return userinfo;
        }
        public String queryUserByupdatetime(String groupid,String updatetime){
//         String updatetime="2015-09-27 18:39:58";
         Connection conn = null;
       	 String qatestft = null;
       	 String userinfo=null;
    		try {
    			conn = DBUtil.getConnection();
    			DBUtil.beginTrans(conn);
    			String sql = "SELECT i.ictid, i.ictnm, i.ictpnm, i.ictgender, i.ictbdt, i.ictaddr, i.ictpcd, i.icttel, i.ictage, i.ictpro, i.ictcity, i.ictcounty, i.ictphone from tblindclient i where i.ictid in(select usercode from tbluserupdatetime u where u.OPERATETIME >=to_date('"+updatetime+"','yyyy-MM-dd HH24:mi:ss')) and i.ICTGCTID='"+groupid+"'";
    			List result = (Vector) DBUtil.querySQL(conn, sql);
    			List<LinkedHashMap<String, String>> userlist = new ArrayList<LinkedHashMap<String, String>>();
    			if (result.size() >= 1) {
/*     				String a=result.get(0).toString();
    				qatestft = ((HashMap) result.get(0)).get("1").toString();*/
//    				for(HashMap<K, V>)
    				for(int i=0;i<result.size();i++)
    				{
    					
    					LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
    					/*for(int j=1;j<13;j++){
    						String key=String.valueOf(j);
    						key=((HashMap)result.get(i)).get(key).toString();
    						map.put("ictid", result.get(i).get())*/
    						
    					
    					if(((HashMap)result.get(i)).get("1")==null){
    						map.put("ictid", "");
    					}
    					else {
    						map.put("ictid", ((HashMap)result.get(i)).get("1").toString());
						}
    					if(((HashMap)result.get(i)).get("2")==null){
    						map.put("ictnm", "");
    					}
    					else {
    						map.put("ictnm", ((HashMap)result.get(i)).get("2").toString());
						}
    					if(((HashMap)result.get(i)).get("3")==null){
    						map.put("ictpnm", "");
    					}
    					else {
    						map.put("ictpnm", ((HashMap)result.get(i)).get("3").toString());
						}
    					if(((HashMap)result.get(i)).get("4")==null){
    						map.put("ictgender", "");
    					}
    					else {
    						map.put("ictgender", ((HashMap)result.get(i)).get("4").toString());
						}
    					if(((HashMap)result.get(i)).get("5")==null){
    						map.put("ictbdt", "");
    					}
    					else {
    						map.put("ictbdt", ((HashMap)result.get(i)).get("5").toString());
						}
    					if(((HashMap)result.get(i)).get("6")==null){
    						map.put("ictaddr", "");
    					}
    					else {
    						map.put("ictaddr", ((HashMap)result.get(i)).get("6").toString());
						}
    					if(((HashMap)result.get(i)).get("7")==null){
    						map.put("ictpcd", "");
    					}
    					else {
    						map.put("ictpcd", ((HashMap)result.get(i)).get("7").toString());
						}
    					if(((HashMap)result.get(i)).get("8")==null){
    						map.put("icttel", "");
    					}
    					else {
    						map.put("icttel", ((HashMap)result.get(i)).get("8").toString());
						}
    					if(((HashMap)result.get(i)).get("9")==null){
    						map.put("ictage", "");
    					}
    					else {
    						map.put("ictage", ((HashMap)result.get(i)).get("9").toString());
						}
    					if(((HashMap)result.get(i)).get("10")==null){
    						map.put("ictpro", "");
    					}
    					else {
    						map.put("ictpro", ((HashMap)result.get(i)).get("10").toString());
						}
    					if(((HashMap)result.get(i)).get("11")==null){
    						map.put("ictcity", "");
    					}
    					else {
    						map.put("ictcity", ((HashMap)result.get(i)).get("11").toString());
						}
    					if(((HashMap)result.get(i)).get("12")==null){
    						map.put("ictcountry", "");
    					}
    					else {
    						map.put("ictcountry", ((HashMap)result.get(i)).get("12").toString());
						}
    					if(((HashMap)result.get(i)).get("13")==null){
    						map.put("ictphone", "");
    					}
    					else {
    						map.put("ictphone", ((HashMap)result.get(i)).get("13").toString());
						}
                      map.toString();
                      userlist.add(map);
              		  
    				}
    				 JSONArray jsonstr=JSONArray.fromObject(userlist);
            		   userinfo=jsonstr.toString();
            		   System.out.println(userinfo);
    			}
    			else {
					userinfo="没有此时间后的用户内容";
				}
    		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
   			DBUtil.closeConnection(conn);
   		}
       	 return userinfo;
        }

        	
      /*  public static void main(String[] args) {
			initCusService init=new initCusService();
//			String qa=init.initCustomer("B0024");
			String userinfo=init.queryUserById("063972");
			System.out.println(userinfo);
		}
        @Test
        public void testBlob(){
        	  Connection con = null;
              PreparedStatement pstmt = null;
              
              int id = 1;
              String sql = "insert into tblfile(id) values(?)";
              try{
                  con = DBUtil.getConnection();
                  DBUtil.beginTrans(con);
                  pstmt = con.prepareStatement(sql);
                  pstmt.setInt(1,id);
                  pstmt.executeUpdate();
                  
                  byte[] bytes = FileUtil.readFile("d:\\品管部.txt");
                  
//                  DBUtil.saveClob(con,"zqb","clob1","id = "+id, id+"$" + buildClob());
                  String key="id='"+id+"'";
                  DBUtil.saveBlob(con,"tblfile","filecontent",key, bytes);
                  DBUtil.commit(con);
              }catch(Exception e){
                  e.printStackTrace();
              }finally{
                  DBUtil.closeStatement(pstmt);
                  DBUtil.rollback(con);
                  DBUtil.closeConnection(con);
              }

        	
        	
        }
        @Test
        public void testdownblob() throws IOException{
        	String id ="1";
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet resultset = null;
    		//String sql = "insert into tblres(id,attachment)values(?,EMPTY_BLOB())";
    		String sql = "select * from tblfile where id = ?";	
            try{
            	con = DBUtil.getJDBCConnection();
            	DBUtil.beginTrans(con);
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,id);
                resultset = pstmt.executeQuery();
                if (resultset.next()) {
    			   Blob blob = resultset.getBlob(2);
    			   String filepath = "D:/郑宏升.txt";
    		          System.out.println("输出文件路径为:" + filepath);
    		          
    		            InputStream in = blob.getBinaryStream(); // 建立输出流
    		            FileOutputStream file = new FileOutputStream(filepath);
    		            int len = (int) blob.length();
    		            byte[] buffer = new byte[len]; // 建立缓冲区
    		            while ( (len = in.read(buffer)) != -1) {
    		              file.write(buffer, 0, len);
    		            }
    		            file.close();
    		            in.close();
    		        }
    			   con.commit();
               
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                DBUtil.closeStatement(pstmt);
                DBUtil.rollback(con);
                DBUtil.closeConnection(con);
            }
    	}
*/
      /*  @Test*/
        public String geneUserid(String userinfo){
        	/*{"ictid":"066346",//用户编号
        		"ictnm":"王安福",//用户姓名
        		"ictpnm":"",//用户亲属姓名
        		"ictgender":"1",//性别，1代男，0代表女
        		"ictbdt":"1937-01-01 00:00:00.0",//出生日期
        		"ictaddr":"0",//用户地址
        		"ictpcd":"",//邮政编码
        		"icttel":"23287210",//联系电话
        		"ictage":"",//年龄
        		"ictpro":"",//省份
        		"ictcity":"",//市
        		"ictcountry":"",//县区
        		"ictphone":""}//手机
*/
        	 
        	
        	 
//        	  guid放了防止重复提交
        	  
        	 /* String userinfo="{guid='111111',ictnm:'郑宏升',ictpnm:'郑父',ictgctid='A0100'," +
        			"ictgender:'1',ictbdt:'1988-01-01 00:00:00'," +
        			"ictaddr:'富阳市回春路11幢404',ictpcd:'1'," +
        			"icttel:'63348878',ictage:'23',ictpro:'1',ictcity:'1'" +
        			",ictcountry:'1',ictphone:'17764590842'}";*/
        	JSONObject userinfojson=JSONObject.fromObject(userinfo);
        	String ictid=null;
        	String result=null;
        	String ictnm=userinfojson.getString("ictnm");
        	String ictpnm=userinfojson.getString("ictpnm");
        	String  gender=userinfojson.getString("ictgender");
        	String  bdt=userinfojson.getString("ictbdt");
        	String addr=userinfojson.getString("ictaddr");
        	String pcd=userinfojson.getString("ictpcd");
        	String tel=userinfojson.getString("icttel");
        	String age=userinfojson.getString("ictage");
        	String pro=userinfojson.getString("ictpro");
        	String city=userinfojson.getString("ictcity");
        	String country=userinfojson.getString("ictcountry");
        	String phone=userinfojson.getString("ictphone");
        	String ictgctid=userinfojson.getString("ictgctid");
        	Date ictdate=DateUtil.getDate();
        	String guid=userinfojson.getString("guid");
        	Connection conn=null;
        	try {
				conn=DBUtil.getConnection();
				String queryuserguid="select userid,guid from tbluserguid where guid='"+guid+"'";
				System.out.println(queryuserguid);
				List guidlist=(Vector)DBUtil.querySQL(conn, queryuserguid);
				if(guidlist.size()>=1){
					String existuserid=(String) ((HashMap)guidlist.get(0)).get("1");
					System.out.println((String)((HashMap)guidlist.get(0)).get("2"));
					if(guid.equals((String)((HashMap)guidlist.get(0)).get("2"))){
						/*return result=queryuserguid;*/
						result=existuserid;
						System.out.println(existuserid);
						return result;
						
					}
					
				}
				else {
					System.out.println("此用户的信息还没上传");
				}
				
				
				List resultlist = (Vector) DBUtil.querySQL(conn,
						"select max(ictid) from tblindclient");
				
				if(null==((HashMap) resultlist.get(0)).get("1"))
				{
					ictid="0";
				}
				else
				{
					ictid = (String) ((HashMap) resultlist.get(0)).get("1");
				}
//				String ictid = (String) ((HashMap) result.get(0)).get("1");
				// System.out.println(ictid);
				Integer id = Integer.valueOf(ictid) + 1;
			
				/*List result = (Vector) DBUtil.querySQL(con,
						"select SEQ_ADGCTID.nextval from dual");
				
				BigDecimal id = (BigDecimal) ((HashMap) result.get(0)).get("1");
				*/
				ictid=id.toString();		
				ictid = String.format("%06d", id);//以0开始的6位数字
				String sql="insert into TBLINDCLIENT(ictid,ictnm,ictpnm,ictbdt,ictaddr,ictpcd,icttel,ictage,ictpro,ictcity,ictcounty,ictphone,ictgender,ictdate,ictgctid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String insertuserguidsql="insert into tbluserguid(userid,guid) values(?,?)";

				System.out.println(sql);
				PreparedStatement pre;
				
					pre = conn.prepareStatement(sql);
					pre.setString(1, ictid);
					pre.setString(2, ictnm);
					pre.setString(3,ictpnm);
					pre.setDate(4, (Date) DateUtil.converToDate(bdt));
					pre.setString(5, addr);
					pre.setString(6, pcd);
					pre.setString(7, tel);
					pre.setInt(8, Integer.valueOf(age));
					pre.setString(9, pro);
					pre.setString(10, city);
					pre.setString(11, country);
					pre.setString(12, phone);
					pre.setString(13, gender);
					pre.setDate(14,ictdate);
					pre.setString(15, ictgctid);
					int i=pre.executeUpdate();
					if(i>0){
						System.out.println("插入用户档案成功");
						result=ictid;
					}
					else {
						System.out.println("插入失败");
					}
					pre=conn.prepareStatement(insertuserguidsql);
					pre.setString(1, ictid);
					pre.setString(2, guid);
					int j=pre.executeUpdate();
					if(j>0){
					System.out.println("插入用户guid表成功");	
					}
					else {
						System.out.println("插入失败");
					}
				conn.commit();
				System.out.println(result);
				
			} catch (NoConnectionException e) {
				// TODO Auto-generated catch block
				result="用户"+ictnm+"的档案上传失败，失败原因"+e.getMessage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	finally{
        		DBUtil.rollback(conn);
        		DBUtil.closeConnection(conn);
        	}
        	return result;
        }
        	
        	
}
