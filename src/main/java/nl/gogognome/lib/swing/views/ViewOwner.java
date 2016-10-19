package nl.gogognome.lib.swing.views;

import javax.swing.*;
import java.awt.*;

/**
 * A view owner is the top level container of a view. Typically, a view onwer will be a frame or a dialog.
 */
public interface ViewOwner {

    /**
     * Gets the Window instance that contains the view.
     * @return the Window
     */
    Window getWindow();

    /**
     * Sets the default button inside the top level container.
     * @param button the default button
     */
    void setDefaultButton(JButton button);

    /**
     * Invalidates the layout of the view owner. Forces the view onwer to redo the layout.
     */
    void invalidateLayout();
}
