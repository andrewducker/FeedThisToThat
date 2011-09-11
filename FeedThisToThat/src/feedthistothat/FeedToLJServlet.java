package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.org.catnip.eddie.FeedData;
import uk.org.catnip.eddie.parser.Parser;
import uk.org.catnip.eddie.Category;
import uk.org.catnip.eddie.Detail;
import uk.org.catnip.eddie.Entry;
import uk.org.catnip.eddie.Link;;


@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/HTML");

		String userName="andrewducker";
		
		String feedURL = "http://feeds.delicious.com/v2/rss/"+userName+"?count=30";
		
		byte[] feedContents = FeedReader.Read(feedURL);
		
		Parser parser = new Parser();
		FeedData feedData = null;
		feedData = parser.parse(feedContents);
		PrintWriter writer = resp.getWriter();
		writer.println("<ul class=\"delicious\">");
		
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

			writer.println("<li><div class=\"delicious-link\"><A href=\""+link+"\">" +title+"</A></div>");
			if(comment != "")
			{
				writer.println("<div class=\"delicious-extended\">"+comment+"</div>");
			}
			writer.println("<div class=\"delicious-tags\">(tags:");
			for (String tag : tags) {
				writer.print(" <a href=\"http://delicious.com/andrewducker/"+tag+"\">"+tag+"</a>");
			}
			writer.print(")</div>");
			
			writer.println("</li>");
		}
		writer.println("</ul>");
		
//	    WriteToLJ writer = new WriteToLJ();
//	    writer.Write();
        
		//resp.getWriter().println(feedContents);
	}
}