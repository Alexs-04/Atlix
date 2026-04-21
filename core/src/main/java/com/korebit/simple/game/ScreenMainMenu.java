package com.korebit.simple.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.korebit.Main;
import com.korebit.screen.ScreenGame;

public class ScreenMainMenu extends ScreenGame{

    public ScreenMainMenu(Main game) {
        super(game);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.spriteBatch.begin();
        game.font.draw(game.spriteBatch, "Bienvenido a ConAgua ", 1 , 1.5f);
        game.font.draw(game.spriteBatch, "Presiona donde sea para comenzar", 1, 1);
        game.spriteBatch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new ScreenBucket(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
    
}
