package nl.gogognome.lib.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

/**
 * This class wraps an <code>AbstractAction</code>. It allows to change
 * the behavior of the <code>actionPerformed()</code> method dynamically.
 */
public class ActionWrapper extends AbstractAction {

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
        this.action = action;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        // By using a copy of the action variable synchronization problems are prevented.
        Action localAction = action;
        if (localAction != null) {
            localAction.actionPerformed(event);
        }
    }
}
