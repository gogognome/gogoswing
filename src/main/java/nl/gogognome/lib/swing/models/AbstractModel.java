package nl.gogognome.lib.swing.models;

import java.util.LinkedList;

/**
 * This class is the base class for all models. It maintains the list of listeners.
 *
 * <p>A model can be disabled or enabled. By default it is enabled. Disabling the model
 * has no effect on the model itself, but a disabled model that is linked to a bean
 * will disable the input component of the bean to prevent the user from changing the model.
 * The disabled model can still be changed programmatically.
 */
public class AbstractModel {

	private boolean enabled = true;

    /** Contains the subscribed listeners. */
    private LinkedList<ModelChangeListener> listeners = new LinkedList<ModelChangeListener>();

    /**
     * Adds a model change listener to this model.
     * @param listener the listener
     */
    public void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a model change listener from this model.
     * @param listener the listener
     */
    public void removeModelChangeListener(ModelChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Enables or disables the model. Notifies all listeners about the change.
     * @param enabled true to enabled; false to disable
     * @param source if not <code>null</code>, then this indicates the
     *         listener that initiated this notification. If the listener
     *         is subcribed, it will not get notified by this method.
     */
    public void setEnabled(boolean enabled, ModelChangeListener source) {
    	this.enabled = enabled;
    	notifyListeners(source);
    }

    /**
     * Checks whether this model is enabled.
     * @return true if this model is enabled; false if this model is disabled
     */
    public boolean isEnabled() {
		return enabled;
	}

    /**
     * Notifies the subscribed listeners about a change in this model.
     * @param source if not <code>null</code>, then this indicates the
     *         listener that initiated this notification. If the listener
     *         is subcribed, it will not get notified by this method.
     */
    protected void notifyListeners(ModelChangeListener source) {
        for (ModelChangeListener listener : listeners) {
            if (listener != source) {
                listener.modelChanged(this);
            }
        }
    }
}
