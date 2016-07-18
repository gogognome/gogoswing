package nl.gogognome.lib.swing.models;

/**
 * This class implements a model for a Double.
 */
public class DoubleModel extends AbstractModel<Double> {
    /**
     * Gets the value of the model.
     * @return the value (possibly null)
     */
    public Double getDouble() {
        return getValue();
    }

    /**
     * Sets the double of this model.
     * @param newDouble the new value of the double (null is allowed)
     */
    public DoubleModel setDouble(Double newDouble) {
    	setValue(newDouble);
        return this;
    }

    /**
     * Sets the double of this model.
     * @param newValue the new value of the double
     * @param source the model change listener that sets the double. It will not get notified. It may be null.
     */
    public DoubleModel setDouble(Double newValue, ModelChangeListener source) {
        setValue(newValue, source);
        return this;
    }
}
