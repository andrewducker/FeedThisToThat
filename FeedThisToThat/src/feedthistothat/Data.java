package feedthistothat;

import java.util.TimeZone;
import java.util.TreeSet;

import feedthistothat.DataTypes.DataAccessObject;
import feedthistothat.Readers.ReaderFactory.Reader;
import feedthistothat.Writers.WriterFactory.Writer;

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
	
	public Reader[] getReaders(){
		return Reader.values();
	}
	
	public String getDefaultPostTemplate(){
		return DataAccessObject.getDefaultPostTemplate();
	}
	
	public String getDefaultSubjectTemplate(){
		return DataAccessObject.getDefaultSubjectTemplate();
	}


}
