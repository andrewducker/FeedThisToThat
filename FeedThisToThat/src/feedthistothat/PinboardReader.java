package feedthistothat;

public class PinboardReader extends RSSReader implements ILinkSourceReader {

	public PinboardReader(String userID) {
		super("http://feeds.pinboard.in/rss/u:"+userID);
		splitTags = true;
	}

}
