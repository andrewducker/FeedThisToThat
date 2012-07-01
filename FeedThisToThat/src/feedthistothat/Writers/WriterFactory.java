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
	
	public static BaseWriter GetWriter(TimeZone timeZone, String destinationUserName,String destinationPassword, Boolean postPrivately, String url, Writer writer, String email) throws Exception{

		switch (writer) {
		case Dreamwidth:
			return new DWWriter(destinationUserName, destinationPassword, timeZone, postPrivately, email);
		case Livejournal:
			return new LJWriter(destinationUserName, destinationPassword, timeZone, postPrivately, email);
		case Test:
			return testWriter;
		case WordPress:
			return new MetaWeblogAPI(destinationUserName, destinationPassword, url, "NotUsedByWordPress");
		default:
			throw new Exception("No such destination - " + writer);
		}
	}
}
