package org.yao;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

/** This is an example of how to use a text matrix. */
public class UsingTextMatrix {

  /**
   * creates a sample document with some text using a text matrix.
   *
   * @param message The message to write in the file.
   * @param outfile The resulting PDF.
   * @throws IOException If there is an error writing the data.
   */
  public void doIt(String message, String outfile) throws IOException {
    // the document
    PDDocument doc = null;
    try {
      doc = new PDDocument();

      // Page 1
      PDFont font = PDType1Font.HELVETICA;
      PDPage page = new PDPage(PDRectangle.A4);
      doc.addPage(page);
      float fontSize = 12.0f;

      PDRectangle pageSize = page.getMediaBox();
      float centeredXPosition = (pageSize.getWidth() - fontSize / 1000f) / 2f;
      float stringWidth = font.getStringWidth(message);
      float centeredYPosition = (pageSize.getHeight() - (stringWidth * fontSize) / 1000f) / 3f;

      PDPageContentStream contentStream =
          new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);
      contentStream.setFont(font, fontSize);
      contentStream.beginText();
      // counterclockwise rotation
      for (int i = 0; i < 8; i++) {
        contentStream.setTextMatrix(
            Matrix.getRotateInstance(
                i * Math.PI * 0.25, centeredXPosition, pageSize.getHeight() - centeredYPosition));
        contentStream.showText(message + " " + i);
      }
      // clockwise rotation
      for (int i = 0; i < 8; i++) {
        contentStream.setTextMatrix(
            Matrix.getRotateInstance(-i * Math.PI * 0.25, centeredXPosition, centeredYPosition));
        contentStream.showText(message + " " + i);
      }

      contentStream.endText();
      contentStream.close();

      // Page 2
      page = new PDPage(PDRectangle.A4);
      doc.addPage(page);
      fontSize = 1.0f;

      contentStream = new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);
      contentStream.setFont(font, fontSize);
      contentStream.beginText();

      // text scaling and translation
      for (int i = 0; i < 10; i++) {
        contentStream.setTextMatrix(
            new Matrix(12f + (i * 6), 0, 0, 12f + (i * 6), 100, 100f + i * 50));
        contentStream.showText(message + " " + i);
      }
      contentStream.endText();
      contentStream.close();

      // Page 3
      page = new PDPage(PDRectangle.A4);
      doc.addPage(page);
      fontSize = 1.0f;

      contentStream = new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);
      contentStream.setFont(font, fontSize);
      contentStream.beginText();

      int i = 0;
      // text scaling combined with rotation
      contentStream.setTextMatrix(
          new Matrix(12, 0, 0, 12, centeredXPosition, centeredYPosition * 1.5f));
      contentStream.showText(message + " " + i++);

      contentStream.setTextMatrix(
          new Matrix(0, 18, -18, 0, centeredXPosition, centeredYPosition * 1.5f));
      contentStream.showText(message + " " + i++);

      contentStream.setTextMatrix(
          new Matrix(-24, 0, 0, -24, centeredXPosition, centeredYPosition * 1.5f));
      contentStream.showText(message + " " + i++);

      contentStream.setTextMatrix(
          new Matrix(0, -30, 30, 0, centeredXPosition, centeredYPosition * 1.5f));
      contentStream.showText(message + " " + i++);

      contentStream.endText();
      contentStream.close();

      doc.save(outfile);
    } finally {
      if (doc != null) {
        doc.close();
      }
    }
  }

  /**
   * This will create a PDF document with some examples how to use a text matrix.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) throws IOException {
    UsingTextMatrix app = new UsingTextMatrix();
    String pathname = Util.outPathname("UsingTextMatrix.pdf");
    app.doIt("XDF", pathname);
  }

  /** This will print out a message telling how to use this example. */
  private void usage() {
    System.err.println("usage: " + this.getClass().getName() + " <Message> <output-file>");
  }
}
