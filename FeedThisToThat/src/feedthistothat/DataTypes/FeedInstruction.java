package feedthistothat.DataTypes;

import java.util.Vector;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Unindexed
public class FeedInstruction {
	@Id @Indexed private Long id;
	@Indexed private String emailAddress = "";
	private Vector<Long> readers = new Vector<Long>();
	private Long writer;
	
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public Vector<Long> getReaders() {
		return readers;
	}
	public void setWriter(Long writer) {
		this.writer = writer;
	}
	public Long getWriter() {
		return writer;
	}
}
