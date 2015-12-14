package nl.gogognome.lib.swing.views;

/**
 * A listener for changes in the state of a view.
 */
public interface ViewListener {

    /**
     * This method is called when the view is closed.
     * @param view the view thas is closed
     */
    public void onViewClosed(View view);
}
