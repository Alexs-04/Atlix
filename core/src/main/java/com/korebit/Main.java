package com.korebit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.korebit.screen.ScreenGame;
import com.korebit.simple.game.ScreenMainMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {

    public SpriteBatch spriteBatch;
    public ScreenGame currentScreen;
    public BitmapFont font;
    public Viewport viewport;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        setScreen(currentScreen = new ScreenMainMenu(this));
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        currentScreen.dispose();
    }

    @Override
    public void render(){
        super.render();
    }
}
