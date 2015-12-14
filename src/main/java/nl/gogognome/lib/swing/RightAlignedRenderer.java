package nl.gogognome.lib.swing;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Table cell renderer that right aligns its contents.
 */
public class RightAlignedRenderer extends DefaultTableCellRenderer {
    @Override
	public void setValue(Object value) {
        super.setValue(value);
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
}
