package com.missionbit.megajumper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by ryanhu on 6/4/14.
 */
public class Player {
    public Texture image;
    public Rectangle bounds;
    public Vector2 position;
    public Vector2 velocity;

    public Player(){
        image = new Texture("missionbit.png");
        bounds = new Rectangle();
        position = new Vector2();
        velocity = new Vector2();
    }
}
