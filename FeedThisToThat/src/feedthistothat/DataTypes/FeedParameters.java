package feedthistothat.DataTypes;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import feedthistothat.Readers.ReaderFactory.Reader;
import feedthistothat.Writers.WriterFactory.Writer;

public class FeedParameters {
	@Id private Long id;
	private String sourceUserName;
	private Reader source;
	private Date endTime;
	private TimeZone timeZone;
	private String destinationUserName;
	private String destinationPassword;
	private Writer destination;
	private String emailAddress;
	private boolean postPrivately;

	public FeedParameters(){}

	public FeedParameters(HttpServletRequest req) throws Exception{
		sourceUserName = req.getParameter("SourceUserName");
		source = Reader.valueOf(req.getParameter("Source"));
		endTime = getEndTime(req);
		timeZone = TimeZone.getTimeZone(req.getParameter("TimeZone"));

		destinationUserName = req.getParameter("DestinationUserName");
		destination = Writer.valueOf(req.getParameter("OutputTo"));
		destinationPassword = PasswordEncrypt.Encrypt(destination, req.getParameter("DestinationPassword"));
		postPrivately = req.getParameter("PostPrivately") != null;
	}
	
	@SuppressWarnings("deprecation")
	public static FeedParameters getDefault(){
		FeedParameters toReturn = new FeedParameters();
		toReturn.sourceUserName="";
		toReturn.destinationUserName = "";
		toReturn.source = Reader.Delicious;
		toReturn.endTime = new Date();
		toReturn.endTime.setHours(11);
		toReturn.timeZone = TimeZone.getTimeZone("Europe/London");
		toReturn.destination = Writer.Dreamwidth;
		return toReturn;
	}
	
	private Date getEndTime(HttpServletRequest req) {
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
		
		return endTime.getTime();
	}

	public String getSourceUserName() {
		return sourceUserName;
	}
	public Reader getSource() {
		return source;
	}
	public Calendar getEndTime() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(endTime);
		return calendar;
	}
	public int getEndHour(){
		return getEndTime().get(Calendar.HOUR_OF_DAY);
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
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getPostPrivately() {
		return postPrivately;
	}
}
