/**
 * CIFacade.java 2009/03/7
 * 
 * Copyright (c) 2009 ��Ŀ: ITSM
 * All rights reserved.<br>
 * @author Tony
 * @version 1.0
 */
package org.radf.apps.business.facade;

import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;

/**
 * �������
 */
public interface BusinessFacade {

	/**
	 * �½���Ϣ���
	 */
	public ResponseEnvelop modify(RequestEnvelop request);
	/**
	 * �����½���Ϣ����,�ŵ��±���ҳ��ʹ��
	 * @param request
	 * @return
	 */
	public ResponseEnvelop modifyOnly(RequestEnvelop request);
	
	/**
	 * ¼��ͻ�������Ϣ
	 * @param request
	 * @return
	 */
	public ResponseEnvelop insert(RequestEnvelop request);
	/**
	 * ���ҿͻ�������Ϣ
	 */
	public ResponseEnvelop query(RequestEnvelop request);
	public ResponseEnvelop query1(RequestEnvelop request);
	
	/**
	 * ��泬�ڲ�ѯ
	 */
	public ResponseEnvelop stoexpquery(RequestEnvelop request);
	
	/**
	 * �����޸Ŀͻ�������Ϣ
	 */
	
	public ResponseEnvelop update(RequestEnvelop request);

	/**
	 * �޸ı�����Ϣ
	 */
	public ResponseEnvelop find(RequestEnvelop request);
	/**
	 * �½Ჹ����Ϣ
	 * @param request
	 * @return
	 */
	public ResponseEnvelop save(RequestEnvelop request);
	
	/**
	 * ����̶��ʲ���Ϣ
	 */
	public ResponseEnvelop saveAsset(RequestEnvelop request);
	
	/**
	 * ��ʾ�̶��ʲ��޸�ҳ��
	 */
	public ResponseEnvelop showAsset(RequestEnvelop request);
	
	/**
	 * �����޸ĺ�Ĺ̶��ʲ���Ϣ
	 */
	public ResponseEnvelop modifyAsset(RequestEnvelop request);
	
	/**
	 * ɾ���̶��ʲ���Ϣ
	 */
	public ResponseEnvelop deleteAsset(RequestEnvelop request);
	
	
	/**
	 * ����̯��������Ϣ
	 */
	public ResponseEnvelop saveAmortize(RequestEnvelop request);
	
	
	/**
	 * ��ʾ�޸�̯��������Ϣҳ��
	 */
	public ResponseEnvelop showAmortize(RequestEnvelop request);
	
	/**
	 * �����޸ĺ��̯����Ϣ
	 */
	public ResponseEnvelop modifyAmortize(RequestEnvelop request);
	
	/**
	 * ɾ��̯��������Ϣ
	 */
	public ResponseEnvelop deleteAmortize(RequestEnvelop request);
	
	/**
	 * �����¶Ƚ��˱�ע
	 */
	public ResponseEnvelop savent(RequestEnvelop request);
	
	public ResponseEnvelop edit(RequestEnvelop request);
	
	public ResponseEnvelop querysale(RequestEnvelop request);
	
	public ResponseEnvelop modifydis(RequestEnvelop request);
	
	public ResponseEnvelop lastMonth(RequestEnvelop request);
	
	/**
	 * �¶Ƚ���
	 */
	public ResponseEnvelop account(RequestEnvelop request);
	
	/**
	 * ��ʾ�޸Ŀ������ҳ��
	 */
	public ResponseEnvelop print(RequestEnvelop request);
	
	
	/**
	 * �����޸ĵĿ������
	 */
	public ResponseEnvelop saveExd(RequestEnvelop request);
	
	/**
	 * ��ѯ�Ǽ��˵껹��ֱ����
	
	 */
	public ResponseEnvelop insertAmortize(RequestEnvelop requestEnvelop);
	
	/**
	 * ��ѯ�Ǽ��˵껹��ֱ����
	
	 */
	public String queryStoreType(String Gctnm);

	/**
	 * ˫���ʼ���
	 * @param requestEnvelop
	 * @return
	 */
	public ResponseEnvelop earCompute(RequestEnvelop requestEnvelop);

/**
 * ���³��ڿ�����״̬
 * @param requestEnvelop
 * @return
 */
	public ResponseEnvelop gctstate(RequestEnvelop requestEnvelop);

	/**
	 * �����ŵ�������������
	 * @param requestEnvelop
	 * @return
	 */
	public ResponseEnvelop saveWeb(RequestEnvelop requestEnvelop);

	public ResponseEnvelop commit(RequestEnvelop requestEnvelop);

	public ResponseEnvelop printWeb(RequestEnvelop requestEnvelop);
	
	public ResponseEnvelop modifyWeb(RequestEnvelop requestEnvelop);
	
	/**
	 * ¼��ֹ�˾�¶����
	 * @param request
	 * @return
	 */
	public ResponseEnvelop insertReport(RequestEnvelop request);
	/**
	 * ���ҷֹ�˾��Ϣ
	 */
	public ResponseEnvelop queryReport(RequestEnvelop request);
	
	/**
	 * ���·ֹ�˾�����Ϣ
	 */
	public ResponseEnvelop updateReport(RequestEnvelop request);
	
	public ResponseEnvelop save1(RequestEnvelop request);
	
	public ResponseEnvelop submit(RequestEnvelop request);
	
	public ResponseEnvelop queryMonthReport(RequestEnvelop request);
	
	public ResponseEnvelop grant(RequestEnvelop request);
}
