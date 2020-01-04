package org.home.hone.pdfcut;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfCutter {

    public static int seal(String seal, BufferedImage sourceImage, String dest, int marginX, int marginY, float intense) {
        try {
            BufferedImage sealImage = ImageIO.read(new File(seal));

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, intense);
            g2d.setComposite(alphaChannel);

            // calculates the coordinate where the image is painted
            int posX = sourceImage.getWidth() - sealImage.getWidth() - marginX;
            int posY = sourceImage.getHeight() - sealImage.getHeight() - marginY;

            // paints the image watermark
            g2d.drawImage(sealImage, posX, posY, null);

            ImageIO.write(sourceImage, "jpg", new File(dest));
            g2d.dispose();
            return 1;
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
        return 0;
    }

    public static int split(String pdfFile) {
        String password = "";
        int dpi = 150;
        try (PDDocument document = PDDocument.load(new File(pdfFile), password)) {

            ImageType imageType = ImageType.RGB;
            PDFRenderer renderer = new PDFRenderer(document);
            int endPage = document.getNumberOfPages() - 1;
            for (int i = 0; i <= endPage; i++) {
                String dest = String.format("%s-%02d-java.jpg", pdfFile.split("\\.", 2)[0], i);
                BufferedImage image = renderer.renderImageWithDPI(i, dpi, imageType);
                ImageIO.write(image, "jpg", new File(dest));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;

    }

    public static int splitAndSeal(String pdfFile, String sealFile) {
        String password = "";
        int dpi = 150;
        try (PDDocument document = PDDocument.load(new File(pdfFile), password)) {

            ImageType imageType = ImageType.RGB;
            PDFRenderer renderer = new PDFRenderer(document);
            int endPage = document.getNumberOfPages() - 1;
            for (int i = 0; i <= endPage; i++) {
                String dest = String.format("%s-%02d-java.jpg", pdfFile.split("\\.", 2)[0], i);
                BufferedImage image = renderer.renderImageWithDPI(i, dpi, imageType);
                ImageIO.write(image, "jpg", new File(dest));
                if (i == endPage) {
                    //seal last page
                    seal(sealFile, image, dest, 200, 600, 1.0f);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;

    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        String pdfFile = "/Users/justin/work/quicksand/playground/filled-ghost4j.pdf";
        String sealFile = "/Users/justin/work/quicksand/seal/seal.png";
        splitAndSeal(pdfFile, sealFile);
    }

}
