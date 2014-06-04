package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MegaJumper extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerImage;
    private int width;
    private int height;
    private Vector2 playerPosition;
    private Vector2 playerVelocity;
    private Vector2 gravity;
    private Rectangle playerBounds;
    private Platform platform;

    @Override
    public void create () {
        batch = new SpriteBatch();
        playerImage = new Texture("missionbit.png");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        playerPosition = new Vector2();
        playerVelocity = new Vector2();
        gravity = new Vector2();
        playerBounds = new Rectangle();
        platform = new Platform();

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
        playerVelocity.set(width/2,0);
        playerVelocity.set(0,0);
        gravity.set(0,-20);
        playerBounds.set(width/2, 0, playerImage.getWidth(),playerImage.getHeight());
        platform.bounds.set(0, 0, platform.image.getWidth(),platform.image.getHeight());
    }

    private void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.justTouched() || playerBounds.overlaps(platform.bounds)){
            playerVelocity.y = 500;
        }

        playerVelocity.add(gravity);
        playerPosition.mulAdd(playerVelocity,deltaTime);
        playerBounds.setX(playerPosition.x);
        playerBounds.setY(playerPosition.y);

    }

    private void drawGame() {
        batch.begin();
        batch.draw(playerImage, playerPosition.x, playerPosition.y);
        batch.draw(platform.image, 0 , 0);
        batch.end();
    }
}
