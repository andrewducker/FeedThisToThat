package feedthistothat.Readers;


public class DeliciousReader extends RSSReader implements ILinkSourceReader {

	String userName;
	public DeliciousReader(String userName){
		super("http://feeds.delicious.com/v2/rss/"+userName+"?count=30");
		splitTags = false;
		this.userName = userName;
	}
	
	@Override
	protected String GetURLForTag(String tag){
		return "http://delicious.com/"+userName+"/"+tag;
	}

}
