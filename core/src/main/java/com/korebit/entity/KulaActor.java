package com.korebit.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.korebit.util.SpriteSplitter;

import java.util.List;

import static com.korebit.util.math.Const.*;
import static com.korebit.util.math.Move.calculeteVelocity;

public class KulaActor extends Actor {

    private final Texture[] texture;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> moveAnimation;

    private float stateTime = 0;

    private float velocity = 0, acceleration = 0;

    private boolean facingRight = true;
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

        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        // Movimiento
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

        // Actualizar posición
        moveBy(velocity * delta, 0);

        // Animación
        if (velocity != 0) {
            currentFrame = moveAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float width = getWidth();
        float height = getHeight();

        if (facingRight) {
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
