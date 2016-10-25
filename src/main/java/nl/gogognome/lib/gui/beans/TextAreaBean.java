package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.models.StringModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * This class implements a bean for entering a <code>String</code>.
 */
public class TextAreaBean extends JPanel implements Bean {

    protected StringModel model;
    private ModelChangeListener modelChangeListener;
    private int nrColumns;
    private int nrRows;
    private JTextArea textArea;
    private DocumentListener documentListener;

    /**
     * Constructs a text area bean.
     * @param stringModel the string model that will reflect the content of the bean
     */
    public TextAreaBean(StringModel stringModel) {
        this(stringModel, 0, 0);
    }

    /**
     * Constructs a text area bean.
     * @param stringModel the string model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     * @param nrRows the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    protected TextAreaBean(StringModel stringModel, int nrColumns, int nrRows) {
        this.model = stringModel;
        this.nrColumns = nrColumns;
        this.nrRows = nrRows;
    }

    @Override
    public void initBean() {
        setOpaque(false);
        setLayout(new BorderLayout());

        if (nrColumns == 0 || nrRows == 0) {
            textArea = new JTextArea();
        } else {
            textArea = new JTextArea(nrRows, nrColumns);
        }

        updateTextAreaWithValuesFromModel();
        updateModelWithValueFromTextArea();
        modelChangeListener = m -> updateTextAreaWithValuesFromModel();
        model.addModelChangeListener(modelChangeListener);

        documentListener = new ParseUserInputOnDocumentChangeListener();
        textArea.getDocument().addDocumentListener(documentListener);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateTextAreaWithValuesFromModel() {
        textArea.setEnabled(model.isEnabled());
        String string = model.getString();
        if (string != null) {
            textArea.setText(string);
        } else {
            textArea.setText("");
        }
    }

    private void updateModelWithValueFromTextArea() {
        model.setString(textArea.getText(), modelChangeListener);
        textArea.setBorder(new LineBorder(Color.GRAY));
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void close() {
        model.removeModelChangeListener(modelChangeListener);
        textArea.getDocument().removeDocumentListener(documentListener);
    }

    private final class ParseUserInputOnDocumentChangeListener implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent evt) {
            updateModelWithValueFromTextArea();
        }

        @Override
        public void insertUpdate(DocumentEvent evt) {
            updateModelWithValueFromTextArea();
        }

        @Override
        public void removeUpdate(DocumentEvent evt) {
            updateModelWithValueFromTextArea();
        }
    }

}
