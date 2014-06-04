package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MegaJumper extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private int width;
    private int height;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("missionbit.png");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        resetGame();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateGame();
        drawGame();
    }

    private void resetGame() {
        //reset any game state variables here
    }

    private void updateGame() {
        //apply all game rules here and check for win or loss
    }

    private void drawGame() {
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
}
