package nl.gogognome.lib.gui.beans;

import java.awt.*;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.text.TextResource;

/**
 * Base class for a text field bean. Make sure that after instantiation
 * first the method {@link #initBean()} is called.
 */
public abstract class AbstractTextFieldBean<M extends AbstractModel> extends JPanel
		implements Bean {

	private static final long serialVersionUID = 1L;

	protected M model;

    /** The text field in which the user can enter the string. */
    private JTextField textfield;

    private JLabel errorMessages;

    /** The listener for changes made programmatically. */
    private ModelChangeListener modelChangeListener;

    /** Listener for changes made by the user. */
    private DocumentListener documentListener;

    private final TextResource textResource;

    private int nrColumns;

    /**
     * Constructor.
     * @param model the model that will reflect the content of the bean
     */
    protected AbstractTextFieldBean(M model, TextResource textResource) {
    	this(model, 0, textResource);
    }

    /**
     * Constructor.
     * @param model the model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     * @param textResource the text resource for obtaining error messages
     */
    protected AbstractTextFieldBean(M model, int nrColumns, TextResource textResource) {
    	this.model = model;
    	this.nrColumns = nrColumns;
        this.textResource = textResource;
    }

    @Override
	public void initBean() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        textfield = createTextField(nrColumns);
        errorMessages = new JLabel();
        errorMessages.setForeground(Color.RED);

        updateTextField();
        modelChangeListener = new UpdateTextFieldOnModelChangeListener();
        model.addModelChangeListener(modelChangeListener);

        documentListener = new ParseUserInputOnDocumentChangeListener();
        textfield.getDocument().addDocumentListener(documentListener);

        add(textfield, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, nrColumns == 0 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE,
            0, 0, 0, 0));

        add(errorMessages, SwingUtils.createGBConstraints(1,0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                0, 0, 0, 0));
    }

    protected JTextField createTextField(int nrColumns) {
		return new JTextField(nrColumns);
	}

	/**
     * Deinitializes this bean. It will free its resources.
     */
    @Override
	public void close() {
        model.removeModelChangeListener(modelChangeListener);
        textfield.getDocument().removeDocumentListener(documentListener);
        modelChangeListener = null;
        documentListener = null;
        model = null;
        textfield = null;
        errorMessages = null;
    }

    @Override
	public void addFocusListener(FocusListener listener) {
        textfield.addFocusListener(listener);
    }

    @Override
	public void removeFocusListener(FocusListener listener) {
        textfield.removeFocusListener(listener);
    }

    /**
     * Updates the text field with the value of the model.
     */
    private void updateTextField() {
    	textfield.setEnabled(model.isEnabled());
        String string = getStringFromModel();
        if (string != null) {
            textfield.setText(string);
        } else {
            textfield.setText("");
        }

        String oldErrorMessages = errorMessages.getText();
        String newErrorMessages = model.getErrorResourceIds().stream().map(id -> textResource.getString(id)).reduce((t, u) -> t + ' ' + u).orElse(null);
        if (newErrorMessages.isEmpty()) {
            newErrorMessages = null;
        }
        if (oldErrorMessages == null || !oldErrorMessages.equals(newErrorMessages)) {
            errorMessages.setText(newErrorMessages);
            Container topLevelContainer = SwingUtils.getTopLevelContainer(this);
            if (topLevelContainer != null) {
                topLevelContainer.revalidate();
                if (topLevelContainer instanceof Window) {
                    ((Window) topLevelContainer).pack();
                }
            }
        }
    }

    /**
     * This method gets a string representation of the model's value.
     * @return the string representation
     */
    protected abstract String getStringFromModel();

	/**
     * Parses the text that has been entered by the user. If the entered text is a valid
     * value, then the model is updated.
     */
    private void parseUserInput() {
        try {
            parseUserInput(textfield.getText(), modelChangeListener);
            textfield.setBorder(new LineBorder(Color.GRAY));
        } catch (ParseException e) {
            if (textfield.getText().length() > 0) {
                textfield.setBorder(new LineBorder(Color.RED));
            } else {
                textfield.setBorder(new LineBorder(Color.GRAY));
            }
        }
    }

    /**
     * Parses the entered text and updates the model with the parsed value.
     * @param text the entered text
     * @throws ParseException if the text is invalid for the model
     */
    protected abstract void parseUserInput(String text, ModelChangeListener modelChangeListener)
    	throws ParseException;

    @Override
    public void requestFocus() {
    	textfield.requestFocus();
    }

    @Override
    public boolean requestFocusInWindow() {
    	return textfield.requestFocusInWindow();
    }

    private final class UpdateTextFieldOnModelChangeListener implements ModelChangeListener {
    	@Override
    	public void modelChanged(AbstractModel model) {
    		updateTextField();
    	}
    }

    private final class ParseUserInputOnDocumentChangeListener implements DocumentListener {
    	@Override
    	public void changedUpdate(DocumentEvent evt) {
    		parseUserInput();
    	}

    	@Override
    	public void insertUpdate(DocumentEvent evt) {
    		parseUserInput();
    	}

    	@Override
    	public void removeUpdate(DocumentEvent evt) {
    		parseUserInput();
    	}
    }
}
