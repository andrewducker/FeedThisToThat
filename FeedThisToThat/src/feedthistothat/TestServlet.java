package feedthistothat;

import java.io.IOException;
import java.util.Enumeration;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/plain");
		
		try {

			DateFormat dateFormat = DateFormat.getInstance();
			DeliciousReader deliciousReader = new DeliciousReader("andrewducker");
			List<LinkEntry> links = deliciousReader.Read();
			
			TimeZone timeZone = TimeZone.getDefault();
			
			links = FilterLinksByDate(links, timeZone);
			
			for (LinkEntry linkEntry : links) {
				resp.getWriter().println(dateFormat.format(linkEntry.PostedDate.getTime()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			resp.setContentType("text/plain");
			for(Enumeration<String> e=req.getParameterNames();e.hasMoreElements();){
				String parameter = e.nextElement();
				resp.getWriter().println(parameter +": " + req.getParameter(parameter));
			}
			
			resp.getWriter().println("Success");
		
		} catch (IOException e) {
			e.printStackTrace();
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
