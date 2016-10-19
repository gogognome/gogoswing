package nl.gogognome.lib.swing.views;

import javax.swing.*;
import java.awt.*;

public class JFrameViewOwner implements ViewOwner {

    private final JFrame frame;

    public JFrameViewOwner(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public JFrame getWindow() {
        return frame;
    }

    @Override
    public void setDefaultButton(JButton button) {
        frame.getRootPane().setDefaultButton(button);
    }

    @Override
    public void invalidateLayout() {
        frame.invalidate();
    }
}
