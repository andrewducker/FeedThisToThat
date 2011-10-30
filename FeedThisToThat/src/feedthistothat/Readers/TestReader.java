package feedthistothat.Readers;

import java.util.Calendar;

import feedthistothat.DataTypes.LinkEntry;
import feedthistothat.DataTypes.LinkSet;
import feedthistothat.DataTypes.LinkTag;

public class TestReader implements ILinkSourceReader {

	@Override
	public LinkSet Read() throws Exception {
		LinkSet linkSet = new LinkSet();
		linkSet.add(testLinkEntry("49",49));
		linkSet.add(testLinkEntry("24",24));
		linkSet.add(testLinkEntry("12",12));
		linkSet.add(testLinkEntry("11",11));
		linkSet.add(testLinkEntry("5",5));
		linkSet.add(testLinkEntry("3",3));
		return linkSet;
	}

	private LinkEntry testLinkEntry(String identifier, int hoursAgo) {
		LinkEntry linkEntry = new LinkEntry();
		linkEntry.PostedDate = Calendar.getInstance();
		linkEntry.PostedDate.add(Calendar.HOUR, -hoursAgo);
		linkEntry.Description = "Test Description " + identifier + " - " +linkEntry.PostedDate.get(Calendar.HOUR_OF_DAY)+":"+String.format("%02d", linkEntry.PostedDate.get(Calendar.MINUTE));
		linkEntry.Title = "Test Title " + identifier;
		linkEntry.URL = "http://NotActuallyALink.com" + identifier;
		LinkTag linkTag = new LinkTag();
		linkTag.Tag = "Tag 1";
		linkTag.TagURL = "http://tagURL1";
		linkEntry.Tags.add(linkTag);
		linkTag = new LinkTag();
		linkTag.Tag = "Tag 2";
		linkTag.TagURL = "http://tagURL2";
		linkEntry.Tags.add(linkTag);
		return linkEntry;
	}

}
