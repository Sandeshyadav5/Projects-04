package in.co.rays.proj4.bean;

public class EnergyBean extends BaseBean{
	
	private String energyCode;
	private String deviceName;
	private long unitsConsumed;
	private String status;
	public String getEnergyCode() {
		return energyCode;
	}
	public void setEnergyCode(String energyCode) {
		this.energyCode = energyCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public long getUnitsConsumed() {
		return unitsConsumed;
	}
	public void setUnitsConsumed(long unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
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
		return energyCode;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return energyCode;
	}
}
