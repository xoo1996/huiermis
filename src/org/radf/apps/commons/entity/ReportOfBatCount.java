package org.radf.apps.commons.entity;

import org.radf.plat.util.entity.EntitySupport;

public class ReportOfBatCount extends EntitySupport {
	private String storename; //门店名称
	private String storeid; //门店代码
	private String batteryname; //电池名称
	private String batteryid; //电池编号
	private Integer number; //数量
	
    public ReportOfBatCount(){
    	
    }
    
	public ReportOfBatCount(String storename, String storeid,
			String batteryname, String batteryid, Integer number) {
		super();
		this.storename = storename;
		this.storeid = storeid;
		this.batteryname = batteryname;
		this.batteryid = batteryid;
		this.number = number;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getBatteryname() {
		return batteryname;
	}

	public void setBatteryname(String batteryname) {
		this.batteryname = batteryname;
	}

	public String getBatteryid() {
		return batteryid;
	}

	public void setBatteryid(String batteryid) {
		this.batteryid = batteryid;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
