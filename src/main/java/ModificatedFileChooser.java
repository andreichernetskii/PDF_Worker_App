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

    public List<File> showOpenMultipleDialog(Window ovnerWindow) {
        List<File> list = fileChooser.showOpenMultipleDialog(ovnerWindow);
        return (list != null) ? new ArrayList<>(list) : new ArrayList<>();
    }

    public void setTitle(String str) {
        fileChooser.setTitle(str);
    }

    public void setExtensionFilter(String description, String extension) {
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extensionFilter);
    }
}
