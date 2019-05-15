package com.ajoscram.flappy_bird;

import com.ajoscram.flappy_bird.entities.Bird;
import com.ajoscram.flappy_bird.entities.Boundary;
import com.ajoscram.flappy_bird.entities.Entity;
import com.ajoscram.flappy_bird.entities.GameOverTitle;

import com.ajoscram.flappy_bird.entities.labels.Score;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Stack;

public class Game extends ApplicationAdapter {

	private int width;
	private int height;
	private float verticalGap;
	private float verticalGapBound;
	private float horizontalGap;

	private float gravity;
	private float push;
	private float maxBirdVelocity;
	private float columnVelocity;
	private int birdFlapSpeed;
	private int columnNumber;

	private boolean lost;

	private SpriteBatch batch;
	private Texture background;

	private Score score;
	private Bird bird;
	private GameOverTitle gameOverTitle;
	private ArrayList<Column> columns;
	private ArrayList<Entity> obstacles;
	private ArrayList<Entity> targets;
	private Stack<Drawable> drawables;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		//variables
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		verticalGap = 550;
		verticalGapBound = 100;
		horizontalGap = 500;

		gravity = -1.5f;
		push = 25f;
		maxBirdVelocity = 50f;
		columnVelocity = -11f;
		birdFlapSpeed = 10;
		columnNumber = 4;
		lost = false;

		//entities
		score = new Score(width/2, (height/10)*9, new BitmapFont(Gdx.files.internal("fonts/score.fnt")));
		gameOverTitle = new GameOverTitle(width / 2, 0);
		bird = new Bird(width/4, height/2, maxBirdVelocity, birdFlapSpeed);
		columns = getColumns(columnNumber);

		//drawables
		drawables = new Stack();
		drawables.push(bird);

		//obstacles
		obstacles = new ArrayList();
		obstacles.add(new Boundary(0, -1, width, 1)); //floor
		obstacles.add(new Boundary(0, height, width, 1)); //ceiling


        targets = new ArrayList();
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
        //adding the score last to the drawable stack so its on top
        drawables.push(score);
	}

	private void update(){
		if(!lost) {
			if(bird.collides(obstacles))
				lost = true;
			else {
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
						column.setX(width + (horizontalGap + column.getWidth()) * (columns.size()-1));
						column.moveGap();
					}
					else
						column.move(Movable.Direction.X);
				}
			}
		} else {
			if(gameOverTitle.isStationary()) {
				drawables.push(gameOverTitle);
				gameOverTitle.accelerate(30);
				stop();
			}
			if(gameOverTitle.getY() <= (height - gameOverTitle.getHeight())/2)//move the title to the middle of the screen
				gameOverTitle.move(Movable.Direction.Y);
			else if(Gdx.input.justTouched()){
				gameOverTitle.reset();
				drawables.remove(gameOverTitle);
				reset();
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();

		batch.begin();
		batch.draw(background, 0, 0, width, height);
		for(Drawable drawable : drawables)
			drawable.draw(batch);
		batch.end();
    }
	
	@Override
	public void dispose(){
		batch.dispose();
		background.dispose();
		for(Drawable drawable : drawables)
		    drawable.dispose();
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

	private void stop(){
		bird.stop();
	    for(Column column : columns)
	    	column.stop();
	}

	private void reset(){
		lost = false;
		bird.reset();
		score.reset();
		for(Column column : columns) {
			column.reset();
			column.moveGap();
			column.accelerate(columnVelocity);
		}
	}
}
