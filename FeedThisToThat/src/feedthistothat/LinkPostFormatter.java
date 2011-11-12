package feedthistothat;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;

import feedthistothat.DataTypes.LinkEntry;

public class LinkPostFormatter {

	public static String Format(List<LinkEntry> links, String postTemplate) throws Exception
	{
		Velocity.init();
		VelocityContext context = new VelocityContext();
		
		context.put("links",links);
		StringWriter writer = new StringWriter();
		Velocity.evaluate(context, writer, "mystring",postTemplate);
		return writer.toString();
	}
	
	public static String FormatTitle(Calendar endTime, String subjectTemplate) throws Exception
	{
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		DateTool dateTool = new DateTool();
		context.put("date", dateTool);
		context.put("postingTime",endTime);
		Velocity.evaluate(context, writer, "", subjectTemplate);
		return writer.toString();
	}
}
