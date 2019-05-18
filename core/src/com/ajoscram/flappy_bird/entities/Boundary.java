package com.ajoscram.flappy_bird.entities;

public class Boundary extends Entity {
    public Boundary(float x, float y, float width, float height){
        super(x, y, width, height);
        this.addRectangle(0, 0, width, height);
    }
}
