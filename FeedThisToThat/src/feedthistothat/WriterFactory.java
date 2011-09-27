package feedthistothat;

import java.util.Vector;

public class WriterFactory {
	public WriterFactory() {
		writers = new Vector<String>();
		writers.add("Dreamwidth");
		writers.add("Livejournal");
		writers.add("Test");
	}

	private Vector<String> writers;
	
	public Vector<String>getWriters(){
		return writers;
	}
}
