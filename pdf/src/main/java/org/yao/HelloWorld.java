package org.yao;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Creates a "Hello World" PDF using the built-in Helvetica font.
 *
 * <p>The example is taken from the PDF file format specification.
 */
public final class HelloWorld {
  private HelloWorld() {}

  public static void main(String[] args) throws IOException {


    String filename = "1.pdf";
    String message = "hello, world";

    try (PDDocument doc = new PDDocument()) {
      PDPage page = new PDPage();
      doc.addPage(page);

      PDFont font = PDType1Font.HELVETICA_BOLD;

      try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
        contents.beginText();
        contents.setFont(font, 12);
        contents.newLineAtOffset(100, 700);
        contents.showText(message);
        contents.endText();
      }

      doc.save(filename);
    }
  }
}
