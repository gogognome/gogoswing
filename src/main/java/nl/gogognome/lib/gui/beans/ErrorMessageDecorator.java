package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

import javax.swing.*;
import java.awt.*;

public class ErrorMessageDecorator implements Bean {

    private final AbstractModel<?> model;
    private ModelChangeListener modelChangeListener;
    private final TextResource textResource;

    private final Bean decoratedBean;
    private JPanel panel;
    private JLabel errorMessages;

    public ErrorMessageDecorator(Bean decoratedBean, AbstractModel<?> model, TextResource textResource) {
        this.decoratedBean = decoratedBean;
        this.model = model;
        this.textResource = textResource;
    }

    @Override
    public void initBean() {
        errorMessages = new JLabel();
        errorMessages.setIcon(new WidgetFactory(Factory.getInstance(TextResource.class)).createIcon("validationError.icon"));
        errorMessages.setForeground(Color.RED);
        errorMessages.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(decoratedBean.getComponent(), BorderLayout.CENTER);
        panel.add(errorMessages, BorderLayout.EAST);

        modelChangeListener = this::updateErrorMessages;
        model.addModelChangeListener(modelChangeListener);

        updateErrorMessages(model);

        decoratedBean.initBean();
    }

    public Bean getDecoratedBean() {
        return decoratedBean;
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void close() {
        model.removeModelChangeListener(modelChangeListener);
        decoratedBean.close();
    }

    private void updateErrorMessages(AbstractModel<?> model) {
        String newErrorMessages = formatErrorMessages(model);
        String oldErrorMessages = errorMessages.getText();
        if (oldErrorMessages == null || !oldErrorMessages.equals(newErrorMessages)) {
            errorMessages.setText(newErrorMessages);
        }

        errorMessages.setVisible(newErrorMessages != null);
    }

    private String formatErrorMessages(AbstractModel<?> model) {
        String newErrorMessages = model.getErrorResourceIds().stream().map(id -> textResource.getString(id)).reduce((t, u) -> t + ' ' + u).orElse(null);
        if (newErrorMessages != null && newErrorMessages.isEmpty()) {
            newErrorMessages = null;
        }
        return newErrorMessages;
    }
}
