package feedthistothat.Writers;

import java.util.List;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class ConsoleWriter extends BaseWriter {

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
