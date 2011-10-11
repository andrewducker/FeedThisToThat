package feedthistothat.DataTypes;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class LinkEntry extends FeedEntry implements Comparable<LinkEntry> {

	public String URL;
	public List<String> Tags = new Vector<String>();
	public String Title;
	public String Description;
	public Calendar PostedDate;
	@Override
	public int compareTo(LinkEntry arg0) {
		return arg0.PostedDate.compareTo(PostedDate);
		
	}
}
