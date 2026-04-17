package com.korebit.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.korebit.Main;
import com.korebit.entity.KulaActor;

public class ScreenScene extends ScreenGame {

    private final Stage stage;
    private KulaActor kulaActor;

    public ScreenScene(Main game) {
        super(game);
        this.stage = new Stage();
        this.kulaActor = new KulaActor();

        stage.addActor(kulaActor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
