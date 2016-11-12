package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.models.IntegerModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.util.StringUtil;

import java.text.ParseException;

/**
 * This class implements a bean for entering an integer.
 */
public class IntegerFieldBean extends AbstractTextFieldBean<IntegerModel> {

    /**
     * Constructor.
     * @param integerModel the integer model that will reflect the content of the bean
     */
    protected IntegerFieldBean(IntegerModel integerModel) {
        super(integerModel);
    }

    /**
     * Constructor.
     * @param integerModel the integer model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    protected IntegerFieldBean(IntegerModel integerModel, int nrColumns) {
        super(integerModel, nrColumns);
    }

    @Override
    protected String getStringFromModel() {
        if (model.isNull()) {
            return "";
        } else {
            return Integer.toString(model.getInteger());
        }
    }

    @Override
    protected void parseUserInput(String text, ModelChangeListener modelChangeListener)
            throws ParseException {
        if (StringUtil.isNullOrEmpty(text)) {
            model.setInteger(null);
        } else {
            try {
                model.setInteger(Integer.parseInt(text), modelChangeListener);
            } catch (NumberFormatException e) {
                throw new ParseException(e.getMessage(), 0);
            }

        }
    }

}
