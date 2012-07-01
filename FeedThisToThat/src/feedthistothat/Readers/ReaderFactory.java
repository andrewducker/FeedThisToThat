package feedthistothat.Readers;

public class ReaderFactory {
	public enum ReaderType {
		Pinboard, Delicious, Test
	}

	public static BaseReader GetReader(ReaderType reader, String userName)
			throws Exception {
		switch (reader) {
		case Delicious:
			return new DeliciousReader(userName);
		case Pinboard:
			return new PinboardReader(userName);
		case Test:
			return new TestReader();
		default:
			throw new Exception("No such destination - " + reader.toString());
		}
	}
}