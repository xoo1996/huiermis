package com.cm.service;

import java.io.*;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.sql.CLOB;
import oracle.sql.DATE;

import org.junit.Test;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.util.exception.NoConnectionException;

import com.ctc.wstx.util.DataUtil;

public class TestFile {
	Connection conn;

	@Test
	public void fileupload() {
		try {
			String filePath = "d:\\品管部.txt";
			String encoding = "GBK";
			StringBuffer sb = new StringBuffer();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
					System.out.println(lineTxt);
				}
				System.out.println(sb.toString());
				testfile("1111111111", sb.toString());
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public void testfile(String guid, String text)  {
			
			try{
				conn=DBUtil.getConnection();
				DBUtil.beginTrans(conn);
			String sql3 = "insert into tbltlfile (tblguid) values(?)";
			PreparedStatement stmt = conn.prepareStatement(sql3);
			stmt.setString(1, guid);
			stmt.execute();
			DBUtil.closeStatement(stmt);
			CallableStatement pstmt = null;
			ResultSet resultset = null;
			String sql1 = "select tblcontent from tbltlfile where tblguid='"
					+ guid + "' for update";
			String sql2 = "update tbltlfile set tblcontent='EMPTY_CLOB()' where tblguid='"
					+ guid + "'";

			pstmt = conn.prepareCall(sql2);
			resultset = pstmt.executeQuery();
			DBUtil.closeStatement(pstmt);
			pstmt = conn.prepareCall(sql1);
			resultset = pstmt.executeQuery();
			while (resultset.next()) {
				CLOB clob = (oracle.sql.CLOB) resultset.getClob(1);
				java.io.Writer pw = clob.getCharacterOutputStream();
				pw.write(text);
				pw.flush();
				pw.close();
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);

		}
        /* String sql="insert into tbltlfile (tblguid,tblcontent) values ('"+guid+"',?)";
         PreparedStatement stmt;
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTrans(conn);
			stmt = conn.prepareStatement(sql);
			Reader clobReader = new StringReader(text); // 将 text转成流形式  
	         stmt.setCharacterStream(1, clobReader, text.length());// 替换sql语句中的？  
	         int num = stmt.executeUpdate();// 执行SQL  
	         if (num > 0) {  
	             System.out.println("ok");  
	         } else {  
	             System.out.println("NO");  
	         }  
	         stmt.close();
	         DBUtil.commit(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 加载SQL语句  
 catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         // PreparedStatement支持SQL带有问号？，可以动态替换？的内容。
		finally{
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}*/
         
	}
	@Test
	public void testdown(){
		System.out.println(downByguid("1111111111").toString());
		
	}
	public String downByguid(String guid){
		String reString = "";
		try {
			String sql="select tblcontent from tbltlfile where tblguid='"+guid+"'";
			conn=DBUtil.getConnection();
			Clob clob=null;
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			while(rs.next()){
				clob=(Clob)rs.getObject(1);
			}
			
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
			}
			reString = sb.toString();
			
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.rollback(conn);
			DBUtil.closeConnection(conn);
		}
		return reString;
		
	}
	@Test
	public void testtime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHHmmss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		System.out.println(df.format(new Date()).toString());
		String str1=df.format(new Date()).toString();
		String downfilename="11"+str1;
		File file1 =new File("D:\\debug\\"+downfilename); 
		//如果文件夹不存在则创建    
		if  (!file1 .exists()  && !file1 .isDirectory())      
		{       
		    System.out.println("//不存在");  
		    file1 .mkdir();    
		} else   
		{  
		    System.out.println("//目录存在");  
		}
		
		
	}
}
