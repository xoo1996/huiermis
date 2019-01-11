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

public class PrintFangXingFactory implements JRDataSource {

	private List<PrintFangXing> data ;
	
	 public List<PrintFangXing> setCollection(String[] listQaid) throws Exception{

		 List<PrintFangXing> data = new ArrayList<PrintFangXing>();
		 
		 Connection con = null;
		 String sql =null;
		 try {
			con = DBUtil.getConnection();
			Statement pstmt;
			pstmt = con.createStatement();
			sql = "select q.*,g.gctnm,p.pdtnm,s.bsc012 as fxman from tblqa q left join tblproduct p on p.pdtid=q.qapid left join tblfolio f on f.folno=q.qafno left join tblgrpclient g on g.gctid=f.folctid left join sc05 s on s.bsc011=q.qafxman where ";	
			
			sql+=" q.qaid='"+listQaid[0]+"' ";
			for(int i =1;i<listQaid.length;i++){
				sql+=" or q.qaid='"+listQaid[i]+"' ";
			}
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			while(rs.next()){ 
				
					PrintFangXing report0 = new PrintFangXing();
					
					if(rs.getString("qafno")== null || rs.getString("qafno").equals(""))
						report0.setQafno("");
					else
						report0.setQafno(rs.getString("qafno"));
					
					if(rs.getString("gctnm")== null || rs.getString("gctnm").equals(""))
						report0.setGctnm("");
					else
						report0.setGctnm(rs.getString("gctnm"));
					
					if(rs.getString("qacltnm")== null || rs.getString("qacltnm").equals(""))
						report0.setQacltnm("");
					else
						report0.setQacltnm(rs.getString("qacltnm"));
					
					if(rs.getString("pdtnm")== null || rs.getString("pdtnm").equals(""))
						report0.setPdtnm("");
					else
						report0.setPdtnm(rs.getString("pdtnm"));
					
					if(rs.getString("qasid")== null || rs.getString("qasid").equals(""))
						report0.setQasid("");
					else
						report0.setQasid(rs.getString("qasid"));
					
					/*if(rs.getString("isfangxing")== null || rs.getString("isfangxing").equals(""))
						report0.setIsfangxing("");
					else
						report0.setIsfangxing(rs.getString("isfangxing"));*/
					
					report0.setIsfangxing("ÊÇ");
					
					if(rs.getString("fxman")== null || rs.getString("fxman").equals(""))
						report0.setQafxman("");
					else
						report0.setQafxman(rs.getString("fxman"));
										
					data.add(report0);
					
				
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
           
        if ("qafno".equals(fieldName))   
        {   
            value = data.get(index).getQafno();   
        }   
        else if ("gctnm".equals(fieldName))   
        {   
            value = data.get(index).getGctnm();   
        }
        else if ("qacltnm".equals(fieldName))   
        {   
            value = data.get(index).getQacltnm();   
        }
        else if ("pdtnm".equals(fieldName))   
        {   
            value = data.get(index).getPdtnm();   
        }   
        else if ("qasid".equals(fieldName))   
        {   
            value = data.get(index).getQasid();   
        }   
        else if ("isfangxing".equals(fieldName))   
        {   
            value = data.get(index).getIsfangxing(); 
        }   
        else if ("qafxman".equals(fieldName))   
        {   
            value = data.get(index).getQafxman();   
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
