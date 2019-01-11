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

public class ReportFactory3 implements JRDataSource {

	private List<Report> data ;
	
	 public List<Report> setCollection(String gcltid,Integer year,Integer month) throws Exception{

		 List<Report> data = new ArrayList<Report>();
		 int maxfee = 0;
		 String[] feename = {
				 "管理费","开票税","税收","社保费","工资","广告费","电话费","水电费",
				 "差旅费","邮费","办公费","公关费","会计工资","手续费","促销","退机费","其他","优惠"
		 };//18
		 String[] feefile = {
				 "feemanage","feeinvoice","feetax","feepension","feepay","feead","feephone","feewater",
				 "feetrip","feepostage","feeoffice","feesocial","feeaccount","feeprocess","feepromotion","feeback","feothersa","feebenefit"
		 };
		 String[] amortizename = {
				 "房租费","转让费","装修费","开办费","固定资产(电脑)","固定资产(空调)","固定资产","设备"
		 };
		 String[] amofile = {
				 "rent","trans","built","open","assetscom","assetscon","other","assetsmach",
		 };
		 String[] score = {
				 "积分成本","惠耳币成本"
		 };
		 String[] coin = {
				 "buyscore","applycoin",
		 };
		 Connection con = null;
		 try {
			con = DBUtil.getConnection();
			//String sql1 = "select  count(*) as maxrow from tblinventory i left outer join tblproduct p on p.pdtid= i.ivtpdtid  where i.ivtpamnt>0 and i.ivtgcltid='"+gcltid+"' and i.ivtyear='"+year+"' and i.ivtmonth='"+month+"' ";
			
			String sql2 = "SELECT DISTINCT NVL(SUM(i.ivtpqnt)over(partition BY p.pdtid,i.ivttype,i.ivtnote order by i.ivttype, p.pdtid),0) AS temp01," +
					//查询月结表中的月结回款数量由月结商品代码和月结类型分类，按照月结类型和月结商品代码排序
					" NVL(SUM(i.ivtpamnt)over(partition BY p.pdtid,i.ivttype,i.ivtnote order by i.ivttype, p.pdtid),0) AS temp02," +
					//查询月结表中的月结回款金额由商品表的商品代码和月结表的月结类型分类，按照月结类型和商品代码排序
					" p.pdtid, p.pdtnm, d.disprice, p.pdtprc, p.pdtcls, I.Ivttype, i.ivtnote, " +
					//商品代码，商品名称，成本扣率？，耳机价格，耳机类别，月结类型
					"Nvl(Sum(S.Changemon)Over(Partition By S.Bsc011,S.Pdtid,i.ivtnote Order By S.Pdtid),0)*0.04 As score, " +
					"Nvl(Sum(S.Changecoin)Over(Partition By S.Bsc011,S.Pdtid,i.ivtnote Order By S.Pdtid),0) As coin " +
					"FROM tblinventory i " +
					//月结表
					"LEFT OUTER JOIN tblproduct p ON p.pdtid= i.ivtpdtid " +
					//产品表
					"LEFT OUTER JOIN tbldiscount d ON (d.dispdtid =i.ivtpdtid And D.Disgctid =I.Ivtgcltid) " +
					//成本扣率表（产品代码=月结商品代码And客户代码=月结团体客户代码）
					"LEFT JOIN tblscore s On S.Pdtid=P.Pdtid AND s.scoredate between trunc(add_months(to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd')+24 and s.bsc011='"+gcltid+"' " +
					"WHERE (i.ivtpqnt <>0 OR i.ivtpamnt <>0) And I.Ivttype <>'repair' And I.Ivttype <>'repairEar' AND (p.pdtnt != 'zsdc' or p.pdtnt is null) And I.Ivtgcltid ='"+gcltid+"' AND i.ivtyear ='"+year+"' AND i.ivtmonth ='"+month+"' AND (i.ivtnote not in ('加急费','续保费') or i.ivtnote is null) " +
					//积分表
					"UNION ALL SELECT DISTINCT 1 AS temp01, " +
					//加急费部分
					"i.ivtpamnt  AS temp02,  " +
					" p.pdtid, p.pdtnm ||' ' ||i.ivtnote, i.ivtfee AS pdtprc, i.ivtfee, p.pdtcls, i.ivttype,  i.ivtnote, " +
					"0 As score, 0 As coin FROM tblinventory i " +
					"LEFT OUTER JOIN tblproduct p ON p.pdtid= i.ivtpdtid " +
					"LEFT OUTER JOIN tbldiscount d ON (d.dispdtid   =i.ivtpdtid And D.Disgctid   =I.Ivtgcltid) " +
					"WHERE i.ivttype <>'repair' And I.Ivttype <> 'repairEar' And I.Ivtgcltid  ='"+gcltid+"' AND i.ivtyear ='"+year+"' AND i.ivtmonth ='"+month+"' AND ivtnote IN ('续保费','加急费') " +
					"ORDER BY 7, 8, 4 DESC  ";
			//sql3选取整个tblfee表中的所有符合年月和店铺id的行
			String sql3 = "select distinct p.* from tblfee p where feegctid='"+gcltid+"' and feeyear='"+year+"' and feemonth='"+month+"'";
			//s

/*			String sql4 = "select distinct nvl(sum(i.ivtpqnt)over(partition by i.ivttype order by i.ivttype),0) as temp01," +
					//月结回款数分组连续求和作为temp1
					"nvl(sum(i.ivtpamnt)over(partition by i.ivttype order by i.ivttype),0) as temp02," +
					//月结回款金额分组连续求和作为temp2
					"NVL(SUM(s.changecoin)Over(Partition By S.Bsc011 Order By S.Pdtid),0) AS coin " +
					//购买助听器抵扣惠耳币分组连续求和作为coin，Bsc011账号Pdtid商品id
					"from tblinventory i " +
					"LEFT JOIN tblscore s On S.Pdtid=I.Ivtpdtid AND s.scoredate between trunc(add_months(to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd')+24 and s.bsc011='"+gcltid+"' where i.ivtpamnt>0 and (i.ivttype='repair' or i.ivttype='repairEar') and i.ivtgcltid='"+gcltid+"' and i.ivtyear='"+year+"' and i.ivtmonth='"+month+"' ";
*/
			String sql4 ="select temp01,temp02 from (select distinct sum(i.ivtpqnt) as temp01," +
					"sum(i.ivtpamnt) as temp02 " +
					"from tblinventory i " +
					"where i.ivtpamnt>0 and (i.ivttype='repair' or i.ivttype='repairEar') and i.ivtgcltid='"+gcltid+"' and i.ivtyear='"+year+"' and i.ivtmonth='"+month+"') " +
					"where temp01 is not null and temp02 is not null ";
			
			/*			String sql5 = "SELECT SUM(rdisprice) AS chengben " +
					"FROM tblrepdiscount " +
					//rediscount????discount???
					"where rdisgctid='"+gcltid+"' and rdisyear='"+year+"' and rdismonth='"+month+"' ";*/
			String sql5 ="SELECT NVL(SUM( DECODE(d.rdisprice,NULL,i.ivtpamnt,d.rdisprice)),0) as ressum from tblinventory i left join tblrepdiscount d  ON (d.rdispdtid = i.ivtpdtid AND d.rdisgctid = i.ivtgcltid AND d.rivtid = i.ivtid) where (i.ivttype = 'repair' or i.ivttype = 'repairEar') AND i.ivtpqnt > 0 AND i.ivtfee <> 0 AND i.ivtgcltid = '"+gcltid+"' AND i.ivtyear = '"+year+"' AND i.ivtmonth = '"+month+"' ";
			//sql5用来维修费的成本，其中包括两个部分，一个是之前的只包括repair类型的成本，另一个是repairEar类型的成本
			String sql6 = "SELECT NVL(ROUND(SUM( DECODE(amotype,'trans',meiyue,NULL)),2),0)  AS trans, " +
					"  NVL(ROUND(SUM( DECODE(amotype,'assetscom',meiyue,NULL)),2),0)   AS assetscom, " +
					"  NVL( ROUND( SUM( DECODE(amotype,'assetscon',meiyue,NULL)),2),0) AS assetscon, " +
					"  NVL(ROUND(SUM(DECODE(amotype,'assetsmach',meiyue,NULL)),2),0)   AS assetsmach, " +
					"  NVL(ROUND(SUM( DECODE(amotype,'built',meiyue,NULL)),2),0)       AS built, " +
					"  NVL(ROUND(SUM( DECODE(amotype,'open',meiyue,NULL)),2),0)        AS OPEN, " +
					"  NVL(ROUND(SUM( DECODE(amotype,'other',meiyue,NULL)),2),0)       AS other, " +
					"  NVL(ROUND(SUM( DECODE(amotype,'rent',meiyue,NULL)),2),0)        AS rent " +
					"FROM " +
					"  (SELECT t.amotype                                      AS amotype, " +
					"    t.feegctid                                           AS id, " +
					"    SUM(t.money/(months_between(t.feeend,t.feestart)+1)) AS meiyue " +
					"  FROM tblfeeamortize t " +
					"  WHERE t.feegctid = '" + gcltid +
					"' AND feestart    <= to_date('" +year+
					"'||'-"+month+"','yyyy-mm') " +
					"  AND feeend >= to_date('" +year+
					"'||'-"+month+"','yyyy-mm') " +
					"  GROUP BY amotype, " +
					"    t.feegctid " +
					"  )";
			String sql7 = "SELECT DISTINCT NVL(SUM(i.ivtpqnt)over(partition BY p.pdtid,i.ivttype order by i.ivttype, p.pdtid),0) AS temp01," +
					"NVL(SUM(i.ivtpamnt)over(partition BY p.pdtid,i.ivttype order by i.ivttype, p.pdtid),0) AS temp02," +
					"p.pdtid,p.pdtprc,p.pdtnm, d.disprice,k.countnum,g.countnum2 " +
					"FROM tblinventory i LEFT OUTER JOIN tbldiscount d ON (d.dispdtid =i.ivtpdtid And D.Disgctid =I.Ivtgcltid) " +
					"LEFT OUTER JOIN tblproduct p ON p.pdtid= i.ivtpdtid " +
					"LEFT OUTER JOIN (SELECT DISTINCT NVL(sum(b.GET_BAT_NUM)over(partition BY b.BATTERY_TYPE ),0) as countnum,battery_type from BAT_EVENT b where IN_STORE_NO = '"+gcltid+"' AND GET_STORE_NO <> '"+gcltid+"' AND b.handle_date between trunc(add_months(to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd')+24) k ON k.battery_type = p.pdtid LEFT OUTER JOIN (SELECT DISTINCT NVL(sum(b.GET_BAT_NUM)over(partition BY b.BATTERY_TYPE ),0) as countnum2,battery_type from BAT_EVENT b where IN_STORE_NO <> '"+gcltid+"' AND GET_STORE_NO = '"+gcltid+"' AND b.handle_date between trunc(add_months(to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd'),-1),'mm')+25 AND to_date('"+year+"' ||'-' ||'"+month+"' ||'-' ||'1','yyyy-mm-dd')+24) g ON g.battery_type = p.pdtid WHERE (i.ivtpqnt <>0 OR i.ivtpamnt <>0 OR (k.countnum is not null and k.countnum <> 0) OR (g.countnum2 is not null and g.countnum2 <> 0)) And I.Ivttype <>'repair' AND p.pdtnt = 'zsdc' And I.Ivtgcltid ='"+gcltid+"' AND i.ivtyear ='"+year+"' AND i.ivtmonth ='"+month+"' ";
			//四种特殊的用来赠送的电池
			System.out.println(sql7+"+++++++++++++++++++++++++++++++");
			Statement pstmt;
			pstmt = con.createStatement();

			ResultSet rs6 = pstmt.executeQuery(sql6);
			while (rs6.next()) {
				for (int i = 0; i < 8; i++) {
					Report report = new Report();
					report.setFname(amortizename[i]);
					if (rs6.getString(amofile[i]) == null) {
						report.setOutfee(new Double(0));
					} else
						report.setOutfee(Double.valueOf(rs6
								.getString(amofile[i])));
					data.add(report);
				}
				maxfee =8;
			}
		   rs6.close();
		
			ResultSet rs3 = pstmt.executeQuery(sql3);
			while (rs3.next()) {
				for (int i = 0; i < 18; i++) {
					Report report = new Report();
					report.setFname(feename[i]);
					if (rs3.getString(feefile[i]) == null) {
						report.setOutfee(new Double(0));
					} else
						report.setOutfee(Double.valueOf(rs3
								.getString(feefile[i])));
					data.add(report);
				}
				maxfee += 18;
			}
		   rs3.close();
		   
			ResultSet rs2 = pstmt.executeQuery(sql2);
			System.out.println("sql2:"+sql2);
			int count = 0;
					
			while(rs2.next()){
			//	System.out.println(rs2.getString("pdtid")+"++++++"+rs2.getString("ivtnote"));
				if(count >= maxfee){
					Report report0 = new Report();
					
					//前半段作用不明，后半段为赠送的唯听微晶耳膜特殊处理
				//	System.out.println(rs2.getString("pdtid")+"%%%%%%"+rs2.getString("ivtnote"));
					if(rs2.getString("disprice")== null||(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）")))
						//如果打折后价格为null
						report0.setDiscount(new Double(0));
					else
						report0.setDiscount(Double.valueOf(rs2.getString("disprice")));
						//成本价
					if(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）"))
						report0.setPname("（赠送）"+rs2.getString("pdtnm"));
					else
						report0.setPname(rs2.getString("pdtnm"));
						//商品名称
						report0.setNumber(Integer.valueOf(rs2.getString("temp01")));
						//temp1为数量
						
						//前半段作用不明，后半段为赠送的唯听微晶耳膜特殊处理
					if(rs2.getString("pdtprc") == null||(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）")))
						report0.setPrice(new Double(0));
					else
					    report0.setPrice(Double.valueOf(rs2.getString("pdtprc")));
						//单价
						report0.setSales(Double.valueOf(rs2.getString("temp02")));
						//temp2为销售额
					//report0.setFname("");
					//report0.setOutfee(new Double(0));	
				/*	if(rs2.getString("score") == null)
						report0.setDtds(new Double(0));
					else report0.setDtds(Double.valueOf(rs2.getString("score")));
					if(rs2.getString("coin") == null)
						report0.setTdds(new Double(0));
					else 
						report0.setTdds(Double.valueOf(rs2.getString("coin")));
					*/
						
					report0.setDtds(new Double(0));
					report0.setTdds(new Double(0));
					//以上两条为了有值
					
					data.add(report0);
					}
				else{
					Report re = data.get(count);
				//	System.out.println(rs2.getString("pdtid")+"%%%%%%"+rs2.getString("ivtnote"));
					if(rs2.getString("disprice")== null||(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）")))
						re.setDiscount(new Double(0));
					else
					    re.setDiscount(Double.valueOf(rs2.getString("disprice")));
					
					if(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）"))
						re.setPname("（赠送）"+rs2.getString("pdtnm"));
					else
						re.setPname(rs2.getString("pdtnm"));
					
					re.setNumber(Integer.valueOf(rs2.getString("temp01")));
						
					if(rs2.getString("pdtprc") == null||(rs2.getString("pdtid").equals("999616")&&rs2.getString("ivtnote")!=null&&rs2.getString("ivtnote").equals("赠送（month）")))
						re.setPrice(new Double(0));
					else
					    re.setPrice(Double.valueOf(rs2.getString("pdtprc")));
						re.setSales(Double.valueOf(rs2.getString("temp02")));
						
			/*		if(rs2.getString("score") == null)
						re.setDtds(new Double(0));
					else 
						re.setDtds(Double.valueOf(rs2.getString("score")));
					
					if(rs2.getString("coin") == null)
						re.setTdds(new Double(0));
					else 
						re.setTdds(Double.valueOf(rs2.getString("coin")));*/
						
					re.setDtds(new Double(0));	
					re.setTdds(new Double(0));
				}
				count ++;
				
					
			}
			rs2.close();
			
			/**
			 * Author:liangping
			 * Time:2017/6/29
			 * 赠送电池打印在利润表中
			 */
			ResultSet rs7 = pstmt.executeQuery(sql7);
			System.out.println("sql7:"+sql7);
			while(rs7.next()){ 
				if(count >= maxfee){
					Report report0 = new Report();
					
					if(rs7.getString("disprice")== null)
						report0.setDiscount(new Double(0));
					else
						report0.setDiscount(Double.valueOf(rs7.getString("disprice")));
					
						report0.setPname(rs7.getString("pdtnm"));
						report0.setNumber(Integer.valueOf(rs7.getString("temp01")));
						
					if(rs7.getString("pdtprc") == null)
						report0.setPrice(new Double(0));
					else
					    report0.setPrice(Double.valueOf(rs7.getString("pdtprc")));
						
						report0.setSales(Double.valueOf(rs7.getString("temp02")));
					
					if(rs7.getString("countnum2") == null)
						report0.setTdds(new Double(0));
					else
					    report0.setTdds(Double.valueOf(rs7.getString("countnum2")));
					
					if(rs7.getString("countnum") == null)
						report0.setDtds(new Double(0));
					else
					    report0.setDtds(Double.valueOf(rs7.getString("countnum")));
						
					
						
					data.add(report0);
					}
				else{
					Report re = data.get(count);
					if(rs7.getString("disprice")== null)
						re.setDiscount(new Double(0));
					else
						re.setDiscount(Double.valueOf(rs7.getString("disprice")));
						re.setPname(rs7.getString("pdtnm"));
						re.setNumber(Integer.valueOf(rs7.getString("temp01")));
						
					if(rs7.getString("pdtprc") == null)
						re.setPrice(new Double(0));
					else
					    re.setPrice(Double.valueOf(rs7.getString("pdtprc")));
						//单价
						re.setSales(Double.valueOf(rs7.getString("temp02")));
					if(rs7.getString("countnum2") == null)
						re.setTdds(new Double(0));
					else
					    re.setTdds(Double.valueOf(rs7.getString("countnum2")));
					
					if(rs7.getString("countnum") == null)
						re.setDtds(new Double(0));
					else
					    re.setDtds(Double.valueOf(rs7.getString("countnum")));		
				}
				count ++;
			}
			rs7.close();
			
			Statement pstmt2;
			pstmt2 = con.createStatement();
			
			ResultSet rs4 = pstmt.executeQuery(sql4);
			ResultSet rs5 = pstmt2.executeQuery(sql5);
			
			while(rs4.next()){ 
				if(count >= maxfee){
					Report report0 = new Report();
					//report0.setDiscount(new Double(0));
					report0.setPname("维修费");
					report0.setNumber(new Integer(1));
					report0.setPrice(new Double(0));
					report0.setSales(Double.valueOf(rs4.getString("temp02")));
					while(rs5.next()){
						report0.setDiscount(Double.valueOf(rs5.getString("ressum")));
					}
					
					/*if(rs4.getString("coin") == null)
						report0.setTdds(new Double(0));
					else report0.setTdds(Double.valueOf(rs4.getString("coin")));*/
					//report0.setFname("");
					//report0.setOutfee(new Double(0));	
					
					report0.setTdds(new Double(0));
					report0.setDtds(new Double(0));
					data.add(report0);
					}
				else{
					Report re = data.get(count);
					//re.setDiscount(new Double(0));
					re.setPname("维修费");
					re.setNumber(new Integer(1));
					re.setPrice(new Double(0));
					re.setSales(Double.valueOf(rs4.getString("temp02")));
					while(rs5.next()){
						re.setDiscount(Double.valueOf(rs5.getString("ressum")));
					}
					/*if(rs4.getString("coin") == null)
						re.setTdds(new Double(0));
					else re.setTdds(Double.valueOf(rs4.getString("coin")));*/
					re.setTdds(new Double(0));
					re.setDtds(new Double(0));
				}
				count ++;
			}
			rs4.close();

			
			
/*			int i=1;
			while(i>0){ 
					Report report0 = new Report();
					report0.setDiscount(new Double(0));
					report0.setPname("+++++");
					report0.setNumber(0);
					report0.setPrice(0.0);
					report0.setSales(0.0);
					report0.setDiscount(777.0);
					report0.setDtds(1.0);
					report0.setTdds(1.0);
					
					data.add(report0);
								
				i--;
			}*/
		
			pstmt.close();
			pstmt2.close();
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
           
        if ("pname".equals(fieldName))   
        {   
            value = data.get(index).getPname();   
        }   
        else if ("number".equals(fieldName))   
        {   
            value = data.get(index).getNumber();   
        }   
        else if ("price".equals(fieldName))   
        {   
            value = data.get(index).getPrice();   
        }   
        else if ("sales".equals(fieldName))   
        {   
            value = data.get(index).getSales();   
        }   
        else if ("discount".equals(fieldName))   
        {   
            value = data.get(index).getDiscount(); 
        }   
        else if ("pmoney".equals(fieldName))   
        {   
            value = data.get(index).getPmoney();   
        }
        else if ("fname".equals(fieldName))   
        {   
            value = data.get(index).getFname();
        }   
        else if ("outfee".equals(fieldName))   
        {   
            value = data.get(index).getOutfee();   
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
