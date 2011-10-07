package feedthistothat;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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