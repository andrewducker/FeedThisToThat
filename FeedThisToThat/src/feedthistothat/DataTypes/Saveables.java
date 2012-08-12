package feedthistothat.DataTypes;

import java.util.List;
import java.util.Vector;

@SuppressWarnings("serial")
public class Saveables extends Vector<BaseSaveable> {

	@SuppressWarnings("unchecked")
	public <T> Vector<T> getSubsetOfClass(Class<T> cls)
	{
		Vector<T> toReturn = new Vector<T>();
		for (BaseSaveable saveable : this) {
			if (cls.isAssignableFrom(saveable.getClass())) {
				toReturn.add((T)saveable);
			}
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	public <T> Vector<T> getSubsetOfClass(Class<T> cls, List<Long> ids)
	{
		Vector<T> toReturn = new Vector<T>();
		for (BaseSaveable saveable : this) {
			if (cls.isAssignableFrom(saveable.getClass()))
				if(ids.contains(saveable.getId())) {
				toReturn.add((T)saveable);
			}
		}
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSubsetOfClass(Class<T> cls, Long id)
	{
		for (BaseSaveable saveable : this) {
			if (cls.isAssignableFrom(saveable.getClass()) && saveable.getId() == id ) {
				return (T)saveable;
			}
		}
		return null;
	}

}
