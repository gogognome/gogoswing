package nl.gogognome.lib.swing.models;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.RunnableWithException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Tables {

    private final static Logger LOGGER = LoggerFactory.getLogger(Tables.class);

    /**
     * Adds a ListSelectionListener to a table's ListSelectionModel. Each time the selection changes
     * the listenerImplementation is executed.
     * This method returns a Closeable that removes the listener when the close() method is called.
     * @param table the table
     * @param listenerImplementation implementation of the listener
     * @return a Closeable that removes the listener from the list selection model
     */
    public static Closeable onSelectionChange(JTable table, RunnableWithException listenerImplementation) {
        ListSelectionListener listener = event -> {
            if (event.getValueIsAdjusting()) {
                return; // ignore events while selection is adjusting
            }

            try {
                listenerImplementation.run();
            } catch (Exception e) {
                LOGGER.warn("Ignored exception: " + e.getMessage(), e);
            }
        };
        table.getSelectionModel().addListSelectionListener(listener);
        listener.valueChanged(new ListSelectionEvent(table, 0, 0, false));
        return () -> table.getSelectionModel().removeListSelectionListener(listener);
    }

}
