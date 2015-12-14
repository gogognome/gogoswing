package nl.gogognome.lib.swing.models;


/**
 * This class implements a model for a <code>Boolean</code>.
 */
public class BooleanModel extends AbstractModel {

    private boolean value;

    public boolean getBoolean() {
        return value;
    }

    /**
     * Sets the boolean of this model.
     * @param newValue the new value of the boolean
     */
    public void setBoolean(boolean newValue) {
    	setBoolean(newValue, null);
    }

    /**
     * Sets the boolean of this model.
     * @param newValue the new value of the boolean
     * @param source the model change listener that sets the boolean. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setBoolean(boolean newValue, ModelChangeListener source) {
        boolean oldValue = this.value;
        if (oldValue != newValue) {
            this.value = newValue;
            notifyListeners(source);
        }
    }
}
