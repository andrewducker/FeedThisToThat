package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import feedthistothat.DataTypes.DataAccessObject;

@SuppressWarnings("serial")
public class PostSingleFeedServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		List<Long> feeds = DataAccessObject.getFeedsForUpdate();
		PrintWriter writer = resp.getWriter();
		for (Long feedKey : feeds) {
			writer.println(feedKey);			
		}
	}

}
