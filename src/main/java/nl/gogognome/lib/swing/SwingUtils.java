package nl.gogognome.lib.swing;

import java.awt.*;

/**
 * This class offers a variety of methods that are useful when writing
 * Swing applications.
 */
public class SwingUtils {

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy,
            int gridwidth, int gridheight, double weightx, double weighty,
            int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = insets;
        return gbc;
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy,
            int gridwidth, int gridheight, double weightx, double weighty,
            int anchor, int fill, int top, int left, int bottom, int right) {

        return createGBConstraints(gridx, gridy, gridwidth, gridheight, weightx,
                weighty, anchor, fill, new Insets(top, left, bottom, right));
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy) {

        return createGBConstraints(gridx, gridy, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                0, 0, 0, 0);
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy, int gridwidth, int gridheight) {

        return createGBConstraints(gridx, gridy, gridwidth, gridheight, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                0, 0, 0, 0);
    }


    public static GridBagConstraints createTextFieldGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                gridy == 0 ? 0 : 3, gridx == 0 ? 0 : 3, 0, 0);
    }

    public static GridBagConstraints createLabelGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                gridy == 0 ? 0 : 3, gridx == 0 ? 0 : 3, 0, 0);
    }

    public static GridBagConstraints createPanelGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gridy == 0 ? 0 : 10, gridx == 0 ? 0 : 10, 10, 10);
    }

    /**
     * gets the top level container of a component.
     * @param component the component
     * @return its top level container or null if the component has no parent
     */
	public static Container getTopLevelContainer(Component component) {
		Container parent = component.getParent();
		if (parent == null || (component instanceof Dialog)) {
			return (Container) component;
		} else {
			return getTopLevelContainer(parent);
		}
	}

	/**
	 * Gets coordinates of a component as screen locations.
	 * @param c the component
	 * @return the coordinates
	 */
	public static Point getCoordinatesRelativeToTopLevelContainer(Component c) {
		Point p = c.getLocation();
		while (c.getParent() != null && !(c.getParent() instanceof Window)) {
			c = c.getParent();
			Point parentLocation = c.getLocation();
			p.translate(parentLocation.x, parentLocation.y);
		}
		return p;
	}

	/**
	 * Puts the window in the center of the screen.
	 * @param window the window
	 */
	public static void center(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - window.getWidth()) / 2;
		int y = (screenSize.height - window.getHeight()) / 2;
		window.setLocation(x, y);
	}
}
