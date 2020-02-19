package com.ajoscram.flappy_bird;

import com.ajoscram.flappy_bird.entities.widgets.labels.Label;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ScoreRow implements Drawable {

    protected Label index;
    protected Label score;
    protected Label date;

    public ScoreRow(float x, float y, float width, int index, String score, String date, BitmapFont font){
        this.index = new Label(x, y, false, Integer.toString(index), font);
        this.score = new Label(width/10*2.5f, y, false, score, font);
        this.date = new Label(width/10*7, y, false, date, font);
    }

    @Override
    public void draw(Batch batch) {
        index.draw(batch);
        score.draw(batch);
        date.draw(batch);
    }

    @Override
    public void dispose() {
        index.dispose();
        score.dispose();
        date.dispose();
    }
}
