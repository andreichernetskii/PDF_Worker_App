import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.PdfMerger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.control.Label;


public class PDFWorker {
    public void startProcess(List<File> fileList, String savePath, Label statusLabel, boolean merge, boolean rescale) {
        if (merge) {
            pdfMerge(fileList, savePath, statusLabel, false);
        }

        if (rescale) {
            pdfReduceSize(fileList.get(0), savePath, statusLabel);
        }

        if (merge && rescale) {
            pdfMerge(fileList, savePath, statusLabel, true);
        }
    }

    private void pdfMerge(List<File> fileList, String savePath, Label statusLabel, boolean rescale) {
        String fileName = fileNaming(savePath, "/merged_file", ".pdf");

        try {
            PdfDocument mergedDocument = new PdfDocument(new PdfWriter(fileName));
            PdfMerger merger = new PdfMerger(mergedDocument);

            for (File file : fileList) {
                PdfDocument sourceDocument = new PdfDocument(new PdfReader(file));
                merger.merge(sourceDocument, 1, sourceDocument.getNumberOfPages());
                sourceDocument.close();
            }

            mergedDocument.close();
            setStatusLabelText(statusLabel, "Merging file complete!");

            if (rescale) {
                pdfReduceSize(new File(fileName), savePath, statusLabel);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pdfReduceSize(File file, String savePath, Label statusLabel) {
        try {
            PdfReader reader = new PdfReader(file);
            String fileName = fileNaming(savePath, "/rescaled_file", ".pdf");
            PdfWriter writer = new PdfWriter(fileName, new WriterProperties().setCompressionLevel(8));

            PdfDocument document = new PdfDocument(reader, writer);
            document.close();

            setStatusLabelText(statusLabel, "File resizing complete!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fileNaming(String filePath, String fileName, String fileType) {
        int counter = 0;
        Path path = Paths.get(filePath + fileName + fileType);

        while(Files.exists(path)) {
            counter++;
            path = Paths.get(filePath + fileName + "_" + counter + fileType);
        }

        return path.toString();
    }

    private void setStatusLabelText(Label statusLabel, String str) {
        statusLabel.setText(null);
        statusLabel.setText(str);
        statusLabel.setVisible(true);
    }
}
