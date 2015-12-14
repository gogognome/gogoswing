package nl.gogognome.lib.gui.beans;

import java.text.ParseException;

import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.models.StringModel;

/**
 * This class implements a bean for entering a <code>String</code>.
 */
public class TextFieldBean extends AbstractTextFieldBean<StringModel> {

    /**
     * Constructor.
     * @param stringModel the string model that will reflect the content of the bean
     */
    public TextFieldBean(StringModel stringModel) {
    	super(stringModel);
    }

    /**
     * Constructor.
     * @param stringModel the string model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    protected TextFieldBean(StringModel stringModel, int nrColumns) {
    	super(stringModel, nrColumns);
    }

    @Override
    protected String getStringFromModel() {
    	return model.getString();
    }

    @Override
    protected void parseUserInput(String text, ModelChangeListener modelChangeListener)
    		throws ParseException {
    	model.setString(text, modelChangeListener);
    }

}
