package com.korebit.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.korebit.state.EntityState;

import static com.korebit.util.math.Const.ACCEL;
import static com.korebit.util.math.Const.FRICTION;
import static com.korebit.util.math.Move.calculeteVelocity;

public abstract class Player extends Actor {
    private boolean facingRight = true;
    private float stateTime = 0;
    private float velocity = 0;
    private EntityState attackState = null;

    public void handleMovement(float delta) {
        // Implement movement logic here
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean shotFire = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT);

        float acceleration;

        if (shotFire) {
            attackState = EntityState.SHOT_FIRE;
            velocity = 0;
            return;
        }

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

        attackState = null;

        moveBy(velocity * delta, 0);
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public boolean isAttackState(){
        return attackState == EntityState.SHOT_FIRE;
    }

    public boolean isFacingRight() {
        return facingRight;
    }
}
