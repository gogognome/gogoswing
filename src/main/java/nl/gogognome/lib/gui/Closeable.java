package nl.gogognome.lib.gui;

/**
 * This interface contains a method to close a component.
 */
public interface Closeable {

	/**
	 * Call this method to close the component. The component can free its resources.
	 */
	public void close();
}
