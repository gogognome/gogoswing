package nl.gogognome.lib.swing;

import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

/**
 * Table cell renderere for dates.
 */
public class DateRenderer extends DefaultTableCellRenderer {

    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        if (value instanceof Date) {
            value = Factory.getInstance(TextResource.class).formatDate("gen.dateFormat", (Date) value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}