package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private BitmapFont font;
    private int score;
    int highscore;
    int state;


    @Override
    public void create () {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();
        player = new Player();
        platforms = new ArrayList<Platform>();
        numPlatform = 100;
        camera = new OrthographicCamera(width,height);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);


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
        score = 0;
        player.velocity.set(width/2,0);
        player.velocity.set(0,0);
        gravity.set(0,-20);
        player.bounds.set(width/2, 0, player.image.getWidth(),player.image.getHeight());

        for (int i = 0; i < numPlatform; i++){
            Platform platform = new Platform();
            platform.bounds.set(((float)(Math.random() * width)), height/3 * i, platform.image.getWidth(),platform.image.getHeight());
            platforms.add(platform);
        }


        camera.position.set(width/2, height/2, 0);
        state = 0;
    }

    private void updateGame() {

        if (state == 0) {
            //display starting screen
            if (Gdx.input.justTouched()) {
                state = 1;
            }
        }

        if (state == 1) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            float accelX = Gdx.input.getAccelerometerX();

            if (Gdx.input.justTouched()) {
                player.velocity.y = 50;
            }

            for (int i = 0; i < numPlatform; i++) {
                if (platforms.get(i).bounds.overlaps(player.bounds)) {
                    player.velocity.y = height;
                    score++;
                }
            }

            if (score > highscore) {
                highscore = score;
            }

            if (player.position.x < -player.image.getWidth()) {
                player.position.x = width;
            }
            if (player.position.x > width) {
                player.position.x = 0;
            }
            //thanks @RyanShee
            player.velocity.x = (accelX * -200);
            player.velocity.add(gravity);
            player.position.mulAdd(player.velocity, deltaTime);
            player.bounds.setX(player.position.x);
            player.bounds.setY(player.position.y);

            if (camera.position.y < player.position.y) {
                camera.position.set(width / 2, player.position.y, 0);
            }

            if (player.position.y < camera.position.y - height / 2) {
                System.out.println("u dead LOL");
                state = 2;


            }
        }

            if (state == 2) {
                System.out.println("loser");
                //display high score and touch to reset screen
                if (Gdx.input.justTouched()) {
                    System.out.println("touched");
                    resetGame();
                }
            }
    }

    private void drawGame() {

        if (state == 0){
            batch.begin();
            batch.draw(player.image, 0 , 0);
            font.setColor(0,0,0,1);
            font.draw(batch, "Tap to start", 0, height/2);
            System.out.println("begin");
            batch.end();
        }

        if (state == 1){
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.setScale(2);
            font.setColor(0, 0, 0, 1);
            font.draw(batch, "" + score, width / 2, camera.position.y + height / 2 - font.getLineHeight());
            batch.draw(player.image, player.position.x, player.position.y);
            for (int i= 0; i < numPlatform; i++){
                batch.draw(platforms.get(i).image, platforms.get(i).bounds.x, platforms.get(i).bounds.y);
            }
            batch.end();
        }

        else if (state == 2){
            batch.begin();
            font.setColor(0,0,0,1);
            font.draw(batch, "High Score: " + highscore, camera.position.x - width /2 , camera.position.y);
            font.draw(batch, "Tap to Restart", camera.position.x - width/ 2, camera.position.y - font.getLineHeight());
            batch.end();
        }

    }
}
