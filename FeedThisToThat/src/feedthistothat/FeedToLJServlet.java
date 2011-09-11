package feedthistothat;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {


//		String userName="andrewducker";
//		String output = ReadFromDelicious.Read(userName);
//
//
//		resp.setContentType("text/HTML");
//		PrintWriter writer = resp.getWriter();
//		writer.println(output);
		
	    WriteToLJ writer = new WriteToLJ("andrewducker","Balisk!");
		try {
			resp.getWriter().println(writer.Write());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}