package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.googlecode.objectify.Key;

import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;


@SuppressWarnings("serial")
public class AutomatedFeedServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		List<Long> feeds = DataAccessObject.getFeedsForUpdate();
		PrintWriter writer = resp.getWriter();
		Queue queue = QueueFactory.getDefaultQueue();
		for (Long feedKey : feeds) {
			    queue.add(withUrl("/postsinglefeed").param("key", feedKey.toString()));			
		}
	}
}
