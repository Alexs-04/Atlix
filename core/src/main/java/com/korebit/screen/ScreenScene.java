package com.korebit.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.korebit.Main;
//import com.korebit.entity.KulaActor;
import com.korebit.entity.Soldier;
import com.korebit.entity.WarTank;

public class ScreenScene extends ScreenGame {

    private final Stage stage;
//    private KulaActor kulaActor;
    private final Soldier soldier;
    private final WarTank warTank;

    public ScreenScene(Main game) {
        super(game);
        this.stage = new Stage();
        // this.kulaActor = new KulaActor();
        this.soldier = new Soldier();
        this.warTank = new WarTank();
        stage.addActor(soldier);
        stage.addActor(warTank);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);

        if (soldier.getBounds().overlaps(warTank.getBounds())) {
            soldier.onCollision(warTank);
            warTank.onCollision(soldier);
        }

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
//        kulaActor.dispose();
        soldier.dispose();
        warTank.dispose();
    }
}
