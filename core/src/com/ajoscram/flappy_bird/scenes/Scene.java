package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.Drawable;
import com.ajoscram.flappy_bird.Stoppable;

public abstract class Scene implements Drawable, Stoppable {
    protected SceneManager manager;
    protected boolean stopped;

    public Scene(SceneManager manager){
        this.manager = manager;
        this.stopped = false;
    }

    public abstract void update();

    //Stoppable
    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void resume() {
        stopped = false;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }
}
