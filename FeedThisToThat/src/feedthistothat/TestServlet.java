package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import feedthistothat.DataTypes.BaseSaveable;
import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.Readers.DeliciousReader;
import feedthistothat.Readers.PinboardReader;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		try {
			DeliciousReader d = new DeliciousReader("andrewducker");
			d.setEmailAddress("andrew@ducker.org.uk");
			DataAccessObject.updateSaveable(d);
			PinboardReader p = new PinboardReader("julieport");
			p.setEmailAddress("andrew@ducker.org.uk");
			DataAccessObject.updateSaveable(p);
			List<BaseSaveable> saveables = DataAccessObject.loadSaveables("andrew@ducker.org.uk");
			for (BaseSaveable baseSaveable : saveables) {
				resp.getWriter().println(baseSaveable.getClass().getName());
			}
			resp.getWriter().println("Success!");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}
	}
}
