package org.home.hone.dip;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Seal {
    public static int seal(String seal, String source, String dest, int marginX, int marginY, float intense) {
        try {
            BufferedImage sourceImage = ImageIO.read(new File(source));
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

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        if (args.length < 2) {
            System.err.println("Error: invalid usage");
            System.exit(1);
        }
        else {
            String seal = args[0];

            for (int i = 1; i < args.length; i++) {
                String src = args[i];
                String dest = String.format("%s-java-sealed.jpg", src.split("\\.", 2)[0]);
                Seal.seal(seal, src, dest, 200, 240, 1.0f);
            }
        }
    }
}
