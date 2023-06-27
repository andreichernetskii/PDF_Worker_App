import org.apache.pdfbox.multipdf.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.control.Label;

public class PDFWorker {
    public void pdfMerge(List<File> list, String path, Label statusLabel) {
        String fileName = fileNaming(path, "/merged_file", ".pdf");
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        try {
            for (File o : list) pdfMergerUtility.addSource(o);
            pdfMergerUtility.setDestinationFileName(fileName);
            pdfMergerUtility.mergeDocuments();
            statusLabel.setText("Done!");
            statusLabel.setVisible(true);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fileNaming(String filePath, String fileName, String fileType) {
        Path path = Paths.get(filePath + fileName + fileType);
        if (Files.notExists(path)) return filePath + fileName + fileType;
        else return fileNaming(filePath, fileName + 1, fileType);
    }
}
