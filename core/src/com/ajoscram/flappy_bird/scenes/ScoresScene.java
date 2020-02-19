package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.Scores;
import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.ScoreRow;
import com.ajoscram.flappy_bird.entities.widgets.labels.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.ArrayList;

public class ScoresScene extends Scene {

    private BitmapFont font;
    private Label title;
    private ArrayList<ScoreRow> rows;
    private Button backButton;

    public ScoresScene(SceneManager manager){
        super(manager);
        this.font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        this.title = new Label(width/2, height/12*11, true, "HIGH SCORES", font);
        this.rows = getScoreRows();
        this.backButton = new Button(width/2, height/12*2, true, "btn_back.png");
    }

    private ArrayList<ScoreRow> getScoreRows(){
        ArrayList<ScoreRow> rows = new ArrayList();
        String[][] scores = Scores.get();
        if(scores != null) {
            int count = 0;
            for (String[] score : scores) {
                String value = score[0];
                String date = score[1];
                rows.add(new ScoreRow(width / 12, height / 12 * (9 - count), width / 12 * 10, count + 1, value, date, font));
                count++;
            }
        }
        return rows;
    }

    @Override
    public void update() {
        if(backButton.isClicked())
            manager.pop();
    }

    @Override
    public void draw(Batch batch) {
        title.draw(batch);
        for(ScoreRow row : rows)
            row.draw(batch);
        backButton.draw(batch);
    }

    @Override
    public void dispose() {
        title.dispose();
        for(ScoreRow row : rows)
            row.dispose();
        backButton.dispose();
    }
}
