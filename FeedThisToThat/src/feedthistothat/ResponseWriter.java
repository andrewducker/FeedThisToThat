package feedthistothat;

import java.io.PrintWriter;

public class ResponseWriter implements IWriter {

	private PrintWriter writer;
	
	public ResponseWriter(PrintWriter writer){
		this.writer = writer;
	}
	@Override
	public String Write(String string, String header) throws Exception {
		writer.println(header);
		writer.print(string);
		writer.println("\n");
		return "Done!";
	}

}
