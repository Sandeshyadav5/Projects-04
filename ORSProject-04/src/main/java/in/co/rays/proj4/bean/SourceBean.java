package in.co.rays.proj4.bean;

public class SourceBean extends BaseBean{
	private String sourceCode;
	private String sourceName;
	private String connectionType;
	private String status;
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		
		return sourceCode;
	}
	@Override
	public String getValue() {
		return sourceCode;
	}
	

}
