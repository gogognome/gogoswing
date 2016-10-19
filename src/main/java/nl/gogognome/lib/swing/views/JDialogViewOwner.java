package nl.gogognome.lib.swing.views;

import javax.swing.*;

public class JDialogViewOwner implements ViewOwner {

    private final JDialog dialog;

    public JDialogViewOwner(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public JDialog getWindow() {
        return dialog;
    }

    @Override
    public void setDefaultButton(JButton button) {
        dialog.getRootPane().setDefaultButton(button);
    }

    @Override
    public void invalidateLayout() {
        dialog.pack();
    }
}
