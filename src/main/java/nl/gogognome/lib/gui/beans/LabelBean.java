package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.models.StringModel;

import javax.swing.*;

public class LabelBean extends JLabel implements Bean {

    private final StringModel model;
    private ModelChangeListener modelChangeListener;

    public LabelBean(StringModel model) {
        this.model = model;
    }

    @Override
    public void initBean() {
        updateLabel();
        modelChangeListener = m -> updateLabel();
        model.addModelChangeListener(modelChangeListener);
    }

    private void updateLabel() {
        String string = model.getString();
        if (string != null) {
            setText(string);
        } else {
            setText("");
        }
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void close() {
        model.removeModelChangeListener(modelChangeListener);
    }
}
