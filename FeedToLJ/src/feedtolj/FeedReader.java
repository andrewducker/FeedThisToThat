package feedtolj;

import java.io.IOException;
import java.net.URL;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class FeedReader {

	public static byte[] Read(String feedURL) throws IOException
	{
		URL url = new URL(feedURL);
		HTTPRequest httpRequest = new HTTPRequest(url);
		HTTPHeader header = new HTTPHeader("Cache-Control", "max-age=300");
		httpRequest.setHeader(header);
		
		URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
		HTTPResponse response = fetchService.fetch(httpRequest);
		
		byte[] responseContent = response.getContent();
		
        return responseContent;
	}
}
