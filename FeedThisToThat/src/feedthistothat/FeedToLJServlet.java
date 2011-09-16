package feedthistothat;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String deliciousUserName="andrewducker";
		String livejournalUserName="andrewducker";
		String livejournalPassword="Balisk!";
		String timeZone = "Europe/London";
		String output = ReadFromDelicious.Read(deliciousUserName);

		resp.setContentType("text/HTML");
		
	    LJWriter writer = new LJWriter(livejournalUserName,livejournalPassword,timeZone);
		try {
			resp.getWriter().println("<A href="+writer.Write(output)+">Link posted</A>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}