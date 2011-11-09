package feedthistothat;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import feedthistothat.DataTypes.LinkEntry;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links, String postTemplate) throws Exception
	{
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		context.put("links",links);
		Template template = new Template();
		
		
		StringWriter writer = new StringWriter();
		//template.merge(context, writer);
		Velocity.evaluate(context, writer, "mystring",postTemplate);
		return writer.toString();
	}
	
	public static String FormatTitle(Calendar endTime)
	{
		return "Interesting Links for "+endTime.get(Calendar.DAY_OF_MONTH)+"-"+(endTime.get(Calendar.MONTH)+1)+"-"+endTime.get(Calendar.YEAR);
	}
}
