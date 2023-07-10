import com.itextpdf.kernel.pdf.*;
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
    private static final String TYPE = ".pdf";
    private final List<File> fileList;
    private final String savePath;
    private final boolean merge;
    private final boolean rescale;

    // dla skrócenia lsity argumentów i ich powtórki
    public PDFWorker(List<File> fileList, String savePath, boolean merge, boolean rescale) {
        this.fileList = fileList;
        this.savePath = savePath;
        this.merge = merge;
        this.rescale = rescale;
    }

    public void startProcess() {
        if (merge && rescale) {
            File merged = pdfMerge();
            pdfReduceSize(merged);
        } else if (rescale) {
            pdfReduceSize(fileList.get(0));
        } else if (merge) {
            pdfMerge();
        }
    }

    private File pdfMerge() {
        String saveFileName = fileNaming(savePath, "/merged_file");

        try {
            PdfDocument mergedDocument = new PdfDocument(new PdfWriter(saveFileName));
            PdfMerger merger = new PdfMerger(mergedDocument);

            for (File file : fileList) {
                PdfDocument sourceDocument = new PdfDocument(new PdfReader(file));
                merger.merge(sourceDocument, 1, sourceDocument.getNumberOfPages());
                sourceDocument.close();
            }

            mergedDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(saveFileName);
    }

    private void pdfReduceSize(File file) {
        try {
            PdfReader reader = new PdfReader(file);
            String fileName = fileNaming(savePath, "/rescaled_file");
            PdfWriter writer = new PdfWriter(fileName, new WriterProperties().setCompressionLevel(CompressionConstants.BEST_COMPRESSION));
            PdfDocument document = new PdfDocument(reader, writer);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileNaming(String filePath, String fileName) {
        int counter = 0;
        String template = filePath + fileName + "%s" + TYPE;
        Path path = Paths.get(String.format(template, ""));

        while (Files.exists(path)) {
            counter++;
            path = Paths.get(String.format(template, "_" + counter));
        }

        return path.toString();
    }
}
