package nl.gogognome.lib.gui.beans;

import java.text.ParseException;

import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.models.StringModel;
import nl.gogognome.lib.text.TextResource;

/**
 * This class implements a bean for entering a <code>String</code>.
 */
public class TextFieldBean extends AbstractTextFieldBean<StringModel> {

    /**
     * Constructor.
     * @param stringModel the string model that will reflect the content of the bean
     * @param textResource the text resource for obtaining error messages
     */
    public TextFieldBean(StringModel stringModel, TextResource textResource) {
    	super(stringModel, textResource);
    }

    /**
     * Constructor.
     * @param stringModel the string model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     * @param textResource the text resource for obtaining error messages
     */
    protected TextFieldBean(StringModel stringModel, int nrColumns, TextResource textResource) {
    	super(stringModel, nrColumns, textResource);
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
