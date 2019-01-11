package org.radf.apps.business.imp;

import it.businesslogic.ireport.Report;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.radf.manage.util.ManageErrorCode;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.imp.BatchProcessing;
import org.radf.plat.util.imp.IMPSupport;
import org.radf.apps.business.facade.BusinessFacade;
import org.radf.apps.commons.entity.BranchFinancialReport;
import org.radf.apps.commons.entity.Business;
import org.radf.apps.commons.entity.Fee;
import org.radf.apps.commons.entity.Order;
import org.radf.apps.commons.entity.OrderDetail;
import org.radf.apps.commons.entity.Product;
import org.radf.apps.commons.entity.Repair;
import org.radf.apps.commons.entity.Sale;

import com.sun.org.apache.xpath.internal.operations.Gt;

public class BusinessImp extends IMPSupport implements BusinessFacade {
	private String className = this.getClass().getName();

	/**
	 * 月结信息修改
	 */
	public ResponseEnvelop modify(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Collection<Business> collection = (Collection<Business>) map
					.get("collection");
			String userId = (String)map.get("userId");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			BatchProcessing.batchProcessing(collection, userId);
			
			
			/*for (Business dto : collection) {
				dto.setIvtoprcd(userId);
				dto.setFileKey("business_update");
				modify(con, dto, null, 0);
				dto.setFileKey("bus02_000");
				modify(con, dto, null, 0);
				dto.setActsta("1");
				dto.setFileKey("bus01_007");
				modify(con, dto, null, 0);
			}
			DBUtil.commit(con);
*/
		/*} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		*/} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "modify",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * only月结表信息修改
	 */
	public ResponseEnvelop modifyOnly(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Collection<Business> collection = (Collection<Business>) map
					.get("collection");
			String userId = (String)map.get("userId");
			String mark = (String) map.get("mark");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			BatchProcessing.batchProcessingOnly(collection, userId, mark);
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "modify",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
/**
 * 插入客户均摊费用
 */
	public ResponseEnvelop insertAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			List<Fee> feeList = (List<Fee>) map.get("feeList");
			String fagctid =(String)map.get("feegctid");
			String feegctname = (String)map.get("feegctname");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			for (Fee fee : feeList) {
				String str = DBUtil.getSequence(con, "s_tblfeeamortize");
				int seq = Integer.parseInt(str)+1;
				String sql1 = "insert into tblfeeamortize (feegctid,feestart,feeend,amotype,money,note,operdate,feegctname,feeamoid,feelong,amomoney) values (?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql1);
				pstmt.setString(1,fagctid);
				pstmt.setDate(2, (Date) fee.getFeestart());
				pstmt.setDate(3, (Date) fee.getFeeend());
				pstmt.setString(4, fee.getAmotype());
				pstmt.setDouble(5, fee.getMoney());
				pstmt.setString(6, fee.getNote());
				pstmt.setDate(7,(Date) fee.getOperDate());
				pstmt.setString(8,feegctname);
				pstmt.setInt(9, seq);
				pstmt.setInt(10, fee.getFeelong());
				pstmt.setDouble(11, fee.getAmomoney());
				pstmt.execute();
			}
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			response.setBody(retmap);
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "insert",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * 客户费用信息
	 */
	public ResponseEnvelop insert(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee fe = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			// 判断ID是否存在和重复
			if (null == fe.getFeegctid() || "".equals(fe.getFeegctid())) {
				throw new AppException("录入费用的团体客户号不能为空!");
			}
			// 判断费用是否已经录入
			fe.setFileKey("fee_select");
			if (getCount(con, fe, 0) > 0) {
				//super.saveSuccessfulMsg(req, "该团体客户费用已录入!");
				throw new AppException("该团体客户费用已录入!");
	
			}else{
/*			fe.setFeetype("0");
			fe.setFileKey("fee_insert1");
			// 插入团体客户费用
			store(con, fe, null, 0);
			
			fe.setFeetype("1");
			fe.setFileKey("fee_insert2");
			// 插入团体客户费用
			store(con, fe, null, 0);
			
			
			fe.setFeetype("2");
			fe.setFileKey("fee_insert3");
			// 插入团体客户费用
			store(con, fe, null, 0);*/
			
			fe.setFileKey("fee_insert");
			store(con,fe,null,0);
			}
			
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("feegctid", fe.getFeegctid());
			retmap.put("workString", "录入团体客户费用信息");
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "insert",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 保存分店月度汇总信息
	 */
	public ResponseEnvelop save1(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee fe = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			// 判断ID是否存在和重复
			if (null == fe.getFeegctid() || "".equals(fe.getFeegctid())) {
				throw new AppException("录入费用的团体客户号不能为空!");
			}
			// 判断子费用tblfee1是否已经保存
			fe.setFileKey("fee1_select");
			if (getCount(con, fe, 0) > 0) {
				//是否授权修改
				String s = "select ISGRANT from tblfee where FEEYEAR='"+fe.getFeeyear()+"' and FEEMONTH='"+fe.getFeemonth()+"' and FEEGCTID='"+fe.getFeegctid()+"'";
				List res = (Vector) DBUtil.querySQL(con,s);
				int grant=3;//代表还未第一次提交
				if(res.size()>0){
					grant = Integer.parseInt(((HashMap) res.get(0)).get("1").toString()); 
					if(grant!=1)
						throw new AppException("您已提交,如需修改请联系总部授权!");
				}
				if(grant!=0){//等于1或3
					fe.setFeetype("0");
					fe.setFileKey("fee_update1");
					modify(con, fe, null, 0);
					fe.setFeetype("1");
					fe.setFileKey("fee_update2");
					modify(con, fe, null, 0);
					fe.setFeetype("2");
					fe.setFileKey("fee_update3");
					modify(con, fe, null, 0);
					fe.setFileKey("fee_update4");
					modify(con, fe, null, 0);
				}
			}else{
				fe.setFeetype("0");
				fe.setFileKey("fee_insert1");
				// 插入团体客户费用
				store(con, fe, null, 0);
				
				fe.setFeetype("1");
				fe.setFileKey("fee_insert2");
				// 插入团体客户费用
				store(con, fe, null, 0);
				
				fe.setFeetype("2");
				fe.setFileKey("fee_insert3");
				// 插入团体客户费用
				store(con, fe, null, 0);
				
				fe.setFileKey("fee_insert4");
				// 插入团体客户费用
				store(con, fe, null, 0);
			}
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("feegctid", fe.getFeegctid());
			retmap.put("workString", "保存月度汇总表信息!");
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "insert",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * 提交分店月度汇总信息
	 */
	public ResponseEnvelop submit(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee fe = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			// 判断ID是否存在和重复
			if (null == fe.getFeegctid() || "".equals(fe.getFeegctid())) {
				throw new AppException("录入费用的团体客户号不能为空!");
			}
			// 判断费用是否已经录入或授权
			String s = "select ISGRANT from tblfee where FEEYEAR='"+fe.getFeeyear()+"' and FEEMONTH='"+fe.getFeemonth()+"' and FEEGCTID='"+fe.getFeegctid()+"'";
			List res = (Vector) DBUtil.querySQL(con,s);
			int grant,flag=0;
			if(res.size()>0){
				grant = Integer.parseInt(((HashMap) res.get(0)).get("1").toString()); 
				if(grant==1){
					flag=1;
				}else{
					throw new AppException("您已提交,如需修改请联系总部授权!");
				}
			}
			if(flag==1||res.size()==0){
				fe.setFileKey("fee1_select");
				if (getCount(con, fe, 0) > 0) {
					fe.setFeetype("0");
					fe.setFileKey("fee_update1");
					modify(con, fe, null, 0);
					fe.setFeetype("1");
					fe.setFileKey("fee_update2");
					modify(con, fe, null, 0);
					fe.setFeetype("2");
					fe.setFileKey("fee_update3");
					modify(con, fe, null, 0);
					fe.setFileKey("fee_update4");
					modify(con, fe, null, 0);
				}else{
					fe.setFeetype("0");
					fe.setFileKey("fee_insert1");
					// 插入团体客户费用
					store(con, fe, null, 0);
					
					fe.setFeetype("1");
					fe.setFileKey("fee_insert2");
					// 插入团体客户费用
					store(con, fe, null, 0);
					
					fe.setFeetype("2");
					fe.setFileKey("fee_insert3");
					// 插入团体客户费用
					store(con, fe, null, 0);
					fe.setFileKey("fee_insert4");
					// 插入团体客户费用
					store(con, fe, null, 0);
				}
				DBUtil.commit(con);				
				//插入tblfee表,总费用=财税+商务				
				fe.setFeerent(fe.getFfeerent()+fe.getBfeerent());
				fe.setFeeassets(fe.getBfeeassets()+fe.getFfeeassets());
				fe.setFeebuilt(fe.getBfeebuilt()+fe.getFfeebuilt());
				fe.setFeetax(fe.getBfeetax()+fe.getFfeetax());
				fe.setZbfee(fe.getBzbfee()+fe.getFzbfee());
				//调用上个月社保
				int feeyear; int feemonth;
				if(fe.getFeemonth()==1){
					 feeyear=fe.getFeeyear()-1;
					 feemonth=12;
				}else{
					feeyear=fe.getFeeyear();
					feemonth= fe.getFeemonth()-1;
					}
				
				String sql = "select FEEPENSION from tblfee where FEEYEAR='"+feeyear+"' and FEEMONTH='"+feemonth+"' and FEEGCTID='"+fe.getFeegctid()+"'";
				List result = (Vector) DBUtil.querySQL(con,sql);
				Double feepension = null;
				if(result.size()>0){
					feepension = Double.parseDouble(((HashMap) result.get(0)).get("1").toString()); 
					fe.setFeepension(feepension);
				}else{
					fe.setFeepension(fe.getFfeepension()+fe.getFfeehousingfund());
				}
				fe.setFeehousingfund(fe.getFfeehousingfund());
				fe.setFeeaccount(fe.getFfeeaccount()+fe.getBfeeaccount());
				fe.setFeeprocess(fe.getFfeeprocess()+fe.getBfeeprocess());
				fe.setFeewage(fe.getFfeewage());
				fe.setFeead(fe.getFfeead()+fe.getBfeead());
				fe.setFeephone(fe.getFfeephone()+fe.getBfeephone());
				fe.setFeewater(fe.getFfeewater()+fe.getBfeewater());
				fe.setFeetrip(fe.getFfeetrip()+fe.getBfeetrip());
				fe.setFeepostage(fe.getFfeepostage()+fe.getBfeepostage());
				fe.setFeeoffice(fe.getFfeeoffice()+fe.getBfeeoffice());
				fe.setFeesocial(fe.getFfeesocial()+fe.getBfeesocial());
				fe.setFeebenefit(fe.getBfeebenefit());
				fe.setFeothersa(fe.getFfeeothercharges()+fe.getBfeeothercharges());
				//fe.setFee1othercharges(fe.getFfee1othercharges()+fe.getBfee1othercharges());
				fe.setFeecontrol(fe.getFeead()+fe.getFeephone()+fe.getFeewater()+fe.getFeetrip()+fe.getFeepostage()+fe.getFeeoffice()+fe.getFeesocial()+fe.getFeothersa());
				fe.setFeesales(fe.getRfeesales());
				if(flag==1){
					fe.setIsgrant(0);
					fe.setFileKey("fee_update");
					modify(con, fe, null, 0);
				}else{
					fe.setFileKey("fee_insert");
					store(con, fe, null, 0);
				}
			}
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("feegctid", fe.getFeegctid());
			retmap.put("workString", "提交月度汇总表信息!");
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "insert",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}

	public ResponseEnvelop query(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Fee dto = (Fee) map.get("beo");
			dto.setFileKey("fe2_select");// 客户费用信息
			Object ci = find(con, dto, null, 0);
			if ("".equals(ci) || ci == null)
				throw new AppException("没有查询到符合条件的信息!");
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			// response.setHead(ExceptionSupport(className, "print",
			// ManageErrorCode.SQLERROR, ex.getMessage(), request
			// .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * 查询客户费用
	 */
	public ResponseEnvelop query1(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Fee dto = (Fee) map.get("beo");
			dto.setFileKey("fee_select1");// 客户费用信息
			Object ci = find(con, dto, null, 0);
			if ("".equals(ci) || ci == null)
				throw new AppException("没有查询到符合条件的信息!");
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			// response.setHead(ExceptionSupport(className, "print",
			// ManageErrorCode.SQLERROR, ex.getMessage(), request
			// .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 库存超期查询
	 */
	public ResponseEnvelop stoexpquery(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		//CallableStatement proc = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			
			
			/*proc = con.prepareCall("{call PRC_STOEXPCHECK}");
			proc.execute();
			
			business.setFileKey("bus01_002");
			Object ci = find(con, business, null, 0);*/
			
			
			business.setFileKey("bus02_001");
			remove(con,business,null,0);
			
			/*if ("".equals(ci) || ci == null)
				throw new AppException("没有查询到符合条件的信息!");*/
		
			DBUtil.commit(con);
			/*HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);*/
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			// response.setHead(ExceptionSupport(className, "print",
			// ManageErrorCode.SQLERROR, ex.getMessage(), request
			// .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	

	public ResponseEnvelop update(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee dtoList = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);

			/*dtoList.setFeetype("0");
			dtoList.setFileKey("fee_update1");
			modify(con, dtoList, null, 0);
			
			dtoList.setFeetype("1");
			dtoList.setFileKey("fee_update2");
			modify(con, dtoList, null, 0);
			
			dtoList.setFeetype("2");
			dtoList.setFileKey("fee_update3");
			modify(con, dtoList, null, 0);*/
			
			dtoList.setFileKey("fee_update");
			modify(con,dtoList,null,0);

			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "update",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 授权门店修改
	 * @param request
	 * @return
	 */
	public ResponseEnvelop grant(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee dtoList = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			//dtoList.setFileKey("fee_grant");
			//modify(con,dtoList,null,0);
			//实质上就是删除
			dtoList.setFileKey("fee_delete");
			remove(con, dtoList, null, 0);
			DBUtil.commit(con);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "update",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 补充费用信息
	 */
	public ResponseEnvelop save(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Sale sa = (Sale) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			//获取
			String sql = "select nvl(sum(ivtpamnt),0) as temp from tblinventory where ivtgcltid='"+sa.getMgctid()+"' and ivtyear='"+sa.getMyear()+"' and ivtmonth='"+sa.getMmonth()+"'";
			List result = (Vector) DBUtil.querySQL(con,sql);
			Double pamnt = null;
			if(result.size()>0)
				pamnt = Double.parseDouble(((HashMap) result.get(0)).get("1").toString()); 
	        Integer lyear = null;
	        Integer lmonth = null;
	        Integer year = sa.getMyear();
	        Integer month = sa.getMmonth();
	        if(month == 12){
	        	lyear = year - 1;
	        	lmonth = 1;
	        }else{
	        	lmonth = month - 1;
	        	lyear = year;
	        }
	        sql = "select nvl(m.marrears,0) as temp from tblmonth m where m.mgctid='"+sa.getMgctid()+"' and m.myear='"+lyear+"' and m.mmonth='"+lmonth+"'";
	        result = (Vector) DBUtil.querySQL(con,sql);
	        Double arrears = null;
	        if(result.size()>0){
		       arrears = Double.parseDouble(((HashMap) result.get(0)).get("1").toString()) ;
	        }
	        else
	        	arrears = new Double(0);
	        sa.setMarrears(arrears+pamnt-sa.getMad()-sa.getMback()+sa.getMothers());
			sa.setFileKey("sale_insert");
			store(con, sa, null, 0);
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("mgctid", sa.getMgctid());
			retmap.put("workString", "团体客户月结费用信息");
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "save",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 保存固定资产信息
	 */
	public ResponseEnvelop saveAsset(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			//获取固定资产流水号
			List result = (Vector) DBUtil.querySQL(con,"select SEQ_ASTID.NEXTVAL from dual");
			BigDecimal id = (BigDecimal)((HashMap)result.get(0)).get("1");
			
			String astid = id.toString();
			business.setAstid(astid);
			business.setFileKey("bus04_001");
			
			store(con, business, null, 0);
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("astid", business.getAstid());
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "saveAsset",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 *显示固定资产修改页面
	 */
	public ResponseEnvelop showAsset(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus04_002");
			
			Object ob = find(con, business, null, 0);
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ob);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "showAsset",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 *保存修改后的固定资产信息
	 */
	public ResponseEnvelop modifyAsset(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus04_003");
			
			modify(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "modifyAsset",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 删除固定资产信息
	 */
	public ResponseEnvelop deleteAsset(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus04_004");
			
			remove(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAsset",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 保存摊销费用信息
	 */
	public ResponseEnvelop saveAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		String date = null;
		Integer year = null;
		Integer month = null;
		Integer day = null;
		Integer total = null;
		List result = null;
		BigDecimal id = null;
		String arzid = null;
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);


			// 获取摊销管理流水号1
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getHarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getHarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setHarzexpdt(calendar.getTime());
			business.setArzitem("房租");
			business.setHarzisexp("0");
			business.setHarzmon(business.getHarzamount()/Integer.parseInt(business.getHarzmonth()));
			business.setFileKey("bus05_001");
			store(con, business, null, 0);
			
			
			// 获取摊销管理流水号2
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getFarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getFarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setFarzexpdt(calendar.getTime());
			business.setArzitem("装修费");
			business.setFarzisexp("0");
			business.setFarzmon(business.getFarzamount()/Integer.parseInt(business.getFarzmonth()));
			business.setFileKey("bus05_002");
			store(con, business, null, 0);
			
			// 获取摊销管理流水号3
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getDarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getDarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setDarzexpdt(calendar.getTime());
			business.setArzitem("设备");
			business.setDarzisexp("0");
			business.setDarzmon(business.getDarzamount()/Integer.parseInt(business.getDarzmonth()));
			business.setFileKey("bus05_003");
			store(con, business, null, 0);
			
			// 获取摊销管理流水号4
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getAarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getAarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setAarzexpdt(calendar.getTime());
			business.setArzitem("固定资产");
			business.setAarzisexp("0");
			business.setAarzmon(business.getAarzamount()/Integer.parseInt(business.getAarzmonth()));
			business.setFileKey("bus05_004");
			store(con, business, null, 0);
			
			// 获取摊销管理流水号5
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getTarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getTarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setTarzexpdt(calendar.getTime());
			business.setArzitem("转让费");
			business.setTarzisexp("0");
			business.setTarzmon(business.getTarzamount()/Integer.parseInt(business.getTarzmonth()));
			business.setFileKey("bus05_005");
			store(con, business, null, 0);
			
			// 获取摊销管理流水号6
			result = (Vector) DBUtil.querySQL(con,"select SEQ_ARZID.NEXTVAL from dual");
			id = (BigDecimal) ((HashMap) result.get(0)).get("1");
			arzid = id.toString();
			business.setArzid(arzid);
			date = format.format(business.getOarzstdt());
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
			total = year * 12 + month + Integer.parseInt(business.getOarzmonth());
			year = total / 12;
			month = total % 12 - 2;
			calendar.set(year, month, day);
			business.setOarzexpdt(calendar.getTime());
			business.setArzitem("开办费");
			business.setOarzisexp("0");
			business.setOarzmon(business.getOarzamount()/Integer.parseInt(business.getOarzmonth()));
			business.setFileKey("bus05_006");
			store(con, business, null, 0);
			
			
			
			DBUtil.commit(con);
			
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "saveAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 显示修改摊销管理信息页面
	 */
	public ResponseEnvelop showAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus05_007");
			
			Object ob = find(con, business, null, 0);
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ob);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "showAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 保存修改后的摊销信息
	 */
	public ResponseEnvelop modifyAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus05_008");
			
			modify(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "modifyAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 删除摊销管理信息
	 */
	public ResponseEnvelop deleteAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee fee = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			fee.setFileKey("del_amo");
			
			remove(con, fee, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
/*	public ResponseEnvelop deleteAmortize(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			business.setFileKey("bus05_009");
			
			remove(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}*/
	
	
	/**
	 * 保存月度结账备注
	 */
	public ResponseEnvelop savent(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Sale sa = (Sale) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			sa.setFileKey("bus01_006");
			
			modify(con, sa, null, 0);
			
			DBUtil.commit(con);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "savent",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	public ResponseEnvelop edit(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Sale sa = (Sale) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
            //获取数据
			String sql = "select nvl(sum(ivtpamnt),0) as temp from tblinventory where ivtgcltid='"+sa.getMgctid()+"' and ivtyear='"+sa.getMyear()+"' and ivtmonth='"+sa.getMmonth()+"'";
			List result = (Vector) DBUtil.querySQL(con,sql);
			Double pamnt = null;
			if(result.size()>0)
				pamnt = Double.parseDouble(((HashMap) result.get(0)).get("1").toString());
	        Integer lyear = null;
	        Integer lmonth = null;
	        Integer year = sa.getMyear();
	        Integer month = sa.getMmonth();
	        if(month == 12){
	        	lyear = year - 1;
	        	lmonth = 1;
	        }else{
	        	lmonth = month - 1;
	        	lyear = year;
	        }
	        sql = "select nvl(m.marrears,0) as temp from tblmonth m where m.mgctid='"+sa.getMgctid()+"' and m.myear='"+lyear+"' and m.mmonth='"+lmonth+"'";
	        result = (Vector) DBUtil.querySQL(con,sql);
	        Double arrears = null;
	        if(result.size()>0){
		       arrears = Double.parseDouble(((HashMap) result.get(0)).get("1").toString()) ;
	        }
	        else
	        	arrears = new Double(0);
	        sa.setMarrears(arrears+pamnt-sa.getMad()-sa.getMback()+sa.getMothers());
			sa.setFileKey("sale_update");
			modify(con, sa, null, 0);

			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "edit",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	public ResponseEnvelop querysale(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Sale dto = (Sale) map.get("beo");
			dto.setFileKey("sale_sale");// 客户费用信息
			Object ci = find(con, dto, null, 0);
			if ("".equals(ci) || ci == null)
				throw new AppException("该月费用信息还没录入!");
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			 response.setHead(ExceptionSupport(className, "query",
			 ManageErrorCode.SQLERROR, ex.getMessage(), request
			 .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 保存成本价
	 */
	public ResponseEnvelop modifydis(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Collection<Business> collection = (Collection<Business>) map
					.get("collection");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			for (Business dto : collection) {
				if(!dto.getIvttype().equals("repair")){
				dto.setFileKey("bus03_000");
				}else{
					dto.setIvtyear((Integer)map.get("ivtyear"));
					dto.setIvtmonth((Integer)map.get("ivtmonth"));
					String sql = "select * from TBLREPDISCOUNT where rivtid='"+dto.getIvtid()+"' and rdispdtid ='"+dto.getIvtpdtid() +"' and rdisgctid='"+dto.getIvtgcltid()+"' and rdisyear='"+dto.getIvtyear()+"' and rdismonth='"+dto.getIvtmonth()+"'";
					List result = (Vector) DBUtil.querySQL(con,sql);
					if(result.size() > 0){
						dto.setFileKey("bus03_0005");
					}else{
						dto.setFileKey("bus03_0004");
					}
				}
				modify(con, dto, null, 0);
			}
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "modifydis",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}

	/**
	 * 上个月的费用
	 */
	public ResponseEnvelop lastMonth(RequestEnvelop request) {
		return find(request);
	}
	
	
	
	/**
	 * 月度结账
	 */
	public ResponseEnvelop account(RequestEnvelop request){
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		PreparedStatement pstm = null;
		try{
			HashMap map = (HashMap)request.getBody();
			Business bi = (Business)map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			
			//获取刚插入的tblinventory表中的数据记录
			
//			String sql0="select y.ivtyear,y.ivtmonth,y.ivtgcltid,y.ivtpdtid,y.ivttype,sum (nvl(ivtlmqnt,0))over(partition by ivtyear,ivtmonth,ivtgcltid,ivtpdtid) as mqnt,"+
//			"(case y.ivttype when 'make' then sum(nvl(y.ivtlsqnt,0))over(partition by ivtyear,ivtmonth,ivtgcltid,ivtpdtid) when 'normal'then nvl(y.ivtlsqnt,0) end) as sqnt,"+
//			"(case y.ivttype when 'make' then sum(nvl(y.ivtpqnt,0))over(partition by ivtyear,ivtmonth,ivtgcltid,ivtpdtid) when 'normal'then nvl(y.ivtpqnt,0) end) as pqnt,"+
//			"(case y.ivttype when 'make' then sum(nvl(y.ivtpamnt,0))over(partition by ivtyear,ivtmonth,ivtgcltid,ivtpdtid) when 'normal'then nvl(y.ivtpamnt,0) end) as pamnt"+
//		            "from tblinventory where ivtyear = '"+bi.getIvtyear()+"' and ivtmonth ='" + bi.getIvtmonth() + "'"; 
//					List result0 = (Vector)DBUtil.querySQL(con, sql0);
//			for(int i=0;i<result0.size();i++)
//			{
//				String ty=((HashMap)result0.get(i)).get("5").toString()
//				if("make".equals(ty))
//				{
//					String sql4="update tblinventory y set y.ivtlmqnt="+Integer.parseInt(((HashMap)result0.get(i)).get("5").toString())+
//				    ",y.sqnt where ivtyear = '"+bi.getIvtyear()+"' and ivtmonth ='" + bi.getIvtmonth() + "' and y.ivtgcltid='"+((HashMap)result0.get(i)).get("3").toString()+"'and y.ivtpdtid = '"+((HashMap)result0.get(i)).get("4").toString()+"'";
//				}
//				else if("normal".equals(ty))
//				{
//				String sql4="update tblinventory y set y.ivtlmqnt="+Integer.parseInt(((HashMap)result0.get(i)).get("6").toString())+
//			  " where ivtyear = '"+bi.getIvtyear()+"' and ivtmonth ='" + bi.getIvtmonth() + "' and y.ivtgcltid='"+((HashMap)result0.get(i)).get("3").toString()+"'and y.ivtpdtid = '"+((HashMap)result0.get(i)).get("4").toString()+"'";
//				}
//			}
			
			
			
			String sql1 = "select distinct ivtyear,ivtmonth,ivtgcltid,nvl(sum(ivtlmqnt)over(partition by ivtyear,ivtmonth,ivtgcltid),0) as mqnt" +
					",nvl(sum(ivtlsqnt)over(partition by ivtyear,ivtmonth,ivtgcltid),0)  as sqnt from tblinventory where ivtyear = '" + bi.getIvtyear()
				+ "' and ivtmonth ='" + bi.getIvtmonth() + "'"; 
//			String sql2 = "select distinct actstayear,actstamonth from tblactsta where actstayear ='"  + bi.getIvtyear() 
//					+ "' and actstamonth ='" + bi.getIvtmonth() + "'";
			String sql21 = "delete from tblactsta where actstayear ='"  + bi.getIvtyear() 
					+ "' and actstamonth ='" + bi.getIvtmonth() + "'";
			List result = (Vector)DBUtil.querySQL(con, sql1);
//			List result2 =(Vector)DBUtil.querySQL(con, sql2);
			
			pstm = con.prepareStatement(sql21);
            pstm.execute();
            
			for(int i = 0; i < result.size(); i ++){
//				if(result2.size() > 0){
//					break;
//				}
				Integer year = Integer.parseInt(((HashMap)result.get(i)).get("1").toString());
				Integer month = Integer.parseInt(((HashMap)result.get(i)).get("2").toString());
				String gcltid = ((HashMap)result.get(i)).get("3").toString();
				Integer mqnt = Integer.parseInt(((HashMap)result.get(i)).get("4").toString());
				Integer sqnt = Integer.parseInt(((HashMap)result.get(i)).get("5").toString()) ;
				List result1 = (Vector)DBUtil.querySQL(con, "select SEQ_ACTSTAID.NEXTVAL from dual");
				BigDecimal id = (BigDecimal)((HashMap)result1.get(0)).get("1");
				String staid = id.toString(); 
				
				bi.setActstaid(staid);
				bi.setActstayear(year);
				bi.setActstamonth(month);
				bi.setActstagcltid(gcltid);
				bi.setActmqnt(mqnt);
				bi.setActsqnt(sqnt);
				
				String sql3 = "select distinct ivtyear,ivtmonth,ivtgcltid,sum(ivtlmqnt)over(partition by ivtpdtid) as mmqnt," + 
						"sum(ivtlsqnt)over(partition by ivtpdtid)as ssqnt from tblinventory where ivtyear = '" + year + 
							"' and ivtmonth='" +
						month + "' and ivtgcltid='" + gcltid + "'";
				List result3 = (Vector)DBUtil.querySQL(con, sql3);
				int count = 0;
				bi.setActsta("1");
				Integer mmqnt = null;
				Integer ssqnt = null;
				if(result3.size() > 0){
					for(int j = 0; j< result3.size(); j++){
//						mmqnt = Integer.parseInt(((HashMap)result3.get(i)).get("4").toString());
//						ssqnt = Integer.parseInt(((HashMap)result3.get(i)).get("5").toString());//i错误，报out of index错误
						mmqnt = Integer.parseInt(((HashMap)result3.get(j)).get("4").toString());
						ssqnt = Integer.parseInt(((HashMap)result3.get(j)).get("5").toString());
						if(mmqnt != 0 || ssqnt != 0){//只要有结存数或者补入数则设为“未结账”
							bi.setActsta("0");
							count ++;
							break;
						}
//						if(count==0){
//							bi.setActsta("1");
//						}
					}
				/*
					if(count<result3.size())
					{
						bi.setActsta("1");
					}
				*/
				}
//				else{//如果没有结果，说明没有记录则不用结账
//					bi.setActsta("1");
//				}
				bi.setFileKey("bus01_004");
				
				store(con, bi, null, 0);
			}
			
			DBUtil.commit(con);
			
		} catch (AppException ae) {
			response.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "account",
					ManageErrorCode.SQLERROR, ex.getMessage(),
					request.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 显示修改库存期限页面
	 */
	public ResponseEnvelop print(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Business dto = (Business) map.get("beo");
			dto.setFileKey("bus02_002");
			Object ci = find(con, dto, null, 0);
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "print1",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 保存修改的库存期限
	 */
	public ResponseEnvelop saveExd(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Business dto = (Business) map.get("beo");
			dto.setFileKey("bus02_003");
			modify(con,dto,null,0);
			DBUtil.commit(con);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "saveExd",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * 查询加盟店还是直属店
	 */
	public String queryStoreType(String Gctnm) {
		String type = null;
		Connection con = null;
		PreparedStatement pstm = null;
		try{
			
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			String sql= "select t.GCTTYPE from TBLGRPCLIENT t where t.GCTNM='"+ Gctnm+"'"; 
			List result = (Vector) DBUtil.querySQL(con,sql);
			
			if(result.size()>0){
//				pamnt = Double.parseDouble(((HashMap) result.get(0)).get("1").toString()); 
				type = ((HashMap)result.get(0)).get("1").toString();
			}
			System.out.println(type);
			DBUtil.commit(con);
		} catch (Exception ex) {
		
			
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		
		return type;
	}
	
	/**
	 * 双耳率计算
	 */
	public ResponseEnvelop earCompute(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Fee fee = (Fee) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			//先清空双耳率表再向里面插入数据，最后打印出来
			fee.setFileKey("del_ear");
			remove(con, fee, null, 0);
			fee.setFileKey("new_ear");
			store(con, fee, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/**
	 * 更新超期库存计算状态
	 */
	public ResponseEnvelop gctstate(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);

			business.setFileKey("up_gct");
			store(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	public ResponseEnvelop saveWeb(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			Business business = (Business) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);

			business.setFileKey("web_insert");
			store(con, business, null, 0);
			DBUtil.commit(con);

		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "deleteAmortize",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	/*
	 * 提交订单
	 */
	public ResponseEnvelop commit(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			HashMap map = (HashMap) request.getBody();
			List<Business> bsList = (List<Business>) map.get("bsList");
			for (Business bs: bsList) {
				bs.setFileKey("web_save");
				store(con, bs, null, 0);
			}
			
			DBUtil.commit(con);
		} catch (AppException ae) {
			response.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "print",
					ManageErrorCode.SQLERROR, ex.getMessage(),
					request.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	@Override
	public ResponseEnvelop printWeb(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Business dto = (Business) map.get("beo");
			dto.setFileKey("web_print");
			Object ci = find(con, dto, null, 0);
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "print1",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	@Override
	public ResponseEnvelop modifyWeb(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Business dto = (Business) map.get("beo");
			dto.setFileKey("web_update");
			modify(con,dto,null,0);
			DBUtil.commit(con);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "saveExd",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	
	/**
	 * 添加分公司情况信息
	 */
	public ResponseEnvelop insertReport(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			BranchFinancialReport report = (BranchFinancialReport) map.get("beo");
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);
			// 判断ID是否存在和重复
			if (null == report.getGctid() || "".equals(report.getGctid())) {
				throw new AppException("客户代码不能为空!");
			}
			// 判断当月数据是否已经录入
			report.setFileKey("report_select");
			if (getCount(con, report, 0) > 0) {
				throw new AppException("该分公司该月情况已录入!");
			}
			/*
			//1月上月是上年12月
			Integer year=report.getIyear();
			Integer month=report.getImonth();
			if(report.getImonth()==1){
				year--;
				month=12;
			}
			//获得上月数据
			String sql="select r.moneyfunds,r.accountpayable,r.yearprofit,r.accuinvoiceamount,r.accuactualsales,r.accucostofsales from TBLBRANCHFINANCIALREPORT r where r.gctid='"+report.getGctid()+"' and r.iyear='" + year + "' and r.imonth='"+month+"'";
			List result = (Vector)DBUtil.querySQL(con, sql);
			Double moneyfunds,accountpayable,yearprofit,accuinvoiceamount,accuactualsales,accucostofsales;
			if(result.size()>0){
				//上月的货币资金、上月的应付货款、上月的本年利润、上月的累计开票额、上月的累计实际销售额、上月的累计主营业务成本
				moneyfunds = Double.valueOf(((HashMap)result.get(0)).get("1").toString());
				accountpayable = Double.valueOf(((HashMap)result.get(0)).get("2").toString());
				yearprofit = Double.valueOf(((HashMap)result.get(0)).get("3").toString());
				accuinvoiceamount = Double.valueOf(((HashMap)result.get(0)).get("4").toString());
				accuactualsales = Double.valueOf(((HashMap)result.get(0)).get("5").toString());
				accucostofsales = Double.valueOf(((HashMap)result.get(0)).get("6").toString());
			}else{
				moneyfunds = 0.00;
				accountpayable = 0.00;
				yearprofit = 0.00;
				accuinvoiceamount = 0.00;
				accuactualsales = 0.00;
				accucostofsales = 0.0;
			}
			//计算数据
			report.setInvoicerate((report.getInvoiceamount()*100/report.getTotalreturns()));//开票比例
			report.setPremoneyfunds(moneyfunds);//上月资金=上月的 本月货币资金
			//本月货币资金=上月资金+开票额-实付款-费用-工资-其他
			report.setMoneyfunds(moneyfunds+report.getInvoiceamount()-report.getActualpurchasecosts()-report.getMonthcosts()-report.getWagespayable()-report.getElsecosts());
			report.setPreaccountpayable(accountpayable);////上月应付账款=取上月 应付款项
			//本月应付账款=上月应付账款+本月进货款-实付货款
			report.setAccountpayable(accountpayable+report.getPurchasecosts()-report.getActualpurchasecosts());
			report.setPreaccuprofit(yearprofit);//上月累计利润=上月的 本年利润
			report.setYearprofit(yearprofit+report.getMonthprofit());//本年利润=上月累计利润+本月利润
			report.setAccuundisprofits(report.getPreyearundisprofits()+report.getYearprofit());//累计未分配利润=上年累计未分配利润+本年利润
			report.setPreaccuinvoiceamount(accuinvoiceamount);//上月累计开票销售额=上月的 累计开票销售额
			report.setAccuinvoiceamount(accuinvoiceamount+report.getInvoiceamount());//累计开票销售额=上月累计开票销售额+开票额
			report.setPreaccuactualsales(accuactualsales);//上月累计实际销售额=取上月 累计实际销售额
			report.setAccuactualsales(accuactualsales+report.getTotalreturns());//累计实际销售额=上月累计销售额+总销售额
			//累计开票量=累计开票销售额*1.03*100/累计实际销售额
			report.setAccuinvoicerate( (report.getAccuinvoiceamount()*1.03*100/report.getAccuactualsales()));
			report.setPreaccucostofsales(accucostofsales);//上月累计主营业务成本=上月 累计主营业务成本
			report.setAccucostofsales(accucostofsales+report.getPurchasecosts());//累计主营业务成本=上月累计主营业务成本+本月进货款
			report.setProfitrate( (report.getYearprofit()*100/report.getAccuinvoiceamount()));//利润率=本年利润/累计开票销售额
			*/
			//查找门店区域
			String findGctArea="select g.gctarea from tblgrpclient g where g.gctid='"+report.getGctid()+"'";
			List areaResult = (Vector)DBUtil.querySQL(con, findGctArea);
			report.setGctarea(((HashMap)areaResult.get(0)).get("1").toString());
			//插入数据
			report.setFileKey("report_insert");
			store(con,report,null,0);
			DBUtil.commit(con);
			HashMap<String, String> retmap = new HashMap<String, String>();
			retmap.put("gctId", report.getGctid());
			retmap.put("workString", "录入分公司情况信息");
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "insertReport",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	@Override
	public ResponseEnvelop queryReport(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			BranchFinancialReport dto = (BranchFinancialReport) map.get("beo");
			dto.setFileKey("report_select");
			Object ci = find(con, dto, null, 0);
			if ("".equals(ci) || ci == null)
				throw new AppException("没有查询到符合条件的信息!");
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			// response.setHead(ExceptionSupport(className, "print",
			// ManageErrorCode.SQLERROR, ex.getMessage(), request
			// .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	/**
	 * 查询月度汇总表保存信息
	 */
	@Override
	public ResponseEnvelop queryMonthReport(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			HashMap map = (HashMap) request.getBody();
			Fee dto = (Fee) map.get("beo");
			dto.setFileKey("fee1De_select");
			Object ci = find(con, dto, null, 0);
			if (ci==null||"".equals(ci))
				throw new AppException("没有查询到符合条件的信息!");
			DBUtil.commit(con);
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			retmap.put("beo", ci);
			response.setBody(retmap);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			// response.setHead(ExceptionSupport(className, "print",
			// ManageErrorCode.SQLERROR, ex.getMessage(), request
			// .getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
	
	public ResponseEnvelop updateReport(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			BranchFinancialReport report = (BranchFinancialReport) map.get("beo");
			/*
			//计算数据
			report.setInvoicerate( (report.getInvoiceamount()*100/report.getTotalreturns()));//开票比例
			//本月货币资金=上月资金+开票额-实付款-费用-工资-其他
			report.setMoneyfunds(report.getPremoneyfunds()+report.getInvoiceamount()-report.getActualpurchasecosts()-report.getMonthcosts()-report.getWagespayable()-report.getElsecosts());
			//本月应付账款=上月应付账款+本月进货款-实付货款
			report.setAccountpayable(report.getPreaccountpayable()+report.getPurchasecosts()-report.getActualpurchasecosts());
			report.setYearprofit(report.getPreaccuprofit()+report.getMonthprofit());//本年利润=上月累计利润+本月利润
			report.setAccuundisprofits(report.getPreyearundisprofits()+report.getYearprofit());//累计未分配利润=上年累计未分配利润+本年利润
			report.setAccuinvoiceamount(report.getPreaccuinvoiceamount()+report.getInvoiceamount());//累计开票销售额=上月累计开票销售额+开票额
			report.setAccuactualsales(report.getPreaccuactualsales()+report.getTotalreturns());//累计实际销售额=上月累计销售额+总销售额
			//累计开票量=累计开票销售额*1.03*100/累计实际销售额
			report.setAccuinvoicerate((report.getAccuinvoiceamount()*1.03*100/report.getAccuactualsales()));
			report.setAccucostofsales(report.getPreaccucostofsales()+report.getPurchasecosts());//累计主营业务成本=上月累计主营业务成本+本月进货款
			report.setProfitrate((report.getYearprofit()*100/report.getAccuinvoiceamount()));//利润率=本年利润/累计开票销售额
			*/
			con = DBUtil.getConnection();
			DBUtil.beginTrans(con);	
			report.setFileKey("report_update");
			modify(con,report,null,0);
			DBUtil.commit(con);
		} catch (AppException ae) {
			response
					.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "updateReport",
					ManageErrorCode.SQLERROR, ex.getMessage(), request
							.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;
	}
}
