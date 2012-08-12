package feedthistothat.Writers;

import java.util.TimeZone;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class DWWriter  extends LJWriter {

	@SuppressWarnings("unused")
	private DWWriter(){}
	
	public DWWriter(String userName, String password, TimeZone timeZone, Boolean postPrivately) {
		super(userName, password, timeZone, postPrivately);
		this.serverURL =  "http://www.dreamwidth.org/interface/xmlrpc";
	}
}
