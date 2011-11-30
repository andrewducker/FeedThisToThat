package feedthistothat.Writers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.jasypt.util.text.BasicTextEncryptor;

public class MetaWeblogAPI implements IWriter {

	private String userName;
	private String password;
	private String url;
	private String blogID;

	public MetaWeblogAPI(String userName, String password, String url, String blogID) throws Exception{
		this.userName = userName;
		this.password = password;
		if(url != null){
			if(!url.substring(0,4).equals("http")){
				url = "http://"+url;
			}
			if(!url.contains("xmlrpc.php")){
				if(!url.substring(url.length()-1).equals("/")){
					url += "/";
				}
				url = url+"xmlrpc.php";
			}
			this.url = url;
		}
		this.blogID = blogID;
	}


	@Override
	public String Write(String contents, String header, List<String> tags)
	throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(url));
		config.setConnectionTimeout(20);
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		HashMap<String,Object> postParams = new HashMap<String,Object>();
		postParams.put("title", header);
		postParams.put("description",contents);
		Object[] params = new Object[]{blogID,userName,DecryptPassword(password),postParams,true};
		String ret = (String) client.execute("metaWeblog.newPost", params);

		return ret;
	}

	@Override
	public String EncryptPassword(String password) throws Exception {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("Test");
		return textEncryptor.encrypt(password);
	}

	public String DecryptPassword(String encryptedPassword){
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("Test");
		return textEncryptor.decrypt(encryptedPassword);
	}

}
