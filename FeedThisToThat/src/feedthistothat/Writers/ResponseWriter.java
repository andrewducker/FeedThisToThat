package feedthistothat.Writers;

import java.io.PrintWriter;
import java.util.List;

import com.googlecode.objectify.annotation.Subclass;

@SuppressWarnings("unused")
@Subclass
public class ResponseWriter extends BaseWriter {

	private ResponseWriter(){}
	
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
