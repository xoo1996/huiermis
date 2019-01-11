package com.cm.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.radf.apps.commons.entity.Charge;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;

public class BatteryService {

	/**
	 * 新增
	 * @param orderId
	 * @param chargeId
	 * @return
	 */
	public String delmembat(String orderId,String chargeId){
		Connection conn = null;
		String sql = null;
		if(orderId != null)
			sql="UPDATE MEM_BAT SET STATUS = 'del' WHERE ORDER_ID = "+orderId;
		if(chargeId != null)
			sql="UPDATE MEM_BAT SET STATUS = 'del' WHERE CHARGE_ID = "+chargeId;
		try{
			conn = DBUtil.getConnection();
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
			return "true";
		}catch(Exception e){
			return "false";
		}
		
	}
	
	/**
	 * 新增
	 * membat中插入数据
	 * @param user
	 * @param name
	 * @param phone
	 * @param account
	 * @param gby
	 * @param bm
	 * @param orderId
	 * @param chargeId
	 * @return
	 */
	public String addmembat(String user,String name,String phone,String account,String gby,String bm,String orderId,String chargeId){
		Connection conn = null;
		Date scdt=DateUtil.getSystemCurrentTime();
		if(orderId == null)
			orderId = "";
		if(chargeId == null)
			chargeId = "";
		try{
			conn = DBUtil.getConnection();
			String sql="insert into MEM_BAT (ID, MEMBER_NO, BATTERY_TYPE, BATTERY_QTY, BATTERY_RE_QTY, STORE_NO, NAME,PHONE,ORDER_ID,CHARGE_ID,STATUS,DATA) "
			+"values (MEM_BAT_ID.nextval,'"+user+"','"+bm+"','"+gby+"','"+gby+"','"+account+"','"+name+"','"+phone+"','"+orderId+"','"+chargeId+"','full',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'))";
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	
	/**
	 * 电池操作表
	 * 为了不让老系统过多改变，老系统中的member_no就是member_id
	 * @param number
	 * @param bsc011
	 * @param type
	 * @return
	 */
	public String batEvent(String memberNo, String inStoreNo, String getStoreNo, String batteryType, long getBatNum,Long memBatId,String thisBatEventId){
		Connection conn = null;
		Date scdt=DateUtil.getSystemCurrentTime();
		try{
			conn = DBUtil.getConnection();
			String sql="insert into bat_event (ID, MEMBER_NO, IN_STORE_NO, GET_STORE_NO, BATTERY_TYPE, GET_BAT_NUM, HANDLE_DATE,MEMBAT_ID,BATEVENT_ID) values (BAT_EVENT_ID.nextval,'"+memberNo+"','"+inStoreNo+"','"+getStoreNo+"','"+batteryType+"','"+getBatNum+"',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'"+memBatId+"','"+thisBatEventId+"')";
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
//			finally {
//    			DBUtil.closeConnection(conn);
//    		}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	public String batEventChange(String getBatNum,String batEvnentId ){
		Connection conn = null;

		try{
			conn = DBUtil.getConnection();
			String sql="update BAT_EVENT set GET_BAT_NUM='"+getBatNum+"' where BATEVENT_ID='" + batEvnentId + "'";
			PreparedStatement ps = null;
			try {

				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	
	
	/**
	 * 电池库存改变
	 * @param number
	 * @param bsc011
	 * @param type
	 * @return
	 */
	public String battChange(String number,String bsc011,String type){
		Connection conn = null;
		Date scdt=DateUtil.getSystemCurrentTime();
		try{
			conn = DBUtil.getConnection();
			String sql2 = "SELECT pdtnm,pdtprc FROM tblproduct s where s.pdtid='" + type+ "'";
			List result = (Vector) DBUtil.querySQL(conn, sql2);
			String name = "";
			String price = "";
			if(result.size()>0){
				if(((HashMap) result.get(0)).get("1")!=null){
					name=(String)((HashMap)result.get(0)).get("1");
					price=((HashMap)result.get(0)).get("2").toString();
				}
			}
			String sql3="select SEQ_CHGID.nextval from dual";
			List result1 = (Vector) DBUtil.querySQL(conn, sql3);
			BigDecimal id=(BigDecimal)((HashMap)result1.get(0)).get("1");
			String chgid = id.toString();
			String sql= "";
			if(Long.parseLong(number)>0)
				sql="insert into tblsto (stoid, stogrcliid, stoproid, stoproname, stoamountun, stoamount, stoactype, storemark, stodate, stoproprice, stodisc) values (SEQ_STOID.NEXTVAL,'"+bsc011+"','"+type+"','"+name+"','颗','"+number+"','-1','电池出库',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'"+price+"','0')";
			else if(Long.parseLong(number)<0)
				sql="insert into tblsto (stoid, stogrcliid, stoproid, stoproname, stoamountun, stoamount, stoactype, storemark, stodate, stoproprice, stodisc) values (SEQ_STOID.NEXTVAL,'"+bsc011+"','"+type+"','"+name+"','颗','"+number+"','1','电池入库',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'"+price+"','0')";
			else
				return "true";
			String sql1="insert into tblnorchgdetail(ncdid,ncdpid,ncdqnt,ncddis,ncdmon,ncdsta,ncdnt) values ('"+chgid+"','"+type+"','"+number+"','1','0','finish','会员系统操作')";
			String sql0="insert into tblnorchg (chgid,chggcltid,chgcltid,chgdt,ncdnum) values ('"+chgid+"','"+bsc011+"','',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'0')";
			
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				ps = conn.prepareStatement(sql1);
				ps.executeUpdate();
				ps = conn.prepareStatement(sql0);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
			finally {
    			DBUtil.closeConnection(conn);
    		}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	
	
	/**
	 * 新增
	 * 电池库存改变，返还电池
	 * @param number
	 * @param bsc011
	 * @param type
	 * @return
	 */
	public String reBat(String number,String bsc011,String type){
		Connection conn = null;
		Date scdt=DateUtil.getSystemCurrentTime();
		try{
			conn = DBUtil.getConnection();
			String sql2 = "SELECT pdtnm,pdtprc FROM tblproduct s where s.pdtid='" + type+ "'";
			List result = (Vector) DBUtil.querySQL(conn, sql2);
			String name = "";
			String price = "";
			if(result.size()>0){
				if(((HashMap) result.get(0)).get("1")!=null){
					name=(String)((HashMap)result.get(0)).get("1");
					price=((HashMap)result.get(0)).get("2").toString();
				}
			}
			String sql3="select SEQ_CHGID.nextval from dual";
			List result1 = (Vector) DBUtil.querySQL(conn, sql3);
			BigDecimal id=(BigDecimal)((HashMap)result1.get(0)).get("1");
			String chgid = id.toString();
			String sql="insert into tblsto (stoid, stogrcliid, stoproid, stoproname, stoamountun, stoamount, stoactype, storemark, stodate, stoproprice, stodisc) values (SEQ_STOID.NEXTVAL,'"+bsc011+"','"+type+"','"+name+"','颗','"+number+"','1','电池入库',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'"+price+"','0')";
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
//			finally {
//    			DBUtil.closeConnection(conn);
//    		}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	
	
	/**
	 * 插入积分tblscore数据库
	 * @param bsc011
	 * @param score
	 * @return
	 */
	public String insertScore(String bsc011,String score,String detail,String pdtid,String num,String changemon,String actiscore,String buyscore,String applycoin,String changecoin){
		Connection conn = null;
		Date scdt=DateUtil.getSystemCurrentTime();
		try{
			conn = DBUtil.getConnection();
			//DBUtil.beginTrans(conn);
			//事务捆绑在一起
			String sql="insert into tblscore(bsc011,scoredate,score,detail,pdtid,num,changemon,actiscore,buyscore,applycoin,changecoin) values ('"+bsc011+"',to_date('"+scdt+"','yyyy-MM-dd HH24:mi:ss'),'"+score+"','"+detail+"','"+pdtid+"','"+num+"','"+changemon+"','"+actiscore+"','"+buyscore+"','"+applycoin+"','"+changecoin+"')";
			PreparedStatement ps = null;
			try {
				// 打开数据库连接并清楚原来记录集
				ps = conn.prepareStatement(sql);
				ps.execute();
				//System.out.println(i);
				//DBUtil.commit(conn);
				conn.close();
			} catch (SQLException ex) {
				throw new SQLException(ex.getMessage());
			}
			return "true";
		}catch(Exception e){
			return "false";
		}
	}
	
	/**
	 * 返回电池数量
	 * @param bsc011
	 * @param type
	 * @return
	 */
	public String battNum(String bsc011,String type){
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			//DBUtil.beginTrans(conn);
			String sql = "SELECT DISTINCT SUM(s.stoamount*s.stoactype)over(partition BY s.stoproid) AS amount FROM tblsto s where s.stoproid='" + type + "' and s.STOGRCLIID='" + bsc011 + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			String num="";
			if(result.size()>0){
				if(((HashMap) result.get(0)).get("1")!=null){
					num=((HashMap)result.get(0)).get("1").toString();
				}
			}
			//int num = (Integer) ((HashMap) result.get(0)).get("1");
			return num;
		}catch(Exception e){
			return "0";
		}
	}
	/**
	 * 判断是否是老用户,是的话返回联系电话
	 * @param memberNo
	 * @return
	 */
	public String judgeOld(String memberNo){
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "SELECT icttel,ictid FROM TBLINDCLIENT where ictid='" + memberNo + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			if(result.size()>0){
				String icttel=String.valueOf(((HashMap)result.get(0)).get("1"));
				String ict=icttel.replaceAll(" ", "");
				return ict;
			}
			return "false";
		}catch(Exception e){
			return "false";
		}
	}
	/**
	 * 判断是否是老用户,是的话电话更新联系电话
	 * @param memberNo
	 * @return
	 */
	public String checkTel(String memberNo,String phone){
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "SELECT icttel,ictid FROM TBLINDCLIENT where ictid='" + memberNo + "'";
			List result = (Vector) DBUtil.querySQL(conn, sql);
			if(result.size()>0){
				String icttel=String.valueOf(((HashMap)result.get(0)).get("1"));
				if(!phone.equals(icttel)){
					sql="update TBLINDCLIENT set icttel='"+phone+"' where ictid='" + memberNo + "'";
					PreparedStatement ps = null;
					try {
						// 打开数据库连接并清楚原来记录集
						ps = conn.prepareStatement(sql);
						ps.execute();
						conn.close();
					} catch (SQLException ex) {
						return "false";
					}
				}
				return icttel;
			}
			return "false";
		}catch(Exception e){
			return "false";
		}
	}
}
