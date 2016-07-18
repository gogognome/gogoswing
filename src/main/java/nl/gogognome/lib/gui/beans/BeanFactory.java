package nl.gogognome.lib.gui.beans;

import javax.swing.*;

import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.DoubleModel;
import nl.gogognome.lib.swing.models.FileModel;
import nl.gogognome.lib.swing.models.ListModel;
import nl.gogognome.lib.swing.models.StringModel;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

/**
 * This class is a factory for creating beans.
 */
public class BeanFactory {

    private final TextResource textResource;

    public BeanFactory(TextResource textResource) {
        this.textResource = textResource;
    }

    /**
     * Creates a check box bean for the specified model.
     * @param model the model
     * @return the check box bean
     */
    public Bean createCheckBoxBean(BooleanModel model) {
        Bean bean = new ErrorMessageDecorator(new CheckBoxBean(model), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a radio button bean for the specified model.
     * @param id the id of the text
     * @param model the model
     * @return the radio button bean
     */
    public Bean createRadioButtonBean(String id, BooleanModel model) {
        RadioButtonBean radioButtonBean = new RadioButtonBean(model);
        WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
        Action action = wf.createAction(id);
        radioButtonBean.setAction(action);

        Bean bean = new ErrorMessageDecorator(radioButtonBean, model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a file selection bean for the specified model.
     * @param model the model
     * @return the file selection bean
     */
    public Bean createFileSelectionBean(FileModel model) {
        Bean bean = new ErrorMessageDecorator(new FileSelectionBean(model), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a combo box bean for the specified model.
     * @param model the model
     * @return the combo box bean
     */
    public <T> Bean createComboBoxBean(ListModel<T> model) {
        ComboBoxBean<T> comboBoxBean = new ComboBoxBean<>(model);
        Bean bean = new ErrorMessageDecorator(comboBoxBean, model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a combo box bean for the specified model.
     * @param model the model
     * @param itemFormatter generates a string for each item of the combo box
     * @return the combo box bean
     */
    public <T> Bean createComboBoxBean(ListModel<T> model, ObjectFormatter<T> itemFormatter) {
        ComboBoxBean<T> comboBoxBean = new ComboBoxBean<>(model);
        comboBoxBean.setItemFormatter(itemFormatter);
        Bean bean = new ErrorMessageDecorator(comboBoxBean, model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a text field bean for the specified string model.
     * @param model the string model
     * @return the text field bean
     */
    public Bean createTextFieldBean(StringModel model) {
        Bean bean = new ErrorMessageDecorator(new TextFieldBean(model), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a right aligned text field bean for the specified string model.
     * @param model the string model
     * @return the text field bean
     */
    public Bean createRightAlignedTextFieldBean(StringModel model) {
        TextFieldBean textFieldBean = new TextFieldBean(model);
        textFieldBean.setHorizontalAlignment(JTextField.RIGHT);
        Bean bean = new ErrorMessageDecorator(textFieldBean, model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a text field bean for the specified string model.
     * @param model the string model
     * @param nrColumns the number of columns
     * @return the text field bean
     */
    public Bean createTextFieldBean(StringModel model, int nrColumns) {
        Bean bean = new ErrorMessageDecorator(new TextFieldBean(model, nrColumns), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a password bean for the specified string model.
     * @param model the string model
     * @param nrColumns the number of columns
     * @return the password bean
     */
    public Bean createPasswordBean(StringModel model, int nrColumns) {
        Bean bean = new ErrorMessageDecorator(new PasswordBean(model, nrColumns), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a double field bean for the specified double model.
     * @param model the double model
     * @return the double field bean
     */
    public Bean createDoubleFieldBean(DoubleModel model) {
        Bean bean = new ErrorMessageDecorator(new DoubleFieldBean(model), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a double field bean for the specified double model.
     * @param model the double model
     * @param nrColumns the number of columns
     * @return the text field bean
     */
    public Bean createDoubleFieldBean(DoubleModel model, int nrColumns) {
        Bean bean = new ErrorMessageDecorator(new DoubleFieldBean(model, nrColumns), model, textResource);
        bean.initBean();
        return bean;
    }

    /**
     * Creates a date selection bean for the specified date model.
     * @param model the date model
     * @return the date field bean
     */
    public Bean createDateSelectionBean(DateModel model) {
        Bean bean = new ErrorMessageDecorator(new DateSelectionBean(model), model, textResource);
        bean.initBean();
        return bean;
    }
}
