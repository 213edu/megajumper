package com.missionbit.megajumper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by ryanhu on 6/4/14.
 */
public class Platform {
    public Texture image;
    public Rectangle bounds;

    public Platform(){
        image = new Texture("platform.jpg");
        bounds = new Rectangle();

    }

}
