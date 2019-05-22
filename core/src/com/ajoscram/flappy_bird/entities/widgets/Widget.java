package com.ajoscram.flappy_bird.entities.widgets;

import com.ajoscram.flappy_bird.entities.Entity;

public abstract class Widget extends Entity {

    public Widget(float x, float y, float width, float height){
        super(x, y, width, height);
    }

    protected void center(boolean changeStartingPosition){
        x = x - width/2;
        y = y - height/2;
        if(changeStartingPosition){
            startingX = x;
            startingY = y;
        }
    }
}
