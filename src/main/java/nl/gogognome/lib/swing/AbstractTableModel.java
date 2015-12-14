package nl.gogognome.lib.swing;

import java.util.Comparator;
import java.util.List;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Abstract class for table model implementations. This class
 * uses ColumnDefinitions to define the table columns.
 */
public abstract class AbstractTableModel extends javax.swing.table.AbstractTableModel {

    /** The column definitions. */
    private List<ColumnDefinition> columnDefinitions;

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     */
    public AbstractTableModel(List<ColumnDefinition> columnDefinitions) {
        super();
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
    public ColumnDefinition getColumnDefinition(int column) {
        return columnDefinitions.get(column);
    }

}
