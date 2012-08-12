package feedthistothat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import feedthistothat.DataTypes.BaseSaveable;
import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.DataTypes.FeedInstruction;
import feedthistothat.DataTypes.Saveables;
import feedthistothat.Readers.BaseReader;
import feedthistothat.Readers.DeliciousReader;
import feedthistothat.Readers.PinboardReader;
import feedthistothat.Writers.LJWriter;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		try {
			String emailAddress = "andrew@ducker.org.uk";
			DeliciousReader d = new DeliciousReader("andrewducker");
			d.setEmailAddress(emailAddress);
			DataAccessObject.updateSaveable(d);
			PinboardReader p = new PinboardReader("julieport");
			p.setEmailAddress(emailAddress);
			DataAccessObject.updateSaveable(p);
			LJWriter w = new LJWriter("BobSmith", "password", TimeZone.getDefault(), true);
			w.setEmailAddress(emailAddress);
			DataAccessObject.updateSaveable(w);
			FeedInstruction feedInstruction = new FeedInstruction();
			feedInstruction.setEmailAddress(emailAddress);
			feedInstruction.Add(d);
			feedInstruction.Add(p);
			feedInstruction.setWriter(w);
			DataAccessObject.updateSaveable(feedInstruction);
			
			
			Saveables saveables = DataAccessObject.loadSaveables(emailAddress);
			List<FeedInstruction> instructions = saveables.getSubsetOfClass(FeedInstruction.class);
			PrintWriter writer = resp.getWriter();
			for (FeedInstruction feedInstruction1 : instructions) {
				writer.println("Instruction: "+ feedInstruction1.getId().toString());
				for (BaseReader reader : feedInstruction1.getReaders(saveables)) {
					writer.println("Reader: "+ reader.getClass().getName());
				}
				writer.println("Writer: " + feedInstruction1.getWriter(saveables).getClass().getName());
				
			}
			resp.getWriter().println("Success!");
		} catch (Exception e) {
			
			PrintWriter pw = new PrintWriter(resp.getWriter());
			e.printStackTrace(pw);
		}
	}
}
