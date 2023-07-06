import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModificatedFileChooser {
    private FileChooser fileChooser;

    public ModificatedFileChooser() {
        fileChooser = new FileChooser();
    }

    private List<File> showOpenMultipleDialog(Window ovnerWindow) {
        List<File> list = fileChooser.showOpenMultipleDialog(ovnerWindow);
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    private void setTitle(String str) {
        fileChooser.setTitle(str);
    }

    private void setExtensionFilter(String description, String extension) {
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    public List<File> createFileListOfFolderFiles(String title, String description, String extension, Window window) {
        setTitle(title);
        setExtensionFilter(description, extension);
        List<File> list = showOpenMultipleDialog(window);
        return list;
    }
}
