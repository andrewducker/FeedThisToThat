package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;

@SuppressWarnings("serial")
public class AutomatedFeedServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		List<FeedParameters> feeds = DataAccessObject.getFeedsForUpdate();
		PrintWriter writer = resp.getWriter();
		for (FeedParameters feedParameters : feeds) {
			writer.println(feedParameters.getEmailAddress());			
		}
	}
}
