package nl.gogognome.lib.swing.models;

import java.util.Date;

import nl.gogognome.lib.util.ComparatorUtil;

/**
 * This class implements a model for a <code>Date</code>.
 */
public class DateModel extends AbstractModel {

    private Date date;

    public DateModel() {
    	this(null);
    }

    public DateModel(Date date) {
    	this.date = date;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     */
    public void setDate(Date newDate) {
    	setDate(newDate, null);
    }

    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     * @param source the model change listener that sets the date. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setDate(Date newDate, ModelChangeListener source) {
        Date oldDate = this.date;
        if (!ComparatorUtil.equals(oldDate, newDate)) {
            this.date = newDate;
            notifyListeners(source);
        }
    }
}
