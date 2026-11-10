package com.korebit.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.korebit.util.EntityState;


public class Soldier extends Player {

    private final TextureAtlas texture;
    private final Animation<TextureRegion> idle;
    private final Animation<TextureRegion> walk;
    private final Animation<TextureRegion> dead;

    private EntityState entityState;

    private final Rectangle bounds;

    // Control de muerte
    private float deathTimer = 0;
    private boolean deathAnimationFinished = false;

    private TextureRegion currentFrame;

    public Soldier() {
        entityState = EntityState.IDLE;

        texture = new TextureAtlas("sprites/soldier/soldier.atlas");

        // Idle
        Array<TextureAtlas.AtlasRegion> idleFrames =
            texture.findRegions("soldier/soldier_idle");
        idle = new Animation<>(0.3f, idleFrames, Animation.PlayMode.LOOP);

        // Walk
        Array<TextureAtlas.AtlasRegion> walkFrames =
            texture.findRegions("soldier/soldier_walk");
        walk = new Animation<>(0.2f, walkFrames, Animation.PlayMode.LOOP);

        // Dead (una sola vez)
        Array<TextureAtlas.AtlasRegion> deadFrames =
            texture.findRegions("soldier/soldier_dead");
        dead = new Animation<>(0.1f, deadFrames, Animation.PlayMode.NORMAL);

        currentFrame = idle.getKeyFrame(0);

        setBounds(100, 200,
            currentFrame.getRegionWidth() * 2f,
            currentFrame.getRegionHeight() * 2f);

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setStateTime(getStateTime() + delta);

        if (entityState == EntityState.DEAD) {
            handleDeath(delta);
        } else {
            handleMovement(delta);
        }

        updateAnimation();
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

    // -----------------------------
    // MOVIMIENTO
    // -----------------------------
    @Override
    public void handleMovement(float delta) {
        super.handleMovement(delta);
        entityState = (getVelocity() != 0) ? EntityState.WALK : EntityState.IDLE;
    }

    // -----------------------------
    // MUERTE
    // -----------------------------
    private void handleDeath(float delta) {

        if (!deathAnimationFinished && dead.isAnimationFinished(getStateTime())) {
            deathAnimationFinished = true;
            deathTimer = 0;
        }

        if (deathAnimationFinished) {
            deathTimer += delta;

            if (deathTimer > 2f) {
                reset(); // respawn o volver a idle
            }
        }
    }

    // -----------------------------
    // ANIMACIÓN
    // -----------------------------
    private void updateAnimation() {
        switch (entityState) {
            case IDLE, INVULNERABLE -> currentFrame = idle.getKeyFrame(getStateTime(), true);
            case WALK -> currentFrame = walk.getKeyFrame(getStateTime(), true);
            case DEAD -> currentFrame = dead.getKeyFrame(getStateTime(), false);
        }
    }

    // -----------------------------
    // COLISIÓN
    // -----------------------------
    public void onCollision(WarTank tank) {
        if (entityState != EntityState.DEAD) {
            entityState = EntityState.DEAD;
            setStateTime(0);                // 👈 clave
            deathAnimationFinished = false;
            deathTimer = 0;
            setVelocity(0);
        }
    }

    // -----------------------------
    // RESET
    // -----------------------------
    private void reset() {
        entityState = EntityState.IDLE;
        setStateTime(0);
        setVelocity(0);
        deathAnimationFinished = false;
        deathTimer = 0;

        setPosition(400, 200); // respawn simple
    }

    // -----------------------------
    // RENDER
    // -----------------------------
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (currentFrame == null) return;

        float width = getWidth();
        float height = getHeight();

        if (isFacingRight()) {
            batch.draw(currentFrame, getX(), getY(), width, height);
        } else {
            batch.draw(currentFrame,
                getX() + width, getY(),
                -width, height);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
