package feedthistothat.Writers;

import java.io.PrintWriter;
import java.util.List;


public class ResponseWriter extends BaseWriter {

	private PrintWriter writer;
	
	public ResponseWriter(PrintWriter writer){
		this.writer = writer;
	}
	@Override
	public String Write(String string, String header,List<String> tags) throws Exception {
		writer.println(header);
		writer.print(string);
		writer.print("Tags: ");
		for (String tag : tags) {
			writer.println(tag);
		}
		writer.println("\n");
		return "Done!";
	}
	@Override
	public String EncryptPassword(String password) throws Exception {
		return null;
	}

}
