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
    private List<File> fileList;
    private String savePath;
    @FXML
    private ComboBox<File> comboBox;
    @FXML
    private Label invisibleLabel;
    @FXML
    private Label invisibleLabel2;
    @FXML
    private ImageView openFileHelpIcon;
    @FXML
    private ImageView saveFileHelpIcon;
    @FXML
    private Tooltip toolTipOpenFiles;
    @FXML
    private Tooltip toolTipSaveFile;


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
        assert openFileHelpIcon != null : "fx:id=\"openFileHelpIcon\" was not injected: check your FXML file 'sample.fxml'.";
        assert saveFileHelpIcon != null : "fx:id=\"saveFileHelpIcon\" was not injected: check your FXML file 'sample.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'sample.fxml'.";
        assert toolTipOpenFiles != null : "fx:id=\"toolTipOpenFiles\" was not injected: check your FXML file 'sample.fxml'.";
        assert toolTipSaveFile != null : "fx:id=\"toolTipSaveFile\" was not injected: check your FXML file 'sample.fxml'.";


        // EVENTS:

        // Show tip event for open files help icon
        Events.setEventsMouseEnteredExitedForTipShowHide(openFileHelpIcon, toolTipOpenFiles, HelpText.OPEN_FILES_TIP.label);
        // Show tip event for save file help icon
        Events.setEventsMouseEnteredExitedForTipShowHide(saveFileHelpIcon, toolTipSaveFile, HelpText.SAVE_FILE_TIP.label);
    }

    @FXML
    private void applicationTerminate() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    // TODO: split this method to few little methods
    @FXML
    private void openFile() {
        Stage stage = (Stage)BrowseFilesButton.getScene().getWindow();

        if ( (fileList != null) && (comboBox.getItems() != null) ) {
            fileList.clear();
            comboBox.getItems().clear();
        }

        // Maybe it will be good to place inside ModificatedFileChooser
        ModificatedFileChooser fileChooser = new ModificatedFileChooser();
        fileChooser.setExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.setTitle("Please choose the file(-s)");
        // and feel the fileList via ModificatedFileChooser  public method
        fileList = fileChooser.showOpenMultipleDialog(stage);

        if (fileList != null) {
            comboBox.getItems().addAll(fileList);
            // Delete selected element from combo box and list if they are not null
            Events.addComboBoxDeleteElementEvent(comboBox, fileList);
        }
    }

    @FXML
    private void saveFilePath() {
        Stage stage = (Stage)BrowseSavePathButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chose directory for saving");
        savePath = directoryChooser.showDialog(stage).getAbsolutePath();
        SavePathField.appendText(savePath);
    }

    @FXML
    private void startProcess() {
        try {
            if (fileList.size() > 1 ) {
                PDFWorker pdfWorker = new PDFWorker();
                pdfWorker.pdfMerge(fileList, savePath, statusLabel);
            }
            else {
                statusLabel.setText("Error. You have selected only one document!");
                statusLabel.setVisible(true);
            }
        } catch (NullPointerException e) {
            statusLabel.setText("Error. You have nothing selected");
            statusLabel.setVisible(true);
        }
    }
}
