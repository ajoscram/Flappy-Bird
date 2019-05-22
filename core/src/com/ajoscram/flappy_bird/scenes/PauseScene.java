package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.entities.widgets.Image;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PauseScene extends Scene{

    private PlayScene parent;

    private Image title;
    private Button playButton;

    public PauseScene(SceneManager manager, PlayScene parent){
        super(manager);
        this.parent = parent;
        title = new Image(width/2, height/4*3, true, "paused.png");
        playButton = new Button(width/2, height/2, true, "btn_play.png");
    }

    @Override
    public void update() {
        if(playButton.isClicked())
            manager.pop();
    }

    @Override
    public void draw(Batch batch) {
        parent.draw(batch);
        title.draw(batch);
        playButton.draw(batch);
    }

    @Override
    public void dispose() {
        title.dispose();
        playButton.dispose();
    }
}
