package feedthistothat;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import feedthistothat.DataTypes.FeedParameters;
import feedthistothat.DataTypes.LinkEntry;
import feedthistothat.DataTypes.LinkSet;
import feedthistothat.Readers.ILinkSourceReader;
import feedthistothat.Readers.ReaderFactory;
import feedthistothat.Writers.IWriter;
import feedthistothat.Writers.WriterFactory;

public class Feeder {

	public static String Feed(FeedParameters feedParameters) throws Exception{
		ILinkSourceReader reader = ReaderFactory.GetReader(feedParameters.getSource(), feedParameters.getSourceUserName());
		LinkSet links = reader.Read();
		
		links = FilterLinksByDate(links, feedParameters.getPostingTime());
	
		Collections.sort(links);
		
		String output = LinkPostFormatter.Format(links);
		
		String header = LinkPostFormatter.FormatTitle(feedParameters.getPostingTime());
	
		IWriter writer = WriterFactory.GetWriter(feedParameters.getTimeZone(),feedParameters.getDestinationUserName(),feedParameters.getDestinationPassword(),feedParameters.getPostPrivately(),feedParameters.getDestination());
		
		List<String> tagsForPosting;
		if (feedParameters.getPostWithTags()) {
			tagsForPosting = links.getTagNames();
		}
		else{
			tagsForPosting = new Vector<String>();
		}
		
		return writer.Write(output, header, tagsForPosting);
	}
	
	private static LinkSet FilterLinksByDate(LinkSet links, Calendar endTime) {
		Calendar startTime = (Calendar) endTime.clone();
		startTime.add(Calendar.DAY_OF_MONTH, -1);

		LinkSet filteredList = new LinkSet();
		for (LinkEntry linkEntry : links) {
			if (linkEntry.PostedDate.before(endTime)
					&& linkEntry.PostedDate.after(startTime)) {
				filteredList.add(linkEntry);
			}
		}
		return filteredList;
	}

}
