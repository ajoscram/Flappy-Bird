package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

public class Bird extends Entity implements Drawable {
    private ArrayList<Texture> textures;
    private int frameCount;

    private int flapSpeed;
    private int flapCount;

    private float maxVelocity;

    public Bird(float x, float y, float maxVelocity, int flapSpeed){
        super(0, y, 0, 0);

        this.textures = new ArrayList();
        this.textures.add(new Texture("bird.png"));
        this.textures.add(new Texture("bird2.png"));

        this.frameCount = 0;
        this.flapSpeed = flapSpeed;
        this.flapCount = 0;

        this.x = this.startingX = x - textures.get(0).getWidth()/2;

        this.width = textures.get(0).getWidth();
        this.height = textures.get(0).getHeight();

        this.maxVelocity = maxVelocity;

        addCircle(width/2, this.height/2, this.height/2);
        addRectangle(95, 12,37, 35);
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
