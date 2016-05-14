package nl.gogognome.lib.gui.beans;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.views.ViewPopup;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

/**
 * This class implements a bean for selecting a Date.
 */
public class DateSelectionBean extends AbstractTextFieldBean<DateModel> {

	private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param dateModel the date model that will reflect the content of the bean
     * @param textResource the text resource for obtaining error messages
     */
    protected DateSelectionBean(DateModel dateModel, TextResource textResource) {
    	super(dateModel, 10, textResource);
    }

    @Override
    public void initBean() {
    	super.initBean();
    	WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
    	JButton button = wf.createIconButton("gen.btnCalendar", new ShowCalendarPopupAction(), 21);
    	add(button);
    }

	@Override
    protected String getStringFromModel() {
        Date date = model.getDate();
        if (date != null) {
            return Factory.getInstance(TextResource.class).formatDate("gen.dateFormat", date);
        } else {
            return "";
        }
    }

    @Override
    protected void parseUserInput(String text,
    		ModelChangeListener modelChangeListener) throws ParseException {
    	Date date = null;
        try {
            date = Factory.getInstance(TextResource.class).parseDate("gen.dateFormat", text);
        } finally {
        	// Set the date in the finally block. This ensures that the
        	// model is cleared in case a ParseException is thrown
            model.setDate(date, modelChangeListener);
        }
    }

	private void showCalendarPopup() {
		CalendarView calendarPanel = new CalendarView(model);
		ViewPopup viewPopup = new ViewPopup(calendarPanel);
		viewPopup.show(this, SwingUtils.getCoordinatesRelativeToTopLevelContainer(this));
	}

	private final class ShowCalendarPopupAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			showCalendarPopup();
		}
	}
}
