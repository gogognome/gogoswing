package nl.gogognome.lib.swing.dialogs;

import nl.gogognome.lib.gui.beans.CollapsiblePanel;
import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.swing.views.ViewDialog;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class MessageDialog {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Return value from class method if YES is chosen. */
    public static final int YES_OPTION = JOptionPane.YES_OPTION;

    /** Return value from class method if NO is chosen. */
    public static final int NO_OPTION = JOptionPane.NO_OPTION;

    /** Return value from class method if CANCEL is chosen. */
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

    /** Return value form class method if OK is chosen. */
    public static final int OK_OPTION = JOptionPane.OK_OPTION;

    /** Return value from class method if user closes window without selecting
     * anything, more than likely this should be treated as either a
     * <code>CANCEL_OPTION</code> or <code>NO_OPTION</code>. */
    public static final int CLOSED_OPTION = JOptionPane.CLOSED_OPTION;

    private final TextResource textResource;
    private final Component parentComponent;

    public MessageDialog(TextResource textResource, Component parentComponent) {
        this.textResource = textResource;
        this.parentComponent = parentComponent;
    }

    /**
     * Shows a dialog with a yes and no button. The dialog further contains a title and a message.
     * @param titleId the id of the title
     * @param resourceId the id of the message
     * @param args optional arguments to be filled in the placeholders of the message
     * @return {@link #YES_OPTION}, {@link #NO_OPTION} or {@link #CLOSED_OPTION}.
     */
    public int showYesNoQuestion(String titleId, String resourceId, Object...args) {
        return showConfirmationDialog(JOptionPane.YES_NO_OPTION, titleId, resourceId, args);
    }

    /**
     * Shows a dialog with a yes, no and cancel button. The dialog further contains a title and a message.
     * @param titleId the id of the title
     * @param resourceId the id of the message
     * @param args optional arguments to be filled in the placeholders of the message
     * @return {@link #YES_OPTION}, {@link #NO_OPTION}, {@link #CANCEL_OPTION} or {@link #CLOSED_OPTION}.
     */
    public int showYesNoCancelQuestion(String titleId, String resourceId, Object...args) {
        return showConfirmationDialog(JOptionPane.YES_NO_CANCEL_OPTION, titleId, resourceId, args);
    }

    private int showConfirmationDialog(int type, String titleId, String messageId, Object...args) {
        TextResource tr = Factory.getInstance(TextResource.class);
        String title = tr.getString(titleId);
        String message = tr.getString(messageId, args);
        return JOptionPane.showConfirmDialog(parentComponent, message, title, type);
    }

    /**
     * Shows an info message dialog.
     * @param resourceId the id of the message to be shown to the user.
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public void showInfoMessage(String resourceId, Object... args) {
        TextResource tr = Factory.getInstance(TextResource.class);
        JOptionPane.showMessageDialog(parentComponent, tr.getString(resourceId, args),
                tr.getString("gen.titleMessage"), JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Shows a warning message dialog.
     * @param resourceId the id of the message
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public void showWarningMessage(String resourceId, Object... args) {
        TextResource tr = Factory.getInstance(TextResource.class);
        String message = tr.getString(resourceId, args);
        showFormattedMessage(parentComponent, textResource.getString("gen.titleWarning"), message, emptyList());
    }

    /**
     * Shows an error message dialog. The message is logged as a warning.
     * @param resourceId the id of the message to be shown to the user.
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public void showErrorMessage(String resourceId, Object... args) {
        String message = textResource.getString(resourceId, args);
        logger.warn(message);
        showFormattedMessage(parentComponent, textResource.getString("gen.titleError"), message, emptyList());
    }

    /**
     * Shows an error message dialog. The message and the stack trace of the throwable are logged as a warning.
     * The stack trace of the throwable is shown in the details of the dialog.
     * @param throwable a throwable
     * @param resourceId the id of the message to be shown to the user.
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public void showErrorMessage(Throwable throwable, String resourceId, Object... args) {
        List<String> lines = new ArrayList<>();
        String message = textResource.getString(resourceId, args);
        lines.add(message);

        logger.warn(message, throwable);

        addStackTraceToLines(lines, throwable);

        showFormattedMessage(parentComponent, textResource.getString("gen.titleError"), throwable.getLocalizedMessage(), lines);
    }

    private void showFormattedMessage(Component parentComponent, String title, String message, List<String> details) {
        ViewDialog viewDialog = new ViewDialog(parentComponent, new MessageView(title, message, details));
        viewDialog.showDialog();
    }

    private void addStackTraceToLines(List<String> lines, Throwable throwable) {
        while (throwable != null) {
            lines.add(throwable.getClass().getName() + ": " + throwable.getMessage());
            for (StackTraceElement ste : throwable.getStackTrace()) {
                lines.add(ste.toString());
            }

            throwable = getCause(throwable);
        }
    }

    private Throwable getCause(Throwable t) {
        Throwable cause = t.getCause();
        if (cause == t) {
            cause = null;
        }
        return cause;
    }

    private static class MessageView extends View {

        private final String title;
        private final String message;
        private final List<String> details;

        private JButton showDetailsButton;

        public MessageView(String title, String message, List<String> details) {
            this.title = title;
            this.message = message;
            this.details = details;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public void onInit() {
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            setLayout(new BorderLayout());
            add(new JLabel(message), BorderLayout.NORTH);

            if (details.isEmpty()) {
                return;
            }

            CollapsiblePanel collapsiblePanel = new CollapsiblePanel(textResource.getString("gen.details"), buildDetailsComponent());
            collapsiblePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            add(collapsiblePanel, BorderLayout.CENTER);
        }

        private Component buildDetailsComponent() {
            JTextArea textArea = new JTextArea(String.join("\n", details));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            return scrollPane;
        }

        @Override
        public void onClose() {
        }
    }
}
