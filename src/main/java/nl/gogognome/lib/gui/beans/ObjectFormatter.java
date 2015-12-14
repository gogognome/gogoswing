package nl.gogognome.lib.gui.beans;

/**
 * An object formatter generates string representations
 * for instances of type T.
 *
 * @param T the type of the objects to be formatted
 */
public interface ObjectFormatter<T> {

	/**
	 * Gets a string representation of the object t.
	 * @param t the object (can be null)
	 * @return the string representation
	 */
	public String format(T t);
}
