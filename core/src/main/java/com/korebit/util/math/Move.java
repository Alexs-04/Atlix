package com.korebit.util.math;

public class Move {
    public static float calculeteVelocity(float velocity, float acceleration, float delta) {
        velocity += acceleration * delta;
        if (velocity > Const.MAX_SPEED) {
            velocity = Const.MAX_SPEED;
        } else if (velocity < -Const.MAX_SPEED) {
            velocity = -Const.MAX_SPEED;
        }
        return velocity;
    }
}
