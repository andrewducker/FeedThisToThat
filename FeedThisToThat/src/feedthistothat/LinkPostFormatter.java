package feedthistothat;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import feedthistothat.DataTypes.LinkEntry;
import feedthistothat.DataTypes.LinkTag;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links) throws Exception
	{
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		context.put("links",links);
		Template template = Velocity.getTemplate("PostTemplate.vm");
		
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
	
	public static String FormatTitle(Calendar endTime)
	{
		return "Interesting Links for "+endTime.get(Calendar.DAY_OF_MONTH)+"-"+(endTime.get(Calendar.MONTH)+1)+"-"+endTime.get(Calendar.YEAR);
	}
}
