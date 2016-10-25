package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusListener;
import java.text.ParseException;

/**
 * Base class for a text field bean. Make sure that after instantiation
 * first the method {@link #initBean()} is called.
 */
public abstract class AbstractTextFieldBean<M extends AbstractModel> extends JPanel implements Bean {

	protected M model;
    private ModelChangeListener modelChangeListener;

    private int horizontalAlignment = JTextField.LEFT;
    private int nrColumns;
    private JTextField textfield;
    private DocumentListener documentListener;

    /**
     * Constructor.
     * @param model the model that will reflect the content of the bean
     */
    protected AbstractTextFieldBean(M model) {
    	this(model, 0);
    }

    /**
     * Constructor.
     * @param model the model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    protected AbstractTextFieldBean(M model, int nrColumns) {
    	this.model = model;
    	this.nrColumns = nrColumns;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        if (textfield != null) {
            textfield.setHorizontalAlignment(horizontalAlignment);
        }
    }

    @Override
	public void initBean() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        textfield = createTextField(nrColumns);
        textfield.setHorizontalAlignment(horizontalAlignment);

        updateTextField();
        parseUserInput();
        modelChangeListener = m -> updateTextField();
        model.addModelChangeListener(modelChangeListener);

        documentListener = new ParseUserInputOnDocumentChangeListener();
        textfield.getDocument().addDocumentListener(documentListener);

        add(textfield, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, nrColumns == 0 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE,
            0, 0, 0, 0));
    }

    protected JTextField createTextField(int nrColumns) {
		return new JTextField(nrColumns);
	}

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
	public void close() {
        model.removeModelChangeListener(modelChangeListener);
        textfield.getDocument().removeDocumentListener(documentListener);
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
