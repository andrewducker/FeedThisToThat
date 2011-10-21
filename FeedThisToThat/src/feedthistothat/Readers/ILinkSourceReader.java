package feedthistothat.Readers;

import feedthistothat.DataTypes.LinkSet;

public interface ILinkSourceReader {
	public LinkSet Read() throws Exception;
}
