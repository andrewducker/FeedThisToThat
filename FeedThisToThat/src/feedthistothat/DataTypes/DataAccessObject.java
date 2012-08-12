package feedthistothat.DataTypes;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import feedthistothat.Readers.BaseReader;
import feedthistothat.Readers.DeliciousReader;
import feedthistothat.Readers.PinboardReader;
import feedthistothat.Readers.RSSReader;
import feedthistothat.Writers.DWWriter;
import feedthistothat.Writers.LJWriter;
import feedthistothat.Writers.MetaWeblogAPIWriter;
import feedthistothat.Writers.ResponseWriter;

public class DataAccessObject {
	static{
		ObjectifyService.register(FeedInstruction.class);
		ObjectifyService.register(DeliciousReader.class);
		ObjectifyService.register(PinboardReader.class);
		ObjectifyService.register(RSSReader.class);
		ObjectifyService.register(BaseReader.class);
		ObjectifyService.register(BaseSaveable.class);
		ObjectifyService.register(LJWriter.class);
		ObjectifyService.register(DWWriter.class);
		ObjectifyService.register(MetaWeblogAPIWriter.class);
		ObjectifyService.register(ResponseWriter.class);
		objectify = ObjectifyService.begin();
	}
	static Objectify objectify; 
	static public ConcurrentHashMap<Long, FeedParameters> readFeedParameters(String email){
		Query<FeedParameters> query = objectify.query(FeedParameters.class).filter("emailAddress", email);
		ConcurrentHashMap<Long, FeedParameters> feedsToReturn = new ConcurrentHashMap<Long, FeedParameters>();
		for (FeedParameters feedParameters : query) {
			feedsToReturn.put(feedParameters.getId(), feedParameters);
		}
		return  feedsToReturn;
	}
	
	static public void updateFeedParameters(FeedParameters feedParameters){
		if (feedParameters == null || feedParameters.getEmailAddress() == null || feedParameters.getEmailAddress().trim()=="") {
			return;
		}
		FeedParameters existingFeedParameter = objectify.query(FeedParameters.class).filter("emailAddress", feedParameters.getEmailAddress()).get();
		if (existingFeedParameter != null) {
			feedParameters.setId(existingFeedParameter.getId());
		}
		objectify.put(feedParameters);
	}
	
	static public Key<BaseSaveable> updateSaveable(BaseSaveable saveable){
		if (saveable == null || saveable.getEmailAddress() == null || saveable.getEmailAddress().trim()=="") {
			return null;
		}

		Key<BaseSaveable> myKey = objectify.put(saveable);
		return myKey;
	}
	
	static public Saveables loadSaveables(String email){
		Saveables outputList = new Saveables();
		Query<BaseSaveable> query = objectify.query(BaseSaveable.class).filter("emailAddress", email);
		
		for (BaseSaveable feedKey : query) {
			outputList.add(feedKey);
		}
		return outputList;
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
	
	public static Vector<Long> ReadFeedList(String email) {
		Vector<Long> outputList = new Vector<Long>();
		Query<FeedParameters> query = objectify.query(FeedParameters.class).filter("emailAddress", email);
		for (Key<FeedParameters> feedKey : query.fetchKeys()) {
			outputList.add(feedKey.getId());
		}
		return outputList;
	}

	public static Vector<FeedInstruction> getFeedInstructions(String email) {
		Query<FeedInstruction> query = objectify.query(FeedInstruction.class).filter("emailAddress", email);
		Vector<FeedInstruction> feedsToReturn = new Vector<FeedInstruction>();
		for (FeedInstruction feedInstruction : query) {
			feedsToReturn.add(feedInstruction);
		}
		return  feedsToReturn;
	}

	public static Vector<BaseReader> ReadReaders(String email) {
		Query<BaseReader> query = objectify.query(BaseReader.class).filter("emailAddress", email);
		Vector<BaseReader> readers = new Vector<BaseReader>();
		for (BaseReader reader : query) {
			readers.add(reader);
		}
		return  readers;
	}
}
