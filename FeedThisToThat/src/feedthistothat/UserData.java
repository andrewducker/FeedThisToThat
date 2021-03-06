package feedthistothat;

import java.util.Vector;
import java.util.logging.Logger;

import javax.persistence.Transient;

import com.google.appengine.api.users.*;
import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;

public class UserData {

	public UserData(){
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		
		if (user != null) {
			Logger log = Logger.getLogger(this.getClass().getName());
			log.info("Logged in " + user.getEmail());
			userName = user.getNickname();
			logoutURL = userService.createLogoutURL("/");
			loggedIn = true;
			isAdmin = userService.isUserAdmin();
			String temp = user.getEmail();
			feedParameters = DataAccessObject.ReadFeedParameters(temp);
			feeds = DataAccessObject.ReadFeedList(temp);
		} else {
			loginURL = userService.createLoginURL("/");
		}
		if (feedParameters == null) {
			try {
				feedParameters = new FeedParameters();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String userName;
	private Boolean loggedIn = false;
	private String loginURL;
	private String logoutURL;
	private boolean isAdmin = false;
	@Transient private FeedParameters feedParameters;
	@Transient private Vector<Long> feeds;

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
	
	public FeedParameters getFeedParameters(String feedID){
		return DataAccessObject.GetFeedParameters(Long.parseLong(feedID));
	}
	public String getLogoutURL() {
		return logoutURL;
	}
	
	public boolean getIsAdmin(){
		return isAdmin;
	}

	public Vector<Long> getFeeds(){
		return feeds;
	}
	
}
