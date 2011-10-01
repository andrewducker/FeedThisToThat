package feedthistothat;

import java.util.TimeZone;
import java.util.TreeSet;

import feedthistothat.WriterFactory.Writer;

public class Data {
	private static TreeSet<String> timeZones = new TreeSet<String>();
	
	static {
		for (String timeZone : TimeZone.getAvailableIDs()) {
			timeZones.add(timeZone);
		}
	}

	public Iterable<String> getTimeZones(){
		return timeZones;
	}
	
	public Writer[] getWriters(){
		return Writer.values();
	}


}
