package nl.gogognome.lib.gui.beans;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import nl.gogognome.lib.swing.SwingUtils;

/**
 * This class implements a panel containing a row of input fields.
 * Each input field consists of a label and a component (typically a text field).
 * The values of the fields are managed by models (for example, StringModel
 * and DateModel).
 */
public class InputFieldsRow extends AbstractInputFieldsPanel {

    private static final long serialVersionUID = 1L;

    public InputFieldsRow() {
        super();

        // Add component to push the input fields to the left
        add(new JLabel(), SwingUtils.createTextFieldGBConstraints(1000, 0));
    }

	@Override
	protected GridBagConstraints getLabelConstraints() {
		GridBagConstraints gbc = SwingUtils.createLabelGBConstraints(2 * components.size(), 0);
		if (!components.isEmpty()) {
			gbc.insets.left += 10;
		}
		return gbc;
	}

	@Override
	protected GridBagConstraints getFixedSizeFieldConstraints() {
		return SwingUtils.createLabelGBConstraints(2 * components.size() + 1, 0);
	}

	@Override
	protected GridBagConstraints getVariableSizeFieldConstraints() {
		return SwingUtils.createTextFieldGBConstraints(2 * components.size() + 1, 0);
	}
}
