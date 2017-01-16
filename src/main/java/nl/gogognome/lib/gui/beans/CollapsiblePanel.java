package nl.gogognome.lib.gui.beans;

import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.swing.views.JDialogViewOwner;
import nl.gogognome.lib.swing.views.JFrameViewOwner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollapsiblePanel extends JPanel {

    private JPanel headerPanel;
    private JPanel contentsPanel;

    private BooleanModel collapsedModel = new BooleanModel();

    public CollapsiblePanel(Component contents) {
        this(null, contents);
    }

    public CollapsiblePanel(String title, Component contents) {
        setLayout(new BorderLayout());

        headerPanel = createHeader(title);
        add(headerPanel, BorderLayout.NORTH);

        contentsPanel = createContentsPanel(contents);
        add(contentsPanel, BorderLayout.CENTER);

        collapsedModel.addModelChangeListener(m -> onToggle());
    }

    private JPanel createHeader(String title) {
        HeaderPanel panel = new HeaderPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(panel.getBackground().darker());
        if (title == null) {
            title = " ";
        }
        panel.add(new JLabel(title), BorderLayout.NORTH);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                collapsedModel.setBoolean(!collapsedModel.getBoolean());
                onToggle();
            }
        });

        return panel;
    }

    private JPanel createContentsPanel(Component contents) {
        JPanel contentsPanel = new JPanel(new BorderLayout());
        contentsPanel.add(contents, BorderLayout.CENTER);
        contentsPanel.setVisible(collapsedModel.getBoolean());
        return contentsPanel;
    }

    private void onToggle() {
        contentsPanel.setVisible(collapsedModel.getBoolean());
        Component root = SwingUtilities.getRoot(this);
        if (root instanceof JDialog) {
            new JDialogViewOwner((JDialog) root).invalidateLayout();
        }
        if (root instanceof JFrame) {
            new JFrameViewOwner((JFrame) root).invalidateLayout();
        }
    }

    private class HeaderPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(Color.BLACK);
            int[] xs = {2, 12, 7};
            int[] ys;
            if (collapsedModel.getBoolean()) {
                ys = new int[]{12, 12, 2};
            } else {
                ys = new int[]{2, 2, 12};
            }
            for (int i=0; i<xs.length; i++) {
                xs[i] += getWidth() - 15;
            }
            g.fillPolygon(xs, ys, xs.length);
        }
    }
}
