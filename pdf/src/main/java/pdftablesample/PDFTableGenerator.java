package pdftablesample;

import java.io.IOException;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;
import org.yao.Util;

public class PDFTableGenerator {

  // Generates document from Table object
  public void generatePDF(Table table) throws IOException {
    PDDocument doc = null;
    String pathname = Util.outPathname("table.pdf");
    try {
      doc = new PDDocument();
      drawTable(doc, table);
      doc.save(pathname);
    } finally {
      if (doc != null) {
        doc.close();
      }
    }
  }

  // Configures basic setup for the table and draws it page by page
  public void drawTable(PDDocument doc, Table table) throws IOException {
    // Calculate pagination
    Integer rowsPerPage =
        new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
    Integer numberOfPages =
        new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

    // Generate each page, get the content and draw it
    for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
      PDPage page = generatePage(doc, table);
      PDPageContentStream contentStream = generateContentStream(doc, page, table);
      String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
      drawCurrentPage(table, currentPageContent, contentStream);
    }
  }

  // Draws current page table grid and border lines and content
  private void drawCurrentPage(
      Table table, String[][] currentPageContent, PDPageContentStream contentStream)
      throws IOException {
    float tableTopY =
        table.isLandscape()
            ? table.getPageSize().getWidth() - table.getMargin()
            : table.getPageSize().getHeight() - table.getMargin();

    // Draws grid and borders
    drawTableGrid(table, currentPageContent, contentStream, tableTopY);

    // Position cursor to start drawing content
    float nextTextX = table.getMargin() + table.getCellMargin();
    // Calculate center alignment for text in cell considering font height
    float nextTextY =
        tableTopY
            - (table.getRowHeight() / 2)
            - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight()
                    / 1000
                    * table.getFontSize())
                / 4);

    // Write column headers
    writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
    nextTextY -= table.getRowHeight();
    nextTextX = table.getMargin() + table.getCellMargin();

    // Write content
    for (int i = 0; i < currentPageContent.length; i++) {
      writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
      nextTextY -= table.getRowHeight();
      nextTextX = table.getMargin() + table.getCellMargin();
    }

    contentStream.close();
  }

  // Writes the content for one line
  private void writeContentLine(
      String[] lineContent,
      PDPageContentStream contentStream,
      float nextTextX,
      float nextTextY,
      Table table)
      throws IOException {
    for (int i = 0; i < table.getNumberOfColumns(); i++) {
      String text = lineContent[i];
      contentStream.beginText();
      contentStream.newLineAtOffset(nextTextX, nextTextY);
      contentStream.showText(text != null ? text : "");
      contentStream.endText();
      nextTextX += table.getColumns().get(i).getWidth();
    }
  }

  private void drawTableGrid(
      Table table,
      String[][] currentPageContent,
      PDPageContentStream contentStream,
      float tableTopY)
      throws IOException {
    // Draw row lines
    float nextY = tableTopY;
    for (int i = 0; i <= currentPageContent.length + 1; i++) {
      contentStream.moveTo(table.getMargin(), nextY);
      contentStream.lineTo(table.getMargin() + table.getWidth(), nextY);
      contentStream.stroke();
      nextY -= table.getRowHeight();
    }

    // Draw column lines
    final float tableYLength =
        table.getRowHeight() + (table.getRowHeight() * currentPageContent.length);
    final float tableBottomY = tableTopY - tableYLength;
    float nextX = table.getMargin();
    for (int i = 0; i < table.getNumberOfColumns(); i++) {
      contentStream.moveTo(nextX, tableTopY);
      contentStream.lineTo(nextX, tableBottomY);
      contentStream.stroke();
      nextX += table.getColumns().get(i).getWidth();
    }
    contentStream.moveTo(nextX, tableTopY);
    contentStream.lineTo(nextX, tableBottomY);
    contentStream.stroke();
  }

  private String[][] getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
    int startRange = pageCount * rowsPerPage;
    int endRange = (pageCount * rowsPerPage) + rowsPerPage;
    if (endRange > table.getNumberOfRows()) {
      endRange = table.getNumberOfRows();
    }
    return Arrays.copyOfRange(table.getContent(), startRange, endRange);
  }

  private PDPage generatePage(PDDocument doc, Table table) {
    PDPage page = new PDPage();
    page.setMediaBox(table.getPageSize());
    page.setRotation(table.isLandscape() ? 90 : 0);
    doc.addPage(page);
    return page;
  }

  private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table)
      throws IOException {
    PDPageContentStream contentStream =
        new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.OVERWRITE, false);
    // User transformation matrix to change the reference when drawing.
    // This is necessary for the landscape position to draw correctly
    if (table.isLandscape()) {
      Matrix matrix = new Matrix(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
      contentStream.transform(matrix);
    }
    contentStream.setFont(table.getTextFont(), table.getFontSize());
    return contentStream;
  }
}
