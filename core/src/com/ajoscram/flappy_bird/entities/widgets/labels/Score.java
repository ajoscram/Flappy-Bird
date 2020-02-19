package com.ajoscram.flappy_bird.entities.widgets.labels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score extends Label {
    private int score;
    private boolean scoring;

    public Score(float x, float y, BitmapFont font){
        super(x, y, true,"0", font);
        this.startingX = x;
        this.score = 0;
        this.scoring = false;
    }

    public int getScore(){
        return score;
    }

    public void score(){
        if(!scoring) {
            scoring = true;
            score++;
            setText(Integer.toString(score));
            x = startingX - width / 2;
        }
    }

    public void stopScoring(){
        scoring = false;
    }

    @Override
    public void reset(){
        super.reset();
        score = 0;
        setText(Integer.toString(score));
        x = startingX - width / 2;
    }

}
