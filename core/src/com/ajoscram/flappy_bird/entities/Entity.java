package com.ajoscram.flappy_bird.entities;

import com.ajoscram.flappy_bird.Collidable;
import com.ajoscram.flappy_bird.Movable;
import com.ajoscram.flappy_bird.Stoppable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Movable, Stoppable, Collidable {
    protected float x;
    protected float y;
    protected float startingX;
    protected float startingY;
    protected float width;
    protected float height;

    protected ArrayList<Circle> circles;
    protected ArrayList<Rectangle> rectangles;

    protected float velocity;
    protected boolean stopped;

    public Entity(float x, float y, float width, float height){
        this.x = this.startingX = x;
        this.y = this.startingY = y;
        this.width = width;
        this.height = height;

        this.circles = new ArrayList();
        this.rectangles = new ArrayList();

        this.velocity = 0;
        this.stopped = false;
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        float offset;
        for(Circle circle : circles) {
            offset = circle.x - this.x;
            circle.setX(x + offset);
        }
        for(Rectangle rectangle : rectangles) {
            offset = rectangle.x - this.x;
            rectangle.setX(x + offset);
        }
        this.x = x;
    }

    public void setStartingX(float x){
        this.startingX = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        float offset;
        for(Circle circle : circles) {
            offset = circle.y - this.y;
            circle.setY(y + offset);
        }
        for(Rectangle rectangle : rectangles) {
            offset = rectangle.y - this.y;
            rectangle.setY(y + offset);
        }
        this.y = y;
    }

    public void setStartingY(float y){
        this.startingY = y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void addCircle(float xOffset, float yOffset, float radius){
        circles.add(new Circle(x + xOffset, y + yOffset, radius));
    }

    public void addRectangle(float xOffset, float yOffset, float width, float height){
        rectangles.add(new Rectangle(x + xOffset, y + yOffset, width, height));
    }

    public float getVelocity(){
        return velocity;
    }

    public void setVelocity(float amount){
        this.velocity = amount;
    }

    //Movable
    @Override
    public void accelerate(float amount){
        velocity += amount;
    }

    @Override
    public void move(Direction direction){
        if(!stopped) {
            if (direction == Direction.X || direction == Direction.BOTH)
                setX(x + velocity);
            if (direction == Direction.Y || direction == Direction.BOTH)
                setY(y + velocity);
        }
    }

    @Override
    public void reset(){
        setX(startingX);
        setY(startingY);
        this.resume();
    }

    //Stoppable
    @Override
    public void stop() {
        this.stopped = true;
    }

    @Override
    public void resume(){
        this.stopped = false;
    }

    @Override
    public boolean isStopped(){
        return stopped;
    }

    //Collidable
    @Override
    public List<Circle> getCircles(){
        return circles;
    }

    @Override
    public List<Rectangle> getRectangles(){
        return rectangles;
    }

    @Override
    public boolean collides(Collidable collidable){
        for(Circle own : circles) {
            for(Circle circle : collidable.getCircles())
                if(Intersector.overlaps(own, circle))
                    return true;
            for(Rectangle rectangle : collidable.getRectangles())
                if(Intersector.overlaps(own, rectangle))
                    return true;
        }
        for(Rectangle own : rectangles) {
            for(Circle circle : collidable.getCircles())
                if(Intersector.overlaps(circle, own))
                    return true;
            for(Rectangle rectangle : collidable.getRectangles())
                if(Intersector.overlaps(own, rectangle))
                    return true;
        }
        return false;
    }

    @Override
    public boolean collides(List<Collidable> collidables){
        for(Collidable collidable : collidables)
            if(this.collides(collidable))
                return true;
        return false;
    }

    @Override
    public void draw(ShapeRenderer renderer){
        for(Rectangle rectangle : rectangles)
            renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        for(Circle circle : circles)
            renderer.circle(circle.x, circle.y, circle.radius);
    }
}
