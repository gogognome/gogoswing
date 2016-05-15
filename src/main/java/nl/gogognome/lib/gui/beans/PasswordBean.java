package nl.gogognome.lib.gui.beans;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import nl.gogognome.lib.swing.models.StringModel;
import nl.gogognome.lib.text.TextResource;

/**
 * This class implements a bean for password entry.
 */
public class PasswordBean extends TextFieldBean {

	private static final long serialVersionUID = 1L;

	public PasswordBean(StringModel stringModel, TextResource textResource) {
		super(stringModel);
	}

	public PasswordBean(StringModel stringModel, int nrColumns) {
		super(stringModel, nrColumns);
	}

	@Override
	protected JTextField createTextField(int nrColumns) {
		return new JPasswordField(nrColumns);
	}
}
