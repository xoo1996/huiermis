/**
 * Rec0803DTO.java 2008/03/27
 * 
 * Copyright (c) 2008 - 2017
 * All rights reserved.<br>
 * @author lwd
 * @version 1.0
 */
package org.radf.apps.recommendation.ownpintorgan.dto;

import org.radf.plat.util.entity.EntitySupport;

/**
 * 民办职业介绍机构日常投诉
 */
public class Rec0803DTO extends EntitySupport {

	private String acb240;// 机构编号

	private String acb260;// 投诉编号

	private String acb241;// 机构名称

	private String aab019;// 企业性质

	public String getAab019() {
		return aab019;
	}

	public void setAab019(String aab019) {
		this.aab019 = aab019;
	}

	public String getAcb240() {
		return acb240;
	}

	public void setAcb240(String acb240) {
		this.acb240 = acb240;
	}

	public String getAcb241() {
		return acb241;
	}

	public void setAcb241(String acb241) {
		this.acb241 = acb241;
	}

	public String getAcb260() {
		return acb260;
	}

	public void setAcb260(String acb260) {
		this.acb260 = acb260;
	}

}
