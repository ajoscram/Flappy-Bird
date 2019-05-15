package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Movable;

public abstract class Entity implements Movable {
    public static final int NONE = -1;

    protected float x;
    protected float y;
    protected float startingX;
    protected float startingY;

    protected float width;
    protected float height;

    protected float velocity;
    private boolean stationary;

    public Entity(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.startingX = x;
        this.startingY = y;

        this.width = width;
        this.height = height;

        this.velocity = 0;
        this.stationary = true;
    }

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
        this.startingX = x;
        this.startingY = y;

        width = height = NONE;

        this.velocity = 0;
        this.stationary = true;
    }

    public Entity(){
        x = startingX = y = startingY = width = height = NONE;

        this.velocity = 0;
        this.stationary = true;
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setStartingX(float x){
        this.startingX = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setStartingY(float y){
        this.startingY = y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getVelocity(){
        return velocity;
    }

    public void setVelocity(float amount){
        this.velocity = amount;
    }

    public boolean isStationary(){
        return stationary;
    }

    //Movable
    @Override
    public void accelerate(float amount){
        velocity += amount;
    }

    @Override
    public void move(Direction direction){
        if(direction == Direction.X || direction == Direction.BOTH)
            x += velocity;
        if(direction == Direction.Y || direction == Direction.BOTH)
            y += velocity;
        this.stationary = false;
    }

    @Override
    public void stop() {
        this.velocity = 0;
        this.stationary = true;
    }

    @Override
    public void reset(){
        this.x = startingX;
        this.y = startingY;
        this.stop();
    }
}