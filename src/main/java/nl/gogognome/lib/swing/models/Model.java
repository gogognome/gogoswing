package nl.gogognome.lib.swing.models;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class Model {

    public static StringModel of(String initialValue, Consumer<String> valueUpdater) {
        return of(initialValue, valueUpdater, null);
    }

    public static StringModel of(String initialValue, Consumer<String> valueUpdater, ModelChangeListener modelChangeListener) {
        StringModel model = new StringModel(initialValue);
        model.addModelChangeListener(m -> valueUpdater.accept(model.getValue()));
        if (modelChangeListener != null) {
            model.addModelChangeListener(modelChangeListener);
        }
        return model;
    }

    public static DateModel of(Date initialValue, Consumer<Date> valueUpdater) {
        return of(initialValue, valueUpdater, null);
    }

    public static DateModel of(Date initialValue, Consumer<Date> valueUpdater, ModelChangeListener modelChangeListener) {
        DateModel model = new DateModel(initialValue);
        model.addModelChangeListener(m -> valueUpdater.accept(model.getValue()));
        if (modelChangeListener != null) {
            model.addModelChangeListener(modelChangeListener);
        }
        return model;
    }

    public static BooleanModel of(boolean initialValue, Consumer<Boolean> valueUpdater) {
        return of(initialValue, valueUpdater, null);
    }

    public static BooleanModel of(boolean initialValue, Consumer<Boolean> valueUpdater, ModelChangeListener modelChangeListener) {
        BooleanModel model = new BooleanModel();
        model.setBoolean(initialValue);
        model.addModelChangeListener(m -> valueUpdater.accept(model.getValue()));
        if (modelChangeListener != null) {
            model.addModelChangeListener(modelChangeListener);
        }
        return model;
    }

    public static <T> ListModel<T> of(List<T> initialValue, Consumer<T> valueUpdater) {
        return of(initialValue, valueUpdater, null);
    }

    public static <T> ListModel<T> of(List<T> initialValue, Consumer<T> valueUpdater, ModelChangeListener modelChangeListener) {
        ListModel<T> model = new ListModel<>(initialValue);
        model.addModelChangeListener(m -> valueUpdater.accept(model.getSelectedItem()));
        if (modelChangeListener != null) {
            model.addModelChangeListener(modelChangeListener);
        }
        return model;
    }
}
