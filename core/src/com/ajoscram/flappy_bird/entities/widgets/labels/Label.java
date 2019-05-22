package com.ajoscram.flappy_bird.entities.widgets.labels;

import com.ajoscram.flappy_bird.Drawable;
import com.ajoscram.flappy_bird.entities.widgets.Widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Label extends Widget implements Drawable {
    protected BitmapFont font;
    protected GlyphLayout layout;
    protected String text;

    public Label(float x, float y, boolean center, String text, BitmapFont font){
        super(x, y, 0, 0);
        this.text = text;
        this.font = font;
        this.layout = new GlyphLayout(font, text);
        this.width = layout.width;
        this.height = layout.height;
        if(center)
            this.center(true);
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        layout.setText(font, text);
        this.text = text;
        this.width = layout.width;
        this.height = layout.height;
    }

    public BitmapFont getFont(){
        return font;
    }

    public void setFont(BitmapFont font){
        font.setColor(this.font.getColor());
        this.font = font;
        layout.setText(font, text);
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
