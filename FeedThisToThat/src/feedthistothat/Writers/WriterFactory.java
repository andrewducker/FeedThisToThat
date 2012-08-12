package feedthistothat.Writers;

import java.util.TimeZone;


public class WriterFactory {
	public enum Writer{
		Dreamwidth,
		Livejournal,
		WordPress,
		Test;
	}

	private static BaseWriter testWriter;
	public static void setTestWriter(BaseWriter testWriter){
		WriterFactory.testWriter = testWriter;
	}
	
	public static BaseWriter GetWriter(TimeZone timeZone, String destinationUserName,String destinationPassword, Boolean postPrivately, String url, Writer writer, String emailAddress) throws Exception{
		BaseWriter baseWriter;
		switch (writer) {
		case Dreamwidth:
			baseWriter = new DWWriter(destinationUserName, destinationPassword, timeZone, postPrivately);
			break;
		case Livejournal:
			baseWriter = new LJWriter(destinationUserName, destinationPassword, timeZone, postPrivately);
			break;
		case Test:
			baseWriter = testWriter;
			break;
		case WordPress:
			baseWriter = new MetaWeblogAPIWriter(destinationUserName, destinationPassword, url, "NotUsedByWordPress");
			break;
		default:
			throw new Exception("No such destination - " + writer);
		}
		baseWriter.setEmailAddress(emailAddress);
		return baseWriter;
	}
}
