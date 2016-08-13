package nl.gogognome.lib.swing.models;

/**
 * This class implements a model for a String.
 */
public class StringModel extends AbstractModel<String> {

    private boolean mustBeFilled;

    public String getString() {
        return getValue();
    }

    /**
     * Constructs a string model without initial value.
     */
    public StringModel() {
    }

    /**
     * Constructs a string model.
     * @param initialValue the inital value of the string model.
     */
    public StringModel(String initialValue) {
        setString(initialValue);
    }

    public StringModel mustBeFilled(boolean mustBeFilled) {
        this.mustBeFilled = mustBeFilled;
        return this;
    }

    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     */
    public StringModel setString(String newString) {
    	setValue((newString));
        return this;
    }

    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     * @param source the model change listener that sets the double. It will not get notified. It may be null.
     */
    public StringModel setString(String newString, ModelChangeListener source) {
        setValue(newString, source);
        return this;
    }

    @Override
    public boolean validate() {
        errorResourceIds.clear();
        if (mustBeFilled && (getValue() == null || getValue().isEmpty())) {
            errorResourceIds.add("validation.fieldMustBeFilledIn");
        }
        notifyListeners(null);
        return errorResourceIds.isEmpty();
    }
}
