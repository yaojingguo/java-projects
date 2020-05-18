package org.yao;

import java.io.IOException;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.function.PDFunctionType2;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;

/**
 * This example shows how to use a separation color / spot color. Here it is a placeholder for gold,
 * and it is displayed as yellow. You can see the colorspace in PDFDebugger by going to
 * "Root/Pages/Kids/[0]/Resources/ColorSpace/cs1".
 *
 * @author Tilman Hausherr
 */
public class CreateSeparationColorBox {
  private CreateSeparationColorBox() {}

  public static void main(String[] args) throws IOException {
    PDDocument doc = new PDDocument();
    PDPage page = new PDPage();
    doc.addPage(page);

    COSArray separationArray = new COSArray();
    separationArray.add(COSName.SEPARATION); // type
    separationArray.add(
        COSName.getPDFName("Gold")); // the name, e.g. metallic, fluorescent, glitter
    separationArray.add(COSName.DEVICERGB); // alternate colorspace

    // tint transform function, results between C0=white (1 1 1) and C1=yellow (1 1 0)
    COSDictionary fdict = new COSDictionary();
    fdict.setInt(COSName.FUNCTION_TYPE, 2);
    COSArray range = new COSArray();
    range.add(COSInteger.ZERO);
    range.add(COSInteger.ONE);
    range.add(COSInteger.ZERO);
    range.add(COSInteger.ONE);
    range.add(COSInteger.ZERO);
    range.add(COSInteger.ONE);
    fdict.setItem(COSName.RANGE, range);
    COSArray domain = new COSArray();
    domain.add(COSInteger.ZERO);
    domain.add(COSInteger.ONE);
    fdict.setItem(COSName.DOMAIN, domain);
    COSArray c0 = new COSArray();
    c0.add(COSInteger.ONE);
    c0.add(COSInteger.ONE);
    c0.add(COSInteger.ONE);
    fdict.setItem(COSName.C0, c0);
    COSArray c1 = new COSArray();
    c1.add(COSInteger.ONE);
    c1.add(COSInteger.ONE);
    c1.add(COSInteger.ZERO);
    fdict.setItem(COSName.C1, c1);
    fdict.setInt(COSName.N, 1);
    PDFunctionType2 func = new PDFunctionType2(fdict);
    separationArray.add(func);

    PDColorSpace spotColorSpace = new PDSeparation(separationArray);

    PDPageContentStream cs = new PDPageContentStream(doc, page);
    PDColor color = new PDColor(new float[] {0.5f}, spotColorSpace);
    cs.setStrokingColor(color);
    cs.setLineWidth(10);
    cs.addRect(50, 50, 500, 700);
    cs.stroke();
    cs.close();
    doc.save("gold.pdf");
    doc.close();
  }
}
