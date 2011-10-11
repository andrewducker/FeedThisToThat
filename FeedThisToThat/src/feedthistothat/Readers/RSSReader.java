package feedthistothat.Readers;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import feedthistothat.DataTypes.LinkEntry;

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
				if (!commentDetail.getValue().trim().equals("<p></p>")) {
					 linkEntry.Description = commentDetail.getValue();
				}
			}
			
			linkEntry.PostedDate = Calendar.getInstance();
			linkEntry.PostedDate.setTime(entry.getModified());			

			for(Iterator<Link> linkIterator = entry.links();linkIterator.hasNext();)
			{
				 linkEntry.URL = linkIterator.next().getValue();
			}
			
			linkEntry.Tags = GetTagsFromCategories(entry.categories());
			
			links.add(linkEntry);
		}
		return links;
	}

	protected Boolean splitTags;
	
	private List<String> GetTagsFromCategories(Iterator<Category> categoryIterator) {
		List<String> Tags = new Vector<String>();
		while(categoryIterator.hasNext())
		{
			String tagText = categoryIterator.next().getTerm();
			String[] tags;
			if (splitTags) {
				tags = tagText.split(" ");
			}
			else {
				tags = new String[1];
				tags[0] = tagText;
			}
			for (String tag : tags) {
				Tags.add(FormatTag(tag));
			} 
		}
		return Tags;
	}

	protected String FormatTag(String tag) {
		return tag;
	}
}
