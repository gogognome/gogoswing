package nl.gogognome.lib.swing.models;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import nl.gogognome.lib.util.ComparatorUtil;

/**
 * This class implements a model to select a file.
 * A button id and file filter can be set. These are used
 * by the FileSelectionBean when the file chooser dialog is shown.
 */
public class FileModel extends AbstractModel {

	private File file;
	private String buttonId = "gen.choose";
	private FileFilter fileFilter;

	public FileModel() {
		this(null);
	}

	public FileModel(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

    /**
     * Sets the file of this model.
     * @param newFile the new value of the file
     * @param source the model change listener that sets the file. It will not
     *         get notified. It may be <code>null</code>.
     */
	public void setFile(File newFile, ModelChangeListener source) {
        File oldFile = this.file;
        if (!ComparatorUtil.equals(oldFile, newFile)) {
            this.file = newFile;
            notifyListeners(source);
        }
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public FileFilter getFileFilter() {
		return fileFilter;
	}

	public void setFileFilter(FileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}
}
