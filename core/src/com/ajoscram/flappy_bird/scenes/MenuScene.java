package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.entities.widgets.Image;
import com.badlogic.gdx.graphics.g2d.Batch;

public final class MenuScene extends Scene {

    private Image title;
    private Button playButton;
    private Button scoresButton;

    public MenuScene(SceneManager manager){
        super(manager);
        this.title = new Image(width/2, height/12*9, true, "title.png");
        this.playButton = new Button(width/2, height/12*6, true, "btn_play.png");
        this.scoresButton = new Button(width/2, height/12*2, true, "btn_scores.png");
    }

    @Override
    public void update() {
        if(playButton.isClicked())
            manager.push(new PlayScene(manager));
        else if(scoresButton.isClicked())
            manager.push(new ScoresScene(manager));
    }

    @Override
    public void draw(Batch batch) {
        title.draw(batch);
        playButton.draw(batch);
        scoresButton.draw(batch);
    }

    @Override
    public void dispose() {
        title.dispose();
        playButton.dispose();
    }
}
