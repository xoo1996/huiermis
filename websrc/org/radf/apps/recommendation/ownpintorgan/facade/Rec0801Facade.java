/**
 * Rec0801Facade.java 2008/03/27
 * 
 * Copyright (c) 2008 - 2017
 * All rights reserved.<br>
 * @author lwd
 * @version 1.0
 */
package org.radf.apps.recommendation.ownpintorgan.facade;

import org.radf.plat.sieaf.envelop.RequestEnvelop;
import org.radf.plat.sieaf.envelop.ResponseEnvelop;

/**
 * 民办职业介绍机构年检
 */
public interface Rec0801Facade {

	/**
	 * 查询机构信息，初始化增加页面
	 * 
	 * @author Y.J
	 * @date 2008-3-19 18:38:12
	 * @param request
	 * @return
	 */
	public ResponseEnvelop initaddorganyc(RequestEnvelop request);

	/**
	 * 保存机构年检
	 * 
	 * @author Y.J
	 * @date 2008-3-19 18:38:12
	 * @param request
	 * @return
	 */
	public ResponseEnvelop save(RequestEnvelop request);

	/**
	 * 所有的查询
	 * 
	 * @author Y.J
	 * @date 2008-3-19 18:38:12
	 * @param request
	 * @return
	 */
	public ResponseEnvelop search(RequestEnvelop request);

	/**
	 * 修改机构年检
	 * 
	 * @author Y.J
	 * @date 2008-3-19 18:39:12
	 * @param request
	 * @return
	 */
	public ResponseEnvelop modifyCommon(RequestEnvelop request);

	/**
	 * 删除机构年检
	 * 
	 * @author Y.J
	 * @date 2008-3-19 13:22:15
	 * @param request
	 * @return
	 */
	public ResponseEnvelop delete(RequestEnvelop request);
}
