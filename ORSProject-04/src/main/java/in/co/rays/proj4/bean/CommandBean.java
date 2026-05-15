package in.co.rays.proj4.bean;

import java.util.Date;

public class CommandBean extends BaseBean {
	private String commandTest;
	private String deviceTarget;
	private Date executionTime;
	private String status;
	public String getCommandTest() {
		return commandTest;
	}
	public void setCommandTest(String commandTest) {
		this.commandTest = commandTest;
	}
	public String getDeviceTarget() {
		return deviceTarget;
	}
	public void setDeviceTarget(String deviceTarget) {
		this.deviceTarget = deviceTarget;
	}
	public Date getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return commandTest;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return commandTest;
	}

}
