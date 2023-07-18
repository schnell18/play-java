package org.home.hone.pdfcut;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfCutter2 {

    public static int split(String pdfFile) {

        try {
            // load PDF document
            PDFDocument document = new PDFDocument();
            document.load(new File(pdfFile));

            // create renderer
            SimpleRenderer renderer = new SimpleRenderer();
            renderer.setMaxProcessCount(4);

            // set resolution (in DPI)
            renderer.setResolution(144);

            // render
            List<Image> images = renderer.render(document);
            // write images to files to disk as PNG
            try {
                for (int i = 0; i < images.size(); i++) {
                    ImageIO.write((RenderedImage) images.get(i), "jgp", new File((i + 1) + ".jpg"));
                }
            }
            catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        }
        catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        String pdfFile = "/Users/justin/Downloads/filled.pdf";
        split(pdfFile);
    }

}
