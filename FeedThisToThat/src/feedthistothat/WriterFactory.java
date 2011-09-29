package feedthistothat;

import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriterFactory {
	public enum Writer{
		Dreamwidth,
		Livejournal,
		Test
	}

	public Writer[] getWriters(){
		return Writer.values();
	}
	
	public static IWriter GetWriter(HttpServletRequest req, HttpServletResponse resp, TimeZone timeZone) throws Exception{
		String destinationUserName = req.getParameter("DestinationUserName");
		String destinationPassword = req.getParameter("DestinationPassword");
		String destination = req.getParameter("OutputTo");
		switch (Writer.valueOf(destination)) {
		case Dreamwidth:
			return new DWWriter(destinationUserName, destinationPassword, timeZone);
		case Livejournal:
			return new LJWriter(destinationUserName, destinationPassword, timeZone);
		case Test:
			return new ResponseWriter(resp.getWriter());
		default:
			throw new Exception("No such destination - " + destination);
		}
	}
}
