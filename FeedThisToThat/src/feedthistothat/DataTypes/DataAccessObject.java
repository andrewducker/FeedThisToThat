package feedthistothat.DataTypes;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

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
		if (feedParameters == null || feedParameters.getEmailAddress() == null) {
			return;
		}
		FeedParameters existingFeedParameter = objectify.query(FeedParameters.class).filter("emailAddress", feedParameters.getEmailAddress()).get();
		if (existingFeedParameter != null) {
			feedParameters.setId(existingFeedParameter.getId());
		}
		objectify.put(feedParameters);
	}
	public static List<Long> getFeedsForUpdate() {
		Vector<Long> outputList = new Vector<Long>();
		Query<FeedParameters> query = objectify.query(FeedParameters.class).filter("inPostingQueue", true).filter("postingTime <", new Date());
		
		for (Key<FeedParameters> feedKey : query.fetchKeys()) {
			outputList.add(feedKey.getId());
		}
		return outputList;
	}
	
	public static FeedParameters GetFeedParameters(long key){
		return objectify.get(FeedParameters.class,key);
	}
}
