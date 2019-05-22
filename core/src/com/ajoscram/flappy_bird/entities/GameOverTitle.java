package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GameOverTitle extends Entity implements Drawable {

    private Texture texture;

    public GameOverTitle(float x, float y){
        super(0,0, 0, 0);
        this.texture = new Texture("game_over.png");

        this.x = this.startingX = x - (texture.getWidth()/2);
        this.y = this.startingY = y - (texture.getHeight());

        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.stopped = true;
        this.velocity = 0;
    }

    public float getHeight(){
        return texture.getHeight();
    }

    @Override
    public void reset(){
        super.reset();
        stop();
    }

    //Drawable
    @Override
    public void draw(Batch batch){
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
