package nl.gogognome.lib.swing.models;

import java.util.Date;

/**
 * This class implements a model for a Date.
 */
public class DateModel extends AbstractModel<Date> {

    public DateModel() {
    }

    public DateModel(Date date) {
    	setDate(date);
    }

    public Date getDate() {
        return getValue();
    }

    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     */
    public DateModel setDate(Date newDate) {
    	setValue(newDate);
        return this;
    }

    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     * @param source the model change listener that sets the date. It will not get notified. It may be null.
     */
    public DateModel setDate(Date newDate, ModelChangeListener source) {
        setValue(newDate, source);
        return this;
    }
}
