package com.korebit.screen;

import com.badlogic.gdx.Screen;
import com.korebit.Main;

public abstract class ScreenGame implements Screen {
    protected Main game;

    protected ScreenGame(Main game) {
        this.game = game;
    }
}
