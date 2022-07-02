package org.yao;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * This is an example that creates a reads a document and adds an image to it..
 *
 * <p>The example is taken from the pdf file format specification.
 *
 * @author Ben Litchfield
 */
public class AddImageToPDF {
  /**
   * Add an image to an existing PDF document.
   *
   * @param inputFile The input PDF to add the image to.
   * @param imagePath The filename of the image to put in the PDF.
   * @param outputFile The file to write to the pdf to.
   * @throws IOException If there is an error writing the data.
   */
  public void createPDFFromImage(String inputFile, String imagePath, String outputFile)
      throws IOException {
    // the document
    PDDocument doc = null;
    try {
      doc = Loader.loadPDF(new File(inputFile));

      // we will add the image to the first page.
      PDPage page = doc.getPage(0);

      // createFromFile is the easiest way with an image file
      // if you already have the image in a BufferedImage,
      // call LosslessFactory.createFromImage() instead
      PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
      PDPageContentStream contentStream =
          new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);

      // contentStream.drawImage(ximage, 20, 20 );
      // better method inspired by http://stackoverflow.com/a/22318681/535646
      // reduce this value if the image is too large
      float scale = 1f;
      contentStream.drawImage(
          pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);

      contentStream.close();
      doc.save(outputFile);
    } finally {
      if (doc != null) {
        doc.close();
      }
    }
  }

  /**
   * This will load a PDF document and add a single image on it. <br>
   * see usage() for commandline
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) throws IOException {
    AddImageToPDF app = new AddImageToPDF();

    app.createPDFFromImage("1.pdf", "slide.png", "2.pdf");
  }

  /** This will print out a message telling how to use this example. */
  private void usage() {
    System.err.println("usage: " + this.getClass().getName() + " <input-pdf> <image> <output-pdf>");
  }
}
