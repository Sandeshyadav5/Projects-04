package in.co.rays.proj4.bean;

import java.util.Date;

public class WarrantyBean extends BaseBean {
	
	private String productName;
	private Date startDate;
	private Date endDate;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String getKey() {
		return productName;
	}
	@Override
	public String getValue() {
		return productName;
	}
	

}
