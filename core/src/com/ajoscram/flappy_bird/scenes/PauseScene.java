package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.entities.widgets.Image;
import com.ajoscram.flappy_bird.entities.widgets.labels.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PauseScene extends Scene{

    private PlayScene parent;

    private Image title;
    private Texture background;
    private Label info;

    public PauseScene(SceneManager manager, PlayScene parent){
        super(manager);
        this.parent = parent;
        background = new Texture("bg_pause.png");
        title = new Image(width/2, height/4*3, true, "paused.png");
        info = new Label(width/2, height/12, true, "Tap the screen to resume.", new BitmapFont(Gdx.files.internal("fonts/font.fnt")));
    }

    @Override
    public void update() {
        if(Gdx.input.justTouched())
            manager.pop();
    }

    @Override
    public void draw(Batch batch) {
        parent.draw(batch);

        batch.draw(background, 0, 0, width, height);
        title.draw(batch);
        info.draw(batch);
    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        info.dispose();
    }
}
