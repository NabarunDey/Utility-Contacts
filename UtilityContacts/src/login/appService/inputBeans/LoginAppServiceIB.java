package login.appService.inputBeans;

public class LoginAppServiceIB {

	
	private String username;
	private String password;
	private String fbCode;
	private String googleCode;
	
	public String getGoogleCode() {
		return googleCode;
	}
	public void setGoogleCode(String googleCode) {
		this.googleCode = googleCode;
	}
	public String getFbCode() {
		return fbCode;
	}
	public void setFbCode(String fbCode) {
		this.fbCode = fbCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
