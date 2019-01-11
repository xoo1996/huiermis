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

public class ReportCusReFactory implements JRDataSource {

	private List<CusReReport> data ;
	
	 public List<CusReReport> setCollection() throws Exception{

		 List<CusReReport> data = new ArrayList<CusReReport>();
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();

			String sql = "SELECT o.folno,g.gctnm,l.fdtcltnm,l.fdtpid,p.pdtnm,l.fdtexadt FROM tblfoldetail l JOIN tblfolio o ON l.fdtfno = o.folno LEFT JOIN tblproduct p ON l.fdtpid = p.pdtid LEFT JOIN tblgrpclient g ON g.gctid = o.folctid where l.fdtexadt is not null and to_char(l.fdtexadt,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') order by l.fdtexadt DESC ";

			Statement pstmt;
			pstmt = con.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
					
			while (rs.next()) {
				CusReReport cusReReport = new CusReReport();
				
				cusReReport.setFolno(rs.getString("folno"));
				cusReReport.setGctnm(rs.getString("gctnm"));
				cusReReport.setFdtcltnm(rs.getString("fdtcltnm"));
				cusReReport.setFdtpid(rs.getString("fdtpid"));
				cusReReport.setPdtnm(rs.getString("pdtnm"));
				cusReReport.setFdtexadt(rs.getString("fdtexadt"));
				
				data.add(cusReReport);
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
           
        if ("folno".equals(fieldName))   
        {   
            value = data.get(index).getFolno();   
        }   
        else if ("gctnm".equals(fieldName))   
        {   
            value = data.get(index).getGctnm();   
        }   
        else if ("fdtcltnm".equals(fieldName))   
        {   
            value = data.get(index).getFdtcltnm();   
        }   
        else if ("fdtpid".equals(fieldName))   
        {   
            value = data.get(index).getFdtpid();   
        }   
        else if ("pdtnm".equals(fieldName))   
        {   
            value = data.get(index).getPdtnm(); 
        }   
        else if ("fdtexadt".equals(fieldName))   
        {   
            value = data.get(index).getFdtexadt();   
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
