package com.korebit.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.korebit.util.SpriteSplitter;

import java.util.List;

public class KulaActor extends Player {

    private final Texture[] texture;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> moveAnimation;

    private float stateTime = 0;
    private TextureRegion currentFrame;

    public KulaActor() {

        texture = new Texture[]{
            new Texture("sprites/idle-kula.png"),
            new Texture("sprites/move-kula.png")
        };

        // Idle
        List<TextureRegion> idleFrames = SpriteSplitter.splitByGrayLines(texture[0]).subList(0, 18);
        idleAnimation = new Animation<>(0.1f, idleFrames.toArray(new TextureRegion[0]));

        // Move
        List<TextureRegion> moveFrames = SpriteSplitter.splitByGrayLines(texture[1]).subList(0, 10);
        moveAnimation = new Animation<>(0.1f, moveFrames.toArray(new TextureRegion[0]));

        currentFrame = idleAnimation.getKeyFrame(0);

        // Tamaño del actor basado en el sprite
        setBounds(100, 200,
            currentFrame.getRegionWidth() * 4f,
            currentFrame.getRegionHeight() * 4f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;

        super.handleMovement(delta);

        // Animación
        if (getVelocity() != 0) {
            currentFrame = moveAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float width = getWidth();
        float height = getHeight();

        if (isFacingRight()) {
            batch.draw(currentFrame, getX(), getY(), width, height);
        } else {
            // Flip manual sin modificar la región
            batch.draw(currentFrame,
                getX() + width, getY(),
                -width, height);
        }
    }

    public void dispose() {
        texture[0].dispose();
        texture[1].dispose();
    }
}
