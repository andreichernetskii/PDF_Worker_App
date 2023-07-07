import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    PDFWorker pdfWorker;
    private List<File> fileList;
    private String savePath;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button BrowseFilesButton;
    @FXML
    private Button BrowseSavePathButton;
    @FXML
    private Button CloseButton;
    @FXML
    private Button StartButton;
    @FXML
    private TextField SavePathField;
    @FXML
    public Label statusLabel;
    @FXML
    private ComboBox<File> comboBox;
    @FXML
    private Label invisibleLabel;
    @FXML
    private Label invisibleLabel2;
    @FXML
    private Label operationTypeLabel;
    @FXML
    private ImageView openFileHelpIcon;
    @FXML
    private ImageView saveFileHelpIcon;
    @FXML
    private Tooltip toolTipOpenFiles;
    @FXML
    private Tooltip toolTipSaveFile;
    @FXML
    private CheckBox mergeCheckBox;
    @FXML
    private CheckBox reduceCheckBox;


    @FXML
    void initialize() {
        assert BrowseFilesButton != null : "fx:id=\"BrowseFilesButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert BrowseSavePathButton != null : "fx:id=\"BrowseSavePathButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert CloseButton != null : "fx:id=\"CloseButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert SavePathField != null : "fx:id=\"SavePathField\" was not injected: check your FXML file 'sample.fxml'.";
        assert StartButton != null : "fx:id=\"StartButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'sample.fxml'.";
        assert invisibleLabel != null : "fx:id=\"invisibleLabel\" was not injected: check your FXML file 'sample.fxml'.";
        assert invisibleLabel2 != null : "fx:id=\"invisibleLabel2\" was not injected: check your FXML file 'sample.fxml'.";
        assert mergeCheckBox != null : "fx:id=\"mergeCheckBox\" was not injected: check your FXML file 'sample.fxml'.";
        assert openFileHelpIcon != null : "fx:id=\"openFileHelpIcon\" was not injected: check your FXML file 'sample.fxml'.";
        assert operationTypeLabel != null : "fx:id=\"operationTypeLabel\" was not injected: check your FXML file 'sample.fxml'.";
        assert reduceCheckBox != null : "fx:id=\"reduceCheckBox\" was not injected: check your FXML file 'sample.fxml'.";
        assert saveFileHelpIcon != null : "fx:id=\"saveFileHelpIcon\" was not injected: check your FXML file 'sample.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'sample.fxml'.";
        assert toolTipOpenFiles != null : "fx:id=\"toolTipOpenFiles\" was not injected: check your FXML file 'sample.fxml'.";
        assert toolTipSaveFile != null : "fx:id=\"toolTipSaveFile\" was not injected: check your FXML file 'sample.fxml'.";

        // EVENTS:

        // Show tip event for open files help icon
        Events.setEventsMouseEnteredExitedForTipShowHide(openFileHelpIcon, toolTipOpenFiles, HelpText.OPEN_FILES_TIP.label);
        // Show tip event for save file help icon
        Events.setEventsMouseEnteredExitedForTipShowHide(saveFileHelpIcon, toolTipSaveFile, HelpText.SAVE_FILE_TIP.label);

        pdfWorker = new PDFWorker();
    }

    @FXML
    private void applicationTerminate() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openFile() {
        Stage stage = (Stage) BrowseFilesButton.getScene().getWindow();

        if ((fileList != null) && (comboBox.getItems() != null)) {
            fileList = null;
            comboBox.getItems().clear();
        }

        // for editing fileList List should be mutable
        // but original FileChooser's showOpenMultipleDialog returns immutable list
        // so here the Modification of File Chooser with mutable List
        fileList = new ModificatedFileChooser().createFileListOfFolderFiles(
                "Please choose the file(-s)",
                "PDF files (*.pdf)",
                "*.pdf",
                stage
        );

        if (fileList != null) {
            comboBox.getItems().addAll(fileList);
            // Delete selected element from combo box and list if they are not null
            Events.addComboBoxDeleteElementEvent(comboBox, fileList);
        }
    }

    @FXML
    private void saveFilePath() {
        Stage stage = (Stage) BrowseSavePathButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chose directory for saving");
        savePath = directoryChooser.showDialog(stage).getAbsolutePath();
        if (savePath == null || savePath.isEmpty()) return;
        SavePathField.appendText(savePath);
    }

    // TODO: from here should start file size reducing too
    // todo: для сжатия сделать условие, чтобы в списке был только один файл
    // start process works by this logic:
    // user can choose what the process he/she need
    // via select checkbox user can choose:
    // merge files or reduce size or both
    // depends on selections startProcess initiate functions
    @FXML
    private void startProcess() {
        if (!isReadyForWork()) return;
        pdfWorker.pdfMerge(fileList, savePath, statusLabel);
    }

    private void setStatusLabelText(String str) {
        statusLabel.setText(null);
        statusLabel.setText(str);
        statusLabel.setVisible(true);
    }

    //Merge | Reduce
    // OK   |  OK -> OK
    // NO   |  NO -> NO
    // OK   |  NO -> OK
    // NO   |  OK -> OK
    private boolean isReadyForWork() {
        if (fileList == null || fileList.isEmpty() || comboBox.getItems().isEmpty() || comboBox.getItems() == null){
            setStatusLabelText("Error. You have not selected files for operations.");
            return false;
        }
        if (!mergeCheckBox.isSelected() && !reduceCheckBox.isSelected()) {
            setStatusLabelText("Error: You have not chosen the operation type.");
            return false;
        }
        if (reduceCheckBox.isSelected()) {
            setStatusLabelText("Error: Reducing file size function is not available now.");
            return false;
        }
        if (savePath == null) {
            setStatusLabelText("Error: Choose the save folder.");
            return false;
        }
        if (fileList.size() == 1 && mergeCheckBox.isSelected()) {
            setStatusLabelText("Error. You have selected only one document!");
            return false;
        }

        return true;
    }
}
