package feedthistothat;

import java.io.IOException;
import java.util.List;

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
		String timeZone = "Europe/London";

		ILinkSourceReader reader = new DeliciousReader(deliciousUserName);

		List<LinkEntry> links;
		try {
			links = reader.Read();

			String output = LinkPostFormatter.Format(links);

			resp.setContentType("text/HTML");

			LJWriter writer = new LJWriter(livejournalUserName, livejournalPassword, timeZone);
			resp.getWriter().println(
					"<A href=" + writer.Write(output) + ">Link posted</A>");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}