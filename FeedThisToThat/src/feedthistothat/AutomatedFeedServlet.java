package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import feedthistothat.DataTypes.DataAccessObject;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;


@SuppressWarnings("serial")
public class AutomatedFeedServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		try{
			List<Long> feeds = DataAccessObject.getFeedsForUpdate();
			Queue queue = QueueFactory.getDefaultQueue();
			for (Long feedKey : feeds) {
				queue.add(withUrl("/postsinglefeed").param("key", feedKey.toString()));			
			}
		} catch (Exception e) {
			Logger log = Logger.getLogger(FeedToLJServlet.class
					.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.severe("Uncaught Exception: " + sw.toString());
		}
	}
}
