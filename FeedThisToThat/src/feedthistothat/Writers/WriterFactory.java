package feedthistothat.Writers;

import java.util.TimeZone;


public class WriterFactory {
	public enum Writer{
		Dreamwidth,
		Livejournal,
		Test
	}

	private static IWriter testWriter;
	public static void setTestWriter(IWriter testWriter){
		WriterFactory.testWriter = testWriter;
	}
	
	public static IWriter GetWriter(TimeZone timeZone, String destinationUserName,String destinationPassword, Writer writer) throws Exception{

		switch (writer) {
		case Dreamwidth:
			return new DWWriter(destinationUserName, destinationPassword, timeZone);
		case Livejournal:
			return new LJWriter(destinationUserName, destinationPassword, timeZone);
		case Test:
			return testWriter;
		default:
			throw new Exception("No such destination - " + writer);
		}
	}
}