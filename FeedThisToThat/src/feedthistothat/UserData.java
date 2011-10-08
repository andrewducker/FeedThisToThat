package feedthistothat;

import com.google.appengine.api.users.*;



public class UserData {

	public UserData(){
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		
		if (user != null) {
			userName = user.getNickname();
			logoutURL = userService.createLogoutURL("/");
			loggedIn = true;
		} else {
			loginURL = userService.createLoginURL("/");
		}
	}
	
	private String userName;
	private Boolean loggedIn;
	private String loginURL;
	private String logoutURL;

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public String getUserName() {
		return userName;
	}

	public String getLogoutURL() {
		return logoutURL;
	}
	
}
