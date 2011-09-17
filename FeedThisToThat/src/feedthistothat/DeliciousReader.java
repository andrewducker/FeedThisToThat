package feedthistothat;

public class DeliciousReader extends RSSReader implements ILinkSourceReader {

	public DeliciousReader(String userName){
		super("http://feeds.delicious.com/v2/rss/"+userName+"?count=30");
	}
}
