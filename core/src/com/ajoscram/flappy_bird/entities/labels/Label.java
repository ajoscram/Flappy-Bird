package com.ajoscram.flappy_bird.entities.labels;

import com.ajoscram.flappy_bird.Drawable;
import com.ajoscram.flappy_bird.entities.Entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Label extends Entity implements Drawable {
    protected BitmapFont font;
    protected GlyphLayout layout;
    protected String text;

    public Label(float x, float y, String text, BitmapFont font){
        super(x, y, 0, 0);

        this.text = text;
        this.font = font;
        this.layout = new GlyphLayout(font, text);
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        layout.setText(font, text);
        this.text = text;
    }

    public BitmapFont getFont(){
        return font;
    }

    public void setFont(BitmapFont font){
        font.setColor(this.font.getColor());
        layout.setText(font, text);
        this.font = font;
    }

    @Override
    public float getWidth(){
        return layout.width;
    }

    @Override
    public float getHeight(){
        return layout.height;
    }

    //Drawable
    @Override
    public void draw(Batch batch) {
        font.draw(batch, layout, x, y);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
