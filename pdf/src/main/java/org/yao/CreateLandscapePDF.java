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

/** This is an example of how to create a page with a landscape orientation. */
public class CreateLandscapePDF {
  /**
   * creates a sample document with a landscape orientation and some text surrounded by a box.
   *
   * @param message The message to write in the file.
   * @param outfile The resulting PDF.
   * @throws IOException If there is an error writing the data.
   */
  public void doIt(String message, String outfile) throws IOException {
    try (PDDocument doc = new PDDocument()) {
      PDFont font = PDType1Font.HELVETICA;
      PDPage page = new PDPage(PDRectangle.A4);
      page.setRotation(90);
      doc.addPage(page);
      PDRectangle pageSize = page.getMediaBox();
      float pageWidth = pageSize.getWidth();
      float fontSize = 12;
      float stringWidth = font.getStringWidth(message) * fontSize / 1000f;
      float startX = 100;
      float startY = 100;
      PDPageContentStream contentStream =
          new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false);

      // add the rotation using the current transformation matrix
      // including a translation of pageWidth to use the lower left corner as 0,0 reference
      contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
      contentStream.setFont(font, fontSize);
      contentStream.beginText();

      contentStream.newLineAtOffset(startX, startY);
      contentStream.showText(message);

      contentStream.newLineAtOffset(0, 100);
      contentStream.showText(message);

      contentStream.newLineAtOffset(100, 100);
      contentStream.showText(message);

      contentStream.endText();

      // Draw the box

      // |
      // |
      contentStream.moveTo(startX - 2, startY - 2);
      contentStream.lineTo(startX - 2, startY + 200 + fontSize);
      contentStream.stroke();

      // |--
      // |
      contentStream.moveTo(startX - 2, startY + 200 + fontSize);
      contentStream.lineTo(startX + 100 + stringWidth + 2, startY + 200 + fontSize);
      contentStream.stroke();

      // |--|
      // |  |
      contentStream.moveTo(startX + 100 + stringWidth + 2, startY + 200 + fontSize);
      contentStream.lineTo(startX + 100 + stringWidth + 2, startY - 2);
      contentStream.stroke();

      // |--|
      // |--|
      contentStream.moveTo(startX + 100 + stringWidth + 2, startY - 2);
      contentStream.lineTo(startX - 2, startY - 2);
      contentStream.stroke();

      contentStream.close();

      doc.save(outfile);
    }
  }

  /**
   * This will create a PDF document with a landscape orientation and some text surrounded by a box.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) throws IOException {
    CreateLandscapePDF app = new CreateLandscapePDF();
    //    if (args.length != 2) {
    //      app.usage();
    //    } else {
    String pathname = Util.outPathname("CreateLandscapePDF.pdf");
    app.doIt("Make China great again", pathname);
  }

}
