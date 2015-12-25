package nl.gogognome.lib.swing.action;

import nl.gogognome.lib.swing.MessageDialog;
import nl.gogognome.lib.swing.RunnableWithException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Actions {

    public static Action build(Component parentComponent, RunnableWithException runnable) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    runnable.run();
                } catch (Exception e) {
                    MessageDialog.showMessage(parentComponent, "gen.error", "gen.problemOccurred");
                }
            }
        };
    }

}
