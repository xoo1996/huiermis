/* Generated by Together */

package org.radf.plat.sieaf.soap.encoding.ser;


/**<p>Description:soap��Ϣ�ŷ�</p>
 * <p>Copyright: Copyright (c) 2002 by LBS Co., Ltd.</p>
 * <p>Company: LBS</p>
 * @author chenshuichao
 * @version 1.0
*/

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

import org.radf.plat.sieaf.event.EventResponse;

public class EventSerializer implements Serializer {

	private static final String END_TAG = "</out:business>";
	private static final String START_TAG =
		"<out:business xmlns:out=\"http://www.molss.gov.cn/\">";
	private static final String STRING_END_TAG = "/>";
	private static final String STRING_START_TAG = "<result ";

	/**
	 * @see org.radf.plat.soap.encoding.Serializer#serialize(java.lang.StringBuffer, java.lang.Object, org.radf.plat.soap.encoding.SerializationContext)
	 */
	public void serialize(
		String Qname,
		StringBuffer out,
		Object value,
		SerializationContext ctx) {

        TypeMappings typeMap = new TypeMappings();
		Serializer serializer = null;
		EventResponse event = (EventResponse) value;
		HashMap map = event.getBody();
		//startElement();
		out.append(START_TAG);
		for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
			Entry entry = (Entry) i.next();
			String key = (String) entry.getKey();
			Object val = entry.getValue();
			if (val instanceof String) {
				out.append(STRING_START_TAG);
				ctx.serialize(key, out, val);
				out.append(STRING_END_TAG);
			}
			if (val instanceof Vector) {

				ctx.serialize(key, out, val);

			}

            if(val==null) {
                                out.append(STRING_START_TAG);
                                ctx.serialize(key, out, val);
                out.append(STRING_END_TAG);
                        }

		}

		//endElement();
		out.append(END_TAG);

	}


}
