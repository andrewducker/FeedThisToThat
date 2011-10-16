package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
	
	private static final Logger log = Logger.getLogger(FeedToLJServlet.class.getName());

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {

			
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			FeedParameters parameters = new FeedParameters(req);
	        if (user != null) {
	        	parameters.setEmailAddress(user.getEmail());
	        	DataAccessObject.updateFeedParameters(parameters);
	        } 
			resp.setContentType("text/HTML");
			WriterFactory.setTestWriter(new ResponseWriter(resp.getWriter()));
			String output =  Feeder.Feed(parameters);
			resp.getWriter().println(output);
		
		} catch (Exception e1) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e1.printStackTrace(pw);
			log.severe("Uncaught Exception: "+sw.toString());
			resp.getWriter().println("Something unexpected has occurred.");
			e1.printStackTrace(resp.getWriter()); 
		}
	}
}