package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.entities.widgets.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public final class MenuScene extends Scene {

    private Image title;
    private Button easyButton;
    private Button normalButton;
    private Button hardButton;


    public MenuScene(SceneManager manager){
        super(manager);
        this.title = new Image(width/2, height/12*9, true, "title.png");
        this.easyButton = new Button(width/2, height/12*6, true, "btn_easy.png");
        this.normalButton = new Button(width/2, height/12*4, true, "btn_normal.png");
        this.hardButton = new Button(width/2, height/12*2, true, "btn_hard.png");
    }

    @Override
    public void update() {
        if(easyButton.isClicked())
            manager.push(new PlayScene(manager, PlayScene.Difficulty.EASY));
        else if(normalButton.isClicked())
            manager.push(new PlayScene(manager, PlayScene.Difficulty.NORMAL));
        else if(hardButton.isClicked())
            manager.push(new PlayScene(manager, PlayScene.Difficulty.HARD));
    }

    @Override
    public void draw(Batch batch) {
        title.draw(batch);
        easyButton.draw(batch);
        normalButton.draw(batch);
        hardButton.draw(batch);
    }

    @Override
    public void dispose() {
        title.dispose();
        easyButton.dispose();
        normalButton.dispose();
        hardButton.dispose();
    }
}
