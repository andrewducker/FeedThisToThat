package feedthistothat.DataTypes;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import feedthistothat.Readers.ReaderFactory.Reader;
import feedthistothat.Writers.WriterFactory.Writer;

public class FeedParameters {
	private String sourceUserName;
	private Reader source;
	private Calendar endTime;
	private TimeZone timeZone;
	private String destinationUserName;
	private String destinationPassword;
	private Writer destination;

	public FeedParameters(HttpServletRequest req){
		sourceUserName = req.getParameter("SourceUserName");
		source = Reader.valueOf(req.getParameter("Source"));
		endTime = getEndTime(req);
		timeZone = TimeZone.getTimeZone(req.getParameter("TimeZone"));

		destinationUserName = req.getParameter("DestinationUserName");
		destinationPassword = req.getParameter("DestinationPassword");
		destination = Writer.valueOf(req.getParameter("OutputTo"));
	}
	
	private Calendar getEndTime(HttpServletRequest req) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar endTime = Calendar.getInstance(timeZone);

		int timeOfDay = Integer.parseInt(req.getParameter("TimeOfDay"));
		int day = Integer.parseInt(req.getParameter("Day"));
		int month = Integer.parseInt(req.getParameter("Month"))-1;
		int year = Integer.parseInt(req.getParameter("Year"));
		endTime.set(Calendar.HOUR_OF_DAY, timeOfDay);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND,0);
		endTime.set(Calendar.DATE, day);
		endTime.set(Calendar.MONTH, month);
		endTime.set(Calendar.YEAR, year);
		return endTime;
	}

	public String getSourceUserName() {
		return sourceUserName;
	}
	public Reader getSource() {
		return source;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public TimeZone getTimeZone() {
		return timeZone;
	}
	public String getDestinationUserName() {
		return destinationUserName;
	}
	public String getDestinationPassword() {
		return destinationPassword;
	}
	public Writer getDestination() {
		return destination;
	}
}
