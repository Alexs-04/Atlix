package com.korebit.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.korebit.util.EntityState;

import static com.korebit.util.math.Const.*;
import static com.korebit.util.math.Move.calculeteVelocity;

public class Soldier extends Actor {

    private final TextureAtlas texture;
    private final Animation<TextureRegion> idle;
    private final Animation<TextureRegion> walk;
    private final Animation<TextureRegion> dead;

    private EntityState entityState;

    private final Rectangle bounds;

    private float stateTime = 0;
    private float velocity = 0;

    // Control de muerte
    private float deathTimer = 0;
    private boolean deathAnimationFinished = false;

    private boolean facingRight = true;
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

        stateTime += delta;

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
    private void handleMovement(float delta) {
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        float acceleration;

        if (left && !right) {
            acceleration = -ACCEL;
            facingRight = false;
        } else if (right && !left) {
            acceleration = ACCEL;
            facingRight = true;
        } else {
            if (velocity > 0) acceleration = -FRICTION;
            else if (velocity < 0) acceleration = FRICTION;
            else acceleration = 0;
        }

        velocity = calculeteVelocity(velocity, acceleration, delta);

        if (!left && !right &&
            Math.signum(velocity) != Math.signum(velocity - acceleration * delta)) {
            velocity = 0;
        }

        moveBy(velocity * delta, 0);

        entityState = (velocity != 0) ? EntityState.WALK : EntityState.IDLE;
    }

    // -----------------------------
    // MUERTE
    // -----------------------------
    private void handleDeath(float delta) {

        if (!deathAnimationFinished && dead.isAnimationFinished(stateTime)) {
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
            case IDLE, INVULNERABLE -> currentFrame = idle.getKeyFrame(stateTime, true);
            case WALK -> currentFrame = walk.getKeyFrame(stateTime, true);
            case DEAD -> currentFrame = dead.getKeyFrame(stateTime, false);
        }
    }

    // -----------------------------
    // COLISIÓN
    // -----------------------------
    public void onCollision(WarTank tank) {
        if (entityState != EntityState.DEAD) {
            entityState = EntityState.DEAD;
            stateTime = 0;                 // 👈 clave
            deathAnimationFinished = false;
            deathTimer = 0;
            velocity = 0;
        }
    }

    // -----------------------------
    // RESET
    // -----------------------------
    private void reset() {
        entityState = EntityState.IDLE;
        stateTime = 0;
        velocity = 0;
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

        if (facingRight) {
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
