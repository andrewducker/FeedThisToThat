package feedthistothat;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class Feeder {

	public static String Feed(FeedParameters feedParameters) throws Exception{
		ILinkSourceReader reader = ReaderFactory.GetReader(feedParameters.getSource(), feedParameters.getDestinationUserName());
		List<LinkEntry> links = reader.Read();
		
		links = FilterLinksByDate(links, feedParameters.getEndTime());
	
		String output = LinkPostFormatter.Format(links);
		
		String header = LinkPostFormatter.FormatTitle(feedParameters.getEndTime());
	
		IWriter writer = WriterFactory.GetWriter(feedParameters.getTimeZone(),feedParameters.getDestinationUserName(),feedParameters.getDestinationPassword(),feedParameters.getDestination());
		
		return writer.Write(output, header);
	}
	
	private static List<LinkEntry> FilterLinksByDate(List<LinkEntry> links, Calendar endTime) {
		Calendar startTime = (Calendar) endTime.clone();
		startTime.add(Calendar.DAY_OF_MONTH, -1);

		List<LinkEntry> filteredList = new Vector<LinkEntry>();
		for (LinkEntry linkEntry : links) {
			if (linkEntry.PostedDate.before(endTime)
					&& linkEntry.PostedDate.after(startTime)) {
				filteredList.add(linkEntry);
			}
		}
		return filteredList;
	}

}
