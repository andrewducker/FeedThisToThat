package feedthistothat;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import feedthistothat.DataTypes.FeedParameters;
import feedthistothat.Writers.ResponseWriter;
import feedthistothat.Writers.WriterFactory;

@SuppressWarnings("serial")
public class FeedToLJServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {

			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
	        if (user != null) {
	            resp.setContentType("text/plain");
	            resp.getWriter().println("Hello, " + user.getNickname());
	        } else {
	            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	        }
			
			FeedParameters parameters = new FeedParameters(req);
			resp.setContentType("text/HTML");
			WriterFactory.setTestWriter(new ResponseWriter(resp.getWriter()));
			String output =  Feeder.Feed(parameters);
			resp.getWriter().println(output);
		
		} catch (Exception e1) {
			e1.printStackTrace(resp.getWriter()); 
		}
	}

}