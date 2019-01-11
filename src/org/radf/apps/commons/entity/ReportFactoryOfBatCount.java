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

public class ReportFactoryOfBatCount implements JRDataSource {

	private List<ReportOfBatCount> data ;
	
	 public List<ReportOfBatCount> setCollection(Integer startyear,Integer startmonth,Integer endyear,Integer endmonth) throws Exception{

		 List<ReportOfBatCount> data = new ArrayList<ReportOfBatCount>();
		 String sql1="select distinct s.gctnm,m.store_no ,b.NAME,m.battery_type,sum(m.battery_qty)over(PARTITION BY m.battery_type,m.STORE_NO order by m.battery_type) as temp1 from MEM_BAT m LEFT JOIN STORE s on s.GCTID = m.STORE_NO LEFT JOIN BATTERY b on b.TYPE = m.BATTERY_TYPE where m.status != 'del' and m.DATA between trunc(add_months(to_date('"+startyear+"' ||'-' ||'"+startmonth+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+endyear+"' ||'-' ||'"+endmonth+"' ||'-' ||'1','yyyy-mm-dd')+24 ORDER BY m.STORE_NO ";
		 //String sql1 = "select distinct t.gctnm,m.store_no ,p.PDTNM,m.battery_type,sum(m.battery_qty)over(PARTITION BY m.battery_type,m.STORE_NO order by m.battery_type) as temp1 from MEM_BAT m left JOIN TBLGRPCLIENT t on t.GCTID = m.STORE_NO LEFT JOIN TBLPRODUCT p on p.pdtid = m.BATTERY_TYPE where m.DATA between trunc(add_months(to_date('"+startyear+"' ||'-' ||'"+startmonth+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+endyear+"' ||'-' ||'"+endmonth+"' ||'-' ||'1','yyyy-mm-dd')+24 ORDER BY m.STORE_NO";
		 Connection con = null;
		 try {
			con = DBUtil.getConnection2();
//注释掉的用来测试同时连接两个数据库用可不可行
/*			Connection con2 = null;
			con2 = DBUtil.getConnection();
			
			Statement pstmt2;
			pstmt2 = con2.createStatement();
			
			String sql2 = "select AAE140 from AA01 where AAA004='外部清洗保养'";
			pstmt2=con2.createStatement();
			ResultSet rs2 = pstmt2.executeQuery(sql2);
			while(rs2.next()){
				System.out.println(rs2.getString("aae140"));
			}
			rs2.close();
			pstmt2.close();
			DBUtil.closeConnection(con2);*/
			
			// con = DBUtil.getConnection();
			//System.out.println(con);
			Statement pstmt;
			pstmt = con.createStatement();
		   
			ResultSet rs1 = pstmt.executeQuery(sql1);
			
			while(rs1.next()){ 

					ReportOfBatCount report0 = new ReportOfBatCount();
					
					report0.setStorename(rs1.getString("gctnm"));
					report0.setStoreid(rs1.getString("store_no"));
					//report0.setBatteryname(rs1.getString("pdtnm"));
					report0.setBatteryname(rs1.getString("name"));
					report0.setBatteryid(rs1.getString("battery_type"));
					report0.setNumber(Integer.valueOf(rs1.getString("temp1")));
						
					data.add(report0);
				
			}
			rs1.close();
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
           
        if ("storename".equals(fieldName))   
        {   
            value = data.get(index).getStorename();   
        }   
        else if ("storeid".equals(fieldName))   
        {   
            value = data.get(index).getStoreid();   
        }   
        else if ("batteryname".equals(fieldName))   
        {   
            value = data.get(index).getBatteryname();   
        }   
        else if ("batteryid".equals(fieldName))   
        {   
            value = data.get(index).getBatteryid();   
        }   
        else if ("number".equals(fieldName))   
        {   
            value = data.get(index).getNumber(); 
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
