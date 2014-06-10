package com.missionbit.megajumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
    float highscore;
    int state;
    Platform singlePlatform;
    Music bgm;
    //Sound ding;



    float randomWithRange(float min, float max)
    {
        float range = (max - min) + 1;
        return (float)(Math.random() * range) + min;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();
        player = new Player();
        platforms = new ArrayList<Platform>();
        numPlatform = 16;
        camera = new OrthographicCamera(width,height);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        singlePlatform = new Platform();
        //Sound ding = Gdx.audio.newSound(Gdx.files.internal("ding.mp3"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

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
        player.velocity.set(500,0);
        gravity.set(0,-20);
        platforms.clear();

        for (int i = 0; i < numPlatform; i++){
            Platform platform = new Platform();
            platform.bounds.set(randomWithRange(0, platform.image.getWidth() + platform.bounds.x), height/8 * i, platform.image.getWidth(),platform.image.getHeight());
            if (platform.bounds.x > width - platform.image.getWidth()){
                platform.bounds.x = 0;
            }
            if (player.bounds.x < 0){
                platform.bounds.x = width - platform.image.getHeight();
            }
            platforms.add(platform);
        }

        singlePlatform.bounds.set(player.bounds.x, player.bounds.y - player.image.getHeight() - singlePlatform.image.getHeight(), singlePlatform.image.getWidth(), singlePlatform.image.getHeight());
        player.position.set(width/2 - player.image.getWidth(), height/2 - player.image.getHeight());
        player.bounds.set(width/2, player.image.getHeight(), player.image.getWidth(),player.image.getHeight());

        camera.position.set(width/2, height/2, 0);
        state = 0;
        bgm.play();
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
/*
            if (Gdx.input.justTouched()) {
                player.velocity.y = height;
            }
*/
            for (int i = 0; i < numPlatform; i++) {
                if (platforms.get(i).bounds.overlaps(player.bounds)) {
                        player.velocity.y = player.velocity.y + height/64;
                        System.out.println(player.velocity.y);
                }


                if (platforms.get(i).bounds.y < camera.position.y - height){
                    platforms.get(i).bounds.y = platforms.get(i).bounds.y + height * 2;
                    if(i < numPlatform && i != 0){
                        platforms.get(i).bounds.x = (randomWithRange(-platforms.get(i).image.getWidth() /3 , platforms.get(i).image.getWidth() / 3) + platforms.get(i-1).bounds.x);
                    }
                    if(i == 0){
                        platforms.get(i).bounds.x = (randomWithRange(-platforms.get(i).image.getWidth() /3 , platforms.get(i).image.getWidth() / 3) + platforms.get(numPlatform - 1).bounds.x);

                    }
                    if(platforms.get(i).bounds.x < 0 ){
                        platforms.get(i).bounds.x = platforms.get(i).bounds.x +  platforms.get(i).image.getWidth();
                    }

                    if(platforms.get(i).bounds.x > width - platforms.get(i).image.getWidth()){
                        platforms.get(i).bounds.x = platforms.get(i).bounds.x -  platforms.get(i).image.getWidth();
                    }
                }

            }



            if (player.velocity.y > highscore) {
                highscore = player.velocity.y;
            }

            if (player.position.x < -player.image.getWidth()) {
                player.position.x = width;
            }
            if (player.position.x > width) {
                player.position.x = 0;
            }
            //thanks @RyanShee
            player.velocity.x = (accelX * -400);
            player.velocity.add(gravity);
            player.position.mulAdd(player.velocity, deltaTime);
            player.bounds.setX(player.position.x);
            player.bounds.setY(player.position.y);

            if (camera.position.y < player.position.y) {
                camera.position.set(width / 2, player.position.y, 0);
            }


            if (player.position.y < camera.position.y - height) {
                state = 2;
            }
        }
            if (state == 2) {
                //display high score and touch to reset screen
                if (Gdx.input.justTouched()) {
                    resetGame();
                    state = 1;
                }
            }
    }

    private void drawGame() {

        if (state == 0){
            batch.begin();
            batch.draw(player.image, 0, 0);
            font.setColor(0, 0, 0, 1);
            font.draw(batch, "Tap to start", 0, height / 2);
            batch.end();
        }

        if (state == 1){
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.setScale(2);
            font.setColor(0, 0, 0, 1);
            font.draw(batch, "Speed: " + player.velocity.y/10, width / 2, camera.position.y + height / 2 - font.getLineHeight());
            font.draw(batch, "Fastest: " + highscore/10, width / 2, camera.position.y + height / 2 - 2*font.getLineHeight());

            batch.draw(player.image, player.position.x, player.position.y);
            for (int i= 0; i < numPlatform; i++){
                batch.draw(platforms.get(i).image, platforms.get(i).bounds.x, platforms.get(i).bounds.y);
            }
            batch.end();
        }

        else if (state == 2){
            batch.begin();
            font.setColor(0,0,0,1);
            font.draw(batch, "Fastest Speed: " + highscore/10, camera.position.x - width /2 , camera.position.y);
            font.draw(batch, "Tap to Restart", camera.position.x - width/ 2, camera.position.y - font.getLineHeight());
            batch.end();
        }

    }
}