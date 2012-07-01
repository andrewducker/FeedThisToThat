package feedthistothat.Readers;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class DeliciousReader extends BaseRSSReader {

	@SuppressWarnings("unused")
	private DeliciousReader(){}
	
	String userName;
	public DeliciousReader(String userName){
		splitTags = false;
		this.userName = userName;
	}
	
	@Override
	protected String GetURLForTag(String tag){
		return "http://delicious.com/"+userName+"/"+tag;
	}

	@Override
	String getFeedURL() {
		return "http://feeds.delicious.com/v2/rss/"+userName+"?count=30";
	}
}
