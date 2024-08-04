package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for renderer.ImageWriter class
 * @author Naomi and Noam
 */
class ImageWriterTest {

    /**
     * Test method for
     * {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void writeToImage() {
        int nX = 800;
        int nY = 500;
        int gapX = nX / 16;
        int gapY = nY / 10;

        ImageWriter imageWriter = new ImageWriter("Grid", nX, nY);
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % gapY == 0 || j % gapX == 0) {
                    imageWriter.writePixel(j, i, Color.BLACK);
                } else {
                    //writing purple pixel
                    imageWriter.writePixel(j, i, new Color(125, 0, 125));
                }
            }
        }
        imageWriter.writeToImage();
    }
}