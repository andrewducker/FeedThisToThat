package feedthistothat;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String deliciousUserName = "andrewducker";
		String livejournalUserName = "andrewducker";
		String livejournalPassword = "Balisk!";
		String timeZoneID = "Europe/London";
		Boolean test = true;
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);

		ILinkSourceReader reader = new DeliciousReader(deliciousUserName);

		List<LinkEntry> links;
		try {
			links = reader.Read();

			links = FilterLinksByDate(links,timeZone );
			
			String output = LinkPostFormatter.Format(links);

			resp.setContentType("text/HTML");

			
			IWriter writer = null;
			if(test){
				writer = new ResponseWriter(resp.getWriter());
			}
			else{
				writer = new LJWriter(livejournalUserName, livejournalPassword, timeZone);
			}
				
			resp.getWriter().println(writer.Write(output));

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private List<LinkEntry> FilterLinksByDate(List<LinkEntry> links, TimeZone timeZone) {
		Calendar endTime = Calendar.getInstance(timeZone);
		Calendar startTime = (Calendar) endTime.clone();
		startTime.add(Calendar.DAY_OF_MONTH, -1);

		List<LinkEntry> filteredList = new Vector<LinkEntry>();
		for (LinkEntry linkEntry : links) {
			if (linkEntry.PostedDate.before(endTime) && linkEntry.PostedDate.after(startTime)) {
				filteredList.add(linkEntry);
			}
		}
		return filteredList;
	}
}