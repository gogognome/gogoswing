package nl.gogognome.lib.gui.beans;

import java.awt.GridBagConstraints;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.*;

/**
 * This class implements a panel containing a column of input fields.
 * Each input field consists of a label and a component (typically a text field).
 * The values of the fields are managed by models (for example, StringModel
 * and DateModel).
 */
public class InputFieldsColumn extends AbstractInputFieldsPanel {

    private static final long serialVersionUID = 1L;

    public InputFieldsColumn() {
        super();
    }

	@Override
	public void addField(String labelId, DateModel model) {
		// Use variable size field constraints instead of fixed size field constraints to make it work in
		// an InputFIeldsColumn.
		addLabelAndFieldWithConstraints(
				labelId,
				beanFactory.createDateSelectionBean(model).getComponent(),
				getVariableSizeFieldConstraints());
	}

	@Override
	protected GridBagConstraints getLabelConstraints() {
		return SwingUtils.createLabelGBConstraints(0, components.size());
	}

	@Override
	protected GridBagConstraints getFixedSizeFieldConstraints() {
		return SwingUtils.createLabelGBConstraints(1, components.size());
	}

	@Override
	protected GridBagConstraints getVariableSizeFieldConstraints() {
		return SwingUtils.createTextFieldGBConstraints(1, components.size());
	}
}
