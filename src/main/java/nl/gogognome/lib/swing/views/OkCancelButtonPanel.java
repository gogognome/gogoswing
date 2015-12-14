package nl.gogognome.lib.swing.views;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import nl.gogognome.lib.swing.ButtonPanel;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.util.Factory;

/**
 * Button panel containing an ok and cancel button.
 */
public class OkCancelButtonPanel extends ButtonPanel {

	private static final long serialVersionUID = 1L;

	private JButton okButton;

	/**
	 * Constructor.
	 * @param okAction the action that will be attached to the ok button
	 * @param cancelAction the action that will be attached to the cancel button
	 */
	public OkCancelButtonPanel(Action okAction, Action cancelAction) {
		super(SwingConstants.CENTER);
		addComponents(okAction, cancelAction);
	}

	private void addComponents(Action okAction, Action cancelAction) {
		WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
		okButton = wf.createButton("gen.ok", okAction);
		add(okButton);
		add(wf.createButton("gen.cancel", cancelAction));
	}

	public JButton getDefaultButton() {
		return okButton;
	}
}
