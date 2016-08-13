package nl.gogognome.lib.swing;

import nl.gogognome.lib.gui.Closeable;

import javax.swing.*;
import java.awt.*;

/**
 * This class implements a JPanel that implements Closeable.
 * The close() method of this class calls close() of any Closeable component that has been added to this panel.
 */
public class CloseableJPanel extends JPanel implements Closeable {

    @Override
    public void close() {
        for (Component c : getComponents()) {
            if (c instanceof Closeable) {
                ((Closeable) c).close();
            }
        }
    }
}
