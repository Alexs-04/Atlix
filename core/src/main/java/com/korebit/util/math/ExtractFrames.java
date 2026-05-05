package com.korebit.util.math;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class ExtractFrames {
    public static Array<TextureAtlas.AtlasRegion> extractFrames(TextureAtlas atlas, String regionName, int frameCount) {
        Array<TextureAtlas.AtlasRegion> frames = new Array<>();
        for (int i = 0; i < frameCount; i++) {
            System.out.println(i);
            TextureAtlas.AtlasRegion region = atlas.findRegion(regionName + "-" + (i + 1));
            if (region != null) {
                frames.add(region);
            } else {
                System.err.println("Warning: Region not found: " + regionName + "-" + (i + 1));
            }
        }
        return frames;
    }
}
