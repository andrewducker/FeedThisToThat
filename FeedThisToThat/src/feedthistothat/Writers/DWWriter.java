package feedthistothat.Writers;

import java.util.TimeZone;


public class DWWriter  extends LJWriter implements IWriter{

	public DWWriter(String userName, String password, TimeZone timeZone, Boolean postPrivately) {
		super(userName, password, timeZone, postPrivately);
		this.serverURL =  "http://www.dreamwidth.org/interface/xmlrpc";
	}
}
