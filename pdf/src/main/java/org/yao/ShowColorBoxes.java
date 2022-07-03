package org.yao;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/** Creates a simple document. The example is taken from the pdf file format specification. */
public final class ShowColorBoxes {

  public static void main(String[] args) throws IOException {
    PDDocument doc = new PDDocument();
    try {
      pdf(doc);
    } finally {
      doc.close();
    }
  }

  private static void pdf(PDDocument doc) throws IOException {
    PDPage page = new PDPage();
    doc.addPage(page);

    PDPageContentStream contents = new PDPageContentStream(doc, page);

    // fill the entire background with cyan
    contents.setNonStrokingColor(Color.CYAN);

    float width = page.getMediaBox().getWidth();
    float height = page.getMediaBox().getHeight();
    System.out.printf("width: %f, height: %f\n", width, height);
    contents.addRect(0, 0, width, height);
    contents.fill();

    // draw a red box in the lower left-hand corner
    contents.setNonStrokingColor(Color.RED);
    contents.addRect(10, 10, 100, 100);
    contents.fill();

    contents.close();

    String pathname = Util.outPathname("ShowColorBoxes.pdf");
    doc.save(pathname);
  }
}
