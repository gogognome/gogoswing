package nl.gogognome.lib.swing.models;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.AbstractTableModel;
import nl.gogognome.lib.swing.ColumnDefinition;
import nl.gogognome.lib.swing.RunnableWithException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.Comparator;

public class Tables {

    /**
     * Creates a non-sortable table based on the specified table model.
     * @param tableModel the table model
     * @return a panel containing the table
     */
    public static JTable createTable(AbstractTableModel tableModel) {
        JTable table = new JTable(tableModel);
        initTableColumns(table, tableModel);
        return table;
    }

    /**
     * Creates a sortable table based on the specified table model.
     * The columns of the table are defined by the ColumnDefinitions
     * of the table model.
     * @param tableModel the table model
     * @return the table
     */
    public static JTable createSortedTable(AbstractTableModel tableModel) {
        JTable table = new JTable(tableModel);
        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<>(tableModel);
        initTableColumns(table, tableModel);
        initSorterForTableModel(sorter, tableModel);
        table.setRowSorter(sorter);
        return table;
    }

    private static void initTableColumns(JTable table, AbstractTableModel tableModel) {
        TableColumnModel columnModel = table.getColumnModel();
        int nrCols = tableModel.getColumnCount();

        for (int i=0; i<nrCols; i++) {
            // Set column width
            int colWidth = tableModel.getColumnWidth(i);
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(colWidth);

            // If present, set the table cell renderer
            TableCellRenderer renderer = tableModel.getRendererForColumn(i);
            if (renderer != null) {
                column.setCellRenderer(renderer);
            }

            // If present, set the table cell editor
            TableCellEditor editor = tableModel.getEditorForColumn(i);
            if (editor != null) {
                column.setCellEditor(editor);
            }
        }
    }

    private static void initSorterForTableModel(TableRowSorter<AbstractTableModel> sorter, AbstractTableModel tableModel) {
        for (int c=0; c<tableModel.getColumnCount(); c++) {
            ColumnDefinition colDef = tableModel.getColumnDefinition(c);
            Comparator<?> comparator =  colDef.getComparator();
            if (comparator != null) {
                sorter.setComparator(c, comparator);
            }
        }
    }

    public static JPanel createNonScrollableTablePanel(JTable table) {
        JTableHeader header = table.getTableHeader();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        return panel;
    }

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
