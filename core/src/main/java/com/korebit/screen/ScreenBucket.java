package com.korebit.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korebit.Main;

public class ScreenBucket extends ScreenGame {

    private Texture backgroundTexture;
    private Texture bucketTexture;
    private Texture dropTexture;
    private Sound dropSound;
    private Music music;
    private FitViewport viewport;

    public ScreenBucket(Main game) {
        super(game);
    }

    @Override
    public void show() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("audio/drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        viewport = new FitViewport(800, 500);
    }

    @Override
    public void render(float delta) {
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        backgroundTexture.dispose();
        bucketTexture.dispose();
        dropTexture.dispose();
        dropSound.dispose();
        music.dispose();
    }

    private void input() {
        boolean left = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT);

        if (left && !right) {

        }
    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(backgroundTexture, 0, 0);
        game.spriteBatch.draw(bucketTexture, 1, 1);
        game.spriteBatch.end();
    }
}
