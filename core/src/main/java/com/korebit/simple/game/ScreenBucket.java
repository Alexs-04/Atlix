package com.korebit.simple.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korebit.Main;
import com.korebit.screen.ScreenGame;

public class ScreenBucket extends ScreenGame {

    private Texture backgroundTexture;
    private Texture bucketTexture;
    private Texture dropTexture;
    private Sound dropSound;
    private Music music;
    private FitViewport viewport;
    private Sprite bucketSprite;
    private Array<Sprite> raindrops;
    private float dropTimer;
    
    protected  ScreenBucket(Main game) {
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
        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(bucketSprite.getWidth(), bucketSprite.getHeight());
        raindrops = new Array<>();
        dropTimer = 0;
        music.play();
        music.setLooping(true);
        music.setVolume(0.5f);
    }

    @Override
    public void render(float delta) {
        draw();
        input(delta);
        logic(delta);
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

    private void input(float delta) {
        boolean left = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D);

        float speed = 400f;

        if (left && !right) {
            bucketSprite.translateX(-speed * delta);
        }else if(!left && right){
            bucketSprite.translateX(speed * delta);
        }
    }

    private void logic(float delta) {
        float worldWidth = viewport.getWorldWidth();
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketSprite.getWidth()));

        dropTimer += delta;

        for(int i = 0; i < raindrops.size; i++){
            Sprite drop = raindrops.get(i);
            drop.translateY(-200 * delta);
            if(drop.getY() + drop.getHeight() < 0){
                raindrops.removeIndex(i);
                i--;
            }else if(drop.getBoundingRectangle().overlaps(bucketSprite.getBoundingRectangle())){
                dropSound.play();
                raindrops.removeIndex(i);
                i--;
            }
        }

        if (dropTimer > 1f) { // cada 1 segundo
            createDrop();
            dropTimer = 0;
        }
    }

    public void createDrop(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite drop = new Sprite(dropTexture);
        drop.setSize(drop.getWidth(), drop.getHeight());
        drop.setPosition(MathUtils.random(0, worldWidth - drop.getWidth()), worldHeight);
        raindrops.add(drop);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(backgroundTexture, 0, 0);
        bucketSprite.draw(game.spriteBatch);
        for (Sprite drop : raindrops) {
            drop.draw(game.spriteBatch);
        }
        game.spriteBatch.end();
    }
}
