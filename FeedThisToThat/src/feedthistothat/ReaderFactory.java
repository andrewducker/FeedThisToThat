package feedthistothat;

public class ReaderFactory {
	public enum Reader {
		Pinboard, Delicious
	}

	public static ILinkSourceReader GetReader(Reader reader, String userName)
			throws Exception {
		switch (reader) {
		case Delicious:
			return new DeliciousReader(userName);
		case Pinboard:
			return new PinboardReader(userName);
		default:
			throw new Exception("No such destination - " + reader.toString());
		}
	}
}
