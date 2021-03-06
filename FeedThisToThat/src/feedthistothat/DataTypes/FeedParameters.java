package feedthistothat.DataTypes;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import feedthistothat.Readers.ReaderFactory.Reader;
import feedthistothat.Writers.IWriter;
import feedthistothat.Writers.WriterFactory;
import feedthistothat.Writers.WriterFactory.Writer;

@Cached
@Unindexed
public class FeedParameters {
	@Id @Indexed private Long id;
	private String sourceUserName = "";
	private Reader source  = Reader.Test;
	@Indexed private Date postingTime;
	private TimeZone timeZone = TimeZone.getTimeZone("Europe/London");
	private String destinationUserName = "";
	private String destinationPassword = "";
	private String url = "";
	private Writer destination = Writer.Test;
	@Indexed private String emailAddress = "";
	private boolean postPrivately = true;
	private Date lastUpdated;
	private boolean postWithTags = false;
	@SuppressWarnings("unused")
	@Indexed private boolean inPostingQueue = false;
	private boolean forcePostInPast = false;
	private int daysToInclude = 1;
	private boolean repeats = false;
	private String results;
	private String postTemplate;
	private String subjectTemplate;
	private String defaultTags;

	@SuppressWarnings("deprecation")
	public FeedParameters() {
		postingTime = new Date();
		postingTime.setHours(11);
		postTemplate = DataAccessObject.getDefaultPostTemplate();
		subjectTemplate =DataAccessObject.getDefaultSubjectTemplate();
		defaultTags = DataAccessObject.getDefaultTags();
	}

	public FeedParameters(HttpServletRequest req) throws Exception{
		sourceUserName = req.getParameter("SourceUserName");
		source = Reader.valueOf(req.getParameter("Source"));
		postingTime = getPostingTime(req);
		timeZone = TimeZone.getTimeZone(req.getParameter("TimeZone"));
		Calendar yesterday = Calendar.getInstance();
		daysToInclude = Integer.parseInt(req.getParameter("DaysToInclude"));
		yesterday.setTime(postingTime);
		yesterday.add(Calendar.DATE, -daysToInclude);
		lastUpdated = yesterday.getTime();
		destinationUserName = req.getParameter("DestinationUserName");
		destination = Writer.valueOf(req.getParameter("OutputTo"));
		IWriter writer = WriterFactory.GetWriter(null, null, null, null, null, destination);
		
		destinationPassword = writer.EncryptPassword(req.getParameter("DestinationPassword"));
		postTemplate = req.getParameter("PostTemplate");
		subjectTemplate = req.getParameter("SubjectTemplate");
		defaultTags = req.getParameter("DefaultTags");
		postPrivately = req.getParameter("PostPrivately") != null;
		postWithTags = req.getParameter("PostWithTags") != null;
		forcePostInPast = req.getParameter("ForcePostInPast") != null;
		repeats = req.getParameter("Repeats") != null;
		url = req.getParameter("URL");
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
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
		setInPostingQueue();
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
	public int getDaysToInclude() {
		return daysToInclude;
	}

	public void setDaysToInclude(int daysToInclude) {
		this.daysToInclude = daysToInclude;
	}

	public boolean isRepeats() {
		return repeats;
	}

	public void setRepeats(boolean repeats) {
		this.repeats = repeats;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getResults() {
		return results;
	}

	public void setPostTemplate(String postTemplate) {
		this.postTemplate = postTemplate;
	}

	public String getPostTemplate() {
		return postTemplate;
	}

	public void setSubjectTemplate(String subjectTemplate) {
		this.subjectTemplate = subjectTemplate;
	}

	public String getSubjectTemplate() {
		return subjectTemplate;
	}

	public String getUrl() {
		return url;
	}
	
	public String getDefaultTags(){
		return defaultTags;
	}
}
