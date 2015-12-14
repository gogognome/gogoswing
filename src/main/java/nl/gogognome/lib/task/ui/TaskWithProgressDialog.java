package nl.gogognome.lib.task.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import nl.gogognome.lib.swing.MessageDialog;
import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.task.Task;
import nl.gogognome.lib.task.TaskProgressListener;

/**
 * This class can execute a task and show the progress of the task in a dialog.
 * The task will be executed on a worker thread.
 */
public class TaskWithProgressDialog implements TaskProgressListener {

    private Window parentWindow;

    private JProgressBar progressBar;
    private JDialog progressDialog;

    private String description;

    public TaskWithProgressDialog(Window parentWindow, String description) {
    	this.parentWindow = parentWindow;
    	this.description = description;
    }

    public TaskWithProgressDialog(View view, String description) {
    	this.parentWindow = view.getParentWindow();
    	this.description = description;
    }

    /**
     * Executes the task while a dialog shows the progress of the task.
     * @param task the task
     * @return the result of the task
     */
    public Object execute(Task task) {
    	WorkerThread thread = new WorkerThread(task, description, this);
    	thread.start();
   		progressDialog = new JDialog(parentWindow);

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
    	Dimension d = parentWindow.getSize();;
    	Point location = parentWindow.getLocation();
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
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
			public void run() {
    			progressDialog.setVisible(false);
		    	if (e != null) {
		            MessageDialog.showErrorMessage(parentWindow, e,
		            		"taskWithProgressDialog.finishedWithException");
		    	}
    		}
    	});
    }

    @Override
	public void onProgressUpdate(final int percentageCompleted) {
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
			public void run() {
    			progressBar.setValue(percentageCompleted);
    		}
    	});
    }

    private class WorkerThread extends Thread {
    	private TaskWithProgressDialog processDialog;
    	private Task task;
    	private Object result;
    	private Object lock = new Object();

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
