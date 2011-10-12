package feedthistothat.DataTypes;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class LinkEntry extends FeedEntry implements Comparable<LinkEntry> {

	public String URL;
	public List<LinkTag> Tags = new Vector<LinkTag>();
	public String Title;
	public String Description;
	public Calendar PostedDate;
	@Override
	public int compareTo(LinkEntry arg0) {
		return PostedDate.compareTo(arg0.PostedDate);
		
	}
}
