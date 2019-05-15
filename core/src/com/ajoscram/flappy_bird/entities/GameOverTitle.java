package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GameOverTitle extends Entity implements Drawable {

    private Texture texture;

    public GameOverTitle(float x, float y){
        super();
        this.texture = new Texture("game_over.png");

        this.x = x - (texture.getWidth()/2);
        this.y = y - (texture.getHeight());
        this.startingX = this.x;
        this.startingY = this.y;

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.velocity = 0;
    }

    @Override
    public void draw(Batch batch){
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
