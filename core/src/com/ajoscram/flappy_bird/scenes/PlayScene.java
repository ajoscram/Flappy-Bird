package com.ajoscram.flappy_bird.scenes;

import com.ajoscram.flappy_bird.Collidable;
import com.ajoscram.flappy_bird.Column;
import com.ajoscram.flappy_bird.Drawable;
import com.ajoscram.flappy_bird.Movable;
import com.ajoscram.flappy_bird.Scores;
import com.ajoscram.flappy_bird.entities.Bird;
import com.ajoscram.flappy_bird.entities.Boundary;
import com.ajoscram.flappy_bird.entities.widgets.Button;
import com.ajoscram.flappy_bird.entities.widgets.labels.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Stack;

public final class PlayScene extends Scene {

    private static final float HORIZONTAL_GAP = 550;
    private static final float VERTICAL_GAP = 600;
    private static final float COLUMN_VELOCITY = -11;

    //difficulty
    private float horizontalGap;
    private float verticalGap;
    private float columnVelocity;

    //default
    private float verticalGapBound;
    private float gravity;
    private float push;
    private float maxBirdVelocity;
    private int birdFlapSpeed;
    private int columnNumber;
    private int lastColumnIndex; //last column positionally on the screen

    //debugging
    private Color birdColor;
    private Color obstacleColor;
    private Color targetColor;

    //entities
    private Button pauseButton;
    private Score score;
    private Bird bird;
    private ArrayList<Column> columns;
    private Stack<Drawable> drawables;
    private ArrayList<Collidable> obstacles;
    private ArrayList<Collidable> targets;

    public PlayScene(SceneManager manager){
        super(manager);

        //difficulty
        this.verticalGap = VERTICAL_GAP;
        this.columnVelocity = COLUMN_VELOCITY;
        this.horizontalGap = HORIZONTAL_GAP;

        //default
        verticalGapBound = 100;
        gravity = -1.5f;
        push = 25f;
        maxBirdVelocity = 50f;
        birdFlapSpeed = 10;
        columnNumber = 2;
        lastColumnIndex = columnNumber - 1;

        //debugging
        birdColor = new Color(0, 0, 1, 0.5f);
        obstacleColor = new Color(1, 0, 0, 0.5f);
        targetColor = new Color(0, 1, 0, 0.5f);

        //entities
        pauseButton = new Button(width/12*10, height/12*1, true, "btn_pause.png");
        score = new Score(width/2, (height/10)*9, new BitmapFont(Gdx.files.internal("fonts/score.fnt")));
        bird = new Bird(width/4, height/2, maxBirdVelocity, birdFlapSpeed);
        columns = getColumns(columnNumber);

        //drawables, obstacles & targets
        drawables = new Stack();
        obstacles = new ArrayList();
        targets = new ArrayList();
        drawables.push(bird);
        obstacles.add(new Boundary(0, -1, width, 1)); //floor
        obstacles.add(new Boundary(0, height, width, 1)); //ceiling
        for(Column column : columns) {
            //accelerating columns to their base velocity
            column.accelerate(columnVelocity);
            //adding the top and bottom pipes as drawables
            drawables.add(column.getTopPipe());
            drawables.add(column.getBottomPipe());
            //adding the top and bottom pipes as obstacles
            obstacles.add(column.getTopPipe());
            obstacles.add(column.getBottomPipe());
            //adding the gap as a target
            targets.add(column.getGap());
        }
        //finally pushing drawn elements on top of other drawables
        drawables.push(pauseButton);
        drawables.push(score);
    }

    private ArrayList<Column> getColumns(int n){
        float nextX = width + horizontalGap;
        ArrayList<Column> columns = new ArrayList();
        while(n > 0){
            Column column = new Column(nextX, height, verticalGap, verticalGapBound);
            columns.add(column);
            nextX += columns.get(columns.size()-1).getWidth() + horizontalGap;
            n--;
        }
        return columns;
    }

    @Override
    public void update() {
        if(pauseButton.isClicked()){
            this.stop();
            manager.push(new PauseScene(manager, this));
        } else if(!bird.collides(obstacles)){
            //check for scoring
            if(bird.collides(targets))
                score.score();
            else
                score.stopScoring();

            //move the bird
            if (Gdx.input.justTouched())
                bird.setVelocity(push);
            else
                bird.accelerate(gravity);
            bird.move(Movable.Direction.Y);

            //move the columns
            for(Column column : columns){
                if(column.getX() + column.getWidth() < 0 ){
                    Column last = columns.get(lastColumnIndex);
                    column.setX(last.getX() + column.getWidth() + horizontalGap);
                    column.moveGap();
                    lastColumnIndex = (lastColumnIndex + 1) % columns.size();
                }
                else
                    column.move(Movable.Direction.X);
            }
        } else {
            this.stop();
            boolean highScore = Scores.add(score.getScore());
            manager.push(new GameOverScene(manager, this, highScore));
        }
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(targetColor);
        for(Collidable target : targets)
            target.draw(renderer);

        renderer.setColor(birdColor);
        bird.draw(renderer);

        renderer.setColor(obstacleColor);
        for(Collidable obstacle : obstacles)
            obstacle.draw(renderer);
    }

    //Drawable
    @Override
    public void draw(Batch batch) {
        for(Drawable drawable : drawables)
            if(!this.isStopped() || (this.isStopped() && drawable != pauseButton))
                drawable.draw(batch);
    }

    @Override
    public void dispose() {
        for(Drawable drawable : drawables)
            drawable.dispose();
    }

    //Stoppable
    @Override
    public void stop() {
        super.stop();
        bird.stop();
        for(Column column : columns)
            column.stop();
    }

    @Override
    public void resume() {
        super.resume();
        bird.resume();
        for(Column column : columns)
            column.resume();
    }

    public void reset(){
        lastColumnIndex = columnNumber - 1;
        bird.reset();
        score.reset();
        for(Column column : columns) {
            column.reset();
            column.moveGap();
        }
    }
}
