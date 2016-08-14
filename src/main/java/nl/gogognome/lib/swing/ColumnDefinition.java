package nl.gogognome.lib.swing;

import java.util.Comparator;
import java.util.function.Function;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

/**
 * This class defines a column in a table.
 *
 * @param <R> the type of the row
 */
public class ColumnDefinition<R> {

    /** The identifier for the name of the column. */
    private String id;

    /** The class to which all values of the column belong. */
    private Class<?> classOfValues;

    /** The column width in pixels. */
    private int widthInPixels;

    /**
     * The table cell renderer for the column. <code>null</code> indicates that
     * the default table cell renderer must be used.
     */
    private TableCellRenderer tableCellRenderer;

    /**
     * The table cell editor for the column. <code>null</code> indicates that the
     * default table cell editor must be used.
     */
    private TableCellEditor tableCellEditor;

    /**
     * The comparator used to sort the values of this column.
     * <code>null</code> indicates that the default comparator must be used.
     */
    private Comparator<Object> comparator;

    /**
     * Function that gets the value for this column from a row object.
     */
    private Function<R, Object> getValueForColumn;

    /**
     * Constructor.
     * @param id the id of the column's name
     * @param classOfValues the class to which all values of the column belong
     * @param widthInPixels the column width in pixels
     */
    public ColumnDefinition(String id, Class<?> classOfValues, int widthInPixels) {
        this.id = id;
        this.classOfValues = classOfValues;
        this.widthInPixels = widthInPixels;
    }

    public String getName() {
        return Factory.getInstance(TextResource.class).getString(id);
    }

    public Class<?> getClassOfValues() {
        return classOfValues;
    }

    public int getWidthInPixels() {
        return widthInPixels;
    }

    public TableCellRenderer getTableCellRenderer() {
        return tableCellRenderer;
    }

    public TableCellEditor getTableCellEditor() {
		return tableCellEditor;
	}

    public Comparator<Object> getComparator() {
        return comparator;
    }

    public Object getValueForColumn(R row) {
        return getValueForColumn.apply(row);
    }

    public static <R> Builder<R> builder(String id, Class<?> classOfValues, int widthInPixels) {
        return new Builder(id, classOfValues, widthInPixels);
    }


    /**
     * A builder for creating {@link ColumnDefinition}s.
     */
    public static class Builder<R> {

    	private ColumnDefinition<R> columnDefinition;

        public Builder(String id, Class<?> classOfValues, int widthInPixels) {
        	columnDefinition = new ColumnDefinition(id, classOfValues, widthInPixels);
        }

        public Builder<R> add(TableCellRenderer tableCellRenderer) {
        	columnDefinition.tableCellRenderer = tableCellRenderer;
        	return this;
        }

        public Builder<R> add(TableCellEditor tableCellEditor) {
        	columnDefinition.tableCellEditor = tableCellEditor;
        	return this;
        }

        public Builder<R> add(Comparator<Object> comparator) {
        	columnDefinition.comparator = comparator;
        	return this;
        }

        public Builder<R> add(Function<R, Object> getValueForColumn) {
        	columnDefinition.getValueForColumn = getValueForColumn;
        	return this;
        }

        public ColumnDefinition<R> build() {
        	return columnDefinition;
        }
    }
}
