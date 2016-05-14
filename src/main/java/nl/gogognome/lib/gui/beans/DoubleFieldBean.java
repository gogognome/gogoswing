package nl.gogognome.lib.gui.beans;

import java.text.ParseException;

import nl.gogognome.lib.swing.models.DoubleModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;
import nl.gogognome.lib.util.StringUtil;

/**
 * This class implements a bean for entering a double.
 */
public class DoubleFieldBean extends AbstractTextFieldBean<DoubleModel> {

    /**
     * Constructor.
     * @param doubleModel the double model that will reflect the content of the bean
	 * @param textResource the text resource for obtaining error messages
     */
    protected DoubleFieldBean(DoubleModel doubleModel, TextResource textResource) {
    	super(doubleModel, textResource);
    }

    /**
     * Constructor.
     * @param doubleModel the double model that will reflect the content of the bean
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
	 * @param textResource the text resource for obtaining error messages
     */
    protected DoubleFieldBean(DoubleModel doubleModel, int nrColumns, TextResource textResource) {
    	super(doubleModel, nrColumns, textResource);
    }

    @Override
    protected String getStringFromModel() {
    	if (model.isNull()) {
    		return "";
    	} else {
    		return Factory.getInstance(TextResource.class).formatDouble(model.getDouble());
    	}
    }

    @Override
    protected void parseUserInput(String text, ModelChangeListener modelChangeListener)
    		throws ParseException {
    	if (StringUtil.isNullOrEmpty(text)) {
    		model.setDouble(null);
    	} else {
	    	double d = Factory.getInstance(TextResource.class).parseDouble(text);
	    	model.setDouble(d, modelChangeListener);
    	}
    }

}
