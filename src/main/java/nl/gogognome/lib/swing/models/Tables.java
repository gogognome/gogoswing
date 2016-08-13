package nl.gogognome.lib.swing.models;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.RunnableWithException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Tables {

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
                throw new RuntimeException(e);
            }
        };
        table.getSelectionModel().addListSelectionListener(listener);
        listener.valueChanged(new ListSelectionEvent(table, 0, 0, false));
        return () -> table.getSelectionModel().removeListSelectionListener(listener);
    }

    /**
     * Gets the selected rows of a (sorted) table. The returned row indices
     * are converted to model indices.
     * @param table the table
     * @return the row indices
     */
    public static int[] getSelectedRowsConvertedToModel(JTable table) {
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            rows[i] = table.convertRowIndexToModel(rows[i]);
        }
        return rows;
    }

    /**
     * Gets the index of the first selected row of a (sorted) table.
     * The returned row indix is converted to a model index.
     * @param table the table
     * @return the row index or -1 if no row is selected
     */
    public static int getSelectedRowConvertedToModel(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            row = table.convertRowIndexToModel(row);
        }
        return row;
    }

    /**
     * Selects the first row of the table. If the table is empty,
     * then this method has no effect.
     * @param table the table
     */
    public static void selectFirstRow(JTable table) {
        if (table.getRowCount() > 0) {
            table.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    /**
     * Selects the specified row of the (sorted) table. The row index is
     * specified as an index of the table model. The corresponding
     * row in the table is selected.
     * @param table the table
     * @param row the row index of the model
     */
    public static void selectRowWithModelIndex(JTable table, int row) {
        row = table.convertRowIndexToView(row);
        table.getSelectionModel().setSelectionInterval(row, row);
    }


}
