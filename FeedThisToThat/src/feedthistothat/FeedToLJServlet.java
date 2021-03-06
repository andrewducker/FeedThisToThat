package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedParameters;
import feedthistothat.Writers.ResponseWriter;
import feedthistothat.Writers.WriterFactory;

@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(FeedToLJServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		FeedParameters parameters = null;
		WriterFactory.setTestWriter(new ResponseWriter(resp.getWriter()));
		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			 parameters = new FeedParameters(req);
			if (user != null) {
				parameters.setEmailAddress(user.getEmail());
			}
			if (parameters.getPostingTime().before(Calendar.getInstance()) && !parameters.getForcePostInPast()) {
				resp.setContentType("text/HTML");
				String output = Feeder.Feed(parameters);
				resp.getWriter().println(output);
			} else {
				if (user != null) {
					resp.getWriter().println("Saved for future posting");
				} else {
					resp.getWriter().println(
							"Only logged in users can set up future postings.");
				}
			}
		} catch (Exception e1) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e1.printStackTrace(pw);
			log.severe("Uncaught Exception: " + sw.toString());
			resp.getWriter().println("Something unexpected has occurred.");
			e1.printStackTrace(resp.getWriter());
		}
		DataAccessObject.updateFeedParameters(parameters);
	}
}