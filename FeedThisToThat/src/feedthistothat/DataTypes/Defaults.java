package feedthistothat.DataTypes;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Defaults {

	public static String getDefaultPostTemplate(){
		File templateFile = new File("PostTemplate.vm");
		String toReturn = "";
		try {
			toReturn = FileUtils.readFileToString(templateFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return toReturn;
	}

	public static String getDefaultSubjectTemplate(){
		return "Interesting Links for $date.format(\"dd-MM-yyyy\",$postingTime)";
	}

	public static String getDefaultTags() {
		return "links";
	}

}
