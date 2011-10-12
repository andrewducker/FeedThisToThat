package feedthistothat.Readers;


public class PinboardReader extends RSSReader implements ILinkSourceReader {

	String userID;
	public PinboardReader(String userID) {
		super("http://feeds.pinboard.in/rss/u:"+userID);
		splitTags = true;
		this.userID = userID;
	}
	
	@Override
	protected String GetURLForTag(String tag){
		return "http://pinboard.in/u:"+userID+"/t:"+tag;
	}

}
