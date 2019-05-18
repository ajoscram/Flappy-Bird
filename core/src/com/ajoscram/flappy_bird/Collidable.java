package com.ajoscram.flappy_bird;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public interface Collidable{
    List<Circle> getCircles();
    List<Rectangle> getRectangles();
    boolean collides(Collidable collidable);
    boolean collides(List<Collidable> collidables);
    void draw(ShapeRenderer renderer);
}
