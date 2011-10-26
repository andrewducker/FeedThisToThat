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
	private String sourceUserName = "";
	private Reader source  = Reader.Delicious;
	private Date postingTime;
	private TimeZone timeZone = TimeZone.getTimeZone("Europe/London");
	private String destinationUserName = "";
	private String destinationPassword = "";
	private Writer destination = Writer.Dreamwidth;
	private String emailAddress = "";
	private boolean postPrivately = true;
	private Date lastUpdated;
	private boolean postWithTags = false;
	@SuppressWarnings("unused")
	private boolean inPostingQueue = false;
	private boolean forcePostInPast = false;

	@SuppressWarnings("deprecation")
	public FeedParameters(){
		postingTime = new Date();
		postingTime.setHours(11);
	}

	@SuppressWarnings("deprecation")
	public FeedParameters(HttpServletRequest req) throws Exception{
		sourceUserName = req.getParameter("SourceUserName");
		source = Reader.valueOf(req.getParameter("Source"));
		postingTime = getPostingTime(req);
		timeZone = TimeZone.getTimeZone(req.getParameter("TimeZone"));
		Calendar yesterday = Calendar.getInstance();
		yesterday.setTime(postingTime);
		yesterday.add(Calendar.DATE, -1);
		lastUpdated = yesterday.getTime();
		destinationUserName = req.getParameter("DestinationUserName");
		destination = Writer.valueOf(req.getParameter("OutputTo"));
		destinationPassword = PasswordEncrypt.Encrypt(destination, req.getParameter("DestinationPassword"));
		postPrivately = req.getParameter("PostPrivately") != null;
		postWithTags = req.getParameter("PostWithTags") != null;
		forcePostInPast = req.getParameter("ForcePostInPast") != null;
		lastUpdated = new Date();
		lastUpdated.setYear(90);
		setInPostingQueue();
	}
	
	private void setInPostingQueue() {
		inPostingQueue = lastUpdated.before(postingTime);
	}

	private Date getPostingTime(HttpServletRequest req) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar postingTime = Calendar.getInstance(timeZone);

		int timeOfDay = Integer.parseInt(req.getParameter("TimeOfDay"));
		int day = Integer.parseInt(req.getParameter("Day"));
		int month = Integer.parseInt(req.getParameter("Month"))-1;
		int year = Integer.parseInt(req.getParameter("Year"));
		postingTime.set(Calendar.HOUR_OF_DAY, timeOfDay);
		postingTime.set(Calendar.MINUTE, 0);
		postingTime.set(Calendar.SECOND,0);
		postingTime.set(Calendar.MILLISECOND,0);
		postingTime.set(Calendar.DATE, day);
		postingTime.set(Calendar.MONTH, month);
		postingTime.set(Calendar.YEAR, year);
		
		return postingTime.getTime();
	}

	public String getSourceUserName() {
		return sourceUserName;
	}
	public Reader getSource() {
		return source;
	}
	public Calendar getPostingTime() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(postingTime);
		return calendar;
	}
	public int getPostingHour(){
		return getPostingTime().get(Calendar.HOUR_OF_DAY);
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

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		setInPostingQueue();
	}

	public Calendar getLastUpdated() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(lastUpdated);
		return calendar;
	}

	public void setPostWithTags(boolean postWithTags) {
		this.postWithTags = postWithTags;
	}

	public boolean getPostWithTags() {
		return postWithTags;
	}
	
	public boolean getForcePostInPast() {
		return forcePostInPast;
	}

}
