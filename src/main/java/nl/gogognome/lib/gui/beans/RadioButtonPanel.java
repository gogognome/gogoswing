package nl.gogognome.lib.gui.beans;

import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.*;

import nl.gogognome.lib.gui.Closeable;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.util.Factory;

/**
 * A panel containing a group of radio buttons.
 */
public class RadioButtonPanel extends JPanel implements Closeable {

    private static final long serialVersionUID = 1L;

    private ButtonGroup buttonGroup = new ButtonGroup();

    private BeanFactory beanFactory = Factory.getInstance(BeanFactory.class);

    public RadioButtonPanel() {
        setLayout(new GridBagLayout());
    }

    public void addRadioButton(String id, BooleanModel model) {
        Bean bean = beanFactory.createRadioButtonBean(id, model);
        add(bean.getComponent(), SwingUtils.createTextFieldGBConstraints(0, getComponentCount()));
        JComponent button = bean instanceof ErrorMessageDecorator ?
                ((ErrorMessageDecorator) bean).getDecoratedBean().getComponent() :
                bean.getComponent();
        buttonGroup.add((AbstractButton) button);
    }

    @Override
    public void close() {
        for (int i=0; i<getComponentCount(); i++) {
            Component c = getComponent(i);
            if (c instanceof Closeable) {
                ((Closeable) c).close();
            }
        }
    }
}
