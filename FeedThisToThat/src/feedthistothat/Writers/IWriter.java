package feedthistothat.Writers;

import java.util.List;

public interface IWriter {
	String Write(String string, String header,List<String> tags) throws Exception;
}
