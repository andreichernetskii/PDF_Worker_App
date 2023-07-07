import org.apache.pdfbox.multipdf.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.control.Label;
import java.awt.*;
import org.apache.pdfbox.pdmodel.PDDocument;


public class PDFWorker {
    public void pdfMerge(List<File> fileList, String savePath, Label statusLabel) {
        String fileName = fileNaming(savePath, "/merged_file", ".pdf");
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();

        try {
            for (File o : fileList) pdfMergerUtility.addSource(o);
            pdfMergerUtility.setDestinationFileName(fileName);
            pdfMergerUtility.mergeDocuments();
            statusLabel.setText("Done!");
            statusLabel.setVisible(true);
//            callFileManager(fileName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void pdfReduceSize(List<File> fileList, String savePath, Label statusLabel) {
//        String fileName = fileNaming(savePath, "/resized_file", ".pdf");
//        File operationFile = new File(fileName);
//        try {
//            PDDocument document = PDDocument.load(operationFile);
//            PDFOptimizer optimizer = new PDFOptimizer();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    // TODO: make adding number to duplicate file name function better
    private String fileNaming(String filePath, String fileName, String fileType) {
        int counter = 0;
        Path path = Paths.get(filePath + fileName + fileType);

        while(Files.exists(path)) {
            counter++;
            path = Paths.get(filePath + fileName + "_" + counter + fileType);
        }

        return path.toString();
    }

    private void callFileManager(String filePath) {
        Desktop desktop = Desktop.getDesktop();

        if (desktop.isSupported(Desktop.Action.BROWSE_FILE_DIR)) {
            File file = new File(filePath);
            desktop.browseFileDirectory(file);
        } else {
            System.out.println("Browse file directory action is not supported!");
        }
    }
}
