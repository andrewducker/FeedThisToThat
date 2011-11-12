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

		links = FilterLinksByDate(links, feedParameters.getLastUpdated(), feedParameters.getPostingTime());

		String result;
		if (links.size() == 0) {
			result = "No entries found";
		} else {

			result = postLinks(feedParameters, links);
		}
		feedParameters.setResults(result);
		feedParameters.setLastUpdated(feedParameters.getPostingTime().getTime());

		if (feedParameters.isRepeats()) {
			Calendar newPostingTime = feedParameters.getPostingTime();
			newPostingTime.add(Calendar.DAY_OF_MONTH, feedParameters.getDaysToInclude());
			feedParameters.setPostingTime(newPostingTime.getTime());
		}

		return result;
	}

	private static String postLinks(FeedParameters feedParameters, LinkSet links)
			throws Exception {
		String result;
		Collections.sort(links);

		String output = LinkPostFormatter.Format(links,feedParameters.getPostTemplate());

		String header = LinkPostFormatter.FormatTitle(feedParameters.getPostingTime(), feedParameters.getSubjectTemplate());

		IWriter writer = WriterFactory.GetWriter(feedParameters.getTimeZone(),feedParameters.getDestinationUserName(),feedParameters.getDestinationPassword(),feedParameters.getPostPrivately(),feedParameters.getDestination());

		List<String> tagsForPosting;
		if (feedParameters.getPostWithTags()) {
			tagsForPosting = links.getTagNames();
		}
		else{
			tagsForPosting = new Vector<String>();
		}
		result = writer.Write(output, header, tagsForPosting);
		return result;
	}

	private static LinkSet FilterLinksByDate(LinkSet links, Calendar startTime, Calendar endTime) {
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
