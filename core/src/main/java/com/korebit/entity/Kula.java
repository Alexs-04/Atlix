package com.korebit.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.korebit.util.SpriteSplitter;

import static com.korebit.util.math.Const.*;
import static com.korebit.util.math.Move.calculeteVelocity;

import java.util.List;

public class Kula {
    private final Texture[] texture;
    private final Sprite sprite;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> moveAnimation;
    private float velocity = 0, acceleration = 0;
    private boolean facingRight = true;  // Dirección actual del sprite

    public Kula() {
        texture = new Texture[]{new Texture("sprites/idle-kula.png"), new Texture("sprites/move-kula.png")};
        List<TextureRegion> allFrames = SpriteSplitter.splitByGrayLines(texture[0]);
        List<TextureRegion> idleFrames = allFrames.subList(0, 18);
        TextureRegion[] idleFramesArray = idleFrames.toArray(new TextureRegion[0]);
        idleAnimation = new Animation<>(0.1f, idleFramesArray);
        sprite = new Sprite(idleFramesArray[0]);

        allFrames.clear();
        allFrames = SpriteSplitter.splitByGrayLines(texture[1]);
        List<TextureRegion> moveFrames = allFrames.subList(0, 10);
        TextureRegion[] moveFramesArray = moveFrames.toArray(new TextureRegion[0]);
        moveAnimation = new Animation<>(0.1f, moveFramesArray);

        sprite.scale(4f);
        sprite.setPosition(100, 200);
    }

    public void update(float delta, float stateTime) {
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        if (left && !right) {
            acceleration = -ACCEL;
            if (facingRight) {
                facingRight = false;
                sprite.setFlip(true, false);
            }
        } else if (right && !left) {
            acceleration = ACCEL;
            if (!facingRight) {
                facingRight = true;
                sprite.setFlip(false, false);
            }
        } else {
            if (velocity > 0) {
                acceleration = -FRICTION;
            } else if (velocity < 0) {
                acceleration = FRICTION;
            } else
                acceleration = 0;
        }

        velocity = calculeteVelocity(velocity, acceleration, delta);

        if (!left && !right && Math.signum(velocity) != Math.signum(velocity - acceleration * delta)) {
            velocity = 0;
        }

        updateAnimation(stateTime);

        sprite.setX(sprite.getX() + velocity * delta);
    }

    public void dispose() {
        texture[0].dispose();
        texture[1].dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }

    private void updateAnimation(float delta) {
        TextureRegion move = moveAnimation.getKeyFrame(delta, true);
        TextureRegion idle = idleAnimation.getKeyFrame(delta, true);

        if (velocity != 0) {
            sprite.setRegion(move);
        } else {
            sprite.setRegion(idle);
        }

        if (!facingRight) {
            sprite.setFlip(true, false);
        }
    }
}
