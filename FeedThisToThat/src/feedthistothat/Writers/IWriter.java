package feedthistothat.Writers;

import java.util.List;

public interface IWriter {
	String Write(String contents, String header,List<String> tags) throws Exception;
	String EncryptPassword(String password) throws Exception;
}
