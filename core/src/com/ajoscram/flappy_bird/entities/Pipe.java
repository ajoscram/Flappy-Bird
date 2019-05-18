package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Pipe extends Entity implements Drawable {

    public enum Direction { TOP, BOTTOM }

    private Texture texture;

    public Pipe(float x, float y, Direction direction){
        super(x, y, 0, 0);

        if(direction == Direction.TOP)
            this.texture = new Texture("toptube.png");
        else if(direction == Direction.BOTTOM)
            this.texture = new Texture("bottomtube.png");

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        addRectangle(0, 0, width, height);
    }

    //Drawable
    @Override
    public void draw(Batch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
