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

public class PrintBeforeVerifyFactory implements JRDataSource {

	private List<PrintBeforeVerify> data ;
	
	 public List<PrintBeforeVerify> setCollection(String folno,String gctnm,String foltype,String fdtcltnm,String start,String end) throws Exception{

		 List<PrintBeforeVerify> data = new ArrayList<PrintBeforeVerify>();
		 
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();
			
			String sql = "SELECT DISTINCT h.folno,(CASE WHEN s.aab300 = '»Ý¶úÌýÁ¦×Ü²¿' THEN s.aab300 ELSE g.gctnm END) AS gctnm,d.FDTCLTNM,p.PDTNM,m.tmksid,d.FDTQNT,d.FDTNT FROM tblfoldetail d LEFT OUTER JOIN tblmaking m ON d.fdtfno = m.tmkfno LEFT OUTER JOIN tblfolio h ON h.folno = d.fdtfno LEFT OUTER JOIN sc05 o ON o.bsc011 = h.folopr LEFT OUTER JOIN tblgrpclient g ON g.gctid = h.folctid LEFT OUTER JOIN tblproduct p ON p.pdtid = d.fdtpid LEFT OUTER JOIN tblindclient i ON h.folindctid = i.ictid LEFT JOIN sc01 s ON h.folctid = s.bsc001 where h.folsta IN ('commited','pass') ";
					//"WHERE h.folno LIKE P{folno} AND d.fdtcltnm LIKE $P{fdtcltnm} AND g.gctnm LIKE $P{gctnm} AND h.foltype like $P{foltype} AND h.folsta IN ('commited','pass')";
			if(folno != null && !folno.equals(""))
				sql += "and h.folno like '%"+folno+"%' ";
			if(gctnm != null && !gctnm.equals(""))
				sql += "and g.gctnm like '%"+gctnm+"%' ";
			if(foltype != null && !foltype.equals(""))
				sql += "and h.foltype = '"+foltype+"' ";
			if(fdtcltnm != null && !fdtcltnm.equals(""))
				sql += "and d.fdtcltnm like '%"+fdtcltnm+"%' ";
			if(start != null && !start.equals(""))
				sql += "and h.foldt >= TO_DATE('"+start+"','yyyy-MM-dd') ";
			if(end != null && !end.equals(""))
				sql += "and h.foldt <= TO_DATE('"+end+"','yyyy-MM-dd') ";
			
			Statement pstmt;
			pstmt = con.createStatement();
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery(sql);

			while(rs.next()){ 
				
					PrintBeforeVerify report0 = new PrintBeforeVerify();
					
					if(rs.getString("folno")== null || rs.getString("folno").equals(""))
						report0.setFolno("");
					else
						report0.setFolno(rs.getString("folno"));
					
					if(rs.getString("gctnm")== null || rs.getString("gctnm").equals(""))
						report0.setGctnm("");
					else
						report0.setGctnm(rs.getString("gctnm"));
					
					if(rs.getString("fdtcltnm")== null || rs.getString("fdtcltnm").equals(""))
						report0.setFdtcltnm("");
					else
						report0.setFdtcltnm(rs.getString("fdtcltnm"));
					
					if(rs.getString("pdtnm")== null || rs.getString("pdtnm").equals(""))
						report0.setPdtnm("");
					else
						report0.setPdtnm(rs.getString("pdtnm"));
					
					if(rs.getString("tmksid")== null || rs.getString("tmksid").equals(""))
						report0.setTmksid("");
					else
						report0.setTmksid(rs.getString("tmksid"));
					
					if(rs.getString("fdtqnt")== null || rs.getString("fdtqnt").equals(""))
						report0.setFdtqnt("");
					else
						report0.setFdtqnt(rs.getString("fdtqnt"));
					
					if(rs.getString("fdtnt")== null || rs.getString("fdtnt").equals(""))
						report0.setFdtnt("");
					else
						report0.setFdtnt(rs.getString("fdtnt"));
										
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
        else if ("pdtnm".equals(fieldName))   
        {   
            value = data.get(index).getPdtnm();   
        }   
        else if ("tmksid".equals(fieldName))   
        {   
            value = data.get(index).getTmksid();   
        }   
        else if ("fdtqnt".equals(fieldName))   
        {   
            value = data.get(index).getFdtqnt(); 
        }   
        else if ("fdtnt".equals(fieldName))   
        {   
            value = data.get(index).getFdtnt();   
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
