package nl.gogognome.lib.swing;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Abstract class for table model implementations. This class
 * uses ColumnDefinitions to define the table columns.
 *
 * @param <R> the type of a row
 */
public abstract class AbstractTableModel<R> extends javax.swing.table.AbstractTableModel {

    /** The column definitions. */
    private List<ColumnDefinition<R>> columnDefinitions;

    /** Constructs a table model without column definitions. Make sure to set the column definitions. */
    public AbstractTableModel() {}

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     */
    public AbstractTableModel(List<ColumnDefinition<R>> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public void setColumnDefinitions(ColumnDefinition<R>... columnDefinitions) {
        setColumnDefinitions(asList(columnDefinitions));
    }

    public void setColumnDefinitions(List<ColumnDefinition<R>> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    @Override
    public String getColumnName(int column) {
        return columnDefinitions.get(column).getName();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return columnDefinitions.get(column).getClassOfValues();
    }

	public int getColumnWidth(int column) {
        return columnDefinitions.get(column).getWidthInPixels();
    }

	public Comparator<Object> getComparator(int column) {
        return columnDefinitions.get(column).getComparator();
    }

	public TableCellRenderer getRendererForColumn(int column) {
        return columnDefinitions.get(column).getTableCellRenderer();
    }

	public TableCellEditor getEditorForColumn(int column) {
        return columnDefinitions.get(column).getTableCellEditor();
    }

    @Override
	public int getColumnCount() {
        return columnDefinitions.size();
    }

    /**
     * Gets the column definition for the specified column.
     * @param column the index of the column
     * @return the column definition
     */
    public ColumnDefinition<R> getColumnDefinition(int column) {
        return columnDefinitions.get(column);
    }

}
