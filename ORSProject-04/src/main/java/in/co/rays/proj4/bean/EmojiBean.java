package in.co.rays.proj4.bean;

public class EmojiBean extends BaseBean {
	
	private String reactionCode;
	private String userName;
	private String emojiType;
	private String status;
	
	
	public String getReactionCode() {
		return reactionCode;
	}
	public void setReactionCode(String reactionCode) {
		this.reactionCode = reactionCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmojiType() {
		return emojiType;
	}
	public void setEmojiType(String emojiType) {
		this.emojiType = emojiType;
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
		return reactionCode;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return reactionCode;
	}
	
	

}
