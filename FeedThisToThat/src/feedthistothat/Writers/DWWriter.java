package feedthistothat.Writers;

import java.util.TimeZone;


public class DWWriter  extends LJWriter {

	public DWWriter(String userName, String password, TimeZone timeZone, Boolean postPrivately, String email) {
		super(userName, password, timeZone, postPrivately, email);
		this.serverURL =  "http://www.dreamwidth.org/interface/xmlrpc";
	}
}
