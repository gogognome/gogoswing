package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.gui.Closeable;

import javax.swing.*;

/**
 * Lifecycle of a bean:
 *
 * <ol>
 *     <li>Bean bean = new BeanImpl();</li>
 *     <li>bean.initBean();
 *     <li>bean.getComponent();</li>
 *     <li>bean.close();</li>
 * </ol>
 */
public interface Bean extends Closeable {

    /**
     * Initializes the bean. Must be called before calling getComponent().
     */
    void initBean();

    /**
     * Gets the component that must be added to the container to make the bean visible.
     * @return the component
     */
    JComponent getComponent();
}
