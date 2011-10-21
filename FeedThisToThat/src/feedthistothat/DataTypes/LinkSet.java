package feedthistothat.DataTypes;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("serial")
public class LinkSet extends Vector<LinkEntry> {

	public List<String> getTagNames() {
		List<String> tags = new Vector<String>();

		for (LinkEntry entry : this) {
			for (LinkTag linkTag : entry.getTags()) {
				if (!tags.contains(linkTag.getTag())) {
					tags.add(linkTag.getTag());
				}
			}
		}
		Collections.sort(tags);
		return tags;
	}

}
