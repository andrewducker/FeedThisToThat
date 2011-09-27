package feedthistothat;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.org.catnip.eddie.Category;
import uk.org.catnip.eddie.Detail;
import uk.org.catnip.eddie.Entry;
import uk.org.catnip.eddie.FeedData;
import uk.org.catnip.eddie.Link;
import uk.org.catnip.eddie.parser.Parser;

public class RSSReader implements ILinkSourceReader {

	protected String feedURL;
	
	public RSSReader(String feedURL)
	{
		this.feedURL = feedURL;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LinkEntry> Read() throws Exception {

		Vector<LinkEntry> links = new Vector<LinkEntry>();
		
		byte[] feedContents = FeedReader.Read(feedURL);
		
		Parser parser = new Parser();
		FeedData feedData = null;
		feedData = parser.parse(feedContents);
		
		for(Iterator<Entry> entryIterator = feedData.entries();entryIterator.hasNext();)
		{
			Entry entry = entryIterator.next();

			LinkEntry linkEntry = new LinkEntry();
			linkEntry.Title = entry.getTitle().getValue();
			
			

			Detail commentDetail = entry.getSummary();
			if(commentDetail != null)
			{
				 linkEntry.Description = commentDetail.getValue();
			}
			
			linkEntry.PostedDate = Calendar.getInstance();
			linkEntry.PostedDate.setTime(entry.getModified());			

			for(Iterator<Link> linkIterator = entry.links();linkIterator.hasNext();)
			{
				 linkEntry.URL = linkIterator.next().getValue();
			}
			for(Iterator<Category> categoryIterator = entry.categories();categoryIterator.hasNext();)
			{
				String tagText = categoryIterator.next().getTerm();
				String[] tags = tagText.split(" ");
				for (String tag : tags) {
					linkEntry.Tags.add(tag);	
				} 
			}
			links.add(linkEntry);
		}
		return links;
	}
}
