package com.korebit.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.korebit.Main;
import com.korebit.entity.Kula;

public class ScreenAnimation extends ScreenGame {

    private float stateTime;
    private OrthographicCamera camera;

    //private TextureAtlas textureAtlas;
    private Kula kula;
    BitmapFont font;

    public ScreenAnimation(Main game) {
        super(game);
    }

    @Override
    public void show() {
        kula = new Kula();
        font = new BitmapFont();
        stateTime = 0f;
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        game.spriteBatch.setProjectionMatrix(camera.combined);

        game.spriteBatch.begin();
        font.draw(game.spriteBatch, "Coordenadas: X = " + kula.getSprite().getX(), 10, 20);
        kula.update(delta, stateTime);
        kula.getSprite().draw(game.spriteBatch);
        game.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        kula.dispose();
        font.dispose();
    }
}
