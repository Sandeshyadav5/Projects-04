package in.co.rays.proj4.bean;

import java.util.Date;

public class DisasterBean extends BaseBean{
	private String alertType;
	private String location;
	private String severty;
	private Date alertTime;
	
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSeverty() {
		return severty;
	}
	public void setSeverty(String severty) {
		this.severty = severty;
	}
	public Date getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return alertType;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return alertType;
	}

}
