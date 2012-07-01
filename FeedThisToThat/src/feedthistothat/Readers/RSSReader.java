package feedthistothat.Readers;

public class RSSReader extends BaseRSSReader{

	@SuppressWarnings("unused")
	private RSSReader(){}
	
	protected String feedURL;
	
	public RSSReader(String feedURL)
	{
		this.feedURL = feedURL;
	}

	@Override
	String getFeedURL() {
		return feedURL;
	}

}
