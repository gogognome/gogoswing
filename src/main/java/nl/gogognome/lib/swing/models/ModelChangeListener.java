package nl.gogognome.lib.swing.models;

/**
 * This interface specifies a listener for changes in a model.
 */
public interface ModelChangeListener {

    /**
     * This method is called when the model has changed
     * @param model the model that has changed
     */
    public void modelChanged(AbstractModel model);
}
