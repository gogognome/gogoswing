package nl.gogognome.lib.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * This class uses ColumnDefinitions to define the table columns. In addition it stores
 * the rows of the table model in a list. This class offers methods to modify the list.
 * Modifications of the list are signaled the registered TableModelListeners.
 *
 * @param <R> the type of rows
 */
public class ListTableModel<R> extends AbstractTableModel<R> {

    private final List<R> rows = new ArrayList<>();

    /**
     * Constructor.
     */
    public ListTableModel() {
        super();
    }

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     */
    public ListTableModel(ColumnDefinition<R>... columnDefinitions) {
        this(asList(columnDefinitions));
    }

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     */
    public ListTableModel(List<ColumnDefinition<R>> columnDefinitions) {
        super(columnDefinitions);
    }

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     * @param initialRows the initial rows
     */
    public ListTableModel(List<ColumnDefinition<R>> columnDefinitions, List<R> initialRows) {
        super(columnDefinitions);
        rows.addAll(initialRows);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Adds a row to the table model.
     * Notifies all listeners about the change in the table.
     * This method must be called from the AWT event thread.
     * @param row the row
     */
    public void addRow(R row) {
        rows.add(row);
        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }

    /**
     * Removes a row from the table model.
     * Notifies all listeners about the change in the table.
     * This method must be called from the AWT event thread.
     * @param index the index of the row
     */
    public void removeRow(int index) {
        rows.remove(index);
        fireTableRowsDeleted(index, index);
    }

    /**
     * Removes a number of rows from the table model.
     * Notifies all listeners about the change in the table.
     * This method must be called from the AWT event thread.
     * @param indices the indices of the rows to be removed
     */
    public void removeRows(int[] indices) {
        Arrays.sort(indices);
        for (int i=indices.length-1; i >= 0; i--) {
            rows.remove(indices[i]);
        }
        fireTableDataChanged();
    }

    /**
     * Removes all rows from the table model.
     * Notifies all listeners about the change in the table.
     * This method must be called from the AWT event thread.
     */
    public void clear() {
        int oldSize = rows.size();
        if (oldSize > 0) {
            rows.clear();
            fireTableDataChanged();
        }
    }

    /**
     * Updates a row.
     * Notifies all listeners about the change in the table.
     * This method must be called from the AWT event thread.
     * @param index the index of the row
     * @param row the new value of the row
     */
    public void updateRow(int index, R row) {
        rows.set(index, row);
        fireTableRowsUpdated(index, index);
    }

    /**
     * Updates all rows of the table.
     * @param newRows the new rows
     */
    public void setRows(List<R> newRows) {
        rows.clear();
        rows.addAll(newRows);
        fireTableDataChanged();
    }

    /**
     * Gets a row.
     * @param index the index of the row
     * @return the row
     */
    public R getRow(int index) {
        return rows.get(index);
    }

    /**
     * Gets the rows of the table.
     *
     * @return an unmodifiable list of rows
     */
    public List<R> getRows() {
        return Collections.unmodifiableList(rows);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ColumnDefinition<R> columnDefinition = getColumnDefinition(columnIndex);
        return columnDefinition.getValueForColumn(getRow(rowIndex));
    }

}
