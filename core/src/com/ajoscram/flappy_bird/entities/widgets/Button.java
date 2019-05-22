package com.ajoscram.flappy_bird.entities.widgets;

import com.ajoscram.flappy_bird.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Button extends Widget implements Drawable {

    private Texture texture;

    public Button(float x, float y, boolean center, String texturePath) {
        super(x, y, 0, 0);
        this.texture = new Texture(texturePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if(center)
            this.center(true);
        rectangles.add(new Rectangle(this.x, this.y, this.width, this.height));
    }

    public boolean isClicked() {
        if(Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = (Gdx.input.getY() - Gdx.graphics.getHeight()) * -1;
            return rectangles.get(0).contains(clickX, clickY);
        }
        return false;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
