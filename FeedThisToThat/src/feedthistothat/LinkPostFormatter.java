package feedthistothat;

import java.util.Calendar;
import java.util.List;

import feedthistothat.DataTypes.LinkEntry;
import feedthistothat.DataTypes.LinkTag;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links)
	{
		String postTemplate = "<ul class=\"links\">$postContents\n</ul>";
		String descriptionTemplate ="<BR><span class=\"link-description\">$description</span>"; 
		String entryTemplate ="\n<li class=\"link\"><A href=\"$url\">$title</A>$descriptionContents$tagContents</li>"; 
		String tagsTemplate = "<BR><span class=\"link-tags\">(tags:$tags)</span>";
		String tagTemplate = "<A href=\"$tagUrl\">$tag</A> ";

		String postContents = "";
		for (LinkEntry linkEntry : links) {
			String entryContents=entryTemplate.replace("$url", linkEntry.URL).replace("$title", linkEntry.Title);
			if(linkEntry.Description!= null && linkEntry.Description != "")
			{
				entryContents = entryContents.replace("$descriptionContents", descriptionTemplate.replace("$description", linkEntry.Description));
			}
			else
			{
				entryContents  = entryContents.replace("$descriptionContents","");
			}
			String tags = ""; 
			for (LinkTag tag : linkEntry.Tags) {
				tags += tagTemplate.replace("$tagUrl", tag.TagURL).replace("$tag", tag.Tag);
			}
			entryContents = entryContents.replace("$tagContents", tagsTemplate.replace("$tags", tags));
			postContents += entryContents;
		}
		return postTemplate.replace("$postContents", postContents);
	}
	
	public static String FormatTitle(Calendar endTime)
	{
		return "Interesting Links for "+endTime.get(Calendar.DAY_OF_MONTH)+"-"+(endTime.get(Calendar.MONTH)+1)+"-"+endTime.get(Calendar.YEAR);
	}
}
