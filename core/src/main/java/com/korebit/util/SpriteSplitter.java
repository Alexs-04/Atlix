package com.korebit.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.List;

public class SpriteSplitter {

    /**
     * Determina si un píxel es gris (canales RGB muy similares)
     * @param pixel valor RGBA (bits: R 24-31, G 16-23, B 8-15, A 0-7)
     * @param maxChannelDiff diferencia máxima permitida entre R, G y B
     */
    private static boolean isGrayPixel(int pixel, int maxChannelDiff) {
        int r = (pixel >> 24) & 0xff;
        int g = (pixel >> 16) & 0xff;
        int b = (pixel >> 8) & 0xff;
        int maxDiff = Math.max(Math.abs(r - g), Math.max(Math.abs(g - b), Math.abs(r - b)));
        return maxDiff <= maxChannelDiff;
    }

    private static List<Integer> detectLines(Pixmap pixmap, int maxChannelDiff, boolean vertical, float noiseTolerance) {
        List<Integer> lines = new ArrayList<>();
        int outerLimit = vertical ? pixmap.getWidth() : pixmap.getHeight();
        int innerLimit = vertical ? pixmap.getHeight() : pixmap.getWidth();
        int minGrayPixels = (int)(innerLimit * (1.0f - noiseTolerance));

        for (int outer = 0; outer < outerLimit; outer++) {
            int grayCount = 0;
            for (int inner = 0; inner < innerLimit; inner++) {
                int x = vertical ? outer : inner;
                int y = vertical ? inner : outer;
                if (isGrayPixel(pixmap.getPixel(x, y), maxChannelDiff)) {
                    grayCount++;
                }
            }
            if (grayCount >= minGrayPixels) {
                lines.add(outer);
            }
        }
        return lines;
    }

    public static List<TextureRegion> splitByGrayLines(Texture texture, int maxChannelDiff, float noiseTolerance) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();

        List<Integer> xLines = detectLines(pixmap, maxChannelDiff, true, noiseTolerance);
        List<Integer> yLines = detectLines(pixmap, maxChannelDiff, false, noiseTolerance);

        System.out.println("Líneas X detectadas: " + xLines);
        System.out.println("Líneas Y detectadas: " + yLines);

        // Agregar bordes
        xLines.addFirst(-1);
        xLines.add(pixmap.getWidth());
        yLines.addFirst(-1);
        yLines.add(pixmap.getHeight());

        List<TextureRegion> frames = extractRegions(texture, pixmap.getWidth(), pixmap.getHeight(), yLines, xLines);
        pixmap.dispose();
        return frames;
    }

    private static List<TextureRegion> extractRegions(Texture texture, int imgWidth, int imgHeight,
                                                      List<Integer> yLines, List<Integer> xLines) {
        List<TextureRegion> frames = new ArrayList<>();
        for (int i = 0; i < yLines.size() - 1; i++) {
            int yStart = yLines.get(i) + 1;
            int yEnd = yLines.get(i + 1) - 1;
            if (yStart >= yEnd) continue;
            for (int j = 0; j < xLines.size() - 1; j++) {
                int xStart = xLines.get(j) + 1;
                int xEnd = xLines.get(j + 1) - 1;
                if (xStart >= xEnd) continue;
                int w = xEnd - xStart + 1;
                int h = yEnd - yStart + 1;
                if (w > 0 && h > 0 && xStart + w <= imgWidth && yStart + h <= imgHeight) {
                    frames.add(new TextureRegion(texture, xStart, yStart, w, h));
                }
            }
        }
        return frames;
    }

    // Método con valores por defecto: maxChannelDiff=12, noiseTolerance=0.05f
    public static List<TextureRegion> splitByGrayLines(Texture texture) {
        return splitByGrayLines(texture, 12, 0.05f);
    }
}
