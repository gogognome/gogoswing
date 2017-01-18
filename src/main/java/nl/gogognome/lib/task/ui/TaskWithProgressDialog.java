package nl.gogognome.lib.task.ui;

import nl.gogognome.lib.swing.dialogs.MessageDialog;
import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.swing.views.ViewOwner;
import nl.gogognome.lib.task.Task;
import nl.gogognome.lib.task.TaskProgressListener;
import nl.gogognome.lib.text.TextResource;

import javax.swing.*;
import java.awt.*;

/**
 * This class can execute a task and show the progress of the task in a dialog.
 * The task will be executed on a worker thread.
 */
public class TaskWithProgressDialog implements TaskProgressListener {

    private final ViewOwner viewOwner;
    private final MessageDialog messageDialog;

    private JProgressBar progressBar;
    private JDialog progressDialog;

    private String description;

    public TaskWithProgressDialog(ViewOwner viewOwner, TextResource textResource, String resourceId, Object... arguments) {
        this.viewOwner = viewOwner;
        this.description = textResource.getString(resourceId, arguments);
        messageDialog = new MessageDialog(textResource, viewOwner.getWindow());
    }

    public TaskWithProgressDialog(View view, TextResource textResource, String resourceId, Object... arguments) {
        this.viewOwner = view.getViewOwner();
        this.description = textResource.getString(resourceId, arguments);
        messageDialog = new MessageDialog(textResource, view);
    }

    /**
     * Executes the task while a dialog shows the progress of the task.
     * @param task the task
     * @return the result of the task
     */
    public Object execute(Task task) {
        WorkerThread thread = new WorkerThread(task, description, this);
        thread.start();
        progressDialog = new JDialog(viewOwner.getWindow());

        progressBar = new JProgressBar(0, 100);
        JPanel panel = new JPanel(new BorderLayout());
        progressDialog.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel(description), BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        progressDialog.add(panel);
        progressDialog.pack();
        progressDialog.setModal(true);
        progressDialog.setResizable(false);

        // Put dialog in center of parent frame or dialog.
        Dimension d = viewOwner.getWindow().getSize();
        Point location = viewOwner.getWindow().getLocation();
        location.translate((int)(d.getWidth() / 2), (int)(d.getHeight() / 2));
        progressDialog.setLocation(location);

        progressDialog.setVisible(true);
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        return thread.getResult();
    }

    public void onFinished(final Exception e) {
        SwingUtilities.invokeLater(() -> {
            progressDialog.setVisible(false);
            if (e != null) {
                messageDialog.showErrorMessage(e, "taskWithProgressDialog.finishedWithException");
            }
        });
    }

    @Override
    public void onProgressUpdate(final int percentageCompleted) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(percentageCompleted));
    }

    private class WorkerThread extends Thread {
        private TaskWithProgressDialog processDialog;
        private Task task;
        private Object result;
        private final Object lock = new Object();

        public WorkerThread(Task task, String description, TaskWithProgressDialog progressListener) {
            super("worker thread for \"" + description + '"');
            this.task = task;
            this.processDialog = progressListener;
        }

        @Override
        public void run() {
            try {
                Object tempResult = task.execute(processDialog);
                synchronized(lock) {
                    result = tempResult;
                }
                processDialog.onFinished(null);
            } catch (Exception e) {
                processDialog.onFinished(e);
            }
        }

        public Object getResult() {
            synchronized(lock) {
                return result;
            }
        }
    }

}
