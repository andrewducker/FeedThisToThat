package feedthistothat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/plain");
		
		try {
            URL url = new URL("http://feeds.delicious.com/v2/rss/andrewducker?plain");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {	
                resp.getWriter().println(line);
            }
            reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			resp.setContentType("text/plain");
			for(Enumeration<String> e=req.getParameterNames();e.hasMoreElements();){
				String parameter = e.nextElement();
				resp.getWriter().println(parameter +": " + req.getParameter(parameter));
			}
			
			resp.getWriter().println("Success");

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
