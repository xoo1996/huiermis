package org.radf.plat.util.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.radf.apps.commons.entity.Business;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.QueryFunction;
import org.radf.plat.util.exception.NoConnectionException;

public class BatchProcessing {
	
	private static Connection conn = null;
	//private static PreparedStatement ps = null;
	private static Statement st = null;  


	public static void batchProcessing(Collection<Business> collection,String userId){
		// 方式2：批量提交
		String hql,sql;
		int i=0;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			for (Business dto : collection) {
				
				dto.setIvtoprcd(userId);
				dto.setFileKey("business_update");
				sql = DBUtil.getSQLByObject(dto,2);
				String parallelString = "/*+parallel(tblinventory,4)*/";
				hql = sql.substring(0, 7) + parallelString + sql.substring(7, sql.length());
				
				//sql = QueryFunction.getHQL(hql, dto);
				System.out.println(hql);
				//ps = conn.prepareStatement(sql);
			    st.addBatch(hql);
			    
			    dto.setFileKey("bus02_000");
			    hql = DBUtil.getSQLByObject(dto,2);
			    //sql = QueryFunction.getHQL(hql, dto);
			    System.out.println(hql);
			    //ps = conn.prepareStatement(sql);
			    st.addBatch(hql);
			    
				dto.setActsta("1");
				dto.setFileKey("bus01_007");
				hql = DBUtil.getSQLByObject(dto,2);
				//sql = QueryFunction.getHQL(hql, dto);
				System.out.println(hql);
				//ps = conn.prepareStatement(sql);
				st.addBatch(hql);
				i++;
				if(i%100==0){
					st.executeBatch();
					conn.commit();
				}
			}
			st.executeBatch();
			conn.commit();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * only月结表信息修改
	 * @param collection
	 * @param userId
	 */
	public static void batchProcessingOnly(Collection<Business> collection,String userId,String mark){
		// 方式2：批量提交
		String hql,sql;
		int i=0;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			for (Business dto : collection) {
				
				dto.setIvtoprcd(userId);
				if("jmself".equals(mark)){//加盟店自己更新月结表
					dto.setFileKey("business_update_j");
				}else{//商务部处更新月结表方式
					dto.setFileKey("business_update");
				}
				sql = DBUtil.getSQLByObject(dto,2);
				String parallelString = "/*+parallel(tblinventory,4)*/";
				hql = sql.substring(0, 7) + parallelString + sql.substring(7, sql.length());
				
				//sql = QueryFunction.getHQL(hql, dto);
				System.out.println(hql);
				//ps = conn.prepareStatement(sql);
			    st.addBatch(hql);
				i++;
				if(i%100==0){
					st.executeBatch();
					conn.commit();
				}
			}
			st.executeBatch();
			conn.commit();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
