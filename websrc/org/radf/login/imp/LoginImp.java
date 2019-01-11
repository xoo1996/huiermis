package org.radf.login.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.radf.apps.commons.entity.ProClass;
import org.radf.apps.commons.entity.Product;
import org.radf.login.dto.LoginDTO;
import org.radf.login.facade.LoginFacade;
import org.radf.manage.entity.Sc01;
import org.radf.manage.entity.Sc03;
import org.radf.manage.entity.Sc04;
import org.radf.manage.entity.Sc07;
import org.radf.manage.entity.Sc08;
import org.radf.manage.entity.Sc10;
import org.radf.plat.commons.ClassHelper;
import org.radf.plat.commons.DBUtil;
import org.radf.plat.commons.DateUtil;
import org.radf.plat.commons.HMAC_MD5;
import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;
import org.radf.plat.util.exception.AppException;
import org.radf.plat.util.global.GlobalErrorCode;
import org.radf.plat.util.global.GlobalNames;

public class LoginImp extends org.radf.plat.util.imp.IMPSupport implements
		LoginFacade {

	private String className = this.getClass().getName();

	/**
	 * ���ܣ��û���½���û�������֤��
	 * 
	 * @param request
	 * @return
	 */
	public ResponseEnvelop userLogin(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop();
		HashMap map = (HashMap) request.getBody();
		LoginDTO dto = (LoginDTO) map.get("dto");
		String bsc011 = dto.getBsc011();
		String aae101 = dto.getAae101();
		Connection con = null;
		ResultSet result = null;
		boolean flag;
		try {
			con = DBUtil.getConnection();
			dto.setFileKey("login_select_sc05");
			// ��ȡ��Ա��Ϣ
			ArrayList list = (ArrayList) find(con, dto, null, 0);
			if (list != null && list.size() > 0) {
				ClassHelper.copyProperties(list.get(0), dto);
				if (dto.getAae100() == null || dto.getAae100().equals("")
						|| dto.getAae100().equals("0"))
					throw new AppException("|��½ʧ�ܣ����û��Ѿ�ע��");
			} else {
				throw new AppException("|��½ʧ�ܣ��޴��û����û��������");
			}
			
			String sql = "select * from sc05 where bsc011='"+bsc011+"' and aae101='1'";
			PreparedStatement pst = con.prepareStatement(sql);
			result = pst.executeQuery();
			if(result.next()==false){
				//kong
				flag = false;
			}else{
				flag = true;
			}
			
			/*if (flag){
				// ��֤ukey���к���Ч��
				dto.setFileKey("ukey_findsn");
				ArrayList listKey = (ArrayList) find(con, dto, null, 0);
				if (listKey != null && listKey.size() > 0) {
				} else {
					throw new AppException("|��½ʧ�ܣ��������ȷ��ukey");
				}
				// ��֤��������Ƿ����
				String key = bsc011;
				String Randata = dto.getRand();
				String ClientDigest = dto.getClientResult();
				HMAC_MD5 hm = new HMAC_MD5(key.getBytes());// �������KEY������
				hm.addData(Randata.getBytes());// ���������!!!

				byte digest[];
				digest = hm.sign();
				String ss = hm.toString();

				if (ClientDigest.equals(ss)) {
					System.out.println("ok,equal!");
				} else {
					throw new AppException("|��½ʧ�ܣ�Ukey��֤ʧ�ܣ��������ȷ��ukey");
				}
			}*/
			// ��ȡ��Ա������Ϣ
			Sc01 sc01 = new Sc01();
			sc01.setBsc001(dto.getBsc001());
			sc01.setFileKey("login_select_sc06");
			ArrayList list1 = (ArrayList) find(con, sc01, null, 0);
			if (list1 != null && list1.size() > 0) {
				ClassHelper.copyProperties(list1.get(0), dto);
			}
			// ��ȡ������Ϣ
			Sc04 sc04 = new Sc04();
			sc04.setBsc008(dto.getBsc008());
			sc04.setFileKey("SC04_select");
			ArrayList list2 = (ArrayList) find(con, sc04, null, 0);
			if (list2 != null && list2.size() > 0) {
				ClassHelper.copyProperties(list2.get(0), dto);
			}
			ArrayList list5 = new ArrayList();
			// ����ǳ����û���ȡ���в˵�
			if (GlobalNames.SUPER_USER.equals(dto.getBsc011())) {
				Sc08 sc08 = new Sc08();
				sc08.setFileKey("login_select_Sc0801");
				ArrayList list4 = (ArrayList) find(con, sc08, null, 0);
				if (list4 != null && list4.size() > 0) {
					for (int j = 0; j < list4.size(); j++) {
						Sc08 sc081 = new Sc08();
						ClassHelper.copyProperties(list4.get(j), sc081);
						list5.add(sc081);
					}

				}
			} else {
				// ��ȡ��ɫ�б�
				Sc07 sc07 = new Sc07();
				sc07.setBsc010(dto.getBsc010());
				sc07.setFileKey("login_select_sc06andsc07");
				ArrayList list3 = (ArrayList) find(con, sc07, null, 0);
				if (list3 != null && list3.size() > 0) {

					StringBuffer stringbuffer = new StringBuffer(
							" select distinct sc08.bsc016, sc08.* from sc08,sc09 where sc08.bsc016=sc09.bsc016 and sc09.bsc014 in  (");

					for (int i = 0; i < list3.size(); i++) {
						ClassHelper.copyProperties(list3.get(i), sc07);
						stringbuffer.append("'" + sc07.getBsc014() + "'");
						if (list3.size() == (i + 1)) {
							stringbuffer
									.append(") order by  sc08.bsc020,sc08.bsc016");
						} else {
							stringbuffer.append(",");
						}

					}

					// ArrayList list4 = (ArrayList) DBUtil.querySQL(con, hql,
					// "2");
					ArrayList list4 = (ArrayList) DBUtil.querySQL(con,
							stringbuffer.toString(), "2");
					if (list4 != null && list4.size() > 0) {
						for (int j = 0; j < list4.size(); j++) {
							Sc08 sc08 = new Sc08();
							ClassHelper.copyProperties(list4.get(j), sc08);
							list5.add(sc08);
						}

					}
				}

			}
			Sc03 sc03 = new Sc03();
			Vector vecsc03 = new Vector();
			sc03.setFileKey("sys01_016");
			sc03.setAae017(dto.getBsc001());
			ArrayList listsc03 = (ArrayList) find(con, sc03, null, 0);
			if (listsc03 != null && listsc03.size() > 0) {
				for (int j = 0; j < listsc03.size(); j++) {
					sc03 = new Sc03();
					ClassHelper.copyProperties(listsc03.get(j), sc03);
					vecsc03.add(sc03);
				}

			}
			dto.setFileKey("pdt02_000");// ������Ʒ����
			Object products = find(con, dto, null, 0);

			// Product nomPdt=new Product();
			// nomPdt.setFileKey("pdt02_002");
			// Object nomProducts = find(con,nomPdt, null, 0);
			//
			// Product cusPdt=new Product();
			// nomPdt.setFileKey("pdt02_003");
			// Object cusProducts = find(con,cusPdt, null, 0);

			// ��Ʒ���
			// ProClass pcl=new ProClass();
			// pcl.setFileKey("pdt06_000");
			// Object classes = find(con, pcl, null, 0);

			// ���ؽ��
			HashMap<String, Object> retmap = new HashMap<String, Object>();
			Object grCli = null;

			if (dto.getBsc001().equals("1501000000")) {

				dto.setFileKey("clt04_001");// �������������
				Object shops = find(con, dto, null, 0);

				dto.setFileKey("clt04_002");
				grCli = find(con, dto, null, 0);

				dto.setFileKey("clt04_000");// ���в���Ա����
				Object users = find(con, dto, null, 0);

				dto.setFileKey("rep04_001");// ά�޴�ʩһ
				Object action1 = find(con, dto, null, 0);
				dto.setFileKey("rep04_002");// ά�޴�ʩ��
				Object action2 = find(con, dto, null, 0);
				dto.setFileKey("rep04_003");// ά�޴�ʩ��
				Object action3 = find(con, dto, null, 0);
				dto.setFileKey("rep04_004");// ά�޴�ʩ��
				Object action4 = find(con, dto, null, 0);
				dto.setFileKey("rep04_005");// ά�޴�ʩ��
				Object action5 = find(con, dto, null, 0);
				dto.setFileKey("rep04_006");// ά�޴�ʩ��
				Object action6 = find(con, dto, null, 0);

				dto.setFileKey("ord01_003");// ������ʽ
				Object folway = find(con, dto, null, 0);

				// dto.setFileKey("tsk01_000");//������|2012-4-14����,tsk01_000��TZ_CUS_SQL.properties
				// Object panels = find(con, dto, null, 0);

				// dto.setFileKey("pdt05_002");//�������|2012-4-17����,pdt05_002��TZ_PDT_SQL.properties
				// Object parts = find(con, dto, null, 0);

				// dto.setFileKey("pdt05_006");
				// Object pnlnm = find(con, dto, null, 0);//����ͺ�

				retmap.put("shops", shops);
				retmap.put("users", users);

				retmap.put("action1", action1);
				retmap.put("action2", action2);
				retmap.put("action3", action3);
				retmap.put("action4", action4);
				retmap.put("action5", action5);
				retmap.put("action6", action6);
				retmap.put("folway", folway);

				// retmap.put("panels", panels);
				// retmap.put("parts", parts);

				// retmap.put("pnlnm", pnlnm);
			} else {
				dto.setFileKey("clt04_001");// �������������
				Object shops = find(con, dto, null, 0);
				dto.setBsc011(bsc011);
				dto.setFileKey("clt04_003");
				grCli = find(con, dto, null, 0);
				retmap.put("shops", shops);
			}

			retmap.put("dto", dto);
			retmap.put("list", list5);
			retmap.put("vecsc03", vecsc03);
			retmap.put("grCli", grCli);
			retmap.put("products", products);
			// retmap.put("nomProducts", nomProducts);
			// retmap.put("cusProducts", cusProducts);
			// retmap.put("classes", classes);

			response.setBody(retmap);
			Sc10 sc10 = (Sc10) map.get("sc10");
			// ��½�ɹ�д��½��־
			sc10.setBsc025(DBUtil.getSequence(con, "SEQ_BSC025"));
			sc10.setBsc010(dto.getBsc010());
			sc10.setBsc011(dto.getBsc011());
			sc10.setBsc012(dto.getBsc012());
			sc10.setBsc027(DateUtil.getSystemCurrentTime());
			create(con, sc10, null, 0);
			DBUtil.commit(con);
			// ��ȡ
		} catch (AppException ae) {
			response.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "userLogin",
					GlobalErrorCode.IMPEXCEPTIONCODE, ex.getMessage(),
					request.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;

	}

	/**
	 * ��¼ע��
	 * 
	 * @param request
	 * @return
	 */
	public ResponseEnvelop logout(RequestEnvelop request) {
		ResponseEnvelop response = new ResponseEnvelop(); // ���ڲ���
		Connection con = null;
		try {
			HashMap map = (HashMap) request.getBody();
			LoginDTO dto = (LoginDTO) map.get("dto");
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			StringBuffer stringbuffer = new StringBuffer(
					"select * from sc10 where bsc025=(select max(bsc025) from sc10 where");
			if (dto.getBsc012() != null && !dto.getBsc012().equals("")) {
				stringbuffer.append(" bsc012='" + dto.getBsc012());
				stringbuffer.append("') and bsc012='" + dto.getBsc012() + "'");
			}
			ArrayList list = (ArrayList) DBUtil.querySQL(con,
					stringbuffer.toString(), "2");
			if (list != null && list.size() > 0) {
				Sc10 sc10 = new Sc10();
				ClassHelper.copyProperties(list.get(0), sc10);
				sc10.setBsc028(DateUtil.getSystemCurrentTime());
				modify(con, sc10, null, 0);
			}
			DBUtil.commit(con);
		} catch (AppException ae) {
			response.setHead(ExceptionSupport(className, ae, request.getHead()));
		} catch (Exception ex) {
			response.setHead(ExceptionSupport(className, "userLogin",
					GlobalErrorCode.IMPEXCEPTIONCODE, ex.getMessage(),
					request.getHead()));
		} finally {
			DBUtil.rollback(con);
			DBUtil.closeConnection(con);
		}
		return response;

	}
}
