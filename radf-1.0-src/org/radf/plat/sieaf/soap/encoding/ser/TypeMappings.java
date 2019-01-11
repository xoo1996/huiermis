/* Generated by Together */

package org.radf.plat.sieaf.soap.encoding.ser;

/**
 * <p>Description:����ӳ�������</p>
 * <p>Copyright: Copyright (c) 2002 by LBS Co., Ltd.</p>
 * <p>Company: LBS</p>
 * @author chenshuichao
 * @version 1.0
*/

import java.util.HashMap;
import java.util.Vector;

import org.radf.plat.sieaf.event.EventResponse;

public class TypeMappings {
	private HashMap maps;
    /**
	 * ��ñ�����.
	 * @param value
	 * @return Serializer
	 */
	public Serializer getSerializer(Object value) {
		String key=null;
		if(value instanceof EventResponse)
			key="Event";
		if(value instanceof HashMap)
			key="HashMap";
		if(value instanceof String)
			key="String";
		if(value instanceof Vector)
			key="Vector";
        if(value == null){
            value = "";
            key = "String";
        }
        if(key==null){
        	System.out.println("===========*********==========responseEvent�а����˲�֧�ֵ����ͣ���ǰ��������["+value.getClass().getName()+"]");
        }
		return (Serializer)maps.get(key);
    }
    /**
	 * ��ʼ��.
	 */
	public void init()
    {
    	//creator all Serializer
    	maps=new HashMap();
    	maps.put("Event",new EventSerializer());
    	maps.put("HashMap",new HashMapSerializer());
    	maps.put("String",new StringSerializer());
    	maps.put("Vector",new VectorSerializer());
    }
}