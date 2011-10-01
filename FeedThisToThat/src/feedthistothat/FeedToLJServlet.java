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

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String sourceUserName = req.getParameter("SourceUserName");
		
		ILinkSourceReader reader = new DeliciousReader(sourceUserName);
		
		List<LinkEntry> links;
		try {
			links = reader.Read();
		
			Calendar endTime = getEndTime(req);
			
			links = FilterLinksByDate(links, endTime);
		
			String output = LinkPostFormatter.Format(links);
		
			resp.setContentType("text/HTML");
		
			IWriter writer = WriterFactory.GetWriter(req, resp);
			resp.getWriter().println(writer.Write(output));
		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private Calendar getEndTime(HttpServletRequest req) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar endTime = Calendar.getInstance(timeZone);

		int timeOfDay = Integer.parseInt(req.getParameter("TimeOfDay"));
		int day = Integer.parseInt(req.getParameter("Day"));
		int month = Integer.parseInt(req.getParameter("Month"))-1;
		int year = Integer.parseInt(req.getParameter("Year"));
		endTime.set(Calendar.HOUR_OF_DAY, timeOfDay);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND,0);
		endTime.set(Calendar.DATE, day);
		endTime.set(Calendar.MONTH, month);
		endTime.set(Calendar.YEAR, year);
		return endTime;
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