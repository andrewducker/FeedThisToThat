package feedthistothat;

import java.util.TimeZone;
import java.util.TreeSet;

import feedthistothat.DataTypes.Defaults;
import feedthistothat.Readers.ReaderFactory.ReaderType;
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
	
	public ReaderType[] getReaders(){
		return ReaderType.values();
	}
	
	public String getDefaultPostTemplate(){
		return Defaults.getDefaultPostTemplate();
	}
	
	public String getDefaultSubjectTemplate(){
		return Defaults.getDefaultSubjectTemplate();
	}
	
	public String getDefaultTags(){
		return Defaults.getDefaultTags();
	}


}
