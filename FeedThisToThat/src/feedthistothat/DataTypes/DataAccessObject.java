package feedthistothat.DataTypes;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class DataAccessObject {
	static{
		ObjectifyService.register(FeedParameters.class);
		objectify = ObjectifyService.begin();
	}
	static Objectify objectify; 
	static public FeedParameters ReadFeedParameters(String email){
		return  objectify.query(FeedParameters.class).filter("emailAddress", email).get();
	}
	static public void updateFeedParameters(FeedParameters feedParameters){
		FeedParameters existingFeedParameter = objectify.query(FeedParameters.class).filter("emailAddress", feedParameters.getEmailAddress()).get();
		if (existingFeedParameter != null) {
			feedParameters.setId(existingFeedParameter.getId());
		}
		objectify.put(feedParameters);
	}
}
