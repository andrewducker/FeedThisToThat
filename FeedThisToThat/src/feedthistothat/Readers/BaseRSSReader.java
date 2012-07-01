package feedthistothat.Readers;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;

import feedthistothat.DataTypes.LinkEntry;
import feedthistothat.DataTypes.LinkSet;
import feedthistothat.DataTypes.LinkTag;

import uk.org.catnip.eddie.Category;
import uk.org.catnip.eddie.Detail;
import uk.org.catnip.eddie.Entry;
import uk.org.catnip.eddie.FeedData;
import uk.org.catnip.eddie.Link;
import uk.org.catnip.eddie.parser.Parser;

@Unindexed
@Cached
@Subclass
public class BaseRSSReader extends BaseReader {
	
	String getFeedURL()
	{
		return "";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkSet Read() throws Exception {

		LinkSet links = new LinkSet();
		
		byte[] feedContents = FeedReader.Read(getFeedURL());
		
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
				if (!commentDetail.getValue().trim().equals("<p></p>") && !commentDetail.getValue().trim().equals("")) {
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
		if (links.isEmpty()) {
			throw new Exception("No links found at that address - user does not exist?");
		}
		return links;
	}

	protected Boolean splitTags;
	
	private List<LinkTag> GetTagsFromCategories(Iterator<Category> categoryIterator) {
		List<LinkTag> Tags = new Vector<LinkTag>();
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
				LinkTag linkTag = new LinkTag();
				linkTag.Tag = tag;
				linkTag.TagURL = GetURLForTag(tag);
				Tags.add(linkTag);
			} 
		}
		return Tags;
	}

	protected String GetURLForTag(String tag) {
		return null;
	}



}
