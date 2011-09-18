package feedthistothat;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class LinkEntry extends FeedEntry {

	public String URL;
	public List<String> Tags = new Vector<String>();
	public String Title;
	public String Description;
	public Calendar PostedDate;
}
