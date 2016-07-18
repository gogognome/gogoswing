package nl.gogognome.lib.swing.models;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * This class implements a model to select a file.
 * A button id and file filter can be set. These are used
 * by the FileSelectionBean when the file chooser dialog is shown.
 */
public class FileModel extends AbstractModel<File> {

    private String buttonId = "gen.choose";
    private FileFilter fileFilter;

    public FileModel() {
    }

    public FileModel(File file) {
        super(file);
    }

    public File getFile() {
        return getValue();
    }

    /**
     * Sets the file of this model.
     * @param newFile the new value of the file
     * @param source the model change listener that sets the file. It will not get notified. It may be null.
     */
    public void setFile(File newFile, ModelChangeListener source) {
        setValue(newFile, source);
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
