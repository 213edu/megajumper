package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MegaJumper extends ApplicationAdapter {
    private SpriteBatch batch;
    private int width;
    private int height;
    private Player player;
    private Vector2 gravity;
    private ArrayList<Platform> platforms;
    private int numPlatform;
    private OrthographicCamera camera;

    @Override
    public void create () {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();
        player = new Player();
        platforms = new ArrayList<Platform>();
        numPlatform = 7;
        camera = new OrthographicCamera(width,height);

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
        player.bounds.set(width/2, 0, player.image.getWidth(),player.image.getHeight());
        camera.position.set(player.position.x, player.position.y, 0);

        for (int i = 0; i < numPlatform; i++){
            Platform platform = new Platform();
            platform.bounds.set(((float)(Math.random() * width)), height/7 * i, platform.image.getWidth(),platform.image.getHeight());
            platforms.add(platform);
        }
    }

    private void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float accelX = Gdx.input.getAccelerometerX();

        if(Gdx.input.justTouched()){
            player.velocity.y = 500;
        }

        for (int i = 0; i< numPlatform; i++){
            if (platforms.get(i).bounds.overlaps(player.bounds)){
                player.velocity.y = 1000;
            }
        }


        player.velocity.x = (accelX * -200);
        player.velocity.add(gravity);
        player.position.mulAdd(player.velocity,deltaTime);
        player.bounds.setX(player.position.x);
        player.bounds.setY(player.position.y);

    }

    private void drawGame() {
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(player.image, player.position.x, player.position.y);
        for (int i= 0; i < numPlatform; i++){
            batch.draw(platforms.get(i).image, platforms.get(i).bounds.x, platforms.get(i).bounds.y);
        }
        batch.end();
    }
}
