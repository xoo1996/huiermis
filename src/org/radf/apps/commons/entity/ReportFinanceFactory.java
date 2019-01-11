package org.radf.apps.commons.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.radf.plat.commons.DBUtil;
import org.radf.plat.util.exception.NoConnectionException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReportFinanceFactory implements JRDataSource {

	private List<FinanceReport> data ;
	
	public List<FinanceReport> setCollection(String gctid,String finsubdtstart,
			String finsubdtend, String finpassdtstart, String finpassdtend,
			String chgdtstart, String chgdtend,String gcttype) throws Exception {

		 List<FinanceReport> data = new ArrayList<FinanceReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "select * from (SELECT DISTINCT c.fintype,c.notype,c.gctid,c.pdtid,c.bindno,c.pdtnm,g.gctsname," +
					"g.taxno,g.gctemail,c.finnt,decode(r.fpdtnm,null,'配件',r.fpdtnm) as fpdtnm,(select taxrate from tblfinconfig where id='1') as taxrate,c.pdtnm as fpdtmodel,p.pdtut," +
					"SUM(c.pdtnum) OVER(PARTITION BY c.fintype,c.gctid,c.pdtid) AS pdtnum,p.pdtprc,c.finrate,c.finprc,g.gctmobilephone " +
					"FROM tblfinance c LEFT JOIN tblgrpclient g ON g.gctid = c.gctid " +
					"LEFT JOIN TBLFINRATE r ON c.pdtnm like '%'||r.band||'%' " +
					"LEFT JOIN TBLPRODUCT p ON p.pdtid = c.pdtid where c.bindno is not null and c.fintype = 'normal' ";
			
			
			if(gctid!=null&&!gctid.equals("")){
				sql+=" and c.gctid ='"+gctid+"' ";
			}
			if(gcttype!=null&&!gcttype.equals("")){
				sql+=" and g.gcttype ='"+gcttype+"' ";
			}
			if(finsubdtstart!=null&&!finsubdtstart.equals("")){
				sql+=" and c.finsubdt >= to_date('"+finsubdtstart+"','YYYY-MM-DD') ";
			}
			if(finsubdtend!=null&&!finsubdtend.equals("")){
				sql+=" and c.finsubdt <= (to_date('"+finsubdtend+"','YYYY-MM-DD')+1) ";
			}
			if(finpassdtstart!=null&&!finpassdtstart.equals("")){
				sql+=" and c.finpassdt >= to_date('"+finpassdtstart+"','YYYY-MM-DD') ";
			}
			if(finpassdtend!=null&&!finpassdtend.equals("")){
				sql+=" and c.finpassdt <= (to_date('"+finpassdtend+"','YYYY-MM-DD')+1) ";
			}
			if(chgdtstart!=null&&!chgdtstart.equals("")){
				sql+=" and c.chgdt >= to_date('"+chgdtstart+"','yyyy-mm-dd') ";
			}
			if(chgdtend!=null&&!chgdtend.equals("")){
				sql+=" and c.chgdt <= (to_date('"+chgdtend+"','yyyy-mm-dd')+1) ";
			}
			
			String sql1_1=") order by bindno,fintype,notype,gctid,pdtid ";
			sql+=sql1_1;
			System.out.println(sql);
			Statement pstmt;
			pstmt = con.createStatement();
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				FinanceReport financeReport = new FinanceReport();

				financeReport.setBindno(Long.valueOf(rs.getString("bindno")));
				financeReport.setGctsname(rs.getString("gctsname"));
				
				if(rs.getString("taxno")==null){
					financeReport.setTaxno("");
				}else{
					financeReport.setTaxno(rs.getString("taxno"));				
				}
				
				if(rs.getString("gctemail")==null){
					financeReport.setGctemail("");
				}else{
					//financeReport.setGctemail(rs.getString("gctemail"));		
					financeReport.setGctemail(rs.getString("gctemail"));	
				}
				
				if(rs.getString("gctmobilephone")==null){
					financeReport.setGctmobilephone("");
				}else{
					financeReport.setGctmobilephone(rs.getString("gctmobilephone"));			
				}

				
				if(rs.getString("finnt")==null){
					financeReport.setFinnt("");
				}else{
					financeReport.setFinnt(rs.getString("finnt"));
				}
				
				financeReport.setFpdtnm(rs.getString("fpdtnm"));
				financeReport.setTaxrate(rs.getString("taxrate"));
				financeReport.setFpdtmodel(rs.getString("fpdtmodel"));
				financeReport.setPdtut(rs.getString("pdtut"));
				financeReport.setPdtnum(rs.getString("pdtnum"));
				financeReport.setFinprc(rs.getString("finprc"));
				financeReport.setPdtprc(rs.getString("pdtprc"));
				financeReport.setFinrate(rs.getString("finrate"));
				
				//financeReport.setTmksid(rs.getString("tmksid"));
				//financeReport.setSellprc(rs.getString("sellprc"));
				
				String sql2="select tmksid,sellprc from tblfinance where bindno='"+rs.getString("bindno")+"' and pdtnm='"+rs.getString("pdtnm")+"'";
				System.out.println(sql2);
				ResultSet rs2=pstmt2.executeQuery(sql2);
				String tmksid="";
				String sellprc="";
				int flag=1;
				while (rs2.next()) {
					if(flag==1){//第一个输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=rs2.getString("sellprc");
						}
					}else{//后边的输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=","+rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=","+rs2.getString("sellprc");
						}
					}
					flag=0;
				}
				rs2.close();
				
				financeReport.setTmksid(tmksid);
				financeReport.setSellprc(sellprc);
				
				data.add(financeReport);
			}
		   rs.close();
		   
		   pstmt.close();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
		}
		  
	      this.data = data;
	      return data;
	 }
	
	public List<FinanceReport> setCollection2() throws Exception {

		 List<FinanceReport> data = new ArrayList<FinanceReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "select * from (SELECT DISTINCT c.fintype,c.notype,c.gctid,c.pdtid,c.bindno,c.pdtnm,g.gctsname," +
					"g.taxno,g.gctemail,c.finnt,decode(r.fpdtnm,null,'配件',r.fpdtnm) as fpdtnm,(select taxrate from tblfinconfig where id='1') as taxrate,c.pdtnm as fpdtmodel,p.pdtut," +
					"SUM(c.pdtnum) OVER(PARTITION BY c.fintype,c.gctid,c.pdtid) AS pdtnum,p.pdtprc,c.finrate,c.finprc,g.gctmobilephone " +
					"FROM tblfinance c LEFT JOIN tblgrpclient g ON g.gctid = c.gctid " +
					"LEFT JOIN TBLFINRATE r ON c.pdtnm like '%'||r.band||'%' " +
					"LEFT JOIN TBLPRODUCT p ON p.pdtid = c.pdtid where c.bindno is not null and c.fintype = 'not' " +
					" and c.chgdt >= last_day(add_months(SYSDATE,-3)) " +
					" and c.chgdt <= last_day(add_months(sysdate, -2)) ) order by bindno,fintype,notype,gctid,pdtid ";
			System.out.println(sql);
			Statement pstmt;
			pstmt = con.createStatement();
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				FinanceReport financeReport = new FinanceReport();

				financeReport.setBindno(Long.valueOf(rs.getString("bindno")));
				financeReport.setGctsname(rs.getString("gctsname"));
				
				if(rs.getString("taxno")==null){
					financeReport.setTaxno("");
				}else{
					financeReport.setTaxno(rs.getString("taxno"));				
				}
				
				if(rs.getString("gctemail")==null){
					financeReport.setGctemail("");
				}else{
					financeReport.setGctemail(rs.getString("gctemail"));			
				}

				if(rs.getString("gctmobilephone")==null){
					financeReport.setGctmobilephone("");
				}else{
					financeReport.setGctmobilephone(rs.getString("gctmobilephone"));			
				}
				
				if(rs.getString("finnt")==null){
					financeReport.setFinnt("");
				}else{
					financeReport.setFinnt(rs.getString("finnt"));
				}
				
				financeReport.setFpdtnm(rs.getString("fpdtnm"));
				financeReport.setTaxrate(rs.getString("taxrate"));
				financeReport.setFpdtmodel(rs.getString("fpdtmodel"));
				financeReport.setPdtut(rs.getString("pdtut"));
				financeReport.setPdtnum(rs.getString("pdtnum"));
				financeReport.setFinprc(rs.getString("finprc"));
				financeReport.setPdtprc(rs.getString("pdtprc"));
				financeReport.setFinrate(rs.getString("finrate"));
				
				//financeReport.setTmksid(rs.getString("tmksid"));
				//financeReport.setSellprc(rs.getString("sellprc"));
				
				String sql2="select tmksid,sellprc from tblfinance where bindno='"+rs.getString("bindno")+"' and pdtnm='"+rs.getString("pdtnm")+"'";
				//System.out.println(sql2);
				ResultSet rs2=pstmt2.executeQuery(sql2);
				String tmksid="";
				String sellprc="";
				int flag=1;
				while (rs2.next()) {
					if(flag==1){//第一个输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=rs2.getString("sellprc");
						}
					}else{//后边的输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=","+rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=","+rs2.getString("sellprc");
						}
					}
					flag=0;
				}
				rs2.close();
				
				financeReport.setTmksid(tmksid);
				financeReport.setSellprc(sellprc);
				
				data.add(financeReport);
			}
		   rs.close();
		   
		   pstmt.close();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
		}
		  
	      this.data = data;
	      return data;
	 }
	 
	public List<FinanceReport> setCollection3(String gctid,String finsubdtstart,
			String finsubdtend, String finpassdtstart, String finpassdtend,
			String chgdtstart, String chgdtend,String gcttype) throws Exception {

		 List<FinanceReport> data = new ArrayList<FinanceReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "select * from (SELECT DISTINCT c.fintype,c.notype,c.typeno,c.gctid,c.pdtid,c.bindno,c.pdtnm,c.gctnm,c.retailname,c.retailtaxno," +
					"g.taxno,g.gctemail,c.finnt,decode(r.fpdtnm,null,'配件',r.fpdtnm) as fpdtnm,(select taxrate from tblfinconfig where id='1') as taxrate,c.pdtnm as fpdtmodel,p.pdtut," +
					"c.pdtnum AS pdtnum,p.pdtprc,c.finrate,c.finprc,g.gctmobilephone " +
					"FROM tblfinance c LEFT JOIN tblgrpclient g ON g.gctid = c.gctid " +
					"LEFT JOIN TBLFINRATE r ON c.pdtnm like '%'||r.band||'%' " +
					"LEFT JOIN TBLPRODUCT p ON p.pdtid = c.pdtid where c.bindno is not null and c.fintype = 'retail' ";
			
			
			if(gctid!=null&&!gctid.equals("")){
				sql+=" and c.gctid ='"+gctid+"' ";
			}
			if(gcttype!=null&&!gcttype.equals("")){
				sql+=" and g.gcttype ='"+gcttype+"' ";
			}
			if(finsubdtstart!=null&&!finsubdtstart.equals("")){
				sql+=" and c.finsubdt >= to_date('"+finsubdtstart+"','YYYY-MM-DD') ";
			}
			if(finsubdtend!=null&&!finsubdtend.equals("")){
				sql+=" and c.finsubdt <= (to_date('"+finsubdtend+"','YYYY-MM-DD')+1) ";
			}
			if(finpassdtstart!=null&&!finpassdtstart.equals("")){
				sql+=" and c.finpassdt >= to_date('"+finpassdtstart+"','YYYY-MM-DD') ";
			}
			if(finpassdtend!=null&&!finpassdtend.equals("")){
				sql+=" and c.finpassdt <= (to_date('"+finpassdtend+"','YYYY-MM-DD')+1) ";
			}
			if(chgdtstart!=null&&!chgdtstart.equals("")){
				sql+=" and c.chgdt >= to_date('"+chgdtstart+"','yyyy-mm-dd') ";
			}
			if(chgdtend!=null&&!chgdtend.equals("")){
				sql+=" and c.chgdt <= (to_date('"+chgdtend+"','yyyy-mm-dd')+1) ";
			}
			
			String sql1_1=") order by bindno,fintype,notype,gctid,retailname ";
			sql+=sql1_1;
			System.out.println(sql);
			Statement pstmt;
			pstmt = con.createStatement();
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				FinanceReport financeReport = new FinanceReport();

				financeReport.setBindno(Long.valueOf(rs.getString("bindno")));
				financeReport.setGctnm(rs.getString("gctnm"));
				
				if(rs.getString("taxno")==null){
					financeReport.setTaxno("");
				}else{
					financeReport.setTaxno(rs.getString("taxno"));				
				}
				
				if(rs.getString("gctemail")==null){
					financeReport.setGctemail("");
				}else{
					//financeReport.setGctemail(rs.getString("gctemail"));		
					financeReport.setGctemail(rs.getString("gctemail"));	
				}
				
				if(rs.getString("gctmobilephone")==null){
					financeReport.setGctmobilephone("");
				}else{
					financeReport.setGctmobilephone(rs.getString("gctmobilephone"));			
				}

				
				if(rs.getString("finnt")==null){
					financeReport.setFinnt("");
				}else{
					financeReport.setFinnt(rs.getString("finnt"));
				}
				
				if(rs.getString("retailname")==null){
					financeReport.setRetailname("");
				}else{
					financeReport.setRetailname(rs.getString("retailname"));
				}
				
				if(rs.getString("retailtaxno")==null){
					financeReport.setRetailtaxno("");
				}else{
					financeReport.setRetailtaxno(rs.getString("retailtaxno"));
				}
				
				financeReport.setFpdtnm(rs.getString("fpdtnm"));
				financeReport.setTaxrate(rs.getString("taxrate"));
				financeReport.setFpdtmodel(rs.getString("fpdtmodel"));
				financeReport.setPdtut(rs.getString("pdtut"));
				financeReport.setPdtnum(rs.getString("pdtnum"));
				financeReport.setFinprc(rs.getString("finprc"));
				financeReport.setPdtprc(rs.getString("pdtprc"));
				financeReport.setFinrate(rs.getString("finrate"));
				
				//financeReport.setTmksid(rs.getString("tmksid"));
				//financeReport.setSellprc(rs.getString("sellprc"));
				
				String sql2="select tmksid from tblfinance where bindno='"+rs.getString("bindno")+"' and typeno='"+rs.getString("typeno")+"'";
				System.out.println(sql2);
				ResultSet rs2=pstmt2.executeQuery(sql2);
				String tmksid="";
				int flag=1;
				while (rs2.next()) {
					if(flag==1){//第一个输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=rs2.getString("tmksid");
						}
					}else{//后边的输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=","+rs2.getString("tmksid");
						}
					}
					flag=0;
				}
				rs2.close();
				
				financeReport.setTmksid(tmksid);
				
				data.add(financeReport);
			}
		   rs.close();
		   
		   pstmt.close();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
		}
		  
	      this.data = data;
	      return data;
	 }
	
	public List<FinanceReport> setCollection4(String gctid,
			String finsubdtstart, String finsubdtend, String finpassdtstart,
			String finpassdtend, String chgdtstart, String chgdtend,
			String gcttype)  throws Exception  {

		 List<FinanceReport> data = new ArrayList<FinanceReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "select * from (SELECT DISTINCT c.fintype,c.notype,c.gctid,c.pdtid,c.bindno,c.pdtnm,g.gctsname," +
					"g.taxno,g.gctemail,g.gctaddr,g.gctdepositbank,g.gctdepositid,gcttel,c.finnt,decode(r.fpdtnm,null,'配件',r.fpdtnm) as fpdtnm,(select taxrate from tblfinconfig where id='1') as taxrate,c.pdtnm as fpdtmodel,p.pdtut," +
					"SUM(c.pdtnum) OVER(PARTITION BY c.fintype,c.gctid,c.pdtid) AS pdtnum,p.pdtprc,c.finrate,c.finprc,g.gctmobilephone " +
					"FROM tblfinance c LEFT JOIN tblgrpclient g ON g.gctid = c.gctid " +
					"LEFT JOIN TBLFINRATE r ON c.pdtnm like '%'||r.band||'%' " +
					"LEFT JOIN TBLPRODUCT p ON p.pdtid = c.pdtid where c.bindno is not null and c.fintype = 'special' ";
			
			
			if(gctid!=null&&!gctid.equals("")){
				sql+=" and c.gctid ='"+gctid+"' ";
			}
			if(gcttype!=null&&!gcttype.equals("")){
				sql+=" and g.gcttype ='"+gcttype+"' ";
			}
			if(finsubdtstart!=null&&!finsubdtstart.equals("")){
				sql+=" and c.finsubdt >= to_date('"+finsubdtstart+"','YYYY-MM-DD') ";
			}
			if(finsubdtend!=null&&!finsubdtend.equals("")){
				sql+=" and c.finsubdt <= (to_date('"+finsubdtend+"','YYYY-MM-DD')+1) ";
			}
			if(finpassdtstart!=null&&!finpassdtstart.equals("")){
				sql+=" and c.finpassdt >= to_date('"+finpassdtstart+"','YYYY-MM-DD') ";
			}
			if(finpassdtend!=null&&!finpassdtend.equals("")){
				sql+=" and c.finpassdt <= (to_date('"+finpassdtend+"','YYYY-MM-DD')+1) ";
			}
			if(chgdtstart!=null&&!chgdtstart.equals("")){
				sql+=" and c.chgdt >= to_date('"+chgdtstart+"','yyyy-mm-dd') ";
			}
			if(chgdtend!=null&&!chgdtend.equals("")){
				sql+=" and c.chgdt <= (to_date('"+chgdtend+"','yyyy-mm-dd')+1) ";
			}
			
			String sql1_1=") order by bindno,fintype,notype,gctid,pdtid ";
			sql+=sql1_1;
			System.out.println(sql);
			Statement pstmt;
			pstmt = con.createStatement();
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				FinanceReport financeReport = new FinanceReport();

				financeReport.setBindno(Long.valueOf(rs.getString("bindno")));
				financeReport.setGctsname(rs.getString("gctsname"));
				
				if(rs.getString("taxno")==null){
					financeReport.setTaxno("");
				}else{
					financeReport.setTaxno(rs.getString("taxno"));				
				}
				
				if(rs.getString("gctemail")==null){
					financeReport.setGctemail("");
				}else{
					//financeReport.setGctemail(rs.getString("gctemail"));		
					financeReport.setGctemail(rs.getString("gctemail"));	
				}
				
				if(rs.getString("gctaddr")==null){
					financeReport.setGctaddr("");
				}else{	
					financeReport.setGctaddr(rs.getString("gctaddr"));	
				}
				
				if(rs.getString("gctdepositbank")==null){
					financeReport.setGctdepositbank("");
				}else{
					financeReport.setGctdepositbank(rs.getString("gctdepositbank"));	
				}
				
				if(rs.getString("gctdepositid")==null){
					financeReport.setGctdepositid("");
				}else{
					financeReport.setGctdepositid(rs.getString("gctdepositid"));	
				}
				
				if(rs.getString("gcttel")==null){
					financeReport.setGcttel("");
				}else{
					financeReport.setGcttel(rs.getString("gcttel"));	
				}
				
				if(rs.getString("gctmobilephone")==null){
					financeReport.setGctmobilephone("");
				}else{
					financeReport.setGctmobilephone(rs.getString("gctmobilephone"));			
				}

				
				if(rs.getString("finnt")==null){
					financeReport.setFinnt("");
				}else{
					financeReport.setFinnt(rs.getString("finnt"));
				}
				
				financeReport.setFpdtnm(rs.getString("fpdtnm"));
				financeReport.setTaxrate(rs.getString("taxrate"));
				financeReport.setFpdtmodel(rs.getString("fpdtmodel"));
				financeReport.setPdtut(rs.getString("pdtut"));
				financeReport.setPdtnum(rs.getString("pdtnum"));
				financeReport.setFinprc(rs.getString("finprc"));
				financeReport.setPdtprc(rs.getString("pdtprc"));
				financeReport.setFinrate(rs.getString("finrate"));
				
				//financeReport.setTmksid(rs.getString("tmksid"));
				//financeReport.setSellprc(rs.getString("sellprc"));
				
				String sql2="select tmksid,sellprc from tblfinance where bindno='"+rs.getString("bindno")+"' and pdtnm='"+rs.getString("pdtnm")+"'";
				System.out.println(sql2);
				ResultSet rs2=pstmt2.executeQuery(sql2);
				String tmksid="";
				String sellprc="";
				int flag=1;
				while (rs2.next()) {
					if(flag==1){//第一个输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=rs2.getString("sellprc");
						}
					}else{//后边的输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=","+rs2.getString("tmksid");
						}
						if(rs2.getString("sellprc")==null){
							sellprc+="";
						}else{
							sellprc+=","+rs2.getString("sellprc");
						}
					}
					flag=0;
				}
				rs2.close();
				
				financeReport.setTmksid(tmksid);
				financeReport.setSellprc(sellprc);
				
				data.add(financeReport);
			}
		   rs.close();
		   
		   pstmt.close();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
		}
		  
	      this.data = data;
	      return data;
	}

	public List<FinanceReport> setCollection5(String gctid,
			String finsubdtstart, String finsubdtend, String finpassdtstart,
			String finpassdtend, String chgdtstart, String chgdtend,
			String gcttype) throws Exception {
		List<FinanceReport> data = new ArrayList<FinanceReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "select * from (SELECT DISTINCT c.fintype,c.notype,c.typeno,c.gctid,c.pdtid,c.bindno,c.pdtnm,c.retailname,c.retailtaxno,c.depositbank,c.depositid,c.comaddr,c.specialtel," +
					"g.taxno,g.gctemail,c.finnt,decode(r.fpdtnm,null,'配件',r.fpdtnm) as fpdtnm,(select taxrate from tblfinconfig where id='1') as taxrate,c.pdtnm as fpdtmodel,p.pdtut," +
					"c.pdtnum AS pdtnum,p.pdtprc,c.finrate,c.finprc,g.gctmobilephone " +
					"FROM tblfinance c LEFT JOIN tblgrpclient g ON g.gctid = c.gctid " +
					"LEFT JOIN TBLFINRATE r ON c.pdtnm like '%'||r.band||'%' " +
					"LEFT JOIN TBLPRODUCT p ON p.pdtid = c.pdtid where c.bindno is not null and c.fintype = 'speretail' ";
			
			
			if(gctid!=null&&!gctid.equals("")){
				sql+=" and c.gctid ='"+gctid+"' ";
			}
			if(gcttype!=null&&!gcttype.equals("")){
				sql+=" and g.gcttype ='"+gcttype+"' ";
			}
			if(finsubdtstart!=null&&!finsubdtstart.equals("")){
				sql+=" and c.finsubdt >= to_date('"+finsubdtstart+"','YYYY-MM-DD') ";
			}
			if(finsubdtend!=null&&!finsubdtend.equals("")){
				sql+=" and c.finsubdt <= (to_date('"+finsubdtend+"','YYYY-MM-DD')+1) ";
			}
			if(finpassdtstart!=null&&!finpassdtstart.equals("")){
				sql+=" and c.finpassdt >= to_date('"+finpassdtstart+"','YYYY-MM-DD') ";
			}
			if(finpassdtend!=null&&!finpassdtend.equals("")){
				sql+=" and c.finpassdt <= (to_date('"+finpassdtend+"','YYYY-MM-DD')+1) ";
			}
			if(chgdtstart!=null&&!chgdtstart.equals("")){
				sql+=" and c.chgdt >= to_date('"+chgdtstart+"','yyyy-mm-dd') ";
			}
			if(chgdtend!=null&&!chgdtend.equals("")){
				sql+=" and c.chgdt <= (to_date('"+chgdtend+"','yyyy-mm-dd')+1) ";
			}
			
			String sql1_1=") order by bindno,fintype,notype,gctid,retailname ";
			sql+=sql1_1;
			System.out.println(sql);
			Statement pstmt;
			pstmt = con.createStatement();
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				FinanceReport financeReport = new FinanceReport();

				financeReport.setBindno(Long.valueOf(rs.getString("bindno")));
				//financeReport.setGctnm(rs.getString("gctnm"));
				
				if(rs.getString("taxno")==null){
					financeReport.setTaxno("");
				}else{
					financeReport.setTaxno(rs.getString("taxno"));				
				}
				
				if(rs.getString("gctemail")==null){
					financeReport.setGctemail("");
				}else{
					//financeReport.setGctemail(rs.getString("gctemail"));		
					financeReport.setGctemail(rs.getString("gctemail"));	
				}
				
				if(rs.getString("gctmobilephone")==null){
					financeReport.setGctmobilephone("");
				}else{
					financeReport.setGctmobilephone(rs.getString("gctmobilephone"));			
				}
				
				if(rs.getString("comaddr")==null){
					financeReport.setComaddr("");
				}else{	
					financeReport.setComaddr(rs.getString("comaddr"));	
				}
				
				if(rs.getString("depositbank")==null){
					financeReport.setDepositbank("");
				}else{
					financeReport.setDepositbank(rs.getString("depositbank"));	
				}
				
				if(rs.getString("depositid")==null){
					financeReport.setDepositid("");
				}else{
					financeReport.setDepositid(rs.getString("depositid"));	
				}
				
				if(rs.getString("specialtel")==null){
					financeReport.setSpecialtel("");
				}else{
					financeReport.setSpecialtel(rs.getString("specialtel"));	
				}
				
				if(rs.getString("finnt")==null){
					financeReport.setFinnt("");
				}else{
					financeReport.setFinnt(rs.getString("finnt"));
				}
				
				if(rs.getString("retailname")==null){
					financeReport.setRetailname("");
				}else{
					financeReport.setRetailname(rs.getString("retailname"));
				}
				
				if(rs.getString("retailtaxno")==null){
					financeReport.setRetailtaxno("");
				}else{
					financeReport.setRetailtaxno(rs.getString("retailtaxno"));
				}
				
				financeReport.setFpdtnm(rs.getString("fpdtnm"));
				financeReport.setTaxrate(rs.getString("taxrate"));
				financeReport.setFpdtmodel(rs.getString("fpdtmodel"));
				financeReport.setPdtut(rs.getString("pdtut"));
				financeReport.setPdtnum(rs.getString("pdtnum"));
				financeReport.setFinprc(rs.getString("finprc"));
				financeReport.setPdtprc(rs.getString("pdtprc"));
				financeReport.setFinrate(rs.getString("finrate"));
				
				//financeReport.setTmksid(rs.getString("tmksid"));
				//financeReport.setSellprc(rs.getString("sellprc"));
				
				String sql2="select tmksid from tblfinance where bindno='"+rs.getString("bindno")+"' and typeno='"+rs.getString("typeno")+"'";
				System.out.println(sql2);
				ResultSet rs2=pstmt2.executeQuery(sql2);
				String tmksid="";
				int flag=1;
				while (rs2.next()) {
					if(flag==1){//第一个输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=rs2.getString("tmksid");
						}
					}else{//后边的输出
						if(rs2.getString("tmksid")==null){
							tmksid+="";
						}else{
							tmksid+=","+rs2.getString("tmksid");
						}
					}
					flag=0;
				}
				rs2.close();
				
				financeReport.setTmksid(tmksid);
				
				data.add(financeReport);
			}
		   rs.close();
		   
		   pstmt.close();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
		}
		  
	      this.data = data;
	      return data;
	}
	
	
	private int index = -1;   
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		
		Object value = null;   
        
        String fieldName = field.getName();   
           
        if ("bindno".equals(fieldName))   
        {   
            value = data.get(index).getBindno();   
        }   
        else if ("gctsname".equals(fieldName))   
        {   
            value = data.get(index).getGctsname();   
        }   
        else if ("taxno".equals(fieldName))   
        {   
            value = data.get(index).getTaxno();   
        }   
        else if ("fpdtnm".equals(fieldName))   
        {   
            value = data.get(index).getFpdtnm();   
        }   
        else if ("taxrate".equals(fieldName))   
        {   
            value = data.get(index).getTaxrate(); 
        }   
        else if ("fpdtmodel".equals(fieldName))   
        {   
            value = data.get(index).getFpdtmodel();   
        }
        else if ("pdtut".equals(fieldName))   
        {   
            value = data.get(index).getPdtut();   
        }
        else if ("pdtnum".equals(fieldName))   
        {   
            value = data.get(index).getPdtnum();   
        }
        else if ("finprc".equals(fieldName))   
        {   
            value = data.get(index).getFinprc();   
        }
        else if ("tmksid".equals(fieldName))   
        {   
            value = data.get(index).getTmksid();   
        }
        else if ("sellprc".equals(fieldName))   
        {   
            value = data.get(index).getSellprc();   
        }
        else if ("gctemail".equals(fieldName))   
        {   
            value = data.get(index).getGctemail();   
        }
        else if ("finnt".equals(fieldName))   
        {   
            value = data.get(index).getFinnt();   
        }
        else if ("gctnm".equals(fieldName))   
        {   
            value = data.get(index).getGctnm();   
        }
        else if ("retailname".equals(fieldName))   
        {   
            value = data.get(index).getRetailname();   
        }
        else if ("retailtaxno".equals(fieldName))   
        {   
            value = data.get(index).getRetailtaxno();   
        }
        return value;   

	}

	@Override
    public boolean next() throws JRException   
    {   
        index++;   
        return (index < data.size());   
    }


}
