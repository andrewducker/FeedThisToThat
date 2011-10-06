package feedthistothat;

import java.util.Calendar;
import java.util.List;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links)
	{
		String post = "<ul class=\"links\">";
		
		for (LinkEntry linkEntry : links) {
			post+="\n";
			post += "<li><A href=\""+linkEntry.URL+"\">" +linkEntry.Title+"</A>";
			if(linkEntry.Description!= null && linkEntry.Description != "")
			{
				post += "<BR>"+linkEntry.Description+"";
			}
			post += "<BR>(tags:";
			for (String tag : linkEntry.Tags) {
				post += tag;
			}
			post+= ")";
			
			post += "</li>";
		}
		post += "</ul>";
		return post;
	}
	
	public static String FormatTitle(Calendar endTime)
	{
		return "Interesting Links for "+endTime.get(Calendar.DAY_OF_MONTH)+"-"+(endTime.get(Calendar.MONTH)+1)+"-"+endTime.get(Calendar.YEAR);
	}
	
}
