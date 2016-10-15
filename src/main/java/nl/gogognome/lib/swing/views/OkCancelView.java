package nl.gogognome.lib.swing.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;


/**
 * Base class for views that have an ok and cancel button at the bottom of the view.
 */
public abstract class OkCancelView extends View {

    private static final long serialVersionUID = 1L;

    /**
     * Call this method from the onInit() method of your class.
     * This method adds the components to the view. This method calls
     * the method createCenterPanel() which creates the component to be placed
     * above the ok and cancel buttons.
     */
    protected void addComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        OkCancelButtonPanel buttonPanel = createButtonPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        addCloseable(buttonPanel);

        JComponent comp = createNorthComponent();
        if (comp != null) {
            comp.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            add(comp, BorderLayout.NORTH);
        }

        comp = createCenterComponent();
        if (comp != null) {
            add(comp, BorderLayout.CENTER);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private OkCancelButtonPanel createButtonPanel() {
        OkCancelButtonPanel panel = new OkCancelButtonPanel(new OkAction(), closeAction);
        setDefaultButton(panel.getDefaultButton());
        return panel;
    }

    /**
     * This method must create the component to be placed
     * at the top of the view.
     * @return the component
     */
    protected JComponent createNorthComponent() {
        return null;
    }

    /**
     * This method must create the component to be placed
     * above the ok and cancel buttons (in the center of the view).
     * @return the component
     */
    protected abstract JComponent createCenterComponent();

    /**
     * This method is called when the user presses the ok button.
     */
    protected abstract void onOk();

    /**
     * This method is called when the user presses the cancel button.
     * Default implementation closes the view.
     */
    protected void onCancel() {
        requestClose();
    }

    private class OkAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionevent) {
            onOk();
        }
    }

    private class CancelAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionevent) {
            onCancel();
        }
    }
}
