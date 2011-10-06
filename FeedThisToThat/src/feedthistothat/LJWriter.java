package feedthistothat;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class LJWriter implements IWriter {

	private String userName;
	private String password;
	private TimeZone timeZone;
	public LJWriter(String userName, String password, TimeZone timeZone){
		this.userName = userName;
		this.password = password;
		this.timeZone = timeZone;
	}
	
	protected String serverURL = "http://www.livejournal.com/interface/xmlrpc";
	
	
	@SuppressWarnings("unchecked")
	public String Write(String contents, String header)  throws Exception{
	    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(serverURL));
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
	    
		Map<String, String> result =  (Map<String, String>) client.execute("LJ.XMLRPC.getchallenge", new Object[0]);
	    
	    String challenge = (String) result.get("challenge");
	    
	    String response = MD5Hex(challenge+MD5Hex(password));

	    Calendar calendar = Calendar.getInstance(timeZone);
	    
	    HashMap<String,Object> postParams = new HashMap<String,Object>();
	    postParams.put("username", userName);
	    postParams.put("auth_method", "challenge");
	    postParams.put("auth_challenge", challenge);
	    postParams.put("auth_response", response);
	    
	    postParams.put("event", contents);
	    postParams.put("subject", header) ;
	    postParams.put("security","private");

	    postParams.put("year",calendar.get(Calendar.YEAR));
	    postParams.put("mon",calendar.get(Calendar.MONTH)+1);
	    postParams.put("day",calendar.get(Calendar.DAY_OF_MONTH));
	    postParams.put("hour",calendar.get(Calendar.HOUR_OF_DAY));
	    postParams.put("min",calendar.get(Calendar.MINUTE));
	    
	    HashMap<String,Object> options = new HashMap<String,Object>();
	    options.put("taglist", "links");
	    options.put("opt_preformatted", true);
	    postParams.put("props",options);
	    
	    Object[] params = new Object[]{postParams};
		result =  (Map<String, String>) client.execute("LJ.XMLRPC.postevent", params);
	    if (result.get("success")=="FAIL"){
	    	return result.get("errmsg");
	    }
	    return "<A href=" + result.get("url")+ ">Link posted</A>";
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
