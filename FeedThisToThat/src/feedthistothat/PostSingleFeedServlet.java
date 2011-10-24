package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;
import feedthistothat.Writers.ConsoleWriter;
import feedthistothat.Writers.WriterFactory;

@SuppressWarnings("serial")
public class PostSingleFeedServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		long key = Long.parseLong(req.getParameter("key"));
		WriterFactory.setTestWriter(new ConsoleWriter());
		FeedParameters parameters = DataAccessObject.GetFeedParameters(key);
		try {
			Feeder.Feed(parameters);
			resp.getWriter().println("Done");
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
