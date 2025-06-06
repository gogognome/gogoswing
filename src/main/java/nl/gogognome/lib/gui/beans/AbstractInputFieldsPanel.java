package nl.gogognome.lib.gui.beans;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.*;
import nl.gogognome.lib.util.Factory;

/**
 * Base class for the InputFieldsColumn and InputFieldsRow.
 */
public abstract class AbstractInputFieldsPanel extends JPanel implements Closeable {

	private static final long serialVersionUID = 1L;

	protected List<Component> components = new ArrayList<>();
    protected BeanFactory beanFactory = Factory.getInstance(BeanFactory.class);

    public AbstractInputFieldsPanel() {
        super(new GridBagLayout());
        setOpaque(false);
    }

    /**
     * This method should be called after the view containing this panel is closed.
     * It releases its resources.
     */
    @Override
	public void close() {
    	for (Component c : components) {
    		if (c instanceof Closeable) {
    			((Closeable) c).close();
    		}
    	}
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addReadonlyField(String labelId, StringModel model) {
        addVariableSizeField(labelId, beanFactory.createLabel(model).getComponent());
    }


    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, StringModel model) {
        addVariableSizeField(labelId, beanFactory.createTextFieldBean(model).getComponent());
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addField(String labelId, StringModel model, int nrColumns) {
        addVariableSizeField(labelId, beanFactory.createTextFieldBean(model, nrColumns).getComponent());
    }

    /**
     * Adds a text area to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text area
     */
    public void addTextArea(String labelId, StringModel model) {
        addVariableSizeField(labelId, beanFactory.createTextAreaBean(model).getComponent());
    }

    /**
     * Adds a text area to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     * @param nrRows the height of the text field as the number of rows.
     *        The value 0 indicates that the height can be determined by the layout manager.
     */
    public void addTextArea(String labelId, StringModel model, int nrColumns, int nrRows) {
        addVariableSizeField(labelId, beanFactory.createTextAreaBean(model, nrColumns, nrRows).getComponent());
    }

    /**
     * Adds a field to edit a double.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DoubleModel model) {
        addVariableSizeField(labelId, beanFactory.createDoubleFieldBean(model).getComponent());
    }

    /**
     * Adds a field to edit a double.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addField(String labelId, DoubleModel model, int nrColumns) {
        addVariableSizeField(labelId, beanFactory.createDoubleFieldBean(model, nrColumns).getComponent());
    }

   /**
     * Adds a field to edit an integer.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, IntegerModel model) {
        addVariableSizeField(labelId, beanFactory.createIntegerFieldBean(model).getComponent());
    }

    /**
     * Adds a field to edit an integer.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addField(String labelId, IntegerModel model, int nrColumns) {
        addVariableSizeField(labelId, beanFactory.createIntegerFieldBean(model, nrColumns).getComponent());
    }

    /**
     * Adds a field to edit a password.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addPasswordField(String labelId, StringModel model, int nrColumns) {
        addVariableSizeField(labelId, beanFactory.createPasswordBean(model, nrColumns).getComponent());
    }

    /**
     * Adds a check box.
     * @param labelId the id of the label that is put in front of the check box
     * @param model the model controlling the check box
     */
    public void addField(String labelId, BooleanModel model) {
        addVariableSizeField(labelId, beanFactory.createCheckBoxBean(model).getComponent());
    }

    /**
     * Adds a field to select a file.
     * @param labelId the id of the label that is put in front of the file selection bean.
     * @param model the model controlling the file seleciton bean
     */
    public void addField(String labelId, FileModel model) {
        addVariableSizeField(labelId, beanFactory.createFileSelectionBean(model).getComponent());
    }

    /**
     * Adds a component. Use this method to add components for which the general
     * models (StringModel, DateModel etc.) cannot be used.
     * @param labelId the id of the label that is put in front of the component
     * @param component the component
     */
    public void addVariableSizeField(String labelId, JComponent component) {
        addLabelAndFieldWithConstraints(labelId, component, getVariableSizeFieldConstraints());
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DateModel model) {
        addLabelAndFieldWithConstraints(labelId, beanFactory.createDateSelectionBean(model).getComponent(),
        		getFixedSizeFieldConstraints());
    }

    /**
     * Adds a combo box to select a single item from a list of items.
     * @param labelId the id of the label that is put in front of the combo box
     * @param model the model controlling the combo box
     */
    public <T> void addComboBoxField(String labelId, ListModel<T> model, ObjectFormatter<T> itemFormatter) {
    	Bean bean = beanFactory.createComboBoxBean(model, itemFormatter);
        addLabelAndFieldWithConstraints(labelId, bean.getComponent(), getFixedSizeFieldConstraints());
    }

    protected void addLabelAndFieldWithConstraints(String labelId, JComponent component, GridBagConstraints constraints) {
        JLabel label = Factory.getInstance(WidgetFactory.class).createLabel(labelId, component);
        add(label, getLabelConstraints());
        add(component, constraints);
        components.add(component);
    }

	protected abstract GridBagConstraints getLabelConstraints();
	protected abstract GridBagConstraints getFixedSizeFieldConstraints();
	protected abstract GridBagConstraints getVariableSizeFieldConstraints();

    @Override
    public void requestFocus() {
    	if (!components.isEmpty()) {
    		components.get(0).requestFocus();
    	}
    }

    @Override
    public boolean requestFocusInWindow() {
    	if (!components.isEmpty()) {
    		return components.get(0).requestFocusInWindow();
    	} else {
    		return false;
    	}
    }
}
