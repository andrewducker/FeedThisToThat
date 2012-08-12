package feedthistothat.DataTypes;

import java.util.Vector;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;

import feedthistothat.Readers.BaseReader;
import feedthistothat.Writers.BaseWriter;

@Cached
@Unindexed
@Subclass
public class FeedInstruction extends BaseSaveable {
	@Indexed
	private Vector<Long> readerIds = new Vector<Long>(); 
	@Indexed
	private Long writerId;

	public Vector<BaseReader> getReaders(Saveables saveables) {
		return saveables.getSubsetOfClass(BaseReader.class, readerIds);
	}
	public void setWriter(BaseWriter writer) {
		this.writerId = writer.getId();
	}
	public BaseWriter getWriter(Saveables saveables) {
		return saveables.getSubsetOfClass(BaseWriter.class, writerId);
	}
	
	public void Add(BaseReader reader){
		if (!readerIds.contains(reader.getId())) {
			readerIds.add(reader.getId());
		}
	}
}
