package feedthistothat.Readers;

import java.util.List;

import feedthistothat.DataTypes.LinkEntry;

public interface ILinkSourceReader {
	public List<LinkEntry> Read() throws Exception;
}
