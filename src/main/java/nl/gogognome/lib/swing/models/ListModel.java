package nl.gogognome.lib.swing.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class implements a model for a list of items.
 */
public class ListModel<T> extends AbstractModel<List<T>> {

    private int[] selectedIndices = new int[0];
    private boolean mustBeFilled;

    public ListModel() {
    }

    public ListModel(List<T> items) {
        super(items);
    }

    public ListModel<T> mustBeFilled(boolean mustBeFilled) {
        this.mustBeFilled = mustBeFilled;
        return this;
    }

    /**
     * Sets the items for the list model.
     * @param items the items
     */
    public ListModel<T> setItems(List<T> items) {
        setValue(items);
        return this;
    }

    /**
     * Sets the items for the list model.
     * @param items the items
     * @param source the model change listener that sets the items. It will not get notified. It may be null.
     */
    public ListModel<T> setItems(List<T> items, ModelChangeListener source) {
        setValue(items, source);
        return this;
    }

    /**
     * Adds an item to the list model.
     * @param item the item
     * @param source the model change listener that sets the items.  It will not
     *        get notified. It may be null.
     */
    public void addItem(T item, ModelChangeListener source) {
        getValue().add(item);
        notifyListeners(source);
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(getValue());
    }

    /**
     * Sets the selected index.
     * @param selectedIndex the selected index. -1 indicates that no item is selected
     * @param source the model change listener that sets the items.  It will not
     *        get notified. It may be <code>null</code>.
     */
    public void setSelectedIndex(int selectedIndex, ModelChangeListener source) {
        int[] indices;
        if (selectedIndex == -1) {
            indices = new int[0];
        } else {
            indices = new int[] { selectedIndex };
        }
        setSelectedIndices(indices, source);
    }

    /**
     * Sets the selected indices.
     * @param selectedIndices the selected indices
     * @param source the model change listener that sets the items.  It will not
     *        get notified. It may be <code>null</code>.
     */
    public void setSelectedIndices(int[] selectedIndices, ModelChangeListener source) {
        if (!Arrays.equals(this.selectedIndices, selectedIndices)) {
            this.selectedIndices = selectedIndices;
            notifyListeners(source);
        }
    }

    /**
     * If exactly one item has been selected, then its index is returned.
     * Otherwise -1 is returned.
     * @return the index of the selected item or -1
     */
    public int getSelectedIndex() {
        if (selectedIndices.length == 1) {
            return selectedIndices[0];
        } else {
            return -1;
        }
    }

    public int[] getSelectedIndices() {
        return selectedIndices;
    }

    /**
     * If exactly one item has been selected, then that item is returned.
     * Otherwise null is returned.
     * @return the selected item or null
     */
    public T getSelectedItem() {
        if (selectedIndices.length == 1) {
            return getValue().get(selectedIndices[0]);
        } else {
            return null;
        }
    }

    /**
     * Selects a single item.
     * @param item the item
     * @param source the model change listener that sets the items.  It will not
     *        get notified. It may be <code>null</code>.
     */
    public void setSelectedItem(T item, ModelChangeListener source) {
        int index = getValue().indexOf(item);
        int[] indices = index == -1 ? new int[0] : new int[] { index };
        setSelectedIndices(indices, source);
    }

    /**
     * Gets a list of selected items.
     * @return the list (never null)
     */
    public List<T> getSelectedItems() {
        List<T> list = new ArrayList<T>();
        for (int index : selectedIndices) {
            list.add(getValue().get(index));
        }
        return list;
    }

    @Override
    public boolean validate() {
        errorResourceIds.clear();
        if (mustBeFilled && getSelectedItem() == null) {
            errorResourceIds.add("validation.fieldMustBeFilledIn");
        }
        notifyListeners(null);
        return errorResourceIds.isEmpty();
    }

}
