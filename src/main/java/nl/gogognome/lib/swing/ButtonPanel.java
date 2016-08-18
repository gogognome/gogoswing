package nl.gogognome.lib.swing;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.action.Actions;
import nl.gogognome.lib.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.*;

/**
 * This class implements a panel that can hold a number of buttons.
 * The buttons are shown in a single row or in a single column.
 * The location of the buttons within the panel is configurable.
 */
public class ButtonPanel extends JPanel implements Closeable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ButtonPanel.class);

	private static final long serialVersionUID = 1L;

	/** The location of the buttons within the panel. */
    private int location;

    /** Indicates whether the buttons are laid out horizontally or vertically. */
    private int orientation;

    /** A label that gets added to this panel to push the row of buttons to the left or the right. */
    private JLabel label = new JLabel();

    /**
     * Constructs a button panel.
     * @param location specifies the location of the buttons within the panel.
     *        Allowed values are <code>SwingConstants.LEFT</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.RIGHT</code>
     */
    public ButtonPanel(int location) {
        this(location, SwingConstants.HORIZONTAL);
    }

    /**
     * Constructs a button panel.
     * @param location specifies the location of the buttons within the panel.
     *        Allowed values for horizontally alligned panels are <code>SwingConstants.LEFT</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.RIGHT</code>.
     *        Allowed values for vertically alligned panels are <code>SwingConstants.TOP</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.BOTTOM</code>.
     * @param orientation the orientation. Must be <code>SwingConstants.HORIZONTAL</code>
     *        or <code>SwingConstants.VERTICAL</code>
     */
    public ButtonPanel(int location, int orientation) {
        super();
        this.location = location;
        this.orientation = orientation;
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
        setLayout(new GridBagLayout());
        switch(location) {
        case CENTER:
            if (orientation == SwingConstants.HORIZONTAL) {
                add(new JLabel(), SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
                add(label, SwingUtils.createGBConstraints(1, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            } else {
                add(new JLabel(), SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
                add(label, SwingUtils.createGBConstraints(0, 1, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            }
            break;

        case LEFT:
            if (orientation != SwingConstants.HORIZONTAL) {
                throw new IllegalArgumentException("The location LEFT is only allowed for HORIZONTAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;

        case RIGHT:
            if (orientation != SwingConstants.HORIZONTAL) {
                throw new IllegalArgumentException("The location RIGHT is only allowed for HORIZONTAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;

        case TOP:
            if (orientation != SwingConstants.VERTICAL) {
                throw new IllegalArgumentException("The location TOP is only allowed for VERTICAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            break;

        case BOTTOM:
            if (orientation != SwingConstants.VERTICAL) {
                throw new IllegalArgumentException("The location BOTTOM is only allowed for VERTICAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            break;

        default:
            throw new IllegalArgumentException("Invalid location specified: " + location);
        }
    }

    /**
     * Adds a button to this panel.
	 * @param action the action to be performed when the button is pressed
	 * @return the button.
     */
    public JButton addButton(Action action) {
        JButton button = new JButton(action);
        add(button);
    	return button;
    }

    /**
     * Adds a button to this panel.
	 * @param id the id of the button's description in the resources.
	 * @param action the action to be performed when the button is pressed
	 * @return the button.
     */
    public JButton addButton(String id, Action action) {
    	WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
    	JButton button = wf.createButton(id, action);
    	add(button);
    	return button;
    }

    /**
     * Adds a button to this panel.
	 * @param id the id of the button's description in the resources.
	 * @param runnable the runnable to be executed when the button is pressed
	 * @return the button.
     */
    public JButton addButton(String id, RunnableWithException runnable) {
    	WidgetFactory wf = Factory.getInstance(WidgetFactory.class);
    	JButton button = wf.createButton(id, Actions.build(this, runnable));
    	add(button);
    	return button;
    }

    /**
     * Adds a component to this panel.
     * @param component the component to be added
     * @return the component
     */
    @Override
    public Component add(Component component) {
        boolean firstButton = getComponentCount() == 1;
    	int leftTopInset = (location == LEFT || location == TOP) && firstButton ? 0 : 5;
    	int rightBottomInset = (location == RIGHT || location == BOTTOM) && firstButton ? 0 : 5;

        switch(location) {
        case CENTER:
        case LEFT:
        case TOP:
            // Remove the label, add the button and add the label as right-most element in the row
            remove(label);
            if (orientation == SwingConstants.HORIZONTAL) {
                add(component, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, leftTopInset, 5, rightBottomInset));
                add(label, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            } else {
                add(component, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, leftTopInset, 5, rightBottomInset, 5));
                add(label, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            }
            break;

        case RIGHT:
            add(component, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, rightBottomInset));
            break;
        case BOTTOM:
            add(component, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, rightBottomInset));
            break;
        }
        return component;
    }

    @Override
    public void close() {
        LOGGER.debug("Closing button panel");
        for (Component c : getComponents()) {
            if (c instanceof Closeable) {
                ((Closeable) c).close();
            }
        }
    }
}
