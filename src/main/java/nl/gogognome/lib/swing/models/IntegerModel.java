package nl.gogognome.lib.swing.models;

/**
 * This class implements a model for a Integer.
 */
public class IntegerModel extends AbstractModel<Integer> {

    /**
     * Gets the value of the model.
     * @return the value (possibly null)
     */
    public Integer getInteger() {
        return getValue();
    }

    /**
     * Sets the integer of this model.
     * @param newInteger the new value of the integer (null is allowed)
     */
    public IntegerModel setInteger(Integer newInteger) {
        setValue(newInteger);
        return this;
    }

    /**
     * Sets the integer of this model.
     * @param newValue the new value of the integer
     * @param source the model change listener that sets the integer. It will not get notified. It may be null.
     */
    public IntegerModel setInteger(Integer newValue, ModelChangeListener source) {
        setValue(newValue, source);
        return this;
    }

}
