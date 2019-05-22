package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.Drawable;
import com.ajoscram.flappy_bird.Stoppable;
import com.badlogic.gdx.Gdx;

public abstract class Scene implements Drawable, Stoppable {
    protected SceneManager manager;
    protected boolean stopped;
    protected float width;
    protected float height;

    public Scene(SceneManager manager){
        this.manager = manager;
        this.stopped = false;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
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
