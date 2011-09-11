package feedthistothat;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class LJWriter {

	private String userName;
	private String password;
	public LJWriter(String userName, String password){
		this.userName = userName;
		this.password = password;
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

	    login.put("year",2011);
	    login.put("mon",9);
	    login.put("day",11);
	    login.put("hour",21);
	    login.put("min",57);
	    
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
