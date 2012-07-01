package feedthistothat;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.persistence.Transient;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedInstruction;
import feedthistothat.DataTypes.FeedParameters;
import feedthistothat.Readers.BaseReader;

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
			Vector<BaseReader> readers =DataAccessObject.ReadReaders(temp); 
			setReaders(readers);
			feedInstructions = DataAccessObject.getFeedInstructions(user.getEmail());
		} else {
			loginURL = userService.createLoginURL("/");
		}
		if (feedInstructions == null) {
			try {
				feedInstructions = new Vector<FeedInstruction>();
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
	@Transient private Vector<FeedInstruction> feedInstructions;
	@Transient private Vector<BaseReader> readers;
	@Transient private ConcurrentHashMap<Long, FeedParameters> feedList;

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public String getUserName() {
		return userName;
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

	public Collection<FeedParameters> getFeedList(){
		return feedList.values();
	}

	public void setReaders(Vector<BaseReader> readers) {
		this.readers = readers;
	}

	public Vector<BaseReader> getReaders() {
		return readers;
	}
}
