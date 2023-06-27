import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
    private TextField FilesPathField;

    @FXML
    private Button StartButton;

    @FXML
    private TextField SavePathField;

    @FXML
    public Label statusLabel;

    private List<File> fileList;
    private String savePath;

    @FXML
    void initialize() {
        assert BrowseFilesButton != null : "fx:id=\"BrowseFilesButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert BrowseSavePathButton != null : "fx:id=\"BrowseSavePathButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert CloseButton != null : "fx:id=\"CloseButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert FilesPathField != null : "fx:id=\"FilesPathField\" was not injected: check your FXML file 'sample.fxml'.";
        assert SavePathField != null : "fx:id=\"SavePathField\" was not injected: check your FXML file 'sample.fxml'.";
        assert StartButton != null : "fx:id=\"StartButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'sample.fxml'.";
    }

    @FXML
    private void applicationTerminate() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openFile() {
        Stage stage = (Stage)BrowseFilesButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.setTitle("Please choose the file(-s)");
        fileChooser.getExtensionFilters().add(extFilter);
        fileList = fileChooser.showOpenMultipleDialog(stage);
        if (fileList != null) FilesPathField.appendText(fileList.get(0).getAbsolutePath());
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
