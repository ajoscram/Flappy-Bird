package com.ajoscram.flappy_bird.entities.widgets;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Image extends Widget implements Drawable {

    private Texture texture;

    public Image(float x, float y, boolean center, String texturePath){
        super(x, y, 0, 0);
        this.texture = new Texture(texturePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if(center)
            this.center(true);
        this.stopped = true;
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
