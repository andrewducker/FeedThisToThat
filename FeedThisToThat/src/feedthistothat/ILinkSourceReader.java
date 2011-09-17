package feedthistothat;

import java.util.List;

public interface ILinkSourceReader {
	public List<LinkEntry> Read() throws Exception;
}
