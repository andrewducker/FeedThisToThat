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
		int timeOfDay = 12;
		Boolean testMode = true;
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);

		feedSourceToLJ(resp, deliciousUserName, livejournalUserName,
				livejournalPassword, testMode,timeOfDay, timeZone);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String sourceUserName = req.getParameter("SourceUserName");
		String livejournalUserName = req.getParameter("LJUserName");
		String livejournalPassword = req.getParameter("LJPassword");
		Boolean testMode = req.getParameter("TestMode") != null;
		int timeOfDay = Integer.parseInt(req.getParameter("TimeOfDay"));
		
		String timeZoneID = "Europe/London";
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);

		
		feedSourceToLJ(resp, sourceUserName, livejournalUserName,
				livejournalPassword, testMode, timeOfDay, timeZone);
	}

	private void feedSourceToLJ(HttpServletResponse resp,
			String deliciousUserName, String livejournalUserName,
			String livejournalPassword, Boolean testMode, int timeOfDay, TimeZone timeZone) {
		ILinkSourceReader reader = new DeliciousReader(deliciousUserName);
		
		List<LinkEntry> links;
		try {
			links = reader.Read();

			Calendar endTime = Calendar.getInstance(timeZone);

			endTime.set(Calendar.HOUR_OF_DAY, timeOfDay);
			endTime.set(Calendar.MINUTE, 0);
			
			links = FilterLinksByDate(links, endTime);

			String output = LinkPostFormatter.Format(links);

			resp.setContentType("text/HTML");

			IWriter writer = null;
			if (testMode) {
				writer = new ResponseWriter(resp.getWriter());
			} else {
				writer = new DWWriter(livejournalUserName, livejournalPassword,
						timeZone);
			}

			resp.getWriter().println(writer.Write(output));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private List<LinkEntry> FilterLinksByDate(List<LinkEntry> links, Calendar endTime) {
		Calendar startTime = (Calendar) endTime.clone();
		startTime.add(Calendar.DAY_OF_MONTH, -1);

		List<LinkEntry> filteredList = new Vector<LinkEntry>();
		for (LinkEntry linkEntry : links) {
			if (linkEntry.PostedDate.before(endTime)
					&& linkEntry.PostedDate.after(startTime)) {
				filteredList.add(linkEntry);
			}
		}
		return filteredList;
	}
}