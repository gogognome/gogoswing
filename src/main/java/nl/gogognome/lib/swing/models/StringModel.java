package nl.gogognome.lib.swing.models;

import nl.gogognome.lib.util.ComparatorUtil;

/**
 * This class implements a model for a <code>String</code>.
 */
public class StringModel extends AbstractModel {

    private String string;

    public String getString() {
        return string;
    }

    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     */
    public void setString(String newString) {
    	setString(newString, null);
    }

    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     * @param source the model change listener that sets the string. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setString(String newString, ModelChangeListener source) {
        String oldString = this.string;
        if (!ComparatorUtil.equals(oldString, newString)) {
            this.string = newString;
            notifyListeners(source);
        }
    }
}
