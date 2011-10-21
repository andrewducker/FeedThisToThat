package feedthistothat.DataTypes;

import java.util.List;
import java.util.Vector;

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
		FeedParameters existingFeedParameter = objectify.query(FeedParameters.class).filter("emailAddress", feedParameters.getEmailAddress()).get();
		if (existingFeedParameter != null) {
			feedParameters.setId(existingFeedParameter.getId());
		}
		objectify.put(feedParameters);
	}
	public static List<FeedParameters> getFeedsForUpdate() {
		Vector<FeedParameters> outputList = new Vector<FeedParameters>();
		Query<FeedParameters> query = objectify.query(FeedParameters.class).filter("inPostingQueue", true);//.filter("postingTime <", new Date());
		for (FeedParameters feedParameters : query) {
			outputList.add(feedParameters);
		}
		return outputList;
	}
}
