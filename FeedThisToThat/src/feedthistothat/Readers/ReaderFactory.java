package feedthistothat.Readers;

public class ReaderFactory {
	public enum ReaderType {
		Pinboard, Delicious, Test
	}

	public static BaseReader GetReader(ReaderType reader, String userName, String emailAddress)
			throws Exception {
		BaseReader toReturn;
		switch (reader) {
		case Delicious:
			toReturn = new DeliciousReader(userName);
			break;
		case Pinboard:
			toReturn = new PinboardReader(userName);
			break;
		case Test:
			toReturn = new TestReader();
			break;
		default:
			throw new Exception("No such destination - " + reader.toString());
		}
		toReturn.setEmailAddress(emailAddress);
		return toReturn;
	}
}