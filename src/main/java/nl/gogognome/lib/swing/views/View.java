package nl.gogognome.lib.swing.views;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.gui.beans.BeanFactory;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.dialogs.MessageDialog;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A view represents a rectangular area inside a dialog or frame. A view typically
 * implements a cohesive part of the user interface.
 *
 * <p>Views can be put inside a dialog (see {@link ViewDialog}),
 * a frame (see {@link ViewFrame}), tabbed pane (see {@link ViewTabbedPane}) and even
 * inside other views (see {@link ViewContainer}).
 *
 * <p>When a view is added to one of the aforementioned containers, its {@link #onInit()}
 * method will be called by the container.
 *
 * <p>The container will call {@link #onClose()} when the container removes the view.
 * The view can close itself by calling {@link #requestClose()}.
 *
 * <p>Some user interface components implement the {@link Closeable} interface.
 * If your view uses such components, call {@link #addCloseable(Closeable)}
 * for each of these components. By doing so, the deinitialize() method will be called
 * automatically for these components when the view is closed. Otherwise, you have to
 * think about deinitializing them in your onClose() implementation.
 */
public abstract class View extends JPanel implements Closeable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Listeners of this view that get informed when this view is closed. */
    private List<ViewListener> listeners = new ArrayList<>();

    /** Objects to be closed when the view is closed. */
    private List<Closeable> closeables = new ArrayList<>();

    /**
     * This action closes the view. The action will be set before the view
     * is shown. The view can use it let itself be closed.
     */
    protected Action closeAction;

    private ViewOwner viewOwner;
    private JButton defaultButton;

    protected TextResource textResource;
    protected WidgetFactory widgetFactory;
    protected BeanFactory beanFactory;
    protected MessageDialog messageDialog;

    protected View() {
        textResource = Factory.getInstance(TextResource.class);
        widgetFactory = Factory.getInstance(WidgetFactory.class);
        beanFactory = Factory.getInstance(BeanFactory.class);
        messageDialog = new MessageDialog(textResource, this);
    }

    /**
     * Gets the title of the view.
     * @return the title of the view
     */
    public abstract String getTitle();

    /**
     * Gets the default button of this view.
     * Override this method to specify a default button.
     * @return the default button of this view or <code>null</code> if this view has no default button
     */
    JButton getDefaultButton() {
        return defaultButton;
    }

    /**
     * This method is called before the view is shown. It initializes the view.
     */
    public abstract void onInit();

    /** This method is called just before the view is closed. Override this method to free resources. */
    public abstract void onClose();

    /**
     * Sets the close action.
     *
     * @param closeAction the close action
     */
    void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }

    void setViewOwner(ViewOwner viewOwner) {
        this.viewOwner = viewOwner;
    }

    public ViewOwner getViewOwner() {
    	return viewOwner;
    }

    public void addViewListener(ViewListener listener) {
        listeners.add(listener);
    }

    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the default button of this view.
     * @param button the default button
     */
    public void setDefaultButton(JButton button) {
        defaultButton = button;
        viewOwner.setDefaultButton(button);
    }

    /**
     * Adds an object that will be closed automatically when this view is closed.
     * @param d the Closeable object
     */
    public void addCloseable(Closeable d) {
    	closeables.add(d);
    }

    /** Call this method to close the view programmatically. */
    public void requestClose() {
    	closeAction.actionPerformed(null);
    }

    /**
     * Closes the view and notifies listeners.
     */
    void doClose() {
        logger.debug("Start closing view " + getClass().getName());

        try {
            onClose();
        } catch (Exception e) {
            messageDialog.showErrorMessage(e, "gen.titleError");
        }

        for (Closeable d : closeables) {
            try {
            	d.close();
            } catch (Exception e) {
                logger.warn("Ignored exception: " + e.getMessage(), e);
            }
        }

        ViewListener[] tempListeners = listeners.toArray(new ViewListener[listeners.size()]);
        for (ViewListener tempListener : tempListeners) {
            try {
                tempListener.onViewClosed(this);
            } catch (Exception e) {
                logger.warn("Ignored exception: " + e.getMessage(), e);
            }
        }

        logger.debug("Finished closing view " + getClass().getName());
    }

	/** Initializes the view. */
    void doInit() {
        logger.debug("Start initializing view " + getClass().getName());
        onInit();
        logger.debug("Finished initializing view " + getClass().getName());
    }

    @Override
    public void close() {
    	closeAction.actionPerformed(null);
    }
}
