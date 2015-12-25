package nl.gogognome.lib.swing.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * This class wraps an <code>AbstractAction</code>. It allows to change
 * the behavior of the <code>actionPerformed()</code> method dynamically.
 */
public class ActionWrapper extends AbstractAction {

    private final PropertyChangeListener propertyChangeListener = evt -> firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());

    /**
     * The action to which <code>actionPerformed()</code> is forwarded.
     * If <code>action</code> is <code>null</code>, then
     * <code>actionPerformed()</code> will do noting.
     */
    private Action action;

    /**
     * Constructor.
     */
    public ActionWrapper() {
        super();
    }

    /**
     * Constructor.
     */
    public ActionWrapper(String name) {
        super(name);
    }

    /**
     * Constructor.
     */
    public ActionWrapper(String name, Icon icon) {
        super(name, icon);
    }

    public void setAction(Action action) {
        Action oldValue = this.action;
        if (oldValue == null || !oldValue.equals(action)) {
            this.action = action;
            if (oldValue != null) {
                oldValue.removePropertyChangeListener(propertyChangeListener);
            }

            if (action != null) {
                action.addPropertyChangeListener(propertyChangeListener);
            }
        }

    }

    @Override
	public void actionPerformed(ActionEvent event) {
        // By using a copy of the action variable synchronization problems are prevented.
        Action localAction = action;
        if (localAction != null) {
            localAction.actionPerformed(event);
        }
    }

    @Override
    public boolean isEnabled() {
        return action != null ? action.isEnabled() : super.isEnabled();
    }

    @Override
    public void setEnabled(boolean newValue) {
        if (action != null) {
            action.setEnabled(newValue);
        }
        super.setEnabled(newValue);
    }
}
