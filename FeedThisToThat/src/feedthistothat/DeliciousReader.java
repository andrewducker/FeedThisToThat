package feedthistothat;

public class DeliciousReader extends RSSReader implements ILinkSourceReader {

	String userName;
	public DeliciousReader(String userName){
		super("http://feeds.delicious.com/v2/rss/"+userName+"?count=30");
		splitTags = false;
		this.userName = userName;
	}
	
	@Override
	protected String FormatTag(String tag) {
		return " <a href=\"http://delicious.com/"+userName+"/"+tag+"\">"+tag+"</a>";
	}

}
