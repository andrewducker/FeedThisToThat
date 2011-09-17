package feedthistothat;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.org.catnip.eddie.Category;
import uk.org.catnip.eddie.Detail;
import uk.org.catnip.eddie.Entry;
import uk.org.catnip.eddie.FeedData;
import uk.org.catnip.eddie.Link;
import uk.org.catnip.eddie.parser.Parser;

public class ReadFromRSS {
	@SuppressWarnings("unchecked")
	public static String Read(String userName) throws IOException
	{
		String feedURL = "http://feeds.delicious.com/v2/rss/"+userName+"?count=30";
		
		byte[] feedContents = FeedReader.Read(feedURL);
		
		Parser parser = new Parser();
		FeedData feedData = null;
		feedData = parser.parse(feedContents);
		
		String output = "<ul class=\"delicious\">";
		
		for(Iterator<Entry> entryIterator = feedData.entries();entryIterator.hasNext();)
		{
			Entry entry = entryIterator.next();
			String title = entry.getTitle().getValue();
			String link = "";
			String comment = "";
			List<String> tags = new Vector<String>();
			Detail commentDetail = entry.getSummary();
			if(commentDetail != null)
			{
				 comment = commentDetail.getValue();
			}
			for(Iterator<Link> linkIterator = entry.links();linkIterator.hasNext();)
			{
				 link =linkIterator.next().getValue();
			}
			for(Iterator<Category> linkIterator = entry.categories();linkIterator.hasNext();)
			{
				 tags.add(linkIterator.next().getTerm());
			}

			output += "<li><div class=\"delicious-link\"><A href=\""+link+"\">" +title+"</A></div>";
			if(comment != "")
			{
				output += "<div class=\"delicious-extended\">"+comment+"</div>";
			}
			output += "<div class=\"delicious-tags\">(tags:";
			for (String tag : tags) {
				output +=" <a href=\"http://delicious.com/andrewducker/"+tag+"\">"+tag+"</a>";
			}
			output+= ")</div>";
			
			output += "</li>";
		}
		output += "</ul>";
		return output;
	}
}
