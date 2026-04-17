package com.korebit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.korebit.screen.ScreenBucket;
import com.korebit.screen.ScreenGame;
import com.korebit.screen.ScreenScene;

//import com.korebit.screen.ScreenAnimation;
//import com.korebit.screen.ScreenGame;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    public SpriteBatch spriteBatch;
    public ScreenGame currentScreen;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        //screenGame = new ScreenAnimation(this);
//        currentScreen = new ScreenScene(this);
//        setScreen(currentScreen);
        setScreen(currentScreen = new ScreenBucket(this));
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        currentScreen.dispose();
    }
}
