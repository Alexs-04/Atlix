package com.korebit.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.korebit.util.EntityState;

public class WarTank extends Actor {
    private final TextureAtlas texture;
    private final Animation<TextureRegion> idle;
    private final Animation<TextureRegion> walk;
    private TextureRegion currentFrame;
    private final Rectangle bounds;
    private EntityState state;
    private float stateTime = 0;
    private float sleepTime = 0;
    private float velocity;

    public WarTank() {
        state = EntityState.IDLE;
        texture = new TextureAtlas("sprites/vehicles/dicokkamk2.atlas");
        // Idle
        Array<TextureAtlas.AtlasRegion> idleFrames = texture.findRegions("dicokkamk2/Di_Cokka_MKII_idle");
        idle = new Animation<>(0.4f, idleFrames, Animation.PlayMode.LOOP);

        // Walk
        Array<TextureAtlas.AtlasRegion> walkFrames = texture.findRegions("dicokkamk2/Di_Cokka_MKII_walk");
        walk = new Animation<>(0.2f, walkFrames, Animation.PlayMode.LOOP);
        currentFrame = idleFrames.get(0);

        setBounds(600, 200,
            currentFrame.getRegionWidth() * 3f,
            currentFrame.getRegionHeight() * 3f);

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());

        velocity = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
        sleepTime += delta;

        float acceleration = 0;
        if (sleepTime > 3) {
            if (sleepTime > 8) sleepTime = 0;

            acceleration = -50;

            state = EntityState.WALK;
        } else {
            state = EntityState.IDLE;
            velocity = 0;
        }

        velocity += acceleration * delta;
        moveBy(velocity * delta, 0);
        bounds.set(getX(), getY(), getWidth(), getHeight());

        if (getX() < 0) {
            velocity = 0;
            moveBy(600, 0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        switch (state) {
            case IDLE -> currentFrame = idle.getKeyFrame(stateTime);
            case WALK -> currentFrame = walk.getKeyFrame(stateTime);
        }
        batch.draw(currentFrame, getX(), getY(),
            currentFrame.getRegionWidth() * 3f,
            currentFrame.getRegionHeight() * 3f);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void onCollision(Soldier soldier) {
    }
}
