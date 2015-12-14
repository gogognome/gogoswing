package nl.gogognome.lib.gui.beans;

import javax.swing.Action;

import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.DoubleModel;
import nl.gogognome.lib.swing.models.FileModel;
import nl.gogognome.lib.swing.models.ListModel;
import nl.gogognome.lib.swing.models.StringModel;
import nl.gogognome.lib.util.Factory;

/**
 * This class is a factory for creating beans.
 */
public class BeanFactory {
	public BeanFactory() {
	}

    /**
     * Creates a check box bean for the specified model.
     * @param model the model
     * @return the check box bean
     */
    public CheckBoxBean createCheckBoxBean(BooleanModel model) {
    	CheckBoxBean bean = new CheckBoxBean(model);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a radio button bean for the specified model.
     * @param id the id of the text
     * @param model the model
     * @return the radio button bean
     */
    public RadioButtonBean createRadioButtonBean(String id, BooleanModel model) {
    	RadioButtonBean bean = new RadioButtonBean(model);
    	WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
    	Action action = wf.createAction(id);
    	bean.setAction(action);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a file selection bean for the specified model.
     * @param model the model
     * @return the file selection bean
     */
    public FileSelectionBean createFileSelectionBean(FileModel model) {
    	FileSelectionBean bean = new FileSelectionBean(model);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a combo box bean for the specified model.
     * @param model the model
     * @return the combo box bean
     */
    public <T> ComboBoxBean<T> createComboBoxBean(ListModel<T> model) {
    	ComboBoxBean<T> bean = new ComboBoxBean<T>(model);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a text field bean for the specified string model.
     * @param model the string model
     * @return the text field bean
     */
    public TextFieldBean createTextFieldBean(StringModel model) {
    	TextFieldBean bean = new TextFieldBean(model);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a text field bean for the specified string model.
     * @param model the string model
     * @param nrColumns the number of columns
     * @return the text field bean
     */
    public TextFieldBean createTextFieldBean(StringModel model, int nrColumns) {
    	TextFieldBean bean = new TextFieldBean(model, nrColumns);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a password bean for the specified string model.
     * @param model the string model
     * @param nrColumns the number of columns
     * @return the password bean
     */
	public PasswordBean createPasswordBean(StringModel model, int nrColumns) {
    	PasswordBean bean = new PasswordBean(model, nrColumns);
    	bean.initBean();
    	return bean;
	}

    /**
     * Creates a double field bean for the specified double model.
     * @param model the double model
     * @return the double field bean
     */
    public DoubleFieldBean createDoubleFieldBean(DoubleModel model) {
    	DoubleFieldBean bean = new DoubleFieldBean(model);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a double field bean for the specified double model.
     * @param model the double model
     * @param nrColumns the number of columns
     * @return the text field bean
     */
    public DoubleFieldBean createDoubleFieldBean(DoubleModel model, int nrColumns) {
    	DoubleFieldBean bean = new DoubleFieldBean(model, nrColumns);
    	bean.initBean();
    	return bean;
    }

    /**
     * Creates a date selection bean for the specified date model.
     * @param model the date model
     * @return the date field bean
     */
    public DateSelectionBean createDateSelectionBean(DateModel model) {
    	DateSelectionBean bean = new DateSelectionBean(model);
    	bean.initBean();
    	return bean;
    }
}
