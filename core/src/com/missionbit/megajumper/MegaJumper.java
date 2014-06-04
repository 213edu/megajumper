package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MegaJumper extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerImage;
    private int width;
    private int height;
    private Vector2 playerPosition;
    private Vector2 playerVelocity;
    private Vector2 gravity;

    @Override
    public void create () {
        batch = new SpriteBatch();
        playerImage = new Texture("missionbit.png");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        playerPosition = new Vector2();
        playerVelocity = new Vector2();
        gravity = new Vector2();

        resetGame(){
            playerVelocity.set(width/2,0);
            playerVelocity.set(0,0);
            gravity.set(0,-20);
        };
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateGame();
        drawGame();
    }

    private void resetGame() {
        //
    }

    private void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.justTouched()){
            playerVelocity.y = 500;
        }


        playerVelocity.add(gravity);
        playerPosition.mulAdd(playerVelocity,deltaTime);
    }

    private void drawGame() {
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
}
