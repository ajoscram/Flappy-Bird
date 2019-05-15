package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

public class Bird extends Entity implements Drawable {
    private ArrayList<Texture> textures;
    private int frameCount;

    private int flapSpeed;
    private int flapCount;

    private float maxVelocity;

    public Bird(float x, float y, float maxVelocity, int flapSpeed){
        super();

        this.textures = new ArrayList();
        this.textures.add(new Texture("bird.png"));
        this.textures.add(new Texture("bird2.png"));
        this.frameCount = 0;
        this.flapSpeed = flapSpeed;
        this.flapCount = 0;

        this.x = x - (textures.get(0).getWidth()/2);
        this.y = y - (textures.get(0).getHeight()/2);
        this.startingX = this.x;
        this.startingY = this.y;

        this.maxVelocity = maxVelocity;
    }

    public boolean collides(List<Entity> entities){
        for(Entity entity : entities)
            if(this.collides(entity))
                return true;
        return false;
    }

    public boolean collides(Entity entity){
        if( this.x < entity.getX() + entity.getWidth() &&
            this.x + this.getWidth() > entity.getX() &&
            this.y < entity.getY() + entity.getHeight() &&
            this.y + this.getHeight() > entity.getY())
            return true;
        else
            return false;
    }

    private Texture getTexture(){
        if(!this.isStationary()) {
            if (flapCount == flapSpeed) {
                flapCount = 0;
                frameCount = (++frameCount) % textures.size();
            }
            flapCount++;
        }
        return textures.get(frameCount);
    }

    @Override
    public float getWidth(){
        return textures.get(frameCount).getWidth();
    }

    @Override
    public float getHeight(){
        return textures.get(frameCount).getHeight();
    }

    @Override
    public void setVelocity(float amount){
        super.setVelocity(amount);
        this.accelerate(0); //called to upper bound the velocity
    }

    //Movable
    @Override
    public void accelerate(float amount){
        super.accelerate(amount);
        if(velocity > maxVelocity)
            velocity = maxVelocity;
        else if(velocity < -maxVelocity)
            velocity = -maxVelocity;
    }

    //Drawable
    @Override
    public void draw(Batch batch){
        batch.draw(getTexture(), x, y);
    }

    @Override
    public void dispose(){
        for(Texture sprite : textures)
            sprite.dispose();
    }
}
