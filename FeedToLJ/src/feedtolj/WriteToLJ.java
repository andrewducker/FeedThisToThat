package feedtolj;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class WriteToLJ {

	public void Write(){
	    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    try {
			config.setServerURL(new URL("http://www.livejournal.com/interface/xmlrpc"));
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
	    HashMap login = new HashMap();
	    login.put("username", "AndrewDucker");
	    login.put("password", "Balisk!");
	    
	    Object[] params = new Object[]{login};
	    Map result =  (Map) client.execute("LJ.XMLRPC.login", params);
	    System.out.println(result.get("fullname"));
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
