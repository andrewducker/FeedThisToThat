package feedthistothat.Writers;

import java.util.List;

public class ConsoleWriter implements IWriter {

	@Override
	public String Write(String contents, String header, List<String> tags)
			throws Exception {
		System.out.println(header);
		System.out.println(contents);
		return null;
	}

	@Override
	public String EncryptPassword(String password) throws Exception {
		return null;
	}

}
