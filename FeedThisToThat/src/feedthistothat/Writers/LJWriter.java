package feedthistothat.Writers;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Id;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import feedthistothat.DataTypes.PasswordEncrypt;


@Unindexed
@Cached
@Entity
public class LJWriter extends BaseWriter {

	@Id @Indexed private Long id;
	@Indexed private String email;
	private String userName;
	private String password;
	private TimeZone timeZone;
	private Boolean postPrivately;
	public LJWriter(String userName, String password, TimeZone timeZone, Boolean postPrivately, String email){
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.timeZone = timeZone;
		this.postPrivately = postPrivately;
	}
	
	protected String serverURL = "http://www.livejournal.com/interface/xmlrpc";
	
	
	@SuppressWarnings("unchecked")
	public String Write(String contents, String header, List<String> tags)  throws Exception{
	    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(serverURL));
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
	    
		Map<String, String> result =  (Map<String, String>) client.execute("LJ.XMLRPC.getchallenge", new Object[0]);
	    
	    String challenge = (String) result.get("challenge");
	    
	    String response = PasswordEncrypt.MD5Hex(challenge+password);

	    Calendar calendar = Calendar.getInstance(timeZone);
	    
	    HashMap<String,Object> postParams = new HashMap<String,Object>();
	    postParams.put("username", userName);
	    postParams.put("auth_method", "challenge");
	    postParams.put("auth_challenge", challenge);
	    postParams.put("auth_response", response);
	    
	    postParams.put("event", contents);
	    postParams.put("subject", header) ;
	    if (postPrivately) {
		    postParams.put("security","private");
		}

	    postParams.put("year",calendar.get(Calendar.YEAR));
	    postParams.put("mon",calendar.get(Calendar.MONTH)+1);
	    postParams.put("day",calendar.get(Calendar.DAY_OF_MONTH));
	    postParams.put("hour",calendar.get(Calendar.HOUR_OF_DAY));
	    postParams.put("min",calendar.get(Calendar.MINUTE));
	    
	    HashMap<String,Object> options = new HashMap<String,Object>();
	    String tagsToUse = "";
	    for (String tag : tags) {
	    	if(tagsToUse.length()>0)
	    	{
	    		tagsToUse+=",";
	    	}
			tagsToUse +=tag; 
		}
	    options.put("taglist", tagsToUse);
	    options.put("opt_preformatted", true);
	    postParams.put("props",options);
	    
	    Object[] params = new Object[]{postParams};
	    try{
		result =  (Map<String, String>) client.execute("LJ.XMLRPC.postevent", params);
	    } catch (XmlRpcException e){
	    	if (e.getMessage().equals("Invalid password")){
	    		return "Invalid Password";
	    	} else{
	    		throw e;
	    	}
	    }
	    
	    if (result.get("success")=="FAIL"){
	    	return result.get("errmsg");
	    }
	    return "<A href=" + result.get("url")+ ">Link posted</A>";
	}
	
	public String EncryptPassword(String password) throws Exception{
		return PasswordEncrypt.MD5Hex(password);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
