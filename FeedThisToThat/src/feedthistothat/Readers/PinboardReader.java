package feedthistothat.Readers;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class PinboardReader extends BaseRSSReader{

	@SuppressWarnings("unused")
	private PinboardReader(){}
	
	String userID;
	public PinboardReader(String userID) {
		splitTags = true;
		this.userID = userID;
	}
	
	@Override
	protected String GetURLForTag(String tag){
		return "http://pinboard.in/u:"+userID+"/t:"+tag;
	}

	@Override
	String getFeedURL() {
		return "http://feeds.pinboard.in/rss/u:"+userID;
	}
}
