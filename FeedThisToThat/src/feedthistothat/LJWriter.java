package feedthistothat;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class LJWriter {

	private String userName;
	private String password;
	private TimeZone timeZone;
	public LJWriter(String userName, String password, String timeZoneID){
		this.userName = userName;
		this.password = password;
		this.timeZone = TimeZone.getTimeZone(timeZoneID);
	}
	
	@SuppressWarnings("unchecked")
	public String Write(String contents) throws MalformedURLException, XmlRpcException, NoSuchAlgorithmException{
	    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://www.livejournal.com/interface/xmlrpc"));
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
	    
		Map<String, String> result =  (Map<String, String>) client.execute("LJ.XMLRPC.getchallenge", new Object[0]);
	    
	    String challenge = (String) result.get("challenge");
	    
	    String response = MD5Hex(challenge+MD5Hex(password));
	    
	    HashMap<String,Object> login = new HashMap<String,Object>();
	    login.put("username", userName);
	    login.put("auth_method", "challenge");
	    login.put("auth_challenge", challenge);
	    login.put("auth_response", response);
	    
	    login.put("event", contents);
	    login.put("subject", "Test Delicious Bookmarks");
	    login.put("security","private");

	    Calendar calendar = Calendar.getInstance(timeZone);
	    
	    login.put("year",calendar.get(Calendar.YEAR));
	    login.put("mon",calendar.get(Calendar.MONTH)+1);
	    login.put("day",calendar.get(Calendar.DAY_OF_MONTH));
	    login.put("hour",calendar.get(Calendar.HOUR_OF_DAY));
	    login.put("min",calendar.get(Calendar.MINUTE));
	    
	    Object[] params = new Object[]{login};
		result =  (Map<String, String>) client.execute("LJ.XMLRPC.postevent", params);
	    if (result.get("success")=="FAIL"){
	    	return result.get("errmsg");
	    }
	    return result.get("url");
	}
	
	private static String MD5Hex(String s) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(s.getBytes());
        return toHex(digest);
	}
    public static String toHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (int i = 0; i < a.length; i++) {
            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(a[i] & 0x0f, 16));
        }
        return sb.toString();
    }
}
