package feedthistothat;

import java.util.Calendar;
import java.util.List;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links)
	{
		String post = "<ul class=\"delicious\">";
		
		for (LinkEntry linkEntry : links) {
			post+="\n";
			post += "<li><div class=\"delicious-link\"><A href=\""+linkEntry.URL+"\">" +linkEntry.Title+"</A></div>";
			if(linkEntry.Description!= null && linkEntry.Description != "")
			{
				post += "<div class=\"delicious-extended\">"+linkEntry.Description+"</div>";
			}
			post += "<div class=\"delicious-tags\">(tags:";
			for (String tag : linkEntry.Tags) {
				post += tag;
			}
			post+= ")</div>";
			
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
