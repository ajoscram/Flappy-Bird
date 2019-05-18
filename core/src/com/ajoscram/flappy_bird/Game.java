package com.ajoscram.flappy_bird;

import com.ajoscram.flappy_bird.entities.Bird;
import com.ajoscram.flappy_bird.entities.Boundary;
import com.ajoscram.flappy_bird.entities.GameOverTitle;

import com.ajoscram.flappy_bird.entities.labels.Score;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

	private int lastColumnIndex; //last column positionally on the screen
	private boolean lost;

	private boolean drawCollidables;
	private boolean drawDrawables;
	private Color birdColor;
	private Color obstacleColor;
	private Color targetColor;

	private ShapeRenderer renderer;

	private SpriteBatch batch;
	private Texture background;

	private Score score;
	private Bird bird;
	private GameOverTitle gameOverTitle;
	private ArrayList<Column> columns;
	private ArrayList<Collidable> obstacles;
	private ArrayList<Collidable> targets;
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
		horizontalGap = 600;

		gravity = -1.5f;
		push = 25f;
		maxBirdVelocity = 50f;
		columnVelocity = -11f;
		birdFlapSpeed = 10;
		columnNumber = 2;
		lastColumnIndex = columnNumber - 1;
		lost = false;

		//debugging variables
		drawCollidables = false;
		drawDrawables  = true;
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
		birdColor = new Color(0, 0, 1, 0.8f);
		obstacleColor = new Color(1, 0, 0, 0.8f);
		targetColor = new Color(Color.rgba8888(0, 1, 0, 0.8f));

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
						Column last = columns.get(lastColumnIndex);
						column.setX(last.getX() + column.getWidth() + horizontalGap);
						column.moveGap();
						lastColumnIndex = (lastColumnIndex + 1) % columns.size();
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		update();

		if(drawDrawables) {
			batch.begin();
			batch.draw(background, 0, 0, width, height);
			for (Drawable drawable : drawables)
				drawable.draw(batch);
			batch.end();
		}

		if(drawCollidables){
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(targetColor);
			for(Collidable target : targets)
				target.draw(renderer);

			renderer.setColor(birdColor);
			bird.draw(renderer);

			renderer.setColor(obstacleColor);
			for(Collidable obstacle : obstacles)
				obstacle.draw(renderer);

			renderer.end();
		}
    }
	
	@Override
	public void dispose(){
		batch.dispose();
		renderer.dispose();
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
		lastColumnIndex = columnNumber - 1;
		bird.reset();
		score.reset();
		for(Column column : columns) {
			column.reset();
			column.moveGap();
			column.accelerate(columnVelocity);
		}
	}
}
