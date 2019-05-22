package com.ajoscram.flappy_bird;

import com.ajoscram.flappy_bird.scenes.PlayScene;
import com.ajoscram.flappy_bird.scenes.Scene;
import com.ajoscram.flappy_bird.scenes.SceneManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends ApplicationAdapter {

	private float width;
	private float height;

	private float verticalGap;
	private float horizontalGap;
	private float columnVelocity;
	private boolean drawCollidables;

	private ShapeRenderer renderer;
	private SpriteBatch batch;
	private Texture background;

	private SceneManager manager;

	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		verticalGap = 550;
		horizontalGap = 600;
		columnVelocity = -11f;

		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
		background = new Texture("bg.png");
		drawCollidables = true;

		manager = new SceneManager();
		manager.push(new PlayScene(manager, verticalGap, horizontalGap, columnVelocity));

		//setting gdx to clear the screen to black when glClear is called in render
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Scene scene = manager.peek();
		scene.update();

		batch.begin();
		batch.draw(background, 0, 0, width, height);
		scene.draw(batch);
		batch.end();

		if(drawCollidables && scene instanceof PlayScene) {
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			((PlayScene)scene).draw(renderer);
			renderer.end();
		}
    }
	
	@Override
	public void dispose(){
		batch.dispose();
		renderer.dispose();
		background.dispose();
		manager.dispose();
	}
}
