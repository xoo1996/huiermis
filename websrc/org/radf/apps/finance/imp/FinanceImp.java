package org.radf.apps.finance.imp;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.radf.apps.commons.entity.Finance;
import org.radf.apps.finance.facade.FinanceFacade;
import org.radf.manage.util.ManageErrorCode;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.exception.NoConnectionException;
import org.radf.plat.util.imp.IMPSupport;

public class FinanceImp extends IMPSupport implements FinanceFacade{
	
	private String className = this.getClass().getName();

	@Override
	public ResponseEnvelop save(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Finance dto = (Finance) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);

			List result = (Vector) DBUtil.querySQL(con,
					"select SEQ_FINNO.NEXTVAL from dual");
			BigDecimal id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			String finno = id.toString();

			dto.setFinno(finno);
			dto.setFileKey("fin_insert");

			store(con, dto, null, 0);
			
			
			DBUtil.commit(con);

			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("finno", dto.getFinno());
			retmap.put("beo", dto);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "savePerson",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response; 
	}

	@Override
	public List<Finance> createfin(String fintype, String notype,
			String[] listTypeno, String[] listGctnm, String[] listGctid,
			String[] listIctnm, String[] listIctid, String[] listPdtid,
			String[] listPdtnm, String[] listBand, String[] listTmksid,
			String[] listChgdt, String[] listRedt, String[] listPdtprc,
			String[] listPdtnum, String[] listFinrate,
			String[] listFinprccount, String[] listFinprc,
			String[] listInvoicecode, String[] listInvoiceno,
			String[] listSellprc, String[] listRetailname,
			String[] listRetailtaxno, String[] listComaddr,
			String[] listDepositbank, String[] listDepositid,
			String[] listSpecialtel) {
		
		List<Finance> financeList= new ArrayList<Finance>();
		for(int i =0;i<listTypeno.length;i++){
			Finance fin = new Finance();
			fin.setFintype(fintype);
			fin.setNotype(notype);
			fin.setTypeno(listTypeno[i]);
			fin.setFinsubdt(DateUtil.getSystemCurrentTime());
			
			if(listGctnm[i]!=null &&!listGctnm[i].equals("")){
				fin.setGctnm(listGctnm[i]);
			}
			if(listGctid[i]!=null &&!listGctid[i].equals("")){
				fin.setGctid(listGctid[i]);
			}
			if(listIctnm[i]!=null &&!listIctnm[i].equals("")){
				fin.setIctnm(listIctnm[i]);
			}
			if(listIctid[i]!=null &&!listIctid[i].equals("")){
				fin.setIctid(listIctid[i]);
			}
			if(listPdtid[i]!=null &&!listPdtid[i].equals("")){
				fin.setPdtid(listPdtid[i]);
			}
			if(listPdtnm[i]!=null &&!listPdtnm[i].equals("")){
				fin.setPdtnm(listPdtnm[i]);
			}
			if(listBand[i]!=null &&!listBand[i].equals("")){
				fin.setBand(listBand[i]);
			}
			if(listTmksid[i]!=null &&!listTmksid[i].equals("")){
				fin.setTmksid(listTmksid[i]);
			}
			if(listChgdt[i]!=null &&!listChgdt[i].equals("")){
				
				Date date = DateUtil.converToDate(listChgdt[i].trim());
				fin.setChgdt(date);
			}
			if (listRedt[i] != null && !listRedt[i].equals("")) {

				Date date = DateUtil.converToDate(listRedt[i].trim());
				fin.setRedt(date);
			}
			if(listPdtprc[i]!=null &&!listPdtprc[i].equals("")){
				fin.setPdtprc(listPdtprc[i]);
			}
			if(listPdtnum[i]!=null &&!listPdtnum[i].equals("")){
				fin.setPdtnum(listPdtnum[i]);
			}
			if(listFinrate[i]!=null &&!listFinrate[i].equals("")){
				fin.setFinrate(listFinrate[i]);
			}
			if(listFinprccount[i]!=null &&!listFinprccount[i].equals("")){
				fin.setFinprccount(listFinprccount[i]);
			}
			if(listFinprc[i]!=null &&!listFinprc[i].equals("")){
				fin.setFinprc(listFinprc[i]);
			}
			
			if(listInvoicecode[i]!=null &&!listInvoicecode[i].equals("")){
				fin.setInvoicecode(listInvoicecode[i]);
			}
			if(listInvoiceno[i]!=null &&!listInvoiceno[i].equals("")){
				fin.setInvoiceno(listInvoiceno[i]);
			}
			if(listSellprc[i]!=null &&!listSellprc[i].equals("")){
				fin.setSellprc(listSellprc[i]);
			}
			if(listRetailname[i]!=null &&!listRetailname[i].equals("")){
				fin.setRetailname(listRetailname[i]);
			}
			if(listRetailtaxno[i]!=null &&!listRetailtaxno[i].equals("")){
				fin.setRetailtaxno(listRetailtaxno[i]);
			}
			if(listComaddr[i]!=null &&!listComaddr[i].equals("")){
				fin.setComaddr(listComaddr[i]);
			}
			if(listDepositbank[i]!=null &&!listDepositbank[i].equals("")){
				fin.setDepositbank(listDepositbank[i]);
			}
			if(listDepositid[i]!=null &&!listDepositid[i].equals("")){
				fin.setDepositid(listDepositid[i]);
			}
			if(listSpecialtel[i]!=null &&!listSpecialtel[i].equals("")){
				fin.setSpecialtel(listSpecialtel[i]);
			}
			//fin.setFintel(fintel);
			financeList.add(fin);
		}
		
		return financeList;
	}

	@Override
	public boolean isTimeOK() {
		
		String s = DateUtil.getSystemCurrentTime("yyyy-mm-dd");
		String day = s.substring(8, 10);
		if (Integer.parseInt(day) < Integer.parseInt("26"))
			return false;
		return true;
	}

	@Override
	public List<Finance> createfin(String fintype, String notype,
			String typeno, String gctnm, String gctid, String ictnm,
			String pdtid, String pdtnm, String band, String tmksid,
			String chgdt, String redt, String pdtprc, String pdtnum,
			String finrate, String finprccount, String finprc,
			String invoicecode, String invoiceno, String sellprc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Finance> createNotBillFin() throws SQLException {
		
		String sql1="select f.folno as typeno,g.gctnm,g.gctid,i.ictnm as ictnm,i.ictid as ictid,p.pdtid as pdtid,p.pdtnm as pdtnm,nvl(r.series,'\u914d\u4ef6') as band,m.tmksid as tmksid,p.pdtprc as pdtprc,(d.fdtdprc*d.fdtdisc) as sellprc,d.fdtqnt as pdtnum,nvl(r.rate,'0.4') as finrate,f.folchgdt as chgdt,to_number((pdtprc*nvl(r.rate,'0.4'))) as finprc,(pdtprc*fdtqnt*nvl(r.rate,'0.4')) as finprccount from tblfolio f left join tblfoldetail d on f.folno = d.fdtfno left join tblgrpclient g on g.gctid = f.folctid left join tblproduct p on p.pdtid = d.fdtpid left join tblindclient i on i.ictid = d.fdtcltid left join tblmaking m on d.fdtfno = m.tmkfno left join tblfinrate r on p.pdtnm like '%'||r.band||'%'  where r.FPDTNM ='助听器' and f.folischg='1' and (f.isfinbill is null or f.isfinbill = '0') and (f.foltype='make' or f.foltype='makeEar' or f.foltype='normal') and (f.folsta <> 'recoiled' and f.folsta <> 'recoiledhead' and f.folsta <> 'recpass') and f.folno not in (select distinct typeno from tblfinance where fintype='normal' and notype='order') and f.folchgdt > last_day(add_months(sysdate, -3)) and f.folchgdt < last_day(add_months(sysdate, -2)) ";
		String sql2="select n.chgid as typeno,g.gctnm,g.gctid,i.ictnm as ictnm,i.ictid as ictid,p.pdtid as pdtid,p.pdtnm as pdtnm,nvl(r.series,'\u914d\u4ef6') as band,p.pdtprc as pdtprc,(p.pdtprc*h.ncddis) as sellprc,h.ncdqnt as pdtnum,nvl(r.rate,'0.4') as finrate,n.chgdt as chgdt,to_number((pdtprc*nvl(r.rate,'0.4'))) as finprc,(pdtprc*ncdqnt*nvl(r.rate,'0.4')) as finprccount from tblnorchg n left join tblnorchgdetail h on n.chgid = h.ncdid left join tblgrpclient g on g.gctid = n.chggcltid left join tblproduct p on p.pdtid = h.ncdpid left join tblindclient i on i.ictid = n.chgcltid left join tblfinrate r on p.pdtnm like '%'||r.band||'%'  where r.FPDTNM ='助听器' and (n.isfinbill is null or n.isfinbill = '0') and (h.ncdsta <> 'nomrecoiled' and h.ncdsta <> 'commited' and h.ncdsta <> 'pass') and n.chgid not in (select distinct typeno from tblfinance where fintype='normal' and notype='common') and n.chgdt > last_day(add_months(SYSDATE,-3)) and n.chgdt < last_day(add_months(sysdate, -2)) ";
		Connection con = null;
		List<Finance> financeList= new ArrayList<Finance>();
		
		 try {
			con = DBUtil.getConnection();
			Statement pstmt1;
			pstmt1 = con.createStatement();
			ResultSet rs1 = pstmt1.executeQuery(sql1);
			
			while (rs1.next()) {
				Finance fin = new Finance();
				
				fin.setFintype("not");
				fin.setNotype("order");
				fin.setTypeno(rs1.getString("typeno"));
				fin.setFinsubdt(DateUtil.getSystemCurrentTime());
				
				if(rs1.getString("gctnm")!=null &&!rs1.getString("gctnm").equals("")){
					fin.setGctnm(rs1.getString("gctnm"));
				}
				if(rs1.getString("gctid")!=null &&!rs1.getString("gctid").equals("")){
					fin.setGctid(rs1.getString("gctid"));
				}
				if(rs1.getString("ictnm")!=null &&!rs1.getString("ictnm").equals("")){
					fin.setIctnm(rs1.getString("ictnm"));
				}
				if(rs1.getString("ictid")!=null &&!rs1.getString("ictid").equals("")){
					fin.setIctid(rs1.getString("ictid"));
				}
				if(rs1.getString("pdtid")!=null &&!rs1.getString("pdtid").equals("")){
					fin.setPdtid(rs1.getString("pdtid"));
				}
				if(rs1.getString("pdtnm")!=null &&!rs1.getString("pdtnm").equals("")){
					fin.setPdtnm(rs1.getString("pdtnm"));
				}
				if(rs1.getString("band")!=null &&!rs1.getString("band").equals("")){
					fin.setBand(rs1.getString("band"));
				}
				if(rs1.getString("tmksid")!=null &&!rs1.getString("tmksid").equals("")){
					fin.setTmksid(rs1.getString("tmksid"));
				}
				if(rs1.getString("chgdt")!=null &&!rs1.getString("chgdt").equals("")){
					
					Date date = DateUtil.converToDate(rs1.getString("chgdt").trim());
					fin.setChgdt(date);
				}
				if(rs1.getString("pdtprc")!=null &&!rs1.getString("pdtprc").equals("")){
					fin.setPdtprc(rs1.getString("pdtprc"));
				}
				if(rs1.getString("pdtnum")!=null &&!rs1.getString("pdtnum").equals("")){
					fin.setPdtnum(rs1.getString("pdtnum"));
				}
				if(rs1.getString("finrate")!=null &&!rs1.getString("finrate").equals("")){
					fin.setFinrate(rs1.getString("finrate"));
				}
				if(rs1.getString("finprccount")!=null &&!rs1.getString("finprccount").equals("")){
					fin.setFinprccount(String.valueOf(rs1.getDouble("finprccount")));
				}
				if(rs1.getString("finprc")!=null &&!rs1.getString("finprc").equals("")){
					fin.setFinprc(String.valueOf(rs1.getDouble("finprc")));
				}
				
				if(rs1.getString("sellprc")!=null &&!rs1.getString("sellprc").equals("")){
					fin.setSellprc(rs1.getString("sellprc"));
				}

				financeList.add(fin);
				
			}
			
			rs1.close();
			pstmt1.close();
			
			Statement pstmt2;
			pstmt2 = con.createStatement();
			ResultSet rs2 = pstmt2.executeQuery(sql2);
			
			while (rs2.next()) {
				Finance fin = new Finance();
				
				fin.setFintype("not");
				fin.setNotype("common");
				fin.setTypeno(rs2.getString("typeno"));
				fin.setFinsubdt(DateUtil.getSystemCurrentTime());
				
				if(rs2.getString("gctnm")!=null &&!rs2.getString("gctnm").equals("")){
					fin.setGctnm(rs2.getString("gctnm"));
				}
				if(rs2.getString("gctid")!=null &&!rs2.getString("gctid").equals("")){
					fin.setGctid(rs2.getString("gctid"));
				}
				if(rs2.getString("ictnm")!=null &&!rs2.getString("ictnm").equals("")){
					fin.setIctnm(rs2.getString("ictnm"));
				}
				if(rs2.getString("pdtid")!=null &&!rs2.getString("pdtid").equals("")){
					fin.setPdtid(rs2.getString("pdtid"));
				}
				if(rs2.getString("pdtnm")!=null &&!rs2.getString("pdtnm").equals("")){
					fin.setPdtnm(rs2.getString("pdtnm"));
				}
				if(rs2.getString("band")!=null &&!rs2.getString("band").equals("")){
					fin.setBand(rs2.getString("band"));
				}
				if(rs2.getString("chgdt")!=null &&!rs2.getString("chgdt").equals("")){
					
					Date date = DateUtil.converToDate(rs2.getString("chgdt").trim());
					fin.setChgdt(date);
				}
				if(rs2.getString("pdtprc")!=null &&!rs2.getString("pdtprc").equals("")){
					fin.setPdtprc(rs2.getString("pdtprc"));
				}
				if(rs2.getString("pdtnum")!=null &&!rs2.getString("pdtnum").equals("")){
					fin.setPdtnum(rs2.getString("pdtnum"));
				}
				if(rs2.getString("finrate")!=null &&!rs2.getString("finrate").equals("")){
					fin.setFinrate(rs2.getString("finrate"));
				}
				if(rs2.getString("finprccount")!=null &&!rs2.getString("finprccount").equals("")){
					fin.setFinprccount(String.valueOf(rs2.getDouble("finprccount")));
				}
				if(rs2.getString("finprc")!=null &&!rs2.getString("finprc").equals("")){
					fin.setFinprc(String.valueOf(rs2.getDouble("finprc")));
				}
				
				//System.out.println(String.valueOf(rs2.getDouble("finprc")));
				
				if(rs2.getString("sellprc")!=null &&!rs2.getString("sellprc").equals("")){
					fin.setSellprc(rs2.getString("sellprc"));
				}

				financeList.add(fin);
				
			}
			
			rs2.close();
			pstmt2.close();
			} catch (NoConnectionException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closeConnection(con);
			}
		
		 return financeList;
	}

	@Override
	public boolean save(List<Finance> financeList) throws Exception {
	//public boolean savetest() throws Exception {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			int index=0;
			for (Finance finance : financeList) {
				//存储finance
				List result = (Vector) DBUtil.querySQL(con,
						"select SEQ_FINNO.NEXTVAL from dual");
				BigDecimal id = (BigDecimal) ((HashMap) result.get(0)).get("1");
				String finno = id.toString();
	
				finance.setFinno(finno);
				finance.setFileKey("fin_insert");
	
				store(con, finance, null, 0);
				
				//更新订单开票标记
				String sql1 = "update tblfolio set isfinbill = '1' where folno='"
						+ finance.getTypeno() + "'";
				String sql2 = "update tblnorchg set isfinbill = '1' where chgid='"
						+ finance.getTypeno() + "'";

				if (finance.getNotype().equals("order")) {
					DBUtil.execSQL(con, sql1);
				} else if (finance.getNotype().equals("common")) {
					DBUtil.execSQL(con, sql2);

				}
				System.out.println(++index+"====");
			}
			DBUtil.commit(con);
			DBUtil.closeConnection(con);
			
			//生成绑定编号
			con = DBUtil.getConnection();
			java.sql.Statement pstmt = con.createStatement();
			CallableStatement proc = null;
			
			String sql_bindno1 = "select distinct gctnm from tblfinance where bindno is null and fintype = 'not' ";
			
			ResultSet rst = pstmt.executeQuery(sql_bindno1);
			
			String fintype =null;
			String notype =null;
			String gctnm =null;
			String pdtnm =null;
			String bindno=null;
			
			while (rst.next()){
				gctnm = rst.getString("gctnm");
				
				proc = con.prepareCall("{ call PRC_CRE_FIN(?) }");
				proc.registerOutParameter(1, Types.VARCHAR);//获得传出参数
				proc.execute();
				bindno = proc.getString(1);
				proc.close();
				
			String sql_bindno2 = "update tblfinance set bindno ='" + bindno + "' ,finpassdt=sysdate "
					+ "where bindno is null and fintype = 'not'"
					+ " and gctnm='"+gctnm+"'";
			
				DBUtil.execSQL(con, sql_bindno2);
			} 
			
			DBUtil.commit(con);
			rst.close();
			pstmt.close();
			return true; 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return false; 
	}

	@Override
	public boolean ifHaveFin(String fintype, String listTypeno[],String listPdtid[]) throws Exception {
		
		Connection con = null;
		try{
			// 检测是否重复提交
			con = DBUtil.getConnection();
			String sql = null;
		
			for (int i = 0; i < listTypeno.length; i++) {
				sql = "select * from tblfinance where fintype='"+fintype+"' and typeno = '"
						+ listTypeno[i]
						+ "' and pdtid = '"
						+ listPdtid[i]
						+ "' ";
				List result = (Vector) DBUtil.querySQL(con, sql);
				if (result.size() > 0) 
					return false;
			}
			
			DBUtil.closeConnection(con);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return false;
	}

	@Override
	public void revokeFin(String[] listFinno, String[] listFintype,
			String[] listNotype, String[] listGctid, String[] listPdtid,
			String[] listPdtnum, String[] listFinnt) throws Exception {
		
		Connection con =null;		
		String typeno =null;
		String fintype =null;
		String notype =null;
		
		try{
			//零售开票的撤销
			if(listFintype[0].equals("retail")){
				//建立连接
				con = DBUtil.getConnection();
				java.sql.Statement pstmt = con.createStatement();
				//根据开票编号查找到相关记录
				String sql_select="select finno,notype,typeno from tblfinance where ";
				sql_select += "finno='"+listFinno[0]+"' ";
				for(int i =1;i<listFinno.length;i++){
					sql_select+="or finno='"+listFinno[i]+"' ";
				}
				
				ResultSet rst = pstmt.executeQuery(sql_select);
				//相关记录更新并删除
				while (rst.next()) {			
						String sql_clear1="update tblfolio set isfinbill ='' where folno='"+rst.getString("typeno")+"'";
						String sql_clear2="update tblnorchg set isfinbill ='' where chgid='"+rst.getString("typeno")+"'";	
						
						if (rst.getString("notype").equals("order")) {
							DBUtil.execSQL(con, sql_clear1);
						} else if (rst.getString("notype").equals("common")) {
							DBUtil.execSQL(con, sql_clear2);
						}
						
					String sql2 = "delete from tblfinance where finno= '" + rst.getString("finno") +"' ";			
					DBUtil.execSQL(con, sql2);
				}
				
				DBUtil.commit(con);
				rst.close();
				pstmt.close();
			}else{
			//其他开票的撤销
				for(int i=0;i<listFintype.length;i++){
					String sql1 = "select typeno,fintype,notype from tblfinance "
							+ "where bindno is null and fintype='" + listFintype[i]
							+ "' and notype='"+listNotype[i]
							+"' and gctid='"+listGctid[i]
							+"' and pdtid='"+listPdtid[i]
							+"'";
					
					con = DBUtil.getConnection();
					java.sql.Statement pstmt = con.createStatement();
					ResultSet rst = pstmt.executeQuery(sql1);
					
					while (rst.next()) {
						
						typeno = rst.getString("typeno");
						fintype = rst.getString("fintype");
						notype = rst.getString("notype");
						
						String sql_clear1="update tblfolio set isfinbill ='' where folno='"+typeno+"'";
						String sql_clear2="update tblnorchg set isfinbill ='' where chgid='"+typeno+"'";
						String sql_clear3="update tblfolio set isfinredbill ='' where folno='"+typeno+"'";
						String sql_clear4="update tblnorchg set isfinredbill ='' where chgid='"+typeno+"'";
									
						if (fintype.equals("normal") && notype.equals("order")) {
							DBUtil.execSQL(con, sql_clear1);
						} else if (fintype.equals("normal")
								&& notype.equals("common")) {
							DBUtil.execSQL(con, sql_clear2);
						}else if (fintype.equals("red")
								&& notype.equals("order")) {
							DBUtil.execSQL(con, sql_clear3);
						}else if (fintype.equals("red")
								&& notype.equals("common")) {
							DBUtil.execSQL(con, sql_clear4);

						}	
						
						//DBUtil.commit(con);
					}
					
					String sql2 = "delete from tblfinance "
							+ "where bindno is null and fintype='" + listFintype[i]
							+ "' and notype='"+listNotype[i]
							+"' and gctid='"+listGctid[i]
							+"' and pdtid='"+listPdtid[i]
							+"'";			
					DBUtil.execSQL(con, sql2);
					
					DBUtil.commit(con);
					rst.close();
					pstmt.close();
					DBUtil.closeConnection(con);
				}//for		
				//DBUtil.commit(con);		
			}//else
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		
	}

}
