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
    private Player player;
    private Vector2 gravity;
    private Platform platform;

    @Override
    public void create () {
        batch = new SpriteBatch();
        playerImage = new Texture("missionbit.png");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();
        platform = new Platform();
        player = new Player();

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
        player.velocity.set(width/2,0);
        player.velocity.set(0,0);
        gravity.set(0,-20);
        player.bounds.set(width/2, 0, playerImage.getWidth(),playerImage.getHeight());
        platform.bounds.set(0, 0, platform.image.getWidth(),platform.image.getHeight());
    }

    private void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.justTouched() || player.bounds.overlaps(platform.bounds)){
            player.velocity.y = 500;
        }

        player.velocity.add(gravity);
        player.position.mulAdd(player.velocity,deltaTime);
        player.bounds.setX(player.position.x);
        player.bounds.setY(player.position.y);

    }

    private void drawGame() {
        batch.begin();
        batch.draw(playerImage, player.position.x, player.position.y);
        batch.draw(platform.image, 0 , 0);
        batch.end();
    }
}
