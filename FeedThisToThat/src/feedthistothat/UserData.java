package feedthistothat;

import javax.persistence.Transient;

import com.google.appengine.api.users.*;
import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;



public class UserData {

	public UserData(){
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		
		if (user != null) {
			userName = user.getNickname();
			logoutURL = userService.createLogoutURL("/");
			loggedIn = true;
			feedParameters = DataAccessObject.ReadFeedParameters(user.getEmail());
		} else {
			loginURL = userService.createLoginURL("/");
		}
		if (feedParameters == null) {
			feedParameters = FeedParameters.getDefault();
		}
	}
	
	private String userName;
	private Boolean loggedIn;
	private String loginURL;
	private String logoutURL;
	@Transient private FeedParameters feedParameters;

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public String getUserName() {
		return userName;
	}

	public FeedParameters getFeedParameters(){
		return feedParameters;
	}
	
	public String getLogoutURL() {
		return logoutURL;
	}
	
}
