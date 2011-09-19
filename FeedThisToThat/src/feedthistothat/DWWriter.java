package feedthistothat;

import java.util.TimeZone;

public class DWWriter  extends LJWriter implements IWriter{

	public DWWriter(String userName, String password, TimeZone timeZone) {
		super(userName, password, timeZone);
		this.serverURL =  "http://www.dreamwidth.org/interface/xmlrpc";
	}
}
