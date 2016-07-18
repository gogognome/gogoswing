package nl.gogognome.lib.swing.models;


/**
 * This class implements a model for a boolean.
 */
public class BooleanModel extends AbstractModel<Boolean> {

    public BooleanModel() {
        super(false);
    }

    public boolean getBoolean() {
        return getValue();
    }

    /**
     * Sets the boolean of this model.
     * @param newValue the new value of the boolean
     */
    public BooleanModel setBoolean(boolean newValue) {
    	setValue(newValue);
        return this;
    }

    /**
     * Sets the boolean of this model.
     * @param newValue the new value of the boolean
     * @param source the model change listener that sets the boolean. It will not get notified. It may be null.
     */
    public BooleanModel setBoolean(boolean newValue, ModelChangeListener source) {
        setValue(newValue, source);
        return this;
    }
}
