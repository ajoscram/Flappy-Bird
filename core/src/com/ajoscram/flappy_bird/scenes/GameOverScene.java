package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.Movable;
import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.entities.widgets.Image;
import com.ajoscram.flappy_bird.entities.widgets.labels.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GameOverScene extends Scene {

    private float acceleration;

    private PlayScene parent;
    private Image title;
    private Button replayButton;
    private Button menuButton;

    //High Score
    private Image highScore;
    private int highScoreCount;
    private Sound highScoreSound;

    public GameOverScene(SceneManager manager, PlayScene parent, boolean highScore){
        super(manager);
        this.parent = parent;

        acceleration = 60f;

        title = new Image(width/2, height/12*7, true, "game_over.png");
        title.setY(title.getY() - height);
        title.setStartingY(title.getY() - height);
        title.accelerate(acceleration);

        replayButton = new Button(width/2, height/12*4, true, "btn_replay.png");
        replayButton.setY(replayButton.getY() - height);
        replayButton.setStartingY(replayButton.getY() - height);
        replayButton.accelerate(acceleration);

        menuButton = new Button(width/2, height/12*2, true, "btn_menu.png");
        menuButton.setY(menuButton.getY() - height);
        menuButton.setStartingY(menuButton.getY() - height);
        menuButton.accelerate(acceleration);

        if(highScore) {
            this.highScore = new Image(width / 2, height / 12 * 9, true, "high_score.png");
            this.highScoreSound = Gdx.audio.newSound(Gdx.files.internal("high_score.mp3"));
        }
    }

    @Override
    public void update() {
        if(title.isStopped()) {
            title.resume();
            replayButton.resume();
            menuButton.resume();
        }
        if(title.getY() <= height/12*7 - title.getHeight()/2) {
            title.move(Movable.Direction.Y);
            replayButton.move(Movable.Direction.Y);
            menuButton.move(Movable.Direction.Y);
        }
        else if(replayButton.isClicked()){
            manager.pop();
            parent.reset();
        }
        else if(menuButton.isClicked()){
            manager.pop();
            manager.pop();
        }
    }

    @Override
    public void draw(Batch batch) {
        parent.draw(batch);

        title.draw(batch);
        replayButton.draw(batch);
        menuButton.draw(batch);

        if(highScoreSound != null && highScoreCount % 60 == 30){
            highScoreSound.play(0.3f);
        }

        if(highScore != null && highScoreCount++ % 60 > 30) {
            highScore.draw(batch);
        }
    }

    @Override
    public void dispose() {
        title.dispose();
        replayButton.dispose();
        menuButton.dispose();
    }
}
