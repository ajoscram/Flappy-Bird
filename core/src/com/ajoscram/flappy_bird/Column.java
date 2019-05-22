package com.ajoscram.flappy_bird;

import com.ajoscram.flappy_bird.entities.Boundary;
import com.ajoscram.flappy_bird.entities.Pipe;

import java.util.Random;

public class Column implements Movable, Stoppable{

    private float height;
    private float gapHeight;
    private float gapBound;

    private Pipe top;
    private Boundary gap;
    private Pipe bottom;

    private boolean stopped;

    public Column(float x, float height, float gap, float bound){
        this.height = height;
        this.gapHeight = gap;
        this.gapBound = bound;

        float gapY = getRandomGapY();
        this.top = new Pipe(x, gapY + gapHeight, Pipe.Direction.TOP);
        this.bottom = new Pipe(x, 0, Pipe.Direction.BOTTOM);
        this.bottom.setY(gapY - bottom.getHeight());
        this.bottom.setStartingY(gapY - bottom.getHeight());
        this.gap = new Boundary(x, gapY, top.getWidth(), gapHeight);

        this.stopped = false;
    }

    private final float getRandomGapY(){
        float gapY = new Random().nextFloat()*(height - gapHeight);
        if(gapY < gapBound)
            gapY += gapBound;
        else if(gapY + gapHeight > height - gapHeight)
            gapY -= gapBound;
        return gapY;
    }

    public float getX(){
        return bottom.getX();
    }

    public void setX(float x) {
        bottom.setX(x);
        top.setX(x);
        gap.setX(x);
    }

    public float getWidth(){
        return bottom.getWidth();
    }

    public void moveGap(){
        gap.setY(getRandomGapY());
        top.setY(gap.getY() + gap.getHeight());
        bottom.setY(gap.getY() - bottom.getHeight());
    }

    public Pipe getTopPipe(){
        return top;
    }
    public Pipe getBottomPipe(){
        return bottom;
    }
    public Boundary getGap(){
        return gap;
    }

    //Movable
    @Override
    public void accelerate(float amount) {
        top.accelerate(amount);
        bottom.accelerate(amount);
        gap.accelerate(amount);
    }

    @Override
    public void move(Direction direction) {
        if(!stopped) {
            top.move(direction);
            bottom.move(direction);
            gap.move(direction);
        }
    }

    @Override
    public void reset() {
        top.reset();
        bottom.reset();
        gap.reset();
        this.resume();
    }

    //Stoppable
    @Override
    public void stop() {
        stopped = true;
        top.stop();
        bottom.stop();
        gap.stop();
    }

    @Override
    public void resume() {
        stopped = false;
        top.resume();
        bottom.resume();
        gap.resume();
    }

    @Override
    public boolean isStopped(){
        return stopped;
    }
}