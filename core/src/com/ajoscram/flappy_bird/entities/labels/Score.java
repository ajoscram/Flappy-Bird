package com.ajoscram.flappy_bird.entities.labels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score extends Label {
    private int score;
    private boolean scoring;

    public Score(float x, float y, BitmapFont font){
        super(x, y, "0", font);
        this.score = 0;
        this.scoring = false;

        this.x = x - layout.width/2;
        this.y = y - layout.height/2;
        this.startingX = x;
        this.startingY = this.y;
    }

    public void score(){
        if(!scoring) {
            scoring = true;
            score++;
            setText(Integer.toString(score));
            x = startingX - layout.width / 2;
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
        x = startingX - layout.width / 2;
    }

}
