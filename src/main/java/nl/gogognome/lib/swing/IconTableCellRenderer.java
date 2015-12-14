package nl.gogognome.lib.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a table cell renderer for icons.
 */
public class IconTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

	private final static Logger LOGGER = LoggerFactory.getLogger(IconTableCellRenderer.class);

	private Icon icon;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Icon) {
			icon = (Icon) value;
		} else {
			icon = null;
			if (icon != null) {
				LOGGER.warn("IconTableCellRenderer used to render instance of " + value.getClass()
						+ " instead of an icon");
			}
		}
		return super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (icon != null) {
			int x = (getWidth() - icon.getIconWidth()) / 2;
			int y = (getHeight() - icon.getIconHeight()) / 2;
			icon.paintIcon(this, g, x, y);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int w;
		int h;
		if (icon != null) {
			w = icon.getIconWidth();
			h = icon.getIconHeight();
		} else {
			w = 16;
			h = 16;
		}
		return new Dimension(w, h);
	}
}
